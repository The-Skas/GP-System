
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skas
 */
/*  View Patient
 * Opens a New Window to ViewPatient
 * @author Salman Khalifa (skas)
 */
package module.Patient;
import module.JTextFieldLimit;
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
import object.Patient;
import object.PermanentPatient;
import exception.DuplicateEntryException;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import framework.GPSISFramework;
import framework.GPSISPopup;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import mapper.PatientDMO;
import mapper.SQLBuilder;
import mapper.StaffMemberDMO;
import module.Broadcastable;
import module.PatientModule;
import module.SearchTable;
import module.StaffMember.StaffMemberATM;
import module.Patient.ViewMedicalCondition;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.sourceforge.jdatepicker.DateModel;
import object.StaffMember;
import object.MedicalCondition;

public class ViewPatient extends GPSISPopup implements ActionListener,Broadcastable
{

	//Container
        private JPanel addPatientForm;
        //GUI Components
        private JComponent comp;
        private ArrayList<JTextField> textFields = new ArrayList<>();
	private JTextField usernameFld;
	private JPasswordField passwordFld;
	private JTextField firstNameFld;
	private JTextField lastNameFld;
        private JTextField addressFld;
        private JTextField postCodeFld;
        private module.Patient.IntegerField phoneFld;
        private module.Patient.IntegerField nhsFld;
	private JDatePicker dobFld;
        private JLabel nhsLbl;
	private JCheckBox isPermanentPatientFld;
        private JComboBox<String> sexFld;
	private String[] sex = {"male", "female"};
        private JButton selDoctorButton;
        private JLabel doctorNameLbl;
        private JButton selectMotherBtn;
        private JButton selectFatherBtn;
        private JButton medCondButton;
        //this button is used to store the last active button for use
        //of broadcast. This is a quick way to solve the problem of not being
        //able to refrence a button except by class.
        private JButton lastActive;
        //Current Patient
        Patient currentPatient;
        //Values stored
        private Patient father;
        private Patient mother;
        private StaffMember doctor;
        private ArrayList<MedicalCondition> patientMedicalConditions;
        private JButton viewFamilyBtn;
        private StaffMemberATM sMM;
        
