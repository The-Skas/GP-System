/**
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.AppointmentManagement;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mapper.CalendarAppointmentDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.CalendarAppointment;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

public class ViewAppointments extends GPSISPopup {

	private static final long serialVersionUID = 1L;
	private static StaffMember staffMember;
	private static AppointmentATM pM;
	private static JTable AppointmentTable;
	private static List<CalendarAppointment> Appointments;

	public ViewAppointments(StaffMember sM) {
		super(sM.getName() + " Appointments");
		this.setModal(true);
		staffMember = sM;
		
		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		
		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			Appointments = CalendarAppointmentDMO.getInstance().getAppointmentsByDoctorId(staffMember.getId());
						
			pM = new AppointmentATM(Appointments);
			AppointmentTable = new JTable (pM);
			leftPanel.add(new JScrollPane(AppointmentTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			leftPanel.add(new JLabel("No Appointments"));
		}
			
		patientView.add(leftPanel, new CC().span().grow());		
		
		this.add(patientView);
		
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}

}
