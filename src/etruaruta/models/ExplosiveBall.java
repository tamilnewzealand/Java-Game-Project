package etruaruta.models;

/**
 * Created by adilb on 8/04/2017.
 */
public class ExplosiveBall extends Skill {

    public void activateEffect(Ball[] balls){
        for (int i = 0; i < balls.length; i++){
            if (!balls[i].isExplosive()){
                balls[i].setExplosive();
            }
        }
    }

    public String getSkillName(){
        return "Explosive Ball";
    }

}
