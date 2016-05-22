/*
 * 
 */
package guimazegenerator;

import javafx.scene.control.TextField;

/**
 *
 * @author Noah
 */
public class MazeController {

    GUIMazeGenerator app;
    
    MazeController(GUIMazeGenerator app) {
        this.app = app;
    }

    public void handleSolutionChecked(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleGenerateButtonPress() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleZoomInPress() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleZoomOutPress() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        else if(Integer.parseInt(text) > 999 || Integer.parseInt(text) < 2){
            app.getDisplay().invalidText(textField);
        }
        else{
            app.getDisplay().validText(textField);
        }
    }
    
}
