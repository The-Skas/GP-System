package module.CalendarAppointments;

//import AppointmentsView;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import mapper.CalendarAppointmentDMO;
import framework.GPSISDataMapper;
import framework.GPSISModuleMain; //why import this?
import mapper.SQLBuilder;
import object.CalendarAppointment;
import object.CareManagementAppointment;
import object.StaffMember;
import object.RoutineAppointment;
import exception.EmptyResultSetException;


public class AppointmentsView {

	
	private Set<CalendarAppointment> getAllAppointments(){
	try {
		Set<CalendarAppointment> ca = (Set<CalendarAppointment>)CalendarAppointmentDMO.getInstance().getAll();
		// HashSet<StaffMember> staffMembers = (HashSet<StaffMember>)staffMemberDMO.getAll(); // why use HashSet instead of Set?
		return ca;
	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
}
	
	
	//constructor
	public AppointmentsView(){
				
		//get a set of all appointments
		//once this is done, filter the results by doctor, day, patients
		Set<CalendarAppointment> allAppointments = getAllAppointments();

        // The data used as the titles for the table.
        String[] title = {"Appointment ID", "Type", "Doctor", "Start Time", "End Time", "Patients"};

        // The data used in the table, placed as a multidimensional array.
        Object[][] data = new Object[allAppointments.size()][6];
        
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        int i = 0;
        
        for (CalendarAppointment appointment : allAppointments)
        {
        	data[i][0] = appointment.getId();
        				if(appointment.isRoutine())        					
            data[i][1] = "Routine Appointment";      					
            			else
            data[i][1] = "Care Management Appointment";	
        				if(appointment.isRoutine())        					
        	data[i][2] = ((RoutineAppointment) appointment).getDoctor();      					
        				else
        	data[i][2] = (((CareManagementAppointment) appointment).getCareProgramme().getDoctor());
        	data[i][3] =  appointment.getStartTime();  
        	data[i][4] =  appointment.getEndTime();       	
        				if(appointment.isRoutine())        					
            	data[i][5] = ((RoutineAppointment) appointment).getPatient();      					
            			else
            	data[i][5] = null; //((CareManagementAppointment) appointment.getCareProgramme().getPatientsOrSomethingLikeThat());
        	
        	i++;
        }

        // Table instantiated using the two sets of data.
        JTable table = new JTable(data, title);
        
        // Make table uneditable
        table.setEnabled(false);
        
        // The table displayed in a Scrollpane.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 550));
    
        JPanel totalGUI = new JPanel();
        totalGUI.add(scrollPane);
        totalGUI.setOpaque(true);
        
        // JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("List of all appointments");

        //Create and set up the ContentPane
        frame.setContentPane(totalGUI);
        
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
		
		//createAndShowGUI();
		
	}
}