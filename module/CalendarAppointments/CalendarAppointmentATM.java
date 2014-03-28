package module.CalendarAppointments;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.CalendarAppointment;
import object.CareManagementAppointment;
import object.RoutineAppointment;

public class CalendarAppointmentATM extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L; // is it me or does this improve speed?
	//private String[] columnNames = {"ID", "Type", "Doctor", "Start Time", "End Time", "Patient(s)"};
	private String[] columnNames = {"ID", "Type", "Doctor", "Start Time", "End Time", "Patient"};
	private List<CalendarAppointment> data;

	public CalendarAppointmentATM(List<CalendarAppointment> d)
	{
		this.data = d;
		//columnNames[0].setPreferredWidth(100);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		 return data.size();
	}
	
    public String getColumnName(int col) {
        return columnNames[col];
    }
    

	@Override
	   public Object getValueAt(int row, int col) {
		
        CalendarAppointment cA = data.get(row);
        
        switch (col)
        {
        	case 0:
        		return cA.getId();
        	case 1:
        		return cA.getType();
        	case 2:
        		if(cA instanceof RoutineAppointment)  
        			return ((RoutineAppointment) cA).getDoctorObject().getName();  
        		else
        			return (((CareManagementAppointment) cA).getCareProgramme().getDoctor());
        	case 3:
        		return new SimpleDateFormat("EEE MMM dd HH:mm yyyy").format(cA.getStartTime());
        	case 4:
        		return new SimpleDateFormat("EEE MMM dd HH:mm yyyy").format(cA.getEndTime());
        	case 5:
        		if(cA instanceof RoutineAppointment)
        			return ((RoutineAppointment) cA).getPatient();
        		else
        			return "See in Care Programme Module";
        		
        }
        return null;
    }
	
	public Class<?> getColumnClass(int c) {
	    return getValueAt(0, c).getClass();
	}
	
	/** addRow
	 * add a Calendar Appointment Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(CalendarAppointment o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Calendar Appointment Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(CalendarAppointment o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}

}
