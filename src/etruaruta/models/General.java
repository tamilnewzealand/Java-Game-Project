package etruaruta.models;

import etruaruta.Main;

/**
 * This class models a General in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class General implements IObject {

    public Paddle paddle;
    public Paddle paddleFollower;
    public Brick[][] wall;
    private final Skill[] skillsArray = new Skill[2];
    private int skillsIndex = 0;
    private boolean skillTriggered = false;
    private int height = 25, width = 25, r, pos = 0;
    private double theta;
    private boolean dead = false, won = false;

    /**
     * Constructor for the general class
     * @param r radius from nearest corner
     * @param theta angle from centre line in radians
     * @param paddle the paddle associated with this general
     * @param wall the wall associated with this general
     * @param pos the position of the general in the game field
     */
    public General(int r, double theta, Paddle paddle, Brick[][] wall, int pos) {
        this.r = r;
        this.theta = theta;
        this.paddle = paddle;
        this.wall = wall;
        this.pos = pos;
        this.skillsArray[0] = new ExplosiveBall();
        this.skillsArray[1] = new BallHold();
    }

    /**
     * Overloaded constructor for use when there is a secondary
     * follower paddle that mirrors the main one
     * @param r radius from nearest corner
     * @param theta angle from centre line in radians
     * @param paddle the paddle associated with this general
     * @param paddleFollower the follower paddle associated with this general
     * @param wall the wall associated with this general
     * @param pos the position of the general in the game field
     */
    public General(int r, double theta, Paddle paddle, Paddle paddleFollower, Brick[][] wall, int pos) {
        this(r, theta, paddle, wall, pos);
        this.paddleFollower = paddleFollower;
        this.skillsArray[0] = new ExplosiveBall();
        this.skillsArray[1] = new BallHold();
    }

    /**
     *
     * @param width sets the width of the general
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     *
     * @param height sets the height of the general
     */
    public void setHeight(int height){
        this.height = height;
    }

    /**
     *
     * @return the current width of the general
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return the current
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @return whether the general is dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Kills the general when the function is called
     */
    public void killGeneral() {
        dead = true;
    }

    /**
     *
     * @return whether the general has won
     */
    public boolean hasWon() {
        return won;
    }

    /**
     * Marks the general as the winner
     */
    public void setWon() {
        this.won = true;
    }

    /**
     *
     * @return the x position of the general in the cartesian plane
     */
    public int calcXPos() {
        int xPos = (int)(r * Math.cos(theta));
        switch (pos) {
            case 0: return xPos;
            case 1: return (Main.WIDTH - xPos - 25);
            case 2: return (Main.WIDTH - xPos - 25);
            case 3: return xPos;
            default: return xPos;
        }
    }

    /**
     *
     * @return the y position of the general in the cartesian plane
     */
    public int calcYPos() {
        int yPos = (int)(r * Math.sin(theta));
        switch (pos) {
            case 0: return yPos;
            case 1: return yPos;
            case 2: return (Main.HEIGHT - yPos - 25);
            case 3: return (Main.HEIGHT - yPos - 25);
            default: return yPos;
        }
    }

    /**
     *
     * @return the angle from the centre line in radians
     */
    public double getTheta () {
        return theta;
    }

    /**
     *
     * @return the distance from the nearest corner
     */
    public int getR () {
        return r;
    }

    /**
     *
     * @return the number of walls that have yet to be destroyed
     */
    public int wallCount () {
        int wallCount = 0;
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[0].length; j++) {
                if (!wall[i][j].isDestroyed()) wallCount++;
            }
        }
        return wallCount;
    }

    /**
     *
     * @return the position on the game screen where this general is
     */
    public int getPos(){
        return pos;
    }

    /**
     *
     * @return the currently enabled skill for the player
     */
    public Skill getCurrentSkill(){
        return skillsArray[skillsIndex];
    }

    /**
     * Moves the currently enabled skill up the skill ladder
     */
    public void increaseSkillsIndex(){
        if (skillsIndex < skillsArray.length - 1){
            skillsIndex++;
        }else{
            skillsIndex = 0;
        }
    }
    /**
     * Moves the currently enabled skill down the skill ladder
     */
    public void decreaseSkillsIndex(){
        if (skillsIndex > 0){
            skillsIndex--;
        }else{
            skillsIndex = skillsArray.length - 1;
        }
    }

    /**
     *
     * @return whether the current skill is being used
     */
    public boolean isSkillTriggered(){
        return skillTriggered;
    }

    /**
     *
     * @param balls array of all the balls in play
     */
    public void triggerSkill(Ball[] balls){
        if (!skillTriggered) {
            skillsArray[skillsIndex].activateEffect(balls);
            skillTriggered = true;
        }
    }

    /**
     *
     * @return all the skills that are available to the player
     */
    public Skill[] getSkills(){
        return skillsArray;
    }

    /**
     * Resets all skills that are currently active
     */
    public void resetAllSkills(){
        skillTriggered = false;
    }
}
