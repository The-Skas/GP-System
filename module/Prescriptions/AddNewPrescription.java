/*
 * Adding a new Prescription
 * 
 */
package module.Prescriptions;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import module.StaffMemberModule;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import object.MedicalStaffMember;
import object.Receptionist;
import object.StaffMember;
import exception.DuplicateEntryException;
import exception.EmptyResultSetException;
import framework.GPSISFramework;
import framework.GPSISPopup;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mapper.MedicineDMO;
import mapper.PrescriptionDMO;
import mapper.SQLBuilder;
import mapper.StaffMemberDMO;
import module.Broadcastable;
import module.Patient.PatientATM;
import module.PatientModule;
import module.PrescriptionsModule;
import module.SearchTable;
import module.StaffMember.StaffMemberATM;
import object.Medicine;
import object.Patient;
import object.Prescription;
import object.MedicalCondition;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class AddNewPrescription extends GPSISPopup implements ActionListener,Broadcastable{

        private JPanel createNewPrescriptionForm;
   
	private JComboBox<String> payOrFreeFld;
        private JComboBox<String> conditionFld;
        private JTextField freqFld;
	private String[] payOrFrees = {"Pay", "Free"};
        private JDatePicker enddateFld;
        private JButton selectDoctor;
        private JLabel doctorNameLbl;
        private JButton selectPatient;
        private JLabel patientNameLbl;
        private JDatePicker startDateFld;
        private JButton medicinesFld;
        
        private StaffMember doctor;
        private Patient patient;
        private List<Medicine> medicines;
   
        public AddNewPrescription()
    {
        super("Adding New Prescription");
        /// Setting the layout for the add prescription
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new MigLayout());
        this.setBackground(new Color(240, 240, 240));
        this.setSize(400, 600);
        this.medicines = new ArrayList<>();
	JPanel h = new JPanel(new MigLayout());	
	JLabel hTitle = new JLabel("Adding New Prescription");
	GPSISFramework.getInstance();
//	hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
        h.add(hTitle, new CC().wrap());
			
	this.add(h, new CC().wrap());  
        
	this.createNewPrescriptionForm = new JPanel(new MigLayout()); 
	//Selecting patient
        this.selectPatient = new JButton("Select Patient");
        this.selectPatient.setActionCommand("Select Patient");
        this.selectPatient.addActionListener(this);
        this.createNewPrescriptionForm.add(this.selectPatient);
        // Showing if the patient has been selected
        this.patientNameLbl = new JLabel("Not selected yet");
        this.patientNameLbl.setVisible(false);
        this.createNewPrescriptionForm.add(this.patientNameLbl, new CC().wrap());     
        // Choosing medicine
        this.medicinesFld = new JButton("Medicine(s)");
        this.medicinesFld.setActionCommand("Medicines");
        this.medicinesFld.addActionListener(this);
        this.createNewPrescriptionForm.add(this.medicinesFld, new CC().wrap());
        
        //frequency label
        JLabel freqLbl = new JLabel("Frequency: ");
        this.createNewPrescriptionForm.add(freqLbl);
        // frequency component
        this.freqFld = new JTextField(20);
        this.createNewPrescriptionForm.add(this.freqFld, new CC().wrap());
        
        // condition label
        JLabel condition = new JLabel("Patients Medical Condition");
        this.createNewPrescriptionForm.add(condition, new CC().wrap());   
      //  this.patient.getMedicalConditions().toArray()
        this.conditionFld = new JComboBox<String>();
        this.createNewPrescriptionForm.add(this.conditionFld, new CC().span(2).wrap());
        
        // Role label
        JLabel RoleLbl = new JLabel("Price: ");
        this.createNewPrescriptionForm.add(RoleLbl);
        // Role component
        this.payOrFreeFld = new JComboBox<String>(this.payOrFrees);
        this.createNewPrescriptionForm.add(this.payOrFreeFld, new CC().wrap());

        // Date label
        JLabel startDateLbl = new JLabel("Start Date: ");
        this.createNewPrescriptionForm.add(startDateLbl);
        // Date component
        this.startDateFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
        this.createNewPrescriptionForm.add((Component) this.startDateFld, new CC().wrap());        
        
        // Date label
        JLabel dateLbl = new JLabel("Expiry Date: ");
        this.createNewPrescriptionForm.add(dateLbl);
        // Date component
        this.enddateFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
        this.createNewPrescriptionForm.add((Component) this.enddateFld, new CC().wrap());
        
        //Add Doctor
        this.selectDoctor = new JButton("Select Doctor");
        this.selectDoctor.setActionCommand("Select Doctor");
        this.selectDoctor.addActionListener(this);
        this.createNewPrescriptionForm.add(this.selectDoctor);
    
        this.doctorNameLbl = new JLabel("Not Selected!");
        this.doctorNameLbl.setVisible(false);
        this.createNewPrescriptionForm.add(this.doctorNameLbl, new CC().wrap());        
        
        
	// Add Button
	JButton addBtn = new JButton("Add!");
	addBtn.addActionListener(this);
	this.createNewPrescriptionForm.add(addBtn);         
        
        this.add(this.createNewPrescriptionForm, new CC());
        
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
         
    public Prescription buildPrescription() throws Exception
    {
        String name = this.selectPatient.getText().trim();
     //   ArrayList<String> condition = this.conditionFld.getText().trim();
        Date startDate = (Date)this.startDateFld.getModel().getValue();
        Date endDate = (Date)this.enddateFld.getModel().getValue();
        String payOrFree = (String) this.payOrFreeFld.getSelectedItem();
        String medicalCondition = (String) this.conditionFld.getSelectedItem();
        Date checkDate = new Date();
        Calendar changeDate = Calendar.getInstance();
        changeDate.setTime(checkDate);
        changeDate.add(Calendar.DAY_OF_MONTH, -1);
        
        
        /// throwing exceptions. seeing if all information needed is inputed.
        // Showing specifically what is missing, so easy to use.
        if(this.freqFld.getText().isEmpty())
        {
            throw new Exception("Frequency Field cant be empty!");
        }
        int frequency = Integer.parseInt(this.freqFld.getText());           
        if(startDate.after(endDate))
        {
            throw new Exception("Start Date cant be after End Date!");
        } 
        else if(startDate.before(changeDate.getTime()))
        {
            throw new Exception("Invalid start Date!");
        }
        else if(this.doctorNameLbl.getText().equals("Not Selected!"))
        {       
            throw new Exception("Doctor is not Selected!");
        }
        else if(this.patient.getMedicalConditions().isEmpty())
        {
            throw new Exception("Patient does not have a medical Condition");
        }
        else if(this.medicines.isEmpty())
        {
            throw new Exception("Medicine(s) has not been selected!");
        }

        
        return new Prescription(this.patient.getId(),this.doctor.getId(),startDate,endDate,this.medicines,frequency, payOrFree, medicalCondition);

    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
         if(ae.getActionCommand().equals("Add!"))
        {

            try{
            Prescription p;
            p = buildPrescription();
            JTable tempPrescT = PrescriptionsModule.getPrescriptionTable();
            PrescriptionDMO.getInstance().put(p);
            ((PrescriptionsATM) PrescriptionsModule.getPrescriptionTable().getModel()).addRow(p);            
            this.dispose();                  
            }
            catch(Exception e)
            {
                if(this.patient ==null)
                {
                        JOptionPane.showMessageDialog(this, "Input patient!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        
                }
                else
                {
                    System.out.println("IT makes sense! see ->"+e.getMessage());
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }    
                
                
            }
              
            

        }       
        else if(ae.getActionCommand().equals("Select Doctor"))
        {
            StaffMemberATM sMM = null;
            
            //to get doctors.
            SQLBuilder sql = new SQLBuilder("role","=","Doctor");
                
            try {
                sMM = new StaffMemberATM(StaffMemberDMO.getInstance().getAllByProperties(sql));
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(AddNewPrescription.class.getName()).log(Level.SEVERE, null, ex);
            }
            JTable sMT = new JTable(sMM);
            new SearchTable(this, sMT,
                    "Patient Search");
            
            this.setEnabled(false);
        }
        else if(ae.getActionCommand().equals("Patients Medical Condition"))
        {
            new SearchTable(this,PatientModule.buildPatientsTable(),
                    "Patient Search");
        }
        else if(ae.getActionCommand().equals("Select Patient"))
        {
            new SearchTable(this,PatientModule.buildPatientsTable(),
                    "Patient Search");
            
            this.setEnabled(false);
        }
        else if(ae.getActionCommand().equals("Medicines"))
        {
            new ViewMedicines(this, medicines);
            this.setEnabled(false);
        }
    
    }

        @Override
    public void broadcast(Object obj) {
        if(obj instanceof SearchTable)
        {
            SearchTable st =(SearchTable) obj;
            //do some stuff to st
            System.out.println("In PrescriptionModule, the row is"+st.getSelectedRow());

            // Having multiple searchTables that search different DMO's.
            // Using this method to differentiate types and construct the 
            // relevant objects
            if(st.tbl.getModel() instanceof StaffMemberATM)
            {
                 
                //TODO: GetAll to store all objects. This is to avoid the overhead
                //of querying everytime.
                
                List<StaffMember> smembers =((StaffMemberATM)st.tbl.getModel()).getData();
                this.doctor = smembers.get(st.getSelectedRow());
                this.selectDoctor.setText("Change Doctor");
                this.doctorNameLbl.setText(this.doctor.getFirstName()+" "
                                          +this.doctor.getLastName());
                this.doctorNameLbl.setVisible(true);
            }  
            else if(st.tbl.getModel() instanceof PatientATM)
            {
                PatientATM pATM = (PatientATM)st.tbl.getModel();
                
                this.patient = pATM.getData().get(st.getSelectedRow());
                // once a patient already has been selected, and then
                // the user changes it to another patient to be selected, then clear
                // the previous medical condition and medicine selected
                // so wont be able to duplicate and add. 
                this.conditionFld.removeAllItems();
                
                this.medicines.clear();
                
                for(MedicalCondition mc : this.patient.getMedicalConditions())
                {                    
                    this.conditionFld.addItem(mc.getName());
                }
                
                this.selectPatient.setText("Change Patient");

                this.patientNameLbl.setText(this.patient.getFirstName()+ " "
                                            +this.patient.getLastName());
                this.patientNameLbl.setVisible(true);
               
                
            }
        }
        else if(obj instanceof ViewMedicines)
        {
            ViewMedicines viewMedTemp =(ViewMedicines) obj;
            this.medicines = viewMedTemp.getMedicines();
        }
    }    
        /// once patient has been selected, then show condition  
	public void stateChanged(ChangeEvent arg0) {
		if (this.selectPatient.isSelected())
		{
			((Component) this.conditionFld).setVisible(true);
			this.conditionFld.setVisible(true);
		}
		else
		{
			((Component) this.conditionFld).setVisible(false);
			this.conditionFld.setVisible(false);
		}
	}    
    
    
    
    
    
}
