# Created By
Jesse Francis, Natalie Dean, Dustin Doell, Stephen Ng, Murray Evans

Final Project: CPSC 233 (Summer 2018) - Team 6 (Tutorial 2) 

# Installing Maven
 1. Go to [The Maven Download Page](https://maven.apache.org/download.cgi) and download 
 [apache-maven-3.5.4-bin.zip](http://apache.mirror.rafal.ca/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.zip)
 2. Unzip to a location of your choosing. i.e., `C:\Program Files\apache-maven-3.5.4\`
 3. Open up the control panel to `Control Panel\System and Security\System` (WIN + PAUSE)
 4. Go into `Advanced System Settings`.
 
    ![Advanced System Settings](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/ControlPanel.PNG "Control Panel")
 5. Press on `Environment Variables`.
 
    ![Environment Variables](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/EnvironmentVariables.PNG "Press Environment Variables")
 6. If you don't have a `JAVA_HOME` environment variable yet then:
    1. Press `New...`
    
       ![New JAVAHOME Environment Variable](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/NewEnvironmentVariable.PNG "Press New Environment Variable")
    2. Type in `JAVA_HOME` for the `Variable Name` field.
    
       ![JAVA_HOME for Variable Name](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/NewBrowseDirectory.PNG "Press Browse Directory...")
    3. Press `Browse Directory...` and choose the directory of your JDK.
    4. Press `OK`
 7. Double click on the `Path` variable.
 
    ![Double Click Path](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/DoubleClickPath.PNG "Double Click Path")
 8. Add Maven to your path:
    1. Click `Browse...`
    
       ![Browse](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/BrowsePath.PNG "Press Browse...")
    2. Find where you extracted in step 2 and select the bin directory
        i.e., `C:\Program Files\apache-maven-3.5.4\bin\`
        
        ![Bin Directory](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/SelectBin.PNG "Select Bin Directory")
 9. Add the JDK to your path if it is not already there:
    1. Click `Browse...`
    2. Find where your jdk is located and select the bin directory. i.e., `C:\Program Files\Java\jdk1.8.0_181\bin`
10. Open a command prompt and run `mvn -v` to confirm installation.

    **Note: If this does not work, but you have followed the steps above then please try restarting your computer**
# Running The Project
1. Download the zip file from D2L and unzip to a sensible location. i.e, `C:\Users\Jesse\Documents\Projects\Tetris-Group-Project`
2. Open a command prompt and change into the directory you have unzipped it to.
    
    If you type `dir` your command prompt should look something like: 
    ```
    2018-08-12  10:55 PM    <DIR>          .
    2018-08-12  10:55 PM    <DIR>          ..
    2018-08-12  10:44 PM                34 HighScore.txt
    2018-08-12  08:35 PM             1,057 pom.xml
    2018-08-12  10:55 PM             1,535 Readme.md
    2018-08-12  10:18 PM    <DIR>          tetris-console
    2018-08-12  10:18 PM    <DIR>          tetris-gui
    2018-08-12  10:18 PM    <DIR>          tetris-logic
    ```
3. Congratulations! You're in the right directory. Now type `mvn clean install` to compile and 
install the game.
4. Now you can play the game.
    1. To play in the console, run `mvn -pl tetris-console exec:java`
    
        For smoother gameplay, 
        1. Right click the cmd window and select `properties`
        
           ![Console Options](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/CmdOptions.PNG "Choose Properties")
        2. Change the `width` to `80` and the `height` to `24`
        
           ![Window Size Options](https://github.com/Gnosley/Tetris-Group-Project/blob/MAVEN-Test/Tetris%20Project%20Readme/WindowSizeOptions.PNG "Change Console Size")
        
        You may also change font size as you wish for larger or smaller graphics.
    1. To play with the gui, run `mvn -pl tetris-gui exec:java`

# Viewing The Source Code
The source code for all modules are in 
`Tetris-Group-Project\tetris-<MODULENAME>\src\main\java\TeamSix.<MODULENAME>`
For example, to see the Console game's source, go to 
`Tetris-Group-Project\tetris-console\src\main\java\TeamSix.console`
# Testing

## JUnit Testing
The majority of tests are stored in the `tetris-logic` module.
They are in the `Tetris-Group-Project\tetris-logic\src\test\java\TeamSix.logic\` directory.

There is also one test in the `tetris-console` module.
It is located at `Tetris-Group-Project\tetris-console\src\test\java\TeamSix.console\ConsoleTest.java`

#### To Test:
Make sure your command prompt is at `Tetris-Group-Project` as in step 2. of _Running The Project_.
Then run: `mvn test` to run all the tests.
To run a specific test, i.e., `BlockTest`, use `mvn -pl tetris-logic test -Dtest=BlockTest`. 
Unless you want to run `ConsoleTest`, then use `mvn -pl tetris-console test -Dtest=ConsoleTest` 
(as it is in the `tetris-console` module)

## Testing through the interface:
### For Console
1.
### For GUI
1.

