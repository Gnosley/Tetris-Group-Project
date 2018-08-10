package org.teamsix;


import java.util.Timer;
import java.util.TimerTask;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import java.io.IOException;
import java.util.Scanner;

public class Console{

    public class DropTimer extends TimerTask {
        @Override
        public void run () {
            // boolean gameDone = game.getBoard().isGameDone();
            if(!isGameDone) {
                game.tryMove('s');
                isGameDone = game.isGameDone();
                printGame();
            } else {
                printGame();
                printer.printGameOver(terminal);
                this.cancel();
            }
        }
    }
    private Terminal terminal;        // the terminal and
    private NonBlockingReader reader; // reader for instant input
    private Printer printer = new Printer();
    private Game game = new Game();
    private boolean isGameDone = false;
    private TimerTask dropTimer = new DropTimer();

    /**
     * Console constructor
     */
    public Console(){
        this("USER", "MEDIUM");
    }

    public Console(String username, String difficulty){
        game = new Game(username, difficulty);
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

    public static String[] getOptions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username (7 Chars MAX):\t");
        String username = scanner.next();
        System.out.println("Please Choose Difficulty (EASY, MEDIUM, HARD): ");
        String difficulty = scanner.next();
        username = username.trim();
        difficulty = difficulty.trim();
        if (username.equals("")) {
            username = "USER";
        }
        while(username.length() < 7) {
            username += " ";
        }
        if (username.length() > 7) {
            username = username.substring(0, 7);
        }
        if(difficulty.equals("")) {
            difficulty = "MEDIUM";
        }
        return new String[] {username, difficulty};
    }
    public static void main(String[] args){

        String[] options = Console.getOptions();
        String username=options[0], difficulty=options[1];

        Console console = new Console(username, difficulty);


        // Print initial board.
        console.printGame();
        console.dropPieces();
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
                        console.pauseDropping();
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
            console.setIsGameDone(console.game.isGameDone());
        }
        // Save Score and username on game done.
        console.game.writeScoreToFile();
        console.printGame();
        console.printer.printGameOver(console.terminal);
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
                      game.getPieceStats(),
                      game.getUsername(),
                      game.getDifficulty(),
                      game.getHighScore(),
                      game.getHighScoreName());
    }

    /**
     * Creates a new timer thread.
     * Tetrominos will drop at a rate determined by the timer
     */
    private void dropPieces() {
        this.dropTimer = new DropTimer();
        new Timer().schedule(dropTimer, 0, 300);
    }

    private void pauseDropping() {
        this.dropTimer.cancel();
    }

    public boolean getIsGameDone() {
        return this.isGameDone;
    }

    private void setIsGameDone(boolean gameDone) {
        this.isGameDone = gameDone;
    }

}
