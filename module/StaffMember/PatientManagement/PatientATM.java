/**
 * 
 */
package module.StaffMember.PatientManagement;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import object.Patient;

import object.PermanentPatient;

/**
 * @author VJ
 *
 */
public class PatientATM extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"NHS Number", "First Name", "Last Name", "Sex", "Address", "Phone", "Date of Birth"};
	private List<Patient> data;
	
	public PatientATM(List<Patient> d)
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
    			return ((PermanentPatient)data.get(row)).getNHSNumber();
    		case 1:
    			return data.get(row).getFirstName();
    		case 2:
    			return data.get(row).getLastName();
    		case 3:
    			return data.get(row).getSex();
    		case 4:
    			return data.get(row).getAddress();
    		case 5:
    			return data.get(row).getPhone();
    		case 6:
    			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    			return fm.format(data.get(row).getDob());
    		
    	}
    	return null;
    }	
	
}
