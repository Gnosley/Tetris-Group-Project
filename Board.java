import java.util.Arrays;

public class Board{
    private Block[][] gameBoard = new Block[24][10];

    /**
     * TetrisBoard object constructor.
     * Initializes new board. (should only be called outside of class for
     * start of newgame)
     */
    public void Board(){
        Block[][] newBoard = new Block[24][10];
        this.gameBoard = newBoard;
    }

    /**
     * Update method for the gameboard
     * @param gameBoard board used to update instance variable
     */
    public void updateBoard(Tetromino currentTetromino){
        Board tempBoard = new Board();
        tempBoard.gameBoard = copyBoard(this.gameBoard);
        Block[] currentTetArray = currentTetromino.getBlockArray();
        for(int b = 0; b < currentTetArray.length; b++){
            int x = currentTetArray[b].getXPosition();
            int y = currentTetArray[b].getYPosition();
            tempBoard.gameBoard[y][x] = currentTetArray[b];
        }
        this.updateBoardPrivate(tempBoard.gameBoard);
        boolean doneBoardCheck = false;
        do{
            doneBoardCheck = this.checkBoard();
        }
        while(!doneBoardCheck);

    }

    /**
     * Private updateBoard method to avoid direct access
     * @param changedBoard board to update with
     */
    private void updateBoardPrivate(Block[][] changedBoard){
        this.gameBoard = changedBoard;
    }


    /**
     * Copy method for gameBoard. Prevents privacy leaks when board is being
     * passed around.
     * @return           copy of gameBoard
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
     * Getter method for the current gameBoard
     * @return copy of the current Board
     */
    public Block[][] getCurrentBoard(){
        Board copyBoard = new Board();
        copyBoard.gameBoard = copyBoard(this.gameBoard);
        return copyBoard.gameBoard;
    }

    /**
     * checkMove method. Itertaes through tetromino block array and checks
     * current board for collisions.
     *
     * @param  movedTetromino position of the moved tetromino
     * @return                boolean indicating if new position is clear
     */
    public boolean checkMove(Tetromino movedTetromino) {
        boolean canMove = false;
        boolean[] checkArray = new boolean[4];
        Block[] blockArray = movedTetromino.getBlockArray();
        //try{
            for(int b = 0; b < blockArray.length; b++){
                int blockYPos = blockArray[b].getYPosition();
                int blockXPos = blockArray[b].getXPosition();
                if(blockYPos == 24){
                    canMove = false;
                    return canMove;
                }
                if((blockXPos > 0 && blockXPos < 10)){
                    if(gameBoard[blockYPos][blockXPos] == null){
                        canMove = true;

                    }
                }
                checkArray[b] = canMove;
        }
        for(int i = 0; i < checkArray.length; i++){
            if(!checkArray[i]){
                canMove = false;
                break;
            }
        }

		return canMove;
	}

    /**
     * Checks if game is over (top row of board contains not-null piece)
     * @return gameover boolean
     */
    public boolean isGameDone(){
        boolean gameOver = false;
        for(int col = 0; col < gameBoard[0].length; col++){
            if(gameBoard[3][col] != null){   //ignores top 4 rows (outside of playable area)
                gameOver = true;
            }
        }
        return gameOver;
    }

    /**
     * Checks board for full rows
     * @return boolean
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
 * Checks if specified row is full
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
     * Clears full row
     * @param rowClear specified row
     */
    private void clearRow(int rowClear){
        Board tempBoard = new Board();
        tempBoard.gameBoard = copyBoard(this.gameBoard);
        for(int col = 0; col < tempBoard.gameBoard[0].length; col++){
            tempBoard.gameBoard[rowClear][col] = null;    //fills row with null
        }
        tempBoard.dropRow(rowClear);
        this.updateBoardPrivate(tempBoard.gameBoard);

    }

/**
 * Drops indicated row after clearing
 * @param rowClear specified row to drop into (recently cleared)
 */
    private void dropRow(int rowCleared){
        for(int row = rowCleared; row >= 0; row--){
            for(int col = 0; col < this.gameBoard[0].length; col++){
                this.gameBoard[row][col] = this.gameBoard[row-1][col]; //fills backwards
            }
        }
    }
}
