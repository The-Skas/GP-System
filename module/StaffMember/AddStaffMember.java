/** AddStaffMember 
 * Displays a Window with a form to Add a new Staff Member
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mapper.StaffMemberDMO;
import module.StaffMemberModule;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import object.MedicalStaffMember;
import object.Receptionist;
import object.StaffMember;
import exception.DuplicateEntryException;
import framework.GPSISFramework;
import framework.GPSISPopup;

public class AddStaffMember extends GPSISPopup implements ActionListener, ChangeListener {

	private static final long	serialVersionUID	= 1L;
	private JTextField			usernameFld;
	private JPasswordField		passwordFld;
	private JTextField			firstNameFld;
	private JTextField			lastNameFld;
	private JCheckBox			isFullTimeFld;
	private JDatePicker			startDateFld;
	private JCheckBox			isOfficeManagerFld;
	private JComboBox<String>	roleFld;
	private String[]			roles				= { "Receptionist", "Nurse", "Doctor" };
	private JSpinner			holidayAllowanceFld;
	private JCheckBox			isMakeTempFld;
	private JLabel				endDateLbl;
	private JDatePicker			endDateFld;

	/** AddStaffMember Constructor
	 * Builds and shows a Window with a form to add a new Staff Member
	 */
	public AddStaffMember() {
		super("Add Staff Member"); // Set the JFrame Title
		this.setModal(true);

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));

		JPanel h = new JPanel(new MigLayout());
		JLabel hTitle = new JLabel("Add Staff Member");
		hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
		h.add(hTitle, new CC().wrap());

		this.add(h, new CC().wrap());

		JPanel addStaffMemberForm = new JPanel(new MigLayout());
		// Username Label
		JLabel usernameLbl = new JLabel("Username: ");
		addStaffMemberForm.add(usernameLbl);
		// Username Component
		this.usernameFld = new JTextField(20);
		addStaffMemberForm.add(this.usernameFld, new CC().wrap());

		// Password Label
		JLabel passwordLbl = new JLabel("Password: ");
		addStaffMemberForm.add(passwordLbl);
		// Password Component
		this.passwordFld = new JPasswordField(20);
		addStaffMemberForm.add(this.passwordFld, new CC().wrap());

		// First Name Label
		JLabel firstNameLbl = new JLabel("First Name: ");
		addStaffMemberForm.add(firstNameLbl);
		// First Name Component
		this.firstNameFld = new JTextField(20);
		addStaffMemberForm.add(this.firstNameFld, new CC().wrap());

		// Last Name Label
		JLabel lastNameLbl = new JLabel("Last Name: ");
		addStaffMemberForm.add(lastNameLbl);
		// Last Name Component
		this.lastNameFld = new JTextField(20);
		addStaffMemberForm.add(this.lastNameFld, new CC().wrap());

		// Is Full Time Label
		JLabel isFullTimeLbl = new JLabel("Full Time");
		addStaffMemberForm.add(isFullTimeLbl);
		// Is Full Time Component
		this.isFullTimeFld = new JCheckBox();
		addStaffMemberForm.add(this.isFullTimeFld, new CC().wrap());

		// Start Date Label
		JLabel startDateLbl = new JLabel("Start Date: ");
		addStaffMemberForm.add(startDateLbl);
		// Start Date Component
		this.startDateFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
		addStaffMemberForm.add((Component) this.startDateFld, new CC().wrap());

		// Is Office Manager Label
		JLabel isOfficeManagerLbl = new JLabel("Office Manager: ");
		addStaffMemberForm.add(isOfficeManagerLbl);
		// Is Office Manager Component
		this.isOfficeManagerFld = new JCheckBox();
		addStaffMemberForm.add(this.isOfficeManagerFld, new CC().wrap());

		// Role Label
		JLabel roleLbl = new JLabel("Role: ");
		addStaffMemberForm.add(roleLbl);

		// Role Component
		this.roleFld = new JComboBox<String>(this.roles);
		addStaffMemberForm.add(this.roleFld, new CC().wrap());

		// Holiday Allowance Label
		JLabel holidayAllowanceLbl = new JLabel("Holiday Allowance: ");
		addStaffMemberForm.add(holidayAllowanceLbl);

		// Holiday Allowance Component
		this.holidayAllowanceFld = new JSpinner(new SpinnerNumberModel(20, 5, 100, 1));
		addStaffMemberForm.add(this.holidayAllowanceFld, new CC().wrap());

		// Make Temporary Label
		JLabel makeTemporaryLbl = new JLabel("Make Temporary: ");
		addStaffMemberForm.add(makeTemporaryLbl);

		// Make Temporary Component
		this.isMakeTempFld = new JCheckBox();
		addStaffMemberForm.add(this.isMakeTempFld);
		this.isMakeTempFld.addChangeListener(this);

		// End Date Label
		this.endDateLbl = new JLabel("End Date: ");
		addStaffMemberForm.add(this.endDateLbl);
		this.endDateLbl.setVisible(false);
		this.endDateFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
		addStaffMemberForm.add((Component) this.endDateFld, new CC().wrap());
		((Component) this.endDateFld).setVisible(false);

		// Add Button
		JButton addBtn = new JButton("Add!");
		addBtn.addActionListener(this);
		addStaffMemberForm.add(addBtn);

		this.add(addStaffMemberForm, new CC());

		this.setLocationRelativeTo(null); // center window
		this.pack();
		this.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Get all of the values from the fields
		String username = this.usernameFld.getText().trim();
		String password = new String(this.passwordFld.getPassword());
		String firstName = this.firstNameFld.getText().trim();
		String lastName = this.lastNameFld.getText().trim();
		boolean isFullTime = this.isFullTimeFld.isSelected();
		Date startDate = (Date) this.startDateFld.getModel().getValue();
		boolean isOfficeManager = this.isOfficeManagerFld.isSelected();
		String role = (String) this.roleFld.getSelectedItem();
		int holidayAllowance = (int) this.holidayAllowanceFld.getValue();

		boolean isMakeTemporary = this.isMakeTempFld.isSelected();
		Date endDate = (Date) this.endDateFld.getModel().getValue();

		// check if fields are not blank
		if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Some fields are blank! Staff Member not created!", "Blank Input",
					JOptionPane.WARNING_MESSAGE);
		} else {

			try {
				StaffMember sM;
				if (role.equals("Receptionist")) {
					sM =
							new Receptionist(username, password, firstName, lastName, isFullTime, startDate,
									isOfficeManager, holidayAllowance);
					((StaffMemberATM) StaffMemberModule.getStaffMemberTable().getModel()).addRow(sM);
				} else {
					sM =
							new MedicalStaffMember(username, password, firstName, lastName, isFullTime, startDate,
									isOfficeManager, role, holidayAllowance);
					((StaffMemberATM) StaffMemberModule.getStaffMemberTable().getModel()).addRow(sM);

				}
				// if temporary
				if (isMakeTemporary) {
					sM.setTemporary(endDate);
					StaffMemberDMO.getInstance().makeTemporary(sM);					
				}

				JOptionPane.showMessageDialog(this, "Created a New Staff Member :D");

				this.dispose();
			} catch (DuplicateEntryException e) {
				JOptionPane.showMessageDialog(this, "Username is already taken!", "Username Taken!",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (this.isMakeTempFld.isSelected()) {
			((Component) this.endDateFld).setVisible(true);
			this.isFullTimeFld.setSelected(true);
			this.endDateLbl.setVisible(true);
		} else {
			((Component) this.endDateFld).setVisible(false);
			this.endDateLbl.setVisible(false);
		}
	}

}

/**
 * End of File: AddStaffMember.java 
 * Location: module/StaffMember
 */