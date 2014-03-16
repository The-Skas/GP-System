package framework;
/** GPSISFramework
 * Superclass for the GPSIS Framework. The GPSIS System resides under this Root Node.
 * 
 * @author Vijendra Patel
 */

import java.awt.Color;
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
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JWindow;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import mapper.CalendarAppointmentDMO;
import mapper.CareProgrammeDMO;
import mapper.MedicineDMO;
import mapper.PatientDMO;
import mapper.PrescriptionDMO;
import mapper.RoomDMO;
import mapper.SpecialityDMO;
import mapper.StaffMemberDMO;
import mapper.TaxFormDMO;
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
    protected static SpecialityDMO specialityDMO = SpecialityDMO.getInstance();
    protected static TaxFormDMO taxFormDMO = TaxFormDMO.getInstance();
//	etc.
	
	protected static final String APPTITLE = "General Practitioner's Surgery Information System";
	protected static StaffMember currentUser;
	private static final GPSISFramework instance = new GPSISFramework();
	
	protected static Map<String, Font> fonts = new HashMap<String, Font>();
	protected static List<Date> publicHolidays = new ArrayList<Date>();
	
	public static JTextArea debug;
	public static JWindow splashWindow;
	
	public static GPSISFramework getInstance()
	{
		return instance;
	}
	
	public static Map<String, Font> getFonts()
	{
		return fonts;
	}
	
	public static List<Date> getPublicHolidays()
	{
		return publicHolidays;
	}

    public static StaffMember getCurrentUser()
    {
            return currentUser;
    }
    
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
		splashWindow = new JWindow();
		splashWindow.setLayout(new MigLayout(new LC(), new AC().grow(), new AC().grow()));
		splashWindow.setSize(600, 350);
		splashWindow.setLocationRelativeTo(null);
		ImageIcon header = new ImageIcon(this.getClass().getResource("/image/splash_header.jpg"));
		splashWindow.add(new JLabel(header), new CC().span().grow().wrap().dockNorth());
		
		debug = new JTextArea();
		debug.setBackground(new Color(51, 51, 51));
		debug.setForeground(new Color(240, 240, 240));
		debug.setEditable(false);

		splashWindow.getContentPane().setBackground(new Color(51, 51, 51));
		splashWindow.add(debug, new CC().pad("5px").grow().span());

		splashWindow.setVisible(true);
		
		debug.setText(debug.getText() + "\nNote: Connecting to Database may take 3-4 tries in the ITL.");
		debug.setText(debug.getText() + "\nRunning Tests:");
		boolean dbConnected = false;
		for (int i = 1; i <= 5; i++) {
			debug.setText(debug.getText() + "\n\t- Connecting to Database");
			
			if (GPSISDataMapper.connectToDatabase()) {
				debug.setText(debug.getText() + "\t\tSuccess.");
				dbConnected = true;
				break;
			} else {
				debug.setText(debug.getText() + "\t\tFailed. Attempts: " + i + "/5");
				debug.setText(debug.getText() + "\n\tWaiting 3 Seconds before retry.");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (!dbConnected) {
			debug.setText(debug.getText() + "\nUnable to Connect to Database... Exiting.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(1);
		}			
		
		debug.setText(debug.getText() + "\n\t- Loading Fonts");
			this.loadFonts();
		debug.setText(debug.getText() + "\n\t- Loading Public Holidays");
			this.loadHolidays();
			
		debug.setText(debug.getText() + "\nCompleted Tests...");
		try {
			Thread.sleep(1000);
			debug.setText(debug.getText() + "\nLoading Login...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


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
	
	/** loadFonts
	 * loads external Fonts into the application, these are accessible via the fonts variable
	 */
	private void loadFonts()
	{
		try {
			// Roboto
			debug.setText(debug.getText() + "\n\t\t- Roboto");
			InputStream resource = this.getClass().getResourceAsStream("/font/Roboto-Regular.ttf");
			Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, resource);
			fonts.put("Roboto", robotoFont);
			debug.setText(debug.getText() + "\t\t\tSuccess.");
			
			// OpenSans
			debug.setText(debug.getText() + "\n\t\t- Open Sans");
			resource = this.getClass().getResourceAsStream("/font/OpenSans-Regular.ttf");			
			Font openSans = Font.createFont(Font.TRUETYPE_FONT, resource);
			fonts.put("OpenSans", openSans);
			debug.setText(debug.getText() + "\t\t\tSuccess.");
			
			// Ubuntu
			debug.setText(debug.getText() + "\n\t\t- Ubuntu");
			resource = this.getClass().getResourceAsStream("/font/Ubuntu-Regular.ttf");			
			Font ubuntu = Font.createFont(Font.TRUETYPE_FONT, resource);
			fonts.put("Ubuntu", ubuntu);
			debug.setText(debug.getText() + "\t\t\tSuccess.");
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("\t\t\tError. File not Found.");
		}
	}
	
	/** loadHolidays
	 * parses an External iCS file containing the Dates of British Public Holidays into an ArrayList of Dates, 
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
			
			debug.setText(debug.getText() + "\t\tSuccess.");
		} catch (IOException e) {
			debug.setText(debug.getText() + "\t\tFailed. www.gov.uk/bank-holidays/england-and-wales.ics does not exist.");
		} catch (ParserException | ParseException e) {
			debug.setText(debug.getText() + "\t\tFailed. Format changed in iCalendar");
		}
	}
	
	/** isHoliday
	 * quickly checks if a given Date is a holiday
	 * @param d
	 * @return true if the given date is a holiday, false otherwise
	 */
	public boolean isHoliday(Date d)
	{
		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
		for (Date pubD : publicHolidays)
		{
			if (sDF.format(pubD).equals(sDF.format(d)))
				return true;
		}
		return false;
	}
	
}

/**
 * End of File: GPSISFramework.java
 * Location: gpsis/framework
 */
