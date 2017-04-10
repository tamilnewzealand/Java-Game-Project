package etruaruta.score;

import java.io.Serializable;

/**
 * This class models a score achieved in the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Score implements Serializable {
    private int score;
    private String name;

    /**
     *
     * @return the score stored in this object
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @return the name of the player who achieved this score
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name of the player
     * @param score the score achieved by this player
     */
    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }
}