/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Prescriptions;

import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import framework.GPSISFramework;
import framework.GPSISPopup;
import java.awt.Color;
import java.awt.Component;
import static java.awt.FlowLayout.LEADING;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import module.Prescriptions.MedicineInformation;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import mapper.PatientDMO;
import mapper.PrescriptionDMO;
import module.Broadcastable;
import module.Patient.ViewPatient;
import module.PrescriptionsModule;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import object.Medicine;
import object.Prescription;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author ozhan azizi
 */
public class ViewAndPrintPrescription extends GPSISPopup implements ActionListener,Broadcastable{

    private static final long serialVersionUID = -8748112836660009010L;
    
    private JPanel viewPrescriptionForm;
    
    private JTextField firstNameFld;
    private JTextField lastNameFld;
    private JTextField addressFld;
    private JTextField postCodeFld;
    private JTextField medicalConditionFld;
    //medicine condition
    // medicine
    private JTextField freqFld;
    private JTextField startDateFld;
    private JTextField expiaryDateFld;
    private JTextField prescriptionValid;
    private JTextField payOrFreeFld;    
    private JTextField doctorNameFld;   
    private JButton showMedicine;
    private JButton printPrescriptionFld;  
    private JButton removePrescription;
    
    Prescription currentPrescription;
    Medicine m;
    
    public ViewAndPrintPrescription(Prescription p) {
        super("View and Print Prescription");
        
                        //add all
                currentPrescription = p;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		//this.setSize(400, 450);
		
		JPanel h = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));	
			JLabel hTitle = new JLabel("Viewing Prescription");
