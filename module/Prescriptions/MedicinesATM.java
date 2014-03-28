/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package module.Prescriptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import object.Medicine;
import object.StaffMember;
import object.MedicalCondition;

/**
*
 * @author oa305
 */
public class MedicinesATM extends AbstractTableModel{
    // Setting up the coloums name of the table
	public String[] columnNames = {"Name", "Description", "Relevant Amount"};
	private List<Medicine> data;    
    
    public MedicinesATM(List<Medicine> data)
    {
        this.data = data;       
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
    
    @Override
    public Object getValueAt(int row, int col) {
        System.out.println("Im here G"+this.data);
        Medicine m = data.get(row);
        switch (col)
        {
                case 0:
                        return m.getName();
        	case 1:
                        return m.getDescription();
                case 2:
                        return m.getRelevant_amount();
               
        }
        return null;
    }
    
	public Class<?> getColumnClass(int c) {
	    return getValueAt(0, c).getClass();
	}	
	
        public List<Medicine> getData()
        {
            return this.data;
        }
	
	/** addRow
	 * add a Medicine Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(Medicine o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Medicine Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(Medicine o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}    

    public void addRow(MedicalCondition MC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
