/** AvailabilityATM 
 * Table Model for Availability
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.AvailabilityManagement;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.Register;

public class AvailabilityATM extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private String[]			columnNames			= { "Date", "Availability" };
	private List<Register>		data;

	/** AvailabilityATM Constructor 
	 * Creates a Table Model for a given List of Register objects that 
	 * contain the Availability of a Staff Member
	 * @param d
	 */
	public AvailabilityATM(List<Register> d) {
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
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				return fm.format(data.get(row).getDate());
			case 1:
				return data.get(row).getReadableAvailability();
		}
		return null;
	}

	/** addRow 
	 * add a Staff Member Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(Register o) {
		this.data.add(o);
		this.fireTableChanged(null);
	}

	/** swapRow 
	 * swaps a given Row with another Object
	 * 
	 * @param o
	 *            The object representing the Row to swap out
	 * @param o1
	 *            The object to swap in
	 */
	public void swapRow(Register o, Register o1) {
		this.data.set(this.data.indexOf(o), o1);
		this.fireTableChanged(null);
	}

}

/**
 * End of File: AvailabilityATM.java 
 * Location: module/StaffMember/AvailabilityManagement
 */
