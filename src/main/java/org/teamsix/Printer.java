package org.teamsix;

import org.jline.terminal.Terminal;
public class Printer {

    private final int BOARDWIDTH = 10;
    private final int BOARDHEIGHT = 24;


    private final int PREVIEWTOP = 9; // where the preview window starts (which row)
    private final int PREVIEWBOT = 16;// where the preview window ends (which row)

    private final int HOLDTOP = 2; // where the hold window starts (which row)
    private final int HOLDBOT = 9;// where the hold window ends (which row)

    private final int BLOCKWIDTH = 2; // How many characters wide each block should be

    private final int PREVIEWWIDTH = 5 * BLOCKWIDTH; // Preview window width
    private final int HOLDWIDTH = 5 * BLOCKWIDTH; // Hold window width
    private final int BOARDCHARWIDTH = BOARDWIDTH * BLOCKWIDTH + 2;//board width

    // Block Character
    private final String BLOCKCHAR = "\u2588";
    // Ghost Block Character
    private final String GBLKCHAR = "\u2591";


    // Spacing between Blocks: "" is no spacing
    private final String BLOCKSPC = ""; // \u200A works well in emacs

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

    /**
     * Call this function with all the game data to print out the current game state to console with pretty colors.
     */
    public void print(Tetromino currentTetromino,
                      Tetromino nextTetromino,
                      Tetromino ghostTetromino,
                      Tetromino holdTetromino,
                      Board board,
                      Terminal terminal,
                      long gameScore,
                      long linesCleared,
                      boolean holdAvailable) {
        Block[][] combinedBoard = combine(currentTetromino, ghostTetromino, board);
        Block[][] previewArray = createPreviewArray(nextTetromino);
        Block[][] holdArray = createPreviewArray(holdTetromino);
        String[] outString = new String[22];
        String bufferForHold = BLOCKSPC + " ";
        for(int r =0; r< 5 ; r++){
            bufferForHold += BLOCKSPC + "  ";
        }
        bufferForHold+=BLOCKSPC;
        outString[0] = bufferForHold + getBoardTop();

        String[] controlStrings = new String[7];

        for (int i = 4; i < BOARDHEIGHT; i++) {
            String rowString = "";
            int curRow = i - 3;
            // if (curRow == HOLDTOP) {
            //     rowString += getPreviewTopBot(TRCHAR);
            // }
            if (curRow > HOLDTOP && curRow < HOLDBOT) {
                if (curRow == HOLDTOP + 1 || curRow == HOLDBOT - 1) {
                    rowString += getHoldRowString(new Block[4]);
                }
                else {
                    int pIndex = curRow - HOLDTOP - 2;
                    rowString += getHoldRowString(holdArray[pIndex]);
                }
            }
            else if (curRow == HOLDTOP) {
                rowString += getHoldTopBot(TLCHAR);
            }
            else if (curRow == HOLDBOT) {
                rowString += getHoldTopBot(BLCHAR);
            }
            else {
                rowString += bufferForHold;
            }

            rowString += getBoardRow(combinedBoard[i], curRow);
            // if (curRow <= controlStrings.length) {
            //     rowString += controlStrings[curRow - 1];
            // }

            if (curRow == PREVIEWTOP) {
                rowString += getPreviewTopBot(TRCHAR);
            }

            if (curRow > PREVIEWTOP && curRow < PREVIEWBOT) {
                if (curRow == PREVIEWTOP + 1 || curRow == PREVIEWBOT - 1) {
                    rowString += getPreviewRowString(new Block[4]);
                }
                else {
                    int pIndex = curRow - PREVIEWTOP - 2;
                    rowString += getPreviewRowString(previewArray[pIndex]);
                }
            }

            if (curRow == PREVIEWBOT) {
                rowString += getPreviewTopBot(BRCHAR);
            }

            if (curRow >=0 && curRow < 1 + getTetrisWord().length) {

                rowString += "   " + (getTetrisWord()[curRow - 1]);
            }

            if (curRow == 1 + getTetrisWord().length) {
                rowString += "      Game Score:  "
                    + ANSI.YELLOW
                    + String.format("%09d", gameScore)
                    + ANSI.RESET;
            }

            if (curRow == 2 + getTetrisWord().length) {
                rowString += "   Lines Cleared:  "
                    + ANSI.BRIGHT_YELLOW
                    + String.format("%09d", linesCleared)
                    + ANSI.RESET;
            }

            if (curRow == 3 + getTetrisWord().length) {
                rowString += "  Hold Available:  ";
                if (holdAvailable) {
                    rowString += ANSI.GREEN + "Yes";
                }
                else rowString += ANSI.RED + " No";
                rowString += ANSI.RESET;
            }

            outString[curRow] = rowString;
        }

        outString[21] = bufferForHold + getBoardBot();


        terminal.writer().print(ANSI.CLEARSCREEN);
        terminal.writer().println("\t\tPress Esc twice to quit! Press 'h' for help!");
        for(String row:outString) {
            terminal.writer().println(row);
        }
        terminal.flush();
    }

