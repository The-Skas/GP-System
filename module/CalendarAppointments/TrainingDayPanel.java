package module.CalendarAppointments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


  public class TrainingDayPanel {
      
        JLabel l = new JLabel("", JLabel.CENTER);
        JDialog d;
             
        Calendar cal = Calendar.getInstance();                  

    public TrainingDayPanel(String day) throws ParseException {
     

    SimpleDateFormat sDF = new SimpleDateFormat("yyyy/MM/dd");      
     
     Color blueishInk = new Color(69, 65, 107);
         
        // convert a String to a Date
        Date date = sDF.parse(day);
        
        // convert a Date to a String
        DateFormat sDF1 = new SimpleDateFormat("EEE dd MMMM yyyy");
        String s = sDF1.format(date);    
        
        d = new JDialog();
        d.setModal(true);
         
         JPanel p = new JPanel();
         p.setPreferredSize(new Dimension(460, 30));
         p.setBackground(Color.WHITE);
         
         JLabel test = new JLabel("<html><b>"+s+"</b></html>");
         test.setForeground(blueishInk);
         p.add(test);

         JPanel p1 = new JPanel();
         p1.setPreferredSize(new Dimension(460, 50));
         p1.setBackground(Color.WHITE);
         
         JLabel holidayLabel = new JLabel("<html><div style=\"text-align: center;\"><b>Holiday / Training Day <br> There are no surgery hours on holidays/training days.</b></html>!");
         holidayLabel.setForeground(blueishInk);
         p1.add(holidayLabel);
         
         d.add(p, BorderLayout.NORTH);
         d.add(p1, BorderLayout.SOUTH);
         d.pack();
         d.setLocationRelativeTo(null);
         d.setTitle("View Daily Appointments");
         d.setVisible(true);
 }
}