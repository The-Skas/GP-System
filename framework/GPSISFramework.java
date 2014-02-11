package framework;
/** GPSISFramework
 * Superclass for the GPSIS Framework. The GPSIS System resides under this Root Node.
 * 
 * @author Vijendra Patel
 * @version 2
 */

import mapper.CalendarAppointmentDMO;
import mapper.CareProgrammeDMO;
import mapper.PatientDMO;
import mapper.RoomDMO;
import mapper.StaffMemberDMO;
import mapper.TaxOfficeDMO;
import module.LoginModule;
import object.StaffMember;

public class GPSISFramework {
	
	protected static RoomDMO roomDMO;
	protected static CareProgrammeDMO careProgrammeDMO;
	protected static StaffMemberDMO staffMemberDMO = StaffMemberDMO.getInstance();
	protected static TaxOfficeDMO taxOfficeDMO = TaxOfficeDMO.getInstance();
	protected static CalendarAppointmentDMO calendarAppointmentDMO = CalendarAppointmentDMO.getInstance();
        protected static PatientDMO patientDMO = PatientDMO.getInstance();
//	protected static AppointmentDMO appointmentDMO
//	etc.
	
	protected static final String APPTITLE = "General Practitioner's Surgery Information System";
	protected static StaffMember currentUser;
	
	
	/** initialise
	 * Initialises the Framework
	 * Initialisation List: 
	 *  - Database Connection
	 *  	- Check tables
	 *  		- If not, initialise tables
	 *  	- Check if Current Day is in StaffMember Register, otherwise create entry
	 *  	- Check StaffMembers (if none, fresh installation, execute installation)
	 *  - Load Login Module
	 *  - Load Menu Module
	 */
	public void initialise()
	{
		
		System.out.println("GPSIS Main Framework");
		System.out.println("Running Tests:");
		System.out.print("\t- Connecting to Database");
		if ((GPSISDataMapper.connectToDatabase()))
			System.out.println("\t\tSuccess.");
		else
		{
			System.out.println("\t\tFailed.");
			System.exit(0);
		}
		
		LoginModule m = new LoginModule();
		m.showLogin();
	
	}
	
	
	
}

/**
 * End of File: GPSISFramework.java
 * Location: gpsis/framework
 */
