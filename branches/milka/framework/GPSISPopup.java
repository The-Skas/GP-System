/**
 * 
 */
package framework;

import javax.swing.JFrame;

import object.StaffMember;

/**
 * @author VJ
 *
 */
public class GPSISPopup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1092919107073466317L;
	protected static StaffMember currentUser = GPSISModuleMain.getCurrentUser(); // the currentUser if needed
	
	public GPSISPopup(String title)
	{
		super(GPSISFramework.APPTITLE + " - " + title);
	}
	
	
}
