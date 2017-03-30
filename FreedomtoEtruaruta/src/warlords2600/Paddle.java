package warlords2600;

import EtruarutaGUI.Main;
import warlordstest.IPaddle;
import static java.lang.Math.cos;
import static java.lang.Math.multiplyExact;

public class Paddle implements IPaddle {
    private int x, y, r = 300, speed = 5, width = 50, height = 10, pos = 0;
    double theta, polarSpeed = 0.1;

    public Paddle() {
        this.theta = Math.PI/4;
        this.x = 0;
        this.y = 0;
    }

    public Paddle(double theta, int pos) {
        this.theta = theta;
        this.pos = pos;
    }

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public void setXPos(int x){
        this.x = x;
    }

    public void setYPos(int y){
        this.y = y;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getXPos(){
        return x;
    }

    public int getYPos(){
        return y;
    }

    public int getSpeed(){
        return speed;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void goLeft() {
        if (x >= speed) x -= speed;
    }

    public void goRight() {
        if (x < Main.WIDTH - speed - width/2)x += speed;
    }

    public void goDown() {
        if (y < Main.HEIGHT - speed - 1.5*height) y += speed;
    }

    public void goUp() {
        if (y >= speed) y -= speed;
    }

    public void moveRight() {
        if (theta > polarSpeed) theta -= polarSpeed;
    }

    public void moveLeft() {
        if (theta < Math.PI / 2 - polarSpeed) theta += polarSpeed;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public int calcXPos() {
        x = (int)(r * Math.cos(theta));
        switch (pos) {
            case 0: return x;
            case 1: return (Main.WIDTH - x);
            case 2: return (Main.WIDTH - x);
            case 3: return x;
            default: return x;
        }
    }

    public int calcYPos() {
        y = (int)(r * Math.sin(theta));
        switch (pos) {
            case 0: return y;
            case 1: return y;
            case 2: return (Main.HEIGHT - y);
            case 3: return (Main.HEIGHT - y);
            default: return y;
        }
    }

    public double getTheta () {
        return theta;
    }

    public int getR () {
        return r;
    }

}
