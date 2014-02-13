/** StaffMemberModule
 * The Main View for the Staff Member Module
 * @author VJ
 */
package module;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import module.StaffMember.AddStaffMember;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISModuleMain;

public class StaffMemberModule extends GPSISModuleMain implements ActionListener {
	
	private JList<StaffMember> staffList;

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add Staff Member":
				new AddStaffMember();
		}
	}

	private JList<StaffMember> buildStaffList()
	{
		try {
			HashSet<StaffMember> staffMembers = (HashSet<StaffMember>)staffMemberDMO.getAll();
			StaffMember[] sM = new StaffMember[staffMembers.size()];
			int i = 0;
			for (StaffMember staffMember : staffMembers)
			{
				sM[i] = staffMember;
				staffMemberDMO.getAbsences(staffMember);
				i++;
			}
			return new JList<StaffMember>(sM);
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
			
		
			
			
			// List View
			JPanel leftPanel = new JPanel(new GridBagLayout());
				this.staffList = this.buildStaffList();
					GridBagConstraints gbC = new GridBagConstraints();
					gbC.anchor = GridBagConstraints.CENTER;
					gbC.weightx = 1;
					gbC.weighty = 1;
					gbC.gridx = 0;
					gbC.gridy = 1;
				leftPanel.add(staffList, gbC);
			
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.CENTER;
				gbC.weightx = 0.8;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 0;
			staffMemberModuleView.add(leftPanel);
			
			
			
			// Controls
			JPanel rightPanel = new JPanel(new GridBagLayout());
				JButton addStaffMemberBtn = new JButton("Add Staff Member");
					gbC = new GridBagConstraints();
					gbC.anchor = GridBagConstraints.LINE_END;
					addStaffMemberBtn.addActionListener(this);
					addStaffMemberBtn.setActionCommand("Add Staff Member");
				rightPanel.add(addStaffMemberBtn, gbC);
			
				
				gbC = new GridBagConstraints();
				gbC.anchor = GridBagConstraints.LINE_END;
				gbC.weightx = 0.2;
				gbC.weighty = 1;
				gbC.gridx = 0;
				gbC.gridy = 0;				
			staffMemberModuleView.add(rightPanel, gbC);
		
		
		staffMemberModuleView.add(addStaffMemberBtn, gbC);
		
		return staffMemberModuleView;
	}

}
