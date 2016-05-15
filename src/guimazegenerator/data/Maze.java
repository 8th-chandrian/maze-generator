/*
 * 
 */
package guimazegenerator.data;

/**
 *
 * @author Noah
 */
public class Maze {
    
    int mazeWidth;
    int mazeHeight;
    MazeCell[][] mazeGrid;
    
    public Maze(int width, int height){
        mazeWidth = width;
        mazeHeight = height;
        mazeGrid = new MazeCell[mazeWidth][mazeHeight];
        
        //Initialize the newly-created maze grid
        for(int i = 0; i < mazeWidth; i++){
            for(int j = 0; j < mazeHeight; j++){
                mazeGrid[i][j] = new MazeCell(i, j);
            }
        }
    }
    
    /**
     * Generates a random maze and sets the solution booleans correctly
     */
    public void generateMaze() {
        
        processCell(mazeGrid[0][0], 0);
        
        for(int i = 0; i < mazeWidth; i++){
            for(int j = 0; j < mazeHeight; j++){
                mazeGrid[i][j].setVisited(false);
            }
        }
        
        solveMaze(mazeGrid[0][0]);
    }
    
    /**
     * Method which solves the maze using recursive backtracking. Note that this outcome could probably
     * be achieved with modifications to processCell, but I suck at recursive backtracking so I decided
     * to practice that instead.
     * @param c
     * @param mazeGrid
     * @return 
     */
    public boolean solveMaze(MazeCell c){
        c.setSolution(true);
        c.setVisited(true);
        if(c.getX() == mazeWidth - 1 && c.getY() == mazeHeight - 1)
            return true;
        
        //Note: need to check if cell has been visited before calling solveMaze on it
        if((!c.hasLeftWall() && !mazeGrid[c.getX() - 1][c.getY()].isVisited() && solveMaze(mazeGrid[c.getX() - 1][c.getY()])) ||
            (!c.hasRightWall() && !mazeGrid[c.getX() + 1][c.getY()].isVisited() && solveMaze(mazeGrid[c.getX() + 1][c.getY()])) ||
            (!c.hasTopWall() && !mazeGrid[c.getX()][c.getY() - 1].isVisited() &&solveMaze(mazeGrid[c.getX()][c.getY() - 1])) ||
            (!c.hasBottomWall() && !mazeGrid[c.getX()][c.getY() + 1].isVisited() && solveMaze(mazeGrid[c.getX()][c.getY() + 1])))
            return true;
        else{
            c.setSolution(false);
            return false;
        }
    }
    
    /**
     * Recursive method to process all cells in the array and generate the maze.
     * @param toProcess
     * @param numVisitedCells
     */
    public int processCell(MazeCell toProcess, int numVisitedCells){
        toProcess.setVisited(true);
        numVisitedCells++;
        if(numVisitedCells == mazeWidth * mazeHeight)
            return numVisitedCells;
        
        //Increment numVisitedCells as we have just visited the current cell
        while(hasUnvisitedNeighbors(toProcess)){
            MazeCell nextCell = getRandomUnvisitedNeighbor(toProcess);
            numVisitedCells = processCell(nextCell, numVisitedCells);
        }
        return numVisitedCells;
    }
    
    /**
     * Checks whether or not the given cell has unvisited neighbors.
     * @param c
     * @return 
     */
    public boolean hasUnvisitedNeighbors(MazeCell c){
        if(!(c.getX() - 1 < 0) && !mazeGrid[c.getX() - 1][c.getY()].isVisited())
            return true;
        if(!(c.getX() + 1 >= mazeWidth) && !mazeGrid[c.getX() + 1][c.getY()].isVisited())
            return true;
        if(!(c.getY() - 1 < 0) && !mazeGrid[c.getX()][c.getY() - 1].isVisited())
            return true;
        if(!(c.getY() + 1 >= mazeHeight) && !mazeGrid[c.getX()][c.getY() + 1].isVisited())
            return true;
        
        return false;
    }
    
    /**
     * Gets a random unvisited neighbor of the given cell and removes the walls separating it
     * and the given cell. Note that this method should not result in an infinite loop, since 
     * it will only be called when the given cell has already been shown to have unvisited neighbors.
     * @param c
     * @return 
     */
    public MazeCell getRandomUnvisitedNeighbor(MazeCell c){
        MazeCell toReturn = new MazeCell(-1, -1);
        toReturn.setVisited(true);
        do{
            int randomInt = (int)(Math.random() * 4);
            if(randomInt == 0 && !(c.getX() - 1 < 0) && !mazeGrid[c.getX() - 1][c.getY()].isVisited()){
                toReturn = mazeGrid[c.getX() - 1][c.getY()];
                toReturn.setHasRightWall(false);
                c.setHasLeftWall(false);
            }
            else if(randomInt == 1 && !(c.getY() - 1 < 0) && !mazeGrid[c.getX()][c.getY() - 1].isVisited()){
                toReturn = mazeGrid[c.getX()][c.getY() - 1];
                toReturn.setHasBottomWall(false);
                c.setHasTopWall(false);
            }
            else if(randomInt == 2 && !(c.getX() + 1 >= mazeWidth) && !mazeGrid[c.getX() + 1][c.getY()].isVisited()){
                toReturn = mazeGrid[c.getX() + 1][c.getY()];
                toReturn.setHasLeftWall(false);
                c.setHasRightWall(false);
            }
            else if(randomInt == 3 && !(c.getY() + 1 >= mazeHeight) && !mazeGrid[c.getX()][c.getY() + 1].isVisited()){
                toReturn = mazeGrid[c.getX()][c.getY() + 1];
                toReturn.setHasTopWall(false);
                c.setHasBottomWall(false);
            }
        }
        while(toReturn.getX() == -1 && toReturn.getY() == -1);
        
        return toReturn;
    }
}
