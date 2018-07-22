// imports for reading characters from the command line.
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;

public class Game {
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private static final int startingX = 3;
    private static final int startingY = 0;
    private long gameScore = 0;
    private long linesCleared = 0;

    private Terminal terminal; // the terminal for instant input
    private NonBlockingReader reader;

    public Game() {

        // the default constructor should generate a random tetromino.
        currentTetromino = new Tetromino(startingX, startingY);
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


    public static void main(String[] args) {
        Board board = new Board();

        // The printer object, which is what will
        // produce graphics for text based game
        Printer printer = new Printer();

        Game game = new Game();

        // Print initial board.
        printer.print(game.currentTetromino,
                      game.nextTetromino,
                      board,
                      game.terminal,
                      game.gameScore,
                      game.linesCleared);

        boolean gameDone = false;

        // While loop runs each turn
        while(!gameDone) {
            // char gameMove = input.next().charAt(0);
            char gameMove = '-';
            int inGameMove = 0;
            try{
                inGameMove = game.reader.read();
            }
            catch(IOException e){
                System.out.println("Couldn't read character for move.");
            }
            gameMove = (char)inGameMove;

            if (inGameMove == 27) { // 27 == ESC
                break; // end game if escape is pressed.
            }

            game.tryMove(gameMove, board);

            // board needs to check if there are any blocks in the board with a
            // y coordinate <= (board.height - 20). Returns true if there is.
            gameDone = board.isGameDone();

            // Prints the board at the end of every turn.
            printer.print(game.currentTetromino,
                          game.nextTetromino,
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
        switch(moveType) {
        case 'q': case 'e': case 'a': case 's': case 'd': break;
        default: return;
        }

        // Tetromino.doMove() should return a NEW tetromino with the move applied
        Tetromino movedTetromino = currentTetromino.doMove(moveType);

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
            long[] gameStatistics = board.getGameStatistics();
            this.updateGameScore(gameStatistics[0]);
            this.updateLinesCleared(gameStatistics[1]);
            currentTetromino = nextTetromino;
            nextTetromino = new Tetromino(startingX, startingY); // initialize a new random Tetromino
        }
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
}
