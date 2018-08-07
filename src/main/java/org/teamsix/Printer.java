package org.teamsix;



import org.jline.utils.InfoCmp.Capability;
import org.jline.terminal.Terminal;
import java.lang.String;

public class Printer {

    // how many characters wide should each block be
    private final int BLOCKWIDTH = 2;

    // info about the board
    private final int BOARDWIDTH = 10;

    private final int BOARDHEIGHT = 24;

    private final int BOARDCHARWIDTH = BOARDWIDTH * BLOCKWIDTH + 2;

    // rows that preview window begins and ends on
    private final int PREVIEWTOP = 9;

    private final int PREVIEWBOT = 14;

    // Preview window width
    private final int PREVIEWWIDTH = 5 * BLOCKWIDTH;

    // rows that preview window begins and ends on
    private final int HOLDTOP = 2;

    private final int HOLDBOT = 7;

    private final int STATSTOP = 9;

    private final int STATSBOT = 20;

    // how many characters wide is the space beside the main part of the board
    private final int LEFTWIDTH = 20;

    private final int RIGHTWIDTH = 38;

    // Standard console width
    private final int CONSOLEWIDTH = 80;

    private final int CONSOLEHEIGHT = 22;

    // Unicode Characters
    // Block Character
    private final String BLOCKCHAR = "\u2588";

    // Ghost Block Character
    private final String GBLKCHAR = "\u2591";

    // Vertical Character
    private final String VCHAR = "\u2551";

    // Horizontal Character
    private final String HCHAR = "\u2550";

    // Top Right Character
    private final String TRCHAR = "\u2557";

    // Top Left Character
    private final String TLCHAR = "\u2554";

    // Bottom Right Character
    private final String BRCHAR = "\u255D";

    // Bottom Left Character
    private final String BLCHAR = "\u255A";

    // Horizontal to Vertical Upper Split
    private final String HVUSPLIT = "\u2569";

    // Vertical to Horizontal Right Split
    private final String VHRSPLIT = "\u2560";

    // Vertical to Horizontal Left Split
    private final String VHLSPLIT = "\u2563";








    /*
     * PUBLIC FUNCTIONS
     */

    /**
     * Prints the current game state from the given objects.
     */
    public void print(Tetromino currentTetromino,
                      Tetromino nextTetromino,
                      Tetromino ghostTetromino,
                      Tetromino holdTetromino,
                      Board board,
                      Terminal terminal,
                      long gameScore,
                      long linesCleared,
                      boolean holdAvailable,
                      int[] tetrominoStats,
                      String username,
                      String difficulty,
                      long highscore,
                      String highscoreName)
    {

        // IF LINUX:

        // int termHeight = terminal.getHeight();
        // for(int i=0; i < termHeight - CONSOLEHEIGHT; i++) {
        //     terminal.writer().println();
        // }
        // terminal.writer().flush();

        // IF WINDOWS

        terminal.puts(Capability.clear_screen);

        // combine the board and the tetrominos played on it
        Block[][] combinedBoard = combine(currentTetromino, ghostTetromino, board);
        // create arrays for easy printing
        Block[][] holdArray = createBlockArray(holdTetromino);
        Block[][] nextArray = createBlockArray(nextTetromino);

        terminal.writer().println(centerString("Press Escape Twice to exit. Press 'h' for help.", 80));

        String[] rowStrings = new String[CONSOLEHEIGHT];
        // get the strings left of the main play space
        String[] leftStrings = getLeftSide(holdArray, holdAvailable, tetrominoStats);
        // get the strings representing the main play space
        String[] boardStrings = getBoardStrings(combinedBoard);
        // get the strings right of the main play space
        String[] rightStrings = getRightSide(username, gameScore,
                                             linesCleared, highscore,
                                             highscoreName, nextArray, difficulty);
        // for each row, add left + main + right then print
        for (int i = 0; i < CONSOLEHEIGHT; i++) {
            rowStrings[i] = leftStrings[i] + boardStrings[i] + rightStrings[i];
            terminal.writer().println(rowStrings[i]);
        }
        // Print everything out
        terminal.writer().flush();
    }

