/**
 * 
 */
package module.StaffMember.SpecialityManagement;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import object.Speciality;

/**
 * @author VJ
 *
 */
public class SpecialityJCBRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -79813809668544749L;
	
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
