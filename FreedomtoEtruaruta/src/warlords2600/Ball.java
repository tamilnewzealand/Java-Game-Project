package warlords2600;

import EtruarutaGUI.Main;

/**
 * This class models a ball in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Ball{
    private int x, y, xSpeed = 0, ySpeed = 0, width = 17, height = 17, maxSpeed = 25;
    private boolean hitLastTick = false;
    private int collisionCounter = 0;
    private boolean spedup = false;
    private int spedUpCounter = 0;
    private int previousXSpeed, previousYSpeed;

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
        if (Math.abs(dX) <= maxSpeed){
            xSpeed = dX;
        } else {
            if (xSpeed < 0) {
                xSpeed = -maxSpeed;
            }else{
                xSpeed =  maxSpeed;
            }
        }
    }

    /**
     *
     * @param dY Velocity of ball in Y direction per game tick
     */
    public void setYVelocity(int dY) {
        if (Math.abs(dY) <= maxSpeed){
            ySpeed = dY;
        } else {
            if (ySpeed < 0) {
                ySpeed = -maxSpeed;
            }else{
                ySpeed =  maxSpeed;
            }
        }
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
     * @param height sets the height of the ball
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @param width sets the width of the ball
     */
    public void setWidth(int width) {
        this.width = width;
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
     *
     * @return the number of collisions that have occurred so far
     */
    public int getCollisionCounter(){
        return collisionCounter;
    }

    /**
     *
     * @return whether a speedup on the ball is currently active
     */
    public boolean getSpedUp(){
        return spedup;
    }

    /**
     *
     * @param spedUp the current speedup condition of the ball
     */
    public void setSpedUp(boolean spedUp){
            if (spedUpCounter <= 0) {
                spedup = spedUp;
                spedUpCounter = 150;
            }
    }

    /**
     *
     * @param hit marks the ball as being hit last tick
     */
    public void setHitLastTick(boolean hit){
        if (hit) {
            collisionCounter = 5;
        }
        hitLastTick = hit;
    }

    /**
     *
     * @return whether the ball was hit last tick
     */
    public boolean getHitLastTick(){
        return hitLastTick;
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
            x = width / 2;
            xSpeed = -xSpeed;
        }
        if (y <= (height / 2)) {
            y = height / 2;
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

    /**
     * Decrements the collision counter
     */
    public void decrementCounter(){
        if (collisionCounter > 0) {
            collisionCounter = collisionCounter - 1;
        }
    }

    /**
     * Checks if a speed up is active and if not applies the
     * speed up power up to the ball.
     * @param x new x speed for the ball
     * @param y new y speed for the ball
     */
    public void checkIncreaseSpeed(int x, int y){
        if (!spedup) {
            if (Math.abs(x) <= (maxSpeed*2 + 1) && Math.abs(y) <= (maxSpeed*2 + 1)) {
                previousYSpeed = Math.abs(ySpeed);
                previousXSpeed = Math.abs(xSpeed);
                ySpeed = y;
                xSpeed = x;
            }
        }
    }

    /**
     * Checks if the speed up power up is activated,
     * if it is then the spedUpCounter is decremented until
     * the time limit for the power up has been reached
     * at which point calling this methods disables the power
     * up and returns the ball to its previous speed.
     */
    public void checkReduceSpeed(){
        if (spedup) {
            if (spedUpCounter > 0) {
                spedUpCounter--;
            } else {
                spedup = false;
                if (xSpeed > 0) {
                    xSpeed = previousXSpeed;
                }else{
                    xSpeed = -1 * previousXSpeed;
                }
                if (ySpeed > 0) {
                    ySpeed = previousYSpeed;
                }else{
                    ySpeed = -1 * previousYSpeed;
                }
            }
        }
    }
}
