/** TODO AddStaffMember
 * Opens a New Window displaying a Form to Add a New Staff Member
 * @author VJ
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
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		//this.setSize(400, 450);
		
		JPanel h = new JPanel(new MigLayout());	
			JLabel hTitle = new JLabel("Add Staff Member");
				GPSISFramework.getInstance();
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
			
			// Add Button
			JButton addBtn = new JButton("Add!");
				addBtn.addActionListener(this);
			addStaffMemberForm.add(addBtn);
		
		this.add(addStaffMemberForm, new CC());
		
		this.setLocationRelativeTo(null); // center window
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
		
		// check if fields are not blank
		if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Some fields are blank! Staff Member not created!", "Blank Input", JOptionPane.WARNING_MESSAGE);
		}
		else 
		{
		
			try
			{
				if (role.equals("Receptionist"))
				{
						StaffMember sM = new Receptionist(username, password, firstName, lastName, isFullTime, startDate, isOfficeManager, holidayAllowance);
						((StaffMemberATM) StaffMemberModule.getStaffMemberTable().getModel()).addRow(sM);
				}
				else
				{
					StaffMember sM = new MedicalStaffMember(username, password, firstName, lastName, isFullTime, startDate, isOfficeManager, role, holidayAllowance);
					((StaffMemberATM) StaffMemberModule.getStaffMemberTable().getModel()).addRow(sM);
				}
				JOptionPane.showMessageDialog(this, "Created a New Staff Member :D");
				
				dispose();
			}
			catch (DuplicateEntryException e)
			{
				JOptionPane.showMessageDialog(this, "Username is already taken!", "Username Taken!", JOptionPane.WARNING_MESSAGE);
			}
		}	
		
	}
	
	
}
