package etruaruta.score;

import java.util.Comparator;

/**
 * This class compares two scores to find the higher score.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class ScoreComparator implements Comparator<Score> {

    /**
     * Compares two scores to find the higher score
     * @param score1 a score to compare with
     * @param score2 another score to comapare with
     * @return -1 if Score 1 is greater and +1 otherwise
     */
    public int compare(Score score1, Score score2) {
        int sc1 = score1.getScore();
        int sc2 = score2.getScore();

        if (sc1 > sc2) return -1;
        if (sc1 < sc2) return 1;
        return 0;
    }
}
