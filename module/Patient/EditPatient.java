
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import mapper.PatientDMO;
import mapper.StaffMemberDMO;
import module.Broadcastable;
import module.PatientModule;
import module.SearchTable;
import module.StaffMember.StaffMemberATM;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.sourceforge.jdatepicker.DateModel;
import object.StaffMember;
import object.MedicalCondition;

public class EditPatient extends GPSISPopup implements ActionListener,Broadcastable
{

	private static final long serialVersionUID = -8748112836660009010L;
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
        private ArrayList<MedicalCondition> patientMedicalConditions = new ArrayList<>();
	public EditPatient(Patient p) 
        {
		super("Edit Patient"); // Set the JFrame Title
		
                //add all
                currentPatient = p;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		//this.setSize(400, 450);
		
		JPanel h = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));	
			JLabel hTitle = new JLabel("Edit Patient");
//				GPSISFramework.getInstance();
//				hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
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
                        this.nhsFld = new module.Patient.IntegerField(20, 10);
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
                            PermanentPatient pp =(PermanentPatient) p;
                            this.isPermanentPatientFld.setSelected(true);
                            this.nhsFld.setText(pp.getNHSNumber());
                            this.doctor = pp.getDoctor();
                            this.doctorNameLbl.setText(doctor.getFirstName() +" "+doctor.getLastName());
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
                        addPatientForm.add(selectMotherBtn, new CC().wrap());
                        
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
			JButton addBtn = new JButton("Add!");
				addBtn.addActionListener(this);
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
		
		this.setLocationRelativeTo(null); // center window
		this.pack();
		this.setVisible(true);
                this.checkAllComponents();
                
	}
        
    public boolean checkAllComponents()
    {
//        this.textFields.add(usernameFld);
//        this.textFields.add(passwordFld);
//        this.textFields.add(firstNameFld);
//        this.textFields.add(addressFld);
        Component[] components = this.addPatientForm.getComponents();
        for(Component comp : components)
        {
            if(comp instanceof JTextField)
            {
                //Do some check
                JTextField tempTxtFld =(JTextField) comp;
                //Fix it up
                tempTxtFld.setText(tempTxtFld.getText().trim());
               // System.out.println(tempTxtFld.getText());
                
                //Check parsing, if error. Return false;
            }
        }
                
                System.out.println(this.dobFld.getModel().getValue());

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
            System.out.println("In PatientModule, the row is"+st.getSelectedRow());

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
                    List<StaffMember> smembers = StaffMemberModule.getStaffMembers();
                    this.doctor = smembers.get(st.getSelectedRow());
                    this.selDoctorButton.setText("Change Doctor");
                    this.doctorNameLbl.setText(this.doctor.getFirstName()+" "
                                              +this.doctor.getLastName());
                    this.doctorNameLbl.setVisible(true);
                }
            }  
            else if(st.tbl.getModel() instanceof module.Patient.PatientATM)
            {
                module.Patient.PatientATM pATM = (module.Patient.PatientATM)st.tbl.getModel();
                
                Patient patient = pATM.getData().get(st.getSelectedRow());

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
        this.checkAllComponents();
        if(e.getActionCommand().equals("Add!"))
        {
           
            if(!this.checkAllComponents())
            {
                JOptionPane.showMessageDialog(this, "Invalid Values!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                module.Patient.PatientATM pATM =(module.Patient.PatientATM) PatientModule.patientTable.getModel();
                
                pATM.addRow(this.buildPatientFromValues());
                this.dispose();
            }
        }
        else if(e.getActionCommand().equals("Select Doctor"))
        {
            StaffMemberATM sMM = null;
            try {
                sMM = new StaffMemberATM(StaffMemberDMO.getInstance().getAll());
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
            new SearchTable(this,PatientModule.buildPatientsTable(),
                    "Patient Search");
            this.lastActive = this.selectMotherBtn;
            this.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Select Father"))
        {
            new SearchTable(this,PatientModule.buildPatientsTable(),
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


    }

    public static void main (String [] args)
    {
        GPSISFramework GPSIS = new GPSISFramework();
        GPSISDataMapper.connectToDatabase();
            try {
                new EditPatient(PatientDMO.getInstance().getById(20));
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(EditPatient.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
	
}