    /**
     * Prints the help screen
     *
     * @param terminal The Terminal object to print to
     */
    public void printHelp(Terminal terminal) {
        terminal.writer().println(ANSI.CLEARSCREEN);
        String[] helpStrings = {
            "Welcome to Tetris",
            "",
            "",
            " Implemented By:",
            "Natalie, Jesse",
            "Dustin, Murray",
            "Stephen",
            "",
            " Controls:",
            "     Rotate:                              ",
            "            Counter Clockwise:  'q'       ",
            "            Clockwise:          'e'       ",
            "     Translate:                           ",
            "            Left:               'a'       ",
            "            Down:               's'       ",
            "           Right:               'd'       ",
            "     Drop:             SPACE or 'f'       ",
            "     Hold:             TAB   or 'w'       ",
            "     Quit:             ESC ESC            ",

        };
        for (String line : helpStrings) {
            terminal.writer().println(centerString(line, 80));
        }
        terminal.writer().flush();
    }

    /*
     * HELPER FUNCTIONS
     */


    /**
     * Centers a string within the given width.
     *
     * @param inputStr  The String to be centered.
     * @param width  The width for the string to be centered within.
     * @return A String that is centered, or if the string was longer than the
     * given width, the string that was given.
     */
    private String centerString(String inputStr, int width) {
        // can't center string that's longer than the given width
        String centeredStr = stripANSI(inputStr);
        if (centeredStr.length() >= width || centeredStr.isEmpty()) {
            return inputStr;
        }

        // calculate the padding for each side
        double padding = (width - centeredStr.length()) / 2.0;
        int leftPadding = (int) padding;
        int rightPadding = (int) Math.ceil(padding);

        // generate padding string
        String padString = generatePadding(leftPadding);

        // add an extra padding to the end of the string being returned if it
        // can't be evenly centered
        if (leftPadding == rightPadding) {
            return padString + inputStr + padString;
        }
        else return padString + inputStr + padString + " ";
    }

    /**
     * Removes all ANSI codes from string.
     *
     * @param  inputStr   The String to be stripped.
     * @return A string stripped of ANSI.
     */
    private String stripANSI(String inputStr) {
        String cleanStr = inputStr;
        String[] ansiList = {ANSI.RED,
                             ANSI.BLUE,
                             ANSI.BLINK,
                             ANSI.CYAN,
                             ANSI.GREEN,
                             ANSI.RESET,
                             ANSI.CLEARSCREEN,
                             ANSI.PURPLE,
                             ANSI.WHITE,
                             ANSI.BLACK,
                             ANSI.BRIGHT_YELLOW,
                             ANSI.YELLOW};
        for (String stringToRemove : ansiList) {
            cleanStr = cleanStr.replace(stringToRemove, "");
        }
        return cleanStr;
    }

    /**
     * Formats a number to be have given number of digits with " " as placeholder.
     *
     * @param number The number to be formatted.
     * @param digits The number of digits to format to.
     * @return String with the given number of digits.
     */
    private String formatNumber(long number, int digits) {
        return String.format("% " + digits + "d", number);
    }

    /**
     * Generate a string of spaces with the given length.
     *
     * @param length The length of the padding to be generated.
     * @return A string of spaces with given length.
     */
    private String generatePadding(int length) {
        String padString = "";
        for (int i =0; i < length; i++) {
            padString += " ";
        }
        return padString;
    }

