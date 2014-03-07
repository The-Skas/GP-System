package module.CalendarAppointments;

//import AppointmentsView;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import mapper.CalendarAppointmentDMO;
import framework.GPSISDataMapper;
import framework.GPSISFramework;
import framework.GPSISModuleMain; //why import this?
import framework.GPSISPopup;
import mapper.SQLBuilder;
import module.StaffMember.AddStaffMember;
import module.StaffMember.ViewStaffMember;
import object.CalendarAppointment;
import object.CareManagementAppointment;
import object.StaffMember;
import object.RoutineAppointment;
import exception.EmptyResultSetException;


public class AddAppointment extends GPSISPopup implements ActionListener{

	//constructor
	public AddAppointment(){
		super("Add a New Appointment"); // Set the JFrame Title
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));
		this.setSize(500, 200);
		
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel("Please select a type of appointment:");
        GPSISFramework.getInstance();
		label.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
		panel.add(label, new CC().wrap());
        
		this.add(panel, new CC().wrap()); // adding the header panel
        
        JButton b1 = new JButton("Routine");
        b1.addActionListener(this);
        b1.setActionCommand("Routine");
        JButton b2 = new JButton("Care Management");
        b2.addActionListener(this);
        b2.setActionCommand("Care Management");
        
        JPanel buttonsPanel = new JPanel(new MigLayout());
        buttonsPanel.add(b1); //Routine Appointment button
        buttonsPanel.add(b2); // Care Management button

        
     			this.add(buttonsPanel, new CC());
     			
     			this.setLocationRelativeTo(null); // center window
     			this.pack();
     			this.setVisible(true);	
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Routine":
				new AddRoutine();
				dispose();
				break;
			case "Care Management":
				new AddCareManagement();
				dispose();
				break;
		}
	}
}