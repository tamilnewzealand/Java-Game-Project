package warlords2600;

/**
 * Created by adilb on 8/04/2017.
 */
public class ExplosiveBall extends Skill {

    public void activateEffect(Ball[] balls, General[] generals){
        for (int i = 0; i < balls.length; i++){
            balls[i].setExplosive(true);
        }
    }

    public String getSkillName(){
        return "Explosive Ball";
    }
}
