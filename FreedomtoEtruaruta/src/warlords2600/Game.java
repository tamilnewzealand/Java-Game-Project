package warlords2600;

import EtruarutaGUI.AIController;
import EtruarutaGUI.SceneManager;
import EtruarutaGUI.SoundManager;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Game{

    private boolean isFinished = false;
    private int timeElapsed = 0;
    private boolean paused = false;

    public General[] generals;
    public Ball ball;
    public ArrayList<SpeedUp> speedUps = new ArrayList<SpeedUp>();
    public SpeedUp speedUp;
    public ArrayList<AIController> AIs = new ArrayList<AIController>();

    public ArrayList<Marker> markers = new ArrayList<>();

    private int[] deadPos;


    public Game(Ball ball, General generalA, General generalB, Brick brick) {
        this.ball = ball;
        this.generals = new General[2];
        this.generals[0] = generalA;
        this.generals[1] = generalB;
        this.generals[0].wall[0][0] = brick;
    }

    public Game(Ball ball, General generalA, General generalB, General generalC, General generalD) {
        this.speedUp = new SpeedUp();
        this.speedUps.add(this.speedUp);
        generatePowerUp(0);
        this.ball = ball;
        this.generals = new General[4];
        this.generals[0] = generalA;
        this.generals[1] = generalB;
        this.generals[2] = generalC;
        this.generals[3] = generalD;

        this.AIs.add(new AIController());
        this.AIs.get(0).setGeneral(generalB);

        this.AIs.add(new AIController());
        this.AIs.get(1).setGeneral(generalC);

        this.AIs.add(new AIController());
        this.AIs.get(2).setGeneral(generalD);

        this.deadPos = new int[4];

    }

    public void tick(){
        if (!paused) {
            timeElapsed++;
            boolean ballHit = false;
            boolean generalHit = false;
            int deadCount = 0;
            //System.out.println("Tick");
            if (!ball.getHitLastTick() && ball.getCollisionCounter() <= 0) {
                for (int i = 0; i < speedUps.size(); i++) {
                    if (!ballHit && (!speedUps.get(i).isHit())) {
                        if (objectCollision(speedUps.get(i), ballHit, false)) {
                            if (!ball.getSpedUp()) {
                                speedUps.get(i).setHit(true);
                                speedUps.get(i).activateEffect(ball);
                                SoundManager.playSpeedUp();
                                ball.setSpedUp(true);
                            }
                        }
                    }
                }
                outerLoop:
                for (int i = 0; i < generals.length; i++) {
                    if (!ballHit && (!generals[i].isDead())) ballHit = objectCollision(generals[i].paddle, ballHit);
                    if (!ballHit && (!generals[i].isDead())) {
                        ballHit = objectCollision(generals[i], ballHit);
                        if (ballHit){
                            generals[i].killGeneral();
                            generalHit = true;
                            SoundManager.playGeneralDeath();
                        }
                    }
                    if (!ballHit) {
                        for (int j = 0; j < generals[i].wall.length; j++) {
                            for (int k = 0; k < generals[i].wall[j].length; k++) {
                                if (!generals[i].wall[j][k].isDestroyed()){
                                    if (!ballHit) ballHit = objectCollision(generals[i].wall[j][k], ballHit);
                                    if (ballHit) {
                                        generals[i].wall[j][k].destroyBrick();
                                        break outerLoop;
                                    }
                                }
                            }
                        }
                    }

                }
            }

            if (ballHit && !generalHit) SoundManager.playCollision();

            for (int i = 0; i < generals.length; i++) {
                if (generals[i].isDead()) deadCount++;
            }
            if (deadCount + 1 == generals.length) {
                for (int i = 0; i < generals.length; i++) {
                    if (!generals[i].isDead()) {
                        generals[i].setWon();
                        isFinished = true;
                    }
                }
            }

            if (deadCount > 0 && markers.size() < deadCount ){
                for (int i = 0; i < deadPos.length;i++) {
                    if (deadPos[i] != 1 && generals[i].isDead()) {
                        this.markers.add(new Marker());
                        this.markers.get(this.markers.size() - 1).setPos(i);
                        System.out.println(this.markers.get(this.markers.size() - 1).getPos());
                        deadPos[i] = 1;
                        break;
                    }
                }
            }
            if (!ballHit) {
                ball.processBall();
                ball.setHitLastTick(false);
                ball.decrementCounter();
            }

            if (timeElapsed > 3600) {
                isFinished = true;
                // need to update this logic to better represent timeout wins
                // rn warlord 0 wins automatically when timeout occurs
                generals[0].setWon();
            }

            for (int i = 0; i < AIs.size();i++){
                AIs.get(i).movePaddle(ball);
            }

            if (timeElapsed % 900 == 0){
                this.speedUps.add(new SpeedUp());
                generatePowerUp(speedUps.size()-1);
            }
            ball.checkReduceSpeed();
        }
    }


    private boolean objectCollision (IObject object, boolean ballHit) {
        for (int x = object.calcXPos(); x < (object.calcXPos() + object.getWidth()); x++) {
            for (int y = object.calcYPos(); y < (object.calcYPos() + object.getHeight()); y++) {
                if (x == object.calcXPos() || y == object.calcYPos() || x == (object.calcXPos() + object.getWidth()) || y == (object.calcYPos() + object.getHeight())) {
                    if (inBallPath(x, y)) {
                        if (x == object.calcXPos()) {
                            ball.setYVelocity(-ball.getYVelocity());
                            //System.out.println(ball.getXPos());
                            ball.setXPos(ball.getXPos() + ball.getXVelocity());
                            ball.setYPos(ball.getYPos() + ball.getYVelocity());
                            //System.out.println("A , X: " + ball.getXPos() + " Y: " + ball.getYPos() + " Y Velocity: " + ball.getYVelocity() + " X Velocity: " + ball.getXVelocity());
                            ball.setHitLastTick(true);
                            return true;
                        } else if (y == object.calcYPos()) {
                            ball.setYVelocity(-ball.getYVelocity());
                            ball.setXPos(ball.getXPos() + ball.getXVelocity());
                            //System.out.println(ball.getYPos());
                            ball.setYPos(ball.getYPos() + ball.getYVelocity());
                            //System.out.println("B , X: " + ball.getXPos() + " Y: " + ball.getYPos() + " Y Velocity: " + ball.getYVelocity() + " X Velocity: " + ball.getXVelocity());
                            ball.setHitLastTick(true);
                            return true;
                        } else if (x == (object.calcXPos() + object.getWidth())) {
                            ball.setYVelocity(-ball.getYVelocity());
                            //ball.setXPos(ball.getXPos() - ball.getXVelocity());
                            ball.setYPos(ball.getYPos() - ball.getYVelocity());
                            System.out.println("C");
                            ball.setHitLastTick(true);
                            return true;
                        } else if (y == (object.calcYPos() + object.getHeight())) {
                            ball.setYVelocity(-ball.getYVelocity());
                            //ball.setXPos(ball.getXPos() - ball.getXVelocity());
                            ball.setYPos(ball.getYPos() - ball.getYVelocity());
                            System.out.println("D");
                            ball.setHitLastTick(true);
                            return true;
                        }
                    }
                }
            }
        }
        return ballHit;
    }

    private boolean objectCollision (IObject object, boolean ballHit, boolean bounce) {
        for (int x = object.calcXPos(); x < (object.calcXPos() + object.getWidth()); x++) {
            for (int y = object.calcYPos(); y < (object.calcYPos() + object.getHeight()); y++) {
                if (x == object.calcXPos() || y == object.calcYPos() || x == (object.calcXPos() + object.getWidth()) || y == (object.calcYPos() + object.getHeight())) {
                    if (inBallPath(x, y)) {
                        if (x == object.calcXPos()) {
                            return true;
                        } else if (y == object.calcYPos()) {
                            return true;
                        } else if (x == (object.calcXPos() + object.getWidth())) {
                            return true;
                        } else if (y == (object.calcYPos() + object.getHeight())) {
                            return true;
                        }
                    }
                }
            }
        }
        return ballHit;
    }

    /* Bresenham Line Drawing Algorithm */
    private boolean inBallPath (int xCord, int yCord) {
        int x1 = ball.getXPos();
        int x2 = ball.getXPos() + ball.getWidth();
        int y1 = ball.getYPos();
        int y2 = ball.getYPos() + ball.getHeight();
        int x, y, dx, dy, p, end;

        dx = Math.abs(x1 - x2);
        dy = Math.abs(y1 - y2);
        p = 2 * dy - dx;
        if(x1 > x2) {
            x = x2;
            y = y2;
            end = x1;
        }
        else {
            x = x1;
            y = y1;
            end = x2;
        }
        if ((x == xCord) && (y == yCord)) return true;
        while(x < end) {
            x = x + 1;
            if(p < 0) {
                p = p + 2 * dy;
            }
            else {
                y = y + 1;
                p = p + 2 * (dy - dx);
            }
            if ((x == xCord) && (y == yCord)) return true;
        }
        return false;
    }

    public boolean isFinished(){
        return isFinished;
    }

    public String getTimeRemaining() {
        int time = (120 - (timeElapsed / 30));
        if (time == 120) return "2:00";
        if (time > 59) return "1:" + String.format("%02d",time-60);
        else return "0:" + String.format("%02d",time);
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (120 - seconds) * 60;
    }

    public void setFinished(boolean finished){
        isFinished = finished;
    }

    public boolean getFinished() {
        return isFinished;
    }

    public boolean getPaused() {
        return paused;
    }

    private void generatePowerUp(int i){
            double xPos = Math.random() * 424 + 350;
            double yPos = Math.random() * 608 + 50;
            speedUps.get(i).setPos((int) xPos, (int) yPos);
    }

    public void HandleInputs(Scene playNowScene, SceneManager sceneManager) {
        playNowScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case LEFT:
                        if (!paused) {
                            generals[0].paddle.moveLeft();
                        }
                        break;
                    case RIGHT:
                        if (!paused){
                            generals[0].paddle.moveRight();
                        }
                        break;
                    case ESCAPE:
                            setFinished(true);
                            sceneManager.goToMenuScene(sceneManager);
                        break;
                    case P:
                        if (paused) {
                            paused = false;
                        } else{
                            paused = true;
                        }
                        break;
                }
            }

        });
    }
}
