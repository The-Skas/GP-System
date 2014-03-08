/** TODO Merge Matt's Branch
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.SpecialistReferralManagement;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

public class ViewSpecialistReferrals extends GPSISPopup {

	private static final long serialVersionUID = 1L;
	private static StaffMember staffMember;
	private static SpecialistReferralATM pM;
	private static JTable SpecialistReferralTable;
	private static List<Referral> SpecialistReferrals;

	public ViewSpecialistReferrals(StaffMember sM) {
		super(sM.getName() + " SpecialistReferrals");
		this.setModal(true);
		staffMember = sM;
		
		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		
		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			SpecialistReferrals = SpecialistReferralDMO.getInstance().getSpecialistReferralsByDoctorId(staffMember.getId());
						
			pM = new SpecialistReferralATM(SpecialistReferrals);
			SpecialistReferralTable = new JTable (pM);
			leftPanel.add(new JScrollPane(SpecialistReferralTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			leftPanel.add(new JLabel("No Specialist Referrals"));
		}
			
		patientView.add(leftPanel, new CC().span().grow());		
		
		this.add(patientView);
		
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}

}
