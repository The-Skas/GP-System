/**
 * 
 */
package object;


/**
 * @author VJ
 *
 */
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
public class MedicalStaffMember extends StaffMember {
	
	//protected Set<CalendarAppointment> appointments = new HashSet<CalendarAppointment>();
	//protected Set<Referral> referrals = new HashSet<Referral>();
	//protected Set<Prescription> prescriptions = new HashSet<Prescription>();
	//protected Set<Family> families = new HashSet<Family>();
	protected Set<CareProgramme> careProgrammes = new HashSet<CareProgramme>();
	
	// retrieve from database
	public MedicalStaffMember(int id, String u, byte[] p, String fN, String lN, boolean fT, Calendar sD, boolean oM, String r, int hA)
	{
		
		super(id, u, p, fN, lN, fT, sD, oM, r, hA);
		// retrieve appointments
		
		// retrieve referrals
		
		// retrieve prescriptions
		
		// retrieve families
		
		// retrieve care programmes
	}
	
	// insert into database
	public MedicalStaffMember(String u, String p, String fN, String lN, boolean fT, Calendar sD, boolean oM, String r, int hA)
	{
		super(u, p, fN, lN, fT, sD, oM, r, hA);
	}
	
	public boolean hasSpeciality(Speciality s)
	{
		//return this.specialities.contains(s);
		return false;
	}
	
	public boolean isAvailable(Date c)
	{
		//return !this.unavailables.contains(c) && !this.appointments.contains(c) && !this.holidays.contains(c);
		return false;
	}
}
