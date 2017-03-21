package warlords2600;

import warlordstest.IBall;

public class Ball implements IBall{
    private int X, Y, XSpeed, YSpeed;

    public void setXPos(int x) {
        X = x;
    }

    public void setYPos(int y) {
        Y = y;
    }

    public int getXPos() {
        return X;
    }

    public int getYPos() {
        return Y;
    }

    public void setXVelocity(int dX) {
        XSpeed = dX;
    }

    public void setYVelocity(int dY) {
        YSpeed = dY;
    }

    public int getXVelocity() {
        return XSpeed;
    }

    public int getYVelocity() {
        return YSpeed;
    }
}
