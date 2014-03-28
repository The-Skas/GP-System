/** MainInterface 
 * Displays the Main Window for GPSIS Uses a CardLayout for the
 * Navigation between Modules
 * 
 * @author Vijendra Patel (vp302)
 */
package module;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import module.CalendarAppointments.AnalogClock;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import framework.GPSISFramework;
import framework.GPSISModuleMain;

public class MainInterface extends GPSISFramework implements ActionListener {

	private static CardLayout cl;
	private static JPanel view;
	private static JLabel headerTitle;
	private static JPanel navigationBtns;
	private static Thread hClockThread;

	private static MainInterface instance;

	/** getInstance
	 * @return
	 */
	public static MainInterface getInstance() {
		if (instance == null)
			instance = new MainInterface();
		return instance;
	}

	/** MainInterface Constructor 
	 * initialise the variables and set colours
	 */
	private MainInterface() {
		cl = new CardLayout();
		view = new JPanel(cl);
		headerTitle = new JLabel("GP-SIS");
		navigationBtns = new JPanel(new MigLayout(new LC()
				.gridGap("0px", "0px").insetsAll("0px"), new AC().shrink(),
				new AC().shrink()));
		navigationBtns.setBackground(new Color(51, 51, 51));
		navigationBtns.setForeground(new Color(235, 235, 235));

	}

	/*** actionPerformed 
	 * Switch through the Card Layout given by the Action
	 * Command
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		cl.show(view, ae.getActionCommand());
		headerTitle.setText(ae.getActionCommand());
	}

	public void loadModule(String m) {
		JButton btn = new JButton(m);
		btn.addActionListener(this);
		btn.setActionCommand(m);
		navigationBtns.add(btn);

		GPSISModuleMain mM = null;
		switch (m) {
			case "Patient Records":
				mM = new PatientModule();
				break;
			case "Staff Records":
				mM = new StaffMemberModule();
				break;
			case "Calendar Appointments":
				mM = new CalendarAppointmentsModule();
				break;
			case "Prescriptions":
				mM = new PrescriptionsModule();
				break;
			case "Specialist Referrals":
				mM = new SpecialistReferralsModule();
				break;
			case "Care Programme Management":
				mM = new CareProgrammeManagementModule();
				break;
			case "Welcome":
				mM = new WelcomeModule();
				break;
		}
		if (mM != null) {
			view.add(mM.getModuleView(), m);
		}

	}

	/** buildFooter
	 * @return
	 */
	private JPanel buildFooter() {
		// set Footer
		JPanel f = new JPanel(new MigLayout(new LC(), new AC().grow(),
				new AC().shrink()));
		JLabel fTitle = new JLabel("Logged in as " + currentUser.getUsername()
				+ " | " + currentUser.getRole());
		if (currentUser.isOfficeManager())
			fTitle.setText(fTitle.getText() + " | Office Manager.");
		fTitle.setFont(fonts.get("OpenSans").deriveFont(12f));
		f.add(fTitle, new CC().dockWest());

		JLabel fCredits = new JLabel(
				"Created by: VJ, Salman, Milka, Oshan, Matt and Seun");
		fCredits.setFont(fonts.get("OpenSans").deriveFont(10f));
		f.add(fCredits, new CC().dockEast());

		f.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		return f;
	}

	/** buildHeader
	 * @return
	 */
	private JPanel buildHeader() {
		// set Header
		JPanel h = new JPanel(new MigLayout(new LC().insets("0px").gridGap("0px", "0px"), new AC().grow().gap("0px"),
				new AC().shrink().gap("0px")));
		h.setBackground(new Color(51, 51, 51));
		h.setForeground(new Color(235, 235, 235));
		
		// add Milka's Clock
		AnalogClock hClock = new AnalogClock();
		hClock.setMinimumSize(new Dimension(64, 64));
		h.add(hClock, new CC().span(1));
		hClockThread = new Thread(hClock);
		hClockThread.start();
		
		JPanel hRight = new JPanel(new MigLayout(new LC().insets("0px").gridGap("0px", "0px"), new AC().grow().gap("0px"),
				new AC().shrink().gap("0px")));
		hRight.setBackground(new Color(51,51,51));
		headerTitle = new JLabel("GP-SIS");
		headerTitle.setFont(fonts.get("OpenSans").deriveFont(24f));
		headerTitle.setBackground(new Color(51, 51, 51));
		headerTitle.setForeground(new Color(235, 235, 235));
		hRight.add(headerTitle, new CC().alignX("center").wrap());
		
		hRight.add(navigationBtns, new CC().alignX("center").span());
		
		h.add(hRight, new CC().span());
		return h;
	}
	
	/** createAndShowGUI
	 * 
	 */
	public void createAndShowGUI() {

		JFrame gpsisMainFrame = new JFrame(APPTITLE);
		// Set Icon
		gpsisMainFrame.setIconImage(getGPSISLogo().getImage());
		gpsisMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gpsisMainFrame.setLayout(new MigLayout(new LC(), new AC().grow(),
				new AC().grow()));
		gpsisMainFrame.setBackground(new Color(240, 240, 240));

		// add the Header to the Window
		gpsisMainFrame.add(buildHeader(), new CC().growX().dockNorth());

		gpsisMainFrame.add(view, new CC().grow());

		// Add the footer to the Window
		gpsisMainFrame.add(buildFooter(), new CC().growX().dockSouth());

		gpsisMainFrame.pack();

		// Set the initial window size equal to 60% of the active display
		// resolution
		GraphicsDevice screenSize = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		double h = screenSize.getDisplayMode().getHeight() * 0.6;
		double w = screenSize.getDisplayMode().getWidth() * 0.6;
		gpsisMainFrame.setMinimumSize(new Dimension(gpsisMainFrame.getWidth(),
				(int) Math.round(h)));
		gpsisMainFrame.setPreferredSize(new Dimension((int) Math.round(w),
				(int) Math.round(h)));
		gpsisMainFrame.setSize(new Dimension((int) Math.round(w), (int) Math
				.round(h)));
		gpsisMainFrame.setLocationRelativeTo(null); // center on screen
		gpsisMainFrame.setVisible(true);

	}
}
