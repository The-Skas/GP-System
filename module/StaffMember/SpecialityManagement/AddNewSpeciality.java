/** AddNewSpeciality 
 * Displays a Window with a form to Add a new Speciality
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.SpecialityManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import object.Speciality;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import exception.DuplicateEntryException;
import framework.GPSISPopup;

public class AddNewSpeciality extends GPSISPopup implements ActionListener {

	private static final long	serialVersionUID	= 1L;
	private static JTextField	newSpeciality;
	private static JButton		addNewSpecialityBtn;

	/** AddNewSpeciality Constructor
	 * Builds and shows a Window with a form to add a New Speciality
	 */
	public AddNewSpeciality() {
		super("Add New Speciality");
		this.setLayout(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));

		this.add(new JLabel("Add Speciality"), new CC().grow().alignX("center"));

		newSpeciality = new JTextField(25);
		this.add(newSpeciality);

		addNewSpecialityBtn = new JButton("Add!");
		addNewSpecialityBtn.addActionListener(this);
		this.add(addNewSpecialityBtn);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Speciality nS = new Speciality(newSpeciality.getText());
			JOptionPane.showMessageDialog(this, "Added a New Speciality");
			((SpecialityALM) ViewSpecialities.specialityFld.getModel()).addSpeciality(nS);
			dispose();
		} catch (DuplicateEntryException e) {
			JOptionPane.showMessageDialog(this, "Speciality already exists!", "Speciality already exists!",
					JOptionPane.WARNING_MESSAGE);
		}

	}

}

/**
 * End of File: AddNewSpeciality.java 
 * Location: module/StaffMember/SpecialityManagement
 */