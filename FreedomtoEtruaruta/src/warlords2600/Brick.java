package warlords2600;

import EtruarutaGUI.Main;

/**
 * This class models a brick in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Brick implements IObject{

    private int height = 25, width = 25, r, pos = 0;
    private double theta;
    private boolean destroyed = false;

    /**
     *
     * @param r the radius of the brick from the nearest corner
     * @param theta the angle from the centre line in radians
     * @param pos the player position this wall is associated with
     */
    public Brick(int r, double theta, int pos) {
        this.r = r;
        this.theta = theta;
        this.pos = pos;
    }

    /**
     *
     * @param width the width of the brick in pixels
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     *
     * @param height the height of the brick in pixels
     */
    public void setHeight(int height){
        this.height = height;
    }

    /**
     *
     * @return the width of the paddle
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return the height of the paddle
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @return whether the brick has been destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Destroys the brick when called
     */
    public void destroyBrick() {
        destroyed = true;
    }

    /**
     *
     * @return the current x position of the brick in cartesian coordinates
     */
    public int calcXPos() {
        int xPos = (int)(r * Math.cos(theta));
        switch (pos) {
            case 0: return xPos;
            case 1: return (Main.WIDTH - xPos);
            case 2: return (Main.WIDTH - xPos);
            case 3: return xPos;
            default: return xPos;
        }
    }

    /**
     *
     * @return the current y position of the paddle in cartesian coordinates.
     */
    public int calcYPos() {
        int yPos = (int)(r * Math.sin(theta));
        switch (pos) {
            case 0: return yPos;
            case 1: return yPos;
            case 2: return (Main.HEIGHT - yPos);
            case 3: return (Main.HEIGHT - yPos);
            default: return yPos;
        }
    }

    /**
     *
     * @return the current theta value in radians
     */
    public double getTheta () {
        return theta;
    }

    /**
     *
     * @return the current radius of the paddle from the nearest corner
     */
    public int getR () {
        return r;
    }

}
