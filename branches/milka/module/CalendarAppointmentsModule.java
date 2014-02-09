/**
 * 
 */
package module;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModuleMain;

public class CalendarAppointmentsModule extends GPSISModuleMain {

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel calendarAppointmentsModuleView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("This is the Calendar Appointments Module Main View in module/CalendarAppointmentsModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			calendarAppointmentsModuleView.add(greeting, gbC);
		
		return calendarAppointmentsModuleView;
	}


}
