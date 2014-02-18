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

public class SpecialistReferralsModule extends GPSISModuleMain {

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel specialistReferralsModuleView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("This is module/SpecialistReferralsModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			specialistReferralsModuleView.add(greeting, gbC);
		
		return specialistReferralsModuleView;
	}


}
