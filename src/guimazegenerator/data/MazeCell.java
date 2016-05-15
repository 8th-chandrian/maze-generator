/*
 * 
 */
package guimazegenerator.data;

/**
 *
 * @author Noah
 */
public class MazeCell {
    
    private boolean hasRightWall;
    private boolean hasTopWall;
    private boolean hasLeftWall;
    private boolean hasBottomWall;
    
    private boolean visited;
    private boolean solution;
    
    private int cellX;
    private int cellY;

    public MazeCell(int x, int y) {
        hasRightWall = true;
        hasTopWall = true;
        hasLeftWall = true;
        hasBottomWall = true;
        visited = false;
        solution = false;
        cellX = x;
        cellY = y;
    }
    
    public boolean getSolution(){
        return solution;
    }
    
    public void setSolution(boolean b){
        solution = b;
    }
    
    public int getX(){
        return cellX;
    }
    
    public int getY(){
        return cellY;
    }

    public boolean hasRightWall() {
        return hasRightWall;
    }

    public void setHasRightWall(boolean hasRightWall) {
        this.hasRightWall = hasRightWall;
    }

    public boolean hasTopWall() {
        return hasTopWall;
    }

    public void setHasTopWall(boolean hasTopWall) {
        this.hasTopWall = hasTopWall;
    }

    public boolean hasLeftWall() {
        return hasLeftWall;
    }

    public void setHasLeftWall(boolean hasLeftWall) {
        this.hasLeftWall = hasLeftWall;
    }

    public boolean hasBottomWall() {
        return hasBottomWall;
    }

    public void setHasBottomWall(boolean hasBottomWall) {
        this.hasBottomWall = hasBottomWall;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
}
