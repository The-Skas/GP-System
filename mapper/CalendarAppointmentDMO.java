package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import object.CalendarAppointment;
import object.CareManagementAppointment;
import object.CareProgramme;
import object.Patient;
import object.RoutineAppointment;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

// a class to connect to the DB and pull/push data from/to there

public class CalendarAppointmentDMO extends GPSISDataMapper<CalendarAppointment> {
	
	// table names used by put and getter methods
	private static String tblRoutine = "RoutineAppointment";
	private static String tblCareManagement = "CareManagementAppointment";

	static	Calendar cal = java.util.Calendar.getInstance(); 
	
    private CalendarAppointmentDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static CalendarAppointmentDMO instance; // singleton 

    public static CalendarAppointmentDMO getInstance() // make it a singleton
    {
        if(instance == null)
        {
            instance = new CalendarAppointmentDMO("CalendarAppointment");
        }
        return instance;
    }
	
	 // getter method for RoutineAppointment
	public RoutineAppointment getRoutineAppointmentById(int id) throws EmptyResultSetException, SQLException
	{
		CalendarAppointment ca = this.getById(id);
		
		ResultSet rs = getResultSet
                (
                        new SQLBuilder("ca_id","=",""+id),CalendarAppointmentDMO.tblRoutine
                );
        
        //Returns a RoutineAppointment if found
        if(rs.next())
        {
        	Patient p = PatientDMO.getInstance().getById(rs.getInt("patient_id"));
        	StaffMember d = StaffMemberDMO.getInstance().getById(rs.getInt("doctor_id"));
        	
    		return new RoutineAppointment(id, ca.getStartTime(), ca.getEndTime(), p, d, rs.getString("summary"));

        }
		return null;
	}
	
	 // getter method for CareManagementAppointment
	public CareManagementAppointment getCareManagementAppointmentById(int id) throws EmptyResultSetException, SQLException
	{
		CalendarAppointment ca= this.getById(id);
		
		ResultSet rs = 
               GPSISDataMapper.getResultSet
               (
                       new SQLBuilder("ca_id","=",""+id),CalendarAppointmentDMO.tblCareManagement
               );
       
       //Returns a tblCareManagementAppointment if found
       if(rs.next())
       {
       	CareProgramme cp = CareProgrammeDMO.getInstance().getById(rs.getInt("id"));
       	
   		return new CareManagementAppointment(id, ca.getStartTime(), ca.getEndTime(), cp);

       }
		return null;
	}
	
	public CalendarAppointment getByProperties(SQLBuilder query) throws EmptyResultSetException
	{
		try {
			
			ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName + " JOIN " + tblRoutine + " ON " + this.tableName + ".id = " + tblRoutine + ".ca_id");
			ResultSet res1 = GPSISDataMapper.getResultSet(query, this.tableName + " JOIN " + tblCareManagement + " ON " + this.tableName + ".id = " + tblCareManagement + ".ca_id");  
			
            if (res.next()) 
            { 
            		Date startTime; 
            		cal.setTime(res.getTime("start_time"));
            		startTime = cal.getTime();
            		cal.add(Calendar.MINUTE, 15);
            		Date endTime = cal.getTime();
            		
            		Patient p = patientDMO.getById(res.getInt("patient_id"));
            		StaffMember sm = staffMemberDMO.getById(res.getInt("staff_member_id"));
            		
            		return new RoutineAppointment(startTime, endTime, p, sm);
            }
            	
            	else if (res1.next())
            {             			      		
            		cal.setTime(res.getTime("start_time"));
            		Date startTime = cal.getTime();
            		cal.setTime(res.getTime("end_time"));
            		Date endTime = cal.getTime();
            	
            		CareProgramme cp = careProgrammeDMO.getById(res.getInt("care_programme_id"));
                
            		return new CareManagementAppointment(startTime, endTime, cp);
            } 
            		else System.err.println("EMPTY SET");       
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new EmptyResultSetException();
	}
	
	@Override
	public List<CalendarAppointment> getAllByProperties(SQLBuilder query) throws EmptyResultSetException
	{ // returns a Set with all the CalendarAppointment objects
		  List<CalendarAppointment> calendarAppointments = new ArrayList<>(); // create a new HashSet with all the Calendar Appointments
          try {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName + " JOIN " + tblRoutine + " ON " + this.tableName + ".id = " + tblRoutine + ".ca_id");                                  
            while(res.next()) { // if found, create a CalendarAppointment object  
            	// return all Routine Appointments
          		Date startTime; 
          		cal.setTime(res.getTimestamp("start_time"));
          		startTime = cal.getTime();
          		cal.add(Calendar.MINUTE, 15);
          		Date endTime = cal.getTime();
          		
          		Patient p = patientDMO.getById(res.getInt("patient_id"));
          		StaffMember sm = staffMemberDMO.getById(res.getInt("staff_member_id"));
          		
          		calendarAppointments.add(new RoutineAppointment(res.getInt("id"),startTime, endTime, p, sm, res.getString("summary")));
            }
           
            ResultSet res1 = GPSISDataMapper.getResultSet(query, this.tableName + " JOIN " + tblCareManagement + " ON " + this.tableName + ".id = " + tblCareManagement + ".ca_id");
            while(res1.next()) { 
          		
          		cal.setTime(res1.getTimestamp("start_time"));
          		Date startTime = cal.getTime();
          		cal.setTime(res1.getTimestamp("end_time"));
          		Date endTime = cal.getTime();
          	
          	    CareProgramme cp = careProgrammeDMO.getById(res1.getInt("care_programme_id"));
              
          		calendarAppointments.add(new CareManagementAppointment(res1.getInt("id"), startTime, endTime, cp));
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
	public void put(CalendarAppointment o) {
        SQLBuilder sql = null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String startTime = format.format(o.getStartTime());
        String endTime = format.format(o.getEndTime());

        sql = new SQLBuilder("id","=",""+o.getId()) // create and add a CalendarAppointment
			.SET("start_time","=",""+startTime)
			.SET("end_time", "=", ""+endTime); 
        try {
        	putHelper(sql, this.tableName, o);
        } catch (SQLException ex) {
        	Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(o instanceof RoutineAppointment) // create and add a RoutineAppointment
        {        	
            String summary = ((RoutineAppointment) o).getSummary();
        	sql = new SQLBuilder("ca_id","=",""+o.getId()) // get the same CalendarAppointment id?
            	.SET("patient_id","=",""+((RoutineAppointment) o).getPatient().getId())
            	.SET("staff_member_id", "=",""+((RoutineAppointment) o).getDoctor().getId())
        	    .SET("summary", "=",""+summary);
        	try {
                putHelper(sql, tblRoutine, o);
            } catch (SQLException ex) {
                Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else  		// create and add a CareManagementAppointment
        {
             sql = new SQLBuilder("ca_id","=",""+o.getId())
             	.SET("care_programme_id","=",""+((CareManagementAppointment) o).getCareProgramme().getId());
             try {
                 putHelper(sql, tblCareManagement, o);
             } catch (SQLException ex) {
                 Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }
}
