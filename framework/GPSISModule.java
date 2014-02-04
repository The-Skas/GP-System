package framework;

import java.awt.*;

import javax.swing.BorderFactory;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import object.StaffMember;

/** GPSISModule
 * Superclass for all Modules (Controllers) in GPSIS. For use when writing your Controller.
 * 
 * @author Vijendra Patel
 * @date 16/01/2014
 */

public abstract class GPSISModule extends GPSISFramework {
	
	protected JPanel header;
	protected JPanel navigation;
	protected JPanel view; // the module view
	protected JPanel footer;
	
	protected static StaffMember currentUser;
	
	// variable placeholders
	private static final String GPSISLOGO = "GP-SIS Logo"; //ImageIcon icon = new ImageIcon("", ""); USE LATER WHEN WE HAVE A LOGO
	
	protected static final String APPTITLE = "General Practitioner's Surgery Information System";
	
	
	public GPSISModule()
	{
		super();
	}
	
	public void createAndShowGUI()
	{
		
		JFrame gpsisMainFrame = new JFrame("General Practitioner's Surgery Information System");
		gpsisMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gpsisMainFrame.setLayout(new GridBagLayout());
		gpsisMainFrame.setBackground(new Color(240, 240, 240));
		
		
		// set Header
		JPanel header = new JPanel(new GridBagLayout());
		header.setBackground(new Color(51, 51, 51));
		header.setForeground(new Color(235, 235, 235));
		GridBagConstraints gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.FIRST_LINE_START;
		gbC.gridx = 0;
		gbC.gridy = 0;
		gbC.weightx = 1;
		gbC.weighty = 1;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		
		// add logo
		JLabel hLogo = new JLabel(GPSISLOGO);
			hLogo.setForeground(new Color(235, 235, 235));
		header.add(hLogo, gbC);
			
		// add title
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.PAGE_START;
		gbC.weightx = 1;
		gbC.weighty = 1;
		gbC.gridx = 1;
		gbC.gridy = 0;
		gbC.gridwidth = 2;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.ipady = 10;
		JLabel hTitle = new JLabel("Header");
			hTitle.setForeground(new Color(235, 235, 235));
		header.add(hTitle, gbC);
				
		// set Footer
		JPanel f = new JPanel(new GridBagLayout());
		
		// add footer title
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.FIRST_LINE_START;
		gbC.weightx = 1;
		gbC.weighty = 1;
		gbC.gridx = 0;
		gbC.gridy = 0;
		gbC.gridwidth = 2;
		//JLabel fTitle = new JLabel("GP-SIS Footer (could show currently logged in user, status of current operation)");
		JLabel fTitle = new JLabel("Logged in as " + currentUser.getUsername());
		f.add(fTitle, gbC);
				
		// add credits
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.FIRST_LINE_END;
		gbC.gridx = 3;
		gbC.gridy = 0;
		gbC.weightx = 1;
		gbC.weighty = 1;
		JLabel fCredits = new JLabel("Created by: VJ, Salman, Milka, Oshan, Matt and Seun");
		fCredits.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		f.add(fCredits, gbC);
		
		f.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		this.header = header;
		this.footer = f;
		
		// add the Header
		gbC = new GridBagConstraints(); // constraints for component to add to frame
		gbC.anchor = GridBagConstraints.PAGE_START;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.gridx = 0;
		gbC.gridy = 0;
		gbC.weightx = 1;
		gbC.weighty = 1;
		gbC.gridwidth = 3;
		gbC.ipady = 10;
		gpsisMainFrame.add(this.header, gbC);
		
		
		// Add the Module view
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.CENTER;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.gridx = 0;
		gbC.weightx = 1;
		gbC.weighty = 1;
		gbC.gridwidth = 3;
		gpsisMainFrame.add(this.view, gbC);
		
		// Add the footer
		
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.PAGE_END;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.gridx = 0;
		gbC.weightx = 1;
		gbC.weighty = 1;
		gbC.gridwidth = 3;
		gpsisMainFrame.add(this.footer, gbC);
		
		
		
		gpsisMainFrame.pack();
		gpsisMainFrame.setVisible(true);
		
		// Set the initial window size equal to 60% of the active display resolution
		GraphicsDevice screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		double w = screenSize.getDisplayMode().getWidth() * 0.6;
		double h = screenSize.getDisplayMode().getHeight() * 0.6;
		gpsisMainFrame.setSize((int)Math.round(w), (int)Math.round(h)); // initial size equal to 60% of active display size
		
	}
}

/**
 * End of File: GPSISModule.java
 * Location: gpsis/framework
 */