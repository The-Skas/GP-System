package module.CalendarAppointments;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import object.Patient;
import object.RoutineAppointment;
import object.StaffMember;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import exception.EmptyResultSetException;
import framework.GPSISFramework;
import framework.GPSISPopup;
import mapper.StaffMemberDMO;
import mapper.PatientDMO;
import module.Broadcastable;
import module.SearchTable;
import module.StaffMemberModule;
import module.Patient.AddPatient;
import module.Patient.PatientATM;
import module.StaffMember.StaffMemberATM;

public class AddRoutine extends GPSISPopup implements ActionListener, Broadcastable{
	
	private static final long serialVersionUID = 1L;
	private JTextField patientField;
	private JTextField doctorField;
	//private JDatePicker dateField;
	private Date date;
	
	private JTextField dynamicField;
	private JButton dynamicBtn;
	
	private JTextField slotField;	
	private JButton slotBtn;
	
	private JTextArea summaryTextArea;
	
	Date now = new Date();
	
	public AddRoutine() {
		super("Add a Routine Appointment");
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
						
		JPanel h = new JPanel(new MigLayout());	
		JLabel hTitle = new JLabel("Add a Routine Appointment");
		GPSISFramework.getInstance();
		hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
		h.add(hTitle, new CC().wrap());
		
		this.add(h, new CC().wrap());
		
		JPanel addRoutinePanel = new JPanel(new MigLayout());
		
		// patient id Label
		JLabel patientLabel = new JLabel("Patient ID: ");
		addRoutinePanel.add(patientLabel);
		// patient Component
		this.patientField = new JTextField(10);
		addRoutinePanel.add(this.patientField, new CC().wrap());
		
		// doctor id Label
		JLabel doctorLabel = new JLabel("Doctor ID: ");
		addRoutinePanel.add(doctorLabel);
		// doctor Component
		this.doctorField = new JTextField(10);
		doctorField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				slotField.setText(null);
				System.out.println("Doctor changed!");
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				slotField.setText(null);
				System.out.println("Doctor changed!");
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				slotField.setText(null);
				System.out.println("Doctor changed!");
			}
			
		}); 
		addRoutinePanel.add(this.doctorField, new CC().wrap());
		
		// select a date label
		//JLabel dateLabel = new JLabel("Select Date: ");
		//addRoutinePanel.add(dateLabel);
		
		// select a date component
		//this.dateField = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
		//addRoutinePanel.add((Component) this.dateField, new CC().wrap());
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// dynamicTesting...
		dynamicBtn = new JButton("Please select a date");
		this.dynamicField = new JTextField(10);
		this.dynamicField.setEditable(false);
		this.dynamicField.setText(dateFormat.format(now));
		addRoutinePanel.add(this.dynamicBtn, new CC());
		addRoutinePanel.add(this.dynamicField, new CC().wrap());
		dynamicBtn.addActionListener(this);
		dynamicBtn.setActionCommand("D");
		dynamicField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				slotField.setText(null);
				System.out.println("Date changed!");
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				slotField.setText(null);
				System.out.println("Date changed!");
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				slotField.setText(null);
				System.out.println("Date changed!");
			}
			
		}); 
		
		slotBtn = new JButton("Please select a time slot");
		this.slotField = new JTextField(5);
		slotField.setEditable(false);
		addRoutinePanel.add(this.slotBtn, new CC());
		addRoutinePanel.add(this.slotField, new CC().wrap());
		slotBtn.addActionListener(this);
		slotBtn.setActionCommand("Select Slot");
		
		// summary text box
		JLabel summaryLabel = new JLabel("Summary: ");
		addRoutinePanel.add(summaryLabel);
		this.summaryTextArea = new JTextArea(5, 20);
		this.summaryTextArea.setText(" ");
		JScrollPane scrollPane = new JScrollPane(summaryTextArea);
		addRoutinePanel.add(scrollPane, new CC().wrap());
		
		// Add Button
		JButton addBtn = new JButton("Add Appointment");
		addBtn.addActionListener(this);
		addRoutinePanel.add(addBtn);
		addBtn.setActionCommand("Add Appointment");
		
		this.add(addRoutinePanel, new CC());
		
		//this.setLocationRelativeTo(null); 
		this.pack();
		this.setVisible(true);	
	}
		
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		// get the values 
		String patientId = this.patientField.getText().trim();
		String doctorId = this.doctorField.getText().trim();
		
		String summary = summaryTextArea.getText();
		
		//////////////////////////////////////// if ID fields not filled with ints, will throw an error TODO

		int pId = Integer.parseInt(patientId);
		int dId = Integer.parseInt(doctorId);
		
		//Date d = (Date) this.dateField.getModel().getValue();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date d = now;
		try {
			d = dateFormat.parse(dynamicField.getText());
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String dateString = dateFormat.format(d);
		DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		String dateString2 = dateFormat2.format(d);
		DateFormat sundayChecker = new SimpleDateFormat("EEE");
		String isItSunday = sundayChecker.format(d);
		
		switch (ae.getActionCommand())
		{
			case "D":
				System.out.println("Dynamic test pressed");
				dynamicField.setText(new DatePicker().setPickedDate());
				
				break;
			case "Select Slot":
				//make sure all necessary fields are filled
				if (patientId.isEmpty() || doctorId.isEmpty())
				{
					JOptionPane.showMessageDialog(this, "Please fill in all necessary fields.", "Blank Input", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sun")) { // make sure selected date isn't Sunday
					JOptionPane.showMessageDialog(this, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sat")) {
					try {							  // open a Saturday time slot picker
						slotField.setText(new HourTableSaturday(dId, dateString2).setPickedHour());						
					} catch (ParseException e1) {
						System.out.println("Parse exception");
						e1.printStackTrace();
					}				
				} else {
					try {							 // open a regular ordinary time slot picker 
						slotField.setText(new HourTable(dId, dateString2).setPickedHour());						
					} catch (ParseException e1) {
						System.out.println("Parse exception");
						e1.printStackTrace();
					}
						}
				break;
				
			case "Add Appointment":																
				
				if((slotField.getText()).equals("")) // if no slot field is selected				
					JOptionPane.showMessageDialog(this, "Please select a time slot.", "Appointment time", JOptionPane.WARNING_MESSAGE);												
				
				String dateTimeString = dateString + " " + slotField.getText(); // concatenate the formatted date and time 
																				// now I have a String with the date and time in a format similar to the timestamp format											
				String[] hourParts = slotField.getText().split(":");
				// convert the dateTimeString to Date
				SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				
				
				try {	 
					 date = newFormatter.parse(dateTimeString);
			 
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				final long minuteMilliseconds = 60000; //millisecs

				long t = date.getTime();
				Date fifteenMinutesLater = new Date(t + (15 * minuteMilliseconds)); // a Routine Appointment is always 15 minutes long
				
				//make sure all necessary fields are filled
				if (patientId.isEmpty() || doctorId.isEmpty()){
					JOptionPane.showMessageDialog(this, "Please fill in all necessary fields.", "Blank Input", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sun")) { // make sure selected date isn't Sunday
					JOptionPane.showMessageDialog(this, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sat") && !(Integer.parseInt(hourParts[0])<12)){ // make sure you can't add an afternoon appointment on Saturday						
					 //if selected hour isn't less than 12
						slotField.setText(null);
						JOptionPane.showMessageDialog(this, "There are no afternoon surgery hours on Saturdays. Please select another date/time", "No afternoon surgery hours on Saturdays", JOptionPane.WARNING_MESSAGE);	
				}
				else
				{
					// add the Routine Appointment
					try {
						 if(date.after(now)){ // make sure appointment is in the future
							new RoutineAppointment(date, fifteenMinutesLater, PatientDMO.getInstance().getById(pId), StaffMemberDMO.getInstance().getById(dId), summary);
							JOptionPane.showMessageDialog(this, "Successfully added a new Routine Appointment.");
							dispose();
						} else {
							JOptionPane.showMessageDialog(this, "Appointments can only be made in the future. Please select another date/time.", "Appointment time", JOptionPane.WARNING_MESSAGE);
							slotField.setText(null); 
						}
					} catch (EmptyResultSetException e) {
						System.out.println("EmptyResultException");
						e.printStackTrace();
					}
				}				
				break;
		}
		
	}



	@Override
	public void broadcast(Object obj) {
		if(obj instanceof SearchTable)
        {
			//TODO AddPatient has been changed, revert the changes!
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
                /*
                List<StaffMember> smembers = StaffMemberModule.getStaffMembers();
                AddPatient.doctor = smembers.get(st.getSelectedRow());
                AddPatient.selDoctorButton.setText("Change Doctor");
                AddPatient.doctorNameLbl.setText(AddPatient.doctor.getFirstName()+" "
                                          +AddPatient.doctor.getLastName());
                ((Component) this.doctorNameLbl).setVisible(true);*/
            }  
            else if(st.tbl.getModel() instanceof PatientATM)
            {
                PatientATM pATM = (PatientATM)st.tbl.getModel();
                
                Patient patient = pATM.getData().get(st.getSelectedRow());
                st.getSelectedRow();
               
            }
        }		
	}

}


//TODO I really need a method which checks the doctors' availability taking into account the CareManagement appointments
// as well! That's part of the specifications so even though TAs tell me not to worry I NEED IT!!!
