package object;
import java.util.Calendar;
import java.util.Date;
 
public class RoutineAppointment extends CalendarAppointment{
 
    Calendar cal = java.util.Calendar.getInstance(); 
     
    protected Patient patient; 
    protected StaffMember doctor;
    protected String summary;
     
    public RoutineAppointment(Date sT, Date eT, Patient p, StaffMember d) { //to be done in the module
        super(sT, eT);
        this.patient = p;
        this.doctor = d;
        calendarAppointmentDMO.put(this);
    }
 
    public RoutineAppointment(int id, Date sT, Date eT, Patient p, StaffMember d, String s) { //coming from DB
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
 
    public Patient getPatient()
    {
        return this.patient;
    }
     
    public StaffMember getDoctor()
    {
        return this.doctor;
    }
    // for Routine Appointments, eT should always be sT + 15 mins
    /* public RoutineAppointment makeNewRoutineAppointment(Date sT, Date eT, StaffMember d, String s){
         
              check if it is in the opening hours of the surgery Mon-Fri 9am to 5.30pm and Sat 9am-12 noon
             check if the day is not a public or bank holiday or a training day (determined by office manager)
             respect availability of nurses and doctors 
             can only be made in the future - check if the dateTime? is not in the past
         
         
        // check if it is in the opening hours
        // or maybe better check if doctor is available
         
        // can only be made in the future
        if(sT.before(cal.getTime())){
             System.out.println("Sorry, an appointment can only be made in the future! Please choose a new Start Time.");
             // return to previous menu to select a new sT
        }
         
        if(d.isAvailable(sT)){
             
        }
         
        RoutineAppointment ra = new RoutineAppointment(sT, eT, d, s);
        return ra; */
    } 