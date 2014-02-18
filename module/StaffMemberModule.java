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
import javax.swing.JTable;

import module.StaffMember.*;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISModuleMain;

public class StaffMemberModule extends GPSISModuleMain implements ActionListener {
	
	private List<StaffMember> staffMembers;
	private JTable staffMemberTable;

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add Staff Member":
				new AddStaffMember();
			case "View/Edit Profile":
				StaffMember sM = this.staffMembers.get(staffMemberTable.getSelectedRow());
				new ViewStaffMember(sM);
		}
	}

	private JTable buildStaffMemberTable()
	{
		try {
			this.staffMembers = staffMemberDMO.getAll();
			
			Object[][] sM = new Object[staffMembers.size()][8];
			int i = 0;
			
			for (StaffMember staffMember : staffMembers)
			{
				sM[i][1] = staffMember.getId();
				sM[i][0] = staffMember.getUsername();
				sM[i][1] = staffMember.getFirstName();
				sM[i][2] = staffMember.getLastName();
				sM[i][3] = staffMember.isFullTime();
				sM[i][4] = staffMember.getStartDate();
				sM[i][5] = staffMember.isOfficeManager();
				sM[i][6] = staffMember.getRole();
				sM[i][7] = staffMember.getHolidayAllowance();
				i++;
			}
			
			StaffMemberATM sMM = new StaffMemberATM(sM);
			
			JTable sMT = new JTable (sMM);
			return sMT;
		} catch (EmptyResultSetException e) {
			System.out.println("EMPTY SET");
			return null;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel staffMemberModuleView = new JPanel(new GridBagLayout());
		
			// USE JTable http://docs.oracle.com/javase/tutorial/uiswing/components/table.html
			
			// need to implement controls for browsing staff members, filters etc.
			
			
			// List View
			JPanel leftPanel = new JPanel(new GridBagLayout());
				this.staffMemberTable = this.buildStaffMemberTable();
					GridBagConstraints gbC = new GridBagConstraints();
					gbC.anchor = GridBagConstraints.PAGE_START;
					gbC.fill = GridBagConstraints.VERTICAL;
					gbC.weightx = 1;
					gbC.weighty = 1;
					gbC.gridx = 0;
					gbC.gridy = 1;
				leftPanel.add(staffMemberTable, gbC);
			
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.PAGE_START;
				gbC.fill = GridBagConstraints.VERTICAL;
				gbC.weightx = 0.8;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 0;
				gbC.ipadx = 10;
				gbC.ipady = 10;
				//leftPanel.setBackground(Color.green);
			staffMemberModuleView.add(leftPanel, gbC);
			
			
			
			// Controls (RIGHT PANE)
			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
				JButton addStaffMemberBtn = new JButton("Add Staff Member");
					addStaffMemberBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
					addStaffMemberBtn.addActionListener(this);
					addStaffMemberBtn.setActionCommand("Add Staff Member");
				rightPanel.add(addStaffMemberBtn);
				
				JButton modifyStaffMemberBtn = new JButton("View/Edit Profile"); // TODO only show when a Staff Member is selected
					modifyStaffMemberBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
					modifyStaffMemberBtn.addActionListener(this);
					modifyStaffMemberBtn.setActionCommand("View/Edit Profile");
				rightPanel.add(modifyStaffMemberBtn);
				
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_END;
				gbC.weightx = 0.2;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 0;				
				
				
			staffMemberModuleView.add(rightPanel, gbC);
			//staffMemberModuleView.setBackground(Color.red);
		
		return staffMemberModuleView;
	}

}
