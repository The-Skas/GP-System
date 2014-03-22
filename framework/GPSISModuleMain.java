/** GPSISModuleMain
 * Main Interface Module template
 * 
 * @author Vijendra Patel (vp302)
 */
package framework;

import javax.swing.JPanel;

public abstract class GPSISModuleMain extends GPSISFramework {

	private static final String GPSISLOGO = "GP-SIS"; //ImageIcon icon = new ImageIcon("", ""); USE LATER WHEN WE HAVE A LOGO	
	
	/** getGPSISLogo
	 * @return the GPSISLogo
	 */
	public static String getGPSISLogo()
	{
		return GPSISLOGO;
	}
	
	/** getModuleView
	 * @return a JPanel for the module
	 */
	public abstract JPanel getModuleView();
}

/**
 * End of File: GPSISModuleMain.java
 * Location: framework
 */