/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Patient;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import object.Patient;
import object.PermanentPatient;

/**
 *
 * @author skas
 */
public class PatientATM extends AbstractTableModel {
    public String[] columnNames = {"First Name", "Last Name","Sex", "Date of Birth", "NHS","Post Code","Phone"};

    private List<Patient> data;
    
    public PatientATM(List<Patient> patients)
    {
        this.data = patients;
    }
    
    public List<Patient>getData()
    {
        return data;
    }
    
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Patient p=data.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0:
                return p.getFirstName();
            case 1:
                return p.getLastName();
            case 2:
                return (p.getSex() == 'f' ? "Female" : "Male");
            case 3:
                return p.getDob().toString();
            case 4:
                if(p instanceof PermanentPatient)
                    return ((PermanentPatient)p).getNHSNumber();
                else
                    return 0;
            case 5:
                return p.getPostCode();
            case 6:
                return p.getPhone();
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
	public void addRow(Patient o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Staff Member Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(Patient o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}
    
}
