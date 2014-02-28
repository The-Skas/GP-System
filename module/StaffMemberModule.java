/** StaffMemberModule
 * The Main View for the Staff Member Module
 * @author VJ
 */
package module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import module.StaffMember.*;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISModuleMain;

public class StaffMemberModule extends GPSISModuleMain implements ActionListener, ListSelectionListener{
	
	private static List<StaffMember> staffMembers;
	private static JTable staffMemberTable;
	private static StaffMemberATM sMM;
	private static JButton modifyStaffMemberBtn; 
	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel staffMemberModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			// need to implement controls for browsing staff members, filters etc.
			
			
			// Table View
			JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
				staffMemberTable = buildStaffMemberTable();
				staffMemberTable.getSelectionModel().addListSelectionListener(this);
			leftPanel.add(new JScrollPane(staffMemberTable), new CC().span().grow());
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
		
		return staffMemberModuleView;
	}
	
	public static List<StaffMember> getStaffMembers()
	{
		return staffMembers;
	}
	
	public static JTable getStaffMemberTable()
	{
		return StaffMemberModule.staffMemberTable;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add Staff Member":
				new AddStaffMember();
				break;
			case "View/Edit Profile":
				StaffMember sM = staffMembers.get(staffMemberTable.getSelectedRow());				
				new ViewStaffMember(sM);
				break;
		}
	}

	public static JTable buildStaffMemberTable()
	{
		try {
			staffMembers = staffMemberDMO.getAll();
						
			sMM = new StaffMemberATM(staffMembers);
			JTable sMT = new JTable (sMM);
			return sMT;
		} catch (EmptyResultSetException e) {
			System.out.println("EMPTY SET");
			return null;
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		modifyStaffMemberBtn.setVisible(true);
	}
}
