/**
 * 
 */
package module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import object.Holidays;
import object.Patient;
import object.StaffMember;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import mapper.HolidaysDMO;
import module.CalendarAppointments.AddRoutine;
import module.CalendarAppointments.AppointmentsView;
import module.CalendarAppointments.DailyView;
import module.CalendarAppointments.DailyViewSaturday;
import module.CalendarAppointments.DatePicker;
import module.CalendarAppointments.HolidayPanel;
import module.CalendarAppointments.TrainingDaysPicker;
import module.CalendarAppointments.WeeklyView;
import module.CalendarAppointments.MonthlyView;
import exception.EmptyResultSetException;
import exception.UserDidntSelectException;
import framework.*;

import javax.swing.UIManager.*;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class CalendarAppointmentsModule extends GPSISModuleMain implements ActionListener {
	
	public static List<Date> publicHolidays = new ArrayList<Date>();
	
	static	Calendar cal = java.util.Calendar.getInstance(); 
	
	static StaffMember selectedDoctor;
	
	Date now = new Date();
	
	private Formatter x; // will need it if I want to unset training days
 
	private static Scanner y;
	
	// do file writing/reading for holidays and training days
	
	public static void openFile(){
		
		try{
			
			y = new Scanner(new File("holidays.txt"));
			
		} catch (Exception e) {
			
			System.out.println("couldn't open file");
		}
		
	}
	
//	public void addRecords(String s){
//		x.format("%s", s+"\n");
//	}
	
	public static void closeFile(){
		y.close();
	}
	
	public void readFile(){
		while(y.hasNext()){
			String a = y.next();
			
			System.out.println(a);
		}
	}
	
	public static void append(String s){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("holidays.txt", true)))) {
		    out.println(s);
		}catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
 
	
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public JPanel getModuleView() {
		
		// testing holiday file
		
		
//		openFile();
//		readFile();
//		closeFile();
//		
//		openFile();
//		append();
//		readFile();
//		closeFile();
//		
//		openFile();
//		append();
//		append();
//		closeFile();

		
		
		loadHolidays();
		
		
		
		// checking if calendar parser works
//		
//		System.out.println("holidays' size: "+publicHolidays.size());
//		
//		Date christmas = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
//		String christmasString = "06-05-2014";
//		try {
//			christmas = sdf.parse(christmasString);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		boolean b = isHoliday(christmas);
//		boolean n = isHoliday(now);
//		System.out.println("chrismas hopefully is a holiday "+b);
//		System.out.println("today is hopefully not "+n);
		// end checking holidays

try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (Exception e) {
    // If Nimbus is not available, set the GUI to another look and feel.
}
		
		JPanel calendarAppointmentsModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		calendarAppointmentsModuleView.setBackground(Color.WHITE);
		
		// make buttons
		JButton addRoutine = new JButton("Add a Routine Appointment");
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
			System.out.println("Staff Member is office manager");
			determineTrainingDays.setEnabled(true);
		}	
		else {
			System.out.println("Staff Member is NOT office manager");
			determineTrainingDays.setEnabled(false);
		}
			
			
        //GPSIS.clock.setPreferredSize(new Dimension(69,69));
		
		final JTextField dateField = new JTextField(10);
		dateField.setEditable(false);
		
		final DateFormat yMD = new SimpleDateFormat("yyyy/MM/dd");
		final DateFormat sundayChecker = new SimpleDateFormat("EEE");
		dateField.setText(yMD.format(now));
		
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
                    	
                    		//System.out.println("appString is: "+appString);
                     		if(isHoliday(appDate))
                     			new HolidayPanel(appDateString);
                     		else if(appString.equals("Sat"))
                    			new DailyViewSaturday(selectedDoctor.getId(), dateField.getText());
                    		else if(appString.equals("Sun"))
                    			JOptionPane.showMessageDialog( dateField, "There are no opening hours on Sundays. Please, select another day.", "No opening hours on Sundays", JOptionPane.WARNING_MESSAGE);
                    		else
                    			new DailyView(selectedDoctor.getId(), dateField.getText());
					} catch (NumberFormatException | ParseException e) {
						JOptionPane.showMessageDialog( dateField, "NumberFormatException or ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					}
            }
    });
        
        weeklyView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                
                			try {
								new WeeklyView(selectedDoctor.getId(), dateField.getText());
							} catch (ParseException e) {
								JOptionPane.showMessageDialog( dateField, "ParseException, please try with different values", "Exception!", JOptionPane.WARNING_MESSAGE);
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
	         
//	    		calendarAppointmentsModuleView.add(patientButton);
//	    		calendarAppointmentsModuleView.add(patientField, "wrap");
	    		
	    		calendarAppointmentsModuleView.add(staffMemberButton, "align left, gapleft 60, split 2");
	    		calendarAppointmentsModuleView.add(staffMemberField, "align left");
	    		
	    		calendarAppointmentsModuleView.add(dailyView, "alignx center, gapright 50, wrap");
	    		
	    		calendarAppointmentsModuleView.add(showCalendar, "align left, gapleft 60, split 2");
	    		calendarAppointmentsModuleView.add(dateField, "align left"); 
    		
	    		calendarAppointmentsModuleView.add(weeklyView, "alignx center, gapright 50, wrap");
	    		
	    		calendarAppointmentsModuleView.add(addRoutine, "align left, gapleft 60");	
	    		
	    		calendarAppointmentsModuleView.add(monthlyView, "alignx center, gapright 50, wrap");	
	    		
	    		calendarAppointmentsModuleView.add(determineTrainingDays, "align left, gapleft 60");
	    		
//	    		calendarAppointmentsModuleView.add(showAppointments, "align left, gapleft 60");
	    		
	            //calendarAppointmentsModuleView.add(GPSIS.clock, "gapleft 90");
	           
	         
		return calendarAppointmentsModuleView;
	}
	
	/*
	public static void loadHolidays(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		Date holiday = new Date();

		// https://www.gov.uk/bank-holidays
		//String[] holidayArray = {"18-04-2014", "21-04-2014", "05-05-2014", "26-05-2014", "25-08-2014", "25-12-2014", "26-12-2014", "01-01-2015", "03-04-2015", "06-04-2015", "25-05-2015", "31-08-2015", "25-12-2015", "28-12-2015" };
		
		ArrayList<String> holidayArrayList = new ArrayList<String>();
		// add each date from holidays.txt to the holidayArrayList
		
		openFile();
		
		while(y.hasNext()){
			String a = y.next();
			holidayArrayList.add(a);
		}
		
		closeFile();
		
		// make an ArrayList of holiday Date objects
		for(int i = 0; i<holidayArrayList.size(); i++){
			try {
				publicHolidays.add(sdf.parse(holidayArrayList.get(i)));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	*/
	
	

	public static void loadHolidays(){
		
		// get an ArrayList of Holiday objects from the DB
		ArrayList<Holidays> holidays = null;
		
		try {
			holidays = (ArrayList<Holidays>) HolidaysDMO.getInstance().getAll();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		// get an ArrayList of Date objects corresponding to the Holiday objects
		for(int i = 0; i < holidays.size(); i++){
			// convert each Holiday object into Date object
			publicHolidays.add(holidays.get(i).getDate());
		}
		
	}

	
	public static StaffMember getSelectedDoctor(){
		return selectedDoctor;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// done
	}
}
