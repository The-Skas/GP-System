/**
 * 
 */
package module.StaffMember;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;




import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import mapper.SpecialityDMO;
import mapper.StaffMemberDMO;
import module.StaffMemberModule;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
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
	private static JButton removeHolidayBtn;
	private static JComboBox<Speciality> specialityFld;
	private static List<Speciality> specialities;

	public ViewSpecialities(StaffMember sM) {
		super("Staff Member Specialities");
		this.setModal(true);
		staffMember = sM;
		
		JPanel staffMemberModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
	
		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			specialities = StaffMemberDMO.getInstance().getSpecialities(staffMember);
						
			sModel = new SpecialityATM(specialities);
			specialityTable = new JTable (sModel);
			specialityTable.getSelectionModel().addListSelectionListener(this);
			leftPanel.add(new JScrollPane(specialityTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			System.out.println("EMPTY SET");
			leftPanel.add(new JLabel("No Specialities"));
		}
			
		staffMemberModuleView.add(leftPanel, new CC().span().grow());		
		
		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			// Specialitiy Label
			JLabel startDateLbl = new JLabel("Add Speciality: ");
			rightPanel.add(startDateLbl);
			// Specialities Component
			try {
				specialityFld = new JComboBox<Speciality>();
				specialityFld.setModel(new SpecialityALM(SpecialityDMO.getInstance().getAll()));
			} catch (EmptyResultSetException e) {
				specialityFld = new JComboBox<Speciality>();
			}
			specialityFld.setRenderer(new SpecialityJCBRenderer());
			AutoCompleteDecorator.decorate(specialityFld);
			rightPanel.add((Component) specialityFld, new CC().wrap());
			
			JButton addHolidayBtn = new JButton("Add Holiday");
			addHolidayBtn.addActionListener(this);
			addHolidayBtn.setActionCommand("Add Holiday");
			rightPanel.add(addHolidayBtn, new CC().wrap());
			
			removeHolidayBtn = new JButton("Remove Holiday");
			removeHolidayBtn.addActionListener(this);
			removeHolidayBtn.setActionCommand("Remove Holiday");
			removeHolidayBtn.setVisible(false);
			rightPanel.add(removeHolidayBtn);
			
		staffMemberModuleView.add(rightPanel, new CC().dockEast());
	
		this.add(staffMemberModuleView);
		
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add Holiday":
				this.add();
				break;
			case "Remove Holiday":
				this.remove();
				break;
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		removeHolidayBtn.setVisible(true);
	}
	
	/** remove
	 * Removes Holiday from the Database
	 */
	public void remove()
	{
		if (JOptionPane.showConfirmDialog(this, 
				"Are you sure that you wish to remove " + specialities.get(specialityTable.getSelectedRow()) + "?", 
				"Are you sure?", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			StaffMemberDMO.getInstance().removeSpeciality(staffMember, specialities.get(specialityTable.getSelectedRow()));
			sModel.removeRow(specialities.get(specialityTable.getSelectedRow())); // use the removeRow method in ATM
		}
		
	}
	
	/** add
	 * Adds a Holiday
	 * TODO need to check if limit reached and for duplicate holiday
	 */
	public void add()
	{
		StaffMemberDMO.getInstance().addSpeciality(staffMember, specialities.get(specialityFld.getSelectedIndex()));
		sModel.addRow(specialities.get(specialityFld.getSelectedIndex()));
	}

}
