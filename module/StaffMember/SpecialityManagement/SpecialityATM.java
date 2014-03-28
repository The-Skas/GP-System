/** SpecialityATM 
 * Table Model for Specialities
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.SpecialityManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.Speciality;

public class SpecialityATM extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private String[]			columnNames			= { "Name" };
	private List<Speciality>	data;

	/** SpecialityATM Constructor
	 * Creates a Table Model for a given List of Speciality objects
	 * @param d
	 */
	public SpecialityATM(List<Speciality> d) {
		this.data = d;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return data.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		return data.get(row).getName();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int c) {
		return columnNames[c].getClass();
	}

	/** addRow 
	 * add a Speciality Object to the Table and refresh the Table
	 * 
	 * @param o
	 */
	public void addRow(Speciality o) {
		this.data.add(o);
		this.fireTableChanged(null);
	}

	/** removeRow 
	 * remove a Speciality Object from the Table and refresh the Table
	 * 
	 * @param o
	 */
	public void removeRow(Speciality o) {
		this.data.remove(o);
		this.fireTableChanged(null);
	}

}

/**
 * End of File: SpecialityATM.java 
 * Location: module/StaffMember/SpecialityManagement
 */