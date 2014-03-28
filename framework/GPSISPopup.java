/** GPSISPopup
 * All Popup windows should extend this class in order to retain access to Framework variables
 * 
 * @author Vijendra Patel (vp302)
 */
package framework;

import java.awt.Font;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import object.StaffMember;

public class GPSISPopup extends JDialog {

	private static final long serialVersionUID = 1L;
	protected static StaffMember currentUser = GPSISModuleMain.getCurrentUser(); // the currentUser if needed
	protected static Map<String, Font> fonts = GPSISFramework.getFonts();
	protected static List<Date> publicHolidays = GPSISFramework.getPublicHolidays();
	
	/** GPSISPopup
	 * @param title
	 */
	public GPSISPopup(String title)
	{
		super(new JFrame(),title+ " - " + GPSISFramework.APPTITLE);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/image/favicon.gif"));
		this.setIconImage((icon.getImage()));
	}
}

/**
 * End of File: GPSISPopup.java
 * Location: framework
 */