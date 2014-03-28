/** GPSISFramework
 * Superclass for the GPSIS Framework. The GPSIS System resides under this Root Node.
 * 
 * @author Vijendra Patel (vp302)
 */
package framework;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import exception.EmptyResultSetException;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import mapper.CalendarAppointmentDMO;
import mapper.CareProgrammeDMO;
import mapper.TrainingDayDMO;
import mapper.MedicineDMO;
import mapper.PatientDMO;
import mapper.PrescriptionDMO;
import mapper.ReferralDMO;
import mapper.RegisterDMO;
import mapper.RoomDMO;
import mapper.SQLBuilder;
import mapper.SpecialityDMO;
import mapper.StaffMemberDMO;
import mapper.TaxFormDMO;
import mapper.TaxOfficeDMO;
import module.LoginModule;
import module.MainInterface;
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
    protected static SpecialityDMO specialityDMO = SpecialityDMO.getInstance();
    protected static TaxFormDMO taxFormDMO = TaxFormDMO.getInstance();
    protected static RegisterDMO registerDMO = RegisterDMO.getInstance();
    protected static ReferralDMO referralDMO = ReferralDMO.getInstance();
//	etc.
	
	protected static final String APPTITLE = "General Practitioner's Surgery Information System";
	private final ImageIcon GPSISLOGO = new ImageIcon(this.getClass().getResource("/image/favicon.gif"));
	
	
	protected static StaffMember currentUser;
	private static final GPSISFramework instance = new GPSISFramework();
	
	protected static Map<String, Font> fonts = new HashMap<String, Font>();
	protected static List<Date> publicHolidays = new ArrayList<Date>();
	
	protected static GPSISSplash splashWindow;

	/** getInstance
	 * A Singleton implementation to ensure that only one GPSISFramework exists
	 * @return the ONLY instance of GPSISFramework
	 */
	public static GPSISFramework getInstance()
	{
		return instance;
	}	
    
	
	
	/** initialise
	 * Initialises the Framework
	 * Initialisation List: 
	 *  - Database Connection
	 *  - Load Fonts
	 *  - Load Login Module
	 *  - Load Main Module
	 */
	public void initialise()
	{
			
		splashWindow = new GPSISSplash();
			
		splashWindow.addText("\nNote: Connecting to Database may take 3-4 tries in the ITL.");
		splashWindow.addText("\nInitialising:");
		splashWindow.addText("\n\t- Connecting to Database");
		boolean dbConnected = false;
		for (int i = 1; i <= 5; i++) {			
			if (GPSISDataMapper.connectToDatabase()) {
				splashWindow.addText("\t\tSuccess.");
				dbConnected = true;
				break;
			} else {
				splashWindow.addText("\t\tFailed. Attempts: " + i + "/5");
				if (i < 5) {				
					splashWindow.addText("\n\tWaiting 3 Seconds before retry.");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (!dbConnected) {
			splashWindow.addText("\nUnable to Connect to Database... Exiting.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(1);
		}
		
		
		splashWindow.addText("\n\t- Loading Fonts");
			this.loadFonts();
		splashWindow.addText("\n\t- Loading Public Holidays");
			this.loadHolidays();
			
		splashWindow.addText("\nCompleted Tests...");
		try {
			Thread.sleep(1000);
			splashWindow.addText("\nLoading Login...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
			
		
		LoginModule m = new LoginModule();
		currentUser = m.showLogin();
		if (currentUser == null)
			System.exit(0); // exit the application as the user did not log in
		
		splashWindow.clearText();
		splashWindow.addText("\nLogged in as: " + currentUser.getName());
		
		splashWindow.addText("\nPre-loading Modules:");
		// Set the modules to load
		String[] modulesToLoad = {"Welcome", "Patient Records", "Staff Records", "Calendar Appointments", "Prescriptions", "Specialist Referrals"};
		for (String module : modulesToLoad) {
			if (module.length() < 15)
				splashWindow.addText("\n\t" + module + "\t");
			else
				splashWindow.addText("\n\t" + module);
			MainInterface.getInstance().loadModule(module);
			splashWindow.addText("\t\tDone.");
		}
		
		splashWindow.addText("\nLoading Main Interface...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		splashWindow.dispose();
		
		MainInterface.getInstance().createAndShowGUI();	
	}
	
	/** getGPSISLogo
	 * @return the GPSISLogo
	 */
	public static ImageIcon getGPSISLogo()
	{
		return GPSISFramework.getInstance().GPSISLOGO;
	}
		
	/** loadFonts
	 * loads external Fonts into the application, these are accessible via the fonts variable
	 */
	private void loadFonts()
	{
		try {
			// Roboto
			InputStream resource = this.getClass().getResourceAsStream("/font/Roboto-Regular.ttf");
			Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, resource);
			fonts.put("Roboto", robotoFont);

			// OpenSans
			resource = this.getClass().getResourceAsStream("/font/OpenSans-Regular.ttf");			
			Font openSans = Font.createFont(Font.TRUETYPE_FONT, resource);
			fonts.put("OpenSans", openSans);
			
			// Ubuntu
			resource = this.getClass().getResourceAsStream("/font/Ubuntu-Regular.ttf");			
			Font ubuntu = Font.createFont(Font.TRUETYPE_FONT, resource);
			fonts.put("Ubuntu", ubuntu);
			
			
			splashWindow.addText("\t\t\tSuccess.");			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			splashWindow.addText("\t\t\tError. File not Found.");
		}
	}
	
	/** loadHolidays
	 * parses an External iCS file containing the Dates of British Public TrainingDay into an ArrayList of Dates, 
	 * this is accessible via the publicHolidays variable
	 */
	private void loadHolidays()
	{
		try {
			URL holidays = new URL("https://www.gov.uk/bank-holidays/england-and-wales.ics");
			InputStream fin = holidays.openStream();

			CalendarBuilder builder = new CalendarBuilder();
			Calendar calendar = builder.build(fin);
	
			for (Iterator<?> i = calendar.getComponents().iterator(); i.hasNext();) {
			    Component component = (Component) i.next();
			    SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
			    publicHolidays.add(fm.parse(component.getProperty("DTSTART").getValue()));  			    
			}
			
			splashWindow.addText("\t\tSuccess.");
		} catch (IOException e) {
			splashWindow.addText("\t\tFailed. www.gov.uk/bank-holidays/england-and-wales.ics does not exist.");
		} catch (ParserException | ParseException e) {
			splashWindow.addText("\t\tFailed. Format changed in iCalendar");
		}
	}
	
	/** getFonts
	 * @return the Map of external available Fonts
	 */
	public static Map<String, Font> getFonts()
	{
		return fonts;
	}
	
	/** getPublicHolidays
	 * @return a List of Dates representing the Public TrainingDay
	 */
	public static List<Date> getPublicHolidays()
	{
		return publicHolidays;
	}

    /** getCurrentUser
     * @return the currently logged in Staff Member
     */
    public static StaffMember getCurrentUser()
    {
            return currentUser;
    }
	
	/** isHoliday
	 * quickly checks if a given Date is a holiday
	 * @param d
	 * @return true if the given date is a holiday, false otherwise
	 * TODO : Test isHoliday function (Add Training Days and check if they're listed)
	 */
	public boolean isHoliday(Date d)
	{
		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
		for (Date pubD : publicHolidays) {
			if (sDF.format(pubD).equals(sDF.format(d)))
				return true;
		}
		
		// check if the Date is a Training Day
		try {
			TrainingDayDMO.getInstance().getByProperties(new SQLBuilder("date", "=", sDF.format(d)));
			return true;
		} catch (EmptyResultSetException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
}

/**
 * End of File: GPSISFramework.java
 * Location: framework
 */
