package sample;

import javafx.application.Application;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.input.*;
import java.util.EventObject;
import java.lang.Object;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import javafx.animation.AnimationTimer;


public class Main extends Application {

    private void reset(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Freedom to Etruaruta" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 1024, 768 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill( Color.WHITE );
        gc.setStroke( Color.WHITE );
        gc.setLineWidth(2);
        Font headingFont = Font.font( "Kavivanar", 54 );
        Font othersFont = Font.font( "Kavivanar", 24 );
        gc.setTextAlign(TextAlignment.CENTER);

        Image etruaruta = new Image( "etruaruta.png" );
        Image star = new Image( "star.png" );
        Image space = new Image( "space.png" );
        Image pointer = new Image( "pointRight.png" );

        final long startNanoTime = System.nanoTime();

//        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent t) {
//                            reset(canvas, Color.BLUE);
//                    }
//                });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                // Clear the canvas
                gc.clearRect(0, 0, 1024,768);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( etruaruta, x, y );
                gc.drawImage( star, 196, 196 );
                gc.setFont( headingFont );
                gc.fillText( "Freedom to Etruaruta!", 512, 80 );
                gc.strokeText( "Freedom to Etruaruta!", 512, 80 );
                gc.setFont( othersFont );
                gc.fillText("Play Now (1P)", 512, 260);
                gc.drawImage(pointer, 395, 235);
                gc.fillText("Multiplayer (2P)", 512, 300);
                gc.fillText("Multiplayer (3P)", 512, 340);
                gc.fillText("Multiplayer (4P)", 512, 380);
                gc.fillText("Help", 512, 420);
                gc.fillText("Demo", 512, 460);
                gc.fillText("High Scores", 512, 500);
                gc.fillText("Settings", 512, 540);
                gc.fillText("Exit", 512, 580);
            }
        }.start();

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
