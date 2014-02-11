package object;
import java.util.Date;
 
public class CareManagementAppointment extends CalendarAppointment{
 
    protected CareProgramme cp;
     
    public CareManagementAppointment(Date sT, Date eT, CareProgramme cp) { //to be done in the module
        super(sT, eT);
        this.cp = cp;
    }
 
    public CareManagementAppointment(int id, Date sT, Date eT, CareProgramme cp) { //coming from DB
        super(id, sT, eT);
        this.cp = cp;
    }
     
    public CareProgramme getCareProgramme()
    {
        return this.cp;
    }
     
}