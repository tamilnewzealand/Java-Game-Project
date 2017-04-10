package etruaruta.models;

/**
 * This class models a powerup in the game.
 * This class implements IObject to enable easy
 * collision detection.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public abstract class PowerUp implements IObject {
    private int xPos, yPos;
    private int width = 25,height = 25;
    private boolean hit = false;

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
     * @return  whether the power up has been hit
     */
    public boolean isHit(){
        return hit;
    }

    /**
     *
     * @param hit sets the power up to be hit
     */
    public void setHit(boolean hit){
        this.hit = hit;
    }

    /**
     *
     * @param xPos the x position to set the power up to
     * @param yPos the y position to set the power up to
     */
    public void setPos(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    abstract public void activateEffect(Ball ball, General[] generals);

    abstract public String getPowerUpName();

}
