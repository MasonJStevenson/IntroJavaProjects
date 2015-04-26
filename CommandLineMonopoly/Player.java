// 12/14/11 11:25 AM

/* FINAL PROJECT
   File Name: Player.java
   Programmer: Mason Stevenson
   Date Last Modified: 12/14/11 8:00 PM

   Problem Statement: 
   Create a class that represents a Player in the game of Monopoly
   
   
   
   Overall Plan ---

   Classes needed and Purpose 
   -
   

*/

import java.util.*;

public class Player
{
	private String name = "";
	private int balance = 1500;
	private int position = 0; // the index on GameBoard of the player
	public int turnsInJail = 0;
	public int doublesRolled = 0;
	private int railroadsOwned = 0;
	private int utilitiesOwned = 0;
	private int lastDieRoll;
	private int[] spacesOwned = new int[40];
	private boolean inJail = false;
	private boolean bankrupt = false;
	private boolean humanPlayer; // humanPlayer = true for human player 
	private boolean doubles;
	
	GameBoard boardPointer; // points to actual gameboard
	
	public Player()
	{
		System.out.println("Player not initialized with a GameBoard!");
	}
	
	public Player(String newName, GameBoard board, boolean YorN)
	{
		name = newName;
		boardPointer = board;
		humanPlayer = YorN;
		
		for(int count = 0; count < spacesOwned.length; count++)
		{
			spacesOwned[count] = -1; // initialize all indexes to -1
		}
		
	}
	
