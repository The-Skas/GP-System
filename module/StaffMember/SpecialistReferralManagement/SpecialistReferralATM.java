/**
 * 
 */
package module.StaffMember.SpecialistReferralManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.ReferralObject;


/**
 * @author VJ
 *
 */
public class SpecialistReferralATM extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"ID", "Start Time", "End Time", "Type"};
	private List<ReferralObject> data;
	
	public SpecialistReferralATM(List<ReferralObject> d)
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
    	// TODO getValueAt SpecialistReferralATM
    	return null;
    }	
	
}
