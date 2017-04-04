package warlords2600;

import EtruarutaGUI.Main;

/**
 * This class models a General in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class General implements IObject {

    public Paddle paddle;
    public Brick[][] wall;
    private int height = 25, width = 25, r, pos = 0;
    private double theta;
    private boolean dead = false, won = false;

    /**
     * Constructor for the general class
     * @param r radius from nearest corner
     * @param theta angle from centre line in radians
     * @param paddle the paddle associated with this general
     * @param wall the wall associated with this general
     * @param pos the position of the general in the game field
     */
    public General(int r, double theta, Paddle paddle, Brick[][] wall, int pos) {
        this.r = r;
        this.theta = theta;
        this.paddle = paddle;
        this.wall = wall;
        this.pos = pos;
    }

    /**
     *
     * @param width sets the width of the general
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     *
     * @param height sets the height of the general
     */
    public void setHeight(int height){
        this.height = height;
    }

    /**
     *
     * @return the current width of the general
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return the current
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @return whether the general is dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Kills the general when the function is called
     */
    public void killGeneral() {
        dead = true;
    }

    /**
     *
     * @return whether the general has won
     */
    public boolean hasWon() {
        return won;
    }

    /**
     * Marks the general as the winner
     */
    public void setWon() {
        this.won = true;
    }

    /**
     *
     * @return the x position of the general in the cartesian plane
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
     * @return the y position of the general in the cartesian plane
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
     * @return the angle from the centre line in radians
     */
    public double getTheta () {
        return theta;
    }

    /**
     *
     * @return the distance from the nearest corner
     */
    public int getR () {
        return r;
    }
}
