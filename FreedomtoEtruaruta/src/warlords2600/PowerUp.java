package warlords2600;

/**
 * Created by adilb on 3/04/2017.
 */
public abstract class PowerUp implements IObject {
    private int xPos, yPos;
    private int width = 25,height = 25;
    private boolean hit = false;

    public void PowerUp(){
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
    abstract public void activateEffect(Ball ball, General[] generals);

    abstract public String getPowerUpName();

}
