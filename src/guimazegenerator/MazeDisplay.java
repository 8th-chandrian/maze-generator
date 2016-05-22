/*
 * 
 */
package guimazegenerator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    public static final double PREFERRED_DISPLAY_WIDTH = 800;
    
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
        
        toolbar.getChildren().addAll(generateButton, widthPane, heightPane, zoomInButton, zoomOutButton, solutionCheckBox);
        appPane.setTop(toolbar);
        appPane.setCenter(display);
    }
    
    /**
     * Initializes the handlers for the toolbar controls.
     */
    public void initHandlers(){
        solutionCheckBox.setOnAction(e -> {
            app.getController().handleSolutionChecked(solutionCheckBox.isSelected());
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
}
