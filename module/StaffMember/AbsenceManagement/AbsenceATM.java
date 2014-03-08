/**
 * 
 */
package module.StaffMember.AbsenceManagement;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author VJ
 *
 */
public class AbsenceATM extends AbstractTableModel {


	private static final long serialVersionUID = 8897868375348957174L;
	private String[] columnNames = {"Date"};
	private List<Date> data;
	
	public AbsenceATM(List<Date> d)
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
    	return data.get(row);
    }	
	
	

}
