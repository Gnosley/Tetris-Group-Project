public class Game {
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private Tetromino ghostTetromino; //  the ghost of the current tetromino
    private Tetromino storedTetromino = null; // the held tetromino
    private GenerateTetromino generateTetromino; //

    private static final int startingX = 3; // where a tetromino should start on
    private static final int startingY = 0; // the board

    private long gameScore = 0;
    private long linesCleared = 0;

    private boolean isHoldOccupied = false;  // if a piece is already being held
    private boolean isHoldMoveAvailable = true; // indicates if hold functionality is available (can only put a piece into the hold once per turn)
    private Board board;

    public Game() {
        generateTetromino = new GenerateTetromino();
        // generate three new tetrominos at the start of the game
        currentTetromino =
            generateTetromino.newTetromino(startingX, startingY);
        nextTetromino = generateTetromino.newTetromino(startingX, startingY);
        ghostTetromino = new Tetromino(currentTetromino, true);

        // Initial positioning ghost version of current tetromino
        ghostTetromino = positionGhost(board);

    }



    /**
     * Getter for the current tetromino instance variable
     * @return tetromino: Tetromino, copy of the currentTetromino
     */
    public Tetromino getCurrentTetromino(){
        Tetromino tetromino = new Tetromino(this.currentTetromino);
        return (tetromino);
    }

    /**
     * Getter for the next tetromino instance variable
     * @return tetromino: Tetromino, copy of the nextTetromino
     */
    public Tetromino getNextTetromino(){
        Tetromino tetromino = new Tetromino(this.nextTetromino);
        return (tetromino);
    }

    /**
     * Getter for the ghost tetromino instance variable
     * @return tetromino: Tetromino, copy of the ghostTetromino
     */
    public Tetromino getGhostTetromino(){
        Tetromino tetromino = new Tetromino(this.ghostTetromino);
        return (tetromino);
    }

    /**
     * Getter for the stored tetromino instance variable
     * @return tetromino: Tetromino, copy of the storedTetromino
     */
    public Tetromino getStoredTetromino(){
        Tetromino tetromino = new Tetromino(this.storedTetromino);
        return (tetromino);
    }
    /**
     * Getter method for the board instance variable
     * @return board: Board, the current board
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Getter method of the score achieved through clearing rows
     * @return gameScore: long, the score achieved
     */
    public long getGameScore(){
        return this.gameScore;
    }

    /**
     * Getter of how many lines were cleared
     * @return linesCleared: long, keeps track of number of lines cleared
     */
    public long getLinesCleared(){
        return this.linesCleared;
    }

    /**
     * Getter method for whether hold move is available
     * @return isHoldMoveAvailable: boolean, inidcates if hold move is available
     */
    public boolean getIsHoldMoveAvailable(){
        return this.isHoldMoveAvailable;
    }



    /**
     * Positioning method for the ghost tetromino. Takes position of current
     * tetromino and sets the ghost position to the "floored" version if it
     * @param  currentTetromino current tetromino in play
     * @param  board            current board
     * @return                  ghost tetromino (re-positioned)
     */
    private Tetromino positionGhost(Board board) {
        boolean canMove = true;
        while (canMove) {
            Tetromino movedGhost = ghostTetromino.doMove('s');
            canMove = board.checkMove(movedGhost);
            if (canMove) {
                ghostTetromino = movedGhost;
            }
        }
        return ghostTetromino;
    }


    /**
     * Attempt to make a move, must be checked for possibility within limits of
     * game
     * @param moveType: char, letter input of I/O
     */
    public void tryMove(int moveType){
        // Possible chars are q, e, a, s, d, w or TAB, f or SPACE.
        // q rotates counter-clockwise.
        // e rotates clockwise.
        // a moves the block left.
        // s moves the block down.
        // d moves the block right.
        // w or TAB holds the current block.
        // f or SPACE drops the current block.
        switch(moveType) {
            case 'q': case 'e': case 'a': case 's': case 'd':
            // Tetromino.doMove() should return a NEW tetromino with the move applied
            Tetromino movedTetromino = currentTetromino.doMove((char)moveType);

            // board.checkBoard() returns true if no blocks are currently in the way and
            // no blocks in the given tetromino are out of board bounds.
            boolean canMove = board.checkMove(movedTetromino);

            if (canMove) {
                currentTetromino = new Tetromino (movedTetromino);
            }
            else if (moveType == 's' && !canMove) {
                board.updateBoard(currentTetromino); // if moving down causes it to hit a block or go out of bounds, add the current blocks in the tetromino to the board.
                commitTetrominoSequence(board);
            }
            break;

            case 'f': case 32:
                this.dropMove(board);
                break;

            case 'w': case 9:
                this.holdMove();
                break;

            default: return;
            }

    }

    /**
     * Method for holding tetromino. if the hold character is pressed, it will
     * switch out the current tetromino for the held one
     */
    private void holdMove(){
        if (isHoldMoveAvailable == true) {
            if (isHoldOccupied == false) {
                // no tetromino is held yet, so it grabs a new one
                storedTetromino = new Tetromino(currentTetromino);
                currentTetromino = new Tetromino(nextTetromino);
                nextTetromino = generateTetromino.newTetromino(startingX, startingY);
                isHoldOccupied = true;
            }
            else {
                // a tetromino is already held, so it replaces current with that one
                Tetromino proxyTetromino; // proxy space for switching tetrominos
                proxyTetromino = new Tetromino(currentTetromino);
                currentTetromino = new Tetromino(startingX, startingY, storedTetromino.getType());
                storedTetromino = new Tetromino(proxyTetromino);
            }
            isHoldMoveAvailable = false;			// a tetromino has already been held for this drop
        }
    }

    /**
     * Method for the drop move
     * @param board Current game board
     */
    private void dropMove(Board board){
        currentTetromino = new Tetromino(ghostTetromino, false);
        board.updateBoard(currentTetromino);
        commitTetrominoSequence(board);
    }

    /**
     * Sequence of events which occur after a piece 'played' (locked into the
     * board). Game statistics get updated to console, current tetromino is
     * updated with the next piece, next tetromino is created, and hold move
     * ability is reset.
     * @param board         current game board
     */
    private void commitTetrominoSequence(Board board){
        long numLinesCleared = board.getNumLinesCleared();
        this.updateLinesCleared(numLinesCleared);
        this.updateGameScore(numLinesCleared);
        board.resetNumLinesCleared();
        this.currentTetromino = this.nextTetromino;
        this.nextTetromino = generateTetromino.newTetromino(startingX, startingY);; // initialize a new random Tetromino
        this.ghostTetromino = new Tetromino(currentTetromino, true);
        this.ghostTetromino = positionGhost(board);
        isHoldMoveAvailable = true; //resets ability to hold piece
    }

    /**
     * Keeping track of score through addition of lines cleared as lines clear
     * @param linesCleared: long, keeps track of number of lines cleared
     */
    public void updateLinesCleared(long linesCleared){
        this.linesCleared += linesCleared;
    }

    /**
     * Setter of the score achieved through clearing rows
     * @param gameScore: long, the score achieved
     */
    public void updateGameScore(long numLinesCleared){
      if(numLinesCleared == 4){
        this.gameScore += (numLinesCleared * 200);
      }
      else{
        this.gameScore += (numLinesCleared * 100);
      }
    }
}