        private int patientIndex;
        private JButton deleteFatherBtn;
        private JButton deleteMotherBtn;
	public ViewPatient(Patient p) 
        {
		super("View Patient"); // Set the JFrame Title
                PatientATM pATM =(PatientATM) PatientModule.patientTable.getModel();
		this.patientIndex = pATM.getData().indexOf(p);
                //add all
                currentPatient = p;
               

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		//this.setSize(400, 450);
		
		JPanel h = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));	
			JLabel hTitle = new JLabel("Edit Patient");
				GPSISFramework.getInstance();
				hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
			h.add(hTitle, new CC().wrap());
			
		this.add(h, new CC().wrap());
		
		this.addPatientForm = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			
			
			// First Name Label
			JLabel firstNameLbl = new JLabel("First Name: ");
			addPatientForm.add(firstNameLbl);
			// First Name Component
			this.firstNameFld = new JTextField(20);
                        this.firstNameFld.setText(p.getFirstName());
			addPatientForm.add(this.firstNameFld, new CC().wrap());
			
			// Last Name Label
			JLabel lastNameLbl = new JLabel("Last Name: ");
			addPatientForm.add(lastNameLbl);
			// Last Name Component
			this.lastNameFld = new JTextField(20);
                        this.lastNameFld.setText(p.getLastName());
			addPatientForm.add(this.lastNameFld, new CC().wrap());
			
			// Is Permanent Label
			JLabel permanentPlbl = new JLabel("Permanent Patient: ");
			addPatientForm.add(permanentPlbl);
			// Is Permanent Component
                        this.isPermanentPatientFld = new JCheckBox("", false);
                        addPatientForm.add(isPermanentPatientFld, new CC().wrap());
			
                        //NHS Number Label:
                        this.nhsLbl = new JLabel("NHS Number: ");
                        this.nhsLbl.setEnabled(false);
                        addPatientForm.add(nhsLbl);

                        //NHS Number Component
                        this.nhsFld = new module.Patient.IntegerField(20, 6);
                        this.nhsFld.setActionCommand("NHS");
                        this.nhsFld.setEnabled(false);
                        addPatientForm.add(this.nhsFld, new CC().wrap());
                        
                        //Doctor
                        this.selDoctorButton = new JButton("Select Doctor");
                        this.selDoctorButton.setActionCommand("Select Doctor");
                        this.selDoctorButton.setEnabled(false);
                        this.selDoctorButton.addActionListener(this);
                        addPatientForm.add(this.selDoctorButton);
                        
                        this.doctorNameLbl = new JLabel("Joe");
                        this.doctorNameLbl.setVisible(false);
                        addPatientForm.add(this.doctorNameLbl, new CC().wrap());
                        //if P is permanentPatient
                        if(p instanceof PermanentPatient)
                        {
                            System.out.println("In permanent!");
                            PermanentPatient pp =(PermanentPatient) p;
                            this.isPermanentPatientFld.setSelected(true);
                            this.nhsFld.setText(pp.getNHSNumber());
                            this.nhsFld.setEnabled(true);
                            this.nhsLbl.setEnabled(true);
                            this.doctor = pp.getDoctor();
                            
                            this.selDoctorButton.setEnabled(true);
                            this.doctorNameLbl.setVisible(true);
                            this.doctorNameLbl.setText(doctor.getFirstName() +" "+doctor.getLastName());
                            this.doctorNameLbl.setEnabled(true);
                        }
                        
			// DoB Label
			JLabel dobLabel = new JLabel("Dob: ");
			addPatientForm.add(dobLabel);
                        
			// DoB Component
			this.dobFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(p.getDob());
                        System.out.println(p.getDob());              //y   m  d
                        this.dobFld.getModel().setDate(cal.get(Calendar.YEAR), 
                                                       cal.get(Calendar.MONTH),
                                                       cal.get(Calendar.DAY_OF_MONTH));
                        
                        addPatientForm.add((Component) this.dobFld, new CC().wrap());
                        
                        //Age Group
                        JLabel ageGroup = new JLabel("AgeGroup: ");
                        addPatientForm.add(ageGroup);
                        
                        JLabel ageGroupValue;
                        ageGroupValue = new JLabel(p.getAgeGroup().toString());
                        addPatientForm.add(ageGroupValue, new CC().wrap());
                        //Sex Label
                        JLabel sexLbl = new JLabel("Sex: ");
                        addPatientForm.add(sexLbl);
                        
                        //Sex Component
                        this.sexFld = new JComboBox<>(this.sex);
                        char t_sex = p.getSex();
                        if(t_sex == 'm')
                        {
                            this.sexFld.setSelectedIndex(0);
                        }
                        else if(t_sex == 'f')
                        {
                            this.sexFld.setSelectedIndex(1);
                        }
                        addPatientForm.add(sexFld,  new CC().wrap());
			
                        //Phone
                        JLabel phoneLbl = new JLabel("Phone: ");
                        addPatientForm.add(phoneLbl);
                        this.phoneFld = new module.Patient.IntegerField(20,20);
                        this.phoneFld.setText(p.getPhone());
                        addPatientForm.add(this.phoneFld, new CC().wrap());
                        
                        JLabel addressLbl = new JLabel("Address: ");
                        addPatientForm.add(addressLbl);
                        this.addressFld = new JTextField(20);
                        this.addressFld.setText(p.getAddress());
                        addPatientForm.add(this.addressFld, new CC().wrap());
                        
                        JLabel postCodeLbl = new JLabel("Post Code: ");
                        addPatientForm.add(postCodeLbl);
                        this.postCodeFld = new JTextField(20);
                        this.postCodeFld.setText(p.getPostCode());
                        addPatientForm.add(this.postCodeFld, new CC().wrap());
                        
                        //Parent Lbl
                        JLabel parentsLbl = new JLabel("Parents: ");
                        addPatientForm.add(parentsLbl);
                        //Father Button //Select Mother
                        this.deleteFatherBtn = new JButton("X");
                        this.deleteFatherBtn.setActionCommand("Delete Father");
                        this.deleteFatherBtn.setForeground(Color.red);
                        this.deleteFatherBtn.addActionListener(this);
                        
                        this.deleteMotherBtn = new JButton("X");
                        this.deleteMotherBtn.setActionCommand("Delete Mother");
                        this.deleteMotherBtn.setForeground(Color.red);
                        this.deleteMotherBtn.addActionListener(this);
                        
                        this.selectFatherBtn = new JButton("  Select Father  ");
                        this.selectFatherBtn.setActionCommand("Select Father");
                        this.selectFatherBtn.addActionListener(this);
                        this.selectMotherBtn = new JButton("  Select Mother  ");
                        this.selectMotherBtn.setActionCommand("Select Mother");
                        this.selectMotherBtn.addActionListener(this);
                        
                        //Sets button/variables if parents arent null
                        if(p.getFather() != null)
                        {
                            this.father = p.getFather();
                            this.selectFatherBtn.setText(father.getFirstName()+" "+father.getLastName());
                        }
                        
                        if(p.getMother() != null)
                        {
                            this.mother = p.getMother();
                            this.selectMotherBtn.setText(mother.getFirstName()+" "+mother.getLastName());
                        }
                        addPatientForm.add(selectFatherBtn, "split 2");
                        addPatientForm.add(this.deleteFatherBtn, new CC().wrap());
                        addPatientForm.add(selectMotherBtn,"cell 1 14, split 2");
                        addPatientForm.add(this.deleteMotherBtn,new CC().wrap());
                        
                        //View Family
                        JLabel viewFamily = new JLabel("Family List: ");
                        addPatientForm.add(viewFamily);
                        this.viewFamilyBtn = new JButton("View Family");
                        this.viewFamilyBtn.setActionCommand("Family");
                        this.viewFamilyBtn.addActionListener(this);
                        addPatientForm.add(viewFamilyBtn,new CC().span().alignX("center"));
                        
			// Medical Condition Label
			JLabel roleLbl = new JLabel("Medical Conditions: ");
			addPatientForm.add(roleLbl);
			
			// Medical Condition Components
			
			this.medCondButton = new JButton("View Medical Conditions");
                        this.medCondButton.setPreferredSize(new Dimension(40, 40));
                        this.medCondButton.setActionCommand("Medical Conditions");
                        this.medCondButton.addActionListener(this);
                        this.patientMedicalConditions = p.getMedicalConditions();
			addPatientForm.add(medCondButton, new CC().span().alignX("center"));
			// Add Button
			JButton addBtn = new JButton("Update!");
				addBtn.addActionListener(this);
                                addBtn.setActionCommand("Update");

                        //Increase Font Size
                        addBtn.setFont(new Font("Verdana", Font.BOLD, 20));
                        
			addPatientForm.add(addBtn, new CC().newline());
                        
                        //Anonymous Listeners
                        this.isPermanentPatientFld.addItemListener(new ItemListener()
                        {       
                            public void itemStateChanged(ItemEvent e) 
                            {
                                if(e.getStateChange() == ItemEvent.SELECTED){
                                    nhsFld.setEnabled(true);
                                    nhsFld.setText("");
                                    selDoctorButton.setEnabled(true);
                                    selDoctorButton.setVisible(true);
                                    nhsLbl.setEnabled(true);
                                    //enable search for doctor.
                                }
                                else if(e.getStateChange() == ItemEvent.DESELECTED){
                                    nhsFld.setEnabled(false);
                                    nhsFld.setText("");
                                    selDoctorButton.setEnabled(false);
                                    nhsLbl.setEnabled(false);
                                }
                                validate();
                                repaint();
                            }
                        });
                        
		this.add(addPatientForm, new CC());
		this.setEnabled(true);
		this.setLocationRelativeTo(null); // center window
		this.pack();
		this.setVisible(true);
                
                if(PatientModule.iscurrentUserNotAllowed() ) {
                    hTitle.setText("View Patient");
                    addBtn.setEnabled(false);
                }
	}
    public void disableAllCompononts()
    {
        Component[] components = this.addPatientForm.getComponents();
        for(Component comp : components)
        {
            if(comp instanceof JComponent)
            {
                //Do some check
                JComponent jcomp =(JComponent) comp;
                //Fix it up
                jcomp.setEnabled(false);
               // System.out.println(tempTxtFld.getText());
                
                //Check parsing, if error. Return false;
            }
        }
    }
    
       public boolean AllTextFieldsNotEmpty(String[] msg)
    {
        
        Component[] components = this.addPatientForm.getComponents();
        for(Component comp : components)
        {
            if(comp instanceof JTextField && !(comp.equals(this.nhsFld)))
            {
                //Do some check
                JTextField tempTxtFld =(JTextField) comp;
                //Fix it up
                tempTxtFld.setText(tempTxtFld.getText().trim());
               // System.out.println(tempTxtFld.getText());
                if(tempTxtFld.getText().isEmpty())
                {
                    msg[0] = "Can't have empty fields!";
                    return false;
                }
                if(PatientModule.validator.matcher(tempTxtFld.getText()).find())
                {
                    msg[0] = "Illegal characters!";
                    return false;
                }
                if(tempTxtFld.getText().length() > 20 
                && !comp.equals(this.postCodeFld)
                && !comp.equals(this.addressFld))
                {
                    msg[0] = "Values too large!";
                    return false;
                }
                //Check parsing, if error. Return false;
            }
        }
        if(this.addressFld.getText().length() > 50)
        {
            msg[0] = "Address Field values too large.";
            return false;
        }
        if(this.postCodeFld.getText().length() > 10)
        {
            msg[0] = "PostCode values too large.";
            return false;
        }
                
        System.out.println(this.dobFld.getModel().getValue());

        return true;
        
    }
    public boolean checkPermanentPatient(String [] msg)
    {
        
        if(this.isPermanentPatientFld.isSelected())
        {
            if(this.nhsFld.getText().length() != 6)
            {
                msg[0] = "NHS Number length incorrect!";
                return false;
            }
            
            if(this.doctor == null)
            {
                msg[0] = "No doctor selected!";
                return false;
            }
        }
        
        //To check if patients NHS Number is unique
        PatientATM pATM =(PatientATM) PatientModule.patientTable.getModel();
        List<Patient> patients = pATM.getData();
        for(Patient p : patients)
        {
            if(p instanceof PermanentPatient && !(p.equals(currentPatient)))
            {
                PermanentPatient pP = (PermanentPatient) p;
                if(pP.getNHSNumber().equals(this.nhsFld.getText()))
                {
                    System.out.println(currentPatient.getId()+" VS "+p.getId());
                    msg[0] = "Duplicate NHS Number!";
                    return false;
                }
            }
                
        }
        return true;
    }
    public boolean checkDateValues(String [] msg)
    {
        Date dob =(Date) this.dobFld.getModel().getValue();
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
          
        Date today = null;
            try {
                today = dt1.parse(dt1.format(new Date()));
                 dob = dt1.parse(dt1.format(dob));
            } catch (ParseException ex) {
                Logger.getLogger(AddPatient.class.getName()).log(Level.SEVERE, null, ex);
            }
       

        if(dob.after(today))
        {
            msg[0] = "Birthdate cant be after current date!";
            return false;
        }
            
        return true;
    }
    private Patient buildPatientFromValues()
    {
        Date dob =(Date) this.dobFld.getModel().getValue();
        
        char sex = ((String)this.sexFld.getSelectedItem()).charAt(0);
        
        Patient p=new Patient(currentPatient.getId(),this.firstNameFld.getText(), 
                this.lastNameFld.getText(),
                sex, 
                this.postCodeFld.getText(), 
                this.addressFld.getText(),
                this.phoneFld.getText(), 
                dob, 
                this.father !=null ? this.father.getId() : 0, 
                this.mother !=null ? this.mother.getId() : 0);
        PatientDMO pDMO=PatientDMO.getInstance();
        
        //have to put here since updating
        pDMO.put(p);
        pDMO.addPatientMedicalConditions(this.patientMedicalConditions, p);
        
        if(this.isPermanentPatientFld.isSelected())
        {   
            //Adds to database a permanent Patient;
            p = PermanentPatient.constructByPatient(p, 
                                                this.doctor, 
                                                this.nhsFld.getText());
        }
        else
        {
            pDMO.deletePermanentPatientById(p.getId());
        }
        return p;
    }
    /*
     * This is an example of how SearchTable communicates with ViewPatient.
     * Every Module which would need a SearchTable must implement this.
    */
    public void broadcast(Object obj) {
        if(obj instanceof SearchTable)
        {
            SearchTable st =(SearchTable) obj;
            //do some stuff to st

            //If you would have multiple searchTables that search in different
            //DMO's then use this method to diffrentiate types and construct
            //the relevant object.
            if(st.tbl.getModel() instanceof StaffMemberATM)
            {
                System.out.println("Its a StaffMemberATM");
                
                //Ive re-edited getStaffMembers to be a static method, that way
                //we can use a single instance. 
                //TODO: GetAll to store all objects. This is to avoid the overhead
                //of querying everytime.
                if(this.lastActive.equals(this.selDoctorButton))
                {
                    List<StaffMember> smembers = this.sMM.getData();
                    int row= st.tbl.getSelectedRow();
                    row = st.tbl.convertRowIndexToModel(row);
                    this.doctor = smembers.get(row);
                    this.selDoctorButton.setText("Change Doctor");
                    this.doctorNameLbl.setText(this.doctor.getFirstName()+" "
                                              +this.doctor.getLastName());
                    this.doctorNameLbl.setVisible(true);
                }
            }  
            else if(st.tbl.getModel() instanceof module.Patient.PatientATM)
            {
                module.Patient.PatientATM pATM = (module.Patient.PatientATM)st.tbl.getModel();
                int row= st.tbl.getSelectedRow();
                row = st.tbl.convertRowIndexToModel(row);
                Patient patient = pATM.getData().get(row);

                if(this.lastActive.equals(selectFatherBtn))
                {
                    if(patient.getId() != this.currentPatient.getId()) 
                    {
                        this.father = patient;
                        this.selectFatherBtn.setText(patient.getFirstName()+" "+
                                                 patient.getLastName());
                    }
                }
                else if(this.lastActive.equals(selectMotherBtn))
                {
                    if(patient.getId() != this.currentPatient.getId())
                    {
                        this.mother = patient;
                    
                        this.selectMotherBtn.setText(patient.getFirstName()+" "+
                                                 patient.getLastName());
                    }
                }
            }
            else if(st.tbl.getModel() instanceof module.Patient.PatientFamilyATM)
            {
                PatientFamilyATM pfATM = (PatientFamilyATM)st.tbl.getModel();
                int row= st.tbl.getSelectedRow();
                row = st.tbl.convertRowIndexToModel(row);
                
                Patient patient = pfATM.getData().get(row).getPatient();
                
                
            }
        }
        else if(this.lastActive.equals(this.medCondButton))
        {
            ViewMedicalCondition viewMedCond = ((ViewMedicalCondition)obj);
            this.patientMedicalConditions = viewMedCond.getMedicalConditions();
            this.medCondButton.setText("Change Medical Conditions");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
            // GET ALL THE VALUES
        System.out.println(this.nhsFld.getText());
        if(e.getActionCommand().equals("Update"))
        {
            String[] msg = new String[1];
            if(!this.AllTextFieldsNotEmpty(msg) || !this.checkPermanentPatient(msg)
             ||!this.checkDateValues(msg))
            {
                JOptionPane.showMessageDialog(this, msg[0], "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                module.Patient.PatientATM pATM =(module.Patient.PatientATM) PatientModule.patientTable.getModel();
                
                //Replace at index;
                System.out.println("Current Patient Value is "+ this.currentPatient);
                System.out.println("Current Patient Local? is "+ currentPatient);
                
                for(Patient p : pATM.getData())
                {
                    if(p.getId() == this.currentPatient.getId())
                    {
                        System.out.println("The patient does exist in the model.");
                    }
                    
                }
                
                pATM.getData().remove(this.patientIndex);
                pATM.getData().add(this.patientIndex, this.buildPatientFromValues());
                this.dispose();
            }
        }
        else if(e.getActionCommand().equals("Select Doctor"))
        {
            this.sMM = null;
            try {
                SQLBuilder sql = new SQLBuilder("role","=","Doctor");
                this.sMM = new StaffMemberATM(StaffMemberDMO.getInstance().getAllByProperties(sql));
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(AddPatient.class.getName()).log(Level.SEVERE, null, ex);
            }
              
            JTable sMT = new JTable (sMM);
			
            new SearchTable(this,sMT,
                    "Doctor Search");
            this.lastActive = this.selDoctorButton;
            this.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Select Mother"))
        {
            PatientATM pATM = null;
            try {            
                pATM = new PatientATM(PatientDMO.getInstance().getAll());
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(PatientModule.class.getName()).log(Level.SEVERE, null, ex);
            }
            JTable pT = new JTable (pATM);
            new SearchTable(this,pT,
                    "Patient Search");
            this.lastActive = this.selectMotherBtn;
            this.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Select Father"))
        {
            PatientATM pATM = null;
            try {            
                pATM = new PatientATM(PatientDMO.getInstance().getAll());
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(PatientModule.class.getName()).log(Level.SEVERE, null, ex);
            }
            JTable pT = new JTable (pATM);
            new SearchTable(this,pT,
                    "Patient Search");
            this.lastActive = this.selectFatherBtn;
            this.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Medical Conditions"))
        {
            new ViewMedicalCondition(this, this.patientMedicalConditions);
            this.lastActive = this.medCondButton;
            this.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Family"))
        {
            System.out.println("Family?");
            PatientFamilyATM pfM = null;
            pfM = new PatientFamilyATM(this.currentPatient);
            JTable pfT = new JTable (pfM);
            new SearchTable(this,pfT,
                    "View Family");
        }
        else if (e.getActionCommand().equals("Delete Father"))
        {
            this.father = null;
            this.selectFatherBtn.setText("Select Father");
        }
        else if (e.getActionCommand().equals("Delete Mother"))
        {
            this.mother = null;
            this.selectMotherBtn.setText("Select Mother");
        }

    }

    public static void main (String [] args)
    {
        GPSISFramework GPSIS = new GPSISFramework();
        GPSISDataMapper.connectToDatabase();
            try {
                new ViewPatient(PatientDMO.getInstance().getById(20));
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(ViewPatient.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
	
}
