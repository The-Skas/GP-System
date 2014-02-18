/** TODO ViewStaffMember
 * Displays a Window with the given Staff Member's properties ready for editing
 * @author VJ
 */
package module.StaffMember;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDatePicker;
import object.StaffMember;
import framework.GPSISPopup;

public class ViewStaffMember extends GPSISPopup {

	private static final long serialVersionUID = -2034755798745110525L;
	
	// Set Fields
	private JTextField usernameFld;
	//private JPasswordField passwordFld;
	private JTextField firstNameFld;
	private JTextField lastNameFld;
	private JCheckBox isFullTimeFld;
	private JLabel startDateFld;
	private JCheckBox isOfficeManagerFld;
	private JComboBox<String> roleFld = new JComboBox<String>(this.roles);
	private String[] roles = {"Receptionist", "Nurse", "Doctor"};
	private JSpinner holidayAllowanceFld;

	public ViewStaffMember(StaffMember sM) {
		super("Modify Staff Member");
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		this.setSize(400, 450);
		
		this.usernameFld.setText(sM.getUsername());
		this.firstNameFld.setText(sM.getFirstName());
		this.lastNameFld.setText(sM.getLastName());
		this.isFullTimeFld.setSelected(sM.isFullTime());
		this.startDateFld.setText(sM.getStartDate().toString()); // need to convert to YYYY-MM-DD
		this.isOfficeManagerFld.setSelected(sM.isOfficeManager());
		this.roleFld.setSelectedIndex(2);
		this.holidayAllowanceFld = new JSpinner(new SpinnerNumberModel(sM.getHolidayAllowance(), 5, 100, 1));
	}

}
