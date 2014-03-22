/** ViewAppointments
 * Displays a Window showing the Calendar Appointments for a given Staff Member
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.AppointmentManagement;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mapper.StaffMemberDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.CalendarAppointment;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

public class ViewAppointments extends GPSISPopup {

	private static final long					serialVersionUID	= 1L;
	private static StaffMember					staffMember;
	private static AppointmentATM				pM;
	private static JTable						AppointmentTable;
	private static List<CalendarAppointment>	Appointments;

	/** ViewAppointments Constructor
	 * Builds and Shows a Window showing the Calendar Appointments for 
	 * the given Staff Member
	 * @param sM The Staff Member to show Calendar Appointments for
	 */
	public ViewAppointments(StaffMember sM) {
		super(sM.getName() + "'s Appointments");
		this.setModal(true);
		staffMember = sM;

		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			Appointments = StaffMemberDMO.getInstance().getAppointments(staffMember);
		} catch (EmptyResultSetException e) {
			System.out.println("No Appointments");
		}

		pM = new AppointmentATM(Appointments);
		AppointmentTable = new JTable(pM);
		leftPanel.add(new JScrollPane(AppointmentTable), new CC().span().grow());

		patientView.add(leftPanel, new CC().span().grow());

		this.add(patientView);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}

/**
 * End of File: ViewAppointments.java 
 * Location: module/StaffMember/AppointmentManagement
 */
