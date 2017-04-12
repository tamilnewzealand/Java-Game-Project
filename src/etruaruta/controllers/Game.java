package etruaruta.controllers;

import etruaruta.Main;
import etruaruta.models.*;
import java.util.ArrayList;

/**
 * This is the controller class that processes all
 * the game logic and algorithms.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class Game{
    private boolean isFinished = false, countingDown = true;
    private int timeElapsed = 0, hiScore = 0, countDown = 91;
    public General[] generals;
    public Ball[] balls;
    private Ball ball;
    public ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    public ArrayList<AIController> AIs = new ArrayList<AIController>();
    public ArrayList<Marker> markers = new ArrayList<>();
    private ArrowPointer arrow = new ArrowPointer();
    private boolean heldByFirstPaddle = true;
    private boolean skillsReady = true;
    public boolean skillUsedAtleastOnce = false;
    public int previousAngle = 0;
    public int arrowsIndex = -1;
    private int[] deadPos;
    public String[] generalsMovement = {"", "", "", ""};

    /**
     * Constuctor class for game controller class
     * @param balls array of balls in the game
     * @param generalA first general
     * @param generalB second general
     * @param generalC third general
     * @param generalD forth general
     */
    public Game(Ball[] balls, General generalA, General generalB, General generalC, General generalD) {
        this.balls = balls;
        this.generals = new General[4];//Initialise the generals in the game
        this.generals[0] = generalA;
        this.generals[1] = generalB;
        this.generals[2] = generalC;
        this.generals[3] = generalD;

        // Initializing and adding AI controllers to the generals
        this.AIs.add(new AIController());
        this.AIs.get(0).setGeneral(generalA);

        this.AIs.add(new AIController());
        this.AIs.get(1).setGeneral(generalB);

        this.AIs.add(new AIController());
        this.AIs.get(2).setGeneral(generalC);

        this.AIs.add(new AIController());
        this.AIs.get(3).setGeneral(generalD);

        this.deadPos = new int[4]; //Array to keep track of the dead
    }

    /**
     * Ticks the countdown timer
     */
    public void countdownTick() {
        if (countDown > 0) countDown--;
        else countingDown = false;
    }

    /**
     *
     * @return whether the game is currently being counted down
     */
    public boolean isCountingDown() {
        return countingDown;
    }

    /**
     * Executes a single tick in the game (runs 60 times a second)
     * All logic controlling game movements are in this method.
     */
    public void tick(){
        timeElapsed++;
        boolean generalHit = false;
        int deadCount = 0;
        executeActions();

        // Loops through all balls in play and checks for all collisions and handles them appropriately
        for (int a = 0; a < balls.length; a++) {
            ball = balls[a];
            boolean ballHit = false;
            if (!ball.getHitLastTick() && ball.getCollisionCounter() <= 0) { // Ensure collisions only happen if ball wasn't hit last tick
                for (int i = 0; i < powerUps.size(); i++) {
                    if (!ballHit && (!powerUps.get(i).isHit())) {
                        if (objectCollision(powerUps.get(i), ballHit, false)) {//When a ball passes through the power ups
                            // checking activation a speed up power up
                            if (powerUps.get(i).getPowerUpName().equals("Speed Up")) {//If the power up is a speed up
                                if (!ball.getSpedUp()) {
                                    powerUps.get(i).setHit(true);
                                    powerUps.get(i).activateEffect(ball, generals); //Activate effect of speed up
                                    SoundManager.playSpeedUp();
                                    ball.setSpedUp(true); //Indicate to the ball that it has been sped up
                                }
                            // checking activation of a paddle size up power up
                            } else if (powerUps.get(i).getPowerUpName().equals("Paddle Size Up")){//If the power up isa paddle size up
                                boolean activateSizeIncrease = false;
                                for (int j = 0; j < generals.length;j++){
                                    if (!generals[j].isDead() && !generals[j].paddle.getSizeIncreased()) activateSizeIncrease = true;//Increase the size of all the paddles
                                }
                                if (activateSizeIncrease){//Activate size increase
                                    powerUps.get(i).setHit(true);
                                    powerUps.get(i).activateEffect(ball, generals);
                                }
                            }
                        }
                    }
                }
                outerLoop://Outer loop is identified to break out of completely at a later stage
                for (int i = 0; i < generals.length; i++) {
                    if (!ballHit && (!generals[i].isDead())){
                        if (ball.getWillBeHeld() && i == 0){
                            //Collision with general 0's first paddle
                            ballHit = objectCollision(generals[i].paddle,ballHit,false);
                            if (ballHit){
                                holdBall(true);//calls holdBall function telling it that first paddle is holding ball
                                arrowsIndex += 1;
                                previousAngle = 0;
                            }
                        } else ballHit = objectCollision(generals[i].paddle, ballHit);//Otherwise regular collision
                    }
                    if (!ballHit && (!generals[i].isDead()) && Main.numberOfPaddles == 2){//Perform the same check for secondary paddle
                        if (ball.getWillBeHeld() && i == 0){
                            //Collision with general 0's second paddle
                            ballHit = objectCollision(generals[i].paddleFollower,ballHit,false);
                            if (ballHit){
                                holdBall(false);//calls holdBall function telling it that second paddle is holding ball
                                arrowsIndex += 1;
                                previousAngle = 0;
                            }
                        } else ballHit = objectCollision(generals[i].paddleFollower, ballHit);
                    }
                    // checking for collisions with the generals
                    if (!ballHit && (!generals[i].isDead())) {
                        ballHit = objectCollision(generals[i], ballHit);
                        if (ballHit){
                            generals[i].killGeneral();//Upon hit kill the general
                            generalHit = true;
                            SoundManager.playGeneralDeath();

                            if (isSinglePlayer() && i != 0 && skillsReady & skillUsedAtleastOnce) resetSkills();
                            break outerLoop;
                        }
                    }
                    // checking for collisions with the walls
                    if (!ballHit) {
                        for (int j = 0; j < generals[i].wall.length; j++) {
                            for (int k = 0; k < generals[i].wall[j].length; k++) {
                                if (!generals[i].wall[j][k].isDestroyed()){//Only perform collision checks if wall isn't destroyed
                                    if (!ballHit) ballHit = objectCollision(generals[i].wall[j][k], ballHit);

                                    if (ballHit) {
                                        if (!ball.isExplosive()) {
                                            generals[i].wall[j][k].destroyBrick();
                                        } else{
                                            explosion(i,j,ball); //Cause the explosion.
                                            checkAllExploded(balls); //Check if all the balls have exploded
                                        }
                                        break outerLoop; //End outer loop
                                    }
                                }
                            }
                        }
                    }

                }
            }

            if (ballHit && !generalHit) SoundManager.playCollision(); //Collision sound

            // if no collisions occurred then move the ball as usual
            if (!ballHit) {
                ball.processBall();
                ball.setHitLastTick(false);
                ball.decrementCounter();
            }
            ball.checkReduceSpeed();
        }

        // counts the number of generals that have been killed
        for (int i = 0; i < generals.length; i++) {
            if (generals[i].isDead()) deadCount++;
        }

        // if all but one general is alive then the winner is set
        if (deadCount + 1 == generals.length) {
            for (int i = 0; i < generals.length; i++) {
                if (!generals[i].isDead()) {
                    generals[i].setWon();
                    isFinished = true;
                }
            }
        }

        // AI general movement
        for (int i = 0; i < AIs.size();i++){
            if (!generals[i].isDead()) {
                AIs.get(i).movePaddle(balls); //Move AI's paddle
            } else {
                for (int j = 0; j < markers.size();j++){
                    if (markers.get(j).getPos() == i){
                        AIs.get(i).moveMarker(markers.get(j), powerUps);//Move each of the markers if AI is dead
                    }
                }
            }
        }

        if (deadCount > 0 && markers.size() < deadCount ){ //Keep track of the dead generals and their markers
            for (int i = 0; i < deadPos.length;i++) {
                if (deadPos[i] != 1 && generals[i].isDead()) {
                    this.markers.add(new Marker());
                    this.markers.get(this.markers.size() - 1).setPos(i);
                    deadPos[i] = 1;
                    break;
                }
            }
        }

        if (timeElapsed > 3600) endGame();

        // generates power ups every 20 seconds
        if (timeElapsed % 600 == 0) generatePowerUp();

        for (int i = 0; i < generals.length;i++){
            generals[i].paddle.checkDecreaseWidth();//check reduction of width of primary paddle
            if (Main.numberOfPaddles == 2) generals[i].paddleFollower.checkDecreaseWidth();//Check reduction of width of secondary paddle
        }

        for (int i = 0; i < generals.length;i++){
            if (deadPos[i] == 1 && generals[i].isDead()){
                for (int j = 0; j < markers.size(); j++){
                    if (markers.get(j).getPos() == i){ //Identify the correct marker that corresponds to the general
                        markers.get(j).decrementReadyCounter();//Decrement counter of the active markers
                    }
                }
            }
        }

        if (heldByFirstPaddle) calculateArrowPivots(true); //Calculate direction of arrow when it is held
        else calculateArrowPivots(false);

        boolean ballStillHeld = true;
        if (heldByFirstPaddle) {//If the ball is held to check if it is still held this tick
            if (!generals[0].paddle.checkStillHoldingBall(balls, arrow)) ballStillHeld = false;
        } else {
            if (!generals[0].paddleFollower.checkStillHoldingBall(balls, arrow)) ballStillHeld = false;
        }
        if (!ballStillHeld) skillsReady = true; //Once the power has been shot the skills are ready to be reset when an AI dies

    }

    /**
     * Marks the game as finished and calculates the winner
     */
    public void endGame() {
        isFinished = true;
        hiScore = Math.max(Math.max(generals[0].wallCount(), generals[1].wallCount()), Math.max(generals[2].wallCount(), generals[3].wallCount()));
        for (int i = 0; i < generals.length; i++) {
            if (hiScore == generals[i].wallCount()) generals[i].setWon(); //Set the person with the highest number of bricks remaining as the winner
        }
    }

    /**
     * Checks for collision between an object and a ball
     * @param object an object that implements IObject
     * @param ballHit whether the ball has been hit this tick
     * @return whether the ball has been hit this tick
     */
    private boolean objectCollision (IObject object, boolean ballHit) {
        for (int x = object.calcXPos(); x <= (object.calcXPos() + object.getWidth()); x++) { //For each pixel in x axis of the object
            for (int y = object.calcYPos(); y <= (object.calcYPos() + object.getHeight()); y++) {//For every pixel in the y axis of the object
                //If if the pixels collide
                if (x == object.calcXPos() || y == object.calcYPos() || x == (object.calcXPos() + object.getWidth()) || y == (object.calcYPos() + object.getHeight())) {
                    if (inBallPath(x, y)) {//Use the ball in path method to find if the object is in the path of the ball
                        if (x == object.calcXPos()) { //Handle each of the sides of the object individually to reflect ball accordingly
                            if (ball.getXVelocity() > 0) {
                                ball.setXVelocity(-ball.getXVelocity());//Invert x velocity
                                ball.setHitLastTick(true);
                                return true;
                            }
                        } else if (y == object.calcYPos()) {
                            if (ball.getYVelocity() > 0) {
                                ball.setYVelocity(-ball.getYVelocity());//Invert y velocity
                                ball.setHitLastTick(true);
                                return true;
                            }
                        } else if (x == (object.calcXPos() + object.getWidth())) {
                            if (ball.getXVelocity() < 0) {
                                ball.setXVelocity(-ball.getXVelocity());//Invert X velocity
                                ball.setHitLastTick(true);
                                return true;
                            }
                        } else if (y == (object.calcYPos() + object.getHeight())) {
                            if (ball.getYVelocity() < 0) {
                                ball.setYVelocity(-ball.getYVelocity()); //Invert y velocity
                                ball.setHitLastTick(true);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return ballHit;
    }

    /**
     * Checks for collision between an object and a ball
     * @param object an object that implements IObject
     * @param ballHit whether the ball has been hit this tick
     * @param bounce whether the ball will bounce
     * @return whether the ball has been hit this tick
     */
    private boolean objectCollision (IObject object, boolean ballHit, boolean bounce) {
        if (!bounce) {
            for (int x = object.calcXPos(); x < (object.calcXPos() + object.getWidth()); x++) {
                for (int y = object.calcYPos(); y < (object.calcYPos() + object.getHeight()); y++) {
                    if (x == object.calcXPos() || y == object.calcYPos() || x == (object.calcXPos() + object.getWidth()) || y == (object.calcYPos() + object.getHeight())) {
                        if (inBallPath(x, y)) {
                            if (x == object.calcXPos()) {
                                return true;
                            } else if (y == object.calcYPos()) {
                                return true;
                            } else if (x == (object.calcXPos() + object.getWidth())) {
                                return true;
                            } else if (y == (object.calcYPos() + object.getHeight())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return ballHit;
    }

    /**
     * Bresenham's Line Drawing Algorithm
     */
    private boolean inBallPath (int xCord, int yCord) {
        int x1 = ball.getXPos();
        int x2 = ball.getXPos() + ball.getWidth();
        int y1 = ball.getYPos();
        int y2 = ball.getYPos() + ball.getHeight();
        int x, y, dx, dy, p, end;

        dx = Math.abs(x1 - x2);
        dy = Math.abs(y1 - y2);
        p = 2 * dy - dx;
        if(x1 > x2) {
            x = x2;
            y = y2;
            end = x1;
        }
        else {
            x = x1;
            y = y1;
            end = x2;
        }
        if ((x == xCord) && (y == yCord)) return true; //If coordinates match then return true
        while(x < end) {
            x = x + 1;
            if(p < 0) {
                p = p + 2 * dy;
            }
            else {
                y = y + 1;
                p = p + 2 * (dy - dx);
            }
            if ((x == xCord) && (y == yCord)) return true; //If coordinates match then return true
        }
        return false;
    }

    /**
     *
     * @return whether the game has finished
     */
    public boolean isFinished(){
        return isFinished;
    }

    /**
     *
     * @return a nice string showing time remaining in the game
     */
    public String getTimeRemaining() {
        int time = (120 - (timeElapsed / 30));
        if (time == 120) return "2:00";
        if (time > 59) return "1:" + String.format("%02d",time-60);//String formatting to make time look like a countdown
        else return "0:" + String.format("%02d",time);
    }

    /**
     *
     * @return a nice string showing time remaining till game starts
     */
    public String getCountdownRemaining() {
        int time = (countDown / 30);
        return "0:" + String.format("%02d",time);//Count down before the game starts
    }

    /**
     *
     * @return the countDown as an integer
     */
    public int getCountDown(){
        return countDown;
    }
    /**
     * Generates a power up at a random location
     */
    public void generatePowerUp(){
        generatePowerUp((int)(Math.random() * 424 + 350),(int)(Math.random() * 608 + 50));
    }

    /**
     * Generates a power up at a specified location
     * @param xPos x position of the power up
     * @param yPos y position of the power up
     */
    public void generatePowerUp(int xPos, int yPos){
        int option = (int)(Math.random() * 2);//Choose between paddle size up and speed up
        if (option == 0) this.powerUps.add(new PaddleSizeUp());
        else if (option == 1) this.powerUps.add(new SpeedUp());
        powerUps.get(powerUps.size()-1).setPos(xPos, yPos);
    }

    /**
     * Executes movement actions depending on the flags settings
     * Flags are set by the keyboard handler
     */
    public void executeActions(){
        for (int i = 0; i < generalsMovement.length; i++) {//For all generals
            switch (generalsMovement[i]) {
                case "left":
                    if(!generals[i].isDead()) {//If the general is moving left and is not dead
                        generals[i].paddle.moveLeft();//Move paddle left
                        if (Main.numberOfPaddles > 1.50) generals[i].paddleFollower.moveRight();//Move paddle follower left
                    }else{
                        for (int j = 0; j < markers.size();j++){//If dead identify the marker and move it left
                            if (markers.get(j).getPos() == i){
                                markers.get(j).moveLeft();
                            }
                        }
                    }
                    break;
                case "right"://Same logic as moving left but instead moves right
                    if(!generals[i].isDead()) {
                        generals[i].paddle.moveRight();
                        if (Main.numberOfPaddles > 1.50) generals[i].paddleFollower.moveLeft();
                    }else{
                        for (int j = 0; j < markers.size();j++){
                            if (markers.get(j).getPos() == i){
                                markers.get(j).moveRight();
                            }
                        }
                    }
                    break;
                case "up":
                    if(generals[i].isDead()) {//Move marker up if general is dead
                        for (int j = 0; j < markers.size();j++){
                            if (markers.get(j).getPos() == i){
                                markers.get(j).moveUp();
                            }
                        }
                    }
                    break;
                case "down":
                    if(generals[i].isDead()) {//Move marker down if general is dead
                        for (int j = 0; j < markers.size();j++){
                            if (markers.get(j).getPos() == i){
                                markers.get(j).moveDown();
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Executes an ball explosion
     * @param i general number
     * @param j wall row number
     * @param ball the ball that is causing the explosion
     */
    private void explosion(int i, int j, Ball ball){
        for (int k = 0; k < generals[i].wall[j].length; k++){
            if (!generals[i].wall[j][k].isDestroyed()) generals[i].wall[j][k].destroyBrick();//Destroy the row of bricks
        }
        ball.setUnexplosive();//Make the ball unexplosive
        SoundManager.playExplosion();
    }

    /**
     * Checks if all the balls have exploded
     * @param balls array of balls in the game
     */
    private void checkAllExploded(Ball[] balls){
        boolean allExploded = true;
        for (int i = 0; i < balls.length; i ++){
            if (balls[i].isExplosive()) allExploded = false;
        }
        if (allExploded) skillsReady = true;//If all balls have exploded then indicate that skills are ready to be reset
    }

    /**
     *
     * @param firstPaddle whether this is the first paddle
     */
    private void holdBall(boolean firstPaddle){
        ball.setBallHeld();
        //When the ball is held it needs to be relocated to the bottom of the paddle in the centre
        if (firstPaddle) {//Relocate it to the centre of the first paddle
            generals[0].paddle.setHoldingBall(true);
            ball.setXPos(generals[0].paddle.calcXPos() + generals[0].paddle.getWidth() / 2 - ball.getWidth() / 2);
            ball.setYPos(generals[0].paddle.calcYPos() + generals[0].paddle.getHeight());
            heldByFirstPaddle = true;
        }else{//Relocate it to the centre of the second paddle
            generals[0].paddleFollower.setHoldingBall(true);
            ball.setXPos(generals[0].paddleFollower.calcXPos() + generals[0].paddleFollower.getWidth() / 2 - ball.getWidth() / 2);
            ball.setYPos(generals[0].paddleFollower.calcYPos() + generals[0].paddleFollower.getHeight());
            heldByFirstPaddle = false;
        }
        for (int i = 0; i < balls.length; i++){
            balls[i].setWillBeHeld(false); //Indicate that the other balls in play will not be held
        }
    }

    /**
     * Decreases tbe arrow direction angle
     */
    public void decreaseBallHoldAngle(){
        arrow.decreaseAngle();
    }

    /**
     * Increases tbe arrow direction angle
     */
    public void increaseBallHoldAngle(){
        arrow.increaseAngle();
    }

    /**
     *
     * @return the ball holding angle
     */
    public int getBallHoldAngle(){
        return arrow.getAngle();
    }

    /**
     * Calculates the arrow pivot positions
     * @param firstPaddle whether this is the first paddle or not
     */
    public void calculateArrowPivots(boolean firstPaddle){
        if (firstPaddle) arrow.calcPivots(generals[0].paddle, ball);
        else arrow.calcPivots(generals[0].paddleFollower, ball);
    }

    /**
     * Calculates the arrow positions
     * @param firstPaddle whether this is the first paddle or not
     */
    public void calculateArrowPosition(boolean firstPaddle){
        if (firstPaddle) arrow.calcPos(generals[0].paddle, ball);
        else arrow.calcPos(generals[0].paddleFollower, ball);
    }

    /**
     *
     * @return the x position of the arrow target
     */
    public int getArrowXPosition(){
        return arrow.getxPos();
    }

    /**
     *
     * @return the y position of the arrow target
     */
    public int getArrowYPosition(){
        return arrow.getyPos();
    }

    /**
     *
     * @return x pivot position of the arrow
     */
    public int getArrowXPivot(){
        return arrow.getxPivot();
    }

    /**
     *
     * @return y pivot position of the arrow
     */
    public int getArrowYPivot(){
        return arrow.getyPivot();
    }

    /**
     *
     * @return whether the current game is single player
     */
    public boolean isSinglePlayer(){
        return (Main.gameMode == 2 || Main.gameMode == 4 || Main.gameMode == 6);
    }

    /**
     * Resets all the skills to default
     */
    private void resetSkills(){
        generals[0].resetAllSkills();
    }

}
