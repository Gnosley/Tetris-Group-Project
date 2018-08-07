package org.teamsix;

import junit.framework.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class ConsoleTest {

    @Test
    // Testing user input for options.
    // https://stackoverflow.com/a/31635737
    public void TestUserInputForGetOptions() {

        // test for 7 char
        String input = "TESTUSR\neasy";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String[] optionsArray = Console.getOptions();
        Assert.assertEquals("Username with 7 characters does not get returned when entered.",optionsArray[0], "TESTUSR");
        Assert.assertEquals("When 'easy' is entered, 'easy' is not returned.",optionsArray[1], "easy");

        // test for 1 char
        input = "T\neasy";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        optionsArray = Console.getOptions();
        Assert.assertEquals("Username with 1 character does not get returned when entered.",optionsArray[0], "T      ");
        Assert.assertEquals("When 'easy' is entered, 'easy' is not returned.",optionsArray[1], "easy");

        // test for >7 char
        input = "Tetromino\neasy";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        optionsArray = Console.getOptions();
        Assert.assertEquals("Username with more than 7 characters does not get trimmed when entered.",optionsArray[0], "Tetromi");
        Assert.assertEquals("When 'easy' is entered, 'easy' is not returned.",optionsArray[1], "easy");

        // test for difficulty input
        input = "Natalie\nmedium";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        optionsArray = Console.getOptions();
        Assert.assertEquals("Username with 7 characters does not get returned when entered.",optionsArray[0], "Natalie");
        Assert.assertEquals("When 'medium' is entered, 'medium' is not returned.",optionsArray[1], "medium");

        // test for difficulty input
        input = "DustinD\nHARD";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        optionsArray = Console.getOptions();
        Assert.assertEquals("Username with 7 characters does not get returned when entered.",optionsArray[0], "DustinD");
        Assert.assertEquals("When 'HARD' is entered, 'HARD' is not returned.",optionsArray[1], "HARD");


    }


}
