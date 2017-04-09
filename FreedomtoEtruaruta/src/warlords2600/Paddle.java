package warlords2600;

import EtruarutaGUI.Main;

/**
 * This class models a paddle in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Paddle implements IObject {
    private int x, y, r = 275, speed = 5, width = 50, height = 10, pos = 0;
    double theta, polarSpeed = 0.03;
    private boolean sizeIncreased = false;
    private int previousWidth;
    private int widthUpCounter = 0;
    private boolean holdingBall = false;
    private int ballHoldCount = 0;

    /**
     * Constructor for paddle class
     * @param theta the angle from the centre line in radians
     * @param pos the position of the paddle in the game board
     */
    public Paddle(double theta, int pos) {
        this.theta = theta;
        this.pos = pos;
    }

    /**
     *
     * @return whether the size of the paddle has been increased
     */
    public boolean getSizeIncreased(){
        return sizeIncreased;
    }

    /**
     *
     * @param speed Sets the new speed of the paddle
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }

    /**
     *
     * @param width sets the new width of the paddle
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     *
     * @param height sets the new height of the paddle
     */
    public void setHeight(int height){
        this.height = height;
    }

    /**
     *
     * @return returns the current speed of the paddle
     */
    public int getSpeed(){
        return speed;
    }

    /**
     *
     * @return width of the paddle
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return height of the paddle
     */
    public int getHeight(){
        return height;
    }

    /**
     * Moves the paddle one speed unit to the right direction
     */
    public void moveRight() {
        if (!holdingBall) {
            if (pos == 0 || pos == 3) {
                if (theta > polarSpeed) theta -= polarSpeed;
            } else {
                if (theta < Math.PI / 2 - polarSpeed) theta += polarSpeed;
            }
        }
    }

    /**
     * Moves the paddle one speed unit to the left direction
     */
    public void moveLeft() {
        if (!holdingBall) {
            if (pos == 0 || pos == 3) {
                if (theta < Math.PI / 2 - polarSpeed) theta += polarSpeed;
            } else {
                if (theta > polarSpeed) theta -= polarSpeed;
            }
        }
    }

    /**
     *
     * @param theta the angle from the centre line in radians
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     *
     * @return the calculated x position in the cartesian plane
     */
    public int calcXPos() {
        x = (int)(r * Math.cos(theta));
        switch (pos) {
            case 0: return x;
            case 1: return (Main.WIDTH - x - 25);
            case 2: return (Main.WIDTH - x - 25);
            case 3: return x;
            default: return x;
        }
    }

    /**
     *
     * @return the calculated y position in the cartesian plane
     */
    public int calcYPos() {
        y = (int)(r * Math.sin(theta));
        switch (pos) {
            case 0: return y;
            case 1: return y;
            case 2: return (Main.HEIGHT - y - 25);
            case 3: return (Main.HEIGHT - y - 25);
            default: return y;
        }
    }

    /**
     *
     * @return returns the angle from the centre line of the paddle in radians
     */
    public double getTheta () {
        return theta;
    }

    /**
     *
     * @return the radius from the nearest corner
     */
    public int getR () {
        return r;
    }

    /**
     * Checks whether the width of the paddle has increased
     *
     * @param width the width of the paddle
     */
    public void checkIncreaseWidth(int width){
        if (!sizeIncreased){
            this.previousWidth = this.width;
            this.width = width;
            this.widthUpCounter = 300;
            sizeIncreased = true;
        }
    }

    /**
     * Checks whether the width of the paddle has decreased
     */
    public void checkDecreaseWidth(){
        if (sizeIncreased){
            if (widthUpCounter > 0){
                if (!holdingBall) {
                    widthUpCounter--;
                }
            } else{
                sizeIncreased = false;
                this.width = previousWidth;
            }
        }
    }

    public void setHoldingBall(boolean holdingBall){
        this.holdingBall = holdingBall;
        if (holdingBall){
            ballHoldCount = 120;
        }
    }

    public boolean isHoldingBall(){
        return holdingBall;
    }

    public boolean checkStillHoldingBall(Ball[] balls, ArrowPointer arrow){
        boolean stillHolding = true;
        if (ballHoldCount > 0){
            ballHoldCount--;
        }else{
            holdingBall = false;
            releaseBall(balls, arrow);
            stillHolding = false;
        }
        return stillHolding;
    }

    private void releaseBall(Ball[] balls, ArrowPointer arrow){
        for (int i = 0; i < balls.length; i++){
            if (balls[i].isHeld()){
                balls[i].unHeld();
                balls[i].launch(arrow);
            }
        }
    }
}
