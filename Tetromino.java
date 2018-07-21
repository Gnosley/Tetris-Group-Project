import java.util.Arrays;

public class Tetromino {
    // {color, size} {block1} {block 2} {block 3} {block 4}
    private final int[][][] tetrominoData = {
        // I-Tetromino
        {
            {0, 4}, {0, 1}, {1, 1}, {2, 1}, {3, 1}
        },

        // O-Tetromino
        {
            {1, 2}, {0, 0}, {0, 1}, {1, 0}, {1, 1}
        },

        // T-Tetromino
        {
            {2, 3}, {1, 0}, {1, 1}, {0, 1}, {2, 1}
        },

        // S-Tetromino
        {
            {3, 3}, {1, 0}, {1, 1}, {2, 1}, {2, 2}
        },

        // Z-Tetromino
        {
            {4, 3}, {0, 0}, {1, 0}, {1, 1}, {2, 1}
        },

        // J-Tetromino
        {
            {5, 3}, {2, 0}, {1, 0}, {1, 1}, {1, 2}
        },

        // L-Tetromino
        {
            {6, 3}, {2, 2}, {1, 0}, {1, 1}, {1, 2}
        }
    };

    private Block[] tetrominoArray;
    private int xReferencePosition;
    private int yReferencePosition;
    private int size;
    private int type;

    public Tetromino(int xRef, int yRef) {
        xReferencePosition = xRef;
        yReferencePosition = yRef;
        type = randomNum(0, 6);
        size = tetrominoData[type][0][1];
        tetrominoArray = generateTetrominoArray(type);
    }

    public Tetromino(Tetromino tetromino) {
        this.xReferencePosition = tetromino.getXReference();
        this.yReferencePosition = tetromino.getYReference();
        this.tetrominoArray = generateTetrominoArray(type);
        this.size = tetromino.getSize();
        this.type = tetromino.getType();
    }

    private int randomNum(int start, int end) {
        double rNum = Math.random();
        rNum *= end + 1 - start;
        rNum += start;

        return (int)rNum;
    }

    private Block[] generateTetrominoArray(int num) {
        if(num < 0 || num > 6) num = 0;
        int color = tetrominoData[num][0][0]; 
        Block[] tetrominoArray = new Block[4];
        for (int i=1; i< tetrominoData[num].length; i++) {
            int x = tetrominoData[num][i][0];
            int y = tetrominoData[num][i][1];

            Block block = new Block(color,
                                    xReferencePosition + x,
                                    yReferencePosition + y);
            tetrominoArray[i-1] = block;
        }
        return tetrominoArray;
    }

    // DONE not tested
    private void rotate(char direction) {
        // We can rotate counter clockwise by {x, y} -> {y, size - 1 - x}
        // We can rotate clockwise by {x, y} -> {size - 1 - y, x}
        int rotSize = size - 1; // for the function to rotate we need size - 1
        for(Block block:tetrominoArray) {
            int x = block.getXPosition();
            int y = block.getYPosition();
            int newX = y - yReferencePosition; // subtract reference positions
            int newY = x - xReferencePosition; // for rotation maths to work.

            // counter clockwise
            if (direction == 'q') {
                newY = rotSize - newY;
            }

            // clockwise
            else if (direction == 'e') {
                newX = rotSize - newX;
            }
            newX += xReferencePosition;
            newY += yReferencePosition;
            block.setXPosition(newX);
            block.setYPosition(newY);
        }
    }

    private void move(char direction) {
        int xMovement = 0;
        int yMovement = 0;

        switch(direction) {
            case 'a': xMovement = -1; break;
            case 's': yMovement = 1; break;
            case 'd': xMovement = 1; break;
        }

        xReferencePosition += xMovement;
        yReferencePosition += yMovement;

        for(Block block:tetrominoArray) {
            block.setXPosition(block.getXPosition() + xMovement);
            block.setYPosition(block.getYPosition() + yMovement);
        }
    }

    public Tetromino doMove(char moveType) {
        Tetromino movedTetromino = new Tetromino(this);
        switch(moveType) {
            case 'a':
            case 's':
            case 'd': movedTetromino.move(moveType); break;
            case 'q':
            case 'e': movedTetromino.rotate(moveType); break;
        }
        return movedTetromino;
    }

    public Block[] getBlockArray() {
        Block[] copy = Arrays.copyOf(tetrominoArray, tetrominoArray.length);
        return copy;
    }

    public int getXReference() {
        return xReferencePosition;
    }

    public int getYReference() {
        return yReferencePosition;
    }

    public int getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

}
