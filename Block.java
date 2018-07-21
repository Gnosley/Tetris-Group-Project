public class Block{
    private int color = 0;
    private int xPosition;
    private int yPosition;

    public Block(int color, int x, int y) {
        xPosition = x;
        yPosition = y;
        this.color = color;
    }

    public Block(int color, int[] position) {
        this.color = color;
        xPosition = position[0];
        yPosition = position[1];
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public void setXPosition(int x) {
        xPosition = x;
    }

    public void setYPosition(int y) {
        yPosition = y;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }
}
