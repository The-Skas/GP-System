/** StaffMemberModule 
 * The Main View for the Staff Member Module
 * 
 * @author Vijendra Patel (vp302)
 */
package module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import module.CalendarAppointments.CalendarAppointmentATM;
import module.StaffMember.*;
import object.CalendarAppointment;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISModuleMain;

public class StaffMemberModule extends GPSISModuleMain implements ActionListener, ListSelectionListener, DocumentListener {

	private static List<StaffMember>				staffMembers;
	private static JTable							staffMemberTable;
	private static JButton							modifyStaffMemberBtn;
	public static TableRowSorter<StaffMemberATM>	sorter;
	private JTextField								textQuery			= new JTextField(30);
	private static int								selectedRowInModel	= 0;

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel staffMemberModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		// need to implement controls for browsing staff members, filters etc.

		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		leftPanel.add(new JLabel("Search: "), new CC().dockNorth());
		leftPanel.add(textQuery, new CC().span().wrap().dockNorth());
		try {
			staffMembers = staffMemberDMO.getAll();
			StaffMemberATM sMM = new StaffMemberATM(staffMembers);
			staffMemberTable = new JTable(sMM);
			sorter = new TableRowSorter<StaffMemberATM>(sMM);
			staffMemberTable.setRowSorter(sorter);
			staffMemberTable.getSelectionModel().addListSelectionListener(this);
			leftPanel.add(new JScrollPane(staffMemberTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			staffMemberTable = new JTable(new StaffMemberATM(new ArrayList<StaffMember>()));
		}

		
		staffMemberModuleView.add(leftPanel, new CC().span().grow());

		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		JButton addStaffMemberBtn = new JButton("Add Staff Member");
		addStaffMemberBtn.addActionListener(this);
		addStaffMemberBtn.setActionCommand("Add Staff Member");
		rightPanel.add(addStaffMemberBtn, new CC().wrap());

		modifyStaffMemberBtn = new JButton("View/Edit Profile");
		modifyStaffMemberBtn.addActionListener(this);
		modifyStaffMemberBtn.setActionCommand("View/Edit Profile");
		modifyStaffMemberBtn.setVisible(false);
		rightPanel.add(modifyStaffMemberBtn);

		staffMemberModuleView.add(rightPanel, new CC().dockEast());

		textQuery.getDocument().addDocumentListener(this);
		return staffMemberModuleView;
	}

	/** getStaffMembers (old implementation of Object Selection - Used in Patient module for now)
	 * @return
	 */
	public static List<StaffMember> getStaffMembers() {
		return staffMembers;
	}

	/** getStaffMemberTable
	 * @return the StaffMember table
	 */
	public static JTable getStaffMemberTable() {
		return StaffMemberModule.staffMemberTable;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
			case "Add Staff Member":
				new AddStaffMember();

				break;
			case "View/Edit Profile":
				StaffMember sM = staffMembers.get(selectedRowInModel);
				new ViewStaffMember(sM);
				break;
		}
	}

	/** newFilter
	 * Filters Staff Members
	 */
	private void newFilter() {
		RowFilter<StaffMemberATM, Object> rf = null;
		List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>();

		try {
			String text = textQuery.getText();
			String[] textArray = text.split(" ");

			for (int i = 0; i < textArray.length; i++) {
				rfs.add(RowFilter.regexFilter("(?i)" + textArray[i]));
			}

			rf = RowFilter.andFilter(rfs);

		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}

		sorter.setRowFilter(rf);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		modifyStaffMemberBtn.setVisible(true);
		int viewRow = staffMemberTable.getSelectedRow();
		if (viewRow < 0) {
		} else {
			selectedRowInModel = staffMemberTable.convertRowIndexToModel(viewRow);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		newFilter();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		newFilter();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		newFilter();
	}
}

/**
 * End of File: StaffMemberModule.java 
 * Location: module
 */