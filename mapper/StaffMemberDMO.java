package mapper;
/** StaffMemberDMO
 * This Data Mapper contains all of the methods concerned with the Staff Member Table.
 * There are many-to-many relations with other Entities and therefore there are more complex methods in here.
 */
import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import object.CalendarAppointment;
import object.MedicalStaffMember;
import object.Receptionist;
import object.Register;
import object.Speciality;
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
    	SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String holiday = fm.format(dateOfHol.getTime());
    	
    	try {
	    	SQLBuilder sql = new SQLBuilder("id","=","0")
		        .SET("staff_member_id","=",""+sM.getId())
		        .SET("date", "=", holiday)
		        .SET("availability", "=", ""+Register.HOLIDAY);
			putHelper(sql, "Register", new Register(sM, dateOfHol, Register.HOLIDAY));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    public void addSpeciality(StaffMember sM, Speciality sp)
    {
    	try {
	    	SQLBuilder sql = new SQLBuilder("id","=","0")
		        .SET("staff_member_id","=",""+sM.getId())
		        .SET("speciality_id", "=", ""+sp.getId());
			putHelper(sql, "Register", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
        	// check if temporary staff entry exists in database. SELECT * FROM TempStaffMember WHERE TempStaffMember.staff_member_id = res.getInt("id");
        	ResultSet tempCheckRes = GPSISDataMapper.getResultSet(new SQLBuilder("staff_member_id", "=", ""+res.getInt("id")), "TempStaffMember");
        	Calendar cal = Calendar.getInstance();
        	
        	Date endDate = null;
        	if (tempCheckRes.first())
        	{
        		cal.setTime(tempCheckRes.getDate("end_date"));
        		// temp contract and on full time
        		endDate = cal.getTime();
        	}
        	
        	cal.setTime(res.getDate("start_date"));
        	Date startDate = cal.getTime();
        	
        	if (res.getString("role").equals("Receptionist")) // if the Staff Member is a Receptionist
        	{
        		Receptionist r = new Receptionist(res.getInt("id"),
        								res.getString("username"),
        								res.getString("enc_password"),
        								res.getString("first_name"),
        								res.getString("last_name"),
        								res.getBoolean("full_time"),
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
											res.getBoolean("full_time"),
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
     * Returns a List of Dates that the Staff Member has been Absent in the past year
     * @param o
     * @return
     */
    public List<Date> getAbsences(StaffMember o) throws EmptyResultSetException
    {
    	String sql = "SELECT * FROM Register WHERE staff_member_id = ? AND date > ?";
    	
    	
   		HashSet<Date> registered = new HashSet<Date>();
   		List<Date> absences = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		try 
		{
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MONTH, -12); // the last 12 months
			Date oneYearAgo = start.getTime();
			PreparedStatement pS = dbConnection.prepareStatement(sql);
	    	pS.setInt(1, o.getId());
	    	pS.setDate(2, new java.sql.Date(oneYearAgo.getTime()));
			ResultSet resRegistered = pS.executeQuery();
			while (resRegistered.next())
			{
				cal.setTime(resRegistered.getDate("date"));
	        	Date register = cal.getTime();				
	        	registered.add(register);
			}
			
			
			if (o.getStartDate().before(oneYearAgo))
			{
				start.setTime(oneYearAgo);
			}
			else
			{
				start.setTime(o.getStartDate());
			}
			
			Calendar end = Calendar.getInstance();
			end.setTime(new Date());
			
			for (Date registeredDate : registered)
			{
				for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
					if (!fm.format(registeredDate.getTime()).equals(fm.format(date.getTime())))
					{
						absences.add(date);
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
    
    public List<CalendarAppointment> getAppointments(StaffMember o) throws EmptyResultSetException
    {
    	return calendarAppointmentDMO.getAllByProperties(new SQLBuilder("staff_member_id", "=",""+ o.getId()));
    }
    
    public List<Speciality> getSpecialities(StaffMember o) throws EmptyResultSetException
    {
    	List<Speciality> sMSpecialities = new ArrayList<Speciality>();
    	try {
    		
			ResultSet res = GPSISDataMapper.getResultSet(new SQLBuilder("staff_member_id", "=",""+ o.getId()), "StaffMemberSpeciality JOIN Speciality ON StaffMemberSpeciality.speciality_id = Speciality.id");
			while (res.next())
			{
				sMSpecialities.add(new Speciality(res.getInt("speciality_id"), res.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	if (sMSpecialities.isEmpty())
    		throw new EmptyResultSetException();
    	else
    		return sMSpecialities;
    }
    
    public List<TaxForm> getTaxForms(StaffMember o) throws EmptyResultSetException 
    {
		//return taxFormDMO.getAllByProperties(new SQLBuilder("staff_member_id", "=", ""+o.getId()));
    	throw new EmptyResultSetException();
	}
    
    /** getAllByProperties
     * returns a Set of StaffMembers that match the given criteria
     * @param query an SQLBuild query
     * @return a Set containing all of the StaffMembers that match the given criteria
     */
    @Override
	public List<StaffMember> getAllByProperties(SQLBuilder query) throws EmptyResultSetException
    {
          List<StaffMember> staffMembers = new ArrayList<>();
          
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
    /** getHolidays
     * retrieves all of the Holidays for a Staff Member in the past year
     * @param
     * @return
     */
    public List<Date> getHolidays(StaffMember o) throws EmptyResultSetException
    {
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", ""+o.getId()).AND("availability", "=", "0");
		List<Date> holidays = new ArrayList<Date>();
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

	public void removeHoliday(StaffMember staffMember, Date date) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	String d = fm.format(date.getTime());
		
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", ""+staffMember.getId()).AND("date", "=", d);
		
		try {
			removeByPropertyHelper(sql, "Register");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeSpeciality(StaffMember sM, Speciality s)
	{
		SQLBuilder sql = new SQLBuilder("staff_member_id", "=", ""+sM.getId()).AND("speciality_id", "=",""+ s.getId());
		
		try {
			removeByPropertyHelper(sql, "StaffMemberSpeciality");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
