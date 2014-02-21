/**
 * 
 */
package module.StaffMember;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import object.StaffMember;
import framework.GPSISPopup;

/**
 * @author VJ
 *
 */
public class ViewHolidays extends GPSISPopup implements ActionListener {

	private static final long serialVersionUID = 6455018870545066105L;
	private StaffMember staffMember;

	public ViewHolidays(StaffMember sM) {
		super("Staff Member Holidays");
		this.staffMember = sM;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}

}
