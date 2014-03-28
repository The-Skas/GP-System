/** SpecialistReferralATM 
 * Table Model for Specialist Referrals
 * 
 * @author Vijendra Patel (vp302)
 */
package module.StaffMember.SpecialistReferralManagement;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.ReferralObject;

public class SpecialistReferralATM extends AbstractTableModel {

	private static final long		serialVersionUID	= 1L;
	private String[]				columnNames			= { "ID", "Date Made", "Doctor Id", "Consultant Id", "Patient Id", "Invoice Paid" };
	private List<ReferralObject>	data;

	/** SpecialistReferralATM Constructor
	 * Creates a Table Model for a given List of Referrals
	 * @param d
	 */
	public SpecialistReferralATM(List<ReferralObject> d) {
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
				return data.get(row).getId();
			case 1:
				return data.get(row).getDate();
			case 2:
				return data.get(row).getDocId();
			case 3:
				return data.get(row).getConID();
			case 4:
				return data.get(row).getPatID();
			case 5:
				return data.get(row).isInvPaid();
		}
		return null;
	}

}

/**
 * End of File: PrescriptionATM.java 
 * Location: module/StaffMember/PrescriptionManagement
 */