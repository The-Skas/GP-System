package module.CalendarAppointments;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exception.EmptyResultSetException;
import mapper.CalendarAppointmentDMO;
import object.CalendarAppointment;
import object.RoutineAppointment;

  public class DailyView {
      
        JLabel l = new JLabel("", JLabel.CENTER);
       // Jframe frame;
        JFrame frame;
        JButton[] button = new JButton[34];
        String[] times = new String[34];
        String hour = "";
        
        List<CalendarAppointment> doctorAppointments;     

        Calendar cal = Calendar.getInstance();                  

    public DailyView(int doctorId, String day) throws ParseException {
     
     //this.doctorAppointments = CalendarAppointmentDMO.getInstance().getAppointmentsByDoctorId(doctorId); 
      
     List<CalendarAppointment> newList = new ArrayList<CalendarAppointment>();

    SimpleDateFormat sDF = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sDF2 = new SimpleDateFormat("HH:mm");
    
    
    
    try {
    	
		this.doctorAppointments = CalendarAppointmentDMO.getInstance().getAppointmentsOfDoctorIdByDay(doctorId, sDF.parse(day));
		
		newList = this.doctorAppointments;
		
	} catch (EmptyResultSetException e) {
		System.out.println("No Appointments");
	} catch (SQLException e) {
		e.printStackTrace();
	}
    
    /*
    // instead of this loop i need the method getAppointmentsOfDoctorIdByDay
         for(int i = 0; i<doctorAppointments.size(); i++)
         {                       
             String testingDate = sDF.format((doctorAppointments.get(i)).getStartTime());       
             if(testingDate.equals(day) && doctorAppointments.get(i) instanceof RoutineAppointment)
                 newList.add((RoutineAppointment) doctorAppointments.get(i)); // the getAppointmentsOfDoctorIdByDay returns Calendar Appointments
             																  // not only Routine...
         }
         */

     Color lightRed = new Color(237, 199, 199);
     Color blueishInk = new Color(69, 65, 107);
         
        // convert a String to a Date
        Date date = sDF.parse(day);
        
        // convert a Date to a String
        DateFormat sDF1 = new SimpleDateFormat("EEE dd MMMM yyyy");
        String s = sDF1.format(date);    
        
         frame = new JFrame();
       //  frame.setModal(true);
         
         JPanel p = new JPanel();
         p.setPreferredSize(new Dimension(460, 30));
         p.setBackground(Color.WHITE);
         
         JLabel test = new JLabel(s);
         test.setForeground(blueishInk);
         p.add(test);

         JPanel p1 = new JPanel(new GridLayout(34, 1));
         p1.setPreferredSize(new Dimension(460, 620));
         
        cal.set(Calendar.HOUR, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);        
         
        // TODO need to improve it to work for Care Management Appointments as well!
        if(!newList.isEmpty()) // there are some appointments for that day
        {
         for (int x = 0; x < button.length; x++) {
        	 
        	 //final int selection = x;
        	 
             Date d = cal.getTime();  
             cal.add(Calendar.MINUTE, 15);
             Date d1 = cal.getTime();
        	                  
                 
                 for(CalendarAppointment newListAppointment : newList)
                 {
                	 System.out.println(sDF2.format(newListAppointment.getStartTime()) + " ?= " + (sDF2.format(d.getTime())));
                	 if((sDF2.format(newListAppointment.getStartTime()).equals(sDF2.format(d.getTime())))){
                		 // appointment is found
                		 System.out.println("Appointment!"+sDF2.format(newListAppointment.getStartTime()));
                		 button[x] = new JButton();
                		 button[x].setFocusPainted(false);
                		 if (newListAppointment instanceof RoutineAppointment)                			 
                			 button[x].setText("" + sDF2.format(d.getTime()) + " - " + sDF2.format(d1.getTime()) + "   Appointment!   Patient: "+((RoutineAppointment) newListAppointment).getPatient());
                		 else
                			 button[x].setText("" + sDF2.format(d.getTime()) + " - " + sDF2.format(d1.getTime()) + "   Care Management Appointment!");
                		 button[x].setBackground(lightRed);
                		 button[x].setForeground(blueishInk);
                		 final CalendarAppointment cF = newListAppointment;
                         button[x].addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent ae) {
                                     //hour = button[selection].getActionCommand();
                            	 new ViewEditAppointment(cF);
                                    // frame.dispose();
                             }
                     });  
                         break;
                	 } else {
                		 button[x] = new DisabledJButton();
                		 button[x].setText("" + sDF2.format(d.getTime()) + " - " + sDF2.format(d1.getTime()) + "   Free   ");
                		 button[x].setBackground(Color.white);	
                		 button[x].setForeground(blueishInk);                 		 
                		 button[x].setActionCommand(sDF2.format(d.getTime()));
                	 }
                 }                                                   
                 p1.add(button[x]);
         }
        } else { // there are no appointments for the specific day
        	for (int x = 0; x < button.length; x++) {
        	 Date d = cal.getTime();  
             cal.add(Calendar.MINUTE, 15);
             Date d1 = cal.getTime();
        	 
             	 button[x] = new DisabledJButton();
                 button[x].setFocusPainted(false);
        		 button[x].setText("" + sDF2.format(d.getTime()) + " - " + sDF2.format(d1.getTime()) + "   Free   ");
        		 button[x].setBackground(Color.white);	
        		 button[x].setForeground(blueishInk); 
        		 button[x].setActionCommand(sDF2.format(d.getTime()));
                 p1.add(button[x]);
        	}
        }

         frame.add(p, BorderLayout.NORTH);
         frame.add(p1, BorderLayout.SOUTH);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setTitle("View Daily Appointments");
         frame.setVisible(true);
 }
}