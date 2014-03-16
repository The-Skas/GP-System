/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Patient;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import module.Patient.PatientFamily.Relationship;
import object.Patient;

/**
 *
 * @author skas
 */
public class PatientFamilyATM extends AbstractTableModel {
    public String[] columnNames = {"First Name", "Last Name","Relationship"};

    private List<PatientFamily> data;
    
    public PatientFamilyATM(Patient p)
    {
        this.data = new ArrayList<>();
        
        if(p.getFather() != null) {
            this.data.add(new PatientFamily(p.getFather(), Relationship.Parent));
        }
        if(p.getMother() != null) {
            this.data.add(new PatientFamily(p.getMother(), Relationship.Parent));
        }
        
        List<Patient> siblings = p.getSiblings();
        
        for(Patient sibling : siblings)
        {
            this.data.add(new PatientFamily(sibling, Relationship.Sibling));
        }
        
        List<Patient> children = p.getChildren();
        
        for(Patient child : children)
        {
            this.data.add(new PatientFamily(child, Relationship.Child));
        }
        
        
    }
    
    public List<PatientFamily>getData()
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
        PatientFamily p=data.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0:
                return p.getPatient().getFirstName();
            case 1:
                return p.getPatient().getLastName();
            case 2:
                return p.getRelation().toString();
        }
        
        return null;
       
    }
    
    public Class<?> getColumnClass(int c) {
	    return getValueAt(0, c).getClass();
	}	
	
	
	/** addRow
	 * add a Patient Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(PatientFamily o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Patient Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(Patient o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}
        
        public PatientFamily getPatientAtRow(int i)
        {
            return this.data.get(i);
        }
        
        
    
}
