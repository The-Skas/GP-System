package module.CalendarAppointments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exception.EmptyResultSetException;
import framework.GPSISFramework;
import mapper.CalendarAppointmentDMO;
import mapper.StaffMemberDMO;
import module.CalendarAppointmentsModule;
import object.CalendarAppointment;
import object.StaffMember;

    public class MonthlyView{

		Calendar cal = java.util.Calendar.getInstance(); 
    	int month = cal.get(java.util.Calendar.MONTH);
    	int year = cal.get(java.util.Calendar.YEAR);
    	JLabel l = new JLabel("", JLabel.CENTER);
    	String day = "";
    	JDialog d;
    	JButton[] button = new JButton[49];
    	Date now = new Date();
    	public static int counterHoliday = 0;
    	String doctorName;
    	StaffMember doctor;
    	
    	int doc;
    	int appNum;
    	List<CalendarAppointment> doctorAppointments;  

 public MonthlyView(final int doc) throws EmptyResultSetException {
	 
	 	this.doc = doc;
	 	doctor = StaffMemberDMO.getInstance().getById(doc);
	 	doctorName = doctor.getFirstName() + " " + doctor.getLastName();
	 
         d = new JDialog();
         d.setModal(true);
         String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
         final JPanel p1 = new JPanel(new GridLayout(7, 7));
         p1.setPreferredSize(new Dimension(960, 580));

         Color lightRed = new Color(237, 199, 199);
         Color blueishInk = new Color(69, 65, 107);
                  
         for (int x = 0; x < button.length; x++) {
                 final int selection = x;
                 button[x] = new JButton();
                 button[x].setFocusPainted(false);
                 if(x == 0 || x == 6) // in case i want every sat and sun to be red - ...|| x == 7 || x == 13 || x == 14 || x == 20 || x == 21 || x == 27 || x == 28 || x == 34 || x == 35 || x == 41 || x == 42 || x == 48
                 button[x].setBackground(lightRed);
                 else
                	 button[x].setBackground(Color.white);
                 if (x > 6)
                         button[x].addActionListener(new ActionListener() {
                                 public void actionPerformed(ActionEvent ae) {
                                         day = button[selection].getActionCommand();
                                         
                                         // create new DailyView
                                         
                                         if (day.equals(""))
                                        	 d.dispose(); // if clicked on an empty button
                                         
                                         try { 
                                        	 
                                             java.util.Calendar cal = java.util.Calendar.getInstance();
                                             cal.set(year, month, Integer.parseInt(day));
                                             
                                         	 Date appDate = cal.getTime();
                                         	 
                                         	 SimpleDateFormat format = new SimpleDateFormat("EEE");
                                         	 SimpleDateFormat sDF = new SimpleDateFormat("yyyy/MM/dd");    
                                         	 
                                         	 String appString = format.format(appDate);
                                         	 String appDateString = sDF.format(appDate);
                                         	
                                         		//System.out.println("appString is: "+appString);
                                                        counterHoliday++;
                                         		if(GPSISFramework.getInstance().isHoliday(appDate))
                                         			new TrainingDayPanel(appDateString);
                                         		else if(appString.equals("Sat"))
                                         			new DailyViewSaturday(doc, appDateString);
                                         		else if(appString.equals("Sun"))
                                         			JOptionPane.showMessageDialog( p1, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
                                         		else
                                         			new DailyView(doc, appDateString);
                     					} catch (NumberFormatException | ParseException e) {
                     						// TODO Auto-generated catch block
                     						e.printStackTrace();
                     					}
                                         
                                        // d.dispose();
                                 }
                         });
                 if (x < 7) {
                         button[x].setText(header[x]);
                         if(header[x].equals("Sun") || header[x].equals("Sat"))
                         button[x].setForeground(Color.black);
                         else
                         {
                        	 button[x].setForeground(blueishInk);
                        	 button[x].setFont(new Font("SansSerif", Font.BOLD+Font.ITALIC, 12));
                         }                  
                 }
                 p1.add(button[x]);
         }
         
         JPanel p2 = new JPanel(new GridLayout(1, 3));
         p2.setPreferredSize(new Dimension(1024, 30));
         JButton previous = new JButton("<< Previous");
         previous.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent ae) {
                         month--;
                         displayDate();
                 }
         });
         p2.add(previous);
         p2.add(l);
         JButton next = new JButton("Next >>");
         next.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent ae) {
                         month++;
                         displayDate();
                 }
         });
         p2.add(next);
         d.add(p2, BorderLayout.NORTH);
         d.add(p1, BorderLayout.CENTER);
         d.pack();
         d.setLocationRelativeTo(null);
         displayDate();
         d.setVisible(true);
 }
 
 public void displayDate() {
     for (int x = 7; x < button.length; x++){
        button[x].setText("");
     	button[x].setBackground(Color.WHITE); 
     }
     java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                     "MMMM yyyy");
     java.util.Calendar cal = java.util.Calendar.getInstance();
     cal.set(year, month, 1);
     
     
     int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
     int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
     
     
     java.util.Calendar cal2 = java.util.Calendar.getInstance();
     java.text.SimpleDateFormat dateF = new java.text.SimpleDateFormat(
                     "dd MMMM yyyy");
     Date today = new Date();
     cal2.setTime(today);
     //System.out.println(dateF.format(today));
     
     DateFormat dayF = new SimpleDateFormat("dd");
     DateFormat monthF = new SimpleDateFormat("MM");
     DateFormat yearF = new SimpleDateFormat("yyyy");
     
     String nowDayString = dayF.format(now);
     String nowMonthString = monthF.format(now);
     String nowYearString = yearF.format(now);
     
     int nowDayInt = Integer.parseInt(nowDayString);
     int nowMonthInt = Integer.parseInt(nowMonthString);
     int nowYearInt = Integer.parseInt(nowYearString);
      
     java.util.Calendar cal1 = java.util.Calendar.getInstance();
     //SimpleDateFormat sDF = new SimpleDateFormat("yyyy/MM/dd");
     
     for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
     {
             
             button[x].setActionCommand("" + day);
             
             cal1.set(year, month, day);
             Date appDate = cal1.getTime();
             
     	    try { 
                                counterHoliday++;
				this.doctorAppointments = CalendarAppointmentDMO.getInstance().getAppointmentsOfDoctorIdByDay(this.doc, appDate);
    			this.appNum = doctorAppointments.size(); // get number of appointments for each particular day
				
    		} catch (EmptyResultSetException e) {
    			System.out.println("No Appointments");
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
             
             
            // System.out.println(nowDayInt+" "+nowMonthInt+" "+nowYearInt);
            // System.out.println(day+" "+month+" "+year);
     	    
     	     // no appointments on sundays
     	     SimpleDateFormat format = new SimpleDateFormat("EEE");
     	     String appString = format.format(cal1.getTime());
             
     	     // make training days visible on monthly view
             counterHoliday++;
     	     if(GPSISFramework.getInstance().isHoliday(appDate))
     	     button[x].setText("<html><div style=\"text-align: center;\"><b>" + day + "</b><br> Holiday or Training Day!</html>");
     	     else if(!appString.equals("Sun"))
             button[x].setText("<html><div style=\"text-align: center;\"><b>" + day + "</b><br> Appointments: " + appNum + "</html>");
     	     else if(appString.equals("Sun")){
     	     button[x].setText("<html><b>"+day+"</b></html>");
     	     }
             
             if(nowDayInt == day && nowMonthInt-1 == month && nowYearInt == year){ // if it is today
            	 Color todayYellow = new Color(177, 229, 237);
				button[x].setBackground(todayYellow);
            	 //System.out.println("TODAY IS THE DAY!");
            	// button[x].setText("<html>" + day + "<br> Today! </html>");
             }
             
     }  
     System.out.println("Amount of times called is : "+ counterHoliday);
     l.setText("<html><div style=\"text-align: center;\"><b>" + sdf.format(cal.getTime()) + "</b><br> Monthly appoinments of doctor <b>"+doctorName+"</b></html>");     
     d.setTitle("Date Picker");
}

public String setPickedDate() {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
            "yyyy/MM/dd");
     if (day.equals(""))
             return sdf.format(now); // if clicked on an empty button

     java.util.Calendar cal = java.util.Calendar.getInstance();
     cal.set(year, month, Integer.parseInt(day));
     return sdf.format(cal.getTime());
}
    }