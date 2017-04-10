package etruaruta.models;

/**
 * This interface is used to simplify the collision algorithm
 * by enabling all objects that implement this interface use
 * the same methods for collision detections.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public interface IObject {

    /**
     *
     * @return calculated X position in the cartesian plane
     */
    int calcXPos();

    /**
     *
     * @return calculated Y position in the cartesian plane
     */
    int calcYPos();

    /**
     *
     * @return the width of the object
     */
    int getWidth();

    /**
     *
     * @return the height of the object
     */
    int getHeight();

}

