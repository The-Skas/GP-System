/** ChangeStaffMemberPassword
 * Opens a new window displaying a form to change the user's password. Visible
 * to all Office Managers or if the user matches the user currently logged in.
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import mapper.StaffMemberDMO;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import object.StaffMember;
import framework.GPSISPopup;

public class ChangeStaffMemberPassword extends GPSISPopup implements ActionListener {

	private static final long	serialVersionUID	= 1L;
	private JPasswordField		newPassword			= new JPasswordField();
	private JPasswordField		confirmPassword		= new JPasswordField();
	private StaffMember			staffMember;

	public ChangeStaffMemberPassword(StaffMember sM) {
		super("Change Password");
		this.setModal(true);
		this.staffMember = sM;

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));

		JLabel newPasswordLbl = new JLabel("New Password: ");
		this.add(newPasswordLbl, new CC());
		this.newPassword.setPreferredSize(new Dimension(200, 24));
		this.add(this.newPassword, new CC().wrap());

		JLabel confirmPasswordLbl = new JLabel("Confirm Password: ");
		this.add(confirmPasswordLbl, new CC());
		this.confirmPassword.setPreferredSize(new Dimension(200, 24));
		this.add(this.confirmPassword, new CC().wrap());

		JButton submitBtn = new JButton("Save!");
		submitBtn.addActionListener(this);
		this.add(submitBtn, new CC().grow());

		this.pack();
		this.setVisible(true);

		this.setLocationRelativeTo(null);
	}

	/* (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (new String(this.newPassword.getPassword()).equals(new String(this.confirmPassword.getPassword()))) {
			this.staffMember.setPassword(new String(this.newPassword.getPassword()));
			StaffMemberDMO.getInstance().put(this.staffMember);

			JOptionPane.showMessageDialog(this, "Updated Password! :D");
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Passwords do not match", "The Passwords do not match!",
					JOptionPane.WARNING_MESSAGE);
		}
	}

}

/**
 * End of File: ChangeStaffMemberPassword.java 
 * Location: module/StaffMember
 */