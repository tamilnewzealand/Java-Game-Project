package warlords2600;

import EtruarutaGUI.Main;

/**
 * Created by adilb on 3/04/2017.
 */
public class Marker implements IObject {
    private int xPos = Main.WIDTH/2,yPos = Main.HEIGHT/2,width = 25,height = 25;
    private int pos;

    public void Marker(){
    }

    public void setxPos(int xPos){
        this.xPos = xPos;
    }

    public void setyPos(int yPos){
        this.yPos = yPos;
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

    public void setPos(int pos){
        this.pos = pos;
    }

    public int getPos(){
        return pos;
    }

}
