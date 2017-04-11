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
    public SpeedUp speedUp;
    public ArrayList<AIController> AIs = new ArrayList<AIController>();
    private int ballHoldAngle = 0;
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
        this.generals = new General[4];
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

        this.deadPos = new int[4];
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
            if (!ball.getHitLastTick() && ball.getCollisionCounter() <= 0) {
                for (int i = 0; i < powerUps.size(); i++) {
                    if (!ballHit && (!powerUps.get(i).isHit())) {
                        if (objectCollision(powerUps.get(i), ballHit, false)) {
                            // checking activation a speed up power up
                            if (powerUps.get(i).getPowerUpName().equals("Speed Up")) {
                                if (!ball.getSpedUp()) {
                                    powerUps.get(i).setHit(true);
                                    powerUps.get(i).activateEffect(ball, generals);
                                    SoundManager.playSpeedUp();
                                    ball.setSpedUp(true);
                                }
                            // checking activation of a paddle size up power up
                            } else if (powerUps.get(i).getPowerUpName().equals("Paddle Size Up")){
                                boolean activateSizeIncrease = false;
                                for (int j = 0; j < generals.length;j++){
                                    if (!generals[j].isDead() && !generals[j].paddle.getSizeIncreased()) activateSizeIncrease = true;
                                }
                                if (activateSizeIncrease){
                                    powerUps.get(i).setHit(true);
                                    powerUps.get(i).activateEffect(ball, generals);
                                }
                            }
                        }
                    }
                }
                outerLoop:
                for (int i = 0; i < generals.length; i++) {
                    if (!ballHit && (!generals[i].isDead())){
                        if (ball.getWillBeHeld() && i == 0){
                            //Collision with general 0's first paddle
                            ballHit = objectCollision(generals[i].paddle,ballHit,false);
                            if (ballHit){
                                holdBall(true);
                                arrowsIndex += 1;
                                previousAngle = 0;
                            }
                        } else ballHit = objectCollision(generals[i].paddle, ballHit);
                    }
                    if (!ballHit && (!generals[i].isDead()) && Main.numberOfPaddles == 2){
                        if (ball.getWillBeHeld() && i == 0){
                            //Collision with general 0's second paddle
                            ballHit = objectCollision(generals[i].paddleFollower,ballHit,false);
                            if (ballHit){
                                holdBall(false);
                                arrowsIndex += 1;
                                previousAngle = 0;
                            }
                        } else ballHit = objectCollision(generals[i].paddleFollower, ballHit);
                    }
                    // checking for collisions with the generals
                    if (!ballHit && (!generals[i].isDead())) {
                        ballHit = objectCollision(generals[i], ballHit);
                        if (ballHit){
                            generals[i].killGeneral();
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
                                if (!generals[i].wall[j][k].isDestroyed()){
                                    if (!ballHit) ballHit = objectCollision(generals[i].wall[j][k], ballHit);

                                    if (ballHit) {
                                        if (!ball.isExplosive()) {
                                            generals[i].wall[j][k].destroyBrick();
                                        } else{
                                            explosion(i,j,ball); //Cause the explosion.
                                            checkAllExploded(balls);
                                        }
                                        break outerLoop;
                                    }
                                }
                            }
                        }
                    }

                }
            }

            if (ballHit && !generalHit) SoundManager.playCollision();

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

        // if AI general is dead, moves the AI ghost marker
        for (int i = 0; i < AIs.size();i++){
            if (!generals[i].isDead()) {
                AIs.get(i).movePaddle(balls);
            } else {
                for (int j = 0; j < markers.size();j++){
                    if (markers.get(j).getPos() == i){
                        AIs.get(i).moveMarker(markers.get(j), powerUps);
                    }
                }
            }
        }

        if (deadCount > 0 && markers.size() < deadCount ){
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
            generals[i].paddle.checkDecreaseWidth();
            if (Main.numberOfPaddles == 2) generals[i].paddleFollower.checkDecreaseWidth();
        }

        for (int i = 0; i < generals.length;i++){
            if (deadPos[i] == 1 && generals[i].isDead()){
                for (int j = 0; j < markers.size(); j++){
                    if (markers.get(j).getPos() == i){
                        markers.get(j).decrementReadyCounter();
                    }
                }
            }
        }

        if (heldByFirstPaddle) calculateArrowPivots(true);
        else calculateArrowPivots(false);

        boolean ballStillHeld = true;
        if (heldByFirstPaddle) {
            if (!generals[0].paddle.checkStillHoldingBall(balls, arrow)) ballStillHeld = false;
        } else {
            if (!generals[0].paddleFollower.checkStillHoldingBall(balls, arrow)) ballStillHeld = false;
        }
        if (!ballStillHeld) skillsReady = true;

    }

    /**
     * Marks the game as finished and calculates the winner
     */
    public void endGame() {
        isFinished = true;
        hiScore = Math.max(Math.max(generals[0].wallCount(), generals[1].wallCount()), Math.max(generals[2].wallCount(), generals[3].wallCount()));
        for (int i = 0; i < generals.length; i++) {
            if (hiScore == generals[i].wallCount()) generals[i].setWon();
        }
    }

    /**
     * Checks for collision between an object and a ball
     * @param object an object that implements IObject
     * @param ballHit whether the ball has been hit this tick
     * @return whether the ball has been hit this tick
     */
    private boolean objectCollision (IObject object, boolean ballHit) {
        for (int x = object.calcXPos(); x <= (object.calcXPos() + object.getWidth()); x++) {
            for (int y = object.calcYPos(); y <= (object.calcYPos() + object.getHeight()); y++) {
                if (x == object.calcXPos() || y == object.calcYPos() || x == (object.calcXPos() + object.getWidth()) || y == (object.calcYPos() + object.getHeight())) {
                    if (inBallPath(x, y)) {
                        if (x == object.calcXPos()) {
                            if (ball.getXVelocity() > 0) {
                                ball.setXVelocity(-ball.getXVelocity());
                                ball.setHitLastTick(true);
                                return true;
                            }
                        } else if (y == object.calcYPos()) {
                            if (ball.getYVelocity() > 0) {
                                ball.setYVelocity(-ball.getYVelocity());
                                ball.setHitLastTick(true);
                                return true;
                            }
                        } else if (x == (object.calcXPos() + object.getWidth())) {
                            if (ball.getXVelocity() < 0) {
                                ball.setXVelocity(-ball.getXVelocity());
                                ball.setHitLastTick(true);
                                return true;
                            }
                        } else if (y == (object.calcYPos() + object.getHeight())) {
                            if (ball.getYVelocity() < 0) {
                                ball.setYVelocity(-ball.getYVelocity());
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
     * @param bounce whether the ball has bounced
     * @return whether the ball has been hit this tick
     */
    private boolean objectCollision (IObject object, boolean ballHit, boolean bounce) {
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
        if ((x == xCord) && (y == yCord)) return true;
        while(x < end) {
            x = x + 1;
            if(p < 0) {
                p = p + 2 * dy;
            }
            else {
                y = y + 1;
                p = p + 2 * (dy - dx);
            }
            if ((x == xCord) && (y == yCord)) return true;
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
        if (time > 59) return "1:" + String.format("%02d",time-60);
        else return "0:" + String.format("%02d",time);
    }

    /**
     *
     * @return a nice string showing time remaining till game starts
     */
    public String getCountdownRemaining() {
        int time = (countDown / 30);
        return "0:" + String.format("%02d",time);
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
        int option = (int)(Math.random() * 2);
        if (option == 0) this.powerUps.add(new PaddleSizeUp());
        else if (option == 1) this.powerUps.add(new SpeedUp());
        powerUps.get(powerUps.size()-1).setPos(xPos, yPos);
    }

    /**
     * Executes movement actions depending on the flags settings
     * Flags are set by the keyboard handler
     */
    public void executeActions(){
        for (int i = 0; i < generalsMovement.length; i++) {
            switch (generalsMovement[i]) {
                case "left":
                    if(!generals[i].isDead()) {
                        generals[i].paddle.moveLeft();
                        if (Main.numberOfPaddles > 1.50) generals[i].paddleFollower.moveRight();
                    }else{
                        for (int j = 0; j < markers.size();j++){
                            if (markers.get(j).getPos() == i){
                                markers.get(j).moveLeft();
                            }
                        }
                    }
                    break;
                case "right":
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
                    if(generals[i].isDead()) {
                        for (int j = 0; j < markers.size();j++){
                            if (markers.get(j).getPos() == i){
                                markers.get(j).moveUp();
                            }
                        }
                    }
                    break;
                case "down":
                    if(generals[i].isDead()) {
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
            if (!generals[i].wall[j][k].isDestroyed()) generals[i].wall[j][k].destroyBrick();
        }
        ball.setUnexplosive();
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
        if (allExploded) skillsReady = true;
    }

    /**
     *
     * @param firstPaddle whether this is the first paddle
     */
    private void holdBall(boolean firstPaddle){
        ball.setBallHeld();
        if (firstPaddle) {
            generals[0].paddle.setHoldingBall(true);
            ball.setXPos(generals[0].paddle.calcXPos() + generals[0].paddle.getWidth() / 2 - ball.getWidth() / 2);
            ball.setYPos(generals[0].paddle.calcYPos() + generals[0].paddle.getHeight());
            heldByFirstPaddle = true;
        }else{
            generals[0].paddleFollower.setHoldingBall(true);
            ball.setXPos(generals[0].paddleFollower.calcXPos() + generals[0].paddleFollower.getWidth() / 2 - ball.getWidth() / 2);
            ball.setYPos(generals[0].paddleFollower.calcYPos() + generals[0].paddleFollower.getHeight());
            heldByFirstPaddle = false;
        }
        for (int i = 0; i < balls.length; i++){
            balls[i].setWillBeHeld(false);
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
