/**
 * 
 */
package module.StaffMember;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
