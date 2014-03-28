/** LoginModule 
 * Displays the Login Window and Sets the static currentUser
 * variable
 * 
 * @author Vijendra Patel (vp302)
 */
package module;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import mapper.SQLBuilder;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISFramework;

public class LoginModule extends GPSISFramework implements ActionListener {

	private JDialog			dialog;
	private JTextField		usernameFld;
	private JPasswordField	passwordFld;
	
	private StaffMember user;

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String username = this.usernameFld.getText();
		String inputPassword = new String(StaffMember.encrypt(new String(this.passwordFld.getPassword())));

		// execute login
		StaffMember sM;
		try {
			sM = staffMemberDMO.getByProperties(new SQLBuilder("username", "=", username));
			String password = new String(sM.getEncryptedPassword());
			if (password.trim().equals(inputPassword.trim())) {
							
				user = sM; // set the current user

				if (!isHoliday(new Date()))
					staffMemberDMO.register(user);
				else
					splashWindow.addText("\nToday's a Holiday/Training Day!");

				this.dialog.dispose();		

			} else {
				JOptionPane.showMessageDialog(dialog, "Invalid Password!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} catch (EmptyResultSetException ex) {
			JOptionPane.showMessageDialog(dialog, "Invalid User!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	/** showLogin
	 * builds and shows the login window
	 * 
	 */
	public StaffMember showLogin() {
		splashWindow.addText("\nLoading Login Module");
		dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setLayout(new MigLayout());
        dialog.setBackground(new Color(240, 240, 240));

        dialog.setIconImage(getGPSISLogo().getImage());
        dialog.setTitle(APPTITLE + " Login");
        
        JPanel h = new JPanel(new MigLayout());
	        JLabel hTitle = new JLabel("Login");
			hTitle.setFont(fonts.get("OpenSans").deriveFont(24f));
		h.add(hTitle);
		dialog.add(h, new CC().wrap().alignX("center"));
        

		JPanel loginForm = new JPanel(new MigLayout());
		

		JLabel usernameLbl = new JLabel("Username: ");
		loginForm.add(usernameLbl);

		this.usernameFld = new JTextField(20);
		loginForm.add(this.usernameFld, new CC().wrap());

		JLabel passwordLbl = new JLabel("Password: ");
		loginForm.add(passwordLbl);

		this.passwordFld = new JPasswordField(20);
		this.passwordFld.addActionListener(this);
		loginForm.add(this.passwordFld, new CC().wrap());

		JButton loginBtn = new JButton("Login!");
		loginBtn.addActionListener(this);
		loginForm.add(loginBtn, new CC().span(2).grow());

		dialog.add(loginForm);
		dialog.pack();

		dialog.setLocationRelativeTo(null); // center window

		dialog.setVisible(true);
		
		return user;
			
	}

}

/**
 * End of File: LoginModule.java 
 * Location: module
 */
