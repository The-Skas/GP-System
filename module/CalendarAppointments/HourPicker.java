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

      class HourTable {
        JLabel l = new JLabel("", JLabel.CENTER);
        JDialog d;
        JButton[] button = new JButton[15];

 public HourTable(JFrame parent) {
         d = new JDialog();
         d.setModal(true);
         String[] header = {"10","10:30","11","11:30","12","12:30","13","13:30","14","14:30","15","15:30","16","16:30","17"};
         JPanel p1 = new JPanel(new GridLayout(15, 1));
         p1.setPreferredSize(new Dimension(330, 520));

         Color lightRed = new Color(237, 199, 199);
         Color blueishInk = new Color(69, 65, 107);
         
         for (int x = 0; x < button.length; x++) {
                 final int selection = x;
                 button[x] = new JButton();
                 button[x].setFocusPainted(false);
                 button[x].setText(header[x]);
                 button[x].setForeground(blueishInk);  
                 button[x].setBackground(Color.white);
                 p1.add(button[x]);
         }

         d.add(p1, BorderLayout.CENTER);
         d.pack();
         d.setLocationRelativeTo(parent);
         d.setVisible(true);
 }
}

  class HourPicker {
 public static void main(String[] args) {
         JButton b = new JButton("Open Day");
         JPanel p = new JPanel();
         p.add(b);
         final JFrame f = new JFrame();
         f.getContentPane().add(p);
         f.pack();
         f.setVisible(true);
         b.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent ae) {
                         new HourTable(f);
                 }
         });
 }

  }
