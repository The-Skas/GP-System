/**
 * 
 */
package module.StaffMember;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.StaffMember;

/**
 * @author VJ
 *
 */
public class StaffMemberATM extends AbstractTableModel {


	private static final long serialVersionUID = 8897868375348957174L;
	private String[] columnNames = {"Username", "First Name", "Last Name", "Full Time", "Start Date", "Office Manager", "Role", "Holiday Allowance"};
	private List<StaffMember> data;
	
	public StaffMemberATM(List<StaffMember> d)
	{
		this.data = d;
	}
	 
    public int getColumnCount() {
        return columnNames.length;
    }
	 
    public int getRowCount() {
        return data.size();
    }
	 
    public String getColumnName(int col) {
        return columnNames[col];
    }
	 
    public Object getValueAt(int row, int col) {
        StaffMember sM = data.get(row);
        switch (col)
        {
        	case 0:
        		return sM.getUsername();
        	case 1:
        		return sM.getFirstName();
        	case 2:
        		return sM.getLastName();
        	case 3:
        		return sM.isFullTime();
        	case 4:
        		return sM.getStartDate();
        	case 5:
        		return sM.isOfficeManager();
        	case 6:
        		return sM.getRole();
        	case 7:
        		return sM.getHolidayAllowance();
        }
        return null;
    }
	 
    /**
	 * JTable uses this method to determine the default renderer/
	 * editor for each cell.  If we didn't implement this method,
	 * then the last column would contain text ("true"/"false"),
	 * rather than a check box.
	 */
	public Class<?> getColumnClass(int c) {
	    return getValueAt(0, c).getClass();
	}	 

}
