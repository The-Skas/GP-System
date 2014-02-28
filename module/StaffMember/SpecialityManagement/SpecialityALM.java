/**
 * 
 */
package module.StaffMember.SpecialityManagement;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import object.Speciality;

/**
 * @author VJ
 *
 */
public class SpecialityALM extends AbstractListModel<Speciality> implements ComboBoxModel<Speciality> {
	
	private static final long serialVersionUID = 2702545823423913068L;
	private Speciality selectedItem;

	private List<Speciality> data;
	
	public SpecialityALM(List<Speciality> d)
	{
		this.data = d;
	}
	
	@Override
	public Speciality getElementAt(int arg0) {
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
		for (Speciality s : data)
		{
			if (s.equals(arg0))
			{
				selectedItem = (Speciality) arg0;
				break;
			}
		}
	}
	
	/** addSpeciality
	 * add a Speciality Object to the Table and refresh the Table
	 * @param o
	 */
	public void addSpeciality(Speciality o)
	{
		this.data.add(o);
		this.fireIntervalAdded(this, data.size() - 1, data.size());
	}
}