    /**
     * Returns an array of strings representing the rows of the left side of the
     * screen to be printed.
     *
     * @param holdArray The array of arrays with correctly positioned blocks.
     * @param isHoldMoveAvailable A boolean, true if the player can use hold
     * move.
     * @return Array containing the rows of the left side as string
     * representations. Every String in the array should be Printer.LEFTWIDTH
     * long.
     */
    private String[] getLeftSide(Block[][] holdArray,
                                 boolean isHoldMoveAvailable,
                                 int[] tetrominoStats) {

        String[] arrayOfRows = new String[CONSOLEHEIGHT];
        for(int i = 0; i < arrayOfRows.length; i++) {
            arrayOfRows[i] = "";

            // Add the timer at the top row
            if (i == 0) {
                arrayOfRows[i] += centerString(getTimerString(), LEFTWIDTH);
            }

            // add the holding box
            else if (i>= HOLDTOP && i <= HOLDBOT) {
                String holdString = getHoldBoxString(i - HOLDTOP, holdArray,
                                                     isHoldMoveAvailable);
                // calculate the spacing needed before the hold box
                int paddingNeeded = LEFTWIDTH - stripANSI(holdString).length();
                // add the padding and hold box to the row
                arrayOfRows[i] += generatePadding(paddingNeeded) + holdString;
            }

            // The stats
            else if (i>=STATSTOP && i <= STATSBOT) {
                arrayOfRows[i] += getStatsString(i - STATSTOP, tetrominoStats);
            }
            // Put blank (filled with spaces) lines in the other rows
            else arrayOfRows[i] += generatePadding(LEFTWIDTH);

        }
        return arrayOfRows;
    }

    /**
     * Convert a given board to an array of strings. Cuts the top 4 rows off.
     *
     * @param board The board to be printed. (must be at least 4 rows tall)
     * @return board The board to be printed.
     */
    private String[] getBoardStrings(Block[][] board) {
        String[] boardStrings = new String[board.length];
        for(int r = 0; r < board.length - 2; r++) {
            switch(r) {
            case 0: boardStrings[r] = getBoardTopBot(TLCHAR, TRCHAR); break;
            case 21: boardStrings[r] = getBoardTopBot(BLCHAR, BRCHAR); break;
            default: boardStrings[r] = getBoardRow(board[r + 3], r + 3); break;
            }
        }
        return boardStrings;
    }


    /**
     * Returns string representations of rows from the right of the board.
     *
     * @param username The current player's name.
     * @param gameScore The current player's score.
     * @param linesCleared The amount of lines the current player has cleared.
     * @param highscore The highest score on file.
     * @param scoreName The name of the person with the highest score on file.
     * @param nextArray The array representing the next Tetromino.
     * @param currentDifficulty An integer representing the current difficulty.
     *
     * @return An array of strings where each string is a row of the right side
     * of the board.
     */
    private String[] getRightSide(String username, long gameScore,
                                  long linesCleared, long highscore,
                                  String scoreName, Block[][] nextArray,
                                  String currentDifficulty) {

        String[] arrayOfRows = new String[CONSOLEHEIGHT];
        for (int r=0; r < arrayOfRows.length; r++) {


            // go through each row and add relevant strings
            switch (r) {
                // Add welcome
            case 0:
                arrayOfRows[r] = centerString("Welcome to Tetris, " + username, RIGHTWIDTH);
                break;
            case 1: case 2: case 3: case 4: 
                arrayOfRows[r] = getTetrisString(r - 1);
                break;
            case 5:
                arrayOfRows[r] = getHighScore(highscore,scoreName);
                break;
            case 7:
                arrayOfRows[r] = getCurrentScore(gameScore, highscore);
                break;
            case 8:
                arrayOfRows[r] = getLinesCleared(linesCleared);
                break;

            case 9: case 10: case 11: case 12: case 13: case 14:
                arrayOfRows[r] = getNextBoxString(nextArray, r-9);
                break;
            case 19:
                arrayOfRows[r] = getDifficultyString(currentDifficulty);
                break;
            default: arrayOfRows[r] = generatePadding(RIGHTWIDTH);
            }


        }
        return arrayOfRows;
    }

    /**
     * Returns the current time TODO
     * @return A pretty string representing the current time.
     */
    private String getTimerString() {
        return " ";
    }

