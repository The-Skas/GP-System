package framework;

import javax.swing.JPanel;
import object.StaffMember;

/** GPSISModule
 * Superclass for all Modules (Controllers) in GPSIS. For use when writing your Controller.
 * 
 * @author Vijendra Patel
 * @date 16/01/2014
 */

public abstract class GPSISModule extends GPSISFramework {
	
	
	protected JPanel view; // the module view
	protected static StaffMember currentUser;
	
	// variable placeholders
	protected static final String APPTITLE = "General Practitioner's Surgery Information System";
	protected static final String GPSISLOGO = "GP-SIS Logo"; //ImageIcon icon = new ImageIcon("", ""); USE LATER WHEN WE HAVE A LOGO	
	
	
	
	public GPSISModule()
	{
		super();
	}
}

/**
 * End of File: GPSISModule.java
 * Location: gpsis/framework
 */