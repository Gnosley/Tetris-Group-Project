public class ANSI {
    public static final String ESC = "\u001B";
    public static final String CLEARSCREEN = "\u001B[1J";
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m"; // Really Orange
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_YELLOW = "\u001B[93m"; // Yellow

    public static String color(int colorNum){
        String[] colorCodes = {
            CYAN,
            BRIGHT_YELLOW,
            PURPLE,
            GREEN,
            RED,
            BLUE,
            YELLOW
        };

        if (0 <= colorNum && colorNum < colorCodes.length) {
            return colorCodes[colorNum];
        }
        return "";
    }
}
