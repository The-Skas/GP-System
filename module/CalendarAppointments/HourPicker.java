package module.CalendarAppointments;

import java.text.ParseException;
import java.util.Calendar;

import mapper.CalendarAppointmentDMO;



  class HourPicker { 
 public static void main(String[] args) throws ParseException {
         CalendarAppointmentDMO.connectToDatabase();
        // new HourTable(1, "28/03/2014");
	 /*
	  * This could just be a class used to test stuff...
	  * 
	  */

	 // i want to compare hours ONLY
	 
	 String hour1 = "09:30";
	 String hour2 = "16:15";
	 
	 String[] parts1 = hour1.split(":");
	 String[] parts2 = hour2.split(":");
	 
	 Calendar cal1 = Calendar.getInstance();
	 cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts1[0]));
	 Calendar cal2 = Calendar.getInstance();
	 cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts2[0]));
	 
	 
	 
 }

  }
  

