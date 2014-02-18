package framework;
/** GPSISFramework
 * Superclass for the GPSIS Framework. The GPSIS System resides under this Root Node.
 * 
 * @author Vijendra Patel
 */

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import mapper.CalendarAppointmentDMO;
import mapper.CareProgrammeDMO;
import mapper.MedicineDMO;
import mapper.PatientDMO;
import mapper.PrescriptionDMO;
import mapper.RoomDMO;
import mapper.StaffMemberDMO;
import mapper.TaxOfficeDMO;
import module.LoginModule;
import object.StaffMember;

public class GPSISFramework {
	
	protected static RoomDMO roomDMO;
	protected static CareProgrammeDMO careProgrammeDMO = CareProgrammeDMO.getInstance();
	protected static StaffMemberDMO staffMemberDMO = StaffMemberDMO.getInstance();
	protected static TaxOfficeDMO taxOfficeDMO = TaxOfficeDMO.getInstance();
	protected static CalendarAppointmentDMO calendarAppointmentDMO = CalendarAppointmentDMO.getInstance();
    protected static PatientDMO patientDMO = PatientDMO.getInstance();
    protected static MedicineDMO medicineDMO = MedicineDMO.getInstance();
    protected static PrescriptionDMO prescriptionDMO = PrescriptionDMO.getInstance();
//	etc.
	
	protected static final String APPTITLE = "General Practitioner's Surgery Information System";
	protected static StaffMember currentUser;
	
	protected static HashMap<String, Font> fonts = new HashMap<String, Font>();
	
	/** initialise
	 * Initialises the Framework
	 * Initialisation List: 
	 *  - Database Connection
	 *  	- Check tables
	 *  		- If not, initialise tables
	 *  	- Check StaffMembers (if none, fresh installation, execute installation)
	 *  - Load Fonts
	 *  - Load Login Module
	 *  - Load Main Module
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
		
		System.out.println("\t- Loading Fonts");
			this.loadFonts();
		
		
		LoginModule m = new LoginModule();
		m.showLogin();	
	}
	
	/** TODO installGPSIS
	 * This method sets up GPSIS for the first time on a new system
	 * 1. Create Tables
	 * 2. Prompt for first Office Manager Username and Password and insert.
	 */
	public void installGPSIS()
	{
		
	}
	
	public void loadFonts()
	{

		File robotoFile = new File("font/Roboto-Regular.ttf");
		try {
			System.out.print("\t\t- Roboto");
			Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, robotoFile);
			fonts.put("Roboto", robotoFont);
			System.out.print("\t\t\tSuccess.");
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("FONT NOT FOUND");
			e.printStackTrace();
		}
	}
	
}

/**
 * End of File: GPSISFramework.java
 * Location: gpsis/framework
 */
