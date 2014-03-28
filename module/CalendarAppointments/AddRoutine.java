package module.CalendarAppointments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import object.Register;
import object.RoutineAppointment;
import object.StaffMember;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import exception.EmptyResultSetException;
import exception.UserDidntSelectException;
import framework.GPSISFramework;
import framework.GPSISPopup;
import mapper.CalendarAppointmentDMO;
import mapper.StaffMemberDMO;
import mapper.PatientDMO;
import module.CalendarAppointmentsModule;

public class AddRoutine extends GPSISPopup implements ActionListener{
	
	private static final long serialVersionUID = 1L;
//	private JTextField patientField;
//	private JTextField doctorField;
	//private JDatePicker dateField;
	private Date date;
	
	static StaffMember selectedDoctor;
	static Patient selectedPatient;
	
	final JTextField patientField;
	final JTextField staffMemberField;
	
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
		
		JButton patientButton = new JButton("Select Patient");
		patientField = new JTextField(10);
		patientField.setEditable(false);
		
		JButton staffMemberButton = new JButton("Select Staff Member");
		staffMemberField = new JTextField(10);
		staffMemberField.setEditable(false);
		
		try {
			selectedPatient = PatientDMO.getInstance().getById(1);
			selectedDoctor = CalendarAppointmentsModule.getSelectedDoctor();
			
			String pName = selectedPatient.getFirstName() + " " + selectedPatient.getLastName();
			String dName = selectedDoctor.getName();
			
			patientField.setText(pName);
			staffMemberField.setText(dName);
			
		} catch (EmptyResultSetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
        staffMemberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	try {
            		StaffMember sM = module.StaffMember.SearchPane.doSearch();
            		staffMemberField.setText(sM.getName());
            		selectedDoctor = sM;
            		// More Logic here
            		} catch (UserDidntSelectException e) {
            		System.out.println("User Didnt Select a Staff Member");
            		} catch (EmptyResultSetException e) {
            		System.out.println("No Staff Members in Table");
            		}
            }
    });
        
        patientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	try {
            		Patient p = module.Patient.SearchPane.doSearch();
            		patientField.setText(p.getFirstName()+" "+p.getLastName());
            		selectedPatient = p;
            		// More Logic here
            		} catch (UserDidntSelectException e) {
            		System.out.println("User Didnt Select a Staff Member");
            		} catch (EmptyResultSetException e) {
            		System.out.println("No Staff Members in Table");
            		}
            }
    });
		
		staffMemberField.getDocument().addDocumentListener(new DocumentListener(){

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
		
		addRoutinePanel.add(patientButton);
		addRoutinePanel.add(patientField, "Wrap");
		addRoutinePanel.add(staffMemberButton);
		addRoutinePanel.add(staffMemberField, "wrap");

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
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
		/*
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
		
		this.setLocationRelativeTo(null); 
		*/
		this.pack();
		this.setVisible(true);	
	}
		
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		// get the values 
		String patientId = selectedPatient.getFirstName();
		String doctorId = selectedDoctor.getFirstName();
		
		String summary = summaryTextArea.getText();

		int pId = selectedPatient.getId();
		int dId = selectedDoctor.getId();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date d = now;
		
		try {
			d = dateFormat.parse(dynamicField.getText());
		} catch (ParseException e2) {
			JOptionPane.showMessageDialog(this, "ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
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
				
				if(selectedDoctor.isDoctor() || selectedDoctor.isNurse()) { // make sure selected staff member is a doctor or nurse
				
				//make sure all necessary fields are filled
				if (patientId.isEmpty() || doctorId.isEmpty())
				{
					JOptionPane.showMessageDialog(this, "Please fill in all necessary fields.", "Blank Input", JOptionPane.WARNING_MESSAGE);
				} else if(GPSISFramework.getInstance().isHoliday(d)) { // make sure selected date is not a holiday or training day 
					JOptionPane.showMessageDialog(this, "There are no opening hours on TrainingDay and Training days. Please, select another day.", "No opening hours on TrainingDay and Training Days", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sun")) { // make sure selected date isn't Sunday
					JOptionPane.showMessageDialog(this, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sat")) {
					try {							  // open a Saturday time slot picker
						slotField.setText(new HourTableSaturday(dId, dateString2).setPickedHour());						
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(this, "ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e1.printStackTrace();
					}				
				} else {
					try {							 // open a regular ordinary time slot picker 
						slotField.setText(new HourTable(dId, dateString2).setPickedHour());						
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(this, "ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e1.printStackTrace();
					}
						}
				} else {
					JOptionPane.showMessageDialog(this, "Appointments can only be scheduled with doctors or nurses. Please select another Staff Member.", "Wrong Staff Member", JOptionPane.WARNING_MESSAGE);
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
				
				// respect availability of doctors and nurses
				int a = 3; // int for availability - either 0, 1, 2 or 3, assume it's ALLDAY (3) if not set  
				
				try {	 
					 date = newFormatter.parse(dateTimeString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				try {
					a  = StaffMemberDMO.getInstance().getRegister(selectedDoctor, date).getAvailability();
				} catch (EmptyResultSetException e1) {
					a = Register.ALLDAY;
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
				} else if(a==0) {
					JOptionPane.showMessageDialog(this, "The doctor's personal availability is set to HOLIDAY for this particular date. Please select another date.", "Doctor is on Holiday today!", JOptionPane.WARNING_MESSAGE);
				} else if(a==1 && !(Integer.parseInt(hourParts[0])<12)) { // if doctor's availability is set to MORNING but an afternoon hour is selected
					JOptionPane.showMessageDialog(this, "The doctor's personal availability is set to MORNING for this particular date. Please select another date/time.", "Doctor is available in the morning today!", JOptionPane.WARNING_MESSAGE);
				} else if(a==2 && (Integer.parseInt(hourParts[0])<12)) { // if doctor's availability is set to AFTERNOON but a morning hour is selected
					JOptionPane.showMessageDialog(this, "The doctor's personal availability is set to AFTERNOON for this particular date. Please select another date/time.", "Doctor is available in the afternoon today!", JOptionPane.WARNING_MESSAGE);
				}
				
				else
				{
					// add the Routine Appointment
					try {
						 if(date.after(now)){ // make sure appointment is in the future
							RoutineAppointment app = new RoutineAppointment(date, fifteenMinutesLater, PatientDMO.getInstance().getById(pId), StaffMemberDMO.getInstance().getById(dId), summary);
							((CalendarAppointmentATM) CalendarAppointmentsModule.getAppointmentsTable().getModel()).addRow(app);
							JOptionPane.showMessageDialog(this, "Successfully added a new Routine Appointment.");
							dispose();
						} else {
							JOptionPane.showMessageDialog(this, "Appointments can only be made in the future. Please select another date/time.", "Appointment time", JOptionPane.WARNING_MESSAGE);
							slotField.setText(null); 
						}
					} catch (EmptyResultSetException e) {
						JOptionPane.showMessageDialog(this, "EmptyResultSetException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					}
				}				
				break;
		}
		
	}

}
