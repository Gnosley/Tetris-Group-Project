import java.util.Timer;
import java.util.TimerTask;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;

public class Console{

    public class DropTimer extends TimerTask {
        @Override
        public void run () {
            // boolean gameDone = game.getBoard().isGameDone();
            if(!isGameDone) {
                game.tryMove('s');
                isGameDone = game.getBoard().isGameDone();
                printGame();
            } else {
                printGame();
                this.cancel();
            }
        }
    }
    private Terminal terminal;        // the terminal and
    private NonBlockingReader reader; // reader for instant input
    Printer printer = new Printer();
    Game game = new Game();
    boolean isGameDone = false;
    TimerTask dropTimer = new DropTimer();

    /**
     * Console constructor
     */
    public Console(){
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


    public static void main(String[] args){
        Console console = new Console();
        // Board board = new Board();
        // The printer object, which is what will produce graphics for text
        // based game

        // Print initial board.
        console.printGame();
        console.dropPieces();
       // jgame.dropPieces();
        while(!console.getIsGameDone()) {
            try{
                int keyInASCII = console.reader.read();
                if (keyInASCII == 'h') {
                    console.pauseDropping();
                    keyInASCII = -1;
                    //pressing 'h' during game opens help/controls menu
                    console.printer.printHelp(console.terminal);
                    while(keyInASCII == -1) {
                        keyInASCII = console.reader.read();
                    }
                    console.dropPieces();
                    console.printGame();
                }
                // check if user wants to quit
                if (keyInASCII == 27) {
                    // game.reader.read() returns many keys other than Escape as
                    // 27. So check if the user presses escape twice.
                    int nextInput = console.reader.read();
                    if (nextInput == 27) {
                        break; // end game if escape is pressed twice.
                    }
                }
                else {
                    //if user enters valid keyboard command, game begins sequence for cheking the move
                    console.game.tryMove(keyInASCII);
                }
            }
            catch(IOException e){
                System.out.println("Couldn't read character for move.");
            }
            console.printGame();
            console.setIsGameDone(console.game.getBoard().isGameDone());
        }
        System.out.println("Game Over!");
        System.exit(0);
    }

    /**
     * [printGame description]
     * @param printer [description]
     */
    private void printGame(){
        printer.print(game.getCurrentTetromino(),
                      game.getNextTetromino(),
                      game.getGhostTetromino(),
                      game.getStoredTetromino(),
                      game.getBoard(),
                      terminal,
                      game.getGameScore(),
                      game.getLinesCleared(),
                      game.getIsHoldMoveAvailable(),
                      game.getPieceStats());
    }

    /**
     * Creates a new timer thread.
     * Tetrominos will drop at a rate determined by the timer
     */
    public void dropPieces() {
        this.dropTimer = new DropTimer();
        new Timer().schedule(dropTimer
                             , 0, 300);
    }

    public void pauseDropping() {
        this.dropTimer.cancel();
    }

    public boolean getIsGameDone() {
        return this.isGameDone;
    }

    public void setIsGameDone(boolean gameDone) {
        this.isGameDone = gameDone;
    }

}
