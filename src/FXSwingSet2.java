import java.awt.Dimension;
import java.awt.Graphics;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.thingsfx.widget.swing.SwingFX;
import com.thingsfx.widget.swing.SwingView;


public class FXSwingSet2 extends Application {
    
    private SwingView demo1View;
    private SwingView demo2View;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	launch(args);
    }

    protected void initFX(Stage stage) {
        final Group root = new Group();
        Scene scene = new Scene(root, 650, 550, Color.BLACK);
        stage.setScene(scene);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

            	final InternalFrameDemo demo1 = new InternalFrameDemo(null);
            	demo1.getDemoPanel().setPreferredSize(new Dimension(650, 550));
            	final ButtonDemo demo2 = new ButtonDemo(null);
            	demo2.getDemoPanel().setPreferredSize(new Dimension(650, 550));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        
                    	TabPane tabPane = new TabPane();

                        demo1View = new SwingView(demo1.getDemoPanel());
                        Tab tab1 = new Tab("Internal Frames");
                        tab1.setContent(demo1View);
                        tabPane.getTabs().add(tab1);

                        Node n = createJavaFXNode();
//                        demo2View = new SwingView(demo2.getDemoPanel());
                        Tab tab2 = new Tab("JavaFX");
                        tab2.setContent(n);
                        tabPane.getTabs().add(tab2);

                        Node n2 = createCustomSwingNode();
//                      demo2View = new SwingView(demo2.getDemoPanel());
                        Tab tab3 = new Tab("Custom");
                        tab3.setContent(n2);
                        tabPane.getTabs().add(tab3);

                        root.getChildren().add(tabPane);
                    }

                });
            }
        });
        
        
    }

	private Node createJavaFXNode() {
		BorderPane layout = new BorderPane();
		layout.setTop(new Rectangle(200, 50, Color.DARKCYAN));
		layout.setBottom(new Rectangle(200, 50, Color.DARKCYAN));
		layout.setCenter(new Rectangle(100, 100, Color.MEDIUMAQUAMARINE));
		layout.setLeft(new Rectangle(50, 100, Color.DARKTURQUOISE));
		layout.setRight(new Rectangle(50, 100, Color.DARKTURQUOISE));

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699");

		Button buttonCurrent = new Button("Current");
		buttonCurrent.setPrefSize(100, 20);

		Button buttonProjected = new Button("Projected");
		buttonProjected.setPrefSize(100, 20);
		hbox.getChildren().addAll(buttonCurrent, buttonProjected);

		layout.setTop(hbox);
 
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setSpacing(10);

		Text title = new Text("Data");
		title.setFont(Font.font("Amble CN", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);

		Text options[] = new Text[] {
		                 new Text("  Sales"),
		                 new Text("  Marketing"),
		                 new Text("  Distribution"),
		                 new Text("  Costs")};

		for (int i=0; i<4; i++) {
		     vbox.getChildren().add(options[i]);
		}

		layout.setLeft(vbox);       // Add to BorderPane from Example 1-2
		return layout;
	}

	private Node createCustomSwingNode() {
		final JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(600, 500));
		p.setSize(new Dimension(600, 500));
		SwingView v = new SwingView(p);
		Thread t = new Thread() {
			public void run() {
				while (true) {
					int w = p.getWidth();
					int h = p.getHeight();
					System.err.println("panel size: " + w + ", " + h);
					if (w == 0 || h == 0) {
						System.err.println("zero size");
						continue;
					}
				Graphics g = p.getGraphics();
				
				System.err.println("graphics: " + g);
				int x = random(w);
				int y = random(h);
				int width = random(w - x);
				int height = random(h - y);
				g.setColor(new java.awt.Color(random(0x1000000)));
				System.err.println("color: " + g.getColor());
				System.err.println("rectangle: " + x + ", " + y + ", " + width + ", " + height);
				if (random(2) == 0) {
					g.create().fillRoundRect(x, y, width, height, 10, 10);
				} else {
  				    g.fillRect(x, y, width, height);
				}
				g.dispose();
				try {Thread.sleep(500);} catch (InterruptedException ex) { return; }
				}
			}
			private int random(int max) {
				return (int) (Math.random() * max);
			}
		};
		t.start();
		return v;
	}

	@Override
	public void start(Stage stage) throws Exception {
        SwingFX.init();
        initFX(stage);
        stage.show();
	}

}
