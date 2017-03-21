package warlords2600;

import warlordstest.IPaddle;

/**
 * Created by adilb on 21/03/2017.
 */
public class Paddle implements IPaddle {
    private int x, y, speed, width, height;

    public void setXPos(int x){
        this.x = x;
    }

    public void setYpos(int y){
        this.y = y;
    }
    public void  setSpeed(int speed){
        this.speed = speed;
    }

    public void  setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }


    public int getXPos(){
        return x;
    }

    public int getYpos(){
        return y;
    }

    public int getSpeed(){
        return speed;
    }

    public int getWidth(){
        return width;
    }

    public int getheight(){
        return height;
    }
}
