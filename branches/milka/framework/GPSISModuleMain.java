/**
 * 
 */
package framework;

import javax.swing.JPanel;

import object.StaffMember;

/**
 * @author VJ
 *
 */
public abstract class GPSISModuleMain extends GPSISFramework {
	
	public abstract JPanel getModuleView();
	
	// variable placeholders
	public static final String GPSISLOGO = "GP-SIS Logo"; //ImageIcon icon = new ImageIcon("", ""); USE LATER WHEN WE HAVE A LOGO	
	
	public static StaffMember getCurrentUser()
	{
		return currentUser;
	}
	
	public static String getGPSISLogo()
	{
		return GPSISLOGO;
	}
}
