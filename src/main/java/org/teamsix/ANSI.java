package org.teamsix;

public class ANSI {
    // clear from cursor to end of screen
    public static final String CLEARSCREEN = "\u001B[0J";
    // clear from cursor to end of screen
    public static final String CLEAR_LINE = "\u001B[2K";
    // reset text to default
    public static final String RESET = "\u001B[39m";
    // set text color
    public static final String BLACK = "\u001B[38;5;16m";
    public static final String RED = "\u001B[38;5;196m";
    public static final String GREEN = "\u001B[38;5;40m";
    public static final String YELLOW = "\u001B[38;5;214m"; // Really Orange
    public static final String BLUE = "\u001B[38;5;21m";
    public static final String PURPLE = "\u001B[38;5;53m";
    public static final String CYAN = "\u001B[38;5;51m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_YELLOW = "\u001B[38;5;226m"; // Yellow
    // move cursor to top left of terminal
    public static final String RESET_CURSOR = "\u001B[1;1H";
    // move cursor down 80 lines
    public static final String SET_CURSOR_BOT = "\u001B[80B";

    /**
     * Takes an int and returns a String for an ANSI escape sequence. Maps as follows:
     * <ul> <li> 0 => cyan </li>
     *      <li> 1 => bright yellow </li>
     *      <li> 2 => purple </li>
     *      <li> 3 => green </li>
     *      <li> 4 => red </li>
     *      <li> 5 => blue </li>
     *      <li> 6 => yellow </li>
     *      <li> n>6 => "" </li>
     * </ul>
     *
     * @param colorNum The int of the color wanted.
     * @return String with an ANSI escape sequence for a color.
     */
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
