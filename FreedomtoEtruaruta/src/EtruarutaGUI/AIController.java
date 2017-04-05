package EtruarutaGUI;


import warlords2600.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by adilb on 31/03/2017.
 */
public class AIController {
    private General general;
    private int movementCounter = 15;
    private boolean movingUp = false, movingRight = false, movingDown = false, movingLeft = false;

    public void AIController(){

    }

    public void setGeneral(General general){
        this.general = general;
    }

    public void movePaddle(Ball ball){
        if (Main.gameMode == 99 || (Main.gameMode == 0 && general.getPos() != 2 && general.getPos() != 0) || (Main.gameMode == 2 && general.getPos() != 0)  || (Main.gameMode == 4  && general.getPos() != 0) || (Main.gameMode == 6  && general.getPos() != 0)) {
            double distanceBefore = calculateDistance(ball);
            if (distanceBefore > 75) {//Stops paddle moving when close to ball
                //System.out.println(distanceBefore);
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

    private double calculateDistance(Ball ball){
        int ballXPos = ball.getXPos();
        int ballYPos = ball.getYPos();
        int ballXVelocity = ball.getXVelocity();
        int ballYVelocity = ball.getYVelocity();
        int yDistanceBefore = Math.abs(general.paddle.calcYPos() - (ballYPos+ballYVelocity*5));//Guess where ball will be by looking at current location and velocity
        int XDistanceBefore = Math.abs(general.paddle.calcXPos() - (ballXPos+ballXVelocity*5));
        return Math.sqrt(Math.pow(XDistanceBefore,2)+Math.pow(yDistanceBefore,2));
    }


    public void moveMarker(Marker markerIn, ArrayList<PowerUp> powerUps){
        if (Main.gameMode == 99 || (Main.gameMode == 0 && general.getPos() != 2 && general.getPos() != 0) || (Main.gameMode == 2 && general.getPos() != 0)  || (Main.gameMode == 4  && general.getPos() != 0)|| (Main.gameMode == 6  && general.getPos() != 0)) {
            if (movementCounter <= 0) {
                movementCounter = 15;
                resetMovement();
                int movement = (int) (Math.random() * 4);
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
                movementCounter--;
                if (movingUp) markerIn.moveUp();
                else if (movingRight) markerIn.moveRight();
                else if (movingDown) markerIn.moveDown();
                else if (movingLeft) markerIn.moveLeft();
            }
            checkDeployPowerUp(markerIn, powerUps);
        }
    }

    private void resetMovement(){
        movingUp = false;
        movingRight = false;
        movingDown = false;
        movingLeft = false;
    }

    private void checkDeployPowerUp(Marker markerIn, ArrayList<PowerUp> powerUps){
        if (markerIn.getReady()){
            powerUps.add(new SpeedUp());
            powerUps.get(powerUps.size()-1).setPos(markerIn.calcXPos(), markerIn.calcYPos());
            markerIn.resetReadyCounter();
        }
    }
}
