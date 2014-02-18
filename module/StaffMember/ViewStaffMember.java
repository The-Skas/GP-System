/** TODO ViewStaffMember
 * Displays a Window with the given Staff Member's properties ready for editing
 * @author VJ
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
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.StaffMember;
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
		
		lP.setBackground(Color.PINK);
		
		lP.add(buildProfilePictureSegment(), new CC().span().wrap().dockNorth());
		lP.add(buildStatsSegment(), new CC().span().dockSouth());
		
		this.add(lP, new CC().dockWest().spanX());
		this.add(buildInfoSegment(), new CC().span().grow());
		
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}
	
	
	// build Info Segment
	public JPanel buildInfoSegment()
	{
		JPanel i = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		this.usernameFld = new JLabel(this.selectedStaffMember.getUsername());
		this.usernameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.usernameFld, new CC().span().wrap());
		
		this.firstNameFld = new JTextField(this.selectedStaffMember.getFirstName());
		this.firstNameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.firstNameFld, new CC().span().wrap());
		
		this.lastNameFld = new JTextField(this.selectedStaffMember.getLastName());
		this.lastNameFld.setPreferredSize(new Dimension(200, 24));
		i.add(this.lastNameFld, new CC().span().wrap());
		
		this.isFullTimeFld = new JCheckBox();
		this.isFullTimeFld.setSelected(this.selectedStaffMember.isFullTime());
		i.add(this.isFullTimeFld, new CC().span().wrap());
		
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String sD = fm.format(this.selectedStaffMember.getStartDate().getTime());
		this.startDateFld = new JLabel(sD);
		i.add(this.startDateFld, new CC().span().wrap());
		
		this.isOfficeManagerFld = new JCheckBox();
		this.isOfficeManagerFld.setSelected(this.selectedStaffMember.isOfficeManager());
		i.add(this.isFullTimeFld, new CC().span().wrap());
		
		this.roleFld = new JComboBox<String>(this.roles);
		this.roleFld.setSelectedIndex(2);
		i.add(this.roleFld, new CC().span().wrap());
		
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

}
