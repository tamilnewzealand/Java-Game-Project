package warlords2600;

/**
 * Created by adilb on 8/04/2017.
 */
public class ExplosiveBall extends Skill {

    public void activateEffect(Ball[] balls, int generalsPos){
        for (int i = 0; i < balls.length; i++){
            if (!balls[i].isExplosive()){
                balls[i].setExplosive(generalsPos);
            }
        }
    }

    public String getSkillName(){
        return "Explosive Ball";
    }

}
