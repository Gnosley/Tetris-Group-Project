import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;

public class Console{
    private Terminal terminal;        // the terminal and
    private NonBlockingReader reader; // reader for instant input

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
        Printer printer = new Printer();
        Game game = new Game();

        // Print initial board.
        console.printGame(printer, game);
        boolean isGameDone = false;
        while(!isGameDone) {
            try{
                int keyInASCII = console.reader.read();
                if (keyInASCII == 'h') {
                    keyInASCII = -1;
                    //pressing 'h' during game opens help/controls menu
                    printer.printHelp(console.terminal);
                    while(keyInASCII == -1) {
                        keyInASCII = console.reader.read();
                    }
                    console.printGame(printer, game);
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
                    game.tryMove(keyInASCII);
                }
            }
            catch(IOException e){
                System.out.println("Couldn't read character for move.");
            }
            console.printGame(printer, game);
            isGameDone = game.getBoard().isGameDone();
        }
        System.out.println("Game Over!");
    }

    /**
     * [printGame description]
     * @param printer [description]
     */
    private void printGame(Printer printer, Game game){
        printer.print(game.getCurrentTetromino(),
                      game.getNextTetromino(),
                      game.getGhostTetromino(),
                      game.getStoredTetromino(),
                      game.getBoard(),
                      terminal,
                      game.getGameScore(),
                      game.getLinesCleared(),
                      game.getIsHoldMoveAvailable());
    }
}
