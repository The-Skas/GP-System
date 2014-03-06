/**
 * Displays a Window with the given Staff Member's properties ready for editing
 * @author Vijendra Patel
 */
package module.StaffMember;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import mapper.PatientDMO;
import mapper.StaffMemberDMO;
import module.StaffMemberModule;
import module.StaffMember.AvailabilityManagement.ViewAvailability;
import module.StaffMember.HolidayManagement.ViewHolidays;
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
	
	private JFileChooser fC = new JFileChooser();
	private JLabel pPicture;

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
		
		// left panel
		JPanel lP = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		
		lP.add(buildProfilePictureSegment(), new CC().span().wrap().dockNorth());
		lP.add(buildStatsSegment(), new CC().span().dockSouth());
		
		this.add(lP, new CC().dockWest().spanX());
		this.add(buildInfoSegment(), new CC().span().grow());
		
		// set permissions
		if (!currentUser.isOfficeManager() && currentUser.getId() != selectedStaffMember.getId())
		{
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
	
	
	// build Info Segment TODO : Permissions
	public JPanel buildInfoSegment()
	{
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
		if(selectedStaffMember.isTemporary())
		{
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
		this.holidayAllowanceFld = new JSpinner(new SpinnerNumberModel(this.selectedStaffMember.getHolidayAllowance(), 5, 400, 1));
		i.add(this.holidayAllowanceFld, new CC().wrap());
		
		if (currentUser.isOfficeManager() || currentUser.getId() == selectedStaffMember.getId()) 
		{
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
	
	// build Picture Segment
	public JPanel buildProfilePictureSegment()
	{
		JPanel p = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			pPicture = new JLabel("Placeholder");
			p.add(pPicture, new CC().wrap());
		//p.setBackground(Color.red);
			
			// change picture button
			JButton changePic = new JButton("Change Picture");
			changePic.addActionListener(this);
			changePic.setActionCommand("Change Profile Picture");
			p.add(changePic);
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
			
			// Specialities Stats
			JButton specialitiesBtn = new JButton("Specialities");
			specialitiesBtn.addActionListener(this);
			specialitiesBtn.setActionCommand("View Specialities");
			s.add(specialitiesBtn);
			JLabel specialitiesVal = new JLabel("0");
			try {
				specialitiesVal = new JLabel(""+StaffMemberDMO.getInstance().getSpecialities(selectedStaffMember).size());
			} catch (EmptyResultSetException e) {
				System.out.println("Stats:- No Specialities");
			}
			s.add(specialitiesVal, new CC().wrap());
			
			// Patients Stats
			JButton patientsBtn = new JButton("Patients");
			patientsBtn.addActionListener(this);
			patientsBtn.setActionCommand("View Patients");
			s.add(patientsBtn);
			JLabel patientsVal = new JLabel("0");
			try {
				patientsVal = new JLabel(""+PatientDMO.getInstance().getAllPermanentPatientsByDoctorId(selectedStaffMember.getId()).size());
			} catch (EmptyResultSetException e) {
				System.out.println("Stats:- No Permanent Patients");
			}
			s.add(patientsVal, new CC().wrap());
			
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
		JButton holidaysBtn = new JButton("Availabilities");
		holidaysBtn.addActionListener(this);
		holidaysBtn.setActionCommand("View Availabilities");
		s.add(holidaysBtn, new CC());
		JLabel holidaysVal = new JLabel("0/" + selectedStaffMember.getHolidayAllowance());
		try {
			holidaysVal = new JLabel(StaffMemberDMO.getInstance().getHolidays(selectedStaffMember).size() + "/" + selectedStaffMember.getHolidayAllowance());
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
			taxFormsVal = new JLabel(""+StaffMemberDMO.getInstance().getTaxForms(selectedStaffMember).size());
		} catch (EmptyResultSetException e) {
			System.out.println("Stats:- No Tax Forms");
		}
		s.add(taxFormsVal, new CC().wrap());
		
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
				new ChangeStaffMemberPassword(this.selectedStaffMember);
				break;
			case "Remove":
				this.remove();
				break;
			case "View Availabilities":
				new ViewAvailability(selectedStaffMember);
				break;
			case "View Specialities":
				new ViewSpecialities(selectedStaffMember);
				break;
			case "Change Profile Picture":
				this.choosePicture();
				break;
			case "View Tax Forms":
				new ViewTaxForms(selectedStaffMember);
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
	
	/** choosePicture
	 * 
	 */
	public void choosePicture()
	{
		int returnVal = fC.showOpenDialog(this);
		 
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fC.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName());
            try {
				BufferedImage img = ImageIO.read(file);
				System.out.println("Resizing...");
				img = resizeImage(img, img.getType());
				this.pPicture.setIcon(new ImageIcon(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
    }
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type)
	{
		BufferedImage resizedImage = new BufferedImage(100, 100, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 100, 100, null);
		g.dispose();
	 
		return resizedImage;
	}

}
