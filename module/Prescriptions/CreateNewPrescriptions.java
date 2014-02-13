/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package module.Prescriptions;

import framework.GPSISFramework;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author oa305
 */
    class CreateNewPrescriptions extends GPSISFramework implements ActionListener
{
    private JFrame gpsisCNP = new JFrame(APPTITLE + " Create New Prescription");
    private JTextField createName;
    private JTextField createPatientID;
    private JTextField putmedicine;
    private JTextField putDoctorID;
    private JTextField condition;
    
    public CreateNewPrescriptions()
    {
        this.gpsisCNP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gpsisCNP.setLayout(new GridBagLayout());
        this.gpsisCNP.setBackground(new Color(240, 240, 240));
        this.gpsisCNP.setSize(750, 250);        
    
        
        JPanel h = new JPanel(new GridBagLayout());
        JLabel hTitle = new JLabel("Create the Prescription");
        hTitle.setFont(new Font("Serif", Font.BOLD, 24));
        GridBagConstraints gbC = new GridBagConstraints();
        gbC.anchor = GridBagConstraints.CENTER;
        h.add(hTitle, gbC);        
        
        JPanel createForm = new JPanel(new GridBagLayout());
        
        JLabel nameLbl = new JLabel("Name: ");
        gbC = new GridBagConstraints();
        gbC.anchor = GridBagConstraints.LINE_START;
        gbC.fill = GridBagConstraints.NONE;
        gbC.weightx = 1;
        gbC.weighty = 1;
        gbC.gridx = 0;
        gbC.gridy = 0;
        createForm.add(nameLbl, gbC);    
        
        this.createName = new JTextField(20);
        gbC = new GridBagConstraints();
        gbC.anchor = GridBagConstraints.CENTER;
        gbC.weightx = 1;
        gbC.weighty = 1;
        gbC.gridx = 1;
        gbC.gridy = 0;
        gbC.gridwidth = 2;
        createForm.add(this.createName, gbC);
        
        /// name done
        
        JLabel conditionLbl = new JLabel("Condition: ");
        gbC = new GridBagConstraints();
        gbC.anchor = GridBagConstraints.LINE_START;
        gbC.fill = GridBagConstraints.NONE;
        gbC.weightx = 1;
        gbC.weighty = 1;
        gbC.gridx = 0;
        gbC.gridy = 0;
        createForm.add(conditionLbl, gbC);    
        
        this.condition = new JTextField(20);
        gbC = new GridBagConstraints();
        gbC.anchor = GridBagConstraints.CENTER;
        gbC.weightx = 1;
        gbC.weighty = 1;
        gbC.gridx = 1;
        gbC.gridy = 0;
        gbC.gridwidth = 2;
        createForm.add(this.condition, gbC);   
        
        //
        
        
        
        
        this.gpsisCNP.setVisible(true);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main (String [] args)
    {
        new CreateNewPrescriptions();
    }
        
}
