package warlords2600;

import EtruarutaGUI.Main;
import warlordstest.IBall;

public class Ball implements IBall{
    private int x, y, xSpeed = 0, ySpeed = 0, width = 15, height = 15;
    private boolean hitLastTick = false;
    private int collisionCounter = 0;

    public Ball() {
        x = 0;
        y = 0;
    }

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setXPos(int x) {
        this.x = x;
    }

    public void setYPos(int y) {
        this.y = y;
    }

    public int getXPos() {
        return this.x;
    }

    public int getYPos() {
        return this.y;
    }

    public void setXVelocity(int dX) {
        xSpeed = dX;
    }

    public void setYVelocity(int dY) {
        ySpeed = dY;
    }

    public int getXVelocity() {
        return xSpeed;
    }

    public int getYVelocity() {
        return ySpeed;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCollisionCounter(){
        return collisionCounter;
    }

    public void setHitLastTick(boolean hit){
        if (hit) {
            collisionCounter = 5;
        }
        hitLastTick = hit;
    }

    public boolean getHitLastTick(){
        return hitLastTick;
    }
    public void processBall() {
        x += xSpeed;
        y += ySpeed;

        if (x <= (width / 2)) {
            x = width / 2;
            xSpeed = -xSpeed;
        }
        if (y <= (height / 2)) {
            y = height / 2;
            ySpeed = -ySpeed;
        }

        if (x >= (Main.WIDTH - width / 2)) {
            x = Main.WIDTH - (x - Main.WIDTH) - 2 * width;
            xSpeed = -xSpeed;
        }
        if (y >= (Main.HEIGHT - height / 2)) {
            y = Main.HEIGHT - (y - Main.HEIGHT) - 2 * height;
            ySpeed = -ySpeed;
        }
    }

    public void decrementCounter(){
        if (collisionCounter > 0) {
            collisionCounter = collisionCounter - 1;
        }
    }
}
