package etruaruta.models;

/**
 * This class models a ball hold in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class BallHold extends Skill {

    /**
     *
     * @param balls array of all the balls
     */
    public void activateEffect(Ball[] balls){
        for (int i = 0; i < balls.length; i ++){
            balls[i].setWillBeHeld(true);
        }
    }

    /**
     *
     * @return name of the skill
     */
    public String getSkillName(){
        return "Ball Hold";
    }
}
