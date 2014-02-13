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
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 

public class createPrescriptions extends GPSISPopup implements ActionListener
{
    JPanel pane3 = new JPanel();
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    JTextField searchName;
    JTextField  searchpatientID;
    JTextField searchmedicine;
    JTextField doctorID;
    JTable searchtable;
    
    public createPrescriptions()
    {
            super("Creating new prescriptions");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400,400);
            setLocationRelativeTo(null);
            Container con = this.getContentPane();
            con.add(pane3);
             
            
            pane3.setLayout(new FlowLayout((FlowLayout.LEADING)));           

            
            searchName = new JTextField("Enter name",20);
            searchpatientID = new JTextField("Enter patient ID");
            searchmedicine = new JTextField("Enter medicine");
            doctorID = new JTextField("Enter doctor ID");
            
            pane3.add(searchName);
            pane3.add(searchpatientID);
            pane3.add(searchmedicine);
            pane3.add(doctorID);            
            setVisible(true);
    }
    
     public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textField.selectAll();
 
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
         public static void main (String [] args)
        {
            new createPrescriptions();
        }

}

                    
