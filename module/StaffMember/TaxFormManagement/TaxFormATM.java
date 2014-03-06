/**
 * 
 */
package module.StaffMember.TaxFormManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.TaxForm;

/**
 * @author VJ
 *
 */
public class TaxFormATM extends AbstractTableModel {


	private static final long serialVersionUID = 8897868375348957174L;
	private String[] columnNames = {"Tax Office", "Tax Number", "Salary"};
	private List<TaxForm> data;
	
	public TaxFormATM(List<TaxForm> d)
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
    			return data.get(row).getTaxOffice().getName();
    		case 1:
    			return data.get(row).getTaxNumber();
    		case 2:
    			return data.get(row).getSalary();
    	}
        return data.get(row);
    }
    
	public Class<?> getColumnClass(int c) {
	    return columnNames[c].getClass();
	}	
	
	
	/** addRow
	 * add a Speciality Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(TaxForm o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Speciality Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(TaxForm o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}

}
