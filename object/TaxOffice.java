/**
 * 
 */
package object;

import framework.GPSISObject;

/**
 * @author VJ
 *
 */
public class TaxOffice extends GPSISObject {
	private String name;
	private String noAndStreet;
	private String locality;
	private String town;
	private String postcodeArea;
	private String postcodeDistrict;
	
	public TaxOffice(int id, String n, String nOA, String l, String t, String pA, String pD)
	{
		this.id = id;
		this.name = n;
		this.noAndStreet = nOA;
		this.locality = l;
		this.town = t;
		this.postcodeArea = pA;
		this.postcodeDistrict = pD;
	}
	
	public TaxOffice(String n, String nOA, String l, String t, String pA, String pD)
	{
		this.name = n;
		this.noAndStreet = nOA;
		this.locality = l;
		this.town = t;
		this.postcodeArea = pA;
		this.postcodeDistrict = pD;
		
		taxOfficeDMO.put(this);
	}
	
	public String getLocality()
	{
		return this.locality;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getNoAndStreet()
	{
		return this.noAndStreet;
	}
	
	public String getPostcodeArea()
	{
		return this.postcodeArea;
	}
	
	public String getPostcodeDistrict()
	{
		return this.postcodeDistrict;
	}
	
	public String getTown()
	{
		return this.town;
	}
}
