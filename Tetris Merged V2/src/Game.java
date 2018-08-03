
public class Game {
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private Tetromino ghostTetromino; //  the ghost of the current tetromino
    private Tetromino storedTetromino = null; // the held tetromino
    private Tetromino proxyTetromino; // proxy space for switching tetrominos

    private static final int startingX = 3; // where a tetromino should start on
    private static final int startingY = 0; // the board
    private long gameScore = 0;
    private long linesCleared = 0;
    private Board board;

    private boolean isHoldOccupied = false;  // if a piece is already being held
    private boolean isHoldMoveAvailable = true; // indicates if hold functionality is available (can only put a piece into the hold once per turn)


    /**
     * This constructor initializes the terminal input/output to work correctly
     * and generates two new Tetrominos.
     */
    public Game() {
        // generate three new tetrominos at the start of the game
        currentTetromino = new Tetromino(startingX, startingY);
        nextTetromino = new Tetromino(startingX, startingY);
        ghostTetromino = new Tetromino(currentTetromino, true);   //creates ghost

        Board board = new Board();
        this.board = board;
        ghostTetromino = positionGhost(currentTetromino, board);
        boolean gameDone = false;
    }

    /**
     * Attempt to make a move, must be checked for possibility within limits of game
     * @param moveType: char, letter input of I/O
     * @param board: Board, the playing surface
     */
    public boolean tryMove(char moveType, Board board) {
        // Possible chars are q, e, a, s, d.
        // q rotates counter-clockwise.
        // e rotates clockwise.
        // a moves the block left.
        // s moves the block down.
        // d moves the block right.
        // h holds the current block.
        // x drops the current block.
        switch(moveType) {
            case 'q': case 'e': case 'a': case 's': case 'd': case 'f' : case 'h': break;
            //default: return;
        }

//        //if the hold character is pressed, it will switch out the current tetromino for the held one
//        if(moveType == 'h') {
//            if (heldTurn == false) {
//                if (heldYet == false) {		// no tetromino is held yet, so it grabs a new one
//                    storedTetromino = new Tetromino (currentTetromino);
//                    currentTetromino = new Tetromino (nextTetromino);
//                    nextTetromino = new Tetromino (startingX, startingY);
//                    heldYet = true;
//                }
//                else {						// a tetromino is already held, so it replaces current with that one
//                    proxyTetromino = new Tetromino (currentTetromino);
//                    currentTetromino = new Tetromino (startingX, startingY, storedTetromino.getType());
//                    storedTetromino = new Tetromino (proxyTetromino);
//                }
//                heldTurn = true;			// a tetromino has already been held for this drop
//            }
//        }

        if(moveType == 'f' || moveType == 32){ // drop on 'f' or space
            currentTetromino = new Tetromino(ghostTetromino, false);
            board.updateBoard(currentTetromino);
            commitTetrominoSequence(board);
            ghostTetromino = positionGhost(currentTetromino, board);
        }

        // Tetromino.doMove() should return a NEW tetromino with the move applied
        Tetromino movedTetromino = currentTetromino.doMove((char)moveType);

        // board.checkBoard() returns true if no blocks are currently in the way and
        // no blocks in the given tetromino are out of board bounds.
        boolean canMove = board.checkMove(movedTetromino);

        if (canMove) {
            currentTetromino = movedTetromino;
        }
        else if (moveType == 's' && !canMove) {
            board.updateBoard(currentTetromino); // if moving down causes it to hit a
                                            // block or go out of bounds, add
                                            // the current blocks in the
                                            // tetromino to the board.

            commitTetrominoSequence(board);
        }
        ghostTetromino = positionGhost(currentTetromino, board);
        return canMove;
    }

    /**
     * Sequence of events which occur after a piece 'played' (locked into the
     * board).
     * @param board         current game board
     */
    private void commitTetrominoSequence(Board board){
        long[] gameStatistics = board.getGameStatistics();
        this.updateGameScore(gameStatistics[0]);
        this.updateLinesCleared(gameStatistics[1]);
        board.resetGameStatistics();
        this.currentTetromino = this.nextTetromino;
        this.nextTetromino = new Tetromino(startingX, startingY); // initialize a new random Tetromino
        isHoldMoveAvailable = true; //resets ability to hold piece
    }

    /**
     * Getter method of the score achieved through clearing rows
     * @return gameScore: long, the score achieved
     */
    public long getGameScore(){
        return this.gameScore;
    }



    /**
     * Setter of the score achieved through clearing rows
     * @param gameScore: long, the score achieved
     */
    public void updateGameScore(long gameScore){
        this.gameScore += gameScore;
    }

    /**
     * Getter of how many lines were cleared
     * @return linesCleared: long, keeps track of number of lines cleared
     */
    public long getLinesCleared(){
        return this.linesCleared;
    }

    /**
     * Keeping track of score through addition of lines cleared as lines clear
     * @param linesCleared: long, keeps track of number of lines cleared
     */
    public void updateLinesCleared(long linesCleared){
        this.linesCleared += linesCleared;
    }

    /**
     * Positioning method for the ghost tetromino. Takes position of current
     * tetromino and sets the ghost position to the "floored" version if it
     * @param  currentTetromino current tetromino in play
     * @param  board            current board
     * @return                  ghost tetromino (re-positioned)
     */
    private Tetromino positionGhost(Tetromino currentTetromino, Board board) {
        boolean canMove = true;
        Tetromino ghostTetromino = new Tetromino(currentTetromino, true);
        while (canMove) {
            Tetromino movedGhost = ghostTetromino.doMove('s');
            canMove = board.checkMove(movedGhost);
            if (canMove) {
                ghostTetromino = movedGhost;
            }
        }
        return ghostTetromino;
    }


    //TODO protect these from privacy leaks

    /**
     * Returns current board object
     * @return Board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Returns current Tetromino object
     * @return Tetromino
     */
    public Tetromino getCurrentTetromino() {
        return this.currentTetromino;
    }

    /**
     * Returns next Tetromino object
     * @return Tetromino
     */
    public Tetromino getNextTetromino() {
        return this.nextTetromino;
    }

    /**
     * Returns ghost Tetromino object
     * @return Tetromino
     */
    public Tetromino getGhostTetromino() {
        return this.ghostTetromino;
    }
}
