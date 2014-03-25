package object;

import java.util.Date;

import framework.GPSISObject;

public class Holidays extends GPSISObject  {
	
	protected Date d;
	
    public Holidays(int id, Date d) // constructor 1 - already exists in database
    { 
        this.id = id;    
        this.d = d;
    }

    public Holidays(Date d) // constructor 2 - insert into database
    { 
        this.d = d;

        // Need to retrieve ID from Query
    }
    
    public Date getDate(){
    	return this.d;
    }

}
