package etruaruta.models;

import etruaruta.Main;

/**
 * This class models a ball in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class Ball{
    private int x, y, xSpeed = 0, ySpeed = 0, width = 17, height = 17, maxSpeed = 25;
    private boolean hitLastTick = false;
    private int collisionCounter = 0;
    private boolean spedup = false;
    private int spedUpCounter = 0;
    private int previousXSpeed, previousYSpeed;
    private boolean isExplosive = false;
    private boolean willBeHeld = false;
    private boolean isHeld = false;

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
        if (Math.abs(dX) <= maxSpeed){//Ensure that velocity isn't more than the maximum
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
        if (Math.abs(dY) <= maxSpeed){//Ensures the y velocity isn't more than the maximum allowed
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
     * @return 15 - the number of ticks that have happen after collision
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
                spedUpCounter = 150; //Ensure ball stalls sped up for 150 ticks
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

        if (x <= (width / 2)) { //Make sure that the ball doesn't go off the screen's width on the left
            x = width / 2;
            xSpeed = -xSpeed;
        }
        if (y <= (height / 2)) { //Make sure that the ball doesn't go off the screen's height at the top
            y = height / 2;
            ySpeed = -ySpeed;
        }

        if (x >= (Main.WIDTH - width / 2)) { //Make sure that the ball doesn't go off the screen's width on the right
            x = Main.WIDTH - (x - Main.WIDTH) - 2 * width;
            xSpeed = -xSpeed;
        }
        if (y >= (Main.HEIGHT - height / 2)) { //Make sure that the ball doesn't go off the screen's height at the bottom
            y = Main.HEIGHT - (y - Main.HEIGHT) - 2 * height;
            ySpeed = -ySpeed;
        }
    }

    /**
     * Decrements the collision counter
     */
    public void decrementCounter(){
        if (collisionCounter > 0) {
            collisionCounter = collisionCounter - 1; //Decrease the collision counter each tick when this method is called
        }
    }

    /**
     * Checks if a speed up is active and if not applies the
     * speed up power up to the ball.
     * @param x new x speed for the ball
     * @param y new y speed for the ball
     */
    public void checkIncreaseSpeed(int x, int y){
        if (!spedup) {//Make sure it is not already sped up
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
                    xSpeed = -1 * previousXSpeed; //Making sure that the reverting of velocity doesn't change direction of the ball
                }
                if (ySpeed > 0) {
                    ySpeed = previousYSpeed;
                }else{
                    ySpeed = -1 * previousYSpeed; //Making sure that the reverting of velocity doesn't change direction of the ball
                }
            }
        }
    }

    /**
     * Sets the ball as explosive if it already isn't
     */
    public void setExplosive(){
        if (!isExplosive) isExplosive = true;
    }

    /**
     * Un sets teh explosive ball effect
     */
    public void setUnexplosive(){
        if (isExplosive) isExplosive = false;
    }

    /**
     *
     * @return the whether the ball is currently explosive
     */
    public boolean isExplosive(){
        return isExplosive;
    }

    /**
     *
     * @param willBeHeld whether the ball will be held
     */
    public void setWillBeHeld(boolean willBeHeld){
        if (willBeHeld && (!this.willBeHeld)) this.willBeHeld = true;
        else this.willBeHeld = false;
    }

    /**
     * Sets the ball as being held
     */
    public void setBallHeld(){
        if (willBeHeld){
            isHeld = true;
            willBeHeld = false;
            spedup = false; //Remove the sped up of the ball
            spedUpCounter = 0;
            xSpeed = 0; //Stop the ball moving
            ySpeed = 0;
        }
    }

    /**
     *
     * @return whether the ball will be held
     */
    public boolean getWillBeHeld(){
       return willBeHeld;
    }

    /**
     *
     * @return whether the ball is held
     */
    public boolean isHeld(){
        return isHeld;
    }

    /**
     * Releases the hold on the ball
     */
    public void unHeld(){
        isHeld = false;
    }

    /**
     * Launches the ball in the direction of the arrow
     * @param arrow the pointer arrow marking the direction
     */
    public void launch(ArrowPointer arrow){
        int angle = arrow.getAngle();
        int velocity = 15;
        xSpeed =(int) (-velocity * Math.sin(angle * Math.PI / 180));//Calculate x speed
        ySpeed = (int) (velocity * Math.cos(angle * Math.PI / 180));//Calculate y speed

    }
}
