	
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import mapper.CalendarAppointmentDMO;
import mapper.PatientDMO;
import mapper.StaffMemberDMO;
import module.CalendarAppointmentsModule;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import object.CalendarAppointment;
import object.Patient;
import object.Register;
import object.RoutineAppointment;
import object.StaffMember;
import exception.EmptyResultSetException;
import exception.UserDidntSelectException;
import framework.GPSISFramework;
import framework.GPSISPopup;

public class ViewEditAppointment implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6157890370297592454L;
	
	// Set Fields
	private JTextField patientField;
	private JTextField staffMemberField;
	
	private Date date;
	
	JButton staffMemberButton;
	JButton patientButton;
	
	private JButton dateBtn;
	private JButton slotBtn;
	
	private JTextField dateField;
	private JTextField slotField;	
	
	private JTextArea summaryTextArea;
	
	static StaffMember selectedDoctor;
	static Patient selectedPatient;
	
	Date now = new Date();
	
	private RoutineAppointment selectedCalendarAppointment;
	
	JDialog d;

	public ViewEditAppointment(CalendarAppointment cA) {
		
		// initialize selectedPatient and selectedDoctor
		
		
		
		d = new JDialog();
		d.setTitle("View/Edit Routine Calendar Appointment");
		d.setLayout(new MigLayout());
		
		JPanel h = new JPanel(new MigLayout());	
		JLabel hTitle = new JLabel("View/Edit an Appointment");
		GPSISFramework.getInstance();
		hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
		h.add(hTitle, new CC().wrap());
		
		JPanel viewEditPanel = new JPanel(new MigLayout());
		
		if (cA instanceof RoutineAppointment){ // if appointment is routine
		selectedDoctor = ((RoutineAppointment) cA).getDoctorObject();
		selectedPatient = ((RoutineAppointment) cA).getPatientObject();
		this.selectedCalendarAppointment = (RoutineAppointment) cA;
		
		 patientButton = new JButton("Change Patient");
		 staffMemberButton = new JButton("Change Staff Member");
		
		patientField = new JTextField(10);
		staffMemberField = new JTextField(10);
		
		patientField.setText(((RoutineAppointment) cA).getPatient());
		staffMemberField.setText(selectedDoctor.getName());
		
		
		patientField.setEditable(false);
		staffMemberField.setEditable(false);

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
		
		
		viewEditPanel.add(patientButton);
		viewEditPanel.add(patientField, "Wrap");
		viewEditPanel.add(staffMemberButton);
		viewEditPanel.add(staffMemberField, "wrap");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dateBtn = new JButton("Change date");
		this.dateField = new JTextField(10);
		dateField.setEditable(false);
		dateField.setText(dateFormat.format(cA.getStartTime()));
		viewEditPanel.add(this.dateBtn, new CC());
		viewEditPanel.add(this.dateField, new CC().wrap());
		dateBtn.addActionListener(this);
		dateBtn.setActionCommand("Change Date");
		dateField.getDocument().addDocumentListener(new DocumentListener(){

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
		
		DateFormat slotFormat = new SimpleDateFormat("HH:mm");
		slotBtn = new JButton("Change time slot");
		this.slotField = new JTextField(5);
		slotField.setText(slotFormat.format(cA.getStartTime()));
		slotField.setEditable(false);		
		viewEditPanel.add(this.slotBtn, new CC());
		viewEditPanel.add(this.slotField, new CC().wrap());
		slotBtn.addActionListener(this);
		slotBtn.setActionCommand("Change Slot");
		
		// summary text box
		JLabel summary = new JLabel("Summary/Notes: ");
		viewEditPanel.add(summary);		
		this.summaryTextArea = new JTextArea(5, 20);
		summaryTextArea.setText(((RoutineAppointment) cA).getSummary());
		summaryTextArea.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(summaryTextArea);
		viewEditPanel.add(scrollPane, new CC().wrap());	
		
		this.d.setLocationRelativeTo(null); 
		
		// Add Button
		JButton addBtn = new JButton("Save");
		addBtn.addActionListener(this);
		viewEditPanel.add(addBtn);
		addBtn.setActionCommand("Save");
		
		// Delete Button
		JButton deleteBtn = new JButton("Delete Appointment");
		deleteBtn.addActionListener(this);
		viewEditPanel.add(deleteBtn);
		deleteBtn.setActionCommand("Delete Appointment");	
		
		d.add(viewEditPanel, new CC());
		d.pack();
		d.setModal(true);
				
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - d.getWidth()) / 2;
		final int y = (screenSize.height - d.getHeight()) / 2;
		d.setLocation(x, y);
		
		d.setVisible(true);
			
		} else {
			
			JOptionPane.showMessageDialog( viewEditPanel, "Viewing/Editing Care Management Appointments not available from this module.", "View/Edit CM Appointments not available", JOptionPane.WARNING_MESSAGE);
			d.dispose();
			System.out.println("It's a Care Management Appointment!");
		}
		

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
			d = dateFormat.parse(dateField.getText());
		} catch (ParseException e2) {
			System.out.println("Parse Exception");
			e2.printStackTrace();
		}
		
		String dateString = dateFormat.format(d);
		DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		String dateString2 = dateFormat2.format(d);
		DateFormat sundayChecker = new SimpleDateFormat("EEE");
		String isItSunday = sundayChecker.format(d);
		
		System.out.println("Action performed");
		
		switch (ae.getActionCommand())
		{
			case "Change Date":
				//System.out.println("Dynamic test pressed");
				dateField.setText(new DatePicker().setPickedDate());				
				break;
			case "Change Slot":
				
				if(selectedDoctor.isDoctor() || selectedDoctor.isNurse()) { // make sure selected staff member is a doctor or nurse
				
				//make sure all necessary fields are filled
				if (patientId.isEmpty() || doctorId.isEmpty())
				{
					JOptionPane.showMessageDialog(  dateBtn, "Please fill in all necessary fields.", "Blank Input", JOptionPane.WARNING_MESSAGE);
				} else if(GPSISFramework.getInstance().isHoliday(d)) { // make sure selected date is not a holiday or training day 
					JOptionPane.showMessageDialog(dateBtn, "There are no opening hours on TrainingDay and Training days. Please, select another day.", "No opening hours on TrainingDay and Training Days", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sun")) { // make sure selected date isn't Sunday
					JOptionPane.showMessageDialog(dateBtn, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sat")) {
					try {							  // open a Saturday time slot picker
						slotField.setText(new HourTableSaturday(dId, dateString2).setPickedHour());						
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(dateBtn, "ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e1.printStackTrace();
					}				
				} else {
					try {							 // open a regular ordinary time slot picker 
						slotField.setText(new HourTable(dId, dateString2).setPickedHour());						
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(dateBtn, "ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e1.printStackTrace();
					}
						}
				} else {
					JOptionPane.showMessageDialog(dateBtn, "Appointments can only be scheduled with doctors or nurses. Please select another Staff Member.", "Wrong Staff Member", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case "Edit Summary":
				summaryTextArea.setEditable(true);
				break;
			case "Save":
				
				if((slotField.getText()).equals("")) // if no slot field is selected				
					JOptionPane.showMessageDialog(dateBtn, "Please select a time slot.", "Appointment time", JOptionPane.WARNING_MESSAGE);												
				
				String dateTimeString = dateString + " " + slotField.getText(); // concatenate the formatted date and time 
																				// now I have a String with the date and time in a format similar to the timestamp format											
				String[] hourParts = slotField.getText().split(":");
				// convert the dateTimeString to Date
				SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy/MM/dd");
				
				// respect availability of doctors and nurses
				int a = 3; // int for availability - either 0, 1, 2 or 3, assume it's ALLDAY (3) if not set  
				
				try {	 
					 date = newDateFormatter.parse(dateTimeString.trim());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				try {
					a  = StaffMemberDMO.getInstance().getRegister(selectedDoctor, date).getAvailability();
				} catch (EmptyResultSetException e1) { // If there is no current Availability set, set as Full Availability
					a = Register.ALLDAY;
				}
				
				final long minuteMilliseconds = 60000; //millisecs

				long t = date.getTime();
				Date fifteenMinutesLater = new Date(t + (15 * minuteMilliseconds)); // a Routine Appointment is always 15 minutes long
				
				//make sure all necessary fields are filled
				if (patientId.isEmpty() || doctorId.isEmpty()){
					JOptionPane.showMessageDialog( dateBtn, "Please fill in all necessary fields.", "Blank Input", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sun")) { // make sure selected date isn't Sunday
					JOptionPane.showMessageDialog(dateBtn, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
				} else if(isItSunday.equals("Sat") && !(Integer.parseInt(hourParts[0])<12)){ // make sure you can't add an afternoon appointment on Saturday						
					 //if selected hour isn't less than 12
						slotField.setText(null);
						JOptionPane.showMessageDialog(dateBtn, "There are no afternoon surgery hours on Saturdays. Please select another date/time", "No afternoon surgery hours on Saturdays", JOptionPane.WARNING_MESSAGE);	
				} else if(a==0) {
					JOptionPane.showMessageDialog(dateBtn, "The doctor's personal availability is set to HOLIDAY for this particular date. Please select another date.", "Doctor is on Holiday today!", JOptionPane.WARNING_MESSAGE);
				} else if(a==1 && !(Integer.parseInt(hourParts[0])<12)) { // if doctor's availability is set to MORNING but an afternoon hour is selected
					JOptionPane.showMessageDialog(dateBtn, "The doctor's personal availability is set to MORNING for this particular date. Please select another date/time.", "Doctor is available in the morning today!", JOptionPane.WARNING_MESSAGE);
				} else if(a==2 && (Integer.parseInt(hourParts[0])<12)) { // if doctor's availability is set to AFTERNOON but a morning hour is selected
					JOptionPane.showMessageDialog(dateBtn, "The doctor's personal availability is set to AFTERNOON for this particular date. Please select another date/time.", "Doctor is available in the afternoon today!", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					if(date.after(now)){ // make sure appointment is in the future
						//new RoutineAppointment(date, fifteenMinutesLater, PatientDMO.getInstance().getById(pId), StaffMemberDMO.getInstance().getById(dId), summary);
						//StaffMemberDMO.getInstance().removeById(selectedStaffMember.getId());
						System.out.println("Removing Appointment: " + selectedCalendarAppointment.getId());
						CalendarAppointmentDMO.getInstance().removeById(selectedCalendarAppointment.getId());	
						new RoutineAppointment(date, fifteenMinutesLater, selectedPatient, selectedDoctor, summary);
						JOptionPane.showMessageDialog( dateBtn, "Appointment saved successfully.");						 
						this.d.dispose();
					} else {
						JOptionPane.showMessageDialog(dateBtn, "Appointments can only be made in the future. Please select another date/time.", "Appointment time", JOptionPane.WARNING_MESSAGE);
						slotField.setText(null); 
					}
				}
				
				break;
			case "Delete Appointment":
				if (JOptionPane.showConfirmDialog(dateBtn, "Are you sure that you wish to remove this appointment?", "Are you sure?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				{
					CalendarAppointmentDMO.getInstance().removeById(selectedCalendarAppointment.getId());	
					JOptionPane.showMessageDialog( dateBtn, "Appointment deleted.");
					this.d.dispose();
				}
				break;
			
			
		}
	}

}
