package warlords2600;

public interface IPowerUp {

    boolean isHit();

    void setHit(boolean hit);

    void setPos(int xPos, int yPos);

    void activateEffect(Ball ball);

    int calcXPos();

    int calcYPos();

    int getWidth();

    int getHeight();

}

