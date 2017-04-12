package etruaruta.controllers;

import etruaruta.Main;
import etruaruta.models.*;
import java.util.ArrayList;

/**
 * This is the controller class for each AI in the game
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class AIController {
    private General general;
    private int movementCounter = 15;
    private boolean movingUp = false, movingRight = false, movingDown = false, movingLeft = false;

    /**
     *
     * @param general the general this AI controller controls
     */
    public void setGeneral(General general){
        this.general = general;
    }

    /**
     * Moves the paddle appropriately to defend the general
     * @param balls the balls that are in play
     */
    public void movePaddle(Ball[] balls){
        int lowestDistanceIndex = findClosestBall(balls);
        Ball ball = balls[lowestDistanceIndex]; //Get the closest ball to the paddle
        //First we check if the AI paddle is active in this game mode.
        if (Main.gameMode == 99 || (Main.gameMode == 0 && general.getPos() != 2 && general.getPos() != 0) || (Main.gameMode == 2 && general.getPos() != 0)  || (Main.gameMode == 4  && general.getPos() != 0) || (Main.gameMode == 6  && general.getPos() != 0)) {
            double distanceBefore = calculateDistance(ball);
            if (distanceBefore > 75 + general.paddle.getWidth()/2) {//Stops paddle moving when close to ball
                general.paddle.moveLeft();
                if(Main.numberOfPaddles > 1.50) general.paddleFollower.moveRight();
                double distanceAfter = calculateDistance(ball);
                if (distanceAfter < distanceBefore) {
                    //Do Nothing our move made it get closer to ball in y axis. No further action required.
                } else {
                    general.paddle.moveRight();
                    general.paddle.moveRight();//Move right twice to compensate for wrong left turn initially
                    if(Main.numberOfPaddles > 1.50) {
                        general.paddleFollower.moveLeft();
                        general.paddleFollower.moveLeft();
                    }
                }
            }
        }
    }

    /**
     *
     * @param ball ball to calculate distance from
     * @return distance to ball
     */
    private double calculateDistance(Ball ball){
        int ballXPos = ball.getXPos();
        int ballYPos = ball.getYPos();
        int ballXVelocity = ball.getXVelocity();
        int ballYVelocity = ball.getYVelocity();
        int yDistanceBefore = Math.abs(general.paddle.calcYPos() - (ballYPos+ballYVelocity*5));//Guess where ball will be by looking at current location and velocity
        int XDistanceBefore = Math.abs(general.paddle.calcXPos() - (ballXPos+ballXVelocity*5));
        return Math.sqrt(Math.pow(XDistanceBefore,2)+Math.pow(yDistanceBefore,2));//Use pythagoras to calculate actual distance taking into account both x and y
    }


    /**
     * This method moves the ghost marker
     * @param markerIn the marker this AIController controls
     * @param powerUps the power ups that can be used
     */
    public void moveMarker(Marker markerIn, ArrayList<PowerUp> powerUps){
        //First we check if the AI marker is active in this game mode.
        if (Main.gameMode == 99 || (Main.gameMode == 0 && general.getPos() != 2 && general.getPos() != 0) || (Main.gameMode == 2 && general.getPos() != 0)  || (Main.gameMode == 4  && general.getPos() != 0)|| (Main.gameMode == 6  && general.getPos() != 0)) {
            if (movementCounter <= 0) {
                movementCounter = 15;//Movement continues for 15 ticks
                resetMovement();
                int movement = (int) (Math.random() * 4);//Decide where the marker will be going
                if (movement == 0) {
                    markerIn.moveUp();
                    movingUp = true;
                } else if (movement == 1) {
                    markerIn.moveRight();
                    movingRight = true;
                } else if (movement == 2) {
                    markerIn.moveDown();
                    movingDown = true;
                } else if (movement == 3) {
                    markerIn.moveLeft();
                    movingLeft = true;
                }
            } else {
                movementCounter--; //Decrease movement counter every tick so it changes direction every 15th tick
                if (movingUp) markerIn.moveUp();
                else if (movingRight) markerIn.moveRight();
                else if (movingDown) markerIn.moveDown();
                else if (movingLeft) markerIn.moveLeft();
            }
            checkDeployPowerUp(markerIn, powerUps);//Call hte function to deploy power ups and see if it is ready to deploy
        }
    }

    /**
     * Resets all movement flags
     */
    private void resetMovement(){
        movingUp = false;
        movingRight = false;
        movingDown = false;
        movingLeft = false;
    }

    /**
     * Checks if a power up can be deployed
     * @param markerIn the marker this AIController controls
     * @param powerUps the power ups that can be used
     */
    private void checkDeployPowerUp(Marker markerIn, ArrayList<PowerUp> powerUps){
        if (markerIn.getReady()){//Deploy the power up if it is ready to deploy
            powerUps.add(new SpeedUp());
            powerUps.get(powerUps.size()-1).setPos(markerIn.calcXPos(), markerIn.calcYPos());
            markerIn.resetReadyCounter();
        }
    }

    /**
     *
     * @param balls array of all the balls in play
     * @return the index of closest ball in play
     */
    private int findClosestBall(Ball[] balls){
        int lowestIndex = 0;
        int minimumDistance = 9999;
        for (int i = 0; i < balls.length; i++){
            if (calculateDistance(balls[i]) < minimumDistance){
                lowestIndex = i;
                minimumDistance =(int) calculateDistance(balls[i]);
            }
        }
        return lowestIndex;
    }
}
