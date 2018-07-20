import java.util.Scanner;

public class Game {
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private Printer printer; // The printer object, which is what will produce
                             // graphics for text based game

    public Game(boolean doPrettyPrint) {

        // the default constructor should generate a random tetromino.
        currentTetromino = new Tetromino();
        nextTetromino = new Tetromino();

        printer = new Printer(doPrettyPrint);
    }


    public static void main(String[] args) {
        Board board = new Board();

        boolean doPrettyPrint = false;
        if (args[0].equals("pretty")) {
            doPrettyPrint = true;
        }
        Game game = new Game(doPrettyPrint);

        // initialize keyboard input
        Scanner input = new Scanner(System.in);


        // Print initial board.
        printer.print(currentTetromino, nextTetromino, board);

        boolean gameDone = false;

        // While loop runs each turn
        while(!gameDone) {
            char gameMove = input.next().charAt(0);

            game.tryMove(gameMove, board);

            // board needs to check if there are any blocks in the board with a
            // y coordinate <= (board.height - 20). Returns true if there is.
            gameDone = game.board.isGameDone();

            // Prints the board at the end of every turn.
            printer.print(currentTetromino, nextTetromino, board);
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
        Tetromino movedTetromino = this.currentTetromino.doMove(moveType);

        // board.checkBoard() returns true if no blocks are currently in the way and
        // no blocks in the given tetromino are out of board bounds.
        if (board.checkBoard(movedTetromino)) {
            currentTetromino = movedTetromino;
        }
        else if (moveType == 's' && board.checkBoard(movedTetromino) == false) {
            board.update(currentTetromino); // if moving down causes it to hit a
                                            // block or go out of bounds, add
                                            // the current blocks in the
                                            // tetromino to the board.

            currentTetromino = nextTetromino;
            nextTetromino = new Tetromino(); // initialize a new random Tetromino
        }
    }
}
