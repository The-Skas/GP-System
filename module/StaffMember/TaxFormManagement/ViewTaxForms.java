/**
 * 
 */
package module.StaffMember.TaxFormManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mapper.SQLBuilder;
import mapper.TaxFormDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import exception.EmptyResultSetException;
import framework.GPSISPopup;
import object.StaffMember;
import object.TaxForm;

/**
 * @author VJ
 *
 */
public class ViewTaxForms extends GPSISPopup implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 5430595743151002403L;
	 
	private static StaffMember selectedStaffMember;
	private static TaxFormATM tFModel;
	private static JTable taxFormTable;
	private static List<TaxForm> taxForms;
	private static JPanel leftPanel;

	public ViewTaxForms(StaffMember sM) {
		super("Staff Member Tax Forms");
		this.setLayout(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow())); 
		this.setModal(true);
		selectedStaffMember = sM;
		
	
		// Table View
		leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
		try {
			taxForms = TaxFormDMO.getInstance().getAllByProperties(new SQLBuilder("staff_member_id", "=", ""+selectedStaffMember.getId()));
			
		} catch (EmptyResultSetException e) {
			System.out.println("No Tax Forms");
			taxForms = new ArrayList<TaxForm>();
		}	
		tFModel = new TaxFormATM(taxForms);
		taxFormTable = new JTable (tFModel);
		taxFormTable.getSelectionModel().addListSelectionListener(this);
		leftPanel.add(new JScrollPane(taxFormTable), new CC().grow().span());
		this.add(leftPanel, new CC().span().grow());		
		
		// Controls (RIGHT PANE)
		JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			// Specialitiy Label
			JButton addTaxFormBtn = new JButton("Add Tax Form");
			addTaxFormBtn.addActionListener(this);
			addTaxFormBtn.setActionCommand("Add Tax Form");
			rightPanel.add(addTaxFormBtn);			
						
		this.add(rightPanel, new CC().dockEast());
	
		this.pack();
		this.setLocationRelativeTo(null); // center window
		this.setVisible(true);
	}
		

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand())
		{
			case "Add Tax Form":
				new AddTaxForm(selectedStaffMember);
				break;
		}
		
	}
	
	public static JTable getTable()
	{
		return taxFormTable;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
