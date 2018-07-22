// imports for reading characters from the command line.
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;

import java.util.Scanner;

public class Game {
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private static final int startingX = 3;
    private static final int startingY = 0;
    private long gameScore = 0;
    private int linesCleared = 0;

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

        // printer = new Printer(doPrettyPrint);
    }


    public static void main(String[] args) {
        Board board = new Board();

        boolean doPrettyPrint = false;
        for (String arg:args) {
            if (arg.equals("pretty")) {
                doPrettyPrint = true;
            }
        }

        // The printer object, which is what will
        // produce graphics for text based game
        Printer printer = new Printer();

        Game game = new Game();

        // initialize keyboard input
        // Scanner input = new Scanner(System.in);


        // Print initial board.
        printer.print(game.currentTetromino, game.nextTetromino, board);

        boolean gameDone = false;

        // While loop runs each turn
        while(!gameDone) {
            // char gameMove = input.next().charAt(0);
            char gameMove = '-';
            try{
                gameMove = (char)game.reader.read();
            }
            catch(IOException e){
                System.out.println("Couldn't read character for move.");
            }

            game.tryMove(gameMove, board);

            // board needs to check if there are any blocks in the board with a
            // y coordinate <= (board.height - 20). Returns true if there is.
            gameDone = board.isGameDone();

            // Prints the board at the end of every turn.
            printer.print(game.currentTetromino, game.nextTetromino, board);
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

            currentTetromino = nextTetromino;
            nextTetromino = new Tetromino(startingX, startingY); // initialize a new random Tetromino
        }
    }
    public long getGameScore(){
        return this.gameScore;
    }
    public updateGameScore(){
        this.linesCleared += 100;
    }
    public int getLinesCleared(){
        return this.gameScore;
    }
    public updateLinesCleared(){
        this.linesCleared += 1;
    }
}

