package mapper;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import framework.GPSISDataMapper;
import object.CalendarAppointment;
import object.CareProgramme;
import object.RoutineAppointment;
import object.CareManagementAppointment;
import object.StaffMember;

import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarAppointmentDMO extends GPSISDataMapper<CalendarAppointment> {

	Calendar cal = java.util.Calendar.getInstance(); 
    private CalendarAppointmentDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static CalendarAppointmentDMO instance; // to make it a singleton 

    public static CalendarAppointmentDMO getInstance() 
    {
        if(instance == null)
        {
            instance = new CalendarAppointmentDMO("CalendarAppointment");
        }
        return instance;
    }

	@Override
	public Set<CalendarAppointment> getAll() {
		// TODO Auto-generated method stub
		return getAllByProperties( new SQLBuilder());	
	}

	@Override
	public CalendarAppointment getById(int id) {
		// TODO Auto-generated method stub
		return this.getByProperties(new SQLBuilder("id", "=", ""+id));
	}

	@Override 
	public CalendarAppointment getByProperties(SQLBuilder query) {
		try {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
              
            if (res.next()) 
            { // if found, create a new CalendarAppointment object
            	if (res.getInt("care_programme_id") == 0) // return Routine Appointment
            	{
            		Date startTime; 
            		cal.setTime(res.getTime("start_time"));
            		startTime = cal.getTime();
            		cal.add(Calendar.MINUTE, 15);
            		Date endTime = cal.getTime();
            		
            		Patient p = patientDMO.getById(res.getInt("patient_id")); 
            		StaffMember sm = staffMemberDMO.getById(res.getInt("staff_member_id"));
            		
            		return new RoutineAppointment(startTime, endTime, p, sm, res.getString("summary"));
            		
            	} else { // return Care Programme Appointment
            		
            		Date startTime; 
            		Date endTime;
            		cal.setTime(res.getTime("start_time"));
            		cal.setTime(res.getTime("end_time"));
            	
            	StaffMember sM = staffMemberDMO.getById(res.getInt("staff_member_id"));
            	CareProgramme cp = careProgrammeDMO.getById(res.getInt("care_programme_id"));
                
                return new CareManagementAppointment(startTime, endTime, cp);
            	} 
            } else System.err.println("EMPTY SET");       
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	@Override
	public Set<CalendarAppointment> getAllByProperties(SQLBuilder query) { // returns a Set with all the CalendarAppointment objects
		  Set<CalendarAppointment> calendarAppointments = new HashSet<>(); // create a new HashSet with all the Calendar Appointments
          try {            
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);                                  
            while(res.next()) { // if found, create a CalendarAppointment object              
               calendarAppointments.add(new CalendarAppointment( res.getInt("id"), 
            		   										     res.getDate("startTime"),
            		   										     res.getDate("endTime")) );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calendarAppointments;//To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void removeById(int id) { // used the same method as StaffMember
        try {
            removeByProperty(new SQLBuilder("id","=",""+id));
        } catch (SQLException ex) {
            Logger.getLogger(CalendarAppointmentDMO.class.getName()).log(Level.SEVERE, null, ex); 
        }
	}
	
	   public void removeByProperty(SQLBuilder query) throws SQLException 
	    {
	        GPSISDataMapper.removeByPropertyHelper(query, this.tableName);   
	    }

	@Override
	public void put(CalendarAppointment o) {
		
		if (o.getId() != 0) // if the object already exists on the database, use UPDATE
			try
			{
				PreparedStatement pS = dbConnection.prepareStatement("UPDATE CalendarAppointment SET some_property = ? WHERE staff_member.id = ?");
				//pS.setString(1, o.someCalendarAppointmentGetMethod());
				pS.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else // Use INSERT as the Object needs to be created on the database
		{
			try
			{
				PreparedStatement pS = dbConnection.prepareStatement("INSERT INTO CalendarAppointment (some_values) VALUES (?)");
				//pS.setString(1, o.anotherCalendarAppointmentGetMethod());
				pS.executeUpdate();
  			}
  			catch (SQLException e)
  			{
  				e.printStackTrace();
  			}		  
  		}		
	}
}
