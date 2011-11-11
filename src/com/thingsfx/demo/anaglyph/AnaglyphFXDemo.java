package com.thingsfx.demo.anaglyph;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnaglyphFXDemo extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        primaryStage.setScene(scene);

        Text text = TextBuilder.create().text("Threeeeee Deeeeee!").
                font(Font.font("Tahoma", 80)).build();
        text.setFill(new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT,
                     new Stop[]{new Stop(0, Color.AQUA), new Stop(0.5f, Color.RED)}));

        text.setStrokeWidth(1);
        text.setStroke(Color.BLACK);
        
        text.setX(10);
        text.setY(100);
        root.getChildren().add(text);
        
        Group circles = createCircles();
        root.getChildren().add(circles);
        
        primaryStage.show();
    }
    
    private Group createCircles() {
        
        Group circles = new Group();
        for (int i = 0; i < 20; i++) {
            Circle3D circle = new Circle3D();
            circles.getChildren().add(circle);
        }
        circles.setDepthTest(DepthTest.ENABLE);
        
        Timeline timeline = new Timeline();
        for (Node circle: circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                    new KeyValue(circle.translateXProperty(), Math.random() * 800),
                    new KeyValue(circle.translateYProperty(), Math.random() * 600)
                ),
                new KeyFrame(new Duration(10000),
                        new KeyValue(circle.translateXProperty(), Math.random() * 800),
                        new KeyValue(circle.translateYProperty(), Math.random() * 600),

                        new KeyValue(circle.scaleXProperty(), 2.),
                        new KeyValue(circle.scaleYProperty(), 2.),
                        new KeyValue(circle.scaleZProperty(), 2.)    
                ),
                new KeyFrame(new Duration(20000),
                        new KeyValue(circle.translateXProperty(), Math.random() * 800),
                        new KeyValue(circle.translateYProperty(), Math.random() * 600)
//                        
//                        new KeyValue(circle.scaleXProperty(), 3.),
//                        new KeyValue(circle.scaleYProperty(), 3.),
//                        new KeyValue(circle.scaleZProperty(), 3.)
                )
            );
        }
        timeline.setAutoReverse(true);
        timeline.play();
        return circles;
    }
}
