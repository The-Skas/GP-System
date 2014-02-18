package mapper;
/** TaxOfficeDMO
 * This Data Mapper contains all of the methods concerned with the Tax Office Table.
 * There are no many-to-many relations with other Entities.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import object.TaxOffice;
import framework.GPSISDataMapper;

public class TaxOfficeDMO extends GPSISDataMapper<TaxOffice> 
{   
	// stores the only instance of this DataMapper
	private static TaxOfficeDMO instance;
	
	/** getInstance
     * returns the only instance of the TaxOfficeDMO
     * @return
     */
    public static TaxOfficeDMO getInstance() 
    {
        if(instance == null)
            instance = new TaxOfficeDMO("TaxOffice");
        return instance;
    }    
    
    /** TaxOfficeDMO Constructor 
	 * This is Private as part of a Singleton implementation.
	 * @param tableName
	 */
    private TaxOfficeDMO(String tableName)
    {
        this.tableName = tableName;
    }
        
    /** buildTaxOffice
     * builds a Staff Member object from the given Result Set. Used as a helper method in retrieving TaxOffices from the Database
     * Returns the correct object type (Receptionist or MedicalTaxOffice) and includes their temp contract if needed
     * @param res
     * @return a complete Staff Member
     * @throws SQLException
     */
    private TaxOffice buildTaxOffice(ResultSet res) throws SQLException
    {
    	if (res != null) // if found, create a the TaxOffice object 
        {
    		return new TaxOffice (res.getInt("id"),
    								res.getString("name"),
    								res.getString("no_and_street"),
    								res.getString("locality"),
    								res.getString("town"),
    								res.getString("postcode_area"),
    								res.getString("postcode_district"));
        }
        else 
        {
            System.err.println("EMPTY SET - No Staff Member Found matching the criteria");
        }
		return null;
    }

    
    /** getAllByProperties
     * returns a Set of TaxOffices that match the given criteria
     * @param query an SQLBuild query
     * @return a Set containing all of the TaxOffices that match the given criteria
     */
    @Override
	public List<TaxOffice> getAllByProperties(SQLBuilder query) 
    {
          List<TaxOffice> staffMembers = new ArrayList<TaxOffice>();
          
          try 
          {            
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
            while(res.next()) // While there's a TaxOffice, create a the TaxOffice object and add it to a Set
            {
                staffMembers.add(this.buildTaxOffice(res));
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return staffMembers;
    }
    
    /** getByProperties
     * Returns the first TaxOffice object matching the criteria
     * @param query an SQLBuilder query
     * @return the first TaxOffice object in the ResultSet
     */
    @Override
	public TaxOffice getByProperties(SQLBuilder query) 
    {
        try 
        {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
            
            if (res.next()) // if found, create a the TaxOffice object 
            {
            	this.buildTaxOffice(res);
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /** put
     * Put a given TaxOffice object onto the Database. Similar to the put method in a Map data structure. Used for INSERT and UPDATE
     * @param o The TaxOffice object
     */
    @Override
	public void put(TaxOffice o) 
    {
        SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("name","=",""+o.getName())
                .SET("no_and_street", "=", ""+o.getNoAndStreet())
                .SET("locality","=",""+o.getLocality())
                .SET("town", "=",""+o.getTown())
                .SET("postcode_area", "=", o.getPostcodeArea())
                .SET("postcode_district", "=", o.getPostcodeDistrict());
        try 
        {
            putHelper(sql, this.tableName, o);
        } 
        catch (SQLException e) 
        {
        	System.err.println(e.getMessage());
        }

    }
}
