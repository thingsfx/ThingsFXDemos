package com.thingsfx.swing.jfreechart;

import java.awt.Dimension;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.thingsfx.widget.swing.SwingFX;
import com.thingsfx.widget.swing.SwingView;

public class JFreeFXDemo {
    
    private SwingView swingViewChart1;
    private SwingView swingViewChart2;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingFX.init();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                JFreeFXDemo demo = new JFreeFXDemo();
                demo.initAndShowGUI();
            }
        });
    }

    protected void initAndShowGUI() {
        JFrame frame = new JFrame("JFreeFXDemo - JFreeChart in JavaFX");
        
        frame.setMinimumSize(new Dimension(750, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private void initFX(JFXPanel fxPanel) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800, Color.BLACK);
        fxPanel.setScene(scene);
        
        CirclesNode circles =
                new CirclesNode(scene.getHeight(), scene.getWidth(), Color.BLACK);
        root.getChildren().add(circles.getGroup());
        
        final VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                final JFreeChartPanel jfreeChartPanel = new JFreeChartPanel();
                jfreeChartPanel.initComponents();
                jfreeChartPanel.setSize(jfreeChartPanel.getPreferredSize());
                jfreeChartPanel.setVisible(true);
                
                final JFreeChartPanel2 jfreeChartPanel2 = new JFreeChartPanel2();
                jfreeChartPanel2.initComponents();
                jfreeChartPanel2.setSize(jfreeChartPanel.getPreferredSize());
                jfreeChartPanel2.setVisible(true);
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        
                        final Group chartGroup = new Group();
                        
                        swingViewChart1 = new SwingView(jfreeChartPanel);
                        swingViewChart2 = new SwingView(jfreeChartPanel2);

                        swingViewChart2.setLayoutX(swingViewChart1.getLayoutX());
                        swingViewChart2.setLayoutY(swingViewChart1.getLayoutY());
                        
                        swingViewChart2.setOpacity(0.0);
                        
                        chartGroup.getChildren().add(swingViewChart1);
                        chartGroup.getChildren().add(swingViewChart2);
                        
                        vbox.getChildren().add(chartGroup);
                        
                        final Button nextChart =
                                new Button("Start very silly animation");
                        nextChart.setOnAction(new EventHandler<ActionEvent>() {
                            
                            @Override
                            public void handle(ActionEvent event) {

                                nextChart.setText("Animating Charts!");
                                FadeTransition ft =
                                        new FadeTransition(Duration.millis(5000),
                                                           swingViewChart1);
                                ft.setFromValue(1.0);
                                ft.setToValue(0.0);
                                ft.setCycleCount(Timeline.INDEFINITE);
                                ft.setAutoReverse(true);
                                ft.play();

                                ft = new FadeTransition(Duration.millis(5000),
                                                           swingViewChart2);
                                ft.setFromValue(0.0);
                                ft.setToValue(1.0);
                                ft.setCycleCount(Timeline.INDEFINITE);
                                ft.setAutoReverse(true);
                                ft.play();
                            }
                        });
                        nextChart.setPrefSize(200, 20);
                        vbox.getChildren().addAll(nextChart);
                    }
                });
            }
        });
        
        root.getChildren().add(vbox);
        
    }
}
