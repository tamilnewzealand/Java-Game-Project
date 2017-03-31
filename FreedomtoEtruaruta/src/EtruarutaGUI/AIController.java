package EtruarutaGUI;


import warlords2600.General;

/**
 * Created by adilb on 31/03/2017.
 */
public class AIController {
    private General general;
    private int tickCOunter = 0;

    public void AIController(){

    }

    public void setGeneral(General general){
        this.general = general;
    }

    public void movePaddle(int ballXPos, int ballYPos){
            if (tickCOunter <= 0) {
                double distanceBefore = calculateDistance(ballXPos, ballYPos);
                if (distanceBefore > 25) {
                    tickCOunter = 5;
                    general.paddle.moveLeft();

                    double distanceAfter = calculateDistance(ballXPos, ballYPos);

                    if (distanceAfter < distanceBefore) {
                        //Do Nothing our move made it get closer to ball in y axis. No further action required.
                    } else {
                        general.paddle.moveRight();
                        general.paddle.moveRight();//Move left twice to compensate for wrong right turn initially
                    }
                }
            }else{
                tickCOunter--;
            }
    }

    private double calculateDistance(int ballXPos, int ballYPos){
        int yDistanceBefore = Math.abs(general.paddle.calcYPos() - ballYPos);
        int XDistanceBefore = Math.abs(general.paddle.calcXPos() - ballXPos);
        return Math.sqrt(Math.pow(XDistanceBefore,2)+Math.pow(yDistanceBefore,2));
    }
}
