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
    
    MazeDisplay display;
    Maze currentMaze;

    @Override
    public void start(Stage primaryStage) {
        
        display = new MazeDisplay(primaryStage);
        primaryStage.show();
    }
    
    public void setCurrentMaze(Maze m){
        currentMaze = m;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
