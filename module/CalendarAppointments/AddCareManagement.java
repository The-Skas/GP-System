package module.CalendarAppointments;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.WindowConstants;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import framework.GPSISFramework;
import framework.GPSISPopup;

public class AddCareManagement extends GPSISPopup implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JTextField patientField;
	private JTextField doctorField;
	private JDatePicker dateField;
	private JSpinner time;
	
	public AddCareManagement() {
		super("Add a Care Management Appointment");
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		
		JPanel h = new JPanel(new MigLayout());	
		JLabel hTitle = new JLabel("Add a Care Management Appointment");
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
		addRoutinePanel.add(this.doctorField, new CC().wrap());
		
		// select a date label
		JLabel dateLabel = new JLabel("Select Date: ");
		addRoutinePanel.add(dateLabel);
		// select a date component
		this.dateField = JDateComponentFactory.createJDatePicker(JDateComponentFactory.createDateModel(new Date()));
		addRoutinePanel.add((Component) this.dateField, new CC().wrap());
		
		// select start time label
		JLabel startTimeLabel = new JLabel("Select Start Time: ");
		addRoutinePanel.add(startTimeLabel);
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.MINUTE);
		time = new JSpinner();
		time.setModel(model);
		time.setEditor(new JSpinner.DateEditor(time, "HH:mm"));
		addRoutinePanel.add(time, new CC().wrap());
		
		// Add Button
		JButton addBtn = new JButton("Add Appointment");
		addBtn.addActionListener(this);
		addRoutinePanel.add(addBtn);
		
		this.add(addRoutinePanel, new CC());
		
		this.setLocationRelativeTo(null); // center window
		this.pack();
		this.setVisible(true);
	
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// get the values
		String patientId = this.patientField.getText().trim();
		String doctorId = this.doctorField.getText().trim();
		Date appDate = (Date) this.dateField.getModel().getValue();
		int appTime = (int)this.time.getValue();
		System.out.println(appTime);
		
		// all fields should be filled
		
		if (patientId.isEmpty() || doctorId.isEmpty() || ((Map<String, Font>) appDate).isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Some fields are blank! Staff Member not created!", "Blank Input", JOptionPane.WARNING_MESSAGE);
		}
		
	}

}
