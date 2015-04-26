/* FINAL PROJECT
   File Name: LetsPlayMonopoly.java
   Programmer: Mason Stevenson
   Date Last Modified: 12/14/11 8:00 PM

   Problem Statement:
   Write a program that simulates a Monopoly game using text in the command window.
   
   
   Overall Plan:
   The LetsPlayMonopoly is my driver class. It will create new Player classes as well as
   a new gameboard class and have them interact.
   
   1) Create new gameboard
   2) Create new player classes (two or more). Have the human player enter his name   
   3) Enter a loop that will keep running until all but one of the players is bankrupt
   4) Ask the user which command he wishes to do (Roll, Pay for Jail, see Stats, Buy property, quit)
   5) Continue to loop through the user commands until he has rolled.
   6) if the user has landed on an unowned property, give him the option to buy it
   7) if he has landed on on an owned property, tell him he has to pay the owner
   8) loop through the computers turn:
   		a) roll (if in jail pay to get out)
   		b) buy property (if unowned)
   		c) try to improve property (place one house per turn)

   Classes needed and Purpose (Input, Processing, Output)
   1) driver class - LetsPlayMonopoly
   2) GameBoard class
   3) Player class
   

*/

import java.util.*;

public class LetsPlayMonopoly
{
	public static void main(String[] args)
	{
		GameBoard board = new GameBoard(); // create new gameboard
		Scanner stringInput = new Scanner(System.in); // for user input
		boolean flag = false; // flag for continuing loops
		
		System.out.println("Lets Play Monopoly!");
		
		//Create player1 (human) and pass in name, board, and humanPlayer=true
		System.out.println("Please enter your name:");
		Player humanPlayer = new Player(stringInput.nextLine(), board, true);
		System.out.println();
		
		//Create player2 **Copy this for more computer players**
		Player player2 = new Player("Bob", board, false);
		
		
		do // Main loop
		{
			// Human Players turn**********************************************
			System.out.println("Your turn:");
			System.out.println("You can ROLL, PAYFORJAIL, see STATS, BUY property, or QUIT:");
			
			do // doubles loop
			{
				if(humanPlayer.hasDoubles())
				{

					System.out.println("Second roll:");
					System.out.println("You can ROLL, PAYFORJAIL, see STATS, BUY property, or QUIT:");
					humanPlayer.resetDoubles();
				}
				
			
				do // player choice loop
				{
					flag = false;
					String tempInput = stringInput.nextLine().toLowerCase();
					System.out.println();
				
					// roll dice
					if(tempInput.equals("roll"))
					{
						if(!humanPlayer.rollAndMove()) // if rollAndMove fails
						{
							System.out.println("Try something else");
							flag = true;
						}
					}
				
					//view name, balance, and properties owned
					else if(tempInput.equals("stats"))
					{
						humanPlayer.printPlayerStats();
						System.out.println("You can ROLL, PAYFORJAIL, see STATS, or QUIT:");
						flag = true;
					}
					
					// see other player's stats
					else if(tempInput.equals(player2.getName().toLowerCase()))
					{
						player2.printPlayerStats();
						System.out.println("You can ROLL, PAYFORJAIL, see STATS, BUY property, or QUIT:");
						flag = true;
					}
				
					// pay for jail 
					else if(tempInput.equals("payforjail"))
					{
						if(humanPlayer.isInJail())
						{
							humanPlayer.payForJail();
						}
						
						else
						{
							System.out.println("You are not in jail");
						}
						
						flag = true;
						System.out.println("You can ROLL, PAYFORJAIL, see STATS, BUY property, or QUIT:");
					}
					
					// buy houses
					else if(tempInput.equals("buy"))
					{
						System.out.println("Your stats:");
						humanPlayer.printPlayerStats();
						String temp;
						int tempInt;
						
						System.out.println("Which property would you like to add houses to? (please enter its full name)");
						temp = stringInput.nextLine();
						System.out.println("How many houses would you like to add?");
						tempInt = stringInput.nextInt();
						stringInput.nextLine();
						
						flag = true;
						System.out.println("You can ROLL, PAYFORJAIL, see STATS, BUY property, or QUIT:");
					}
					
					//exit game
					else if(tempInput.equals("quit"))
					{
						System.exit(0);
					}
					
					//default
					else
					{
						System.out.println("Invalid command! Try something else");
						flag = true;
					}
				}while(flag); // continues until the user has sucessfully rolled or skipped turn
				
				//Deal with space that the player has landed on
				
				if(!board.Space[humanPlayer.getPosition()].isOwned() && (board.Space[humanPlayer.getPosition()].getType().equals("PROPERTY") || board.Space[humanPlayer.getPosition()].getType().equals("RR") || board.Space[humanPlayer.getPosition()].getType().equals("UTIL")))
				{
					System.out.printf("This space is unowned: Would you like to buy it for $%d?:\n", board.Space[humanPlayer.getPosition()].getCost());
					String tempInput = stringInput.nextLine().toLowerCase();
					System.out.println();
					
					if(tempInput.equals("yes"))
					{
						humanPlayer.purchase(board.Space[humanPlayer.getPosition()]); //buy property
					}
				}
				
				else if(board.Space[humanPlayer.getPosition()].isOwned())
				{
					if(board.Space[humanPlayer.getPosition()].getOwner().equals(humanPlayer.getName()))
					{
						System.out.println("You already own this space.");
					}
					
					// add more of these for more players
					else if(board.Space[humanPlayer.getPosition()].getOwner().equals(player2.getName()) && board.Space[humanPlayer.getPosition()].getType().equals("PROPERTY"))
					{
						System.out.printf("%s owns this space\n", board.Space[humanPlayer.getPosition()].getOwner());
						System.out.printf("You must pay him $%d in rent (hit enter)\n", board.Space[humanPlayer.getPosition()].getRent());
						stringInput.nextLine();
						humanPlayer.payPlayer(player2, board.Space[humanPlayer.getPosition()].getRent());
					}
					
					else if(board.Space[humanPlayer.getPosition()].getOwner().equals(player2.getName()) && board.Space[humanPlayer.getPosition()].getType().equals("RR"))
					{
						System.out.printf("%s owns this space\n", board.Space[humanPlayer.getPosition()].getOwner());
						
						switch(player2.getRRsOwned())
						{
							case 1:
							System.out.println("You must pay him $25 in rent (hit enter)");
							stringInput.nextLine();
							humanPlayer.payPlayer(player2, 25);
							break;
							
							case 2:
							System.out.println("You must pay him $50 in rent (hit enter)");
							stringInput.nextLine();
							humanPlayer.payPlayer(player2, 50);
							break;
							
							case 3:
							System.out.println("You must pay him $100 in rent (hit enter)");
							stringInput.nextLine();
							humanPlayer.payPlayer(player2, 100);
							break;
							
							case 4:
							System.out.println("You must pay him $200 in rent (hit enter)");
							stringInput.nextLine();
							humanPlayer.payPlayer(player2, 200);
							break;
							
							default:
							System.out.println("!");
							break;
							}
					}
				
					else if(board.Space[humanPlayer.getPosition()].getOwner().equals(player2.getName()) && board.Space[humanPlayer.getPosition()].getType().equals("UTIL"))
					{
						System.out.printf("%s owns this space\n", board.Space[humanPlayer.getPosition()].getOwner());
						
						switch(player2.getUtilsOwned())
						{
							case 1:
							System.out.printf("You must pay him 4 times your die roll ($%d) in rent (hit enter)\n", 4 * humanPlayer.getLastDieRoll());
							stringInput.nextLine();
							humanPlayer.payPlayer(player2, 4 * humanPlayer.getLastDieRoll());
							break;
							
							case 2:
							System.out.printf("You must pay him 10 times your die roll ($%d) in rent (hit enter)\n", 10 * humanPlayer.getLastDieRoll());
							stringInput.nextLine();
							humanPlayer.payPlayer(player2, 10 * humanPlayer.getLastDieRoll());
							break;
							
							default:
							System.out.println("!");
							break;
						}
					}	
				}
			
				else if(board.Space[humanPlayer.getPosition()].getType().equals("FS"))
				{
					System.out.println("This space is a free space");
				}
			
				else if(board.Space[humanPlayer.getPosition()].getType().equals("INCTAX"))
				{
					humanPlayer.payTax(200);
				}
				
				else if(board.Space[humanPlayer.getPosition()].getType().equals("LUXTAX"))
				{
					humanPlayer.payTax(75);
				}
				
				else if(board.Space[humanPlayer.getPosition()].getType().equals("GTJ"))
				{
					humanPlayer.goToJail();
				}
			
				if(!humanPlayer.hasDoubles())
				{
					System.out.println("Your turn is over (hit enter)");
					stringInput.nextLine();
				}
			
			}while(humanPlayer.hasDoubles());
			//End Player 1's turn********************************************
			
			//Player 2's turn************************************************
			System.out.printf("%s's turn:\n", player2.getName());
			do
			{
				if(player2.hasDoubles())
				{
					player2.resetDoubles();
				}
				
				if(player2.isInJail())
				{
					player2.payForJail();
				}
			
				if(!player2.isInJail())
				{
					player2.rollAndMove();
					System.out.println();
					
					if(!board.Space[player2.getPosition()].isOwned() && 
					(board.Space[player2.getPosition()].getType().equals("PROPERTY") 
					|| board.Space[player2.getPosition()].getType().equals("RR")
					|| board.Space[player2.getPosition()].getType().equals("UTIL")))
					{
						player2.purchase(board.Space[player2.getPosition()]);
					}
					
					else if(board.Space[player2.getPosition()].isOwned())
					{
						// Duplicate these for more players
						if(board.Space[player2.getPosition()].getOwner().equals(humanPlayer.getName()) && board.Space[player2.getPosition()].getType().equals("PROPERTY"))
						{
								player2.payPlayer(humanPlayer, board.Space[player2.getPosition()].getRent());
				
						}
				
						else if(board.Space[player2.getPosition()].getOwner().equals(humanPlayer.getName()) && board.Space[player2.getPosition()].getType().equals("RR"))
						{
							System.out.printf("You own this space\n", board.Space[humanPlayer.getPosition()].getOwner());
						
							switch(humanPlayer.getRRsOwned())
							{
								case 1:
								player2.payPlayer(humanPlayer, 25);
								break;
							
								case 2:
								player2.payPlayer(humanPlayer, 50);
								break;
							
								case 3:
								player2.payPlayer(humanPlayer, 100);
								break;
							
								case 4:
								player2.payPlayer(humanPlayer, 200);
								break;
							
								default:
								System.out.println("!");
								break;
							}
						}
				
						else if(board.Space[player2.getPosition()].getOwner().equals(humanPlayer.getName()) && board.Space[player2.getPosition()].getType().equals("UTIL"))
						{
							switch(humanPlayer.getUtilsOwned())
							{
								case 1:
								player2.payPlayer(humanPlayer, 4 * humanPlayer.getLastDieRoll());
								break;
							
								case 2:
								player2.payPlayer(humanPlayer, 10 * humanPlayer.getLastDieRoll());
								break;
						
								default:
								System.out.println("!");
								break;
							}
						}
					}
			
					else if(board.Space[player2.getPosition()].getType().equals("INCTAX"))
					{
						player2.payTax(200);
					}
				
					else if(board.Space[player2.getPosition()].getType().equals("LUXTAX"))
					{
						player2.payTax(75);
					}
			
					else if(board.Space[player2.getPosition()].getType().equals("GTJ"))
					{
						player2.goToJail();
					}
					
					if(!player2.isBankrupt())
					{
						player2.buyHousesComp();
					}
				
					System.out.println();
				}	
			}while(player2.hasDoubles());
			//End Player 2's turn********************************************
			
		}while(!player2.isBankrupt()); // if human player goes bankrupt, we get a System.exit(0)
		
		if(player2.isBankrupt())
		{
			System.out.println("You win!");
		}
	}
}

//end