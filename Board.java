import java.util.Arrays;

public class Board{
    private Block[][] gameBoard;

    /**
     * TetrisBoard object constructor.
     * Initializes new board. (should only be called for start of newgame)
     */
    public void Board(){
        Block[][] board = new Block[24][10];
        this.gameBoard = gameBoard;
    }

    /**
     * Update method for the gameboard
     * @param gameBoard board used to update instance variable
     */
    public void updateBoard(Tetromino currentTetromino){
        this.gameBoard = gameBoard;
    }
    /**
     * Copy method for gameBoard. Prevents privacy leaks when board is being
     * passed around.
     * @param  gameBoard current gameBoard (instance variable)
     * @return           copy of gameBoard
     */
    private Block[][] copyBoard(Block[][] gameBoard){
        Block[][] tempBoard = new board();
        for(int row = 0; row < tempBoard.length; row++){
            for(int col = 0; col < tempBoard[0].length; col++){
                tempBoard[row][col] = gameBoard[row][col];
            }
        }
        return tempBoard;
    }
    /**
     * Getter method for the current gameBoard
     * @return copy of the current Board
     */
    public Block[][] getCurrentBoard(){
        Block[][] copyBoard = copyBoard(this.gameBoard);
        return copyBoard;
    }

    /**
     * checkBoard method. Itertaes through tetromino block array and checks
     * current board for collisions.
     *
     * @param  movedTetromino position of the moved tetromino
     * @return                boolean indicating if new position is clear
     */
    public boolean checkBoard(Tetromino movedTetromino) {
        boolean canMove = false;
        Block[] blockArray = movedTetromino.getBlockArray();
        for(int block = 0; block < blockArray.length; block++){
            if(gameBoard[blockArray[block].getXPosition()][blockArray[block].getYPosition()] == null){
                canMove = true;
            }
            else{
                canMove = false;
            }
        }
		return canMove;
	}
    /**
     * Checks if game is over (top row of board contains not-null piece)
     * @return gameover boolean
     */
    public boolean isGameDone();{
        boolean gameOver = false;
        for(int col = 0; col < gameBoard[0].length; col++){
            if(gameBoard[0][col] != null){
                gameOver = true;
            }
        }
    return gameOver;
    }

}
