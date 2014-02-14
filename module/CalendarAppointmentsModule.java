/**
 * 
 */
package module;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import module.CalendarAppointments.DatePicker;
import module.CalendarAppointments.AnalogClock;
import framework.GPSISModuleMain;

// Front end of the CalendarAppointments 

public class CalendarAppointmentsModule extends GPSISModuleMain {

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */ 
	
	
	@Override
	public JPanel getModuleView() {
		
		JPanel calendarAppointmentsModuleView = new JPanel(new GridBagLayout());
		calendarAppointmentsModuleView.setBackground(Color.WHITE);
		
		// make buttons
		JButton makeAppointment = new JButton("Schedule a new appointment");
		
		JLabel l = new JLabel("Yohoho");
		
		/*
		JLabel greeting = new JLabel("This is the Calendar Appointments Module Main View in module/CalendarAppointmentsModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
		*/
		
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			gbC.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.FIRST_LINE_END;
			
			calendarAppointmentsModuleView.add(makeAppointment, gbC);
			calendarAppointmentsModuleView.add(l, c);
			
			//JLabel label = new JLabel("A testing label");
			//calendarAppointmentsModuleView.add(label);
			final JFrame f = new JFrame();
			
	         makeAppointment.addActionListener(new ActionListener() {
	                 public void actionPerformed(ActionEvent ae) {
	                         new DatePicker(f);
	                 }
	         });
		
		return calendarAppointmentsModuleView;
	}


}
