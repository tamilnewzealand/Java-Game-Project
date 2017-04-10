package etruaruta.models;

/**
 * This class extends the PowerUp class.
 * This class models the speed up power up in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class SpeedUp extends PowerUp{

    /**
     *
     * @param ball the ball to apply the power up to
     * @param generals an array of the generals in the game
     */
    public void activateEffect(Ball ball, General[] generals){
        ball.checkIncreaseSpeed(ball.getXVelocity()*2,ball.getYVelocity()*2);
    }

    /**
     *
     * @return string with the name of the power up
     */
    public String getPowerUpName(){
        return "Speed Up";
    }
}
