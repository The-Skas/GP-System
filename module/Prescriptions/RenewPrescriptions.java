/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package module.Prescriptions;


import framework.GPSISPopup;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class RenewPrescriptions extends GPSISPopup
        {
            JPanel pane2 = new JPanel();
            JTextField searchName;
            JTextField  searchpatientID;
            FlowLayout fl;

        public RenewPrescriptions()
        {
                      
            super("Renewing Prescriptions");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(450,400);
            setLocationRelativeTo(null);
            Container con = this.getContentPane();
            con.add(pane2);

            
            pane2.setLayout(new FlowLayout((FlowLayout.LEFT)));           
            
            
            searchName = new JTextField("Enter name",20);
            searchpatientID = new JTextField("Enter patient ID");
 
            
            
            pane2.add(searchName);
            pane2.add(searchpatientID);
            setVisible(true);
            
            
        }
        
 public void actionPerformed(ActionEvent event)
        {
            
            
        }    
        
        public static void main (String [] args)
        {
            new RenewPrescriptions();
        }
            
        }