package EtruarutaGUI;

import javafx.scene.Scene;

interface SceneInterface {
    /**
     * @param width the width of the Scene
     * @param height the height of the Scene
     * @return Scene with the specified attributes
     */
    Scene init(int width, int height);
}