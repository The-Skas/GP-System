/** TODO ViewStaffMember
 * Displays a Window with the given Staff Member's properties ready for editing
 * @author Vijendra Patel
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

import mapper.StaffMemberDMO;
import module.StaffMemberModule;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

public class ViewStaffMember extends GPSISPopup implements ActionListener {

	private static final long serialVersionUID = -2034755798745110525L;
	
	// Set Fields
	private JLabel usernameFld;
	//private JPasswordField passwordFld;
	private JTextField firstNameFld;
	private JTextField lastNameFld;
	private JCheckBox isFullTimeFld;
	private JLabel startDateFld;
	private JCheckBox isOfficeManagerFld;
	private JComboBox<String> roleFld;
	private String[] roles = {"Receptionist", "Nurse", "Doctor"};
	private JSpinner holidayAllowanceFld;
	
	private StaffMember selectedStaffMember;

	public ViewStaffMember(StaffMember sM) {
		super("Modify Staff Member");
		this.selectedStaffMember = sM;
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout(
				new LC().fill(),
				new AC().grow(),
				new AC().grow()
			));
		this.setBackground(new Color(240, 240, 240));
		//this.setSize(600, 450);
		
		// left panel
		JPanel lP = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		
		lP.add(buildProfilePictureSegment(), new CC().span().wrap().dockNorth());
		lP.add(buildStatsSegment(), new CC().span().dockSouth());
		
		this.add(lP, new CC().dockWest().spanX());
		this.add(buildInfoSegment(), new CC().span().grow());
		
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}
	
	
	// build Info Segment TODO : Permissions
	public JPanel buildInfoSegment()
	{
		JPanel i = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		// Username
		i.add(new JLabel("Username"), new CC());
		this.usernameFld = new JLabel(this.selectedStaffMember.getUsername());
		this.usernameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.usernameFld, new CC().span().wrap());
		
		// First Name Field
		i.add(new JLabel("First Name"), new CC());
		this.firstNameFld = new JTextField(this.selectedStaffMember.getFirstName());
		this.firstNameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.firstNameFld, new CC().span().wrap());
		
		// Last Name Field
		i.add(new JLabel("Last Name"), new CC());
		this.lastNameFld = new JTextField(this.selectedStaffMember.getLastName());
		this.lastNameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.lastNameFld, new CC().span().wrap());
		
		// Full Time Field
		i.add(new JLabel("Full Time"), new CC());
		this.isFullTimeFld = new JCheckBox();
		this.isFullTimeFld.setSelected(this.selectedStaffMember.isFullTime());
		i.add(this.isFullTimeFld, new CC().span().wrap());
		
		// Start Date Field
		i.add(new JLabel("Start Date"), new CC());
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String sD = fm.format(this.selectedStaffMember.getStartDate().getTime());
		this.startDateFld = new JLabel(sD);
		i.add(this.startDateFld, new CC().span().wrap());
		
		// Office Manager Field
		i.add(new JLabel("Office Manager"), new CC());
		this.isOfficeManagerFld = new JCheckBox();
		this.isOfficeManagerFld.setSelected(this.selectedStaffMember.isOfficeManager());
		i.add(this.isOfficeManagerFld, new CC().span().wrap());
		
		// Role Field
		i.add(new JLabel("Role"), new CC());
		this.roleFld = new JComboBox<String>(this.roles);
		this.roleFld.setSelectedIndex(2);
		i.add(this.roleFld, new CC().span().wrap());
		
		// Holiday Allowance Field
		i.add(new JLabel("Holiday Allowance"), new CC());
		this.holidayAllowanceFld = new JSpinner(new SpinnerNumberModel(this.selectedStaffMember.getHolidayAllowance(), 5, 400, 1));
		i.add(this.holidayAllowanceFld, new CC().span().wrap());
		
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
				
		//i.setBackground(Color.cyan);
		return i;
	}
	
	// build Picture Segment
	public JPanel buildProfilePictureSegment()
	{
		JPanel p = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			//JLabel picture = new JLabel(new ImageIcon());
			JLabel picture = new JLabel ("Placeholder for Picture");
			p.add(picture);
		//p.setBackground(Color.red);
		return p;
	}
	
	// build Stats Segment
	public JPanel buildStatsSegment()
	{
		JPanel s = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		// Appointments Stats (only if Doctor or Nurse)
		if (selectedStaffMember.isNurse() || selectedStaffMember.isDoctor())
		{
			JButton appointmentsBtn = new JButton("Appointments");
			s.add(appointmentsBtn, new CC());
			JLabel appointmentsVal = new JLabel("0");
			try {
				appointmentsVal = new JLabel(""+StaffMemberDMO.getInstance().getAppointments(selectedStaffMember).size());
			} catch (EmptyResultSetException e) {
				System.out.println("Stats:- No Appointments");
			}
			s.add(appointmentsVal, new CC().wrap());
			
		}
		
		// Absences Stats
		JButton absencesBtn = new JButton("Absences");
		s.add(absencesBtn, new CC());
		JLabel absencesVal = new JLabel("0");
		try {
			absencesVal = new JLabel(""+StaffMemberDMO.getInstance().getAbsences(selectedStaffMember).size());
		} catch (EmptyResultSetException e) {
			System.out.println("Stats:- No Absences");
		}
		s.add(absencesVal, new CC().wrap());
		
		// Holidays Stats
		JButton holidaysBtn = new JButton("Holidays");
		s.add(holidaysBtn, new CC());
		JLabel holidaysVal = new JLabel("0/" + selectedStaffMember.getHolidayAllowance());
		try {
			holidaysVal = new JLabel(StaffMemberDMO.getInstance().getHolidays(selectedStaffMember).size() + "/" + selectedStaffMember.getHolidayAllowance());
		} catch (EmptyResultSetException e) {
			System.out.println("Stats:- No Holidays");
		}
		s.add(holidaysVal, new CC().wrap());
		
		//s.setBackground(Color.green);
		return s;
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Save":
				this.save();
				break;
			case "Change Password":
				new ChangePassword();
				break;
			case "Remove":
				this.remove();
				break;
		}
	}
	
	/** save
	 * Saves the values to the Staff Member object then puts it to the Database
	 * check for blank values
	 */
	public void save()
	{
		String firstName = this.firstNameFld.getText().trim();
		String lastName = this.lastNameFld.getText().trim();
		String role = (String) this.roleFld.getSelectedItem();
		Boolean isOfficeManager = this.isOfficeManagerFld.isSelected();		
		int holidayAllowance = (int)this.holidayAllowanceFld.getValue();
		
		
		if (!firstName.isEmpty())
			selectedStaffMember.setFirstName(firstName);
		
		if (!lastName.isEmpty())
			selectedStaffMember.setLastName(lastName);
		
		selectedStaffMember.setRole(role);
		selectedStaffMember.setOfficeManager(isOfficeManager);
		selectedStaffMember.setHolidayAllowance(holidayAllowance);
		
		StaffMemberDMO.getInstance().put(selectedStaffMember);
		JOptionPane.showMessageDialog(this, "Saved!");
		dispose();
	}
	
	/** remove
	 * Removes a the Staff Member Object from the Database
	 */
	public void remove()
	{
		if (JOptionPane.showConfirmDialog(this, 
				"Are you sure that you wish to remove " + selectedStaffMember.getUsername() + "?", 
				"Are you sure?", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			StaffMemberDMO.getInstance().removeById(selectedStaffMember.getId());
			((StaffMemberATM) StaffMemberModule.getStaffMemberTable().getModel()).removeRow(selectedStaffMember); // use the removeRow method in ATM
			dispose();
		}
		
	}

}
