/** SpecialityJCBRenderer 
 * JComboBox Renderer for Speciality Objects
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.SpecialityManagement;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import object.Speciality;

public class SpecialityJCBRenderer extends DefaultListCellRenderer {

	private static final long	serialVersionUID	= 1L;

	/* (non-Javadoc)
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel result = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		Speciality speciality = (Speciality) value;
		if (speciality != null)
			result.setText(speciality.getName());
		else
			result.setText("");
		return result;
	}
}

/**
 * End of File: SpecialityJCBRenderer.java 
 * Location: module/StaffMember/SpecialityManagement
 */