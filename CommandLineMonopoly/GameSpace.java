// 12/14/11 11:25 AM

/* FINAL PROJECT
   File Name: GameSpace.java
   Programmer: Mason Stevenson
   Date Last Modified: 12/14/11 8:00 PM

   Problem Statement: 
   Create a class that represents one Space on a Monopoly board
   
   
   Overall Plan ---

   Classes needed and Purpose 
   ---
   

*/
public class GameSpace
{
	//must be set
	private String name;
	private String type;
	private String owner;
	private int cost; // purchace price
	private int regRent; // cost with no improvments on property
	private int houseRate; // rent per house on property
	private int hotelRate; // cost with one hotel on property
	private int priority; // the strategic advantage of the space 1-8
	private int setNumber;
	
	//defaults
	private int houses = 0;
	private int costPerHouse = -1;
	private boolean hotel = false;
	private boolean owned = false;
	private boolean set = false;
	
	// no arg
	public GameSpace()
	{
		System.out.println("No Values Initialized for GameSpace!");
	}
	
	public GameSpace(String newName, String newType, int newCost, int newHouseCost, int newRent,int newHouseRate, int newHotelRate, int newPriority, int newSetNumber)
	{
		name = newName;
		type = newType;
		cost = newCost;
		costPerHouse = newHouseCost;
		regRent = newRent;
		houseRate = newHouseRate;
		hotelRate = newHotelRate;
		priority = newPriority;
		setNumber = newSetNumber;
	}
	
	//mutators
	public void setOwner(String newOwner)
	{
		owner = newOwner;
	}
	
	public void justBought()
	{
		owned = true;
	}
	
	public void addToSet()
	{
		set = true;
	}
	
	public void restoreDefaults()
	{
		houses = 0;
		hotel = false;
		owned = false;
		set = false;
	}
	
	//accessors
	public String getName()
	{
		return name;
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean isOwned()
	{
		if(owned == true)
			return true;
		else
			return false;
			
	}
	
	public boolean isPartOfSet()
	{
		if(set == true)
			return true;
		else
			return false;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public int getHouseCost()
	{
		return costPerHouse;
	}
	
	public int getRegRent()
	{
		return regRent;
	}
	
	public int getRent() // for regular property
	{
		int rent;
		
		// if set is owned with no improvments, paydouble regular rent
		if(set && houses == 0)
		{
			rent = regRent * 2;
		}
		
		else if(set && houses > 0)
		{
			rent = houseRate * houses;
		}
		
		else if(set && hotel == true)
		{
			rent = hotelRate;
		}
		
		else
		{
			rent = regRent;
		}
		
		return rent;
	}
	
	public int getRent(int dieRoll) // for utilities
	{
		return 4* dieRoll;
	}
	
	public int getHouseRate()
	{
		return houseRate;
	}
	
	public int getHotelRate()
	{
		return hotelRate;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public int getSetNumber()
	{
		return setNumber;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	//special methods
	public boolean addProperty(int newHouses) // for human Player
	{
		if(set)
		{
			if(hotel == true)
			{
				System.out.println("You already have a hotel on this property"
				+"!");
				
				return false;
			}
		
			else if((houses + newHouses) == 5)
			{
				System.out.println("Your fifth house becomes a hotel "
				+"(ominous music plays in backround)");
					
				houses = -1;
				hotel = true;
				return true;
			}
		
			else if(houses + newHouses > 5)
			{
				System.out.println("You cant put that many houses on this "
				+"property:");
				System.out.printf("You can put %d more houses on this property"
				+" (the fifth house becomes a hotel)", 5 - houses);
				return false;
			}
		
			else
			{
				System.out.printf("You sucessfully added your houses on %s!\n", name);
				houses += newHouses;
				return true;
			}
		}
		
		else
		{
			System.out.println("You dont have a set!");
			return false;
		}
	}
	
	public boolean addProperty(int newHouses, String playerName) // for Computer Player
	{
		if(set)
		{
			if(hotel == true)
			{
				return false;
			}
		
			else if((houses + newHouses) == 5)
			{
				System.out.printf("%s's fifth house on %s becomes a hotel "
				+"(ominous music plays in backround)\n", playerName, name);
					
				houses = -1;
				hotel = true;
				return true;
			}
		
			else if(houses + newHouses > 5)
			{
				return false;
			}
		
			else
			{
				System.out.printf("%s boght a house and placed it on %s.\n", playerName, name);
				houses += newHouses;
				return true;
			}
		}
		
		else
		{
			return false;
		}
	}
	
	//print all gamespace info
	public void printInfo()
	{
		System.out.printf("%s, %s, %d, %d, %d, %d, %d, %d\n", name, type, cost, regRent, houseRate, hotelRate, priority, setNumber);
	}
	
}