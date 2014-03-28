/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package module.Prescriptions;


import framework.GPSISDataMapper;
import framework.GPSISFramework;
import framework.GPSISPopup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mapper.PatientDMO;
import mapper.PrescriptionDMO;
import module.Broadcastable;
import module.CalendarAppointments.DatePicker;
import module.PatientModule;
import module.PrescriptionsModule;
import module.StaffMember.ChangeStaffMemberPassword;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import object.Patient;
import object.PermanentPatient;
import object.Prescription;
import object.StaffMember;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class RenewPrescriptions extends GPSISPopup implements ActionListener, ListSelectionListener, Broadcastable
        {
    private static final long serialVersionUID = -8748112836660009010L;;
        
        private JPanel renewPrescriptionForm;
    
        private Prescription selectedPrescription;
        private JTextField idFld;
        private JTextField firstNameFld;
        private JTextField lastNameFld; 
        private JTextField freqFld;
        private JTextField medicineFld;
        private JTextField medicalConditionFld;
        private JDatePicker startDateFld;
        private JDatePicker expiaryDateFld;
        private JTextField prescriptionValid;
        private JTextField doctorFld;
        private Prescription prescription;
        
        public RenewPrescriptions(Prescription p)
        {
		super("Renew Prescription");
                
		this.prescription = p;
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		//this.setSize(400, 450);
		
		JPanel a = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));	
			JLabel hTitle = new JLabel("Renew Prescription");
