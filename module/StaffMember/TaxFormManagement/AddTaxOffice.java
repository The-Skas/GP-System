/** AddTaxOffice 
 * A window that displays a Form to add a Tax Office
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.TaxFormManagement;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import object.TaxOffice;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import framework.GPSISFramework;
import framework.GPSISPopup;

public class AddTaxOffice extends GPSISPopup implements ActionListener {

	private static final long	serialVersionUID	= 1L;
	private static JTextField	nameFld;
	private static JTextField	noStreetFld;
	private static JTextField	localityFld;
	private static JTextField	townFld;
	private static JTextField	postcodeAreaFld;
	private static JTextField	postcodeDistrictFld;

	/** AddTaxOffice Constructor
	 * Builds and displays a Window with a form to create a new Tax Office
	 */
	public AddTaxOffice() {
		super("Add Tax Office");

		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));

		JPanel h = new JPanel(new MigLayout());

		JLabel hTitle = new JLabel("Add Tax Office");
		hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
		h.add(hTitle, new CC().wrap());

		this.add(h, new CC().wrap());

		JPanel addTaxOfficeForm = new JPanel(new MigLayout());

		// name component
		JLabel nameLbl = new JLabel("Name");
		addTaxOfficeForm.add(nameLbl);
		nameFld = new JTextField(30);
		addTaxOfficeForm.add(nameFld, new CC().span().wrap());

		// No. and Street
		JLabel noStreetLbl = new JLabel("No and Street");
		addTaxOfficeForm.add(noStreetLbl);
		noStreetFld = new JTextField(50);
		addTaxOfficeForm.add(noStreetFld, new CC().span().wrap());

		// Locality
		JLabel localityLbl = new JLabel("Locality");
		addTaxOfficeForm.add(localityLbl);
		localityFld = new JTextField(20);
		addTaxOfficeForm.add(localityFld, new CC().span().wrap());

		// Town
		JLabel townLbl = new JLabel("Town");
		addTaxOfficeForm.add(townLbl);
		townFld = new JTextField(20);
		addTaxOfficeForm.add(townFld, new CC().span().wrap());

		// Post code
		JLabel postcodeLbl = new JLabel("Post Code");
		addTaxOfficeForm.add(postcodeLbl);
		postcodeAreaFld = new JTextField(4);
		addTaxOfficeForm.add(postcodeAreaFld);
		postcodeDistrictFld = new JTextField(3);
		addTaxOfficeForm.add(postcodeDistrictFld, new CC().wrap());

		JButton saveBtn = new JButton("Add Tax Office!");
		saveBtn.addActionListener(this);
		saveBtn.setActionCommand("Add Tax Office");

		addTaxOfficeForm.add(saveBtn);
		this.add(addTaxOfficeForm);

		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		// Gather values
		String name = nameFld.getText();
		String noStreet = noStreetFld.getText();
		String locality = localityFld.getText();
		String town = townFld.getText();
		String pcArea = postcodeAreaFld.getText();
		String pcDistrict = postcodeDistrictFld.getText();

		((TaxOfficeALM) AddTaxForm.taxOfficeFld.getModel()).addTaxOffice(new TaxOffice(name, noStreet, locality, town,
				pcArea, pcDistrict));
		JOptionPane.showMessageDialog(this, "Added a New Tax Office");
		dispose();
	}

}

/**
 * End of File: AddTaxOffice.java 
 * Location: module/StaffMember/TaxFormManagement
 */