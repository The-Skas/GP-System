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

public class PatientModule extends GPSISModuleMain {

	@Override
	public JPanel getModuleView() {
		JPanel patientModuleView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("This is the Patient Module Main View in module/PatientModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
		patientModuleView.add(greeting, gbC);
		
		return patientModuleView;
	}


}
