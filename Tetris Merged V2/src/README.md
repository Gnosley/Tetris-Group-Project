# Tetris Group Project

## Running:
### On Linux:
  Compile with: `javac -cp .:jansi-1.17.jar:jline-3.9.0.jar *.java`
  
  Run with: `java -cp .:jansi-1.17.jar:jline-3.9.0.jar Game`
### On Windows:
  Compile with: `javac -cp .;jansi-1.17.jar;jline-3.9.0.jar *.java`
  
  Run with: `java -cp .;jansi-1.17.jar;jline-3.9.0.jar Game`

***
This project uses [jansi](https://github.com/fusesource/jansi) and [jline3](https://github.com/jline/jline3) to get characters in from the terminal without requiring return to be pressed as well as outputting colors to the Windows 10 command prompt without any registry edits.