    /**
     * Create an array from a Tetromino.
     *
     * @param tetromino The tetromino to be turned into a block array.
     * @return An array of arrays of blocks representing the current tetromino.
     */
    private Block[][] createBlockArray(Tetromino tetromino) {
        int xRef = 0, yRef = 0;
        Block[] tetrominoData = new Block[4];
        if (tetromino != null)  {
            xRef = tetromino.getXReference();
            yRef = tetromino.getYReference();
            tetrominoData = tetromino.getBlockArray();
        }
        Block[][] tetrominoArray = new Block[4][4];
        for(Block block:tetrominoData) {
            if (block != null) {
                int x = block.getXPosition();
                int y = block.getYPosition();
                tetrominoArray[y - yRef][x - xRef] = block;
            }
        }
        return tetrominoArray;
    }

    /**
     * Combines the board with the current tetromino and its ghost.
     *
     * @param currentTetromino The current tetromino to be played.
     * @param ghostTetromino The ghost of the current tetromino.
     * @param board The current game board.
     * @return An array of arrays of blocks representing the current game board
     * with the tetromino being played.
     */
    private Block[][] combine(Tetromino currentTetromino,
                                Tetromino ghostTetromino,
                                Board board) {
        Block[][] boardArray = board.getCurrentBoard();
        Block[] cTetrominoArray = currentTetromino.getBlockArray();
        Block[] gTetrominoArray = ghostTetromino.getBlockArray();

        for(Block block:gTetrominoArray) {
            int x = block.getXPosition();
            int y = block.getYPosition();
            boardArray[y][x] = block;
        }
        for(Block block:cTetrominoArray) {
            int x = block.getXPosition();
            int y = block.getYPosition();
            boardArray[y][x] = block;
        }

        return boardArray;
    }


    /**
     * TODO returns the amount of times a tetromino has been created.
     *
     * @param tetrominoType  The type of tetromino to query.
     * @param TetrominoFactory  TODO the tetromino generator.
     */
    private int getTetrominoData(int type, int[] tetrominoStats) {
        switch (type) {
        case 0: case 1: case 2: case 3: case 4: case 5: case 6:
            return tetrominoStats[type];
            // return total if type == 7
        case 7:
            int sum = 0;
            for (int i=0; i < type; i++) {
                sum += tetrominoStats[i];
            }
            return sum;
        default: return -1;
        }
    }


    /**
     * Returns the strings needed to display stats. 0 is the row "S T A T S".
     * The last available row is 11.
     * @param row Which row to return a string representation of.
     */
    private String getStatsString(int row, int[] tetrominoStats) {
        String tetrominoType = "";
        String tetrominoColor ="";
        int tetrominoStat;
        if (row >= 2 && row <= 8) {
            tetrominoColor = ANSI.color(row - 2);
        }

        switch(row) {
        default: return generatePadding(LEFTWIDTH);
        case 0: return centerString("- - S T A T S - -", LEFTWIDTH);
        case 2: tetrominoType = "I"; break;
        case 3: tetrominoType = "O"; break;
        case 4: tetrominoType = "T"; break;
        case 5: tetrominoType = "S"; break;
        case 6: tetrominoType = "Z"; break;
        case 7: tetrominoType = "J"; break;
        case 8: tetrominoType = "L"; break;
        case 10: return centerString("Total Tetrominos:", LEFTWIDTH);
        case 11:
            return centerString(
                                formatNumber(
                                             getTetrominoData(7, tetrominoStats),
                                             8
                                             ),
                                LEFTWIDTH
                                );
        }
        String rowStr = tetrominoType
            + ": "
            + formatNumber(getTetrominoData(row - 2, tetrominoStats), 6);

        rowStr = centerString(rowStr, LEFTWIDTH);
        rowStr = tetrominoColor + rowStr + ANSI.RESET;
        return rowStr;

    }

