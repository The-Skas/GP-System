package module;
/** WelcomeModule
 * The initial Module to show greeting a User to the application :)
 * @author VJ
 */
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModuleMain;
import java.awt.Point;
import javax.swing.JFrame;

public class WelcomeModule extends GPSISModuleMain {

	@Override
	public JPanel getModuleView() {
		JPanel welcomeView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("Happy B-Day " + currentUser.getFirstName() + "!");
			greeting.setFont(fonts.get("Roboto").deriveFont(24f));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
		welcomeView.add(greeting, gbC);
		
                JFrame frame = new JFrame();
                frame.add(new ImagePanel());
                 frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setLocation(new Point(0,0));
		return welcomeView;
	}
}
