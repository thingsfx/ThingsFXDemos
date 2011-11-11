package com.thingsfx.demo.anaglyph;

import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Circle3D extends Group {

    private Circle rightCircle;
    private Circle leftCircle;
    
    public Circle3D() {
        leftCircle = new Circle(25);
        leftCircle.setFill(Color.RED);
        leftCircle.setSmooth(true);
        leftCircle.setOpacity(0.5);
        leftCircle.setLayoutX(0.0);
        leftCircle.setLayoutY(0.0);
        
        rightCircle = new Circle(25);
        rightCircle.setFill(Color.CYAN);
        rightCircle.setSmooth(true);
        rightCircle.setOpacity(0.5);
        rightCircle.setLayoutX(5.);
        rightCircle.setLayoutY(0.);
                
        getChildren().add(leftCircle);
        getChildren().add(rightCircle);
        
        setEffect(new BoxBlur(1, 0, 5));
    }
}
