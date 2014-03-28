/** SpecialityALM 
 * List Model for Specialities
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.SpecialityManagement;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import object.Speciality;

public class SpecialityALM extends AbstractListModel<Speciality> implements ComboBoxModel<Speciality> {

	private static final long	serialVersionUID	= 1L;
	private Speciality			selectedItem;
	private List<Speciality>	data;

	/** SpecialityALM Constructor
	 * Creates a List Model for a given List of Speciality objects
	 * @param d
	 */
	public SpecialityALM(List<Speciality> d) {
		this.data = d;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Speciality getElementAt(int arg0) {
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
		for (Speciality s : data) {
			if (s.equals(arg0)) {
				selectedItem = (Speciality) arg0;
				break;
			}
		}
	}

	/** addSpeciality 
	 * add a Speciality Object to the Table and refresh the Table
	 * 
	 * @param o
	 */
	public void addSpeciality(Speciality o) {
		this.data.add(o);
		this.fireIntervalAdded(this, data.size() - 1, data.size());
	}
}

/**
 * End of File: SpecialityALM.java 
 * Location: module/StaffMember/SpecialityManagement
 */