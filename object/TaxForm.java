package object;

import java.math.BigDecimal;

import framework.GPSISObject;

public class TaxForm extends GPSISObject {
	private TaxOffice taxOffice;
	private char[] number = new char[6];
	private BigDecimal salary;
	
	public TaxForm (int id, TaxOffice tO, String n, BigDecimal s)
	{
		this.id = id;
		this.taxOffice = tO;
		this.number = n.toCharArray();
		this.salary = s;
	}
	
	public TaxForm (TaxOffice tO, String n, BigDecimal s)
	{
		this.taxOffice = tO;
		this.number = n.toCharArray();
		this.salary = s;
	}

	/**
	 * @return the number
	 */
	public char[] getNumber() {
		return number;
	}

	/**
	 * @return the salary
	 */
	public BigDecimal getSalary() {
		return salary;
	}

	/**
	 * @return the taxOffice
	 */
	public TaxOffice getTaxOffice() {
		return taxOffice;
	}

	
}
