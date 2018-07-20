public class Printer {

    private final int BOARDWIDTH = 10;
    private final int BOARDHEIGHT = 24;
    // How many characters wide each block should be
    private final int BLOCKWIDTH = 2;
    private final int BOARDCHARWIDTH = BOARDWIDTH * BLOCKWIDTH;
    // Block Character
    private final String BLOCKCHAR = "\u2588";
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

    // public void print(Tetromino currentTetromino,
    //                   Tetromino nextTetromino,
    //                   Board board) {
    // }

    // private Block[][] combine(Tetromino currentTetromino, Board board) {
    //     Block[][] boardArray = board.getCurrentBoard();
    //     Block[] tetromino = currentTetromino.getBlockArray();

    //     for(Block block:tetromino) {
    //         int x = block.getXPosition();
    //         int y = block.getYPosition();
    //         boardArray[y][x] = block;
    //     }

    //     return boardArray;
    // }

    private String[] getHeader() {
        String[] header = new String[3];
        String header1 = "";
        for(int i =0; i <= BOARDCHARWIDTH * 3 / 4; i++) {
            if(i < BOARDCHARWIDTH / 4) {
                header1 += " ";
            }
            else if (i == BOARDCHARWIDTH / 4) {
                header1 += TLCHAR;
            }
            else if (i > BOARDCHARWIDTH / 4 && i < BOARDCHARWIDTH * 3 / 4) {
                header1 += HCHAR;
            }
            else if (i == BOARDCHARWIDTH * 3 / 4) {
                header1 += TRCHAR;
            }
        }
        header[0] = header1;
        return header;
    }

    public static void main(String[] args) {
        Printer printer = new Printer();
        System.out.println(printer.getHeader()[0]);
    }

}
