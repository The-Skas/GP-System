/** PrescriptionATM 
 * Table Model for Prescriptions
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.PrescriptionManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.Prescription;

public class PrescriptionATM extends AbstractTableModel {

	private static final long	serialVersionUID	= 1L;
	private String[]			columnNames			= { "ID", "Patient", "Doctor", "Medical Condition", "Start Date", "End Date", "Medicines" };
	private List<Prescription>	data;

	/** PrescriptionATM Constructor
	 * Creates a Table Model for a given List of Prescriptions
	 * @param d
	 */
	public PrescriptionATM(List<Prescription> d) {
		this.data = d;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return data.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		switch (col)
		{
			case 0: 
				return data.get(row).getPrescriptionID();
			case 1:
				return data.get(row).getPatient().getFirstName() + " " + data.get(row).getPatient().getLastName();
			case 2:
				return data.get(row).getDoctor().getName();
			case 3:
				return data.get(row).getMedicalCondition();
			case 4:
				return data.get(row).getStartDate();
			case 5:
				return data.get(row).getendDate();
			case 6:
				String medicines = "";
				for (Object m : data.get(row).getlistofMedicine())
				{
					medicines += " " + m;
				}
				return medicines;
		}
		return null;
	}

}

/**
 * End of File: PrescriptionATM.java 
 * Location: module/StaffMember/PrescriptionManagement
 */