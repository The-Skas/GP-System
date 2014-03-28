package module.CalendarAppointments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import object.TrainingDay;
import mapper.CalendarAppointmentDMO;
import mapper.TrainingDayDMO;
import module.CalendarAppointmentsModule;

    public class TrainingDaysPicker{

		Calendar cal = java.util.Calendar.getInstance(); 
    	int month = cal.get(java.util.Calendar.MONTH);
    	int year = cal.get(java.util.Calendar.YEAR);
    	JLabel l = new JLabel("", JLabel.CENTER);
    	String day = "";
    	JDialog d;
    	JButton[] button = new JButton[49];
    	Date now = new Date();

 public TrainingDaysPicker() {
         d = new JDialog();
         d.setModal(true);
         String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
         JPanel p1 = new JPanel(new GridLayout(7, 7));
         p1.setPreferredSize(new Dimension(630, 320));

         Color lightRed = new Color(237, 199, 199);
         Color blueishInk = new Color(69, 65, 107);
         
         for (int x = 0; x < button.length; x++) {
                 final int selection = x;
                 button[x] = new JButton();
                 button[x].setFocusPainted(false);
                 if(x == 0 || x == 6 || x == 7 || x == 13 || x == 14 || x == 20 || x == 21 || x == 27 || x == 28 || x == 34 || x == 35 || x == 41 || x == 42 || x == 48)
                 button[x].setBackground(lightRed);
                 else
                	 button[x].setBackground(Color.white);
                 if (x > 6)
                         button[x].addActionListener(new ActionListener() {
                                 public void actionPerformed(ActionEvent ae) {
                                         day = button[selection].getActionCommand();
                                         
                                         if (day.equals(""))
                                        	 d.dispose();
                                         
                         				if (JOptionPane.showConfirmDialog(d, "Are you sure you want to set this day as a Training Day?", "Are you sure?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                        				{
                                            
                                            java.util.Calendar cal = java.util.Calendar.getInstance();
                                            cal.set(year, month, Integer.parseInt(day));
                                            
                                            Date trainingDay = cal.getTime();
                                            
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
                                            String trainingDayString = sdf.format(trainingDay);
                                            
                                            /*
                                            // append trainingDayString to the end of the holidays.txt
                                            CalendarAppointmentsModule.openFile();
                                            
                                            CalendarAppointmentsModule.append(trainingDayString);
                                            
                                            CalendarAppointmentsModule.closeFile();
                                            
                                            // add the training days to the volatile publicHolidays
                                            CalendarAppointmentsModule.publicHolidays.add(trainingDay);
                                            
                                            // add that date to holidays and training days ArrayList                                           
                                             */
                                            
                                            // add selected Date to TrainingDayDMO
                                            
                                    		TrainingDayDMO holiTbl=TrainingDayDMO.getInstance();
                                    		
                                    		holiTbl.put(new TrainingDay(trainingDay));
                         					
                        					JOptionPane.showMessageDialog( d, "Day set as Training Day.");
                        				}
                                         
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
     	button[x].setBackground(Color.WHITE); // had a weird bug with the color here!
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
     System.out.println(dateF.format(today));
     
     DateFormat dayF = new SimpleDateFormat("dd");
     DateFormat monthF = new SimpleDateFormat("MM");
     DateFormat yearF = new SimpleDateFormat("yyyy");
     
     String nowDayString = dayF.format(now);
     String nowMonthString = monthF.format(now);
     String nowYearString = yearF.format(now);
     
     int nowDayInt = Integer.parseInt(nowDayString);
     int nowMonthInt = Integer.parseInt(nowMonthString);
     int nowYearInt = Integer.parseInt(nowYearString);
          
     for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
     {
             button[x].setText("" + day);
             
             System.out.println(nowDayInt+" "+nowMonthInt+" "+nowYearInt);
             System.out.println(day+" "+month+" "+year);
             
             if(nowDayInt == day && nowMonthInt-1 == month && nowYearInt == year){
            	 Color todayYellow = new Color(177, 229, 237);
				button[x].setBackground(todayYellow);
            	 System.out.println("TODAY IS THE DAY!");
             }
     }  
     l.setText(sdf.format(cal.getTime()));
     d.setTitle("Date Picker");
}

public String setPickedDate() {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
            "yyyy/MM/dd");
     if (day.equals(""))
             return sdf.format(now);

     java.util.Calendar cal = java.util.Calendar.getInstance();
     cal.set(year, month, Integer.parseInt(day));
     return sdf.format(cal.getTime());
}
    }