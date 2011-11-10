package com.thingsfx.swing.jfreechart;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class CirclesNode {
    
    private Group blendModeGroup;
    private double width;
    private double height;
    public CirclesNode(double width, double height, Paint fill) {
        
        this.width = width;
        this.height = height;
        
        Rectangle gradient = createGradient();
        Group circles = createCircles();
        createAnimation(circles);
        
        Rectangle rect =
                new Rectangle(width, height, fill);
        Group gradientGroup = new Group(rect, circles);
        blendModeGroup = new Group(gradientGroup, gradient);
        gradient.setBlendMode(BlendMode.OVERLAY);
    }

    Group getGroup() {
        return blendModeGroup;
    }
    
    private void createAnimation(Group nodes) {
        Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        for (Node circle: nodes.getChildren()) {
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0
                    new KeyValue(circle.translateXProperty(), Math.random() * 800),
                    new KeyValue(circle.translateYProperty(), Math.random() * 600)
                ),
                new KeyFrame(new Duration(20000), // set mid position at 20s
                    new KeyValue(circle.translateXProperty(), Math.random() * 800),
                    new KeyValue(circle.translateYProperty(), Math.random() * 600)
                ),
                new KeyFrame(new Duration(40000), // set another mid position at 40s
                        new KeyValue(circle.translateXProperty(), Math.random() * 800),
                        new KeyValue(circle.translateYProperty(), Math.random() * 600)
                ),
                new KeyFrame(new Duration(80000), // set end position at 80s
                        new KeyValue(circle.translateXProperty(), Math.random() * 800),
                        new KeyValue(circle.translateYProperty(), Math.random() * 600)
                )
            );
        }
        // play animation
        timeline.play();
    }
    
    private Rectangle createGradient() {
        Rectangle colors = new Rectangle(width, height,
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                                   new  Stop [] {
                                        new Stop(0, Color.web("#f8bd55")),
                                        new Stop(0.14, Color.web("#c0fe56")),
                                        new Stop(0.28, Color.web("#5dfbc1")),
                                        new Stop(0.43, Color.web("#64c2f8")),
                                        new Stop(0.57, Color.web("#be4af7")),
                                        new Stop(0.71, Color.web("#ed5fc2")),
                                        new Stop(0.85, Color.web("#ef504c")),
                                        new Stop(1, Color.web("#f2660f")),
        }));
        return colors;
    }

    private Group createCircles() {
        Group circles = new Group();
        for (int i = 0; i < 30; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStrokeWidth(4);
            circle.setEffect(new BoxBlur(10, 10, 3));
            
            circles.getChildren().add(circle);
        }
        return circles;
    }

}
