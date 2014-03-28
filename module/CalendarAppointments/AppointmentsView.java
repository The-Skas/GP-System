package module.CalendarAppointments;

//import AppointmentsView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import mapper.CalendarAppointmentDMO; //why import this?
import framework.GPSISPopup;
import object.CalendarAppointment;
import exception.EmptyResultSetException;


public class AppointmentsView extends GPSISPopup implements ActionListener, ListSelectionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static List<CalendarAppointment> calendarAppointments;
	private static JTable calendarAppointmentsTable;
	private static CalendarAppointmentATM cAM; 
	private static JButton modifyAppointmentButton; 
	
	//constructor
	public AppointmentsView(){
		
		super("A list of all appointments"); // Set the JFrame Title
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(255, 255, 255));
		this.setSize(100, 10);
		
		JPanel allAppointmentsMainPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		//TODO why is this not working?
		//allAppointmentsMainPanel.setSize(new Dimension(900, 900));
		
		// List View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		calendarAppointmentsTable = this.buildCalendarAppointmentsTable();
		calendarAppointmentsTable.getSelectionModel().addListSelectionListener(this);
		leftPanel.add(new JScrollPane(calendarAppointmentsTable), new CC().span().grow());
		allAppointmentsMainPanel.add(leftPanel, new CC().span().grow());
		
		//not working as well
		leftPanel.setSize(new Dimension(900, 900));
	
	// Controls (RIGHT PANE)
	JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
	JButton addAppointmentButton = new JButton("Add an Appointment");
	addAppointmentButton.addActionListener(this);
	addAppointmentButton.setActionCommand("Add an Appointment");
	rightPanel.add(addAppointmentButton, new CC().wrap());
		
		modifyAppointmentButton = new JButton("View/Edit");
		modifyAppointmentButton.addActionListener(this);
		modifyAppointmentButton.setActionCommand("View/Edit");
		modifyAppointmentButton.setVisible(false);
		rightPanel.add(modifyAppointmentButton);
		
	allAppointmentsMainPanel.add(rightPanel, new CC().dockEast());
	
	this.getContentPane().add(allAppointmentsMainPanel, new CC().wrap());
	
	this.pack();
	this.setVisible(true);
	
	this.setLocationRelativeTo(null);
	
	}

	public static List<CalendarAppointment> getCalendarAppointments()
	{
		return calendarAppointments;
	}
	
	public static JTable getCalendarAppointmentsTable()
	{
		return AppointmentsView.calendarAppointmentsTable;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		modifyAppointmentButton.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add an Appointment":
				new AddRoutine();
				break;
			case "View/Edit":
				CalendarAppointment cA = calendarAppointments.get(calendarAppointmentsTable.getSelectedRow());				
				new ViewEditAppointment(cA);
				break;
		}
	}
	
	private JTable buildCalendarAppointmentsTable()
	{
		try {			
			calendarAppointments = CalendarAppointmentDMO.getInstance().getAll();
						
			cAM = new CalendarAppointmentATM(calendarAppointments);
			JTable cAT = new JTable (cAM);
			//cAT.setAutoCreateRowSorter(true);
			
			// set column widths
			cAT.getColumnModel().getColumn(0).setMinWidth(30);
			cAT.getColumnModel().getColumn(0).setMaxWidth(50);
			cAT.getColumnModel().getColumn(0).setPreferredWidth(40);			
			
			cAT.getColumnModel().getColumn(1).setMinWidth(30);
			cAT.getColumnModel().getColumn(1).setMaxWidth(70);
			cAT.getColumnModel().getColumn(1).setPreferredWidth(70);
			
			cAT.getColumnModel().getColumn(2).setMinWidth(40);
			cAT.getColumnModel().getColumn(2).setMaxWidth(50);
			cAT.getColumnModel().getColumn(2).setPreferredWidth(50);
			
			cAT.getColumnModel().getColumn(3).setMinWidth(150);
			cAT.getColumnModel().getColumn(3).setMaxWidth(150);
			cAT.getColumnModel().getColumn(3).setPreferredWidth(150);
			
			cAT.getColumnModel().getColumn(4).setMinWidth(150);
			cAT.getColumnModel().getColumn(4).setMaxWidth(150);
			cAT.getColumnModel().getColumn(4).setPreferredWidth(150);
			
			
			return cAT;
		} catch (EmptyResultSetException e) {
			return null;
		}
		
		
	}


}