//				GPSISFramework.getInstance();
//				hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
			h.add(hTitle, new CC().wrap());
			
		this.add(h, new CC().wrap());
		
		this.viewPrescriptionForm = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
        
        
        
        			// First Name Label
			JLabel firstNameLbl = new JLabel("First Name: ");
			viewPrescriptionForm.add(firstNameLbl);
			// First Name Component
			this.firstNameFld = new JTextField(20);
                        this.firstNameFld.setText(p.getPatient().getFirstName());
                        this.firstNameFld.setEditable(false);
			viewPrescriptionForm.add(this.firstNameFld, new CC().wrap());
			
			// Last Name Label
			JLabel lastNameLbl = new JLabel("Last Name: ");
			viewPrescriptionForm.add(lastNameLbl);
			// Last Name Component
			this.lastNameFld = new JTextField(20);
                        this.lastNameFld.setText(p.getPatient().getLastName());
                        this.lastNameFld.setEditable(false);
			viewPrescriptionForm.add(this.lastNameFld, new CC().wrap());
                        
                        // patient address
                        JLabel addressLbl = new JLabel("Address: ");
                        viewPrescriptionForm.add(addressLbl);
                        this.addressFld = new JTextField(20);
                        this.addressFld.setText(p.getPatient().getAddress());
                        this.addressFld.setEditable(false);
                        viewPrescriptionForm.add(this.addressFld, new CC().wrap());         
                        
                        // patient post code
                        JLabel postCodeLbl = new JLabel("Post Code: ");
                        viewPrescriptionForm.add(postCodeLbl);
                        this.postCodeFld = new JTextField(20);
                        this.postCodeFld.setText(p.getPatient().getPostCode());
                        this.postCodeFld.setEditable(false);
                        viewPrescriptionForm.add(this.postCodeFld, new CC().wrap());  
                        
                        JLabel medicationConditionLbl = new JLabel("Medical condition: ");
                        viewPrescriptionForm.add(medicationConditionLbl);
                        this.medicalConditionFld = new JTextField(20);
                        this.medicalConditionFld.setText(p.getMedicalCondition());
                        this.medicalConditionFld.setEditable(false);
                        viewPrescriptionForm.add(this.medicalConditionFld, new CC().wrap());           
                                                    
                        // Button to show medicines that is in the prescription
                        this.showMedicine = new JButton("Show Medicince(s)");
                        this.showMedicine.setActionCommand("Show Medicines(s)");
                        this.showMedicine.addActionListener(this);
                        this.showMedicine.setEnabled(true);
                        viewPrescriptionForm.add(showMedicine, new CC().wrap());
                                                                      
                        // frequency label
                        JLabel freqLbl = new JLabel("Frequency: ");
                        viewPrescriptionForm.add(freqLbl);
                        // frequency Component
                        this.freqFld = new JTextField(20);
                        this.freqFld.setText(""+p.getfrequency());
                        this.freqFld.setEditable(false);
                        viewPrescriptionForm.add(this.freqFld, new CC().wrap());
                        
         		// Doctor Name Label
			JLabel doctorNameLbl = new JLabel("Doctor Name: ");
			viewPrescriptionForm.add(doctorNameLbl);
			// Doctor Name Component
			this.doctorNameFld = new JTextField(20);
                        this.doctorNameFld.setText(p.getDoctor().getName());
                        this.doctorNameFld.setEditable(false);
			viewPrescriptionForm.add(this.doctorNameFld, new CC().wrap());                       
                    
                        // Pay or free Label
			JLabel payOrFreeLbl = new JLabel("Pay/Free: ");
			viewPrescriptionForm.add(payOrFreeLbl);
			// First Name Component
			this.payOrFreeFld = new JTextField(20);
                        this.payOrFreeFld.setText(p.getPayOrFree());
                        this.payOrFreeFld.setEditable(false);
			viewPrescriptionForm.add(this.payOrFreeFld, new CC().wrap());                        
                                        
        		// Date Label
			JLabel startDateLbl = new JLabel("Start Date: ");
			viewPrescriptionForm.add(startDateLbl);
			// Date Component
                        this.startDateFld = new JTextField(20);
                        String startdisplay = new SimpleDateFormat("dd-MM-yyyy").format(p.getStartDate());
			this.startDateFld.setText(startdisplay);
                        this.startDateFld.setEditable(false);
			viewPrescriptionForm.add(this.startDateFld, new CC().wrap());        
                        
        		// Expiary Date Label
			JLabel expiaryDateLbl = new JLabel("Expiary Date: ");
			viewPrescriptionForm.add(expiaryDateLbl);
			// Expiary Date Component
                        String displayExpiary = new SimpleDateFormat("dd-MM-yyyy").format(p.getendDate());
                        this.expiaryDateFld = new JTextField(20);
                        this.expiaryDateFld.setText(displayExpiary); 
                        this.expiaryDateFld.setEditable(false);
			viewPrescriptionForm.add(this.expiaryDateFld, new CC().wrap());    
                        
                        // shows how long is the prescription is valid for
                        JLabel valid = new JLabel("Valid for");
                        viewPrescriptionForm.add(valid);
                        this.prescriptionValid = new JTextField(20);
                        this.prescriptionValid.setText(ifPrescriptionIsValid());
                        this.prescriptionValid.setEditable(false);
                        viewPrescriptionForm.add(this.prescriptionValid, new CC().wrap());
                        
                        // creating a print button
                        this.printPrescriptionFld = new JButton("Print");
                        this.printPrescriptionFld.setActionCommand("Print");
                        this.printPrescriptionFld.addActionListener(this);
                        this.printPrescriptionFld.setEnabled(true);
                        viewPrescriptionForm.add(printPrescriptionFld, new CC());
                        
                        //creating a button to delete the prescription.
                        this.removePrescription = new JButton("Delete");
                        this.removePrescription.setActionCommand("Delete");
                        this.removePrescription.addActionListener(this);
                        this.removePrescription.setEnabled(true);
                
                        viewPrescriptionForm.add(removePrescription, new CC());
                        
                        
		this.add(viewPrescriptionForm, new CC());
		this.setEnabled(true);
		this.setLocationRelativeTo(null); // center window
		this.pack();
		this.setVisible(true);
                
              //  this.checkAllComponents();                        
    }
    
    public String ifPrescriptionIsValid()
    {
        int days = Days.daysBetween(new DateTime(this.currentPrescription.getStartDate()), new DateTime(this.currentPrescription.getendDate())).getDays();
        if(days<0)
        {
            return "Expired";
        }
        return days + " days";       
    }    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Show Medicines(s)"))
        {
            new MedicineInformation(this, currentPrescription.getlistofMedicine()); // calls a different class to show the medicines.
            this.setEnabled(false);
        }
        else if(ae.getActionCommand().equals("Print"))
        {
            TextFilePrescription textFilePresc;
            try {
                textFilePresc = new TextFilePrescription(currentPrescription); // calls a different class. Converts the view Prescription to a text file to print.
                JOptionPane.showMessageDialog(this, "Printed in file "+textFilePresc.getFileName(), "Info:", JOptionPane.PLAIN_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(ViewAndPrintPrescription.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }
        else if(ae.getActionCommand().equals("Delete"))
        {
            PrescriptionDMO.getInstance().removeById(currentPrescription.getId()); // delteing the prescription from the database
                        ((PrescriptionsATM) PrescriptionsModule.getPrescriptionTable().getModel()).removeRow(currentPrescription); // remove the prescription from the table
            this.dispose();
        }
    }
    


    @Override
    public void broadcast(Object obj) {
              if(obj instanceof MedicineInformation)
        {
           // module.ViewMedicines viewMedTemp =(module.ViewMedicines) obj;
        //    this.medicines = viewMedTemp.getMedicines();
        }
    }
    
        public static void main(String [] args)
    {
        GPSISFramework GPSIS = new GPSISFramework();
        GPSISDataMapper.connectToDatabase();
        new ViewAndPrintPrescription(PrescriptionDMO.getInstance().getById(3));
    }
        
}
