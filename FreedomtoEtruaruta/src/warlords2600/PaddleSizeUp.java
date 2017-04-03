package warlords2600;

/**
 * Created by adilb on 3/04/2017.
 */
public class PaddleSizeUp extends PowerUp{
    public void activateEffect(Ball ball, General[] generals){
        for (int i = 0; i < generals.length; i ++){
            generals[i].paddle.checkIncreaseWidth(generals[i].paddle.getWidth()*2);
        }
    }

    public String getPowerUpName(){
        return "Paddle Size Up";
    }
}
