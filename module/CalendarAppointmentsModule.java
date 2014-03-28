package module;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import mapper.CalendarAppointmentDMO;
import mapper.TrainingDayDMO;
import module.CalendarAppointments.AddRoutine;
import module.CalendarAppointments.CalendarAppointmentATM;
import module.CalendarAppointments.DailyView;
import module.CalendarAppointments.DailyViewSaturday;
import module.CalendarAppointments.TrainingDayPanel;
import module.CalendarAppointments.ViewEditAppointment;
import module.StaffMember.*;
import object.CalendarAppointment;
import object.TrainingDay;
import object.StaffMember;
import exception.EmptyResultSetException;
import exception.UserDidntSelectException;
import framework.GPSISFramework;
import framework.GPSISModuleMain;
import groovyjarjarcommonscli.ParseException;
import module.CalendarAppointments.*;

public class CalendarAppointmentsModule extends GPSISModuleMain implements ActionListener, ListSelectionListener, DocumentListener {
	
	//TODO delete after completing testing
	
	public static List<Date> publicHolidays = new ArrayList<Date>();
	
	private static final long serialVersionUID = 1L;
	private static List<CalendarAppointment> calendarAppointments;
	private static JTable calendarAppointmentsTable;
	private static CalendarAppointmentATM cAM; 
	private static JButton modifyAppointmentButton; 
	private JTextField								textQuery			= new JTextField(30);
	public static TableRowSorter<CalendarAppointmentATM>	sorter;
	private static int								selectedRowInModel	= 0;
	static	Calendar cal = java.util.Calendar.getInstance(); 

	static StaffMember selectedDoctor;
	
	Date now = new Date();

