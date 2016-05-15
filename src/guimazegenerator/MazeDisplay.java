/*
 * 
 */
package guimazegenerator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class MazeDisplay {
    
    public static final double PREFERRED_DISPLAY_HEIGHT = 600;
    public static final double PREFERRED_DISPLAY_WIDTH = 600;
    
    //The requisite panes for the overall app
    BorderPane appPane;
    Pane display;
    HBox toolbar;
    
    //The subpanes and labels
    HBox widthPane;
    HBox heightPane;
    Label widthLabel;
    Label heightLabel;
    
    //The controls for the app
    CheckBox solutionCheckBox;
    Button generateButton;
    Button zoomInButton;
    Button zoomOutButton;
    TextField mazeWidth;
    TextField mazeHeight;
    
    //The controller class
    MazeController control;

    MazeDisplay(Stage primaryStage) {
        initDisplay();
        initHandlers();
        
        Scene primaryScene = new Scene(appPane);
        primaryStage.setScene(primaryScene);
    }
    
    /**
     * Initializes the maze display.
     */
    public void initDisplay(){
        
        //Initialize the controller class
        control = new MazeController(this);
        
        //Initialize the display panes
        appPane = new BorderPane();
        display = new Pane();
        display.setMinHeight(PREFERRED_DISPLAY_HEIGHT);
        display.setMinWidth(PREFERRED_DISPLAY_WIDTH);
        toolbar = new HBox();
        
        //Initialize the subpanes and labels
        widthPane = new HBox();
        heightPane = new HBox();
        widthLabel = new Label("Width:");
        heightLabel = new Label("Height:");
        
        //Initialize the controls and set their handlers
        solutionCheckBox = new CheckBox();
        solutionCheckBox.setSelected(false);
        generateButton = new Button("Generate Maze");
        zoomInButton = new Button("Zoom In");
        zoomOutButton = new Button("Zoom Out");
        mazeWidth = new TextField();
        mazeHeight = new TextField();
        
        widthPane.getChildren().addAll(widthLabel, mazeWidth);
        heightPane.getChildren().addAll(heightLabel, mazeHeight);
        
        toolbar.getChildren().addAll(generateButton, widthPane, heightPane, zoomInButton, zoomOutButton, solutionCheckBox);
        appPane.setTop(toolbar);
        appPane.setCenter(display);
    }
    
    /**
     * Initializes the handlers for the toolbar controls.
     */
    public void initHandlers(){
        solutionCheckBox.setOnAction(e -> {
            control.handleSolutionChecked(solutionCheckBox.isSelected());
        });
        
        generateButton.setOnAction(e -> {
           control.handleGenerateButtonPress(); 
        });
        
        zoomInButton.setOnAction(e -> {
            control.handleZoomInPress();
        });
        
        zoomOutButton.setOnAction(e -> {
           control.handleZoomOutPress(); 
        });
    }
    
}
