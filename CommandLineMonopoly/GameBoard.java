// 12/14/11 11:25 AM

/* FINAL PROJECT
   File Name: GameBoard.java
   Programmer: Mason Stevenson
   Date Last Modified: 12/14/11 8:00 PM

   Problem Statement:
   Create a GameBoard class the represents a Monopoly board 
   Use an array of GameSpace objects and fill them with data
   
   Overall Plan 
   1) create an array of GameSpace objects
   2) read in the GameSpace info from a text file and assign it to the objects

   Classes needed and Purpose 
   GameSpace class, Scanner, FileInputStream
   

*/
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameBoard
{
	GameSpace Space[] = new GameSpace[40];
	
	public GameBoard()
	{
		Scanner inputStream = null;
		
		try
		{
			inputStream = new Scanner(new FileInputStream("properties.txt"));
		}
		
		catch(FileNotFoundException e)
      	{
      		System.out.println("File readFile.txt was not found");
         	System.out.println("or could not be opened");
        	System.exit(0);
      	}
      	
      	for(int count = 0; count < Space.length; count++)
      	{
      		Space[count] = new GameSpace(inputStream.nextLine(), 
      		inputStream.next(), inputStream.nextInt(), inputStream.nextInt(), 
      		inputStream.nextInt(), inputStream.nextInt(), inputStream.nextInt(), 
      		inputStream.nextInt(), inputStream.nextInt());
      		inputStream.nextLine();
      		inputStream.nextLine();
      	}
      	inputStream.close();
      
	}
}