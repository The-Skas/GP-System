/**
 * 
 */
package module.StaffMember.SpecialityManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.Speciality;

/**
 * @author VJ
 *
 */
public class SpecialityATM extends AbstractTableModel {


	private static final long serialVersionUID = 8897868375348957174L;
	private String[] columnNames = {"Name"};
	private List<Speciality> data;
	
	public SpecialityATM(List<Speciality> d)
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
        return data.get(row).getName();
    }
    
	public Class<?> getColumnClass(int c) {
	    return columnNames[c].getClass();
	}	
	
	
	/** addRow
	 * add a Speciality Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(Speciality o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Speciality Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(Speciality o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}

}
