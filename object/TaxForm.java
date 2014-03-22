/** TaxForm 
 * An Object representing a Tax Form
 * 
 * @author Vijendra Patel (vp302)
 */
package object;

import framework.GPSISObject;

public class TaxForm extends GPSISObject {

	private StaffMember	staffMember;
	private TaxOffice	taxOffice;
	private char[]		taxNumber	= new char[6];
	private double		salary;

	/** TaxForm Constructor
	 * Used when retrieving a Tax Form from the Database
	 * @param id
	 * @param sM
	 * @param tO
	 * @param n
	 * @param s
	 */
	public TaxForm(int id, StaffMember sM, TaxOffice tO, String n, double s) {
		this.id = id;
		this.staffMember = sM;
		this.taxOffice = tO;
		this.taxNumber = n.toCharArray();
		this.salary = s;
	}

	/** TaxForm Constructor
	 * Used when creating a new Tax Form
	 * @param sM
	 * @param tO
	 * @param n
	 * @param s
	 */
	public TaxForm(StaffMember sM, TaxOffice tO, String n, double s) {
		this.staffMember = sM;
		this.taxOffice = tO;
		this.taxNumber = n.toCharArray();
		this.salary = s;

		taxFormDMO.put(this);
	}

	/** getTaxNumber
	 * @return the number
	 */
	public char[] getTaxNumber() {
		return taxNumber;
	}

	/** getSalary
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/** getTaxOffice
	 * @return the taxOffice
	 */
	public TaxOffice getTaxOffice() {
		return taxOffice;
	}

	/** getStaffMember
	 * @return the staffMember
	 */
	public StaffMember getStaffMember() {
		return staffMember;
	}
}

/**
 * End of File: TaxForm.java 
 * Location: object
 */
