/**
 * 
 */
package module.StaffMember.AppointmentManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.CalendarAppointment;
import object.RoutineAppointment;

/**
 * @author VJ
 *
 */
public class AppointmentATM extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"ID", "Start Time", "End Time", "Type"};
	private List<CalendarAppointment> data;
	
	public AppointmentATM(List<CalendarAppointment> d)
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
