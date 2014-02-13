package module.CalendarAppointments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

    class DatePicker {
    	Calendar cal = java.util.Calendar.getInstance(); 
 int month = cal.get(java.util.Calendar.MONTH);
 int year = cal.get(java.util.Calendar.YEAR);;
 JLabel l = new JLabel("", JLabel.CENTER);
 String day = "";
 JDialog d;
 JButton[] button = new JButton[49];

 public DatePicker(JFrame parent) {
         d = new JDialog();
         d.setModal(true);
         String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
         JPanel p1 = new JPanel(new GridLayout(7, 7));
         p1.setPreferredSize(new Dimension(630, 320));

         for (int x = 0; x < button.length; x++) {
                 final int selection = x;
                 button[x] = new JButton();
                 button[x].setFocusPainted(false);
                 button[x].setBackground(Color.white);
                 if (x > 6)
                         button[x].addActionListener(new ActionListener() {
                                 @Override
								public void actionPerformed(ActionEvent ae) {
                                         day = button[selection].getActionCommand();
                                         d.dispose();
                                 }
                         });
                 if (x < 7) {
                         button[x].setText(header[x]);
                         if(header[x].equals("Sun") || header[x].equals("Sat"))
                         button[x].setForeground(Color.red);
                         else
                         button[x].setForeground(Color.blue);          
                 }
                 p1.add(button[x]);
         }
         JPanel p2 = new JPanel(new GridLayout(1, 3));
         JButton previous = new JButton("<< Previous");
         previous.addActionListener(new ActionListener() {
                 @Override
				public void actionPerformed(ActionEvent ae) {
                         month--;
                         displayDate();
                 }
         });
         p2.add(previous);
         p2.add(l);
         JButton next = new JButton("Next >>");
         next.addActionListener(new ActionListener() {
                 @Override
				public void actionPerformed(ActionEvent ae) {
                         month++;
                         displayDate();
                 }
         });
         p2.add(next);
         d.add(p1, BorderLayout.CENTER);
         d.add(p2, BorderLayout.SOUTH);
         d.pack();
         d.setLocationRelativeTo(parent);
         displayDate();
         d.setVisible(true);
 }

 public void displayDate() {
         for (int x = 7; x < button.length; x++)
                 button[x].setText("");
         java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                         "MMMM yyyy");
         java.util.Calendar cal = java.util.Calendar.getInstance();
         cal.set(year, month, 1);
         int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
         int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
         for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
                 button[x].setText("" + day);
         l.setText(sdf.format(cal.getTime()));
         d.setTitle("Date Picker");
 }

 public String setPickedDate() {
         if (day.equals(""))
                 return day;
         java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                         "dd-MM-yyyy");
         java.util.Calendar cal = java.util.Calendar.getInstance();
         cal.set(year, month, Integer.parseInt(day));
         return sdf.format(cal.getTime());
 }
}

  class Picker {
 public static void main(String[] args) {
         JLabel label = new JLabel("Selected Date:");
         final JTextField text = new JTextField(20);
         JButton b = new JButton("Open Calendar");
         JPanel p = new JPanel();
         p.add(label);
         p.add(text);
         p.add(b);
         final JFrame f = new JFrame();
         f.getContentPane().add(p);
         f.pack();
         f.setVisible(true);
         b.addActionListener(new ActionListener() {
                 @Override
				public void actionPerformed(ActionEvent ae) {
                         text.setText(new DatePicker(f).setPickedDate());
                 }
         });
 }

  }
