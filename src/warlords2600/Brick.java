package warlords2600;

import warlordstest.IWall;

/**
 * This class models a brick in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Brick implements IWall{

    private int xPos, yPos, height = 5, width = 5;
    private boolean destroyed = false;

    /**
     * Constructor for the Brick class
     * @param xPos Position of the brick on the X plane of the cartesian coordinate system
     * @param yPos Position of the brick on the Y plane of the cartesian coordinate system
     */
    public Brick(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     *
     * @param x setting X position
     */

    public void setXPos(int x) {
        xPos = x;
    }

    /**
     *
     * @param y setting Y position
     */
    public void setYPos(int y) {
        yPos = y;
    }

    /**
     *
     * @return returns current x position of the brick
     */
    public int getXPos() {
        return xPos;
    }

    /**
     *
     * @param width The width of the brick
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     *
     * @param height The height of the brick
     */
    public void setHeight(int height){
        this.height = height;
    }

    /**
     *
     * @return returns current y position of the brick
     */
    public int getYPos() {
        return yPos;
    }

    /**
     *
     * @return width of the brick
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return height of the brick
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @return whether the brick has been destroyed already
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     *  Destroys this brick when it is called
     */
    public void destroyWall() {
        destroyed = true;
    }

}
