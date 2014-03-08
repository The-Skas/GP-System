/**
 * 
 */
package module.StaffMember.PrescriptionManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.Prescription;


/**
 * @author VJ
 *
 */
public class PrescriptionATM extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"ID", "Start Time", "End Time", "Type"};
	private List<Prescription> data;
	
	public PrescriptionATM(List<Prescription> d)
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
    	// TODO getValueAt PrescriptionATM
    	return null;
    }	
	
}
