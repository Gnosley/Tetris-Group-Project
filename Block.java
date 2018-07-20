public class Block{
    private int color = 0;
    private int[] position ; // position is an array. [x, y]

    public Block(int color, int[] position) {
        this.color = color;
        this.position = position;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getPosition() {
        return position; // privacy leak, but will deal with it.
    }

}
