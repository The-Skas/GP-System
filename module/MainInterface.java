package module;
/** MainInterface
 * Displays the Main Window for GPSIS
 * Uses a CardLayout for the Navigation between Modules
 * @author Vijendra Patel
 */
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import framework.GPSISFramework;
import framework.GPSISModuleMain;

public class MainInterface extends GPSISFramework implements ActionListener {
	
	private static CardLayout cl = new CardLayout();
	private static JPanel view = new JPanel(cl);
	private static JLabel headerTitle;
	
	/** actionPerformed
	 *  Switch through the Card Layout given by the Action Command
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		cl.show(view, ae.getActionCommand());
		headerTitle.setText(ae.getActionCommand());
	}
	
	private JPanel buildFooter()
	{
		// set Footer
		JPanel f = new JPanel(new MigLayout(
								new LC(),
								new AC().grow(),
								new AC().shrink()
							));
			JLabel fTitle = new JLabel("Logged in as " + currentUser.getUsername());
				fTitle.setFont(fonts.get("Ubuntu").deriveFont(12f));
			f.add(fTitle, new CC().dockWest());
		
			JLabel fCredits = new JLabel("Created by: VJ, Salman, Milka, Oshan, Matt and Seun");
				fCredits.setFont(fonts.get("Ubuntu").deriveFont(10f));
			f.add(fCredits, new CC().dockEast());
		
		f.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		return f;
	}
	
	private JPanel buildHeader()
	{
		// set Header
		JPanel h = new JPanel(new MigLayout(
								new LC(),
								new AC().grow(),
								new AC().shrink()
							));
			h.setBackground(new Color(51, 51, 51));
			h.setForeground(new Color(235, 235, 235));
			
			JLabel hLogo = new JLabel(GPSISModuleMain.getGPSISLogo());
				hLogo.setForeground(new Color(235, 235, 235));
			h.add(hLogo, new CC().span(1));
			
			
			
			headerTitle = new JLabel("GP-SIS");
				headerTitle.setFont(fonts.get("Ubuntu").deriveFont(24f));
				headerTitle.setForeground(new Color(235, 235, 235));
			h.add(headerTitle, new CC().pad("5px").span().wrap());
			
			
			h.add(this.buildNavigation(), new CC().span().dockSouth().alignX("center"));
			
		return h;
	}
	
	private JPanel buildNavigation()
	{
		// Set Navigation
		JPanel n = new JPanel(new MigLayout(
								new LC().gridGap("0px", "0px").insetsAll("0px"),
								new AC().shrink(),
								new AC().shrink()
							));
		// Create JButtons and add to Navigation Panel
		JButton patientRecordsBtn = new JButton("Patient Records");
		n.add(patientRecordsBtn);
		JButton staffRecordsBtn = new JButton("Staff Records");
		n.add(staffRecordsBtn);
		JButton calendarAppointmentsBtn = new JButton("Calendar Appointments");
		n.add(calendarAppointmentsBtn);
		JButton prescriptionsBtn = new JButton("Prescriptions");
		n.add(prescriptionsBtn);
		JButton specialistReferralsBtn = new JButton("Specialist Referrals");
		n.add(specialistReferralsBtn);
		JButton careProgrammeManagementBtn = new JButton("Care Programme Management");
		n.add(careProgrammeManagementBtn);	
		
		
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

	public void createAndShowGUI()
	{
		JFrame gpsisMainFrame = new JFrame("General Practitioner's Surgery Information System");
		// Set Icon
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/image/favicon.jpg"));
		gpsisMainFrame.setIconImage((icon.getImage()));
		gpsisMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gpsisMainFrame.setLayout(new MigLayout(
									new LC(),
									new AC().grow(),
									new AC().grow()
								));
		gpsisMainFrame.setBackground(new Color(240, 240, 240));
				
		// add the Header to the Window
		gpsisMainFrame.add(this.buildHeader(), new CC().growX().dockNorth());
				
			
		gpsisMainFrame.add(view, new CC().grow());
				
		// Add the footer to the Window	
		gpsisMainFrame.add(this.buildFooter(), new CC().growX().dockSouth());
		
		gpsisMainFrame.pack();
		
		// Set the initial window size equal to 60% of the active display resolution
		GraphicsDevice screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		//double w = screenSize.getDisplayMode().getWidth() * 0.3;
		double h = screenSize.getDisplayMode().getHeight() * 0.6;
		gpsisMainFrame.setMinimumSize(new Dimension(gpsisMainFrame.getWidth(), (int)Math.round(h)));
		gpsisMainFrame.setSize(gpsisMainFrame.getWidth(), (int)Math.round(h));
		//gpsisMainFrame.setSize((int)Math.round(w), (int)Math.round(h)); // initial height size equal to 60% of active display size
		gpsisMainFrame.setLocationRelativeTo(null); // center on screen
		gpsisMainFrame.setVisible(true);
		
	}
}
