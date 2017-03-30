package warlords2600;

import warlordstest.IWarlord;

/**
 * This class models a General in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class General implements IWarlord {

    public Paddle paddle = new Paddle(0,0);
    public Brick[][] wall = new Brick[3][7];
    private int xPos, yPos, height = 5, width = 5;
    private boolean dead = false, won = false;

    /**
     * Constructor for the General class
     * @param xPos Position on the Cartesian Coordinate X plane
     * @param yPos Position on the Cartesian Coordinate Y plane
     * @param paddle Paddle that this general is associated with
     */
    public General(int xPos, int yPos, Paddle paddle) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.paddle = paddle;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                wall[i][j] = new Brick((2 + 5*i), (2 + 5*j));
            }
        }
    }

    /**
     *
     * @param x sets the new X position
     */
    public void setXPos(int x) {
        xPos = x;
    }

    /**
     *
     * @param y sets the new Y position
     */
    public void setYPos(int y) {
        yPos = y;
    }

    /**
     *
     * @return returns the current X position
     */
    public int getXPos() {
        return xPos;
    }

    /**
     *
     * @return returns the current Y position
     */
    public int getYPos() {
        return yPos;
    }

    /**
     *
     * @return width of the general
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return height of the general
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @return whether the general has been killed already
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Kills the general when called
     */
    public void killGeneral() {
        dead = true;
    }

    /**
     *
     * @return whether the general has won the game
     */
    public boolean hasWon() {
        return won;
    }

    /**
     * Sets this general to be the winner of the game
     */

    public void setWon() {
        this.won = true;
    }
}
