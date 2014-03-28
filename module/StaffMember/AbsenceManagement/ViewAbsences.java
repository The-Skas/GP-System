/** ViewAbsences 
 * Displays a Window showing the Absences for a given Staff Member
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.AbsenceManagement;

import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mapper.StaffMemberDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

public class ViewAbsences extends GPSISPopup {

	private static final long	serialVersionUID	= 1L;
	private static StaffMember	staffMember;
	private static AbsenceATM	aM;
	private static JTable		absenceTable;
	private static List<Date>	absences;

	/** View Absences Constructor 
	 * Builds and Shows a Window showing the Absences for the given Staff Member
	 * @param sM The Staff Member to show Absences for
	 */
	public ViewAbsences(StaffMember sM) {
		super(sM.getName() + "'s Absences");
		this.setModal(true);
		staffMember = sM;

		JPanel availabilityView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			absences = StaffMemberDMO.getInstance().getAbsences(staffMember);

			aM = new AbsenceATM(absences);
			absenceTable = new JTable(aM);
			leftPanel.add(new JScrollPane(absenceTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			leftPanel.add(new JLabel("No Absences"));
		}

		availabilityView.add(leftPanel, new CC().span().grow());

		this.add(availabilityView);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}

/**
 * End of File: ViewAbsences.java 
 * Location: module/StaffMember/AbsenceManagement
 */
