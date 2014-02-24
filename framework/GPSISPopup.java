/**
 * 
 */
package framework;

import javax.swing.ImageIcon;
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
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/image/favicon.jpg"));
		this.setIconImage((icon.getImage()));
	}
	
	
}
