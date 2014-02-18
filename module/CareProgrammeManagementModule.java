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

public class CareProgrammeManagementModule extends GPSISModuleMain {

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel careProgrammeManagementModuleView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("This is module/CareProgrammeManagementModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			careProgrammeManagementModuleView.add(greeting, gbC);
		
		return careProgrammeManagementModuleView;
	}


}
