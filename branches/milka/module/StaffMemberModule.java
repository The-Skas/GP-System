/** StaffMemberModule
 * The Main View for the Staff Member Module
 * @author VJ
 */
package module;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModuleMain;

public class StaffMemberModule extends GPSISModuleMain {

	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel staffMemberModuleView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("This is the Staff Member Module Main View in module/StaffMemberModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			staffMemberModuleView.add(greeting, gbC);
		
		return staffMemberModuleView;
	}

}
