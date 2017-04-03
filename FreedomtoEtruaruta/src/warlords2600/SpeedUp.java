package warlords2600;

/**
 * Created by adilb on 1/04/2017.
 */
public class SpeedUp extends PowerUp{

    public void activateEffect(Ball ball, General[] generals){
        ball.checkIncreaseSpeed(ball.getXVelocity()*2,ball.getYVelocity()*2);
    }

    public String getPowerUpName(){
        return "Speed Up";
    }
}
