/**
 * 
 */
package module.StaffMember.SpecialityManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mapper.SpecialityDMO;
import mapper.StaffMemberDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.Speciality;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

/**
 * @author VJ
 *
 */
public class ViewSpecialities extends GPSISPopup implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 6455018870545066105L;
	private static StaffMember staffMember;
	private static SpecialityATM sModel;
	private static JTable specialityTable;
	private static JButton removeSpecialityBtn;
	public static JComboBox<Speciality> specialityFld;
	private static List<Speciality> specialities;
	private static JPanel leftPanel;

	public ViewSpecialities(StaffMember sM) {
		super("Staff Member Specialities");
		this.setLayout(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow())); 
		this.setModal(true);
		staffMember = sM;
		
	
		// Table View
		leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			specialities = StaffMemberDMO.getInstance().getSpecialities(staffMember);
			
		} catch (EmptyResultSetException e) {
			System.out.println("No Specialities");
			specialities = new ArrayList<Speciality>();
		}	
		sModel = new SpecialityATM(specialities);
		specialityTable = new JTable (sModel);
		specialityTable.getSelectionModel().addListSelectionListener(this);
		leftPanel.add(new JScrollPane(specialityTable), new CC().grow().span());
		this.add(leftPanel, new CC().span().grow());		
		
		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			// Specialitiy Label
			JLabel startDateLbl = new JLabel("Add Speciality: ");
			rightPanel.add(startDateLbl);
			// Specialities Component
			try {
				specialityFld = new JComboBox<Speciality>();
				specialityFld.setModel(new SpecialityALM(SpecialityDMO.getInstance().getAll()));
				specialityFld.setRenderer(new SpecialityJCBRenderer());
				rightPanel.add((Component) specialityFld);
				specialityFld.setSelectedIndex(0);
				JButton addSpecialityBtn = new JButton("Add Speciality");
				addSpecialityBtn.addActionListener(this);
				addSpecialityBtn.setActionCommand("Add Speciality");
				rightPanel.add(addSpecialityBtn, new CC().wrap());
				
			} catch (EmptyResultSetException e) {
				System.out.println("No Specialities Found, add some!");				
			}
			
			removeSpecialityBtn = new JButton("Remove Speciality");
			removeSpecialityBtn.addActionListener(this);
			removeSpecialityBtn.setActionCommand("Remove Speciality");
			removeSpecialityBtn.setVisible(false);
			rightPanel.add(removeSpecialityBtn);
			
			
			JButton addHolidayBtn = new JButton("Add New Speciality");
			addHolidayBtn.addActionListener(this);
			addHolidayBtn.setActionCommand("Add New Speciality");
			rightPanel.add(addHolidayBtn, new CC().alignX("right").alignY("baseline"));
			
			
			
		this.add(rightPanel, new CC().dockEast());
	
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}
		

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add Speciality":
				this.add();
				break;
			case "Remove Speciality":
				this.remove();
				break;
			case "Add New Speciality":
				new AddNewSpeciality();
				break;
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		removeSpecialityBtn.setVisible(true);
	}
	
	/** remove
	 * Removes Speciality Relationship from the Database
	 */
	private void remove()
	{
		if (JOptionPane.showConfirmDialog(this, 
				"Are you sure that you wish to remove " + specialities.get(specialityTable.getSelectedRow()).getName() + "?", 
				"Are you sure?", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			StaffMemberDMO.getInstance().removeSpeciality(staffMember, specialities.get(specialityTable.getSelectedRow()));
			sModel.removeRow(specialities.get(specialityTable.getSelectedRow())); // use the removeRow method in ATM
		}
		
	}
	
	/** add
	 * Adds a Speciality
	 */
	private void add()
	{
		StaffMemberDMO.getInstance().addSpeciality(staffMember, (Speciality)specialityFld.getSelectedItem());
		sModel.addRow((Speciality)specialityFld.getSelectedItem());
	}

}
