/***************************************
 * File: Route.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan   
 * Program Description:	This class holds information - 
 * about every route that gets created.
 ***************************************/

class Route {
    
    //instance variables
    private String startPoint, destination, departTime;
    private int routeNumber;
	private static int nextNumber = 1;
    private Date departDate;

    //default constructor
    public Route(){this("UNDEFINED", "UNDEFINED", new Date(), "UNDEFINED");}

    //custom contstructor
	public Route(String sP, String dest, Date deptDate, String departHour) {
        setStartPoint(sP);
        setDestination(dest);
        setDepartureTime(departHour);
        setDepartureDate(deptDate);
		setRouteNumber(nextNumber);
        Manager.routes.queue(this, this.routeNumber);
		nextNumber++;
	}

	// custom constructor for saving
	public Route(String sP, String dest, int rn, String departHour, Date deptDate) {
		setStartPoint(sP);
        setDestination(dest);
      	setDepartureTime(departHour);
      	setDepartureDate(deptDate);
		setRouteNumber(rn);

        if(routeNumber >= nextNumber) {
            nextNumber = routeNumber + 1;
        }
	}

    //toString
    public String toString(){
        return "\tRoute #: " + routeNumber + "\n"
        + "\tStart Point: " + startPoint + "\n"
        + "\tDestination: " + destination + "\n"
        + "\tDeparture Date: " + departDate + "\n"
        + "\tDeparture Time: " + departTime + "\n";
    }

    //Getters & Setters
    public String getStartPoint(){return this.startPoint;}
    public String getDestination(){return this.destination;}
    public String getDepartureTime(){return this.departTime;}
    public int getRouteNumber(){return this.routeNumber;}
    public Date getDepartureDate(){return this.departDate;}
    public void setStartPoint(String v){this.startPoint = v;}
    public void setDestination(String v){this.destination = v;}
    public void setDepartureTime(String v){this.departTime = v;}
    public void setRouteNumber(int v){this.routeNumber = v;}
    public void setDepartureDate(Date v){this.departDate = v;}

    //returns all the info for a route instance 
	public static String save(Route v) {
		return Constants.route +
			"|" + v.getStartPoint() +
			"|" + v.getDestination() +
      		"|" + v.getRouteNumber() + 
      		"|" + v.getDepartureTime() +
      		"|" + Date.dateToString(v.getDepartureDate());
	}

	public static Route parseRoute(String v) {
		String[] data = v.split("\\|");

        // indexes for date string
        int index = 0;
        String dateString = "";

        // traverse data to find date
        for(int i = 0; i < data.length; i++) {
            if(data[i].equals(Constants.date)) {
                // create substring
                dateString = v.substring(index);
                break;
            }

            index += data[i].length() + 1;
        }

		try {
			return new Route(data[1], 
							data[2], 
							Integer.parseInt(data[3]),
							data[4],
                            Date.parseDate(dateString)
							);			
		}
		catch(Exception e) {
			System.out.println("Cannot parse route.");
			return null;
		}

	}
}