	@Override
	public JPanel getModuleView() {
		JPanel appointmentsModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		// Table View
		JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		leftPanel.add(new JLabel("Search: "), new CC().dockNorth());
		leftPanel.add(textQuery, new CC().span().wrap().dockNorth());
		try {
			calendarAppointments = CalendarAppointmentDMO.getInstance().getAll();
			CalendarAppointmentATM cAM = new CalendarAppointmentATM(calendarAppointments);
			calendarAppointmentsTable = new JTable(cAM);
			sorter = new TableRowSorter<CalendarAppointmentATM>(cAM);
			calendarAppointmentsTable.setRowSorter(sorter);
			calendarAppointmentsTable.getSelectionModel().addListSelectionListener(this);
			
		} catch (EmptyResultSetException e) {
			calendarAppointmentsTable = new JTable(new CalendarAppointmentATM(new ArrayList<CalendarAppointment>()));
		}

		leftPanel.add(new JScrollPane(calendarAppointmentsTable), new CC().span().grow());
		appointmentsModuleView.add(leftPanel, new CC().span().grow());

		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		
		JButton addRoutine = new JButton("Add Appointment");
		addRoutine.addActionListener(this);
		addRoutine.setActionCommand("Add Appointment");

		modifyAppointmentButton = new JButton("View/Edit");
		modifyAppointmentButton.addActionListener(this);
		modifyAppointmentButton.setActionCommand("View/Edit");
		modifyAppointmentButton.setVisible(false);
		
		JButton showCalendar = new JButton("Change Date");
		JButton showAppointments = new JButton("Show all appointments");
		final JButton dailyView = new JButton("Daily View");
		final JButton weeklyView = new JButton("WeeklyView");
		final JButton monthlyView = new JButton("MonthlyView");
		
		JButton staffMemberButton = new JButton("Staff Member");
		final JTextField staffMemberField = new JTextField(10);
		staffMemberField.setEditable(false);
		staffMemberField.setText(currentUser.getName()); 
		
		selectedDoctor = currentUser;
		
		if(selectedDoctor.isDoctor() || selectedDoctor.isNurse()) { // Receptionists and Office Managers don't have appointments
			dailyView.setEnabled(true);
			weeklyView.setEnabled(true);
			monthlyView.setEnabled(true);
		} else {
			dailyView.setEnabled(false);
			weeklyView.setEnabled(false);
			monthlyView.setEnabled(false);
		}
		
		JButton determineTrainingDays = new JButton("Determine Training Days");
		determineTrainingDays.setToolTipText("Training Days can only be determined by Office Manager");
		
		// check if logged in user is Office Manager
		// if he is, he CAN determine training days
		// otherwise, he CAN'T determine training days
		
		if(currentUser.isOfficeManager()){
			determineTrainingDays.setEnabled(true);
		}	
		else {
			determineTrainingDays.setEnabled(false);
		}
		
		final JTextField dateField = new JTextField(10);
		dateField.setEditable(false);
		
		final DateFormat yMD = new SimpleDateFormat("yyyy/MM/dd");
		final DateFormat sundayChecker = new SimpleDateFormat("EEE");
		dateField.setText(yMD.format(now));
		
		// add action listeners
		
		 staffMemberButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent ae) {
	            	try {
	            		StaffMember sM = module.StaffMember.SearchPane.doSearch();
	            		staffMemberField.setText(sM.getName());
	            		selectedDoctor = sM;

	            		if(selectedDoctor.isDoctor() || selectedDoctor.isNurse()) { // Receptionists and Office Managers don't have appointments
	            			dailyView.setEnabled(true);
	            			weeklyView.setEnabled(true);
	            			monthlyView.setEnabled(true);
	            		} else {
	            			dailyView.setEnabled(false);
	            			weeklyView.setEnabled(false);
	            			monthlyView.setEnabled(false);
	            		}
	            		
	            		
	            		} catch (UserDidntSelectException e) {
	            		System.out.println("User Didnt Select a Staff Member");
	            		} catch (EmptyResultSetException e) {
	            		System.out.println("No Staff Members in Table");
	            		}
	            }
	    });
			
	        dailyView.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent ae) {
	                    try { 
	                    	Date appDate = yMD.parse(dateField.getText());
	           			 String appString = sundayChecker.format(appDate);

	           		    SimpleDateFormat sDF = new SimpleDateFormat("yyyy/MM/dd");  
	           		    String appDateString = sDF.format(appDate);
	           		     
	           		     Color blueishInk = new Color(69, 65, 107);
	           		         
	           		        // convert a String to a Date
	           		       // Date date = sDF.parse(day);
	           		        
	           		        // convert a Date to a String
	           		        DateFormat sDF1 = new SimpleDateFormat("EEE dd MMMM yyyy");
	           		        String s = sDF1.format(appDate);  
	                    	
	                     		if(GPSISFramework.getInstance().isHoliday(appDate))
	                     			new TrainingDayPanel(appDateString);
	                     		else if(appString.equals("Sat"))
	                    			new DailyViewSaturday(selectedDoctor.getId(), dateField.getText());
	                    		else if(appString.equals("Sun"))
	                    			JOptionPane.showMessageDialog( dateField, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
	                    		else
	                    			new DailyView(selectedDoctor.getId(), dateField.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog( dateField, "NumberFormatException or ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
							e.printStackTrace();
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            }
	    });
	        
	        weeklyView.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent ae) {
	                
	                			try {
									new WeeklyView(selectedDoctor.getId(), dateField.getText());
								} catch (java.text.ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

	        }
	});
			
	        monthlyView.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent ae) {
	                
	    			try {
						new MonthlyView(selectedDoctor.getId());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog( dateField, "NumberFormatException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					} catch (EmptyResultSetException e) {
						JOptionPane.showMessageDialog( dateField, "EmptyResultSetException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					}

	}
	});			
		         
		         addRoutine.addActionListener(new ActionListener() {
	                 public void actionPerformed(ActionEvent ae) {
	                         new AddRoutine();
	                 }
	         });
	            
		         showCalendar.addActionListener(new ActionListener() {
		                 public void actionPerformed(ActionEvent ae) {
		                	 dateField.setText(new DatePicker().setPickedDate());
		                 }
		         });
		         
		         showAppointments.addActionListener(new ActionListener() {
		        	 public void actionPerformed(ActionEvent ae) {
		        		 new AppointmentsView();
		        	 }
		         });
		         
		         determineTrainingDays.addActionListener(new ActionListener() {
		        	 public void actionPerformed(ActionEvent ae) {
		        		 new TrainingDaysPicker();
		        	 }
		         });
		         
		// try to change the bg color of add appointment button
		         UIDefaults overrides = new UIDefaults();
		         Color lightRed = new Color(247, 199, 199);
		         overrides.put("Button.background", lightRed);
		         addRoutine.putClientProperty("Nimbus.Overrides", overrides);
		         addRoutine.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
		         SwingUtilities.updateComponentTreeUI(addRoutine);
		   
		
		// add all the controls to the right panel
		rightPanel.add(showCalendar,new CC().wrap());
		rightPanel.add(dateField,new CC().wrap());
		rightPanel.add(staffMemberButton,new CC().wrap());
		rightPanel.add(staffMemberField,new CC().wrap());
		rightPanel.add(dailyView,new CC().wrap());
		rightPanel.add(weeklyView,new CC().wrap());
		rightPanel.add(monthlyView,new CC().wrap());
		rightPanel.add(determineTrainingDays,new CC().wrap());
		rightPanel.add(addRoutine, new CC().wrap());
		rightPanel.add(modifyAppointmentButton);

		appointmentsModuleView.add(rightPanel, new CC().dockEast());

		textQuery.getDocument().addDocumentListener(this);
		
		// set column widths
		if (calendarAppointmentsTable != null) {
			calendarAppointmentsTable.getColumnModel().getColumn(0).setMinWidth(30);
			calendarAppointmentsTable.getColumnModel().getColumn(0).setMaxWidth(50);
			calendarAppointmentsTable.getColumnModel().getColumn(0).setPreferredWidth(40);
			
			calendarAppointmentsTable.getColumnModel().getColumn(1).setMinWidth(50);
			calendarAppointmentsTable.getColumnModel().getColumn(1).setMaxWidth(150);
			calendarAppointmentsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		}
		return appointmentsModuleView;
	}

	/** getAppointments (old implementation of Object Selection - Used in Patient module for now)
	 * @return
	 */
	public static List<CalendarAppointment> getAppointments() {
		return calendarAppointments;
	}

	/** getAppointmetntsTable
	 * @return the CalendarAppointments table
	 */
	public static JTable getAppointmentsTable() {
		return calendarAppointmentsTable;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
			case "View/Edit":
				CalendarAppointment cAM = calendarAppointments.get(selectedRowInModel);
				new ViewEditAppointment(cAM);
				break;
		}
	}

	/** newFilter
	 * Filters Staff Members
	 */
	private void newFilter() {
		RowFilter<? super CalendarAppointmentATM, ? super Integer> rf = null;
		List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>();

		try {
			String text = textQuery.getText();
			String[] textArray = text.split(" ");

			for (int i = 0; i < textArray.length; i++) {
				rfs.add(RowFilter.regexFilter("(?i)" + textArray[i]));
			}

			rf = RowFilter.andFilter(rfs);

		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}

		sorter.setRowFilter(rf);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		modifyAppointmentButton.setVisible(true);
		int viewRow = calendarAppointmentsTable.getSelectedRow();
		if (viewRow < 0) {
		} else {
			selectedRowInModel = calendarAppointmentsTable.convertRowIndexToModel(viewRow);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		newFilter();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		newFilter();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		newFilter();
	}
	
	public static StaffMember getSelectedDoctor(){
		return selectedDoctor;
	}
}