package etruaruta.models;

import etruaruta.Main;

/**
 * This class models a marker in the game.
 * This class implements IObject to enable easy
 * collision detection.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Marker implements IObject {
    private int xPos = Main.WIDTH/2,yPos = Main.HEIGHT/2,width = 25,height = 25;
    private int pos, readyCounter = 300;

    /**
     *
     * @return the calculated x position in the cartesian plane
     */
    public int calcXPos(){
        return xPos;
    }

    /**
     *
     * @return the calculated y position in the cartesian plane
     */
    public int calcYPos(){
        return yPos;
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
     *
     * @param pos sets the position of the marker in the game screen
     */
    public void setPos(int pos){
        this.pos = pos;
    }

    /**
     *
     * @return the position of the marker in the game screen
     */
    public int getPos(){
        return pos;
    }

    /**
     * Moves the marker to the left
     */
    public void moveLeft(){
        if (xPos > 10) xPos = xPos - 10;
    }

    /**
     * Moves the marker to the right
     */
    public void moveRight(){
        if (xPos < Main.WIDTH-width-10) xPos = xPos + 10;
    }

    /**
     * Moves the marker upwards
     */
    public void moveUp(){
        if (yPos > 10) yPos = yPos - 10;
    }

    /**
     * Moves the marker downwards
     */
    public void moveDown(){
        if (yPos < Main.HEIGHT-height-10) yPos = yPos + 10;
    }

    /**
     *
     * @return whether the marker is ready to release a power up
     */
    public boolean getReady(){
        if (readyCounter == 0) return true;
        else return false;
    }

    /**
     * Decrements the power up ready counter
     */
    public void decrementReadyCounter(){
        if (readyCounter > 0) readyCounter--;
    }

    /**
     * Resets the power up ready counter
     */
    public void resetReadyCounter(){
        readyCounter = 300;
    }
}
