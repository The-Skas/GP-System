/**
 * 
 */
package module.StaffMember;

import javax.swing.table.AbstractTableModel;

/**
 * @author VJ
 *
 */
public class StaffMemberATM extends AbstractTableModel {


	private static final long serialVersionUID = 8897868375348957174L;
	private String[] columnNames = {"Username", "First Name", "Last Name", "Full Time", "Start Date", "Office Manager", "Role", "Holiday Allowance"};
	private Object[][] data;
	
	public StaffMemberATM(Object[][] d)
	{
		this.data = d;
	}
	 
    public int getColumnCount() {
        return columnNames.length;
    }
	 
    public int getRowCount() {
        return data.length;
    }
	 
    public String getColumnName(int col) {
        return columnNames[col];
    }
	 
    public Object getValueAt(int row, int col) {
        return data[row][col];
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
