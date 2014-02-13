package module.Prescriptions;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

    class MainWindow extends JFrame implements ActionListener
    {   
        JPanel pane = new JPanel();
        JPanel pane2 = new JPanel();
        JButton press1 = new JButton("Renew prescription");
        JButton press2 = new JButton("Create prescription");
        JLabel answer = new JLabel("");
        MainWindow()
        {
            super("Prescriptions");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400,100);
            setLocationRelativeTo(null);
            Container con = new Container();
            con.add(pane);
            
            press1.setMnemonic('P');
            pane.add(press1);
            pane.requestFocus();
            setVisible(true);
  
            press2.setMnemonic('P');
            pane.add(press2);
            pane.requestFocus();
            setVisible(true);          
            
            press1.addActionListener(this);
            pane.add(answer);
            pane.add(press1);
            press1.requestFocus();
            pane.setVisible(true);
         
            
            press2.addActionListener(this);
            pane.add(answer);
            pane.add(press2);
            press2.requestFocus();
            setVisible(true);
            
        }
        
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();
    
            if (source == press1)   
            {
                new RenewPrescriptions();
            }
            else if(source == press2)
            {
                new createPrescriptions();
       // JOptionPane.showMessageDialog(null,"I hear you 2!","Creating new prescritpion",
      //  JOptionPane.PLAIN_MESSAGE); 
       // setVisible(true);  // show something                
            }
            
        }
        
        public static void main (String [] args)
        {
            new MainWindow();
        }
    }  