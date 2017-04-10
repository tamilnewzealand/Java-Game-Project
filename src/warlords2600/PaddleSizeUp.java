package warlords2600;

import EtruarutaGUI.Main;

/**
 * This class extends the PowerUp class.
 * This class models the size up power up in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class PaddleSizeUp extends PowerUp{

    /**
     *
     * @param ball the ball to apply the power up to
     * @param generals an array of the generals in the game
     */
    public void activateEffect(Ball ball, General[] generals){
        for (int i = 0; i < generals.length; i ++){
            generals[i].paddle.checkIncreaseWidth(generals[i].paddle.getWidth()+50);
            if (Main.numberOfPaddles == 2) generals[i].paddleFollower.checkIncreaseWidth(generals[i].paddleFollower.getWidth()+50);
        }
    }

    /**
     *
     * @return string with the name of the power up
     */
    public String getPowerUpName(){
        return "Paddle Size Up";
    }
}
