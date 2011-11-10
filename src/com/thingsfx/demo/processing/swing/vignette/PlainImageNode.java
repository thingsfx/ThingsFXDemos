package com.thingsfx.demo.processing.swing.vignette;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlainImageNode extends ImageView {

    private static final String IMAGE_FILE =
            "/com/thingsfx/demo/processing/swing/vignette/castel_nuovo.jpg";
    
    public PlainImageNode() {
        super(new Image(PlainImageNode.class.getResourceAsStream(IMAGE_FILE)));
    }
}
