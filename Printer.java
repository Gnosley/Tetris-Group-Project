public class Printer {

    private final int BOARDWIDTH = 10;
    private final int BOARDHEIGHT = 24;
    private final int PREVIEWTOP = 6;
    private final int PREVIEWBOT = 13;
    // How many characters wide each block should be
    private final int BLOCKWIDTH = 2;
    private final int PREVIEWWIDTH = 5 * BLOCKWIDTH;
    private final int BOARDCHARWIDTH = BOARDWIDTH * BLOCKWIDTH + 2;
    // Block Character
    private final String BLOCKCHAR = "\u2588";
    // Spacing between Blocks
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

    public void print(Tetromino currentTetromino,
                      Tetromino nextTetromino,
                      Board board) {
        Block[][] combinedBoard = combine(currentTetromino, board);
        Block[][] previewArray = createPreviewArray(nextTetromino);
        String[] outString = new String[22];
        outString[0] = getBoardTop();

        String[] controlStrings = new String[5];
        controlStrings[0] = "Controls:\t'a' - move left";
        controlStrings[1] = "\t\t's' - move down";
        controlStrings[2] = "\t\t'd' - move right";
        controlStrings[3] = "\t\t'e' - rotate clockwise";
        controlStrings[4] = "\t\t'q' - rotate counter-clockwise";

        for (int i = 4; i < BOARDHEIGHT; i++) {
            String rowString = "";
            int curRow = i - 3;
            rowString += getBoardRow(combinedBoard[i], curRow);
            if (curRow <= controlStrings.length) {
                rowString += controlStrings[curRow - 1];
            }

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

            if (curRow > PREVIEWBOT && curRow < 14 + getTetrisWord().length) {

                rowString += (getTetrisWord()[curRow - 14]);
            }

            outString[curRow] = rowString;
        }

        outString[21] = getBoardBot();


        System.out.print(ANSI.CLEARSCREEN);
        for(String row:outString) {
            System.out.println(row);
        }
    }

    private Block[][] combine(Tetromino currentTetromino, Board board) {
        Block[][] boardArray = board.getCurrentBoard();
        Block[] tetrominoArray = currentTetromino.getBlockArray();

        for(Block block:tetrominoArray) {
            int x = block.getXPosition();
            int y = block.getYPosition();
            boardArray[y][x] = block;
        }

        return boardArray;
    }

    public Block[][] createPreviewArray(Tetromino nextTetromino) {
        int xRef = nextTetromino.getXReference();
        int yRef = nextTetromino.getYReference();
        Block[] nextTetrominoArray = nextTetromino.getBlockArray();
        Block[][] previewArray = new Block[4][4];
        for(Block block:nextTetrominoArray) {
            int x = block.getXPosition();
            int y = block.getYPosition();
            previewArray[y - yRef][x - xRef] = block;
        }
        return previewArray;
    }

    public String getPreviewRowString(Block[] previewRow) {
        String previewRowString = BLOCKSPC + getBlockString(null);
        for(Block block:previewRow) {
            previewRowString += getBlockString(block);
        }
        previewRowString += BLOCKSPC + VCHAR;
        return previewRowString;
    }

    public String getPreviewTopBot(String endPiece) {
        String top = BLOCKSPC;
        for(int i = 0; i < PREVIEWWIDTH; i++) {
            if (i % 2 == 0) {
                top += BLOCKSPC;
            }
            top += HCHAR;
        }
        top += BLOCKSPC + endPiece;
        return top;
    }

    public String getBoardTop() {
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

    public String getBoardBot() {
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

    public String getBoardRow(Block[] boardRow, int rowNum) {
        String rowString = VCHAR;
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
        String blockStr = BLOCKSPC;
        if (block != null) {
            blockStr += ANSI.color(block.getColor());
        }
        else blockStr += ANSI.BLACK;
        for (int i =0; i<BLOCKWIDTH; i++) {
            blockStr += BLOCKCHAR;
        }
        blockStr += ANSI.RESET;
        return blockStr;
    }

    private String[] getTetrisWord() {
        String[] tetris = new String[4];
        tetris[0] = "\t ___________________  ________"; // http://patorjk.com/software/taag/#p=display&v=3&f=Small%20Slant&t=TETRIS
        tetris[1] = "\t/_  __/ __/_  __/ _ \\/  _/ __/";
        tetris[2] = "\t / / / _/  / / / , _// /_\\ \\  ";
        tetris[3] = "\t/_/ /___/ /_/ /_/|_/___/___/  ";
        return tetris;
    }

}
