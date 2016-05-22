/*
 * 
 */
package guimazegenerator;

import guimazegenerator.data.Maze;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Noah
 */
public class GUIMazeGenerator extends Application {
    
    private MazeDisplay display;
    private MazeController control;
    private Maze currentMaze;

    @Override
    public void start(Stage primaryStage) {
        display = new MazeDisplay(primaryStage, this);
        control = new MazeController(this);
        primaryStage.show();
    }
    
    public void setCurrentMaze(Maze m){
        currentMaze = m;
    }
    
    public MazeDisplay getDisplay(){
        return display;
    }
    
    public MazeController getController(){
        return control;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
