package warlords2600;

/**
 * Created by adilb on 1/04/2017.
 */
public class SpeedUp implements IObject, IPowerUp{
    private int xPos, yPos;
    private int width = 25,height = 25;
    private boolean hit = false;

    public void SpeedUp(){
    }

    public int calcXPos(){
        return xPos;
    }

    public int calcYPos(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isHit(){
        return hit;
    }

    public void setHit(boolean hit){
        this.hit = hit;
    }

    public void setPos(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void activateEffect(Ball ball){
        ball.checkIncreaseSpeed(ball.getXVelocity()*2,ball.getYVelocity()*2);
    }
}
