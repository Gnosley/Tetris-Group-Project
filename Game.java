import java.util.Scanner;

public class Game {
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private static final int startingX = 3;
    private static final int startingY = 0;

    public Game() {

        // the default constructor should generate a random tetromino.
        currentTetromino = new Tetromino(startingX, startingY);
        nextTetromino = new Tetromino(startingX, startingY);

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
        Scanner input = new Scanner(System.in);


        // Print initial board.
        printer.print(game.currentTetromino, game.nextTetromino, board);

        boolean gameDone = false;

        // While loop runs each turn
        while(!gameDone) {
            char gameMove = input.next().charAt(0);

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
        // Right now this assumes valid character... WILL FIX.

        // Tetromino.doMove() should return a NEW tetromino with the move applied
        Tetromino movedTetromino = currentTetromino.doMove(moveType);

        // board.checkBoard() returns true if no blocks are currently in the way and
        // no blocks in the given tetromino are out of board bounds.
        if (board.checkMove(movedTetromino)) {
            currentTetromino = movedTetromino;
        }
        else if (moveType == 's' && board.checkMove(movedTetromino) == false) {
            board.updateBoard(currentTetromino); // if moving down causes it to hit a
                                            // block or go out of bounds, add
                                            // the current blocks in the
                                            // tetromino to the board.

            currentTetromino = nextTetromino;
            nextTetromino = new Tetromino(startingX, startingY); // initialize a new random Tetromino
        }
    }
}
