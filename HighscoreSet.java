import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.io.FileNotFoundException;


public class HighscoreSet {
    
	/**
	 * Sets the highscore by reading and writing from a text file
	 * @param username: username, inputted (7 characters exactly)
	 * @param updateScore: long, whatever the score is
	 */
    public static void setHighscores (String username, long updateScore) {

        try{
        Scanner input = new Scanner (new File("HighScore.txt"));
        List<String> userAndScore = new ArrayList<String>();
        
        //this is the string that needs to be written to the text file
        String newUserScore = username + Long.toString(updateScore);


        while (input.hasNextLine()) {
        	//run through text file and transfer to a String ArrayList
            userAndScore.add(input.nextLine());
        }

        //String ArrayList for username storage, long ArrayList for score storage
        List<String> user = new ArrayList<String>();
        List<Long> score = new ArrayList<Long>();

        for (int i=0; i<userAndScore.size(); i++) {
        	//newScore is just the score
            String newScore = userAndScore.get(i).substring(7);
            long newLongScore = Long.parseLong(newScore);

            score.add(newLongScore);
        }

        int placement = 0;
        //find what the placement of the new score is compared to the rest
        for (int a=0; a<score.size(); a++) {
            if (score.get(a)>updateScore) {
                placement = a+1;
            } 
        }

        PrintWriter output = new PrintWriter("HighScore.txt");
        
        userAndScore.add(null);

        //place the old data back onto the ArrayList in the new order
        if (placement != userAndScore.size()-1) {	
            for (int b = userAndScore.size()-1; b>placement; b--) {
                userAndScore.set(b, userAndScore.get(b-1));
            }    
        }
        userAndScore.set(placement, newUserScore);
        
        //print out onto the document
        for (int c=0; c<userAndScore.size(); c++) {
            output.println(userAndScore.get(c));
            }
            
        output.close();
        }
        
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
   
    public static void main (String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String user = "";
        do {
        System.out.println("input username TOTAL 7 CHARACTERS");
        user = keyboard.nextLine();
        }
        while(user.length()!=7);

        
        System.out.println("input score: ");
        long score = keyboard.nextLong();
        
        setHighscores(user, score);
        
    }
    
}