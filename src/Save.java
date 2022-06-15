
/***************************************
 * File: Save.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan 	   
 * Program Description:	This class contains methods - 
 * which will allow the employees to save information to a -
 * database and retrieve information from a database
 ***************************************/

import java.io.*;

abstract class Save {
    // static method for saving
    public static void save(LinkedList<Bus> b, LinkedList<Route> r) {
        try {
            // initialize file writer and print writer
            FileWriter fw = new FileWriter("save.txt");
            PrintWriter pw = new PrintWriter(fw);

            // loop through buses to save all of the buses
            Node<Bus> currentBus = b.getHead();

            while (currentBus != null) {
                // add save string to the text file
                pw.println(Bus.save(currentBus.getData()));
                currentBus = currentBus.getNext();
            }

			pw.println("Routes");
					
            // loop through routes to save all the routes
            // need this because we don't want duplicate routes
            Node<Route> currentRoute = r.getHead();

            while (currentRoute != null) {
                // add save string to the text file
                pw.println(Route.save(currentRoute.getData()));
                currentRoute = currentRoute.getNext();
            }

            // close file writer and print writer
            fw.close();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // static method for loading
    public static void load() {
		String line;
		
		try {
			// initialize file reader and buffered reader
			FileReader fr = new FileReader("save.txt");
			BufferedReader br = new BufferedReader(fr);

			line = br.readLine();

			// loop through each save string
			while(line != null) {
				// check if new bus
				if(line.substring(0, 5).contains("Bus")) {
					// add bus to list
					Bus bus = Bus.parseBus(line);
					Manager.buses.queue(bus, bus.getID());
				}
				else if(line.substring(0, 5).contains("Route")) {break;}
				
				line = br.readLine();
			}

            line = br.readLine();

			// loop through to save routes
			while(line != null) {
				if(line.substring(0, 5).contains("Route")) {
					Route route = Route.parseRoute(line);
					Manager.routes.queue(route, route.getRouteNumber());
				}

				line = br.readLine();
			}

			br.close();
		}
		catch(Exception e) {
			System.out.println("Error loading file or first time creating save file.");
		}
	}
}