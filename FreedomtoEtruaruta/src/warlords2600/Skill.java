package warlords2600;

/**
 * This class models a Skill in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public abstract class Skill {
    private int width = 50,height = 50;
    private boolean executed = false;

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


    abstract public void activateEffect(Ball[] balls);

    abstract public String getSkillName();

}
