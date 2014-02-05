package module;
/** Initial Module to load when GP-SIS is started.
 * This module loads the main interface including navigation to Navigate between Modules
 * 
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import framework.GPSISModule;

public class WelcomeModule extends GPSISModule {
	
	protected JPanel header;
	protected JPanel navigation;
	protected JPanel footer;
	
	public WelcomeModule()
	{
		super();
		JPanel welcomeView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("Welcome " + currentUser.getFirstName() + "!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
		welcomeView.add(greeting, gbC);
		
		this.view = welcomeView;
	}
	
	
	public void createAndShowGUI()
	{
		JFrame gpsisMainFrame = new JFrame("General Practitioner's Surgery Information System");
		gpsisMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gpsisMainFrame.setLayout(new GridBagLayout());
		gpsisMainFrame.setBackground(new Color(240, 240, 240));
		
		this.buildHeader(); // build the Header
		this.buildNavigation(); // build the Navigation
		this.buildFooter(); // build the Footer
				
		// add the Header to the Window
		GridBagConstraints gbC = new GridBagConstraints(); // constraints for component to add to frame
		gbC.anchor = GridBagConstraints.PAGE_START;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.gridx = 0;
		gbC.gridy = 0;
		gbC.weightx = 1;
		gbC.weighty = 0.1;
		gbC.gridwidth = 3;
		gbC.gridheight = 1;
		gbC.ipady = 10;
		
		gpsisMainFrame.add(this.header, gbC);
		
		// add the Navigation to the header
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.PAGE_START;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.gridx = 0;
		gbC.gridy = 1;
		gbC.weightx = 1;
		gbC.weighty = 0.05;
		gbC.gridwidth = 3;
		this.header.add(this.navigation, gbC);
		
		
		// Add the Module view to the Window
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.CENTER;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.gridx = 0;
		gbC.gridy = 2;
		gbC.weightx = 1;
		gbC.weighty = 0.85;
		gbC.gridwidth = 3;
		gbC.gridheight = 3;
		this.view.setBackground(Color.GREEN);
		gpsisMainFrame.add(this.view, gbC);
		
		// Add the footer to the Window	
		gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.PAGE_END;
		gbC.fill = GridBagConstraints.HORIZONTAL;
		gbC.gridx = 0;
		gbC.weightx = 1;
		gbC.weighty = 0.05;
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
	
	private void buildHeader()
	{
		// set Header
		JPanel h = new JPanel(new GridBagLayout());
		h.setBackground(new Color(51, 51, 51));
		h.setForeground(new Color(235, 235, 235));
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
		h.add(hLogo, gbC);
			
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
		h.add(hTitle, gbC);		
		this.header = h;
	}
	
	private void buildNavigation()
	{
		// Set Navigation
		JPanel n = new JPanel(new GridBagLayout());
		
			JLabel nvTest = new JLabel("Navigation");
		n.add(nvTest);
		
		n.setBackground(Color.cyan);
		this.navigation = n;
	}
	
	private void buildFooter()
	{
		// set Footer
		JPanel f = new JPanel(new GridBagLayout());
		
		// add footer title
		GridBagConstraints gbC = new GridBagConstraints();
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
		this.footer = f;
	}
}
