package object;

import framework.GPSISObject;

public class TaxForm extends GPSISObject {
	
	private StaffMember staffMember;
	private TaxOffice taxOffice;
	private char[] taxNumber = new char[6];
	private double salary;
	
	public TaxForm (int id, StaffMember sM, TaxOffice tO, String n, double s)
	{
		this.id = id;
		this.staffMember = sM;
		this.taxOffice = tO;
		this.taxNumber = n.toCharArray();
		this.salary = s;
	}
	
	public TaxForm (StaffMember sM, TaxOffice tO, String n, double s)
	{
		this.staffMember = sM;
		this.taxOffice = tO;
		this.taxNumber = n.toCharArray();
		this.salary = s;
	}

	/**
	 * @return the number
	 */
	public char[] getTaxNumber() {
		return taxNumber;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @return the taxOffice
	 */
	public TaxOffice getTaxOffice() {
		return taxOffice;
	}

	/**
	 * @return the staffMember
	 */
	public StaffMember getStaffMember() {
		return staffMember;
	}
}
