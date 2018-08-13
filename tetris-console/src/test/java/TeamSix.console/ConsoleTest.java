package TeamSix.console;

import TeamSix.logic.*;
import junit.framework.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * JUnit tests for the Console class
 **/ 
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
        Assert.assertEquals("Username with 7 characters does not get returned when entered.","TESTUSR" ,optionsArray[0]);
        Assert.assertEquals("When 'easy' is entered, 'easy' is not returned.", "easy",optionsArray[1]);

        // test for 1 char
        input = "T\neasy";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        optionsArray = Console.getOptions();
        Assert.assertEquals("Username with 1 character does not get returned when entered.", "T      ",optionsArray[0]);
        Assert.assertEquals("When 'easy' is entered, 'easy' is not returned.", "easy",optionsArray[1]);

        // test for >7 char
        input = "Tetromino\neasy";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        optionsArray = Console.getOptions();
        Assert.assertEquals("Username with more than 7 characters does not get trimmed when entered.", "Tetromi",optionsArray[0]);
        Assert.assertEquals("When 'easy' is entered, 'easy' is not returned.", "easy",optionsArray[1]);

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
        Assert.assertEquals("Username with 7 characters does not get returned when entered.", "DustinD",optionsArray[0]);
        Assert.assertEquals("When 'HARD' is entered, 'HARD' is not returned.", "HARD",optionsArray[1]);


    }


}
