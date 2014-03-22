/** TaxFormATM 
 * Table Model for Tax Forms
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.TaxFormManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.TaxForm;

public class TaxFormATM extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private String[]			columnNames			= { "Tax Office", "Tax Code", "Salary" };
	private List<TaxForm>		data;

	/** TaxFormATM Constructor
	 * Creates a Table Model for a given List of Tax Forms
	 * @param d
	 */
	public TaxFormATM(List<TaxForm> d) {
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
		switch (col) {
			case 0:
				return data.get(row).getTaxOffice().getName();
			case 1:
				return new String(data.get(row).getTaxNumber());
			case 2:
				return data.get(row).getSalary();
		}
		return data.get(row);
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
	public void addRow(TaxForm o) {
		this.data.add(o);
		this.fireTableChanged(null);
	}

	/** removeRow 
	 * remove a Speciality Object from the Table and refresh the Table
	 * 
	 * @param o
	 */
	public void removeRow(TaxForm o) {
		this.data.remove(o);
		this.fireTableChanged(null);
	}

}

/**
 * End of File: TaxFormATM.java 
 * Location: module/StaffMember/TaxFormManagement
 */
