package warlords2600;

import EtruarutaGUI.Main;
import warlordstest.IBall;

/**
 * This class models a ball in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Ball implements IBall{
    private int x, y, xSpeed = 0, ySpeed = 0, width = 25, height = 25;

    /**
     * Constructor for the ball class
     * @param x X position of ball in cartesian plane
     * @param y Y position of ball in cartesian plane
     */
    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x Current X position of ball
     */
    public void setXPos(int x) {
        this.x = x;
    }

    /**
     *
     * @param y Current Y position of ball
     */
    public void setYPos(int y) {
        this.y = y;
    }

    /**
     *
     * @return Current X position of the ball
     */
    public int getXPos() {
        return this.x;
    }

    /**
     *
     * @return Current Y position of the ball
     */
    public int getYPos() {
        return this.y;
    }

    /**
     *
     * @param dX Velocity of ball in X direction per game tick
     */
    public void setXVelocity(int dX) {
        xSpeed = dX;
    }

    /**
     *
     * @param dY Velocity of ball in Y direction per game tick
     */
    public void setYVelocity(int dY) {
        ySpeed = dY;
    }

    /**
     *
     * @return Current velocity of ball in X direction per game tick
     */
    public int getXVelocity() {
        return xSpeed;
    }

    /**
     *
     * @return Current velocity of ball in Y direction per game tick
     */
    public int getYVelocity() {
        return ySpeed;
    }

    /**
     *
     * @return The width of the ball
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return The height of the ball
     */
    public int getHeight() {
        return height;
    }

    /**
     * Process the movement of the ball in one game tick.
     * The ball moves by dx and dy in the x and y directions
     * respectively. Collisions with boundary is handled here.
     * This function is only to be called when there are no
     * other collisions with the ball.
     */
    public void processBall() {
        x += xSpeed;
        y += ySpeed;

        if (x <= (width / 2)) {
            x = -x + 2 * width;
            xSpeed = -xSpeed;
        }
        if (y <= (height / 2)) {
            y = -y + 2 * height;
            ySpeed = -ySpeed;
        }

        if (x >= (Main.WIDTH - width / 2)) {
            x = Main.WIDTH - (x - Main.WIDTH) - 2 * width;
            xSpeed = -xSpeed;
        }
        if (y >= (Main.HEIGHT - height / 2)) {
            y = Main.HEIGHT - (y - Main.HEIGHT) - 2 * height;
            ySpeed = -ySpeed;
        }
    }
}
