
package module.CalendarAppointments;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import object.CalendarAppointment;
import object.RoutineAppointment;

import framework.GPSISFramework;
import framework.GPSISPopup;

public class ViewEditAppointment extends GPSISPopup implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6157890370297592454L;
	// Set Fields
	private JTextField patientField;
	private JTextField doctorField;
	
	private Date date;
	
	JButton changePatient;
	JButton changeDoctor;
	
	private JTextField dateField;
	private JButton dateBtn;
	private JTextField slotField;	
	private JButton slotBtn;
	
	private JTextArea summaryTextArea;
	
	Date now = new Date();
	
	private RoutineAppointment selectedCalendarAppointment;

	public ViewEditAppointment(CalendarAppointment cA) {
		
		super("Modify Calendar Appointment");
		
		this.selectedCalendarAppointment = (RoutineAppointment) cA;
		
		System.out.println("View//Edit Appointmnet");
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));

		JPanel h = new JPanel(new MigLayout());	
		JLabel hTitle = new JLabel("View/Edit a Routine Appointment");
		GPSISFramework.getInstance();
		hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
		h.add(hTitle, new CC().wrap());
		
		this.add(h, new CC().wrap());
		
		JPanel viewEditPanel = new JPanel(new MigLayout());
		
		// change patient button
		changePatient = new JButton("Change Patient");
		viewEditPanel.add(changePatient);
		changePatient.addActionListener(this);
		changePatient.setActionCommand("Change Patient");
		
		// patient Component
		this.patientField = new JTextField(20);
		this.patientField.setText(((RoutineAppointment) cA).getPatient());
		this.patientField.setEditable(false);
		viewEditPanel.add(this.patientField, new CC().wrap());
		
		// change doctor button
		changeDoctor = new JButton("Change Doctor");
		viewEditPanel.add(changeDoctor);
		changeDoctor.addActionListener(this);
		changeDoctor.setActionCommand("Change Doctor");
		
		// doctor Component
		this.doctorField = new JTextField(20);
		this.doctorField.setText(((RoutineAppointment) cA).getDoctor());
		this.doctorField.setEditable(false);
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
		viewEditPanel.add(this.doctorField, new CC().wrap());
		
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
		JButton summaryBtn = new JButton("Edit Summary");
		viewEditPanel.add(summaryBtn);
		summaryBtn.addActionListener(this);
		summaryBtn.setActionCommand("Edit Summary");
		this.summaryTextArea = new JTextArea(5, 20);
		summaryTextArea.setText(((RoutineAppointment) cA).getSummary());
		summaryTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(summaryTextArea);
		viewEditPanel.add(scrollPane, new CC().wrap());
		
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
		
		this.add(viewEditPanel, new CC());
		
		//this.setLocationRelativeTo(null); 
		this.pack();
		this.setVisible(true);	
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Change Date":
				System.out.println("Dynamic test pressed");
				dateField.setText(new DatePicker().setPickedDate());				
				break;
			case "Change Patient":				
				break;
			case "Change Doctor":				
				break;
			case "Change Slot":
				break;
			case "Edit Summary":
				summaryTextArea.setEditable(true);
				break;
			case "Save":
				break;
			case "Delete Appointment":
				//this.remove();
				break;
			
			
		}
	}
	
	
	/** remove
	 * Removes a the Object from the Database
	 */
	/*
	public void remove()
	{
		if (JOptionPane.showConfirmDialog(this, 
				"Are you sure that you wish to remove " + selectedStaffMember.getUsername() + "?", 
				"Are you sure?", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			StaffMemberDMO.getInstance().removeById(selectedStaffMember.getId());
			((StaffMemberATM) StaffMemberModule.getStaffMemberTable().getModel()).removeRow(selectedStaffMember); // use the removeRow method in ATM
			dispose();
		}
		
	}*/

}