	//accessors
	public int getPosition()
	{
		return position;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isBankrupt()
	{
		return bankrupt;
	}
	
	public boolean isHuman()
	{
		return humanPlayer;
	}
	
	public boolean isInJail()
	{
		return inJail;
	}
	
	public int getRRsOwned()
	{
		return railroadsOwned;
	}
	
	public int getUtilsOwned()
	{
		return utilitiesOwned;
	}
	
	public int getBalance()
	{
		return balance;
	}
	
	public int getLastDieRoll()
	{
		return lastDieRoll;
	}
	
	public boolean hasDoubles()
	{
		return doubles;
	}
	
	//mutators
	public void addToBalance(int amount)
	{
		balance += amount;
	}
	
	public void resetDoubles()
	{
		doubles = false;
	}
	
	public void resetDoublesRolled()
	{
		doublesRolled = 0;
	}
	//special methods
	
	public boolean rollAndMove()
	{
		//if player is in jail
		if(inJail)
		{
			if(turnsInJail < 3)
			{
				int die1 = (int)(Math.random()* 6 + 1);
				int die2 = (int)(Math.random()* 6 + 1);
				
				if(die1 == die2)
				{
					inJail = false;
					turnsInJail = 0;
					
					if(humanPlayer)
					{
						System.out.printf("You rolled a %d and a %d and "
						+"got out of jail!", die1, die2);
					}
					
					else
					{
						System.out.printf("%s rolled a %d and a %d and "
						+"got out of jail!", name, die1, die2);
					}
					
					lastDieRoll = die1+die2;
					landOnSpace(die1+die2);
					return true;
				}
				
				else
				{
					turnsInJail++;
					
					if(humanPlayer)
					{
						System.out.printf("You rolled a %d and a %d, so you "
						+"are still in jail\n", die1, die2);
						
						if(turnsInJail == 3)
						{
							System.out.println("Next turn you must to "
							+"PAYFORJAIL and get out");
						}
					}
					
					return true;
				}
			}
			
			else
			{
				if(humanPlayer)
				{
					System.out.println("You have already been in jail for"
					+" three turns, you must PAYFORJAIL");
					return false;
				}
			}
		}
		
		// if not in jail
		else
		{
			int die1 = (int)(Math.random()* 6 + 1);
			int die2 = (int)(Math.random()* 6 + 1);
		
			if(die1 == die2)
			{
				if(humanPlayer)
				{
					System.out.println("You rolled doubles! You may roll again"
					+" after completing your first roll");
				}
				
				else
				{
					System.out.printf("%s rolled doubles: he gets to go again!\n", name);
				}
				
				doubles = true;
			}
		
			lastDieRoll = die1+die2;
			landOnSpace(die1+die2);
			return true;
		}
		
		return false;
	}
	
	// Land on a Gamespace
	private void landOnSpace(int dieRoll)
	{
		if(position + dieRoll < 40)
			position += dieRoll;
			
		else
		{
			position = dieRoll - (40 - position);
			
			// Landed directly on GO
			if(position == 0)
			{
				if(humanPlayer)
					System.out.printf("You rolled a %d, landed on GO, and colected $200\n", dieRoll);
				else
					System.out.printf("%s landed rolled a %d, landed on GO, and colected $200\n", name, dieRoll);
			}
			
			// Passed GO
			else
			{
				if(humanPlayer)
					System.out.printf("You rolled a %d, passed GO, and colected $200\n", dieRoll);
				
				else
					System.out.printf("%s landed rolled a %d, passed GO, and colected $200\n", name, dieRoll);
			}
			
			balance += 200;
		}
		
		
		if(humanPlayer)
		{
			System.out.printf("You rolled a %d and landed on %s ", dieRoll, boardPointer.Space[position].getName());
					
			if(boardPointer.Space[position].isOwned() && (boardPointer.Space[position].getType().equals("PROPERTY") || boardPointer.Space[position].getType().equals("RR")))
				System.out.printf("(owned)\n\n");
			else if(boardPointer.Space[position].getType().equals("PROPERTY") || boardPointer.Space[position].getType().equals("RR"))
				System.out.printf("(unowned)\n\n");
			else if(boardPointer.Space[position].getType().equals("JL"))
				System.out.printf("(just visiting)\n\n");
			else
				System.out.println("\n");
					
		}
				
		else
		{
			System.out.printf("%s rolled a %d and landed on %s", name, dieRoll, boardPointer.Space[position].getName());
			if(boardPointer.Space[position].getType().equals("JL"))
				System.out.printf("(just visiting)\n\n");
		}
	}
	
	//purchase a gameSpace
	public boolean purchase(GameSpace property)
	{
		if(property.isOwned())
		{
			if(humanPlayer)
				System.out.println("This property is already owned");
				
			return false;
		}
		
		else if(balance - property.getCost() >= 0)
		{
			balance -= property.getCost(); // subtract cost
			property.justBought(); //set isOwned to true
			property.setOwner(name); //set owner
			
			//add current position to the next available inded in spacesOwned
			for(int count = 0; count < spacesOwned.length; count++)
			{
				if(spacesOwned[count] == -1)
				{
					spacesOwned[count] = position;
					break;
				}
			}
			
			if(humanPlayer)
			{
				System.out.printf("You just purchased %s for $%d!\n", property.getName(), property.getCost());
				System.out.printf("Your remaining balance is: $%d\n\n", balance);
			}
			
			else
			{
				System.out.printf("He purchased it for $%d\n", property.getCost());
			}
			
			if(property.getType().equals("RR"))
			{
				railroadsOwned++;
				return true;
			}
			
			if(property.getType().equals("UTIL"))
			{
				utilitiesOwned++;
				return true;
			}
			
			//check for set
			int posProperty2 = -1; // the second property in set
			int posProperty3 = -1; // the third property in set
			
			for(int count = 0; count < spacesOwned.length; count++)
			{
				if(spacesOwned[count] == position)
				{
					break;
				}
				
				if(property.getSetNumber() == boardPointer.Space[spacesOwned[count]].getSetNumber())
				{
					if(posProperty2 == -1)
					{
						posProperty2 = spacesOwned[count];
					}
					
					else
					{
						posProperty3 = spacesOwned[count];
					}
				}
			}
			
			// for three-property sets
			if(posProperty2 != -1 && posProperty3 != -1)
			{
				boardPointer.Space[position].addToSet();
				boardPointer.Space[posProperty2].addToSet();
				boardPointer.Space[posProperty3].addToSet();
				
				if(humanPlayer)
				{
					System.out.println("You have a set!");
					System.out.printf("You may now place houses on %s, %s, and"
					+" %s starting next turn\n", 
					boardPointer.Space[position].getName(), 
					boardPointer.Space[posProperty2].getName(), 
					boardPointer.Space[posProperty3].getName());
				}
			}
			
			// for two-property sets
			else if((property.getSetNumber() == 1 || property.getSetNumber() == 8) && posProperty2 != -1)
			{
				boardPointer.Space[position].addToSet();
				boardPointer.Space[posProperty2].addToSet();
				
				if(humanPlayer)
				{
					System.out.println("You have a set!");
					System.out.printf("You may now place houses on %s and %s starting next turn\n", 
					boardPointer.Space[position].getName(), 
					boardPointer.Space[posProperty2].getName());
				}
			}
			
			return true;
		}
		
		else if(balance - property.getCost() < 0)
		{
			if(humanPlayer)
				System.out.println("You cant afford this property!");
			return false;
		}
		
		return false;
	}
	
	public boolean buyHouses(String propertyName, int numHouses)
	{
		for(int count = 0; count < spacesOwned.length; count++)
		{
			if(spacesOwned[count] == -1)
			{
				break;
			}
			
			else if(boardPointer.Space[spacesOwned[count]].getName().equals(propertyName))
			{
				// will return true for a sucessful transaction
				if(balance - boardPointer.Space[spacesOwned[count]].getHouseCost() >= 0)
				{
					if(boardPointer.Space[spacesOwned[count]].addProperty(numHouses)) 
					{
						return true;
					}
				
					else
					{
						return false;
					}
				}
				
				else
				{
					System.out.println("You cant afford to put houses on this property!");
					return false;
				}
			}
		}
		
		return false;
	}
	
	public void buyHousesComp()
	{
		for(int count = 0; count < spacesOwned.length; count++)
		{
			if(spacesOwned[count] == -1)
			{
				break;
			}
			
			else if(balance - boardPointer.Space[spacesOwned[count]].getHouseCost() >= 0)
			{
				boardPointer.Space[spacesOwned[count]].addProperty(1, name); // computer tries to add one house to every property it owns
			}
		}
	}
	
	public void payPlayer(Player otherPlayer, int amount)
	{
		if(balance - amount < 0)
		{
			if(humanPlayer)
			{
				System.out.println("You can not afford to pay your rent!");
				System.out.println("YOU LOSE");
				System.exit(0);
			}
			
			else if(otherPlayer.isHuman())
			{
				System.out.printf("%s could not afford to pay you and went bankrupt!\n", name);
				bankrupt = true;
			}
			
			else
			{
				System.out.printf("%s could not afford to pay %s and went bankrupt!\n", name, otherPlayer.getName());
				bankrupt = true;
			}
		}
		
		else
		{
			if(humanPlayer)
			{
				balance -= amount;
				otherPlayer.addToBalance(amount);
				System.out.printf("You paid %s $%d\n", otherPlayer.getName(), amount);
				System.out.printf("Your balance is now $%d\n", balance);
			}
			
			else if(otherPlayer.isHuman())
			{
				balance -= amount;
				otherPlayer.addToBalance(amount);
				System.out.printf("%s paid you $%d\n", name, amount);
				System.out.printf("Your balance is now $%d\n", otherPlayer.getBalance());
			}
			
			else
			{
				balance -= amount;
				otherPlayer.addToBalance(amount);
				System.out.printf("%s paid %s $%d", name, otherPlayer.getName(), amount);
			}
		}
	}
	
	public void payForJail()
	{
		if(!inJail)
		{
			if(humanPlayer)
			{
				
			}
		}
		
		else
		{
			if(balance - 50 >= 0)
			{
				balance -= 50;
				inJail = false;
				turnsInJail = 0;
				
				if(humanPlayer)
				{
					System.out.println("You paid $50 and got out of jail.");
				}
				
				else
				{
					System.out.printf("%s paid $50 and got out of jail\n", name);
				}
			}
			
			else
			{
				if(turnsInJail != 3)
				{
					if(humanPlayer)
					{
						System.out.println("You cant afford to get out of jail right now... (try rolling for doubles)");
					}
				}
				
				else
				{
					if(humanPlayer)
					{
						System.out.println("You couldn't afford to get out of jail! (You go bankrupt!)");
						System.out.println("YOU LOOSE!");
						System.exit(0);
					}
					
					else
					{
						System.out.printf("%s could not afford to get out of jail and went bankrupt!\n", name);
						bankrupt = true;
					}
				}
			}
		}
	}
	
	public void printPlayerStats()
	{
		int count = 0;
		System.out.printf("\nName: %s\n", name);
		System.out.printf("Balance: %d\n", balance);
		
		if(inJail)
			System.out.printf("Turns in Jail: %d\n", turnsInJail);
			
		if(spacesOwned[count] != -1)
		{
			System.out.printf("\nOwned Property:\n");
			System.out.printf("Name:               Rent:  Rent(Per House):  Rent(With Hotel):  CostPerHouse:   SetNumber:\n");
		
			while(spacesOwned[count] != -1)
			{
				if(boardPointer.Space[spacesOwned[count]].getType().equals("RR"))
				{
					switch(railroadsOwned)
					{
						case 1:
						System.out.printf("%-19s $25    N/A               N/A                N/A             N/A\n", boardPointer.Space[spacesOwned[count]].getName());
						break;
						
						case 2:
						System.out.printf("%-19s $50    N/A               N/A                N/A             N/A\n", boardPointer.Space[spacesOwned[count]].getName());
						break;
						
						case 3:
						System.out.printf("%-19s $100   N/A               N/A                N/A             N/A\n", boardPointer.Space[spacesOwned[count]].getName());
						break;
						
						case 4:
						System.out.printf("%-19s $200   N/A               N/A                N/A             N/A\n", boardPointer.Space[spacesOwned[count]].getName());
						break;
						
						default:
						System.out.println("!");
						break;
					}
				}
				
				else if(boardPointer.Space[spacesOwned[count]].getType().equals("UTIL"))
				{
					switch(utilitiesOwned)
					{
						case 1:
						System.out.printf("%-19s4*roll  N/A               N/A                N/A             N/A\n", boardPointer.Space[spacesOwned[count]].getName());
						break;
						
						case 2:
						System.out.printf("%-19s10*roll N/A               N/A                N/A             N/A\n", boardPointer.Space[spacesOwned[count]].getName());
						break;
						
						default:
						System.out.println("!");
						break;
					}
				}
				
				else
				{
					System.out.printf("%-19s $%-5d $%-16d $%-17d $%-15d %d\n", 
					boardPointer.Space[spacesOwned[count]].getName(),
					boardPointer.Space[spacesOwned[count]].getRegRent(), 
					boardPointer.Space[spacesOwned[count]].getHouseRate(), 
					boardPointer.Space[spacesOwned[count]].getHotelRate(),
					boardPointer.Space[spacesOwned[count]].getHouseCost(),
					boardPointer.Space[spacesOwned[count]].getSetNumber());
				}
				count++;
			}
		}
		
		System.out.println();
		
	}
	
	public void payTax(int amount)
	{
		if(balance - amount < 0)
		{
			if(humanPlayer)
			{
				System.out.println("You can not afford to pay the tax!");
				System.out.println("YOU LOSE");
				System.exit(0);
			}
			
			else 
			{
				System.out.printf("%s could not afford to pay the tax and went bankrupt!\n", name);
				bankrupt = true;
			}
		}
		
		else
		{
			if(humanPlayer)
			{
				balance -= amount;
				System.out.printf("You paid $%d in taxes\n", amount);
				System.out.printf("Your balance is now $%d\n", balance);
			}
			
			else
			{
				balance -= amount;
				System.out.printf("%s paid $%d in taxes\n", name, amount);
			}
		}
	}
	
	public void goToJail()
	{
		position = 10;
		inJail = true;
		
		if(humanPlayer)
		{
			System.out.println("You're in Jail!");
		}
		
		else
		{
			System.out.printf("%s is in Jail!\n", name);
		}
	}
	

}