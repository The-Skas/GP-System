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
    
	public Class<?> getColumnClass(int c) {
	    return getValueAt(0, c).getClass();
	}	
	
	
	/** addRow
	 * add a Staff Member Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(StaffMember o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Staff Member Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(StaffMember o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}

}
