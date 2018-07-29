// imports for reading characters from the command line and enabling colors on
// Windows 10.
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;

public class Game {
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private Tetromino ghostTetromino; //  the ghost of the current tetromino
	private Tetromino storedTetromino; // the held tetromino
	private Tetromino proxyTetromino; // proxy space for switching tetrominos

    private static final int startingX = 3; // where a tetromino should start on
    private static final int startingY = 0; // the board
    private long gameScore = 0;
    private long linesCleared = 0;

    private Terminal terminal;        // the terminal and
    private NonBlockingReader reader; // reader for instant input
	
	private boolean heldYet = false;  // if there is already a held piece
	private boolean heldTurn = false; // if the piece this turn was already held


    /**
     * This constructor initializes the terminal input/output to work correctly
     * and generates two new Tetrominos.
     */
    public Game() {
        // generate three new tetrominos at the start of the game
        currentTetromino = new Tetromino(startingX, startingY);
        ghostTetromino = new Tetromino(currentTetromino, true);   //creates ghost
        nextTetromino = new Tetromino(startingX, startingY);


        try{
            terminal = TerminalBuilder.builder()
                .jansi(true)
                .system(true)
                .build();

            terminal.enterRawMode(); // Don't require new line for input.
            reader = terminal.reader();
        }
        catch (IOException e) {
            System.out.println("There was an Error");
        }

    }


    /**
     * Run Game from the command line to play a game of Tetris.
     */
    public static void main(String[] args) {
        Board board = new Board();
        // The printer object, which is what will
        // produce graphics for text based game
        Printer printer = new Printer();
        Game game = new Game();
        // Initial positioning ghost version of current tetromino
        game.ghostTetromino = game.positionGhost(game.currentTetromino, board);
        // Print initial board.
        printer.print(game.currentTetromino,
                      game.nextTetromino,
                      game.ghostTetromino,
                      board,
                      game.terminal,
                      game.gameScore,
                      game.linesCleared);

        boolean gameDone = false;

        // While loop runs each turn
        while(!gameDone) {
            int gameMove = 0;
            try{
                gameMove = game.reader.read();

                // check if user wants to quit
                if (gameMove == 27) {
                    // game.reader.read() returns many keys other than Escape as
                    // 27. So check if the user presses escape twice.
                    int nextInput = game.reader.read();
                    if (nextInput == 27) {
                        break; // end game if escape is pressed twice.
                    }
                }
            }
            catch(IOException e){
                System.out.println("Couldn't read character for move.");
            }

            game.tryMove((char)gameMove, board);
            game.ghostTetromino = game.positionGhost(game.currentTetromino, board);
            // board needs to check if there are any blocks in the board with a
            // y coordinate <= (board.height - 20). Returns true if there is.
            gameDone = board.isGameDone();

            // Prints the board at the end of every turn.
            printer.print(game.currentTetromino,
                          game.nextTetromino,
                          game.ghostTetromino,
                          board,
                          game.terminal,
                          game.gameScore,
                          game.linesCleared);
        }
        System.out.println("Game Over!");

    }

    private void tryMove(char moveType, Board board) {
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
        default: return;
        }
		
        //if the hold character is pressed, it will switch out the current tetromino for the held one
		if(moveType == 'h') {
			if (heldTurn == false) {
				if (heldYet == false) {		// no tetromino is held yet, so it grabs a new one
					storedTetromino = new Tetromino (currentTetromino);
					currentTetromino = new Tetromino (nextTetromino);
					nextTetromino = new Tetromino (startingX, startingY);
					heldYet = true;
				}
				else {						// a tetromino is already held, so it replaces current with that one
					proxyTetromino = new Tetromino (currentTetromino);
					currentTetromino = new Tetromino (startingX, startingY, storedTetromino.getType());
					storedTetromino = new Tetromino (proxyTetromino);
				}
				heldTurn = true;			// a tetromino has already been held for this drop
			}
		}


        if(moveType == 'f'){
            currentTetromino = new Tetromino(ghostTetromino, false);
            board.updateBoard(currentTetromino);
            commitTetrominoSequence(board);
			heldTurn = false;
        }
        // Tetromino.doMove() should return a NEW tetromino with the move applied
        Tetromino movedTetromino = currentTetromino.doMove(moveType);

        // board.checkBoard() returns true if no blocks are currently in the way and
        // no blocks in the given tetromino are out of board bounds.
        boolean canMove = board.checkMove(movedTetromino);

        if (canMove) {
            currentTetromino = new Tetromino (movedTetromino);
        }
        else if (moveType == 's' && !canMove) {
            board.updateBoard(currentTetromino); // if moving down causes it to hit a
                                            // block or go out of bounds, add
                                            // the current blocks in the
                                            // tetromino to the board.
            commitTetrominoSequence(board);
            long[] gameStatistics = board.getGameStatistics();
            this.updateGameScore(gameStatistics[0]);
            this.updateLinesCleared(gameStatistics[1]);
            board.resetGameStatistics();
            currentTetromino = new Tetromino (nextTetromino);
            nextTetromino = new Tetromino(startingX, startingY); // initialize a new random Tetromino

        }
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
        heldTurn = false;				// can hold the tetromino again
    }

    public long getGameScore(){
        return this.gameScore;
    }
    public void updateGameScore(long gameScore){
        this.gameScore += gameScore;
    }
    public long getLinesCleared(){
        return this.linesCleared;
    }
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
}
