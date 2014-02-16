/**
 * 
 */
package module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import object.CalendarAppointment;
import object.RoutineAppointment;
import mapper.SQLBuilder;
import module.CalendarAppointments.AppointmentsView;
import module.CalendarAppointments.DatePicker;
import module.CalendarAppointments.AnalogClock;
import exception.EmptyResultSetException;
import framework.*;
import module.CalendarAppointments.AnalogClock;
// Front end of the CalendarAppointments 

public class CalendarAppointmentsModule extends GPSISModuleMain {

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */ 
	
	
	@Override
	public JPanel getModuleView() {
		
	/*
		try {
			new RoutineAppointment(new Date(), new Date(), patientDMO.getById(1), staffMemberDMO.getById(1));
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/	
		JPanel calendarAppointmentsModuleView = new JPanel(new GridBagLayout());
		calendarAppointmentsModuleView.setBackground(Color.WHITE);
		
		// make buttons
		JButton showCalendar = new JButton("View Calendar");
		JButton showAppointments = new JButton("Show all appointments");
		
		/*
		JLabel greeting = new JLabel("This is the Calendar Appointments Module Main View in module/CalendarAppointmentsModule.java!");
			greeting.setFont(new Font("SansSerif", Font.BOLD, 20));
		*/
		
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			gbC.fill = GridBagConstraints.HORIZONTAL;
			
			calendarAppointmentsModuleView.add(showCalendar, gbC);
			calendarAppointmentsModuleView.add(showAppointments, gbC);
			
			
			//calendarAppointmentsModuleView.add(l, c);
			
			//JLabel label = new JLabel("A testing label");
			//calendarAppointmentsModuleView.add(label);
			final JFrame f = new JFrame();
			
            AnalogClock clock = new AnalogClock();
            clock.setPreferredSize(new Dimension(100,100));
            
            calendarAppointmentsModuleView.add(clock, gbC);
        
        /*
            f.getContentPane().add(clock);
            f.pack();
            f.setVisible(true);
		*/	
	         showCalendar.addActionListener(new ActionListener() {
	                 public void actionPerformed(ActionEvent ae) {
	                         new DatePicker(f);
	                 }
	         });
	         
	         showAppointments.addActionListener(new ActionListener() {
	        	 public void actionPerformed(ActionEvent ae) {
	        		 new AppointmentsView();
	        	 }
	         });
	         
	         
		
		return calendarAppointmentsModuleView;
	}
}
