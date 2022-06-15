/***************************************
 * File: Passenger.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan	   
 * Program Description:	This class holds information about - 
 * every passenger that is added to a bus
 ***************************************/

public class Passenger {

  private String name, phoneNumber;
  private int id;
  private static int nextId = 1;

  //default constructor
  public Passenger(){this("UNDEFINED", "UNDEFINED");}

  //custom constructor
  public Passenger(String name, String phoneNumber) {
    setName(name);
    setPhoneNumber(phoneNumber);
    setId(nextId);
    nextId++;  
  }

  //custom constructor
  public Passenger(String name, String phoneNumber, int n) {
    setName(name);
    setPhoneNumber(phoneNumber);
    setId(n);
      
	if(n >= nextId) {
		nextId = n + 1;
	}
  }

  //toString
  public String toString(){
    return "\s\sPassenger ID: " + this.id
    +"\n\s\s\s\sName: " + this.name 
    + "\n\s\s\s\sPhone Number: " + this.phoneNumber + "\n"; 
  }

  //Getters & Setters
  public String getName(){return this.name;}
  public String getPhoneNumber(){return this.phoneNumber;}
  public int getID(){return this.id;}
  public static int getNextID(){return nextId;}
  public void setName(String name) {this.name = name;}
  public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
  public void setId(int id) {this.id = id;}

  public static String save(Passenger v) {
		return Constants.passenger +
			"|" + v.name +
			"|" + v.phoneNumber +
            "|" + v.id;
	}

	public static Passenger parsePassenger(String v) {
		String[] data = v.split("\\|");

		try {
			return new Passenger(
				data[0],
				data[1],
				Integer.parseInt(data[2])
			);
		}
		catch(Exception e) {
			System.out.println("Cannot parse passenger.");
			return null;
		}
	}

	public static LinkedList<Passenger> parsePassengers(String v) {
		LinkedList<Passenger> passengers = new LinkedList<Passenger>();
		String[] data = v.split("\\|");

		// loop through passengers string and call parsePassenger
		for(int i = 1; i < data.length; i += 4) {
			Passenger parsedPassenger = Passenger.parsePassenger(data[i + 1] + "|" + data[i + 2] + "|" + data[i + 3]);
			
			passengers.queue(parsedPassenger, parsedPassenger.getID());
		}

		return passengers;
	}
}