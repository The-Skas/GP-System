/**
 * 
 */
package framework;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import object.StaffMember;

/**
 * @author VJ
 *
 */
public class GPSISPopup extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1092919107073466317L;
	protected static StaffMember currentUser = GPSISModuleMain.getCurrentUser(); // the currentUser if needed
	
	public GPSISPopup(String title)
	{
		super(new JFrame(), GPSISFramework.APPTITLE + " - " + title, JDialog.ModalityType.APPLICATION_MODAL);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/image/favicon.jpg"));
		this.setIconImage((icon.getImage()));
	}
	
	
}
