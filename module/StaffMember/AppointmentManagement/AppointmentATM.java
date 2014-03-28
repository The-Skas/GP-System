/** AppointmentATM 
 * Table Model for Calendar Appointments
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.AppointmentManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.CalendarAppointment;
import object.RoutineAppointment;

public class AppointmentATM extends AbstractTableModel {

	private static final long			serialVersionUID	= 1L;
	private String[]					columnNames			= { "ID", "Start Time", "End Time", "Type" };
	private List<CalendarAppointment>	data;

	/** AppointmentATM Constructor 
	 * Creates a Table Model for a given list of Calendar Appointments
	 * @param d
	 */
	public AppointmentATM(List<CalendarAppointment> d) {
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
				return data.get(row).getId();
			case 1:
				return data.get(row).getStartTime();
			case 2:
				return data.get(row).getEndTime();
			case 3:
				return (data.get(row) instanceof RoutineAppointment ? "Routine" : "Care Management");

		}
		return null;
	}

}

/**
 * End of File: AppointmentATM.java 
 * Location: module/StaffMember/AppointmentManagement
 */
