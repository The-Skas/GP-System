/** ViewPrescriptions 
 * Displays a Window showing the Prescriptions given by a Staff Member
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.PrescriptionManagement;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mapper.PrescriptionDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.Prescription;
import object.StaffMember;
import framework.GPSISPopup;

public class ViewPrescriptions extends GPSISPopup {

	private static final long			serialVersionUID	= 1L;
	private static StaffMember			staffMember;
	private static PrescriptionATM		pM;
	private static JTable				PrescriptionTable;
	private static List<Prescription>	Prescriptions;

	/** ViewPrescriptions Constructor 
	 * Builds and Shows a Window showing the Prescriptions given by a Staff Member
	 * @param sM
	 *            The Staff Member to show Prescriptions given by
	 */
	public ViewPrescriptions(StaffMember sM) {
		super(sM.getName() + "'s Prescriptions");
		this.setModal(true);
		staffMember = sM;

		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		Prescriptions = PrescriptionDMO.getInstance().getPrescriptionsByDoctor(staffMember);

		pM = new PrescriptionATM(Prescriptions);
		PrescriptionTable = new JTable(pM);
		leftPanel.add(new JScrollPane(PrescriptionTable), new CC().span().grow());

		patientView.add(leftPanel, new CC().span().grow());

		this.add(patientView);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}

/**
 * End of File: ViewPrescriptions.java 
 * Location: module/StaffMember/PrescriptionManagement
 */