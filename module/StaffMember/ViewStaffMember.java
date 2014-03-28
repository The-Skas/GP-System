/** ViewStaffMember
 * Displays a Window with the given Staff Member's properties ready for editing
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import mapper.PatientDMO;
import mapper.PrescriptionDMO;
import mapper.StaffMemberDMO;
import module.StaffMemberModule;
import module.StaffMember.AbsenceManagement.ViewAbsences;
import module.StaffMember.AppointmentManagement.ViewAppointments;
import module.StaffMember.AvailabilityManagement.ViewAvailability;
import module.StaffMember.PatientManagement.ViewPatients;
import module.StaffMember.PrescriptionManagement.ViewPrescriptions;
import module.StaffMember.SpecialistReferralManagement.ViewSpecialistReferrals;
import module.StaffMember.SpecialityManagement.ViewSpecialities;
import module.StaffMember.TaxFormManagement.ViewTaxForms;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

public class ViewStaffMember extends GPSISPopup implements ActionListener {

	private static final long	serialVersionUID	= 1L;

	// Set Fields
	private JLabel				usernameFld;
	private JTextField			firstNameFld;
	private JTextField			lastNameFld;
	private JCheckBox			isFullTimeFld;
	private JLabel				startDateFld;
	private JCheckBox			isOfficeManagerFld;
	private JComboBox<String>	roleFld;
	private String[]			roles				= { "Receptionist", "Nurse", "Doctor" };
	private JSpinner			holidayAllowanceFld;

	private StaffMember			selectedStaffMember;

	/**
	 * ViewStaffMember Constructor Builds and shows a Window with details of a
	 * given Staff Member
	 * 
	 * @param sM
	 */
	public ViewStaffMember(StaffMember sM) {
		super(sM.getName() + "'s Profile");
		this.selectedStaffMember = sM;

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		this.setBackground(new Color(240, 240, 240));

		// left panel
		JPanel lP = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		lP.add(this.buildStatsSegment(), new CC().span().dockSouth());

		this.add(lP, new CC().dockWest().spanX());
		this.add(this.buildInfoSegment(), new CC().span().grow());

		// set permissions
		if (!GPSISPopup.currentUser.isOfficeManager()
				&& GPSISPopup.currentUser.getId() != this.selectedStaffMember.getId()) {
			this.firstNameFld.setEditable(false);
			this.lastNameFld.setEditable(false);
			this.isFullTimeFld.setEnabled(false);
			this.isOfficeManagerFld.setEnabled(false);
			this.roleFld.setEnabled(false);
			this.holidayAllowanceFld.setEnabled(false);
		}

		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
			case "Save":
				this.save();
				break;
			case "Change Password":
				new ChangeStaffMemberPassword(this.selectedStaffMember);
				break;
			case "Remove":
				this.remove();
				break;
			case "View Availabilities":
				new ViewAvailability(this.selectedStaffMember);
				break;
			case "View Specialities":
				new ViewSpecialities(this.selectedStaffMember);
				break;
			case "View Tax Forms":
				new ViewTaxForms(this.selectedStaffMember);
				break;
			case "View Absences":
				new ViewAbsences(this.selectedStaffMember);
				break;
			case "View Patients":
				new ViewPatients(this.selectedStaffMember);
				break;
			case "View Appointments":
				new ViewAppointments(this.selectedStaffMember);
				break;
			case "View Prescriptions":
				new ViewPrescriptions(this.selectedStaffMember);
				break;
			case "View Referrals":
				new ViewSpecialistReferrals(this.selectedStaffMember);
				break;
		}
	}

	/**
	 * buildInfoSegment builds the Info section of the View Staff Member Frame
	 * 
	 * @return a JPanel representing the Info of the Staff Member
	 */
	private JPanel buildInfoSegment() {
		JPanel i = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		// Username
		i.add(new JLabel("Username"), new CC());
		this.usernameFld = new JLabel(this.selectedStaffMember.getUsername());
		this.usernameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.usernameFld, new CC().wrap());

		// First Name Field
		i.add(new JLabel("First Name"), new CC());
		this.firstNameFld = new JTextField(this.selectedStaffMember.getFirstName());
		this.firstNameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.firstNameFld, new CC().wrap());

		// Last Name Field
		i.add(new JLabel("Last Name"), new CC());
		this.lastNameFld = new JTextField(this.selectedStaffMember.getLastName());
		this.lastNameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.lastNameFld, new CC().wrap());

		// Full Time Field
		i.add(new JLabel("Full Time"), new CC());
		this.isFullTimeFld = new JCheckBox();
		this.isFullTimeFld.setSelected(this.selectedStaffMember.isFullTime());
		i.add(this.isFullTimeFld, new CC().wrap());

		// Start Date Field
		i.add(new JLabel("Start Date"), new CC());
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String sD = fm.format(this.selectedStaffMember.getStartDate().getTime());
		this.startDateFld = new JLabel(sD);
		i.add(this.startDateFld, new CC().wrap());

		// End Date Field - ONLY ON TEMPORARY Staff Members
		if (this.selectedStaffMember.isTemporary()) {
			i.add(new JLabel("End Date"));
			String eD = fm.format(this.selectedStaffMember.getEndDate().getTime());
			JLabel endDateFld = new JLabel(eD);
			i.add(endDateFld, new CC().wrap());
		}

		// Office Manager Field
		i.add(new JLabel("Office Manager"), new CC());
		this.isOfficeManagerFld = new JCheckBox();
		this.isOfficeManagerFld.setSelected(this.selectedStaffMember.isOfficeManager());
		i.add(this.isOfficeManagerFld, new CC().wrap());

		// Role Field
		i.add(new JLabel("Role"), new CC());
		this.roleFld = new JComboBox<String>(this.roles);
		this.roleFld.setSelectedIndex(2);
		i.add(this.roleFld, new CC().wrap());

		// Holiday Allowance Field
		i.add(new JLabel("Holiday Allowance"), new CC());
		this.holidayAllowanceFld =
				new JSpinner(new SpinnerNumberModel(this.selectedStaffMember.getHolidayAllowance(), 5, 400, 1));
		i.add(this.holidayAllowanceFld, new CC().wrap());

		if (GPSISPopup.currentUser.isOfficeManager()
				|| GPSISPopup.currentUser.getId() == this.selectedStaffMember.getId()) {
			JButton changePwdBtn = new JButton("Change Password");
			changePwdBtn.addActionListener(this);
			changePwdBtn.setActionCommand("Change Password");
			i.add(changePwdBtn, new CC().push());

			JButton saveBtn = new JButton("Save!");
			saveBtn.addActionListener(this);
			saveBtn.setActionCommand("Save");
			i.add(saveBtn, new CC().push());

			JButton removeBtn = new JButton("Remove!");
			removeBtn.addActionListener(this);
			removeBtn.setActionCommand("Remove");
			i.add(removeBtn, new CC().push());
		}
		return i;
	}

	/** buildStatsSegment 
	 * builds the Stats segment of the View Staff Member Frame
	 * 
	 * @return a JPanel containing the Stats segment of the Profile View
	 */
	private JPanel buildStatsSegment() {
		JPanel s = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		// Appointments Stats (only if Doctor or Nurse)
		if (this.selectedStaffMember.isNurse() || this.selectedStaffMember.isDoctor()) {
			JButton appointmentsBtn = new JButton("Appointments");
			s.add(appointmentsBtn, new CC());
			JLabel appointmentsVal = new JLabel("0");
			try {
				appointmentsVal =
						new JLabel("" + StaffMemberDMO.getInstance().getAppointments(this.selectedStaffMember).size());
				appointmentsBtn.addActionListener(this);
				appointmentsBtn.setActionCommand("View Appointments");
			} catch (EmptyResultSetException e) {
				System.out.println("Stats:- No Appointments");
				appointmentsBtn.setEnabled(false);
			}
			s.add(appointmentsVal, new CC().wrap());

			// Specialities Stats
			JButton specialitiesBtn = new JButton("Specialities");
			specialitiesBtn.addActionListener(this);
			specialitiesBtn.setActionCommand("View Specialities");
			s.add(specialitiesBtn);
			JLabel specialitiesVal = new JLabel("0");
			try {
				specialitiesVal =
						new JLabel("" + StaffMemberDMO.getInstance().getSpecialities(this.selectedStaffMember).size());
			} catch (EmptyResultSetException e) {
				System.out.println("Stats:- No Specialities");
				specialitiesBtn.setEnabled(false);
			}
			s.add(specialitiesVal, new CC().wrap());

			// Patients Stats
			JButton patientsBtn = new JButton("Patients");
			s.add(patientsBtn);
			JLabel patientsVal = new JLabel("0");
			try {
				patientsVal =
						new JLabel(""
								+ PatientDMO.getInstance()
										.getAllPermanentPatientsByDoctorId(this.selectedStaffMember.getId()).size());
				patientsBtn.addActionListener(this);
				patientsBtn.setActionCommand("View Patients");
			} catch (EmptyResultSetException e) {
				System.out.println("Stats:- No Permanent Patients");
				patientsBtn.setEnabled(false);
			}
			s.add(patientsVal, new CC().wrap());

			// Prescriptions Stats			
			JButton prescriptionsBtn = new JButton("Prescriptions");
			s.add(prescriptionsBtn); 
			int prescriptionsAmount = PrescriptionDMO.getInstance().getPrescriptionsByDoctor(selectedStaffMember).size();
			JLabel prescriptionsVal = new JLabel(""+prescriptionsAmount);
			prescriptionsBtn.addActionListener(this);
			prescriptionsBtn.setActionCommand("View Prescriptions"); 
			s.add(prescriptionsVal, new CC().wrap());
			if (prescriptionsAmount == 0) {
				System.out.println("Stats:- No Prescriptions");
				prescriptionsBtn.setEnabled(false); 
			}
			
			// Prescriptions Stats			
			JButton referralsBtn = new JButton("Referrals");
			s.add(referralsBtn); 
			int referralsAmount;
			referralsAmount = StaffMemberDMO.getInstance().getReferrals(selectedStaffMember).size();
			JLabel referralsVal = new JLabel(""+referralsAmount);
			referralsBtn.addActionListener(this);
			referralsBtn.setActionCommand("View Referrals"); 
			s.add(referralsVal, new CC().wrap());
			if (prescriptionsAmount == 0) {
				System.out.println("Stats:- No Referrals");
				prescriptionsBtn.setEnabled(false); 
			}
			
		}

		// Absences Stats
		JButton absencesBtn = new JButton("Absences");
		s.add(absencesBtn, new CC());
		JLabel absencesVal = new JLabel("0");
		try {
			absencesVal = new JLabel("" + StaffMemberDMO.getInstance().getAbsences(this.selectedStaffMember).size());
			absencesBtn.addActionListener(this);
			absencesBtn.setActionCommand("View Absences");
		} catch (EmptyResultSetException e) {
			System.out.println("Stats:- No Absences");
			absencesBtn.setEnabled(false);
		}
		s.add(absencesVal, new CC().wrap());

		// TrainingDay Stats
		JButton holidaysBtn = new JButton("Availabilities");
		holidaysBtn.addActionListener(this);
		holidaysBtn.setActionCommand("View Availabilities");
		s.add(holidaysBtn, new CC());
		JLabel holidaysVal = new JLabel("0/" + this.selectedStaffMember.getHolidayAllowance());
		try {
			holidaysVal =
					new JLabel(StaffMemberDMO.getInstance().getHolidays(this.selectedStaffMember).size() + "/"
							+ this.selectedStaffMember.getHolidayAllowance());
		} catch (EmptyResultSetException e) {
			System.out.println("Stats:- No Availabilities");
		}
		s.add(holidaysVal, new CC().wrap());

		// Tax Form Stats
		JButton taxFormsBtn = new JButton("Tax Forms");
		taxFormsBtn.addActionListener(this);
		taxFormsBtn.setActionCommand("View Tax Forms");
		s.add(taxFormsBtn);
		JLabel taxFormsVal = new JLabel("0");
		try {
			taxFormsVal = new JLabel("" + StaffMemberDMO.getInstance().getTaxForms(this.selectedStaffMember).size());
		} catch (EmptyResultSetException e) {
			System.out.println("Stats:- No Tax Forms");
		}
		s.add(taxFormsVal, new CC().wrap());

		// s.setBackground(Color.green);
		return s;
	}

	/** remove 
	 * Removes a the Staff Member Object from the Database
	 */
	public void remove() {
		if (JOptionPane.showConfirmDialog(this,
				"Are you sure that you wish to remove " + this.selectedStaffMember.getUsername() + "?",
				"Are you sure?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			StaffMemberDMO.getInstance().removeById(this.selectedStaffMember.getId());
			((StaffMemberATM) StaffMemberModule.getStaffMemberTable().getModel()).removeRow(this.selectedStaffMember);
			this.dispose();
		}

	}

	/** save 
	 * Saves the values to the Staff Member object then puts it to the
	 * Database check for blank values
	 */
	public void save() {
		String firstName = this.firstNameFld.getText().trim();
		String lastName = this.lastNameFld.getText().trim();
		String role = (String) this.roleFld.getSelectedItem();
		Boolean isOfficeManager = this.isOfficeManagerFld.isSelected();
		int holidayAllowance = (int) this.holidayAllowanceFld.getValue();

		if (!firstName.isEmpty()) {
			this.selectedStaffMember.setFirstName(firstName);
		}

		if (!lastName.isEmpty()) {
			this.selectedStaffMember.setLastName(lastName);
		}

		this.selectedStaffMember.setRole(role);
		this.selectedStaffMember.setOfficeManager(isOfficeManager);
		this.selectedStaffMember.setHolidayAllowance(holidayAllowance);

		StaffMemberDMO.getInstance().put(this.selectedStaffMember);
		JOptionPane.showMessageDialog(this, "Saved!");
		this.dispose();
	}

}

/**
 * End of File: ViewStaffMember.java 
 * Location: module/StaffMember
 */
