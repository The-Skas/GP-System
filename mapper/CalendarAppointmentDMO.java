package mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import object.CalendarAppointment;
import object.CareProgramme;
import object.RoutineAppointment;
import object.CareManagementAppointment;
import object.StaffMember;
import object.Patient;

import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarAppointmentDMO extends GPSISDataMapper<CalendarAppointment> {

	static	Calendar cal = java.util.Calendar.getInstance(); 
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
	public CalendarAppointment getByProperties(SQLBuilder query) throws EmptyResultSetException
	{
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
            		
            		return new RoutineAppointment(startTime, endTime, p, sm);
            		
            	} else { // return Care Programme Appointment
            		
            		cal.setTime(res.getTime("start_time"));
            		Date startTime = cal.getTime();
            		cal.setTime(res.getTime("end_time"));
            		Date endTime = cal.getTime();
            	
            	CareProgramme cp = careProgrammeDMO.getById(res.getInt("care_programme_id"));
                
                return new CareManagementAppointment(startTime, endTime, cp);
            	} 
            } else System.err.println("EMPTY SET");       
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new EmptyResultSetException();
	}
	
	@Override
	public Set<CalendarAppointment> getAllByProperties(SQLBuilder query) throws EmptyResultSetException
	{ // returns a Set with all the CalendarAppointment objects
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
          if(calendarAppointments.isEmpty())
        	  throw new EmptyResultSetException();
          else
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
        SQLBuilder sql = null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String startTime = format.format(o.getStartTime());
        String endTime = format.format(o.getEndTime());
        if(o.isRoutine())
        {
        sql = new SQLBuilder("id","=",""+o.getId())
            .SET("start_time","=",""+startTime)
            .SET("end_time", "=", ""+endTime)
            .SET("patient_id","=",""+((RoutineAppointment) o).getPatient().getId())
            .SET("staff_member_id", "=",""+((RoutineAppointment) o).getDoctor().getId());
        }
        else // it is not routine
        {
        	System.out.println("CalendarAppointment");
             sql = new SQLBuilder("id","=",""+o.getId())
            .SET("start_time","=",""+o.getStartTime())
            .SET("end_time", "=", ""+o.getEndTime())
            .SET("care_programme_id","=",""+((CareManagementAppointment) o).getCareProgramme().getId());
        }
           
    try {
        putHelper(sql, this.tableName, o);
        } catch (SQLException ex) {
        Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	public static void main (String[] args)
	{
		
		GPSISDataMapper.connectToDatabase();
		cal.set(2014, 5, 11, 9, 30);
		Date d = cal.getTime();
		
		cal.set(2014, 5, 11, 9, 45);
		Date d1 = cal.getTime();
		
		try 
		{
			CalendarAppointment ca = new RoutineAppointment(d, d1, patientDMO.getById(2), staffMemberDMO.getById(1));
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