    public void printHelp(Terminal terminal) {
        terminal.writer().print(ANSI.CLEARSCREEN);
        terminal.writer().println("\t\tPress any key other than 'h' to return to game.");
        String[] controlStrings = new String[8];
        controlStrings[0] = "     Controls:\t'a' or LEFT - move left";
        controlStrings[1] = "\t\t's' or DOWN - move down";
        controlStrings[2] = "\t\t'd' or RIGHT - move right";
        controlStrings[3] = "\t\t'e' or UP - rotate clockwise";
        controlStrings[4] = "\t\t'q' - rotate counter-clockwise";
        controlStrings[5] = "\t\t'f' or SPACE - drop";
        controlStrings[6] = "\t\t'w' or TAB - hold";
        controlStrings[7] = "\t\t ESCAPE ESCAPE  - quit";
        for(String row:controlStrings) {
            terminal.writer().println(row);
        }
        terminal.flush();
    }

    private Block[][] combine(Tetromino currentTetromino, Tetromino ghostTetromino, Board board) {
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

    private Block[][] createPreviewArray(Tetromino nextTetromino) {
        int xRef = 0, yRef = 0;
        Block[] nextTetrominoArray = new Block[4];
        if (nextTetromino != null)  {
            xRef = nextTetromino.getXReference();
            yRef = nextTetromino.getYReference();
            nextTetrominoArray = nextTetromino.getBlockArray();
        }
        Block[][] previewArray = new Block[4][4];
        for(Block block:nextTetrominoArray) {
            if (block != null) {
                int x = block.getXPosition();
                int y = block.getYPosition();
                previewArray[y - yRef][x - xRef] = block;
            }
        }
        return previewArray;
    }

    private String getHoldRowString(Block[] holdRow) {
        String holdRowString = BLOCKSPC + VCHAR + getBlockString(null);
        for(Block block:holdRow) {
            holdRowString += getBlockString(block);
        }
        holdRowString += BLOCKSPC;
        // holdRowString += BLOCKSPC + getBlockString(null);
        return holdRowString;
    }

    private String getPreviewRowString(Block[] previewRow) {
        String previewRowString = BLOCKSPC + getBlockString(null);
        for(Block block:previewRow) {
            previewRowString += getBlockString(block);
        }
        previewRowString += BLOCKSPC + VCHAR;
        return previewRowString;
    }

    private String getHoldTopBot(String startPiece) {
        String top =  BLOCKSPC + startPiece;
        String word = "HOLD";
        for(int i = 0; i < PREVIEWWIDTH; i++) {
            if (i % 2 == 0) {
                top += BLOCKSPC;
            }
            if (i >= 3 && i < 3 + word.length()) {
                top += word.charAt(i - 3);
            }
            else top += HCHAR;
        }
        top += BLOCKSPC;
        return top;
    }

    private String getPreviewTopBot(String endPiece) {
        String top = BLOCKSPC;
        String word = "NEXT";
        for(int i = 0; i < PREVIEWWIDTH; i++) {
            if (i % 2 == 0) {
                top += BLOCKSPC;
            }
            if (i >= 3 && i < 3 + word.length()) {
                top += word.charAt(i - 3);
            }
            else top += HCHAR;
       }
        top += BLOCKSPC + endPiece;
        return top;
    }

    private String getBoardTop() {
        String top = TLCHAR;
        for(int i = 0; i < BOARDCHARWIDTH - 2; i++) {
            if (i % 2 == 0) {
                top += BLOCKSPC;
            }
            top += HCHAR;
        }
        top += BLOCKSPC + TRCHAR;
        return top;
    }

    private String getBoardBot() {
        String bot = BLCHAR;
        for(int i = 0; i < BOARDCHARWIDTH - 2; i++) {
            if (i % 2 == 0) {
                bot += BLOCKSPC;
            }
            bot += HCHAR;
        }
        bot += BLOCKSPC + BRCHAR;
        return bot;
    }

    private String getBoardRow(Block[] boardRow, int rowNum) {
        String rowString = "";
        if (rowNum == HOLDBOT || rowNum == HOLDTOP) {
            rowString += VHLSPLIT;
        }
        else rowString += VCHAR;
        for(Block block:boardRow) {
            rowString += getBlockString(block);
        }
        rowString += BLOCKSPC;
        if (rowNum == PREVIEWBOT || rowNum == PREVIEWTOP) {
            rowString += VHRSPLIT;
        }
        else rowString += VCHAR;
        return rowString;
    }

    private String getBlockString(Block block) {
        String Blockchar = BLOCKCHAR;
        String blockStr = BLOCKSPC;
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
