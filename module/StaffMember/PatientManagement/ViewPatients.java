/**
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.PatientManagement;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mapper.PatientDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;
import object.Patient;

public class ViewPatients extends GPSISPopup {

	private static final long serialVersionUID = 1L;
	private static StaffMember staffMember;
	private static PatientATM pM;
	private static JTable PatientTable;
	private static List<Patient> Patients;

	public ViewPatients(StaffMember sM) {
		super(sM.getName() + " Patients");
		this.setModal(true);
		staffMember = sM;
		
		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		
		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			Patients = PatientDMO.getInstance().getAllPermanentPatientsByDoctorId(staffMember.getId());
						
			pM = new PatientATM(Patients);
			PatientTable = new JTable (pM);
			leftPanel.add(new JScrollPane(PatientTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			leftPanel.add(new JLabel("No Patients"));
		}
			
		patientView.add(leftPanel, new CC().span().grow());		
		
		this.add(patientView);
		
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}

}
