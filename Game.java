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
    private Tetromino storedTetromino = null; // the held tetromino
    private Tetromino proxyTetromino; // proxy space for switching tetrominos

    private static final int startingX = 3; // where a tetromino should start on
    private static final int startingY = 0; // the board
    private long gameScore = 0;
    private long linesCleared = 0;

    private Terminal terminal;        // the terminal and
    private NonBlockingReader reader; // reader for instant input

    private boolean isHoldOccupied = false;  // if a piece is already being held
    private boolean isHoldMoveAvailable = true; // indicates if hold functionality is available (can only put a piece into the hold once per turn)

    /**
     * This constructor initializes the terminal input/output to work correctly
     * and generates two new Tetrominos.
     */
    public Game() {
        // generate three new tetrominos at the start of the game
        currentTetromino = new Tetromino(startingX, startingY);
        ghostTetromino = new Tetromino(currentTetromino, true);
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
                      game.storedTetromino,
                      board,
                      game.terminal,
                      game.gameScore,
                      game.linesCleared,
                      game.isHoldMoveAvailable);

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
                    else if (nextInput == 91) {
                        nextInput = game.reader.read();
                        switch(nextInput) {
                            case 65: gameMove = 101; break; // up arrow
                            case 66: gameMove = 115; break; // down arrow
                            case 67: gameMove = 100; break; // right arrow
                            case 68: gameMove = 97; break; //left arrow
                        }
                    }
                }
            }
            catch(IOException e){
                System.out.println("Couldn't read character for move.");
            }
            //if user enters valid keyboard command, game begins sequence for cheking the move
            game.tryMove(gameMove, board);
            game.ghostTetromino = game.positionGhost(game.currentTetromino, board);

            // board needs to check if there are any blocks in the board with a
            // y coordinate <= (board.height - 20). Returns true if there is.
            gameDone = board.isGameDone();

            if (gameMove == 'h') {
                //pressing 'h' during game opens help/controls menu
                printer.printHelp(game.terminal);
            }
            else {
                // Prints the board at the end of every turn.
                printer.print(game.currentTetromino,
                              game.nextTetromino,
                              game.ghostTetromino,
                              game.storedTetromino,
                              board,
                              game.terminal,
                              game.gameScore,
                              game.linesCleared,
                              game.isHoldMoveAvailable);
            }
        }
        System.out.println("Game Over!");

    }

    /**
     * Attempt to make a move, must be checked for possibility within limits of game
     * @param moveType: char, letter input of I/O
     * @param board: Board, the playing surface
     */
    private void tryMove(int moveType, Board board) {
        // Possible chars are q, e, a, s, d.
        // q rotates counter-clockwise.
        // e rotates clockwise.
        // a moves the block left.
        // s moves the block down.
        // d moves the block right.
        // w or TAB holds the current block.
        // f or SPACE drops the current block.
        switch(moveType) {
        case 'q': case 'e': case 'a': case 's': case 'd': case 'h': break;
        case 'f': case 32:
            dropMove(board);
            break;
        case 'w': case 9:
            holdMove();
            break;
        default: return;
        }
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
    }


    /**
     * Method for holding tetromino. if the hold character is pressed, it will
     * switch out the current tetromino for the held one
     */
    private void holdMove(){
        if (isHoldMoveAvailable == true) {
            if (isHoldOccupied == false) {
                // no tetromino is held yet, so it grabs a new one
                storedTetromino = new Tetromino (currentTetromino);
                currentTetromino = new Tetromino (nextTetromino);
                nextTetromino = new Tetromino (startingX, startingY);
                isHoldOccupied = true;
            }
            else {
                // a tetromino is already held, so it replaces current with that one
                proxyTetromino = new Tetromino (currentTetromino);
                currentTetromino = new Tetromino (startingX, startingY, storedTetromino.getType());
                storedTetromino = new Tetromino (proxyTetromino);
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
}
