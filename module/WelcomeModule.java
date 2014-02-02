package module;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModule;

public class WelcomeModule extends GPSISModule {
	
	public WelcomeModule()
	{
		JPanel welcomeView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("Welcome " + currentUser.getFirstName() + "!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
		welcomeView.add(greeting, gbC);
		
		this.view = welcomeView;
	}
	
}
