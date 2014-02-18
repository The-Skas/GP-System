/** StaffMemberModule
 * The Main View for the Staff Member Module
 * @author VJ
 */
package module;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
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
	
	private List<StaffMember> staffMembers;
	private JTable staffMemberTable;
	private StaffMemberATM sMM;
	private JButton modifyStaffMemberBtn; 
	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel staffMemberModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			// need to implement controls for browsing staff members, filters etc.
			
			
			// List View
			JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
				this.staffMemberTable = this.buildStaffMemberTable();
				this.staffMemberTable.getSelectionModel().addListSelectionListener(this);
			leftPanel.add(new JScrollPane(staffMemberTable), new CC().span().grow());
		staffMemberModuleView.add(leftPanel, new CC().span().grow());
			
			
			
			// Controls (RIGHT PANE)
			JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
				JButton addStaffMemberBtn = new JButton("Add Staff Member");
					addStaffMemberBtn.addActionListener(this);
					addStaffMemberBtn.setActionCommand("Add Staff Member");
				rightPanel.add(addStaffMemberBtn, new CC().wrap());
				
				this.modifyStaffMemberBtn = new JButton("View/Edit Profile");
					this.modifyStaffMemberBtn.addActionListener(this);
					this.modifyStaffMemberBtn.setActionCommand("View/Edit Profile");
					this.modifyStaffMemberBtn.setVisible(false);
				rightPanel.add(modifyStaffMemberBtn);
				
			staffMemberModuleView.add(rightPanel, new CC().dockEast());
			//staffMemberModuleView.setBackground(Color.red);
		
		return staffMemberModuleView;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add Staff Member":
				new AddStaffMember();
				break;
			case "View/Edit Profile":
				StaffMember sM = this.staffMembers.get(staffMemberTable.getSelectedRow());				
				new ViewStaffMember(sM);
				break;
		}
	}

	private JTable buildStaffMemberTable()
	{
		try {
			this.staffMembers = staffMemberDMO.getAll();
						
			sMM = new StaffMemberATM(this.staffMembers);
			JTable sMT = new JTable (sMM);
			return sMT;
		} catch (EmptyResultSetException e) {
			System.out.println("EMPTY SET");
			return null;
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		this.modifyStaffMemberBtn.setVisible(true);
	}
}
