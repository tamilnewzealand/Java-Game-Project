package EtruarutaGUI;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.control.Labeled;

/**
 * This class generates GUI components needed in the game
 * menu system. It can generate text, buttons, and a moving
 * planet animation background.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class GUIComponent {
	public static final String DEFAULT_FONT_FAMILY = "Perfect Dark (BRK)";
    public static final int DEFAULT_FONT_SIZE = 30;
	public static final double DEFAULT_BUTTON_WIDTH = 200;
	public static final double DEFAULT_BUTTON_HEIGHT = 50;

	/**
	 * @param text String to fill the text
	 * @param x x-coordinate of the text
	 * @param y y-coordinate of the text
	 * @param fontSize font size of the text
	 * @return Text with the specified attributes
	 */
	public static Text createText(String text, double x, double y, int fontSize) {
		Text textUI = new Text();
		textUI.setText(text);
		textUI.setX(x);
		textUI.setY(y);
		textUI.setFill(Color.WHITE);
		textUI.setTextAlignment(TextAlignment.LEFT);
		textUI.setFont(Font.font(DEFAULT_FONT_FAMILY, fontSize));
		
		return textUI;
	}
	
	/**
	 * @param text String to fill the button
	 * @param x x-coordinate of the button
	 * @param y y-coordinate of the button
	 * @return Button with the specified attributes
	 */
	public static Button createButton(String text, double x, double y) {
		return createButton(text, x, y, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, DEFAULT_FONT_SIZE);
	}
	
	/**
	 * @param text String to fill the button
	 * @param x x-coordinate of the button
	 * @param y y-coordinate of the button
	 * @param width width of the button
	 * @param height height of the button
	 * @param fontSize font size of the button
	 * @return Button with the specified attributes
	 */
	public static Button createButton(String text, double x, double y, double width, double height, int fontSize) {
		Button buttonUI = new Button();
		buttonUI.setText(text);
		buttonUI.setBackground(Background.EMPTY);
		buttonUI.setLayoutX(x);
		buttonUI.setLayoutY(y);
		buttonUI.setMinWidth(width);
		buttonUI.setMinHeight(height);
		buttonUI.setFont(Font.font(DEFAULT_FONT_FAMILY, fontSize));
        buttonUI.setTextFill(Paint.valueOf("#FFFFFF"));
		
		return buttonUI;
	}

    /**
	 * Draws the orbiting planet around star animation,
     * which is used as part of the background in menu
     * scenes.
     *
     * @param gc GraphicsContext to draw animation onto
     * @return GraphicsContext with the animation drawn
     */
	public static GraphicsContext createAnimationBackground(GraphicsContext gc) {
        Image etruaruta = new Image( "etruaruta.png" );
        Image star = new Image( "star.png" );
        Image space = new Image( "space.png" );
        gc.drawImage( space, 0, 0 );

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                // Clear the canvas
                gc.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( etruaruta, x, y );
                gc.drawImage( star, 196, 196 );
            }
        }.start();

        return gc;
    }
}