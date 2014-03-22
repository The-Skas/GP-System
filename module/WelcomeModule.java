/** WelcomeModule 
 * The First View that is Opened when the Application starts
 * 
 * @author Vijendra Patel (vp302)
 */
package module;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModuleMain;

public class WelcomeModule extends GPSISModuleMain {

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel welcomeView = new JPanel(new GridBagLayout());

		JLabel greeting = new JLabel("Welcome " + currentUser.getFirstName() + "!");
		greeting.setFont(fonts.get("Roboto").deriveFont(24f));
		GridBagConstraints gbC = new GridBagConstraints();
		gbC.anchor = GridBagConstraints.CENTER;
		welcomeView.add(greeting, gbC);

		return welcomeView;
	}
}

/**
 * End of File: WelcomeModule.java 
 * Location: module
 */