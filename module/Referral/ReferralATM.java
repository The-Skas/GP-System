
package module.Referral;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import object.ReferralObject;

public class ReferralATM extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Referral Id","Date Made", "Patient's Name"};
	private List<ReferralObject> data;
	
	/** ReferralATM Constructor
	 * Creates a Staff Member Abstract Table Model with the given data
	 * @param d a List of Staff Member objects to present in the table
	 */
	public ReferralATM(List<ReferralObject> d)
	{
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
        ReferralObject rO = data.get(row);
        switch (col)
        {
        	case 0:
        		return rO.getId()+"";
        	case 1:
        		return rO.getDate();
                case 2:
                        return rO.getPatient().getFirstName() +" "+ rO.getPatient().getLastName();
   
        }
        return null;
    }
    
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int c) {
	    return getValueAt(0, c).getClass();
	}	
	
	
	/** addRow
	 * add a Staff Member Object to the Table and refresh the Table
	 * @param o
	 */
	public void addRow(ReferralObject o)
	{
		this.data.add(o);
		this.fireTableChanged(null);
	}
	
	/** removeRow
	 * remove a Staff Member Object from the Table and refresh the Table
	 * @param o
	 */
	public void removeRow(ReferralObject o)
	{
		this.data.remove(o);
		this.fireTableChanged(null);
	}
        
        /* getData
        * return the ReferralObjects
        */
        
        public List<ReferralObject> getData()
        {
            return this.data;
        }

}
