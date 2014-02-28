/**
 * 
 */
package module.StaffMember.HolidayManagement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mapper.StaffMemberDMO;
import module.StaffMemberModule;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISPopup;

/**
 * @author VJ
 *
 */
public class ViewHolidays extends GPSISPopup implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 6455018870545066105L;
	private static StaffMember staffMember;
	private static HolidayATM hM;
	private static JTable holidayTable;
	private static JButton removeHolidayBtn;
	private static JDatePicker holidayFld;
	private static List<Date> holidays;

	public ViewHolidays(StaffMember sM) {
		super("Staff Member Holidays");
		this.setModal(true);
		staffMember = sM;
		
		JPanel staffMemberModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		// need to implement controls for browsing staff members, filters etc.
		
		
		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			holidays = StaffMemberDMO.getInstance().getHolidays(staffMember);
						
			hM = new HolidayATM(holidays);
			holidayTable = new JTable (hM);
			holidayTable.getSelectionModel().addListSelectionListener(this);
			leftPanel.add(new JScrollPane(holidayTable), new CC().span().grow());
		} catch (EmptyResultSetException e) {
			System.out.println("EMPTY SET");
			leftPanel.add(new JLabel("No Holidays"));
		}
			
		staffMemberModuleView.add(leftPanel, new CC().span().grow());		
		
		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			// Holiday Label
			JLabel startDateLbl = new JLabel("Add Holiday: ");
			rightPanel.add(startDateLbl);
			// Holiday Component
			holidayFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
			rightPanel.add((Component) holidayFld, new CC().wrap());
			
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
				"Are you sure that you wish to remove " + holidays.get(holidayTable.getSelectedRow()) + "?", 
				"Are you sure?", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			StaffMemberDMO.getInstance().removeHoliday(staffMember, holidays.get(holidayTable.getSelectedRow()));
			hM.removeRow(holidays.get(holidayTable.getSelectedRow())); // use the removeRow method in ATM
		}
		
	}
	
	/** add
	 * Adds a Holiday
	 * TODO need to check if limit reached and for duplicate holiday
	 */
	public void add()
	{
		StaffMemberDMO.getInstance().addHoliday(staffMember, (Date) holidayFld.getModel().getValue());
		hM.addRow((Date) holidayFld.getModel().getValue());
	}

}
