package EtruarutaGUI;

import javafx.scene.Scene;

/**
 * Every class that generates a scene implements this
 * interface. Used to ensure consistency among scene
 * generation classes.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

interface SceneInterface {
    /**
     * @param width the width of the Scene
     * @param height the height of the Scene
     * @return Scene with the specified attributes
     */
    Scene init(int width, int height);
}