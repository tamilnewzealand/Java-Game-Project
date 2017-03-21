package warlords2600;

import warlordstest.IBall;

public class Ball implements IBall{
    private int x = 0, y = 0, xSpeed = 0, ySpeed = 0;

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

    public void processBall() {
        x += xSpeed;
        y += ySpeed;

        if(x <= 0) {
            x = -x;
            xSpeed = -xSpeed;
        }
        if(y <= 0) {
            y = -y;
            ySpeed = -ySpeed;
        }

        if(x >= 1024) {
            x = 1024 - (x - 1024);
            xSpeed = -xSpeed;
        }
        if(y >= 768) {
            y = 768 - (y - 768);
            ySpeed = -ySpeed;
        }
    }
}
