package module;
/** MainInterface
 * Displays the Main Window for GPSIS
 * Uses a CardLayout for the Navigation between Modules
 * @author VJ
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import framework.GPSISFramework;
import framework.GPSISModuleMain;

public class MainInterface extends GPSISFramework implements ActionListener {
	
	private static CardLayout cl = new CardLayout();
	private static JPanel view = new JPanel(cl);
	
	private JPanel buildHeader()
	{
		// set Header
		JPanel h = new JPanel(new GridBagLayout());
			h.setBackground(new Color(51, 51, 51));
			h.setForeground(new Color(235, 235, 235));
			
			// add logo
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.FIRST_LINE_START;
			gbC.gridx = 0;
			gbC.gridy = 0;
			gbC.weightx = 1;
			gbC.weighty = 1;
			gbC.gridwidth = 2;
			gbC.fill = GridBagConstraints.HORIZONTAL;			
			JLabel hLogo = new JLabel(GPSISModuleMain.getGPSISLogo());
				hLogo.setForeground(new Color(235, 235, 235));
		h.add(hLogo, gbC);
			
			// add title
			gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.PAGE_START;
			gbC.weightx = 1;
			gbC.weighty = 1;
			gbC.gridx = 2;
			gbC.gridy = 0;
			gbC.gridwidth = 10;
			gbC.fill = GridBagConstraints.HORIZONTAL;
			gbC.ipady = 10;
			JLabel hTitle = new JLabel("Header");
				hTitle.setForeground(new Color(235, 235, 235));
		h.add(hTitle, gbC);
		
			// add Navigation to Header
			gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			gbC.fill = GridBagConstraints.HORIZONTAL;
			gbC.gridx = 0;
			gbC.gridy = 1;
			gbC.weightx = 1;
			gbC.gridheight = 1;
			gbC.weighty = 1;
			gbC.gridwidth = 12;
			h.add(this.buildNavigation(), gbC);
		return h;
	}
	
	private JPanel buildNavigation()
	{
		// Set Navigation
		JPanel n = new JPanel(new GridBagLayout());
		// Create JButtons and add to Navigation Panel
		JButton patientRecordsBtn = new JButton("Patient Records");
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.PAGE_START;
			gbC.fill = GridBagConstraints.HORIZONTAL;
		n.add(patientRecordsBtn, gbC);
		JButton staffRecordsBtn = new JButton("Staff Records");
		n.add(staffRecordsBtn, gbC);
		JButton calendarAppointmentsBtn = new JButton("Calendar Appointments");
		n.add(calendarAppointmentsBtn, gbC);
		JButton prescriptionsBtn = new JButton("Prescriptions");
		n.add(prescriptionsBtn, gbC);
		JButton specialistReferralsBtn = new JButton("Specialist Referrals");
		n.add(specialistReferralsBtn, gbC);
		JButton careProgrammeManagementBtn = new JButton("Care Programme Management");
		n.add(careProgrammeManagementBtn, gbC);	
		
		
		// set ActionListners
		patientRecordsBtn.addActionListener(this);
		patientRecordsBtn.setActionCommand("Patient Records");
		staffRecordsBtn.addActionListener(this);
		staffRecordsBtn.setActionCommand("Staff Records");
		calendarAppointmentsBtn.addActionListener(this);
		calendarAppointmentsBtn.setActionCommand("Calendar Appointments");
		prescriptionsBtn.addActionListener(this);
		prescriptionsBtn.setActionCommand("Prescriptions");
		specialistReferralsBtn.addActionListener(this);
		specialistReferralsBtn.setActionCommand("Specialist Referrals");
		careProgrammeManagementBtn.addActionListener(this);
		careProgrammeManagementBtn.setActionCommand("Care Programme Management");
		
		// set the CardLayout
		GPSISModuleMain mM = new WelcomeModule();		
		view.add(mM.getModuleView(), "Welcome");
		
		mM = new PatientModule();
		view.add(mM.getModuleView(), "Patient Records");
		
		mM = new StaffMemberModule();
		view.add(mM.getModuleView(), "Staff Records");
		
		mM = new CalendarAppointmentsModule();
		view.add(mM.getModuleView(), "Calendar Appointments");
		
		mM = new PrescriptionsModule();
		view.add(mM.getModuleView(), "Prescriptions");
		
		mM = new SpecialistReferralsModule();
		view.add(mM.getModuleView(), "Specialist Referrals");
		
		mM = new CareProgrammeManagementModule();
		view.add(mM.getModuleView(), "Care Programme Management");
		
				
		n.setBackground(new Color(51, 51, 51));
		n.setForeground(new Color(235, 235, 235));
		return n;
	}
	
	private JPanel buildFooter()
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
			gbC.gridwidth = 9;
			JLabel fTitle = new JLabel("Logged in as " + currentUser.getUsername());
		f.add(fTitle, gbC);
				
			// add credits
			gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.FIRST_LINE_END;
			gbC.gridx = 9;
			gbC.gridy = 0;
			gbC.weightx = 1;
			gbC.weighty = 1;
			gbC.gridwidth = 3;
			JLabel fCredits = new JLabel("Created by: VJ, Salman, Milka, Oshan, Matt and Seun");
				fCredits.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		f.add(fCredits, gbC);
		
		f.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		return f;
	}
	
	public void createAndShowGUI()
	{
		JFrame gpsisMainFrame = new JFrame("General Practitioner's Surgery Information System");
		gpsisMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gpsisMainFrame.setLayout(new GridBagLayout());
		gpsisMainFrame.setBackground(new Color(240, 240, 240));
				
			// add the Header to the Window
			GridBagConstraints gbC = new GridBagConstraints(); // constraints for component to add to frame
			gbC.anchor = GridBagConstraints.PAGE_START;
			gbC.fill = GridBagConstraints.HORIZONTAL;
			gbC.gridx = 0;
			gbC.gridy = 0;
			gbC.weightx = 1;
			gbC.weighty = 0.1;
			gbC.gridwidth = 12;
			gbC.gridheight = 1;		
		gpsisMainFrame.add(this.buildHeader(), gbC);
				
			// Add the Module view to the Window
			gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			gbC.fill = GridBagConstraints.HORIZONTAL;
			gbC.gridx = 0;
			gbC.gridy = 1;
			gbC.weightx = 0.85;
			gbC.weighty = 1;
			gbC.gridwidth = 12;
			gbC.gridheight = 1;
			gbC.ipadx = 10;
			gbC.ipady = 10;
		gpsisMainFrame.add(view, gbC);
				
			// Add the footer to the Window	
			gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.PAGE_END;
			gbC.fill = GridBagConstraints.HORIZONTAL;
			gbC.gridx = 0;
			gbC.gridy = 2;
			gbC.weightx = 1;
			gbC.weighty = 0.05;
			gbC.gridwidth = 12;
		gpsisMainFrame.add(this.buildFooter(), gbC);
		
		gpsisMainFrame.pack();
		
		// Set the initial window size equal to 60% of the active display resolution
		GraphicsDevice screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		double w = screenSize.getDisplayMode().getWidth() * 0.6;
		double h = screenSize.getDisplayMode().getHeight() * 0.6;
		gpsisMainFrame.setSize((int)Math.round(w), (int)Math.round(h)); // initial size equal to 60% of active display size
		gpsisMainFrame.setLocationRelativeTo(null); // center on screen
		gpsisMainFrame.setVisible(true);
		
	}

	/** actionPerformed
	 *  Switch through the Card Layout given by the Action Command
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		cl.show(view, ae.getActionCommand());
	}
}
