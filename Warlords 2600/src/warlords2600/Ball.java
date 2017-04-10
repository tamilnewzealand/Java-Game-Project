package warlords2600;

import warlordstest.IBall;

public class Ball implements IBall{
    private int x, y, xSpeed = 0, ySpeed = 0, width = 5, height = 5;

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

    public void processBall() {
        x += xSpeed;
        y += ySpeed;

        if (x <= (width / 2)) {
            x = -x + 2 * width;
            xSpeed = -xSpeed;
        }
        if (y <= (height / 2)) {
            y = -y + 2 * height;
            ySpeed = -ySpeed;
        }

        if (x >= (Globals.WIDTH - width / 2)) {
            x = Globals.WIDTH - (x - Globals.WIDTH) - 2 * width;
            xSpeed = -xSpeed;
        }
        if (y >= (Globals.HEIGHT - height / 2)) {
            y = Globals.HEIGHT - (y - Globals.HEIGHT) - 2 * height;
            ySpeed = -ySpeed;
        }
    }
}