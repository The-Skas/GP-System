/** AbsenceATM 
 * Table Model for Absences
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.AbsenceManagement;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AbsenceATM extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private String[]			columnNames			= { "Date" };
	private List<Date>			data;

	/** AbsenceATM Constructor 
	 * Creates a Table Model for a given List of Dates that represent Absences
	 * @param d
	 */
	public AbsenceATM(List<Date> d) {
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
		return data.get(row);
	}

}

/**
 * End of File: AbsenceATM.java 
 * Location: module/StaffMember/AbsenceManagement
 */
