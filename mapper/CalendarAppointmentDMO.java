package mapper;

import java.sql.PreparedStatement;
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
import exception.DuplicateEntryException;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

// a class to connect to the DB and pull/push data from/to there

public class CalendarAppointmentDMO extends GPSISDataMapper<CalendarAppointment> {
	
	// table names used by put and getter methods
	private static String tblRoutine = "RoutineAppointment";
	private static String tblCareManagement = "CareManagementAppointment";
	private static String tblCareProgramme = "CareProgramme";

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
    
    public List<CalendarAppointment> getAppointmentsByDoctorId(int id) 
    {
    	List<CalendarAppointment> appointments = new ArrayList<>();
    	
    	try {
			ResultSet rs = getResultSet(
							new SQLBuilder("staff_member_id","=", ""+id), tblRoutine
					);
			
			while(rs.next())
			{					
				appointments.add(getById(rs.getInt("ca_id")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    			
    	return appointments;
    }
    
    public boolean isTimeAvailable(int doctorId, Date date)
    {
    	List<CalendarAppointment> appointments = new ArrayList<>();
    	
    	try {
			ResultSet rs = getResultSet
					(
							new SQLBuilder("staff_member_id","=", ""+doctorId).AND("start_time", "=", "2014-01-01 15:30"), CalendarAppointmentDMO.tblRoutine
					);
			
			while(rs.next())
			{					
				appointments.add(getById(rs.getInt("ca_id")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(appointments.isEmpty())
    		return true;
    	else
    		return false;
    }
    
    
    public List<CalendarAppointment> getAppointmentsOfDoctorIdByDay(int id, Date day) throws EmptyResultSetException, SQLException
    {
    	String sql = "SELECT * FROM CalendarAppointment cA RIGHT JOIN RoutineAppointment rA ON rA.ca_id = cA.id LEFT JOIN CareManagementAppointment cMA ON cMA.ca_id = cA.id LEFT JOIN CareProgramme cP ON cMA.care_programme_id = cP.id WHERE DATE(start_time) = ? AND rA.staff_member_id = ? OR cP.staff_member_id = ?";
    	PreparedStatement pS = dbConnection.prepareStatement(sql); 
    	
    	pS.setDate(1, new java.sql.Date(day.getTime()));
    	pS.setInt(2, id);
    	pS.setInt(3, id);
    	
    	ResultSet res = pS.executeQuery();
    	
    	ArrayList<CalendarAppointment> a = new ArrayList<CalendarAppointment>();
    	while(res.next())
    	{
    		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    		Date startTime, endTime; 
    		cal.setTime(res.getTimestamp("start_time"));
    		startTime = cal.getTime();
    		cal.setTime(res.getTimestamp("end_time"));
    		endTime = cal.getTime();
    		if(res.getObject("care_programme_id") == null) // If Routine Appointment
    		{
    			a.add(new RoutineAppointment(res.getInt("id"),
    										startTime,
    										endTime,
    										patientDMO.getById(res.getInt("patient_id")),
    										staffMemberDMO.getById(res.getInt("staff_member_id")),
    										res.getString("summary")));
    		} else {
    			a.add(new CareManagementAppointment(res.getInt("id"),
													startTime,
													endTime,
													careProgrammeDMO.getById(res.getInt("care_programme_id"))
    					));
    		}
    		
    	}
    		
		return a;
    	
    }
    
    
	
	 // getter method for RoutineAppointment
	public RoutineAppointment getRoutineAppointmentById(int id) throws EmptyResultSetException
	{
		CalendarAppointment ca = this.getById(id);
		
		ResultSet rs;
		try {
			rs = getResultSet
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        
		throw new EmptyResultSetException();
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
		
		String sql = this.tableName 
				+ " LEFT OUTER JOIN " + tblRoutine + " ON "
				+ this.tableName + ".id = " + tblRoutine + ".ca_id"
				+ " LEFT OUTER JOIN " + tblCareManagement + " ON "
				+ this.tableName + ".id = " + tblCareManagement + ".ca_id"
				+ " LEFT OUTER JOIN Patient ON " + tblRoutine + ".patient_id = Patient.id"
				+ " LEFT OUTER JOIN PermanentPatient ON Patient.id = PermanentPatient.patient_id"
				+ " LEFT OUTER JOIN StaffMember ON " + tblRoutine + ".staff_member_id = StaffMember.id"
				+ " LEFT OUTER JOIN TempStaffMember ON StaffMember.id = TempStaffMember.staff_member_id"
				+ " LEFT OUTER JOIN CareProgramme ON " + tblCareManagement + ".care_programme_id = CareProgramme.id";
		
		try {
			
			ResultSet res = GPSISDataMapper.getResultSet(query, sql);
			//ResultSet res1 = GPSISDataMapper.getResultSet(query, this.tableName + " JOIN " + tblCareManagement + " ON " + this.tableName + ".id = " + tblCareManagement + ".ca_id");  
			
            if (res.next()) 
            {
            	if ((res.getInt("care_programme_id") == 0) && res.getInt("patient_id") != 0) { // Routine Appointment
            		Date startTime; 
            		cal.setTime(res.getTimestamp("start_time"));
            		startTime = cal.getTime();
            		cal.add(Calendar.MINUTE, 15);
            		Date endTime = cal.getTime();
            		
            		Patient p = patientDMO.buildPermanentPatient(res);
            		StaffMember sm = staffMemberDMO.buildStaffMember(res);
            		
            		return new RoutineAppointment(res.getInt("id"),startTime, endTime, p, sm, res.getString("summary"));
            	} else if (res.getInt("care_programme_id") != 0) {// Care Management Appointment
			      		
		    		cal.setTime(res.getTime("start_time"));
		    		Date startTime = cal.getTime();
		    		cal.setTime(res.getTime("end_time"));
		    		Date endTime = cal.getTime();
		    	
		    		CareProgramme cp = careProgrammeDMO.getById(res.getInt("care_programme_id"));
		        
		    		return new CareManagementAppointment(startTime, endTime, cp);
            	}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new EmptyResultSetException();
	}
	
	@Override
	public List<CalendarAppointment> getAllByProperties(SQLBuilder query) throws EmptyResultSetException
	{ // returns a List with all the CalendarAppointment objects
		  List<CalendarAppointment> calendarAppointments = new ArrayList<>(); // create a new HashSet with all the Calendar Appointments
		  String sql = this.tableName 
					+ " LEFT OUTER JOIN " + tblRoutine + " ON "
					+ this.tableName + ".id = " + tblRoutine + ".ca_id"
					+ " LEFT OUTER JOIN " + tblCareManagement + " ON "
					+ this.tableName + ".id = " + tblCareManagement + ".ca_id"
					+ " LEFT OUTER JOIN Patient ON " + tblRoutine + ".patient_id = Patient.id"
					+ " LEFT OUTER JOIN PermanentPatient ON Patient.id = PermanentPatient.patient_id"
					+ " LEFT OUTER JOIN StaffMember ON " + tblRoutine + ".staff_member_id = StaffMember.id"
					+ " LEFT OUTER JOIN TempStaffMember ON StaffMember.id = TempStaffMember.staff_member_id"
					+ " LEFT OUTER JOIN CareProgramme ON " + tblCareManagement + ".care_programme_id = CareProgramme.id";
          try {
        	  
        	ResultSet res = GPSISDataMapper.getResultSet(query, sql);
        	while(res.next()) {
	          	if ((res.getInt("care_programme_id") == 0) && res.getInt("patient_id") != 0) { // Routine Appointment
	          		Date startTime; 
	          		cal.setTime(res.getTimestamp("start_time"));
	          		startTime = cal.getTime();
	          		cal.add(Calendar.MINUTE, 15);
	          		Date endTime = cal.getTime();
	          		
	          		Patient p = patientDMO.buildPermanentPatient(res);
	          		StaffMember sm = staffMemberDMO.buildStaffMember(res);
	          		
	          		calendarAppointments.add(new RoutineAppointment(res.getInt("id"),startTime, endTime, p, sm, res.getString("summary")));
	          	} else if (res.getInt("care_programme_id") != 0) { // Care Management Appointment
				      		
			    		cal.setTime(res.getTime("start_time"));
			    		Date startTime = cal.getTime();
			    		cal.setTime(res.getTime("end_time"));
			    		Date endTime = cal.getTime();
			    	
			    		CareProgramme cp = careProgrammeDMO.getById(res.getInt("care_programme_id"));
			        
			    		calendarAppointments.add(new CareManagementAppointment(startTime, endTime, cp));
	          	}
        	}
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
          if(calendarAppointments.isEmpty())
        	  throw new EmptyResultSetException();
          else
        	  return calendarAppointments;
	}


	@Override
	public void put(CalendarAppointment o) {
        SQLBuilder sql = null;
        String pattern = "yyyy-MM-dd HH:mm";
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
            	.SET("patient_id","=",""+((RoutineAppointment) o).getPatientObject().getId())
            	.SET("staff_member_id", "=",""+((RoutineAppointment) o).getDoctorObject().getId())
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
