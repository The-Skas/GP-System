/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package module.Prescriptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import object.Prescription;
import object.StaffMember;
import object.MedicalCondition;

/**
*
 * @author oa305
 */
public class PrescriptionsATM extends AbstractTableModel{
    // Setting up the coloums name of the table
    /// Would be shown in the PrescriptionsModuleView 
	public String[] columnNames = {"Reference Number","First Name","Last Name", "Start Date", "Expiary Date","Medicines","Medical Conditions", "Doctor Name"};
	private List<Prescription> data;    
    
    public PrescriptionsATM(List<Prescription> data)
    {
        this.data = data;       
    }
    
    public List<Prescription>getData()
    {
        return data;
    }    
       
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
            return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }    
    // for each perscription show these attributes on the table. 
    @Override
    public Object getValueAt(int row, int col) {
        if(data.isEmpty())
        {
            return null;
        }
        Prescription pM = data.get(row);
    //    System.out.println("Prescription ID: " + pM.getId());
      //  System.out.println("Patient: "+pM.getPatient());
        switch (col)
        {
                case 0:
                        return pM.getId()+"";
        	case 1:
                        return pM.getPatient().getFirstName();
                case 2:
                        return pM.getPatient().getLastName();
                case 3: 
                        return pM.getStartDate();
                case 4:
                        return pM.getendDate(); 
                case 5:
                        return pM.getlistofMedicine();
                case 6:
                        return pM.getMedicalCondition();
                case 7:
                        return pM.getDoctor().getName();

        }
        return null;
    }
    
	public Class<?> getColumnClass(int c) {
            if(data.isEmpty())
            {
                return "".getClass();
            }
	    return getValueAt(0, c).getClass();
	}	
	
	
	/** addRow
	 * add a Prescription Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(Prescription o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Prescription Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(Prescription o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}    
    
}
