package warlords2600;

import warlordstest.IBall;

public class Ball implements IBall{
    private int x, y, xSpeed, ySpeed;

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
}
