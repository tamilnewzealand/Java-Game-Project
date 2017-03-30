package warlords2600;

import warlordstest.IPaddle;

/**
 * This class models a paddle in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Paddle implements IPaddle {
    private int x, y, speed, width = 5, height = 5;

    /**
     * Default constructor initiated at (0, 0)
     */
    public Paddle() {
        this(0,0);
    }

    /**
     * Constructor class for the paddle
     * @param x X position of the paddle in Cartesian coordinates
     * @param y Y position of the paddle in Cartesian coordinates
     */
    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x new X position of the paddle
     */
    public void setXPos(int x){
        this.x = x;
    }

    /**
     *
     * @param y new Y position of the paddle
     */
    public void setYPos(int y){
        this.y = y;
    }

    /**
     *
     * @return current X position of the paddle
     */
    public int getXPos(){
        return x;
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
     * @return current Y position of the paddle
     */
    public int getYPos(){
        return y;
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
     * @return returns teh current speed of the paddle
     */
    public int getSpeed(){
        return speed;
    }

    /**
     *
     * @return height of the paddle
     */
    public int getHeight(){
        return height;
    }

    /**
     * Moves the paddle to the left by speed
     * Called when the left key button is pressed
     */
    public void goLeft() {
        x -= speed;
    }

    /**
     * Moves the paddle to the right by speed
     * Called when the right key button is pressed
     */
    public void goRight() {
        x += speed;
    }

    /**
     * Moves the paddle to the down by speed
     * Called when the down key button is pressed
     */
    public void goDown() {
        y += speed;
    }

    /**
     * Moves the paddle to the up by speed
     * Called when the up key button is pressed
     */
    public void goUp() {
        y -= speed;
    }

}