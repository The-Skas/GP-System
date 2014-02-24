package module;
/** LoginModule
 * Displays the Login Window and Sets the static currentUser variable
 * @author VJ
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	
	private JFrame gpsisLogin = new JFrame(APPTITLE + " Login");
	private JTextField usernameFld;
	private JPasswordField passwordFld;
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		String username = this.usernameFld.getText();
		String inputPassword = new String(StaffMember.encrypt(new String(this.passwordFld.getPassword())));
		
		// execute login
		StaffMember sM;
		try 
		{
			sM = staffMemberDMO.getByProperties(new SQLBuilder("username", "=", username));
			String password = new String(sM.getEncryptedPassword());
			if (password.trim().equals(inputPassword.trim()))
			{
				gpsisLogin.dispose(); // close login window
				currentUser = sM; // set the current user
				
				staffMemberDMO.register(currentUser);
				
				MainInterface wM = new MainInterface(); // load the welcome module
				wM.createAndShowGUI(); // show the welcome module
				
				// close login window and open main window with StaffMember object in the parameter
			}
			else
			{
				JOptionPane.showMessageDialog(gpsisLogin, "Invalid Password!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} 
		catch (EmptyResultSetException ex) 
		{
			JOptionPane.showMessageDialog(gpsisLogin, "Invalid User!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
				
	}

	public void showLogin()
	{
		this.gpsisLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gpsisLogin.setLayout(new MigLayout());
		this.gpsisLogin.setBackground(new Color(240, 240, 240));
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/image/favicon.jpg"));
		gpsisLogin.setIconImage((icon.getImage()));
		JPanel h = new JPanel(new MigLayout());			
			JLabel hTitle = new JLabel("Login");
				hTitle.setFont(fonts.get("Roboto").deriveFont(24f));
			h.add(hTitle);
		this.gpsisLogin.add(h, new CC().wrap().grow());
		
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
		

		this.gpsisLogin.add(loginForm);
		this.gpsisLogin.pack();
		
		this.gpsisLogin.setLocationRelativeTo(null); // center window
		
		this.gpsisLogin.setVisible(true);
	}
	
}
