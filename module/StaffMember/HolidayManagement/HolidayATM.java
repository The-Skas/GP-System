/**
 * 
 */
package module.StaffMember.HolidayManagement;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author VJ
 *
 */
public class HolidayATM extends AbstractTableModel {


	private static final long serialVersionUID = 8897868375348957174L;
	private String[] columnNames = {"Date"};
	private List<Date> data;
	
	public HolidayATM(List<Date> d)
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
        Date d = data.get(row);
        return d;
    }
    
	public Class<?> getColumnClass(int c) {
	    return new Date().getClass();
	}	
	
	
	/** addRow
	 * add a Staff Member Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(Date o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Staff Member Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(Date o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}

}
