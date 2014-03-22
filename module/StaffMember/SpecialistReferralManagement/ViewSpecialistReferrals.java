/** ViewPrescriptions 
 * Displays a Window showing the Prescriptions given by a Staff Member
 * 
 * TODO VJ :- Merge Matt's Branch with his DMO
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.SpecialistReferralManagement;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mapper.StaffMemberDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.ReferralObject;
import object.StaffMember;
import framework.GPSISPopup;

public class ViewSpecialistReferrals extends GPSISPopup {

	private static final long				serialVersionUID	= 1L;
	private static StaffMember				staffMember;
	private static SpecialistReferralATM	pM;
	private static JTable					SpecialistReferralTable;
	private static List<ReferralObject>		SpecialistReferrals;
	
	/** ViewSpecialistReferrals Constructor 
	 * Builds and Shows a Window showing the Referrals given by a Staff Member
	 * 
	 * @param sM
	 *            The Staff Member to show Referrals given by
	 */
	public ViewSpecialistReferrals(StaffMember sM) {
		super(sM.getName() + "'s Referrals");
		this.setModal(true);
		staffMember = sM;

		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		SpecialistReferrals = StaffMemberDMO.getInstance().getReferrals(staffMember);
		pM = new SpecialistReferralATM(SpecialistReferrals);
		SpecialistReferralTable = new JTable (pM);
		leftPanel.add(new JScrollPane(SpecialistReferralTable), new CC().span().grow());

		patientView.add(leftPanel, new CC().span().grow());

		this.add(patientView);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}

/**
 * End of File: ViewSpecialistReferrals.java 
 * Location: module/StaffMember/SpecialistReferralManagement
 */