    /**
     * Returns the string representing the given row of the hold box.
     *
     * @param row The row to be returned
     * @param holdArray  The array of arrays representing the currently held tetromino.
     * @param isHoldMoveAvailable A boolean indicating if the player can use the
     * hold move function.
     * @return String representing row of the hold box. Empty string if outside
     * box rows.
     */
    private String getHoldBoxString(int row, Block[][] holdArray,
                                    boolean isHoldMoveAvailable) {
        String holdString = "HOLD";
        // make text in border blink green if can hold.
        if (isHoldMoveAvailable) {
            holdString = ANSI.BLINK + ANSI.GREEN + holdString + ANSI.RESET;
        }
        // make text in border solid red if can't hold.
        else holdString = ANSI.RED + holdString + ANSI.RESET;


        // Generate strings
        if (row == 0) {
            return getBoxTopBot(TLCHAR, "", holdString);
        }
        else if (row >= 1 && row < 5) {
            return generatePadding(1 + BLOCKWIDTH * 4)
                + getBoxRowString(holdArray[row - 1], true);
        }
        else if (row == 5) {
            return getBoxTopBot(BLCHAR, "", holdString);
        }
        return "";
    }

    /**
     * Returns the string representing the given row of the hold box.
     *
     * @param nextArray The array of arrays representing the currently held
     * tetromino.
     * @param row  The row to generate a string for.
     * @return String representing row of the next tetromino box. Empty string if outside
     * box rows.
     */
    private String getNextBoxString(Block[][] nextArray, int row) {
        String nextString = "NEXT";
        if (row == 0)  {
            return getBoxTopBot("", TRCHAR, nextString);
        }
        else if (row >= 1 && row < 5) {
            return getBoxRowString(nextArray[row - 1], false);
        }
        else if (row == 5)  {
            return getBoxTopBot("", BRCHAR, nextString);
        }
        return "";
    }

    /**
     * Returns the top and bottom rows of a tetromino box.
     *
     * @param startPiece The string that should start the row.
     * @param endPiece The string that should end the row.
     * @param word The string that should be embedded in the border.
     * @return The top or bottom row of a tetromino box.
     */
    private String getBoxTopBot(String startPiece, String endPiece, String word) {
        String top =  startPiece;
        for(int i = 0; i < PREVIEWWIDTH; i++) {
            if (i == 3) {
                top += word;
            }
            else if (i > 3 && i < 3 + 4) {
                // top += word.charAt(i - 3);
                top+="";
            }
            else top += HCHAR;
        }
        top += endPiece;
        return top;
    }

    /**
     *  Generates the string representation of a row for the middle of a
     *  tetromino box.
     *
     * @param boxRow The blocks representing the current row of the box.
     * @param isLeft boolean, true if generating a box to the left of the board.
     * @return String representation of given box row.
     */
    private String getBoxRowString(Block[] boxRow, boolean isLeft) {
        String boxRowString = "";
        for(Block block:boxRow) {
            boxRowString += getBlockString(block);
        }
        if (isLeft) {
            boxRowString = VCHAR + getBlockString(null) + boxRowString;
        }
        else {
            boxRowString = getBlockString(null) + boxRowString + VCHAR;
        }
        return boxRowString;
    }

    /**
     * Get a string representation of a block.
     *
     * @param block The block to be converted to a string.
     * @return String representation of given block.
     */
    private String getBlockString(Block block) {
        String Blockchar = BLOCKCHAR;
        String blockStr = "";
        if (block != null) {
            blockStr += ANSI.color(block.getColor());
            if (block.getIsGhost()) {
                Blockchar = GBLKCHAR;
            }
        }
        else blockStr += ANSI.BLACK;
        for (int i =0; i<BLOCKWIDTH; i++) {
            blockStr += Blockchar;
        }
        blockStr += ANSI.RESET;
        return blockStr;
    }

    /**
     * Generates a string for the top or bottom border of the board.
     *
     * @leftChar The String to be printed on the left end of the border.
     * @leftChar The String to be printed on the right end of the border.
     */
    private String getBoardTopBot(String leftChar, String rightChar) {
        String row = "";
        for(int i= 0; i < BOARDCHARWIDTH; i++) {
            switch(i) {
            case 0: row += leftChar; break;
            case BOARDCHARWIDTH - 1: row += rightChar; break;
            default: row += HCHAR; break;
            }
        }
        return row;
    }

