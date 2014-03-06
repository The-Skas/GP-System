/**
 * 
 */
package module.StaffMember.AvailabilityManagement;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.Register;

/**
 * @author VJ
 *
 */
public class AvailabilityATM extends AbstractTableModel {


	private static final long serialVersionUID = 8897868375348957174L;
	private String[] columnNames = {"Date", "Availability"};
	private List<Register> data;
	
	public AvailabilityATM(List<Register> d)
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
    	switch(col)
    	{
			case 0:
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				return fm.format(data.get(row).getDate());
			case 1:
				return data.get(row).getReadableAvailability();
    	}
		return null;
    }	
	
	/** addRow
	 * add a Staff Member Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(Register o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	public void swapRow(Register o, Register o1)
	{
		this.data.set(this.data.indexOf(o), o1);
		this.fireTableChanged(null);
	}

}
