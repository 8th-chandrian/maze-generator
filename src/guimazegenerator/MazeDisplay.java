/*
 * 
 */
package guimazegenerator;

import guimazegenerator.data.MazeCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class MazeDisplay {
    
    public static final double PREFERRED_DISPLAY_HEIGHT = 800;
    public static final double PREFERRED_DISPLAY_WIDTH = 800;
    public static final double DISPLAY_BUFFER = 1.1;
    public static final Color SOLUTION_COLOR = Color.CHARTREUSE;
    
    static final String WIDTH_TEXT_CLASS = "width_text";
    static final String HEIGHT_TEXT_CLASS = "height_text";
    
    //The requisite panes for the overall app
    BorderPane appPane;
    Pane display;
    HBox toolbar;
    
    //The subpanes and labels
    HBox widthPane;
    HBox heightPane;
    Label widthLabel;
    Label heightLabel;
    Label solutionLabel;
    
    //The controls for the app
    CheckBox solutionCheckBox;
    Button generateButton;
    Button zoomInButton;
    Button zoomOutButton;
    TextField widthText;
    TextField heightText;
    
    //Flags keeping track of text validity
    boolean widthTextIsValid;
    boolean heightTextIsValid;
    
    //Flag to keep track of whether or not maze is displayed
    boolean mazeIsDisplayed;
    
    //Flag to keep track of whether or not solution is displayed
    boolean solutionIsDisplayed;
    
    //The controller class
    GUIMazeGenerator app;

    MazeDisplay(Stage primaryStage, GUIMazeGenerator app) {
        this.app = app;
        
        initDisplay();
        initHandlers();
        
        Scene primaryScene = new Scene(appPane);
        primaryStage.setScene(primaryScene);
    }
    
    /**
     * Initializes the maze display.
     */
    public void initDisplay(){
        
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
        solutionLabel = new Label("Display solution");
        
        //Initialize the controls and set their handlers
        solutionCheckBox = new CheckBox();
        solutionCheckBox.setSelected(false);  
        generateButton = new Button("Generate Maze");
        zoomInButton = new Button("Zoom In");
        zoomOutButton = new Button("Zoom Out");
        widthText = new TextField();
        heightText = new TextField();
        
        //Disable zooming and solution controls, since no maze exists
        generateButton.setDisable(true);
        solutionCheckBox.setDisable(true);
        zoomInButton.setDisable(true);
        zoomOutButton.setDisable(true);
        
        widthPane.getChildren().addAll(widthLabel, widthText);
        heightPane.getChildren().addAll(heightLabel, heightText);
        widthTextIsValid = false;
        heightTextIsValid = false;
        mazeIsDisplayed = false;
        solutionIsDisplayed = false;
        
        toolbar.getChildren().addAll(generateButton, widthPane, heightPane, zoomInButton, 
                zoomOutButton, solutionLabel, solutionCheckBox);
        appPane.setTop(toolbar);
        appPane.setCenter(display);
    }
    
    /**
     * Initializes the handlers for the toolbar controls.
     */
    public void initHandlers(){
        solutionCheckBox.setOnAction(e -> {
            solutionIsDisplayed = solutionCheckBox.isSelected();
            
            /* Note: when displayCurrentMaze is called, currentMaze will have already been initialized
            because solutionCheckBox will not be enabled until a maze is displayed. */
            displayCurrentMaze(app.getCurrentMaze().getWidth(), app.getCurrentMaze().getHeight());
        });
        
        generateButton.setOnAction(e -> {
           app.getController().handleGenerateButtonPress(); 
        });
        
        zoomInButton.setOnAction(e -> {
            app.getController().handleZoomInPress();
        });
        
        zoomOutButton.setOnAction(e -> {
           app.getController().handleZoomOutPress(); 
        });
        
        widthText.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                display.requestFocus();
            }
        });
        
        heightText.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER))
                display.requestFocus();
        });
        
        widthText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue){
                app.getController().handleTextEdited(widthText, newValue);
            }
        });
        
        heightText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue){
                app.getController().handleTextEdited(heightText, newValue);
            }
        });
    }
    
    /**
     * Disables generate button and sets text color to red if text is invalid.
     * @param text 
     */
    public void invalidText(TextField text){
        if(text.equals(widthText)){
            widthText.setStyle("-fx-text-fill: red");
            widthTextIsValid = false;
        }
        else if(text.equals(heightText)){
            heightText.setStyle("-fx-text-fill: red");
            heightTextIsValid = false;
        }
        generateButton.setDisable(true);
    }
    
    /**
     * Enables generate button and sets text color to black if text is valid.
     * @param text 
     */
    public void validText(TextField text){
        if(text.equals(widthText)){
            widthText.setStyle("-fx-text-fill: black");
            widthTextIsValid = true;
        }
        else if(text.equals(heightText)){
            heightText.setStyle("-fx-text-fill: black");
            heightTextIsValid = true;
        }
        
        //If both text fields are valid, enable generate button
        if(widthTextIsValid && heightTextIsValid)
            generateButton.setDisable(false);
    }
    
    public int getMazeWidth(){
        return Integer.parseInt(widthText.getText());
    }
    
    public int getMazeHeight(){
        return Integer.parseInt(heightText.getText());
    }

    /**
     * Displays the current maze, centered in the middle of the display frame. Calculates cell size
     * based on the number of cells.
     * @param width
     * @param height 
     */
    public void displayCurrentMaze(int width, int height) {
        if(mazeIsDisplayed)
            display.getChildren().clear();  // First clear all children from display pane, if another maze is currently displayed
        mazeIsDisplayed = true;
        if(solutionCheckBox.isDisable())
            solutionCheckBox.setDisable(false);
        
        // By dividing by slightly more than width and height, cells are smaller than necessary so that
        // there will be space on all sides between the edge of the display and the maze
        double cellWidth = (PREFERRED_DISPLAY_WIDTH) / (DISPLAY_BUFFER * width);
        double cellHeight = (PREFERRED_DISPLAY_HEIGHT) / (DISPLAY_BUFFER * height);
        
        //Set the cell size to be the minimum of the cell width and height
        double cellSize = 0;
        if(cellWidth < cellHeight)
            cellSize = cellWidth;
        else
            cellSize = cellHeight;
        
        double widthOffset = (PREFERRED_DISPLAY_WIDTH - (cellSize * width)) / 2;
        double heightOffset = (PREFERRED_DISPLAY_HEIGHT - (cellSize * height)) / 2;
        
        // Display all cells in display pane
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(solutionIsDisplayed && app.getCurrentMaze().getCell(i, j).getSolution())
                    display.getChildren().add(displaySolutionCell(app.getCurrentMaze().getCell(i, j), widthOffset + (i * cellSize), 
                        heightOffset + (j * cellSize), cellSize));
                else
                    display.getChildren().add(displayCell(app.getCurrentMaze().getCell(i, j), widthOffset + (i * cellSize), 
                        heightOffset + (j * cellSize), cellSize));
            }
        }    
    }
    
    /**
     * Creates a group of lines which can then be added to the display pane for a given cell.
     * @param c
     * @param startX
     * @param startY
     * @param cellSize
     * @return 
     */
    public Group displayCell(MazeCell c, double startX, double startY, double cellSize){
        Group cellGroup = new Group();
        if(c.hasTopWall())
            cellGroup.getChildren().add(new Line(startX, startY, startX + cellSize, startY));
        if(c.hasBottomWall())
            cellGroup.getChildren().add(new Line(startX, startY + cellSize, startX + cellSize, startY + cellSize));
        if(c.hasLeftWall())
            cellGroup.getChildren().add(new Line(startX, startY, startX, startY + cellSize));
        if(c.hasRightWall())
            cellGroup.getChildren().add(new Line(startX + cellSize, startY, startX + cellSize, startY + cellSize));
        return cellGroup;
    }
    
    /**
     * Creates a group of lines which can then be added to the display pane for a given cell.
     * Additionally, adds a colored square to the group so that the maze's solution is clear to the user.
     * @param c
     * @param startX
     * @param startY
     * @param cellSize
     * @return 
     */
    public Group displaySolutionCell(MazeCell c, double startX, double startY, double cellSize){
        Group cellGroup = new Group();
        if(c.hasTopWall())
            cellGroup.getChildren().add(new Line(startX, startY, startX + cellSize, startY));
        if(c.hasBottomWall())
            cellGroup.getChildren().add(new Line(startX, startY + cellSize, startX + cellSize, startY + cellSize));
        if(c.hasLeftWall())
            cellGroup.getChildren().add(new Line(startX, startY, startX, startY + cellSize));
        if(c.hasRightWall())
            cellGroup.getChildren().add(new Line(startX + cellSize, startY, startX + cellSize, startY + cellSize));
        
        Rectangle r = new Rectangle(startX, startY, cellSize, cellSize);
        r.setStroke(Color.TRANSPARENT);
        r.setFill(SOLUTION_COLOR);
        cellGroup.getChildren().add(r);
        
        return cellGroup;
    }
}
