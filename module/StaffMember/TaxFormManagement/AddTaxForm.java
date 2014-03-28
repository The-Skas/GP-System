/** AddTaxForm 
 * A window that displays a Form to add a Tax Form to a given Staff
 * Member
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.TaxFormManagement;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import object.StaffMember;
import object.TaxForm;
import object.TaxOffice;
import mapper.TaxOfficeDMO;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import exception.EmptyResultSetException;
import framework.GPSISFramework;
import framework.GPSISPopup;

public class AddTaxForm extends GPSISPopup implements ActionListener {

	private static final long			serialVersionUID	= 1L;
	public static JComboBox<TaxOffice>	taxOfficeFld;
	private static StaffMember			selectedStaffMember;
	private static JFormattedTextField	salaryFld;
	private static JTextField			taxCodeFld;

	/** AddTaxForm Constructor 
	 * Initialises and displays a window with a Form to
	 * add a Tax Form to a given Staff Member
	 * 
	 * @param sM
	 *            The Staff Member to add a Tax form to
	 */
	public AddTaxForm(StaffMember sM) {
		super("Add Tax Form");
		selectedStaffMember = sM;
		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setBackground(new Color(240, 240, 240));

		JPanel h = new JPanel(new MigLayout());

		JLabel hTitle = new JLabel("Add Tax Form");
		hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
		h.add(hTitle, new CC().wrap());

		this.add(h);

		JPanel addTaxFormForm = new JPanel(new MigLayout());

		// Tax Office Component
		JLabel taxOfficeLbl = new JLabel("Tax Office: ");
		addTaxFormForm.add(taxOfficeLbl);

		JButton addBtn = new JButton("Add!");
		addBtn.addActionListener(this);
		addBtn.setActionCommand("Add");
		// Tax Office Component
		try {
			taxOfficeFld = new JComboBox<TaxOffice>();
			taxOfficeFld.setModel(new TaxOfficeALM(TaxOfficeDMO.getInstance().getAll()));
			taxOfficeFld.setRenderer(new TaxOfficeJCBRenderer());
			addTaxFormForm.add((Component) taxOfficeFld);
			taxOfficeFld.setSelectedIndex(0);

		} catch (EmptyResultSetException e) {
			System.out.println("No Tax Office Found, add some!");
			addBtn.setVisible(false);
		}
		JButton addTaxOfficeBtn = new JButton("Add Tax Office");
		addTaxOfficeBtn.addActionListener(this);
		addTaxOfficeBtn.setActionCommand("Add Tax Office");
		addTaxFormForm.add(addTaxOfficeBtn, new CC().wrap());

		// Salary Component
		JLabel salaryLbl = new JLabel("Salary");
		addTaxFormForm.add(salaryLbl);
		salaryFld = new JFormattedTextField(NumberFormat.getCurrencyInstance());
		salaryFld.setValue(new Double(0.0));
		salaryFld.setColumns(10);
		addTaxFormForm.add(salaryFld, new CC().wrap());

		JLabel taxCodeLbl = new JLabel("Tax Code");
		addTaxFormForm.add(taxCodeLbl);
		taxCodeFld = new JTextField(6);
		addTaxFormForm.add(taxCodeFld, new CC().wrap());

		addTaxFormForm.add(addBtn);

		this.add(addTaxFormForm);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
			case "Add Tax Office":
				new AddTaxOffice();
				break;
			case "Add":
				this.add();
				break;
		}
	}

	/** add 
	 * Adds a Tax Form to the Database
	 */
	public void add() {
		// retrive the tax Code
		String taxCode = taxCodeFld.getText().trim();
		
		if (taxCode.isEmpty()) {
			JOptionPane.showMessageDialog(this, "The Tax Code field is blank!", "Blank Input",
					JOptionPane.WARNING_MESSAGE);
		} else {
			Double salary = 0.0;
			if (salaryFld.getValue() instanceof java.lang.Long) {
				salary = ((Long) salaryFld.getValue()).doubleValue();
			} else {
				salary = (Double) salaryFld.getValue();
			}
			((TaxFormATM) ViewTaxForms.taxFormTable.getModel()).addRow(new TaxForm(selectedStaffMember,	(TaxOffice) taxOfficeFld.getSelectedItem(), taxCode, salary));
			dispose();
		}
	}

}

/**
 * End of File: AddTaxForm.java 
 * Location: module/StaffMember/TaxFormManagement
 */
