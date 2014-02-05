package mapper;
/** TaxOfficeDMO
 * This Data Mapper contains all of the methods concerned with the Tax Office Table.
 * There are no many-to-many relations with other Entities.
 */
import framework.GPSISDataMapper;
import object.TaxOffice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

public class TaxOfficeDMO extends GPSISDataMapper<TaxOffice> 
{   
	// stores the only instance of this DataMapper
	private static TaxOfficeDMO instance;
	
	/** TaxOfficeDMO Constructor 
	 * This is Private as part of a Singleton implementation.
	 * @param tableName
	 */
    private TaxOfficeDMO(String tableName)
    {
        this.tableName = tableName;
    }    
    
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
        
    /** getAll
     * return a Set of all TaxOffices
     */
    public Set<TaxOffice> getAll()
    {
        return getAllByProperties(new SQLBuilder());
    }

    
    /** getById 
     * @param id the id of the TaxOffice to retrieve
     * @return a TaxOffice object that relates to the id
     */
    public TaxOffice getById(int id)
    {
        return this.getByProperties(new SQLBuilder("id", "=", ""+id));
        
    }
    
    /** getByProperties
     * Returns the first TaxOffice object matching the criteria
     * @param query an SQLBuilder query
     * @return the first TaxOffice object in the ResultSet
     */
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
        
    /** getAllByProperties
     * returns a Set of TaxOffices that match the given criteria
     * @param query an SQLBuild query
     * @return a Set containing all of the TaxOffices that match the given criteria
     */
    public Set<TaxOffice> getAllByProperties(SQLBuilder query) 
    {
          Set<TaxOffice> staffMembers = new HashSet<>();
          
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
    
    /** removeById
     * Remove a TaxOffice from the database given its Id
     * @param id the id of the TaxOffice to remove     * 
     */
    public void removeById(int id) 
    {
        try 
        {
            removeByProperty(new SQLBuilder("id","=",""+id));
        } 
        catch (SQLException e) 
        {
        	System.err.println(e.getMessage());
        }
    }
    
    /** removeByProperty
     * WARNING: Removes all TaxOffices from the database that match the given criteria
     * @param query the criteria to match
     * @throws SQLException
     */
    public void removeByProperty(SQLBuilder query) throws SQLException 
    {
        GPSISDataMapper.removeByPropertyHelper(query, this.tableName);        
    }

    /** put
     * Put a given TaxOffice object onto the Database. Similar to the put method in a Map data structure. Used for INSERT and UPDATE
     * @param o The TaxOffice object
     */
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
            putHelper(sql, this.tableName);
        } 
        catch (SQLException e) 
        {
        	System.err.println(e.getMessage());
        }

    }
}
