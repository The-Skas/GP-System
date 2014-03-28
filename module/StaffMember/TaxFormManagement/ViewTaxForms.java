/** ViewTaxForms 
 * Displays a Window showing the Tax Forms for a Staff Member
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.TaxFormManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import mapper.SQLBuilder;
import mapper.TaxFormDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import exception.EmptyResultSetException;
import framework.GPSISPopup;
import object.StaffMember;
import object.TaxForm;

public class ViewTaxForms extends GPSISPopup implements ActionListener {

	private static final long		serialVersionUID	= 1L;
	private static StaffMember		selectedStaffMember;
	private static TaxFormATM		tFModel;
	public static JTable			taxFormTable;
	private static List<TaxForm>	taxForms;
	private static JPanel			leftPanel;

	/** ViewTaxForms Constructor 
	 * Builds and Shows a Window showing the Tax Forms for a Staff Member
	 * 
	 * @param sM
	 *            The Staff Member to show Tax Forms for
	 */
	public ViewTaxForms(StaffMember sM) {
		super(sM.getName() + "'s Tax Forms");
		this.setLayout(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		this.setModal(true);
		selectedStaffMember = sM;

		// Table View
		leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			taxForms =
					TaxFormDMO.getInstance().getAllByProperties(
							new SQLBuilder("staff_member_id", "=", "" + selectedStaffMember.getId()));

		} catch (EmptyResultSetException e) {
			System.out.println("No Tax Forms");
			taxForms = new ArrayList<TaxForm>();
		}
		tFModel = new TaxFormATM(taxForms);
		taxFormTable = new JTable(tFModel);
		leftPanel.add(new JScrollPane(taxFormTable), new CC().grow().span());
		this.add(leftPanel, new CC().span().grow());

		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		// Specialitiy Label
		JButton addTaxFormBtn = new JButton("Add Tax Form");
		addTaxFormBtn.addActionListener(this);
		addTaxFormBtn.setActionCommand("Add Tax Form");
		rightPanel.add(addTaxFormBtn);

		this.add(rightPanel, new CC().dockEast());

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
			case "Add Tax Form":
				new AddTaxForm(selectedStaffMember);
				break;
		}
	}
}

/**
 * End of File: ViewTaxForms.java 
 * Location: module/StaffMember/TaxFormManagement
 */