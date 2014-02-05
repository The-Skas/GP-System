package mapper;
/** StaffMemberDMO
 * This Data Mapper contains all of the methods concerned with the Staff Member Table.
 * There are many-to-many relations with other Entities and therefore there are more complex methods in here.
 */
import framework.GPSISDataMapper;
import object.MedicalStaffMember;
import object.Receptionist;
import object.StaffMember;
import object.TaxForm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            	this.buildStaffMember(res);
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
                staffMembers.add(this.buildStaffMember(res));
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return staffMembers;
    }
    
    /** buildStaffMember
     * builds a Staff Member object from the given Result Set. Used as a helper method in retrieving StaffMembers from the Database
     * Returns the correct object type (Receptionist or MedicalStaffMember) and includes their temp contract if needed
     * @param res
     * @return a complete Staff Member
     * @throws SQLException
     */
    private StaffMember buildStaffMember(ResultSet res) throws SQLException
    {
    	if (res != null) // if found, create a the StaffMember object 
        {
        	boolean fullTime;
        	// check if temporary staff entry exists in database. SELECT * FROM TempStaffMember WHERE TempStaffMember.staff_member_id = res.getInt("id");
        	ResultSet tempCheckRes = GPSISDataMapper.getResultSet(new SQLBuilder("staff_member_id", "=", ""+res.getInt("id")), "TempStaffMember");
        	
        	Calendar endDate = null;
        	if (tempCheckRes.first())
        	{
        		// temp contract and on full time
        		endDate = Calendar.getInstance();
        		endDate.setTime(tempCheckRes.getDate("end_date"));
        		fullTime = true;
        	}
        	else
        		fullTime = res.getBoolean("full_time");
        	
        	Calendar startDate = Calendar.getInstance();
        	startDate.setTime(res.getDate("start_date"));
        	if (res.getString("role").equals("Receptionist")) // if the Staff Member is a Receptionist
        	{
        		Receptionist r = new Receptionist(res.getInt("id"),
        								res.getString("username"),
        								res.getBytes("enc_password"),
        								res.getString("first_name"),
        								res.getString("last_name"),
        								fullTime,
        								startDate,
        								res.getBoolean("office_manager"),
        								res.getInt("holiday_allowance"));
        		r.setContract(endDate);
        		return r;
        	}
        	else // if the Staff Member is a MedicalStaffMember (Doctor or Nurse)
        	{
        		MedicalStaffMember mSM = new MedicalStaffMember(res.getInt("id"),
											res.getString("username"),
											res.getBytes("enc_password"),
											res.getString("first_name"),
											res.getString("last_name"),
											fullTime,
											startDate,
											res.getBoolean("office_manager"),
											res.getString("role"), 
											res.getInt("holiday_allowance"));
        		mSM.setContract(endDate);
        		return mSM;
        	}
            
        }
        else 
        {
            System.err.println("EMPTY SET - No Staff Member Found matching the criteria");
        }
		return null;
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
    	SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String sD = fm.format(o.getStartDate().getTime());
        SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("username","=",""+o.getUsername())
                .SET("enc_password", "=",""+o.getEncryptedPassword())
                .SET("first_name", "=", ""+o.getFirstName())
                .SET("last_name","=",""+o.getLastName())
                .SET("full_time", "=","" + (o.isFullTime()? 1 :0))
                .SET("start_date", "=", sD)
                .SET("office_manager", "=", "" + (o.isOfficeManager()? 1 :0))
                .SET("role", "=", o.getRole())
                .SET("holiday_allowance", "=", "" + o.getHolidayAllowance())
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
    
    /**
     * 
     * @return
     */
    public Set<Date> getAllHolidays(StaffMember o)
    {
    	if (o.getAllHolidays() == null)
    	{
    		// retrieve holidays from database
    		// SELECT * FROM StaffMemberRegister WHERE staff_member_id = o.getId();
    		return null;
    	}
    	else
    	{
    		return o.getAllHolidays();
    	}
    }
    
    public Set<Date> getCurrentYearHolidays(StaffMember o)
    {
    	if (o.getCurrentYearHolidays() == null)
    	{
    		if (o.getAllHolidays() == null)
    		{
    			// retrieve holidays
    			this.getAllHolidays(o);
    			// recall function
    			return this.getCurrentYearHolidays(o);
    		}
    		else
    		{
    			return null;
    		}
    	}
    	else
    	{
    		return o.getCurrentYearHolidays();
    	}
    }
    
    public Set<Date> getAbsences(StaffMember o)
    {
    	if (o.getAbsences() == null)
    	{
    		// retrieve absences
    	}
    	else
    	{
    		
    	}
		return null;
    	
    }
    
    public Set<Date> getAvailables()
    {
		return null;
    	
    }
    
    public Set<TaxForm> getTaxForms()
    {
		return null;
    	
    }
    
    
    /** create a new relation between the StaffMember and Register in StaffRegister, check if date exists in Register before creating
     * 
     * @param sM
     */
    public void addHoliday(StaffMember sM, Date dateOfHol)
    {
    	
    	// INSERT INTO Register COLUMNS (date) VALUES (dateOfHol.date())
  
    	// get last insert id (is the Date id) 
    	
    	// INSERT INTO StaffMemberRegister (sM.getId(), dateId, true);
    	
    }
    
    public void addTaxForm()
    {
    	
    }
    
    public void addAvailability()
    {
    	
    }
}
