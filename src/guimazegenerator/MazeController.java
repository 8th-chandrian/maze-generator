/*
 * 
 */
package guimazegenerator;

import guimazegenerator.data.Maze;
import javafx.scene.control.TextField;

/**
 *
 * @author Noah
 */
public class MazeController {

    public static final int MAX_MAZE_SIZE = 200;
    
    GUIMazeGenerator app;
    
    MazeController(GUIMazeGenerator app) {
        this.app = app;
    }

    public void handleGenerateButtonPress() {
        int width = app.getDisplay().getMazeWidth();
        int height = app.getDisplay().getMazeHeight();
        Maze m = new Maze(width, height);
        m.generateMaze();
        app.setCurrentMaze(m);
        app.getDisplay().displayCurrentMaze(width, height);
    }
    
    /**
     * Disables generate button and sets text color to red if the following parameters are not met:
     *      Text only consists of digits
     *      Text is not empty
     *      Texts parses to integer between 2 and 999
     * .
     * @param text
     * @param textField
     */
    public void handleTextEdited(TextField textField, String text) {
        if(text.isEmpty()){
            app.getDisplay().invalidText(textField);
        }
        else if(!text.matches("[0-9]+")){
            app.getDisplay().invalidText(textField);
        }
        else if(Integer.parseInt(text) > MAX_MAZE_SIZE || Integer.parseInt(text) < 2){
            app.getDisplay().invalidText(textField);
        }
        else{
            app.getDisplay().validText(textField);
        }
    }
}
