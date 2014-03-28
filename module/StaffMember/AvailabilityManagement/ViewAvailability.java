/** ViewAvailability 
 * Displays a Window showing the Availability for a given Staff Member
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.AvailabilityManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import mapper.StaffMemberDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import object.Register;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

public class ViewAvailability extends GPSISPopup implements ActionListener {

	private static final long			serialVersionUID	= 1L;
	private static StaffMember			staffMember;
	private static AvailabilityATM		aM;
	private static JTable				availabilityTable;
	private static JDatePicker			availabilityDateFld;
	private static JComboBox<String>	availabilityComboBoxFld;
	private static String[]				availabilities		= { "Holiday", "Morning", "Afternoon", "All Day" };
	private static List<Register>		register;

	/** ViewAvailability Constructor 
	 * Builds and Shows a Window showing the Availability for the given Staff Member
	 * 
	 * @param sM
	 *            The Staff Member to show Availability for
	 */
	public ViewAvailability(StaffMember sM) {
		super(sM.getName() + "'s Availability");
		this.setModal(true);
		staffMember = sM;

		JPanel availabilityView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			register = StaffMemberDMO.getInstance().getAllRegisters(staffMember);

			aM = new AvailabilityATM(register);
			availabilityTable = new JTable(aM);
			leftPanel.add(new JScrollPane(availabilityTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			leftPanel.add(new JLabel("No Availabilties"));
		}

		availabilityView.add(leftPanel, new CC().span().grow());

		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		// Availability Date Label
		JLabel availabilityDateLbl = new JLabel("Add Availability: ");
		rightPanel.add(availabilityDateLbl);
		// Availability Date Component
		availabilityDateFld =
				JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
		rightPanel.add((Component) availabilityDateFld, new CC().wrap());

		availabilityComboBoxFld = new JComboBox<String>(availabilities);
		rightPanel.add((Component) availabilityComboBoxFld);
		availabilityComboBoxFld.setSelectedIndex(0);

		JButton addHolidayBtn = new JButton("Add Availability");
		addHolidayBtn.addActionListener(this);
		addHolidayBtn.setActionCommand("Add Availability");
		rightPanel.add(addHolidayBtn, new CC().wrap());

		availabilityView.add(rightPanel, new CC().dockEast());

		this.add(availabilityView);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
			case "Add Availability":
				this.add();
				break;
		}

	}

	/** add 
	 * Adds a Holiday TODO need to check if limit reached and for duplicate
	 * holiday
	 */
	public void add() {
		Register r =
				new Register(staffMember, (Date) availabilityDateFld.getModel().getValue(),
						availabilityComboBoxFld.getSelectedIndex());
		for (Register i : register) {
			if (r.getId() == i.getId()) {
				aM.swapRow(i, r);
				return;
			}
		}

		aM.addRow(r);
	}

}

/**
 * End of File: ViewAvailibility.java 
 * Location: module/StaffMember/AvailibilityManagement
 */