//				GPSISFramework.getInstance();
//				hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
			a.add(hTitle, new CC().wrap());
			
		this.add(a, new CC().wrap());
		
		this.renewPrescriptionForm = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
                
      
        		// ID Label
			JLabel idLbl = new JLabel("ID: ");
			renewPrescriptionForm.add(idLbl);
			// ID Component
			this.idFld = new JTextField(20);
                        this.idFld.setText(""+p.getId());
                        this.idFld.setEditable(false);
			renewPrescriptionForm.add(this.idFld, new CC().wrap());                
                
        		// First Name Label
			JLabel firstNameLbl = new JLabel("First Name: ");
			renewPrescriptionForm.add(firstNameLbl);
			// First Name Component
			this.firstNameFld = new JTextField(20);
                        this.firstNameFld.setText(p.getPatient().getFirstName());
                        this.firstNameFld.setEditable(false);
			renewPrescriptionForm.add(this.firstNameFld, new CC().wrap());
			
			// Last Name Label
			JLabel lastNameLbl = new JLabel("Last Name: ");
			renewPrescriptionForm.add(lastNameLbl);
			// Last Name Component
			this.lastNameFld = new JTextField(20);
                        this.lastNameFld.setText(p.getPatient().getLastName());
                        this.lastNameFld.setEditable(false);
			renewPrescriptionForm.add(this.lastNameFld, new CC().wrap());			                
                
                         // frequency label
                        JLabel freqLbl = new JLabel("Frequency: ");
                        renewPrescriptionForm.add(freqLbl);
                        // frequency Component
                        this.freqFld = new JTextField(10);
                        this.freqFld.setText(""+p.getfrequency());
                        this.freqFld.setEditable(false);
                        renewPrescriptionForm.add(this.freqFld, new CC().wrap());    
                        
                        JLabel medicineLbl = new JLabel("Medicine: ");
                        renewPrescriptionForm.add(medicineLbl);
                        this.medicineFld = new JTextField(20);
                        this.medicineFld.setText(p.getlistofMedicine().toString());
                        this.medicineFld.setEditable(false);
                        renewPrescriptionForm.add(this.medicineFld, new CC().wrap()); 
                        
                        JLabel medicationConditionLbl = new JLabel("Medical condition: ");
                        renewPrescriptionForm.add(medicationConditionLbl);
                        this.medicalConditionFld = new JTextField(20);
                        this.medicalConditionFld.setText(p.getPatient().getMedicalConditions().toString());
                        this.medicalConditionFld.setEditable(false);
                        renewPrescriptionForm.add(this.medicalConditionFld, new CC().wrap());                        
                        
        		// Date Label
			JLabel dateLbl = new JLabel("Date: ");
			renewPrescriptionForm.add(dateLbl);
			// Date Component
                        this.startDateFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(p.getStartDate())); 
			renewPrescriptionForm.add((Component) this.startDateFld, new CC().wrap()); 
                        this.startDateFld.addActionListener(this);
             
                        
        		// Expiary Date Label
			JLabel expiaryDateLbl = new JLabel("Expiary Date: ");
			renewPrescriptionForm.add(expiaryDateLbl);
			// Expiary Date Component
                        this.expiaryDateFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(p.getendDate()));
			this.expiaryDateFld.addActionListener(this);
                        renewPrescriptionForm.add((Component) this.expiaryDateFld, new CC().wrap());       
                        
                        JLabel valid = new JLabel("Valid for");
                        renewPrescriptionForm.add(valid);
                        this.prescriptionValid = new JTextField(20);
                        this.prescriptionValid.setText(ifPrescriptionIsValid());
                        this.prescriptionValid.setEditable(false);
                        this.prescriptionValid.addActionListener(this);
                        renewPrescriptionForm.add(this.prescriptionValid, new CC().wrap());                        
                
        		// Doctor Name Label
			JLabel doctorLbl = new JLabel("Doctor Name: ");
			renewPrescriptionForm.add(doctorLbl);
			// Doctor Name Component
			this.doctorFld = new JTextField(20);
                        this.doctorFld.setText(p.getDoctor().getName());
                        this.doctorFld.setEditable(false);
			renewPrescriptionForm.add(this.doctorFld, new CC().wrap());                        
                        
                        JButton renewBtn = new JButton("Update Prescription");
                        renewBtn.setActionCommand("Update");
                        renewBtn.addActionListener(this);
                        renewBtn.setFont(new Font("",Font.BOLD, 15));
                        renewPrescriptionForm.add(renewBtn);
                                                
 		this.add(renewPrescriptionForm, new CC());    
		this.pack();
		this.setLocationRelativeTo(null); // centers the window                               
                this.setVisible(true);
        }
        
        private Prescription buildPrescriptionFromValues() throws Exception
        {
            Date startDate =(Date) this.startDateFld.getModel().getValue();
            Date expiaryDate = (Date) this.expiaryDateFld.getModel().getValue();
            
        Date checkDate = new Date();
        Calendar changeDate = Calendar.getInstance();
        changeDate.setTime(checkDate);
        changeDate.add(Calendar.DAY_OF_MONTH, -1);            
            
        if(this.freqFld.getText().isEmpty())
        {
            throw new Exception("Frequency Field cant be empty!");
        }
        int frequency = Integer.parseInt(this.freqFld.getText());           
        if(startDate.after(expiaryDate))
        {
            throw new Exception("Start Date cant be after End Date!");
        } 
        else if(startDate.before(changeDate.getTime()))
        {
            throw new Exception("Invalid start Date!");
        }            
            
            this.prescription.setStartDate(startDate);
            this.prescription.setendDate(expiaryDate);
            PrescriptionDMO.getInstance().put(this.prescription);
           
            return this.prescription;
        }
        
    public String ifPrescriptionIsValid()
    {
        Date startDate =(Date) this.startDateFld.getModel().getValue();
        Date expiaryDate = (Date) this.expiaryDateFld.getModel().getValue();
        int days = Days.daysBetween(new DateTime(startDate), new DateTime(expiaryDate)).getDays();
        if(days<0)
        {
            return "Expired";
        }
        return days + " days";       
    }      
        
        public void actionPerformed(ActionEvent ae)
        {
		switch (ae.getActionCommand())
		{
			case "Update":
                            try{
                                
                            
                            module.Prescriptions.PrescriptionsATM pATM = (module.Prescriptions.PrescriptionsATM) PrescriptionsModule.getPrescriptionTable().getModel();
                            this.prescription = buildPrescriptionFromValues();
                            pATM.removeRow(prescription);
                            pATM.addRow(prescription);
                            this.dispose();
                            }
                            catch(Exception e)
                            {
                                JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case "Date selected":
                            this.prescriptionValid.setText(ifPrescriptionIsValid());
                            System.out.println(this.prescriptionValid.getText());
                            this.prescriptionValid.updateUI();
                            this.prescriptionValid.repaint();
                            break;
		}            
            
        }   

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void broadcast(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        public static void main(String [] args)
    {
        GPSISFramework GPSIS = new GPSISFramework();
        GPSISDataMapper.connectToDatabase();
        new RenewPrescriptions(PrescriptionDMO.getInstance().getById(17));
    }    
    
}
        