
/***************************************
 * File: Manager.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan 	   
 * Program Description:		   
 * This class is the command center of the program
 * All events of user input and decision making is handled here.
 ***************************************/
import java.util.Scanner;

abstract class Manager {
	public static LinkedList<Bus> buses = new LinkedList<Bus>();
	public static LinkedList<Route> routes = new LinkedList<Route>();

    //starts up the program, loads the data from txt file and prints menu
	public static void initialize() {
		Save.load();
		printMenu();
	}
	
    //this method outputs the menu and takes in the user choice
    public static void printMenu(){
        //output the menu
		System.out.println("\n\n\t\t~ ~ ~ ~ ~ ~ \s\sMain Menu\s\s ~ ~ ~ ~ ~");
		System.out.println("\n" 
		+"\t\t\s\s\s1. Show Scheduled Bus List\n"
		+"\n\t\t\s\s\s2. Show Passenger List\n"
		+"\n\t\t\s\s\s3. Create a Bus Route\n"
		+"\n\t\t\s\s\s4. Delete a Bus Route\n"
		+"\n\t\t\s\s\s5. Book a Ticket on a Bus\n"
		+"\n\t\t\s\s\s6. Cancel a Ticket on a Bus\n"
        +"\n\t\t\s\s\s7. Remove a bus\n");
		System.out.println("\n\t\t\s\s\s\s\sAt any time enter 0 to quit" +
		"\n\n\t\t~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
        
        makeMenuDecision(getChoiceInput(1,7));
    }

    //this method saves to the txt file
    public static void save(){Save.save(buses, routes);}

    private static void makeMenuDecision(int choice){

        //use a switch statement for different scenarios
        switch(choice){
            case 1: buses.print(); printMenu(); // see scheduled buses
                break;
            case 2: //see passengers on buses
                printPassengers(); printMenu();
                break;
            case 3: createRoute(); printMenu();
                break;
            case 4: deleteRoute(); printMenu();
                break;
            case 5: bookTicket(); printMenu();
                break;
            case 6: cancelTicket(); printMenu();
                break;
            case 7: removeBus(); printMenu();
                break;
            default:
                System.out.println("\nThank you for using The Omega Bus Lines Service");
        }
    }

    //these methods will check if the user wants to exit
    private static boolean checkIfExited(int input){return input ==0;}
	private static boolean checkIfExited(String  input){return input.equalsIgnoreCase("0");}

    //this method will cancel a ticket
    private static void cancelTicket(){
        int id;

        //get passenger id
        System.out.print("\nPlease enter the ID of the passenger you'd like to remove: ");
        id = getIntInput(1,Passenger.getNextID()-1);
        removePassenger(id);
    }

    //this method rmeoves the passenger from the linked list
    private static void removePassenger(int id){
        Node<Bus> currentBus = Manager.buses.getHead();
        Node<Passenger> currentPassenger = currentBus.getData().getPassengers().getHead();

        //loop through the busses and look for the given passenger id
	    while(currentBus != null) {
            
            while(currentPassenger != null){
                if(currentPassenger.getData().getID() == id){
                    currentBus.getData().removePassenger(currentPassenger.getData());
                    //print new list
                    System.out.println("\n\t\t\t\t~~Passenger has been removed~~ \n\nNew list of passengers:");
                    printPassengers();
                    Manager.save();
                    break;
                }

				currentPassenger = currentPassenger.getNext();
            }
            
	        currentBus = currentBus.getNext();

			if(currentBus != null) {
				currentPassenger = currentBus.getData().getPassengers().getHead();
			}
		}
    }

    //this method will book a ticket on a bus
    private static void bookTicket(){
        String name, ph;
        int busID;

        //ask for the bus they want to book a ticket for
        System.out.print("\nPlease enter the bus ID you'd like to add a passenger to: ");
        busID = getIntInput(1000, buses.getHighest());

        //get passenger info
        System.out.print("\nPlease enter the passengers name: ");
        name = getStringInput();
        System.out.print("\nPlease enter the passengers phone number: ");
        ph = getStringInput();

        //add passenger to the specefied bus
        addPassenger(name, ph, busID);
    }

    //this method will add a passenger to a bus
    private static void addPassenger(String name, String ph, int busID){		
        Node<Bus> currentBus = Manager.buses.getHead();
		
	    while(currentBus != null) {
			if(currentBus.getData().getID() == busID) {
				currentBus.getData().addPassenger(new Passenger(name, ph));
                Manager.save();
                break;
			}
	        currentBus = currentBus.getNext();
		}
    }

    //this method will allow the user the delete a route
    private static void deleteRoute(){
        int input;
        //get the route id the user wants to remove
        System.out.print("\nPlease enter the route number you wish to delete: ");
		input = getIntInput(0, Manager.routes.getHighest());
        Manager.routes.remove(input);
        Manager.save();
        removeBusWRoute(input);
    }

    //this method will remove a bus the user wants
    private static void removeBus(){
        System.out.print("\nPlease enter the bus ID you wish to remove: ");
        int input = getIntInput(0, Manager.buses.getHighest());
		Node<Bus> currentBus = Manager.buses.getHead();
		
	      while(currentBus != null) {
				if(currentBus.getData().getID() == input) {
					Manager.buses.remove(currentBus.getID());
                    //print new list
                    System.out.println("\n\t\t\t\t~~Bus has been removed~~ \n\nNew list of Buses:");
                    buses.print();
                    Manager.save();
                    break;
				}
	            currentBus = currentBus.getNext();
		  }
    }

    //this method removes the bus corresponding to a route
    private static void removeBusWRoute(int input){
		Node<Bus> currentBus = Manager.buses.getHead();
		
	      while(currentBus != null) {
				if(currentBus.getData().getRoute().getRouteNumber() == input) {
					Manager.buses.remove(currentBus.getID());
                    //print new list
                    System.out.println("\n\t\t\t\t~~Route has been removed~~ \n\nNew list of routes:");
                    buses.print();
                    Manager.save();
                    break;
				}
	            currentBus = currentBus.getNext();
		  }
    }

    //this method will print the passengers on a bus
    private static void printPassengers(){
        Node<Bus> currentNode = buses.getHead();
	    while(currentNode != null) {
	        System.out.println("\nBus : #" + currentNode.getData().getID());
            currentNode.getData().getPassengers().print();
	        currentNode = currentNode.getNext();
	    }
    }

    //this method will create a new route
    private static void createRoute(){
        String startPoint, destination, departTime;
        Date departDate;
        int year, month, day, busCapacity;

        //get inputs
        System.out.print("\nPlease enter the starting location: ");
        startPoint = getStringInput();

        System.out.print("\nPlease enter the destination: ");
        destination = getStringInput();

        System.out.print("\nPlease enter departing year: ");
        year = getIntInput(2022, 2100);

        System.out.print("\nPlease enter departing month Ex.(1, 3, 10): ");
        month = getIntInput(0,12);

        System.out.print("\nPlease enter departing day: Ex.(1, 3, 10): ");
        day = getIntInput(0,31);

        System.out.print("\nPlease enter the departing time Ex.(1:35 pm ET): ");
        departTime = getStringInput();
        
        System.out.print("\nPlease enter the capacity of the bus range 20-30: ");
        busCapacity = getIntInput(20,30);

        //create the route and assign a bus to it
        new Bus(busCapacity, new Route(startPoint, destination, new Date(day,month,year), departTime));
        Manager.save();
        
        //print new lists
        System.out.println("\n\t\t\t\t~~Route has been created~~ \n\nNew list of routes:");
        buses.print();
    }

    //this method will create a new bus
    public static Bus createBus(Route route){
        int busCapacity = 0;
        System.out.print("\nPlease enter the capacity of the bus range 20-30: ");
        busCapacity = getIntInput(20,30);

		Bus bus = new Bus(busCapacity, route);
		Manager.save();

		return bus;
    }

    //this method gets string inputs assuring they are valid
    private static String getStringInput(){
        String input;
        boolean error = false;
        
        do{
            input = new Scanner(System.in).nextLine();
            if(checkIfExited(input)){System.out.println("\nThank you for using The Classy Dog Bus Lines Service");System.exit(1);}

            for(String s : Constants.constants){
                
                if(input.contains(s)){
                    error = true;
                    System.err.print("\nPlease do not include any word containing *Class* or *|* \nTry again: ");
                    break;
                }
            }
        }while(error);
        return input;
    }

    //this method gets integer input and verifys it
    private static int getIntInput(int min, int max){
        int num = 0;
        boolean error = false;
        
        //get user choice and implement user verification
        do{
            try{
                num = new Scanner(System.in).nextInt();

				if(checkIfExited(num)){System.out.println("\nThank you for using The Omega Bus Lines Service");System.exit(1);}
                error = false;
				if(num < min || num > max){
                    error = true;
                    System.err.println("~~Error Please Enter Valid Input~~");
                }
            }catch(Exception e){
                error = true;
                System.err.println("~~Error Please Enter Valid Input~~");
            }

        }while(error);
        return num;
    }
    
    //helper method for taking in integer input
    //enter the minimum choice and maximum choice for input validation
    public static int getChoiceInput(int minChoice, int maxChoice){
        int choice = 0;
        boolean error = false;
        
        //get user choice and implement user verification
        do{
            try{
                System.out.print("\nPlease make a selection: ");
                choice = new Scanner(System.in).nextInt();
                if(checkIfExited(choice)){System.out.println("\nThank you for using The Omega Bus Lines Service");System.exit(1);}
                error = false;

				if(choice < minChoice && choice !=0 || choice > maxChoice){
                    error = true;
                    System.err.println("~~Error Please Enter Valid Input~~");
                }
            }catch(Exception e){
                error = true;
                System.err.println("~~Error Please Enter Valid Input~~");
            }

        }while(error);
        return choice;
    }
}