package mapper;
/** StaffMemberDMO
 * This Data Mapper contains all of the methods concerned with the Staff Member Table.
 * There are many-to-many relations with other Entities and therefore there are more complex methods in here.
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import object.MedicalStaffMember;
import object.Receptionist;
import object.Register;
import object.StaffMember;
import object.TaxForm;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class StaffMemberDMO extends GPSISDataMapper<StaffMember> 
{   
	// stores the only instance of this DataMapper
	private static StaffMemberDMO instance;
	
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
    
    /** StaffMemberDMO Constructor 
	 * This is Private as part of a Singleton implementation.
	 * @param tableName
	 */
    private StaffMemberDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    public void addAvailability()
    {
    	// TODO StaffMemberDMO:addAvailability
    }
        
    /** create a new relation between the StaffMember and Register in StaffRegister, check if date exists in Register before creating
     * 
     * @param sM
     */
    public void addHoliday(StaffMember sM, Date dateOfHol)
    {
    	// TODO StaffMemberDMO:addHoliday
    	
    	// check if Date exists
    		// INSERT INTO Register COLUMNS (date) VALUES (dateOfHol.date())
  
    		// get last insert id (is the Date id) 
    	
    	// INSERT INTO StaffMemberRegister (sM.getId(), dateId, Register.HOLIDAY);
    	
    }
    
    public void addSpeciality()
    {
    	// TODO StaffMemberDMO:addSpeciality
    }
    
    public void addTaxForm()
    {
    	// TODO StaffMemberDMO:addTaxForm
    }
    
    /** buildStaffMember
     * builds a Staff Member object from the given Result Set. Used as a helper method in retrieving StaffMembers from the Database
     * Returns the correct object type (Receptionist or MedicalStaffMember) and includes their temporary contract if needed
     * @param res
     * @return a complete Staff Member
     * @throws SQLException
     * @throws EmptyResultSetException 
     */
    private StaffMember buildStaffMember(ResultSet res) throws SQLException, EmptyResultSetException
    {
    	if (res != null) // if found, create a the StaffMember object 
        {
        	boolean fullTime;
        	// check if temporary staff entry exists in database. SELECT * FROM TempStaffMember WHERE TempStaffMember.staff_member_id = res.getInt("id");
        	ResultSet tempCheckRes = GPSISDataMapper.getResultSet(new SQLBuilder("staff_member_id", "=", ""+res.getInt("id")), "TempStaffMember");
        	Calendar cal = Calendar.getInstance();
        	
        	Date endDate = null;
        	if (tempCheckRes.first())
        	{
        		cal.setTime(tempCheckRes.getDate("end_date"));
        		// temp contract and on full time
        		endDate = cal.getTime();
        		fullTime = true;
        	}
        	else
        		fullTime = res.getBoolean("full_time");
        	
        	cal.setTime(res.getDate("start_date"));
        	Date startDate = cal.getTime();
        	
        	System.err.println("Username: "+ res.getString("username"));
        	System.err.println("String:\t" + res.getString("enc_password"));
        	System.err.println("Bytes:\t" + new String(res.getBytes("enc_password")));
        	//System.err.println("Decrypted Password:\t" + StaffMember.decrypt(res.getBytes("enc_password")));
        	if (res.getString("role").equals("Receptionist")) // if the Staff Member is a Receptionist
        	{
        		Receptionist r = new Receptionist(res.getInt("id"),
        								res.getString("username"),
        								res.getString("enc_password"),
        								res.getString("first_name"),
        								res.getString("last_name"),
        								fullTime,
        								startDate,
        								res.getBoolean("office_manager"),
        								res.getInt("holiday_allowance"));
        		if (endDate != null)
        			r.setTemporary(endDate);
        		return r;
        	}
        	else // if the Staff Member is a MedicalStaffMember (Doctor or Nurse)
        	{
        		MedicalStaffMember mSM = new MedicalStaffMember(res.getInt("id"),
											res.getString("username"),
											res.getString("enc_password"),
											res.getString("first_name"),
											res.getString("last_name"),
											fullTime,
											startDate,
											res.getBoolean("office_manager"),
											res.getString("role"), 
											res.getInt("holiday_allowance"));
        		if (endDate != null)
        			mSM.setTemporary(endDate);
        		return mSM;
        	}
            
        }
        else 
        {
        	// throw Exception because of Empty set
        	throw new EmptyResultSetException();
        }
    }

    /** getAbsences
     * 
     * @param o
     * @return
     */
    public Set<Date> getAbsences(StaffMember o) throws EmptyResultSetException
    {
    	String sql = "SELECT * FROM Register WHERE staff_member_id = ?";
    	
    	
   		HashSet<Date> registered = new HashSet<Date>();
   		HashSet<Date> absences = new HashSet<Date>();
		Calendar cal = Calendar.getInstance();
		try 
		{
			PreparedStatement pS = dbConnection.prepareStatement(sql);
	    	pS.setInt(1, o.getId());
			ResultSet resRegistered = pS.executeQuery();
			while (resRegistered.next())
			{
				cal.setTime(resRegistered.getDate("date"));
	        	Date register = cal.getTime();				
	        	registered.add(register);
			}
			
			
			Calendar start = Calendar.getInstance();
			start.set(2014, 01, 01);
			Calendar end = Calendar.getInstance();
			end.set(2014, 01, 19);
			
			for (Date registeredDate : registered)
			{
				for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
					if (fm.format(registeredDate.getTime()).equals(fm.format(date.getTime())))
					{
						System.out.println("PRESENT ON: " +  fm.format(date.getTime()));
					}
					else
					{
						absences.add(date);
						System.out.println("ABSENT ON: " +  fm.format(date.getTime()));
					}
				}
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
		if (absences.isEmpty())
        	throw new EmptyResultSetException();
        else
        	return absences;
    	
    }
    
    /** getAllByProperties
     * returns a Set of StaffMembers that match the given criteria
     * @param query an SQLBuild query
     * @return a Set containing all of the StaffMembers that match the given criteria
     */
    @Override
	public Set<StaffMember> getAllByProperties(SQLBuilder query) throws EmptyResultSetException
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
        if (staffMembers.isEmpty())
        	throw new EmptyResultSetException();
        else
        	return staffMembers;
    }
    /** getAllHolidays
     * retrieves all of the Holidays for a Staff Member
     * @param
     * @return
     */
    public Set<Date> getAllHolidays(StaffMember o) throws EmptyResultSetException
    {
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", ""+o.getId()).AND("availability", "=", "0");
		HashSet<Date> holidays = new HashSet<Date>();
		Calendar cal = Calendar.getInstance();
		try 
		{
			ResultSet resHolidays = GPSISDataMapper.getResultSet(sql, "Register");
			while (resHolidays.next())
			{
				cal.setTime(resHolidays.getDate("date"));
	        	Date holiday = cal.getTime();
				
				holidays.add(holiday);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if (holidays.isEmpty())
        	throw new EmptyResultSetException();
        else
        	return holidays;
    	
    }
    
    public Set<Date> getAvailables()
    {
    	// TODO StaffMemberDMO:getAvailables
		return null;
    	
    }
    
    /** getByProperties
     * Returns the first StaffMember object matching the criteria
     * @param query an SQLBuilder query
     * @return the first StaffMember object in the ResultSet
     */
    @Override
	public StaffMember getByProperties(SQLBuilder query) throws EmptyResultSetException
    {
        try 
        {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
            
            if (res.next()) // if found, create a the StaffMember object 
            {
            	return this.buildStaffMember(res);
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        // throw Exception because of empty result set
        throw new EmptyResultSetException();
    }
    
    public Set<TaxForm> getTaxForms()
    {
    	// TODO StaffMemberDMO:getTaxForms
		return null;
    	
    }
    
    
    /** put
     * Put a given StaffMember object onto the Database. Similar to the put method in a Map data structure. Used for INSERT and UPDATE
     * @param o The StaffMember object
     */
    @Override
	public void put(StaffMember o) 
    {
    	SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String sD = fm.format(o.getStartDate().getTime());
        SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("username","=",""+o.getUsername())
                .SET("enc_password", "=",o.getEncryptedPassword())
                .SET("first_name", "=", ""+o.getFirstName())
                .SET("last_name","=",""+o.getLastName())
                .SET("full_time", "=","" + (o.isFullTime()? 1 :0))
                .SET("start_date", "=", sD)
                .SET("office_manager", "=", "" + (o.isOfficeManager()? 1 :0))
                .SET("role", "=", o.getRole())
                .SET("holiday_allowance", "=", "" + o.getHolidayAllowance());
        try 
        {
            putHelper(sql, this.tableName, o);
        } 
        catch (SQLException e) 
        {
        	System.err.println(e.getMessage());
        }
    }
    
    /** register
     * registers attendance for today for a given Staff Member
     * @param sM the Staff Member to register
     */
    public void register(StaffMember sM)
    {
    	SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String today = fm.format(new Date().getTime());
    	// Check if user's already registered on Database
    	try {
    		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", "" + sM.getId()).AND("date", "=", today);
			ResultSet registerdCheckRes = GPSISDataMapper.getResultSet(sql, "Register");
			
			if (registerdCheckRes.next())
				System.out.println("Already Registered for Today!");
			else
			{
				// Register User for today :D
		    	sql = new SQLBuilder("id","=","0")
			        .SET("staff_member_id","=",""+sM.getId())
			        .SET("date", "=", today);
				putHelper(sql, "Register", new Register(sM, new Date(), Register.ALLDAY));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    /** removeById
     * Remove a StaffMember from the database given its Id
     * @param id the id of the StaffMember to remove     * 
     */
    @Override
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
    
    /** makeTemporary
     * 
     */
    public void setTemporary(StaffMember o)
    {
    	SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String eD = fm.format(o.getEndDate().getTime());
    	SQLBuilder sql = new SQLBuilder("staff_member_id", "=", ""+o.getId()).SET("end_date", "=", eD);
    	
    	try
    	{
    		putHelper(sql, "TempStaffMember", o);
    	}
    	catch (SQLException e)
    	{
    		System.err.println(e.getMessage());
    	}
    }
}
