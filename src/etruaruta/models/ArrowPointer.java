package etruaruta.models;

/**
 * This class models an arrow pointer in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class ArrowPointer {
    private int angle = 0;
    private int xPivot = 0, yPivot = 0;
    private int xPos = 0, yPos = 0;

    /**
     * Increases the angle of the pointer by 2
     */
    public void increaseAngle(){
        if (angle < 60){
            angle += 2;
        }
    }

    /**
     * Decreases the angle of the pointer by 2
     */
    public void decreaseAngle(){
        if (angle > -60){
            angle -= 2;
        }
    }

    /**
     *
     * @return current angle of the pointer
     */
    public int getAngle(){
        return angle;
    }

    /**
     *
     * @return x pivot position
     */
    public int getxPivot(){
        return xPivot;
    }

    /**
     *
     * @return y pivot position
     */
    public int getyPivot(){
        return yPivot;
    }

    /**
     *
     * @param paddle the paddle that this skill applies to
     * @param ball the ball which the paddle caught
     */
    public void calcPivots(Paddle paddle, Ball ball){
        xPivot = paddle.calcXPos() + paddle.getWidth()/2;
        yPivot = paddle.calcYPos() + ball.getHeight()/2 + paddle.getHeight();
    }

    /**
     *
     * @param paddle the paddle that this skill applies to
     * @param ball the ball which the paddle caught
     */
    public void calcPos(Paddle paddle, Ball ball){
        xPos = paddle.calcXPos() + paddle.getWidth()/2 - 17;
        yPos = paddle.calcYPos() + ball.getHeight() + paddle.getHeight() + 10;
    }

    /**
     *
     * @return the x position of the arrow
     */
    public int getxPos(){
        return xPos;
    }

    /**
     *
     * @return the y position of the arrow
     */
    public int getyPos(){
        return yPos;
    }

    /**
     * Resets the angle to 0
     */
    public void resetAngle(){
        angle = 0;
    }

}
