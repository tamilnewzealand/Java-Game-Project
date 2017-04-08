package warlords2600;

/**
 * This class models a powerup in the game.
 * This class implements IObject to enable easy
 * collision detection.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public abstract class Skill {
    private int width = 40,height = 40;
    private boolean triggered = false;

    /**
     *
     * @return width of the skill
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return height of the skill
     */
    public int getHeight(){
        return height;
    }


    abstract public void activateEffect(Ball[] balls, int generalsPos);

    abstract public String getSkillName();

}
