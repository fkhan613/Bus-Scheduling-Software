/***************************************
 * File: Date.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan	   
 * Program Description:		   
 * This class contains the departure date of a bus					   
 ***************************************/

class Date {

    private int day, month, year;

    // default constructor
    public Date() {
        this(1, 1, 2000);
    }

    // custom constructor
    public Date(int d, int m, int y) {
        setYear(y);
        setMonth(m);
        setDay(d);
    }

    //getters & setters
    public int getDay(){return day;}
    public int getMonth(){return month;}
    public int getYear(){return year;}

    public void setDay(int v){this.day = checkDay(v);}
    public void setMonth(int v){this.month = checkMonth(v);}
    public void setYear(int v){this.year = v;}

    //toString class
    public String toString(){
        String text = "%02d / %02d / %4d";
        return String.format(text, day, month, year);
    }

    //checks the day entered to see if it is in range of the month
    private int checkDay(int d) {
        int[] daysPerMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        //check leap year
        if(((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
            // make February have 29 days
            daysPerMonth[2] = 29;
        }

        //check if the day is in range of the month
        if (d >= 1 && d <= daysPerMonth[this.month]) {
            return d;
        } 
        else {
            System.err.println("\n~~~DAY OUT OF RANGE~~~");
            return 1;
        }
    }

    // this private method checks if the month is valid
    private int checkMonth(int month) {
        if (month >= 1 && month <= 12){
            return month;
        } else {
            System.err.println("\n~~~INVALID MONTH~~~");
        }
        return 1;
    }

	// returns a string in format for saving in text files
	public static String dateToString(Date d) {
		return Constants.date + "|" + d.getDay() + "|" + d.getMonth() + "|" + d.getYear();
	}

	// returns a date class using a string
	public static Date parseDate(String v) {
		String[] data = v.split("\\|");

		// try to return a new date instance based on the string given
		try {
			return new Date(Integer.parseInt(data[1]), // day
											Integer.parseInt(data[2]), // month
											Integer.parseInt(data[3]) // year
											);				
		}
		catch(Exception e) {
			System.out.println("Invalid date data.");
			return null;
		}

		}
}