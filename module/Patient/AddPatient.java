/** TODO AddStaffMember
 * Opens a New Window displaying a Form to Add a New Staff Member
 * @author VJ
 */
package module.Patient;

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
import framework.GPSISDataMapper;
import framework.GPSISFramework;
import framework.GPSISPopup;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import module.Broadcastable;
import module.SearchTable;
import module.StaffMember.StaffMemberATM;
import object.StaffMember;

public class AddPatient extends GPSISPopup implements ActionListener,Broadcastable
{

	private static final long serialVersionUID = -8748112836660009010L;
	
	private JTextField usernameFld;
	private JPasswordField passwordFld;
	private JTextField firstNameFld;
	private JTextField lastNameFld;
	private JCheckBox isPermanent;
	private JDatePicker dobFld;
        private IntegerField nhsFld;
	private JCheckBox isPermanentPatientFld;
	private JComboBox<String> roleFld;
	private String[] roles = {"Receptionist", "Nurse", "Doctor"};
	private JSpinner holidayAllowanceFld;
        private JButton selDoctorButton;
        private JLabel doctorNameLbl;
        
        private StaffMember doctor;
	public AddPatient() 
        {
		super("Add Patient"); // Set the JFrame Title
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		//this.setSize(400, 450);
		
		JPanel h = new JPanel(new MigLayout());	
			JLabel hTitle = new JLabel("Add Patient");
//				GPSISFramework.getInstance();
//				hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
			h.add(hTitle, new CC().wrap());
			
		this.add(h, new CC().wrap());
		
		JPanel addPatientForm = new JPanel(new MigLayout());
			
			
			// First Name Label
			JLabel firstNameLbl = new JLabel("First Name: ");
			addPatientForm.add(firstNameLbl);
			// First Name Component
			this.firstNameFld = new JTextField(20);
			addPatientForm.add(this.firstNameFld, new CC().wrap());
			
			// Last Name Label
			JLabel lastNameLbl = new JLabel("Last Name: ");
			addPatientForm.add(lastNameLbl);
			// Last Name Component
			this.lastNameFld = new JTextField(20);
			addPatientForm.add(this.lastNameFld, new CC().wrap());
			
			// Is Permanent Label
			JLabel permanentPlbl = new JLabel("Permanent Patient: ");
			addPatientForm.add(permanentPlbl);
			// Is Permanent Component
                        this.isPermanentPatientFld = new JCheckBox("", false);
                        addPatientForm.add(isPermanentPatientFld, new CC().wrap());
			
                        //NHS Number Label:
                        JLabel nhsNumberLbl = new JLabel("NHS Number: ");
                        addPatientForm.add(nhsNumberLbl);

                        //NHS Number Component
                        this.nhsFld = new IntegerField(20);
                        this.nhsFld.setActionCommand("NHS");
                        this.nhsFld.setEnabled(false);
                        addPatientForm.add(this.nhsFld, new CC().wrap());
                        
                        //Doctor
                        this.selDoctorButton = new JButton("Select Doctor");
                        this.selDoctorButton.setActionCommand("Select Doctor");
                        this.selDoctorButton.setVisible(false);
                        this.selDoctorButton.setEnabled(false);
                        this.selDoctorButton.addActionListener(this);
                        addPatientForm.add(this.selDoctorButton);
                        
                        this.doctorNameLbl = new JLabel("Joe");
                        this.doctorNameLbl.setVisible(false);
                        addPatientForm.add(this.doctorNameLbl, new CC().wrap());
			// DoB Label
			JLabel dobLabel = new JLabel("Dob: ");
			addPatientForm.add(dobLabel);
                        
			// DoB Component
			this.dobFld = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
			addPatientForm.add((Component) this.dobFld, new CC().wrap());
			
			
			// Role Label
			JLabel roleLbl = new JLabel("Role: ");
			addPatientForm.add(roleLbl);
			
			// Role Component
			this.roleFld = new JComboBox<String>(this.roles);
			addPatientForm.add(this.roleFld, new CC().wrap());
			
			// Holiday Allowance Label
			JLabel holidayAllowanceLbl = new JLabel("Holiday Allowance: ");
			addPatientForm.add(holidayAllowanceLbl);
			
			// Holiday Allowance Component
			this.holidayAllowanceFld = new JSpinner(new SpinnerNumberModel(20, 5, 100, 1));
			addPatientForm.add(this.holidayAllowanceFld, new CC().wrap());
			
			// Add Button
			JButton addBtn = new JButton("Add!");
				addBtn.addActionListener(this);
			addPatientForm.add(addBtn);
                        
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
                                    //enable search for doctor.
                                }
                                else if(e.getStateChange() == ItemEvent.DESELECTED){
                                    nhsFld.setEnabled(false);
                                    nhsFld.setText("");
                                    selDoctorButton.setEnabled(false);
                                }
                                validate();
                                repaint();
                            }
                        });
                        
		this.add(addPatientForm, new CC());
		
		this.setLocationRelativeTo(null); // center window
		this.pack();
		this.setVisible(true);
	}
    /*
     * This is an example of how SearchTable communicates with AddPatient.
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
                
                List<StaffMember> smembers = StaffMemberModule.getStaffMembers();
                this.doctor = smembers.get(st.getSelectedRow());
                this.selDoctorButton.setText("Change Doctor");
                this.doctorNameLbl.setText(this.doctor.getFirstName()+" "
                                          +this.doctor.getLastName());
                this.doctorNameLbl.setVisible(true);
            }  
            else if(st.tbl.getModel() instanceof PatientATM)
            {
                PatientATM pATM = (PatientATM)st.tbl.getModel();
                
                Patient patient = pATM.getData().get(st.getSelectedRow());
                st.getSelectedRow();
               
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            // GET ALL THE VALUES
        System.out.println(this.nhsFld.getText());
        if(e.getActionCommand().equals("Add!"))
        {
            if(this.nhsFld.getText().length() !=7)
            {
                JOptionPane.showMessageDialog(this, "Invalid Values!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else 
            {

            }
        }
        else if(e.getActionCommand().equals("Select Doctor"))
        {
           // new SearchTable(this,StaffMemberModule.buildStaffMemberTable(),
             //       "Patient Search");
            this.setEnabled(false);
        }

    }

    public static void main (String [] args)
    {
        GPSISFramework GPSIS = new GPSISFramework();
        GPSISDataMapper.connectToDatabase();
        new AddPatient();
    }
	
}
