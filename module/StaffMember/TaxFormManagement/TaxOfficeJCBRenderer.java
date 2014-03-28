/** TaxOfficeJCBRenderer 
 * JComboBox Renderer for Tax Offices
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.TaxFormManagement;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import object.TaxOffice;

public class TaxOfficeJCBRenderer extends DefaultListCellRenderer {

	private static final long	serialVersionUID	= 1L;

	/* (non-Javadoc)
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
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

/**
 * End of File: TaxOfficeJCBRenderer.java 
 * Location: module/StaffMember/TaxFormManagement
 */