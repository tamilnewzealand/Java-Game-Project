package warlords2600;

/**
 * Created by adilb on 9/04/2017.
 */
public class ArrowPointer {
    private int angle = 0;
    private int xPivot = 0, yPivot = 0;
    private int xPos = 0, yPos = 0;

    public void ArrowPointer(){

    }

    public void increaseAngle(){
        if (angle < 60){
            angle += 2;
        }
    }

    public void decreaseAngle(){
        if (angle > -60){
            angle -= 2;
        }
    }

    public int getAngle(){
        return angle;
    }

    public int getxPivot(){
        return xPivot;
    }

    public int getyPivot(){
        return yPivot;
    }

    public void calcPivots(Paddle paddle, Ball ball){
        xPivot = paddle.calcXPos() + paddle.getWidth()/2;
        yPivot = paddle.calcYPos() + ball.getHeight()/2 + paddle.getHeight();
    }

    public void calcPos(Paddle paddle, Ball ball){
        xPos = paddle.calcXPos() + paddle.getWidth()/2 - 17;
        yPos = paddle.calcYPos() + ball.getHeight() + paddle.getHeight() + 10;
    }

    public int getxPos(){
        return xPos;
    }

    public int getyPos(){
        return yPos;
    }

}
