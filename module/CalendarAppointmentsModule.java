/**
 * 
 */
package module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import module.CalendarAppointments.AddAppointment;
import module.CalendarAppointments.AddRoutine;
import module.CalendarAppointments.AppointmentsView;
import module.CalendarAppointments.DailyView;
import module.CalendarAppointments.DailyViewSaturday;
import module.CalendarAppointments.DatePicker;
import module.CalendarAppointments.AnalogClock;
import framework.*;


public class CalendarAppointmentsModule extends GPSISModuleMain implements ActionListener {
	
	static	Calendar cal = java.util.Calendar.getInstance(); 
	
	Date now = new Date();
	
	@Override
	public JPanel getModuleView() {
		
		JPanel calendarAppointmentsModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		calendarAppointmentsModuleView.setBackground(Color.WHITE);
		
		// make buttons
		JButton addAppointment = new JButton("Add a new Appointment");
		JButton addRoutine = new JButton("Add a Routine Appointment");
		JButton showCalendar = new JButton("View Calendar");
		JButton showAppointments = new JButton("Show all appointments");
		
		// testing DailyView
		JButton selectDoctor = new JButton("Select Doctor"); // using it as a label atm
		final JTextField doctorId = new JTextField(5);
		JButton dailyView = new JButton("View/Edit daily appointments");
		final JTextField dateField = new JTextField(10);
		final DateFormat yMD = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat dMY = new SimpleDateFormat("dd/MM/yyyy");
		dateField.setText(yMD.format(now));
		//Date appDate = now;
		//final String appString = "";
		final DateFormat sundayChecker = new SimpleDateFormat("EEE");
				
		//appString = sundayChecker.format(appDate);
		
        dailyView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                    try { 
                    	Date appDate = yMD.parse(dateField.getText());
           			 String appString = sundayChecker.format(appDate);
                    	
                    		System.out.println("appString is: "+appString);
                    		if(appString.equals("Sat"))
                    			new DailyViewSaturday(Integer.parseInt(doctorId.getText().trim()), dateField.getText());
                    		else
                    			new DailyView(Integer.parseInt(doctorId.getText().trim()), dateField.getText());
					} catch (NumberFormatException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
    });
		
		calendarAppointmentsModuleView.add(selectDoctor);
		calendarAppointmentsModuleView.add(doctorId);
		calendarAppointmentsModuleView.add(showCalendar);
		calendarAppointmentsModuleView.add(dateField);
		calendarAppointmentsModuleView.add(dailyView, new CC().wrap());
		// end testing DailyView	
		
			calendarAppointmentsModuleView.add(addAppointment);
			calendarAppointmentsModuleView.add(addRoutine);			
			calendarAppointmentsModuleView.add(showAppointments);
			
            AnalogClock clock = new AnalogClock();
            clock.setPreferredSize(new Dimension(69,69));
            
            calendarAppointmentsModuleView.add(clock);	
            
	         addAppointment.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent ae) {
                         new AddAppointment();
                 }
         });
	         
	         addRoutine.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent ae) {
                         new AddRoutine();
                 }
         });
            
	         showCalendar.addActionListener(new ActionListener() {
	                 public void actionPerformed(ActionEvent ae) {
	                	 dateField.setText(new DatePicker().setPickedDate());
	                 }
	         });
	         
	         showAppointments.addActionListener(new ActionListener() {
	        	 public void actionPerformed(ActionEvent ae) {
	        		 new AppointmentsView();
	        	 }
	         });
	         
		return calendarAppointmentsModuleView;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
