/**
 * 
 */
package module.StaffMember.TaxFormManagement;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import object.TaxOffice;

/**
 * @author VJ
 *
 */
public class TaxOfficeALM extends AbstractListModel<TaxOffice> implements ComboBoxModel<TaxOffice> {
	
	private static final long serialVersionUID = 2702545823423913068L;
	private TaxOffice selectedItem;

	private List<TaxOffice> data;
	
	public TaxOfficeALM(List<TaxOffice> d)
	{
		this.data = d;
	}
	
	@Override
	public TaxOffice getElementAt(int arg0) {
		return data.get(arg0);
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		for (TaxOffice s : data)
		{
			if (s.equals(arg0))
			{
				selectedItem = (TaxOffice) arg0;
				break;
			}
		}
	}
	
	/** addTaxOffice
	 * add a Tax Office Object to the List and refresh the List
	 * @param o
	 */
	public void addTaxOffice(TaxOffice o)
	{
		this.data.add(o);
		this.fireIntervalAdded(this, data.size() - 1, data.size());
	}
}