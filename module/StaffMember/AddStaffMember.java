/** TODO AddStaffMember
 * Opens a New Window displaying a Form to Add a New Staff Member
 * @author VJ
 */
package module.StaffMember;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import object.MedicalStaffMember;
import object.Receptionist;
import object.StaffMember;
import framework.GPSISPopup;

public class AddStaffMember extends GPSISPopup implements ActionListener {

	private static final long serialVersionUID = -8748112836660009010L;
	
	private JTextField usernameFld;
	private JPasswordField passwordFld;
	private JTextField firstNameFld;
	private JTextField lastNameFld;
	private JCheckBox isFullTimeFld;
	private JDatePicker startDateFld;
	private JCheckBox isOfficeManagerFld;
	private JComboBox<String> roleFld;
	private String[] roles = {"Receptionist", "Nurse", "Doctor"};
	private JSpinner holidayAllowanceFld;

	public AddStaffMember() {
		super("Add Staff Member"); // Set the JFrame Title
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.setBackground(new Color(240, 240, 240));
		this.setSize(400, 450);
		
		JPanel h = new JPanel(new GridBagLayout());	
			JLabel hTitle = new JLabel("Add Staff Member");
				hTitle.setFont(new Font("Serif", Font.BOLD, 24));
				GridBagConstraints gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
			h.add(hTitle, gbC);
			
			gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.PAGE_START;
			gbC.gridx = 0;
			gbC.gridy = 0;
			gbC.weightx = 1;
			gbC.weighty = 1;
			gbC.gridwidth = 3;
			gbC.ipady = 0;
		this.add(h, gbC);
		
		JPanel addStaffMemberForm = new JPanel(new GridBagLayout());
			// Username Label
			JLabel usernameLbl = new JLabel("Username: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 0;
			addStaffMemberForm.add(usernameLbl, gbC);
			// Username Component
			this.usernameFld = new JTextField(20);
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 0;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.usernameFld, gbC);
			
			// Password Label
			JLabel passwordLbl = new JLabel("Password: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 1;
			addStaffMemberForm.add(passwordLbl, gbC);
			// Password Component
			this.passwordFld = new JPasswordField(20);
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 1;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.passwordFld, gbC);
			
			// First Name Label
			JLabel firstNameLbl = new JLabel("First Name: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 2;
			addStaffMemberForm.add(firstNameLbl, gbC);
			// First Name Component
			this.firstNameFld = new JTextField(20);
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 2;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.firstNameFld, gbC);
			
			// Last Name Label
			JLabel lastNameLbl = new JLabel("Last Name: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 3;
			addStaffMemberForm.add(lastNameLbl, gbC);
			// Last Name Component
			this.lastNameFld = new JTextField(20);
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 3;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.lastNameFld, gbC);
			
			// Is Full Time Label
			JLabel isFullTimeLbl = new JLabel("Full Time");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 4;
			addStaffMemberForm.add(isFullTimeLbl, gbC);
			// Is Full Time Component
			this.isFullTimeFld = new JCheckBox();
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 4;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.isFullTimeFld, gbC);
			
			// Start Date Label
			JLabel startDateLbl = new JLabel("Start Date: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 5;
			addStaffMemberForm.add(startDateLbl, gbC);
			// Start Date Component
			this.startDateFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 5;
				gbC.gridwidth = 2;
			addStaffMemberForm.add((Component) this.startDateFld, gbC);
			
			// Is Office Manager Label
			JLabel isOfficeManagerLbl = new JLabel("Office Manager: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 6;
			addStaffMemberForm.add(isOfficeManagerLbl, gbC);
			// Is Office Manager Component
			this.isOfficeManagerFld = new JCheckBox();
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 6;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.isOfficeManagerFld, gbC);
			
			// Role Label
			JLabel roleLbl = new JLabel("Role: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 7;
			addStaffMemberForm.add(roleLbl, gbC);
			
			// Role Component
			this.roleFld = new JComboBox<String>(this.roles);
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 7;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.roleFld, gbC);
			
			// Holiday Allowance Label
			JLabel holidayAllowanceLbl = new JLabel("Holiday Allowance: ");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_START;
				gbC.fill = GridBagConstraints.NONE;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 8;
			addStaffMemberForm.add(holidayAllowanceLbl, gbC);
			
			// Holiday Allowance Component
			this.holidayAllowanceFld = new JSpinner(new SpinnerNumberModel(20, 5, 100, 1));
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 1;
				gbC.gridy = 8;
				gbC.gridwidth = 2;
			addStaffMemberForm.add(this.holidayAllowanceFld, gbC);
			
			// Add Button
			JButton addBtn = new JButton("Add!");
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LAST_LINE_END;
				gbC.weightx = 1;
				gbC.weighty = 1;
				gbC.gridx = 2;
				gbC.gridy = 9;
				addBtn.addActionListener(this);
			addStaffMemberForm.add(addBtn, gbC);
		
			gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			gbC.gridx = 0;
			gbC.weightx = 1;
			gbC.weighty = 1;
			gbC.gridwidth = 3;
		this.add(addStaffMemberForm, gbC);
		
		this.setLocationRelativeTo(null); // center window
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO AddStaffMember:actionPerformed
		// GET ALL THE VALUES
		String username = this.usernameFld.getText().trim();
		String password = new String (this.passwordFld.getPassword());
		String firstName = this.firstNameFld.getText().trim();
		String lastName = this.lastNameFld.getText().trim();
		boolean isFullTime = this.isFullTimeFld.isSelected();
		Date startDate = (Date) this.startDateFld.getModel().getValue();
		boolean isOfficeManager = this.isOfficeManagerFld.isSelected();
		String role = (String) this.roleFld.getSelectedItem();
		int holidayAllowance = (int)this.holidayAllowanceFld.getValue();
		
		if (role.equals("Receptionist"))
		{
			StaffMember o = new Receptionist(username, password, firstName, lastName, isFullTime, startDate, isOfficeManager, holidayAllowance);
			o.printDetails();
		}
		else
		{
			StaffMember o = new MedicalStaffMember(username, password, firstName, lastName, isFullTime, startDate, isOfficeManager, role, holidayAllowance);
			o.printDetails();
		}
		
		System.out.println("Created Staff Member! :D");
		
		dispose();
		
		
	}
	
	
}
