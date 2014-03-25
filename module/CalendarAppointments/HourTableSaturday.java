package module.CalendarAppointments;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mapper.CalendarAppointmentDMO;
import object.CalendarAppointment;
import object.RoutineAppointment;

  class HourTableSaturday {
      
        JLabel l = new JLabel("", JLabel.CENTER);
        JDialog dialog;
        JButton[] button = new JButton[12];
        String[] times = new String[12];
        String hour = "";
        
        List<CalendarAppointment> doctorAppointments;     

        Calendar cal = Calendar.getInstance();                  

    public HourTableSaturday(int doctorId, String day) throws ParseException {
     
     this.doctorAppointments = CalendarAppointmentDMO.getInstance().getAppointmentsByDoctorId(doctorId);     
           
    ArrayList<RoutineAppointment> newList = new ArrayList<>();
     
    SimpleDateFormat sDF = new SimpleDateFormat("dd/MM/yyyy");      
    
         for(int i = 0; i<doctorAppointments.size(); i++)
         {                       
             String testingDate = sDF.format((doctorAppointments.get(i)).getStartTime());       
             
             if(testingDate.equals(day) && doctorAppointments.get(i) instanceof RoutineAppointment)          
                 newList.add((RoutineAppointment) doctorAppointments.get(i));

         }

     Color lightRed = new Color(237, 199, 199);
     Color blueishInk = new Color(69, 65, 107);
         
        // convert a String to a Date
       // SimpleDateFormat sDF = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sDF.parse(day);
        
        // convert a Date to a String
        DateFormat sDF1 = new SimpleDateFormat("EEE dd MMMM yyyy");
        String s = sDF1.format(date);    
        
         dialog = new JDialog();
         dialog.setModal(true);
         
         JPanel p = new JPanel();
         p.setPreferredSize(new Dimension(460, 30));
         p.setBackground(Color.WHITE);
         
         JLabel test = new JLabel(s);
         test.setForeground(blueishInk);
         p.add(test);

         JPanel p1 = new JPanel(new GridLayout(12, 1));
         p1.setPreferredSize(new Dimension(460, 260));
         
        cal.set(Calendar.HOUR, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        
        SimpleDateFormat sDF2 = new SimpleDateFormat("HH:mm");
         
        if(!newList.isEmpty()) // if there are some appointments for the specific day
        {
         for (int x = 0; x < button.length; x++) {
        	 
        	 final int selection = x;
        	 
             Date d = cal.getTime();  
             cal.add(Calendar.MINUTE, 15);
             Date d1 = cal.getTime();
        	 
                 button[x] = new JButton();
                 button[x].setFocusPainted(false);
                 for(RoutineAppointment newListAppointment : newList)
                 {
                	 if((sDF2.format(newListAppointment.getStartTime()).equals(sDF2.format(d.getTime())))){ // if the slot is already taken
                		 System.out.println("BU!"+sDF2.format(newListAppointment.getStartTime()));
                		 button[x] = new DisabledJButton();
                		 button[x].setText("" + sDF2.format(d.getTime()) + " - " + sDF2.format(d1.getTime()) + "   Busy!   Patient: "+newListAppointment.getPatient());
                		 button[x].setBackground(lightRed);
                		 button[x].setForeground(Color.black);
                		 break;
                	 } else { // if the slot is free
                		 button[x].setText("" + sDF2.format(d.getTime()) + " - " + sDF2.format(d1.getTime()) + "   This slot is available   ");
                		 button[x].setBackground(Color.white);	
                		 button[x].setForeground(blueishInk);  
                		 button[x].setActionCommand(sDF2.format(d.getTime())); // get the start time
                         button[x].addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent ae) {
                            	 System.out.println("newList is NOT empty");                            	 	 
                                     hour = button[selection].getActionCommand();
                                     dialog.dispose();
                             }
                     });
                	 }
                 }                                                   
                 p1.add(button[x]);
         }
        } else { // if there are no appointments for the specific day yet
        	for (int x = 0; x < button.length; x++) {
        	 final int selection = x;
        	 
             Date d = cal.getTime();  
             cal.add(Calendar.MINUTE, 15);
             Date d1 = cal.getTime();
        	 
                 button[x] = new JButton();
                 button[x].setFocusPainted(false);
        		 button[x].setText("" + sDF2.format(d.getTime()) + " - " + sDF2.format(d1.getTime()) + "   This slot is available   ");
        		 button[x].setBackground(Color.white);	
        		 button[x].setForeground(blueishInk);  
        		 button[x].setActionCommand(sDF2.format(d.getTime())); // get the start time
                 button[x].addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent ae) {
                    	 System.out.println("newList is empty");                    	 	
                             hour = button[selection].getActionCommand();
                             dialog.dispose();
                     }
             });
                 p1.add(button[x]);
        	}
        }

         dialog.add(p, BorderLayout.NORTH);
         dialog.add(p1, BorderLayout.SOUTH);
         dialog.pack();
         dialog.setLocationRelativeTo(null);
         dialog.setTitle("Select a Saturday Time Slot");
         dialog.setVisible(true);
 }
    
    public String setPickedHour(){
    	return hour;
    }
}
