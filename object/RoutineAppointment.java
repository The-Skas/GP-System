package object;
import java.util.Calendar;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
public class RoutineAppointment extends CalendarAppointment{
 
 ///   Calendar cal = java.util.Calendar.getInstance(); 
     
    protected Patient patient; 
    protected StaffMember doctor;
    protected String summary;
     
    //This Constructor is used when creating a New Routine Appointment object
    public RoutineAppointment(Date sT, Date eT, Patient p, StaffMember d, String s) { 
        super(sT, eT);
        this.patient = p;
        this.doctor = d;
        this.summary = s;
        calendarAppointmentDMO.put(this);
    }
 
    //This Constructor is used when retrieving Routine Appointment objects from the Database
    public RoutineAppointment(int id, Date sT, Date eT, Patient p, StaffMember d, String s) { 
        super(id, sT, eT);
        this.patient = p;
        this.doctor = d;
        this.summary = s;
    }
     
    public void setSummary(String s)
    {
     this.summary = s;
     calendarAppointmentDMO.put(this);
    }
    
    public String getSummary()
    {
    	return this.summary;
    }
    
    public Patient getPatientObject()
    {
    	return this.patient;
    }
    
    public StaffMember getDoctorObject()
    {
    	return this.doctor;
    }
 
    public String getPatient()
    {
        return this.patient.getFirstName() + " " + this.patient.getLastName();
    }
     
    public String getDoctor() 
    {
        return this.doctor.getUsername();
    }
    

    } 