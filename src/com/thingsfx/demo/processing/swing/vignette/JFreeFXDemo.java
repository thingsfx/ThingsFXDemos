package com.thingsfx.demo.processing.swing.vignette;

import java.awt.image.BufferedImage;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import com.thingsfx.widget.swing.BufferedImageView;

public class JFreeFXDemo extends Application {

    private static final boolean CREATE_GRAIN = true;  
    
    private Random random = new Random();

    private Group imageGroup = new Group();
    private Group imageEffects = new Group();

    private void createImageGroup() {
        
        // our base image layer
        PlainImageNode imageNode = new PlainImageNode();
        
        double w = imageNode.getImage().getWidth();
        double h = imageNode.getImage().getHeight();
        
        // film grain layer
        if (CREATE_GRAIN) {
            BufferedImageView filmGrainView = createFilmGrain(w, h);
            filmGrainView.setBlendMode(BlendMode.MULTIPLY);
            filmGrainView.setOpacity(0.1);
            imageEffects.getChildren().add(filmGrainView);
        }
        
        // the vignette layer
        Rectangle vignette = createVignette(w, h);
        vignette.setBlendMode(BlendMode.MULTIPLY);
        
        imageEffects.getChildren().add(vignette);
        imageEffects.setVisible(false);
        
        imageGroup.getChildren().addAll(imageNode, imageEffects);
    }
      
    private BufferedImageView createFilmGrain(double w, double h) {
        BufferedImage bufferedImage =
                new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_ARGB);
        int scanline = bufferedImage.getHeight();
        int[] pixels = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(),
                                            bufferedImage.getHeight(),
                                            null, 0, scanline);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[scanline * y + x] = random.nextBoolean() ? 0xFFFFFFFF :
                                                                  0xFF000000;
            }
        }
        bufferedImage.setRGB(0, 0, bufferedImage.getWidth(),
                             bufferedImage.getHeight(), pixels, 0, scanline);
        return new BufferedImageView(bufferedImage);
    }
    
    private Rectangle createVignette(double w, double h) {
        
        Stop[] stops = { new Stop(0, Color.WHITE), new Stop(1, Color.BLACK) };
        RadialGradient vignetteGradient =
                new RadialGradient(0.0, 0.0, 120, 380, 500, false,
                        CycleMethod.NO_CYCLE, stops);
        return new Rectangle(w, h, vignetteGradient);        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        Group root = new Group();
        Scene scene = new Scene(root, 450, 700, Color.BLACK);
        primaryStage.setScene(scene);
        
        final VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        
        createImageGroup();
        
        final ToggleButton effectSwitch = new ToggleButton("Effects On/Off");
        effectSwitch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean enableEffect = effectSwitch.isSelected();
                imageEffects.setVisible(enableEffect);
                ColorAdjust bwFilter = new ColorAdjust();
                if (enableEffect) {
                    bwFilter.setSaturation(-1.0);
                } else {
                    bwFilter.setSaturation(0.0);
                }
                imageGroup.setEffect(bwFilter);
            }
        });
        
        vbox.getChildren().addAll(imageGroup, effectSwitch);
        root.getChildren().add(vbox);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
