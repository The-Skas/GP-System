/** TaxOfficeALM 
 * List Model for Tax Offices
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.TaxFormManagement;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import object.TaxOffice;

public class TaxOfficeALM extends AbstractListModel<TaxOffice> implements ComboBoxModel<TaxOffice> {

	private static final long	serialVersionUID	= 1L;
	private TaxOffice			selectedItem;
	private List<TaxOffice>		data;

	/** TaxOfficeALM Constructor
	 * Creates a List Model for a given List of Tax Offices
	 * @param d
	 */
	public TaxOfficeALM(List<TaxOffice> d) {
		this.data = d;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public TaxOffice getElementAt(int arg0) {
		return data.get(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return data.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(Object arg0) {
		for (TaxOffice s : data) {
			if (s.equals(arg0)) {
				selectedItem = (TaxOffice) arg0;
				break;
			}
		}
	}

	/** addTaxOffice 
	 * add a Tax Office Object to the List and refresh the List
	 * 
	 * @param o
	 */
	public void addTaxOffice(TaxOffice o) {
		this.data.add(o);
		this.fireIntervalAdded(this, data.size() - 1, data.size());
	}
}

/**
 * End of File: TaxOfficeALM.java 
 * Location: module/StaffMember/TaxFormManagement
 */