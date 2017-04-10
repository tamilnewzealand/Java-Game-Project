package etruaruta.models;

/**
 * This class models an explosive ball in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class ExplosiveBall extends Skill {

    /**
     *
     * @param balls array of all the balls
     */
    public void activateEffect(Ball[] balls){
        for (int i = 0; i < balls.length; i++){
            if (!balls[i].isExplosive()){
                balls[i].setExplosive();
            }
        }
    }

    /**
     *
     * @return name of the skill
     */
    public String getSkillName(){
        return "Explosive Ball";
    }

}
