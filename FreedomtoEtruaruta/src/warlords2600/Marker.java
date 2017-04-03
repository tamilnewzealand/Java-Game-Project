package warlords2600;

import EtruarutaGUI.Main;

/**
 * Created by adilb on 3/04/2017.
 */
public class Marker implements IObject {
    private int xPos = Main.WIDTH/2,yPos = Main.HEIGHT/2,width = 25,height = 25;
    private int pos, readyCounter = 900;

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

    public void moveLeft(){
        if (xPos > 10) {
            xPos = xPos - 10;
        }
    }

    public void moveRight(){
        if (xPos < Main.WIDTH-width-10) {
            xPos = xPos + 10;
        }
    }

    public void moveUp(){
        if (yPos > 10) {
            yPos = yPos - 10;
        }
    }

    public void moveDown(){
        if (yPos < Main.HEIGHT-height-10) {
            yPos = yPos + 10;
        }
    }

    public boolean getReady(){
        if (readyCounter == 0){
            return true;
        }else{
            return false;
        }
    }

    public void decrementReadyCounter(){
        if (readyCounter > 0) {
            readyCounter--;
        }
    }

    public void resetReadyCounter(){
        readyCounter = 450;
    }
}
