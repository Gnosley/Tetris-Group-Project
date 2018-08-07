import java.util.ArrayList;
import java.util.Arrays;
/**
 * Board class for Tetris implimentation.
 *
 */
public class Board{
    private Block[][] gameBoard = new Block[24][10];
    private long numLinesCleared;
    private ArrayList<Integer> rowsToClear = new ArrayList<>();
    private Block[][] preClearedBoard = new Block[24][10];


    /**
     * Update method for Board. Takes in a tetromino object and adds it to the
     * current board.
     * @param currentTetromino Tetromino object to add to the board.
     */
    public void updateBoard(Tetromino currentTetromino){
        Block[] tetrominoBlockArray = currentTetromino.getBlockArray();
        for(int b = 0; b < tetrominoBlockArray.length; b++){
            this.gameBoard[tetrominoBlockArray[b].getYPosition()][tetrominoBlockArray[b].getXPosition()] = tetrominoBlockArray[b];
        }



        //after new piece played, loop checking board for any rows to clear until done
        boolean doneBoardCheck = false;
        this.preClearedBoard = copyBoard(gameBoard);
        do{
            doneBoardCheck = this.checkBoard();
        }
        while(!doneBoardCheck);
    }

    /**
     * Getter method for the current gameBoard. Returns copy to avoid privacy leak
     * @return copy of the current Board
     */
    public Block[][] getCurrentBoard(){
        Board copyBoard = new Board();
        copyBoard.gameBoard = copyBoard(this.gameBoard);
        return copyBoard.gameBoard;
    }

    /**
     * Getter method for the current gameBoard. Returns copy to avoid privacy leak
     * @return copy of the current Board
     */
//    public Block[][] getPreClearedBoard(){
//        Board copyBoard = new Board();
//        copyBoard.preClearedBoard = copyBoard(this.preClearedBoard);
//        return copyBoard.preClearedBoard;
//    }
    public Block[][] getPreClearedBoard(){
        Board copyBoard = new Board();
        copyBoard.preClearedBoard = copyBoard(this.preClearedBoard);
        return copyBoard.preClearedBoard;
    }

    /**
     * Creates copy of specified gameBoard
     * @param  gameBoard gameBoard to be copied (Block[][])
     * @return           copy of gameBoard attribute (Block[][])
     */
    private Block[][] copyBoard(Block[][] gameBoard){
        Board tempBoard = new Board();
        for(int row = 0; row < tempBoard.gameBoard.length; row++){
            for(int col = 0; col < tempBoard.gameBoard[0].length; col++){
                tempBoard.gameBoard[row][col] = gameBoard[row][col];
            }
        }
        return tempBoard.gameBoard;
    }

    /**
     * checkMove method. Itertaes through tetromino block array and checks
     *  current board for collisions. Returns boolean to board indicating if it
     *  is possible for the tetromino can be played in the location.
     *
     * @param  movedTetromino position of the moved tetromino
     * @return                boolean indicating if new position is clear
     */
    public boolean checkMove(Tetromino movedTetromino) {
        boolean canMove = false;
        boolean[] checkArray = new boolean[4];
        Block[] tetrominoBlockArray = movedTetromino.getBlockArray();
        for(int b = 0; b < tetrominoBlockArray.length; b++){
            int blockYPos = tetrominoBlockArray[b].getYPosition();
            int blockXPos = tetrominoBlockArray[b].getXPosition();
            //checks blocks are within bounds of board
            if((blockYPos < 24) && (blockXPos >= 0 && blockXPos <= 9)) {
                //checks block positions are not currently occupied
                if(gameBoard[blockYPos][blockXPos] == null){
                    checkArray[b] = true; //updates checkArray with true if move for the specific block is valid.
                }
            }
            else{
                //one of the blocks is outside of the bounds of the board
                return canMove = false;
            }
        }
        for(int i = 0; i < checkArray.length; i++){
            if(!checkArray[i]){ //check for invalid move
                return canMove = false;
            }
        }
        return canMove = true; //otherwise, return move is valid
    }

    /**
     * Checks if game is over (top row of playable board does not contain any null objects)
     * @return gameover boolean
     */
    public boolean isGameDone(){
        boolean gameOver = false;
        for(int col = 0; col < this.gameBoard[0].length; col++){
            if(this.gameBoard[3][col] != null){   //ignores top 4 rows (outside of playable area)
                return true;
            }
        }
        return gameOver;
    }

    /**
     * Checks board by running iterating over board rows and calling checkRowFull method to determined if there are any full rows and calling clearRow method when appropriate.
     * @return boolean indicating it is fisnished checking and clearing current board state
     */
    private boolean checkBoard(){
        boolean doneBoardCheck = false;
        for(int row = 0; row < this.gameBoard.length; row++){
            if(this.checkRowFull(row)){
                this.clearRow(row);  //if row is full, clear it
            }
            else{
                doneBoardCheck = true;
            }
        }
        return doneBoardCheck;
    }

    /**
     * Checks if specified row is full (no null objects)
     * @param  rowCheck board row to check
     * @return          boolean (True = full)
     */
    private boolean checkRowFull(int rowCheck){
        int counter = 0;
        for(int col = 0; col < this.gameBoard[0].length; col++){
            if(this.gameBoard[rowCheck][col] != null){
                counter++;
            }
        }
        if(counter == 10){  //if all 10 blocks are full
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Clears full row & calls dropRow to fill resulting space.
     * @param rowClear specified row
     */
    private void clearRow(int rowClear){
        Board tempBoard = new Board();
//        this.preClearedBoard = tempBoard.getCurrentBoard();
        tempBoard.gameBoard = copyBoard(this.gameBoard);
        for(int col = 0; col < tempBoard.gameBoard[0].length; col++){
            tempBoard.gameBoard[rowClear][col] = null;
        }
        tempBoard.dropRow(rowClear);
        this.gameBoard = tempBoard.gameBoard;
        this.numLinesCleared += 1;
        rowsToClear.add(rowClear);
    }

    /**
     * Drops indicated row after clearing
     * @param rowCleared specified row to drop into (recently cleared)
     */
    private void dropRow(int rowCleared){
        for(int row = rowCleared; row > 0; row--){
            for(int col = 0; col < this.gameBoard[0].length; col++){
                this.gameBoard[row][col] = this.gameBoard[row-1][col]; //fills backwards
            }
        }
    }
    /**
     * Resets variable for number of lines cleared
     */
    public void resetNumLinesCleared(){
        numLinesCleared = 0;
    }

    public long getNumLinesCleared(){
        return this.numLinesCleared;
    }

    public ArrayList<Integer> getRowsToClear() {
        ArrayList<Integer> rowsToClear = new ArrayList<>(this.rowsToClear);
        return rowsToClear;
    }

    public void resetRowsToClear() {
        rowsToClear.clear();
    }

//    public Block[][] getPreClearedBoard() {
//        return preClearedBoard;
//    }

}