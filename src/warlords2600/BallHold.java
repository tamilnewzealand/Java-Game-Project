package warlords2600;

/**
 * Created by adilb on 8/04/2017.
 */
public class BallHold extends Skill {
    public void activateEffect(Ball[] balls){
        for (int i = 0; i < balls.length; i ++){
            balls[i].setWillBeHeld(true);
        }
    }

    public String getSkillName(){
        return "Ball Hold";
    }
}
