package mapper;
/** StaffMemberDMO
 * This Data Mapper contains all of the methods concerned with the Staff Member Table.
 * There are no many-to-many relations with other Entities.
 */
import framework.GPSISDataMapper;
import object.StaffMember;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

public class StaffMemberDMO extends GPSISDataMapper<StaffMember> 
{   
	// stores the only instance of this DataMapper
	private static StaffMemberDMO instance;
	
	/** StaffMemberDMO Constructor 
	 * This is Private as part of a Singleton implementation.
	 * @param tableName
	 */
    private StaffMemberDMO(String tableName)
    {
        this.tableName = tableName;
    }    
    
    /** getInstance
     * returns the only instance of the StaffMemberDMO
     * @return
     */
    public static StaffMemberDMO getInstance() 
    {
        if(instance == null)
            instance = new StaffMemberDMO("StaffMember");
        return instance;
    }
        
    /** getAll
     * return a Set of all StaffMembers
     */
    public Set<StaffMember> getAll()
    {
        return getAllByProperties(new SQLBuilder());
    }

    
    /** getById 
     * @param id the id of the StaffMember to retrieve
     * @return a StaffMember object that relates to the id
     */
    public StaffMember getById(int id)
    {
        return this.getByProperties(new SQLBuilder("id", "=", ""+id));
        
    }
    
    /** getByProperties
     * Returns the first StaffMember object matching the criteria
     * @param query an SQLBuilder query
     * @return the first StaffMember object in the ResultSet
     */
    public StaffMember getByProperties(SQLBuilder query) 
    {
        try 
        {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
            
            if (res.next()) // if found, create a the StaffMember object 
            {
                return new StaffMember(res.getInt("id"), 
                                       res.getString("username"), 
                                       res.getBytes("enc_password"),
                                       res.getString("first_name"), 
                                       res.getString("last_name"), 
                                       res.getBoolean("full_time"));
            }
            else 
            {
                System.err.println("EMPTY SET - No Staff Member Found matching the criteria");
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /** getAllByProperties
     * returns a Set of StaffMembers that match the given criteria
     * @param query an SQLBuild query
     * @return a Set containing all of the StaffMembers that match the given criteria
     */
    public Set<StaffMember> getAllByProperties(SQLBuilder query) 
    {
          Set<StaffMember> staffMembers = new HashSet<>();
          
          try 
          {            
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
            while(res.next()) // While there's a StaffMember, create a the StaffMember object and add it to a Set
            {
                staffMembers.add(new StaffMember( res.getInt("id"), 
                                        res.getString("username"), 
                                        res.getBytes("enc_password"),
                                        res.getString("first_name"), 
                                        res.getString("last_name"), 
                                        res.getBoolean("full_time"))
                                );
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return staffMembers;
    }
    
    
    /** removeById
     * Remove a StaffMember from the database given its Id
     * @param id the id of the StaffMember to remove     * 
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
     * WARNING: Removes all StaffMembers from the database that match the given criteria
     * @param query the criteria to match
     * @throws SQLException
     */
    public void removeByProperty(SQLBuilder query) throws SQLException 
    {
        GPSISDataMapper.removeByPropertyHelper(query, this.tableName);        
    }

    /** put
     * Put a given StaffMember object onto the Database. Similar to the put method in a Map data structure. Used for INSERT and UPDATE
     * @param o The StaffMember object
     */
    public void put(StaffMember o) 
    {
        SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("username","=",""+o.getUsername())
                .SET("first_name", "=", ""+o.getFirstName())
                .SET("last_name","=",""+o.getLastName())
                .SET("enc_password", "=",""+o.getEncryptedPassword())
                .SET("full_time", "=", "1");
        try 
        {
            putHelper(sql, this.tableName);
        } 
        catch (SQLException e) 
        {
        	System.err.println(e.getMessage());
        }

    }
    
    /** removeByProperty
     * 
     * @param p
     * @param v
     *
	public void removeByProperty(String p, String v)
	{
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    
    /** updateByProperties
     * Update an object on the database given by its properties
     * @param Obj the 
     * @param where
     * @throws SQLException
     *
    public void updateByProperties(StaffMember Obj, SQLBuilder where) throws SQLException{
        SQLBuilder set;
        set = new SQLBuilder("first_name","=", Obj.getLastName()).SET("last_name","=","Anderson");   
        
        //Just push in all the relevant values, dont worry about the rest.
        GPSISDataMapper.updateByPropertiesHelper(set,where, this.tableName);
        System.out.println("Check table");
    }*/
    
    /*
    public static void main(String[] args) throws SQLException
    {
        StaffMemberDMO.connectToDatabase();
        StaffMemberDMO staffMembertbl;
        staffMembertbl = StaffMemberDMO.getInstance();
        
        Set<StaffMember> staffMembers = staffMembertbl.getAll();
        
        for(StaffMember staff : staffMembers)
        {
            System.out.println(staff.getLastName());
        }
     
        
        //Example usage updateByProperties.
        StaffMember staff = staffMembertbl.getById(1);
       
        //staffMembertbl.updateByProperties(staff, new SQLBuilder("first_name", "=","Mr."));
       
        
        
        //Example usage removeByProperty, Should Remove skas
        staffMembertbl.removeByProperty(new SQLBuilder("username", "=","skas"));
        
        
        //Example usage, should add  Salman into database
   
        StaffMember aStaff = new StaffMember("sally", "password", "Salman", "K", true);
        staffMembertbl.put(aStaff);
     
    }*/
}