    /**
     * Generates a string representing a row for the board.
     *
     * @param boardRow   The block array of the row to be processed.
     * @param rowNum   The row number of the board being processed.
     * @return A string representing the given board row.
     */
    private String getBoardRow(Block[] boardRow, int rowNum) {
        String rowString = "";
        // add 3 because top 4 rows are removed
        if (rowNum == HOLDBOT + 3|| rowNum == HOLDTOP + 3) {
            rowString += VHLSPLIT;
        }
        else rowString += VCHAR;
        for(Block block:boardRow) {
            rowString += getBlockString(block);
        }
        if (rowNum == PREVIEWBOT + 3|| rowNum == PREVIEWTOP + 3) {
            rowString += VHRSPLIT;
        }
        else rowString += VCHAR;
        return rowString;
    }


    /**
     * Generates a string for the previous highscore.
     *
     * @param highscore  The previous highscore.
     * @param scoreName  The player that achieved the previous highscore.
     */
    private String getHighScore(long highscore, String scoreName) {
        String highscoreStr = "Score to Beat: " + formatNumber(highscore, 9);
        highscoreStr = centerString(highscoreStr, RIGHTWIDTH);
        highscoreStr = highscoreStr.substring(0, highscoreStr.length() - 6);
        if (scoreName.length() > 6) {
            scoreName = scoreName.substring(0, 5);
        }

        return ANSI.RED + highscoreStr + ANSI.RESET
            + ANSI.GREEN + scoreName + ANSI.RESET;
    }


    /**
     * Generates a string for the current game score.
     *
     * @param gameScore  The current score.
     * @param highscore  The previous highscore.
     * @return A pretty string of the current score.
     */
    private String getCurrentScore(long gameScore, long highscore) {
        String curScoreString = "Current Score: ";
        String scoreStr = curScoreString + formatNumber(gameScore, 9);
        scoreStr = centerString(scoreStr, RIGHTWIDTH);

        if (gameScore >= highscore) {
            return ANSI.BRIGHT_YELLOW + ANSI.BLINK + scoreStr + ANSI.RESET;
        }
        else return scoreStr;
    }

    /**
     * Produces a string representation for lines cleared.
     *
     * @param linesCleared  The amount of lines that have been cleared.
     * @return Nicely formatted string for the current lines cleared.
     */
    private String getLinesCleared(long linesCleared) {
        return centerString("Lines Cleared: "
                            + formatNumber(linesCleared, 9), RIGHTWIDTH);
    }

    /**
     * Produces a string representation for the difficulty.
     *
     * @param currentDifficulty The current difficulty.
     * @return Nicely formatted string representing the current difficulty.
     */
    private String getDifficultyString(String currentDifficulty) {
        return centerString("Current Difficulty: "
                            + currentDifficulty, RIGHTWIDTH);
    }

    /**
     * Returns a row of the Tetris ASCII ART.
     * @param row  The row of the ART to get, index 0.
     * @return The string corresponding to given row. Returns null if not a
     * valid row.
     */
    private String getTetrisString(int row) {
        if (row < getTetrisWord().length || row < 0) {
            return centerString(getTetrisWord()[row], RIGHTWIDTH);
        }
        return null;
    }

    /**
     * Get the ASCII art for the Tetris word..
     *
     * @return Array of strings where each string is a row of the art.
     */
    private String[] getTetrisWord() {
        String[] tetris = new String[4];
        // taken from:
        // http://patorjk.com/software/taag/#p=display&v=3&f=Small%20Slant&t=TETRIS
        tetris[0] = " ___________________  ________";
        tetris[1] = "/_  __/ __/_  __/ _ \\/  _/ __/";
        tetris[2] = " / / / _/  / / / , _// /_\\ \\  ";
        tetris[3] = "/_/ /___/ /_/ /_/|_/___/___/  ";
        return tetris;
    }


}
