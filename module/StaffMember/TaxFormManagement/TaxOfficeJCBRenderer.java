/**
 * 
 */
package module.StaffMember.TaxFormManagement;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import object.TaxOffice;

/**
 * @author VJ
 *
 */
public class TaxOfficeJCBRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -8486663360833953851L;

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    JLabel result = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    TaxOffice taxOffice = (TaxOffice) value;
	    if (taxOffice != null)
	    	result.setText(taxOffice.getName());
	    else
	    	result.setText("");
	    return result;
	   }
}
