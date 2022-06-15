/***************************************
 * File: Bus.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan	   
 * Program Description: 	   
 * This class contains information about a bus & all of it's passengers
 ***************************************/

class Bus {

    //instance variables
    private LinkedList<Passenger> passengers = new LinkedList<Passenger>();
    private Route route;
    private int capacity;
    private int availableSeats;
    private int numOfPassengers = 0;
    private int id;
    private static int nextId = 1000;

    //default constructor 
    public Bus(){this(0, new Route());}

    //custom constructor
    public Bus(int capacity, Route route){
        setCapacity(capacity);
        setRoute(route);
        setID(nextId);
        setAvailableSeats(this.capacity);
        Manager.buses.queue(this, this.id);
        nextId++;
    }

	// custom constructor for saving
	public Bus(LinkedList<Passenger> p, Route r, int c, int np, int i, int a) {
		setPassengers(p);
		setRoute(r);
		setCapacity(c);
		setNumOfPassengers(np);
		setAvailableSeats(a);
		setID(i);

		// set nextID
		if(this.getID() >= nextId) {
			nextId = this.getID() + 1;
		}
	}

    //Getters & Setters
    public int getCapacity(){return this.capacity;}
    public int getNumOfPassengers(){return this.numOfPassengers;}
    public int getID(){return this.id;}
	public Route getRoute(){return this.route;}
    public int getAvailableSeats(){return this.availableSeats;}
    public LinkedList<Passenger> getPassengers(){return this.passengers;}
    public void setCapacity(int v){this.capacity = v;}
    public void setRoute(Route v){this.route = v;}
    public void setID(int v){this.id = v;}
    public void setNumOfPassengers(int v){this.numOfPassengers = v;}
	public void setPassengers(LinkedList<Passenger> v){this.passengers = v;}
    public void setAvailableSeats(int v){this.availableSeats = v;}

    //toString
    public String toString(){
        return "\nBus: #" + this.id 
        + "\n\n\s\sStart Point: " + route.getStartPoint()
        + "\n\s\sDestination: " + route.getDestination()
        + "\n\s\sDeparture Date: " + route.getDepartureDate()
        + "\n\s\sDeparture Time: " + route.getDepartureTime()
        + "\n\s\sRoute Number: " + route.getRouteNumber()
        + "\n\s\sAvailable Seats: " + this.availableSeats
        + "\n\s\sNumber of Passengers: " + this.numOfPassengers;
    }

    //this method will be used when adding passengers to a bus
    public void addPassenger(Passenger newPassengers){
        if(availableSeats == 0){
            checkBusCapacity(newPassengers);
            return;
        }
        
        //insert a new passenger node to the front of the linked list of passengers
        this.passengers.queue(newPassengers, newPassengers.getID());
        this.numOfPassengers++;
        this.availableSeats = this.capacity - this.numOfPassengers;
        System.out.println("\n\t\t\t\t~~Passenger has been added~~ \n\nNew list of passengers:");
        this.passengers.print();
        Manager.save();
    }

    //this method will ask user if they want to create a bus
    private void checkBusCapacity(Passenger newPassengers){
        int choice = 0;
        System.err.println("\n~~No more seats available in this bus~~");  
        System.out.println("\nWould you like to create a new bus and add this passenger?: " 
                              + "\n1. Yes \n2. No \nPlease enter 1 or 2");  
        choice = Manager.getChoiceInput(1,2);

        if(choice == 1){
            Bus bus = Manager.createBus(this.route);
            bus.getPassengers().queue(newPassengers, newPassengers.getID());
            bus.numOfPassengers++;
            bus.availableSeats = bus.capacity - bus.numOfPassengers;
            System.out.println("\n\t\t\t\t~~Passenger has been added~~ \n\nNew list of passengers:");
            bus.passengers.print();
            Manager.save();
        }

        return;
    }

    //this method will be used when a passenger needs to be removed from a bus
    public void removePassenger(Passenger passenger){

        //remove the passenger coresponding to the node ID
        passengers.remove(passenger.getID());
        this.numOfPassengers--;
        this.availableSeats = this.capacity - this.numOfPassengers;
        Manager.save();
    }

    //save method
    public static String save(Bus v){
				String save = Constants.bus +
					"|" + v.getCapacity() +
					"|" + v.getNumOfPassengers() +
					"|" + v.getID() +
					"|" + v.getAvailableSeats() +
					"|" + Route.save(v.getRoute());
		
				Node<Passenger> passenger = v.getPassengers().getHead();

				save += "|" + Constants.passengers;
			
		    //traverse the linked list to retrieve all the info for each passenger
				while(passenger != null) {
				    save += "|" + Passenger.save(passenger.getData());
					passenger = passenger.getNext();
				}
			
				return save;
    }

	  public static Bus parseBus(String v) {
				LinkedList<Passenger> personList = new LinkedList<Passenger>();
				String[] data = v.split("\\|");

				try {
					// start index for substring
					int startIndex = 0;
					int endIndex = 0;
					boolean foundRoute = false;
					String routeString = "";
					String peopleString = "";

					// get substring for route
					for(int i = 0; i < data.length; i++) {
						if(data[i].equals(Constants.route)) {
							endIndex += data[i].length() + 1;
							foundRoute = true;
						}
						else if(data[i].equals(Constants.passengers)) {
							// create substring
							endIndex += data[i].length() + 1;
							routeString = v.substring(startIndex, endIndex - 1);
							break;
						}
						else if(!foundRoute) {
							startIndex += data[i].length() + 1;
							endIndex += data[i].length() + 1;
						}
						else {
							endIndex += data[i].length() + 1;
						}
					}

					// get substring for passengers
					peopleString = v.substring(endIndex - 1);

					// get person list
					personList = Passenger.parsePassengers(peopleString);
					
					return new Bus(
						personList,
						Route.parseRoute(routeString),
						Integer.parseInt(data[1]),
						Integer.parseInt(data[2]),
						Integer.parseInt(data[3]),
						Integer.parseInt(data[4])
					);
				}
				catch(Exception e) {
					System.out.println("Cannot parse bus.\n");

					for(int i = 0; i < e.getStackTrace().length; i++) {
						System.out.println(e.getStackTrace()[i]);
					}
					return null;
				}
	  }	
}