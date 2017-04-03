package warlords2600;

import EtruarutaGUI.Main;

/**
 * This class models a brick in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Brick implements IObject{

    private int xPos, yPos, height = 25, width = 25, r, pos = 0;
    private double theta;
    private boolean destroyed = false;

    public Brick() {
        xPos = 0;
        yPos = 0;
        theta = Math.PI/4;
        r = 10;
    }

    public Brick(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Brick(int r, double theta, int pos) {
        this.r = r;
        this.theta = theta;
        this.pos = pos;
    }


    public void setXPos(int x) {
        xPos = x;
    }

    public void setYPos(int y) {
        yPos = y;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroyBrick() {
        destroyed = true;
    }

    public int calcXPos() {
        xPos = (int)(r * Math.cos(theta));
        switch (pos) {
            case 0: return xPos;
            case 1: return (Main.WIDTH - xPos);
            case 2: return (Main.WIDTH - xPos);
            case 3: return xPos;
            default: return xPos;
        }
    }

    public int calcYPos() {
        yPos = (int)(r * Math.sin(theta));
        switch (pos) {
            case 0: return yPos;
            case 1: return yPos;
            case 2: return (Main.HEIGHT - yPos);
            case 3: return (Main.HEIGHT - yPos);
            default: return yPos;
        }
    }

    public double getTheta () {
        return theta;
    }

    public int getR () {
        return r;
    }

}
