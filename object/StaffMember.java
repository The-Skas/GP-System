package object;

/** StaffMember Object
 * An Abstract class defining and implementing the methods that are inherited by MedicalStaffMember and Receptionist
 * @author Vijendra Patel
 */


import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import mapper.SQLBuilder;

import org.apache.commons.codec.binary.Base64;

import exception.DuplicateEntryException;
import exception.EmptyResultSetException;
import framework.GPSISObject;

public abstract class StaffMember extends GPSISObject {
	/** decrypt 
	 * This method should never be used in Practice. 
	 * and only compare encrypted passwords.
	 * @param eP a pre-hashed String to be de-hashed.
	 * @return
	 */
	public static String decrypt(String eP) {
		try 
		{
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			c.init(Cipher.DECRYPT_MODE, skeySpec, salt);
			byte[] dP = c.doFinal(Base64.decodeBase64(eP));
			return new String(dP);
		}
		catch (Exception ex)
		{
		    ex.printStackTrace();
		}
		return null;
	}
	protected String username;
		
	private String encryptedPassword;
	protected String firstName;
	protected String lastName;
	protected boolean fullTime;
	protected boolean officeManager;
	protected String role;
	
	protected int holidayAllowance;
	protected Date startDate;

	protected Date endDate = null; // null if not temporary
	protected Set<Date> holidays;
	protected Set<Date> absences;
	protected Set<Date> unavailables;
	
	protected Set<TaxForm> taxForms;
	
	// the Key to use when Encrypting and Decrypting
	static byte[] key = "20vPmHsKoAmSsA14".getBytes(); // needed 16 bytes, here's a combo of our Initials and the current year :D
	
	// A Salt to be used in conjunction with the Key
	static IvParameterSpec salt = new IvParameterSpec(key);
	public static final String RECEPTIONIST = "Receptionist";
	public static final String DOCTOR = "Doctor";
	
	public static final String NURSE = "Nurse";
	
	/** doHash
	 * Hashes a given String using AES and the static Key set in this class
	 * @param p the String to be hashed
	 * @return
	 */
	private static String doHash(String p) {
		try 
	    {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			c.init(Cipher.ENCRYPT_MODE, skeySpec, salt);
			byte[] eP = c.doFinal(p.getBytes());
			return Base64.encodeBase64String(eP);
	    } 
		catch (Exception ex) 
		{
			ex.printStackTrace();
	    }
	    return null;
	}
	
	/** public accessor to do the private method in which we hash a String
	 * 
	 * @param p the String to hash
	 * @return a byte array of the hashed String
	 */
	public static String encrypt(String p)
	{
		return doHash(p);
	}
	
	/** StaffMember Constructor
	 * This Constructor is used when retrieving Staff Member objects from the Database
	 * @param id The id column from the database
	 * @param u	 The username column from the database
	 * @param p  The enc_password column from the database
	 * @param fN The first_name column from the database
	 * @param lN The last_name column from the database
	 * @param fT The full_time column from the database
	 * @param sD The start_date column from the database
	 * @param oM The office_manager column from the database
	 * @param r  The role column from the database
	 * @param hA The holiday_allowance column from the database
	 */
	protected StaffMember(int id, String u, String p, String fN, String lN, boolean fT, Date sD, boolean oM, String r, int hA)
	{
		this.id = id;
		this.username = u;
		this.encryptedPassword = p;
		this.firstName = fN;
		this.lastName = lN;
		this.fullTime = fT;
		this.startDate = sD;
		this.officeManager = oM;
		this.role = r;
		this.holidayAllowance = hA;
	}
	
	/** StaffMember Constructor
	 * This Constructor is used when creating a New Staff Member object
	 * @param u  the username for the new Staff Member
	 * @param p  the password for the new Staff Member
	 * @param fN the First Name of the new Staff Member
	 * @param lN the Last Name of the new Staff Member
	 * @param fT if the New Staff Member is working Full Time
	 * @param sD when the New Staff Member starts working
	 * @param oM if the New Staff Member is an Office Manager
	 * @param r  the Role that the New Staff Member has
	 * @param hA the amount of holiday the Staff Member has yearly
	 */
	protected StaffMember(String u, String p, String fN, String lN, boolean fT, Date sD, boolean oM, String r, int hA) throws DuplicateEntryException
	{
		// check for duplicates in StaffMember table
		SQLBuilder sql = new SQLBuilder("username", "=", u);
		try 
		{
			staffMemberDMO.getByProperties(sql);
			throw new DuplicateEntryException();
		} 
		catch (EmptyResultSetException e) // if there's an Empty Result Set, this username is not taken
		{ 
			this.username = u;
			this.encryptedPassword = doHash(p);
			this.firstName = fN;
			this.lastName = lN;
			this.fullTime = fT;
			this.startDate = sD;
			this.officeManager = oM;
			this.role = r;
			this.holidayAllowance = hA;		
			
			staffMemberDMO.put(this);
		}
		
	}
	
	/**
	 * 
	 */
	public void addHoliday()
	{
		// TODO
	}
	
	/** getAbsences
	 * returns all of the Dates that this Staff Member has been absent
	 * @return a Set of Dates that this Staff Member has been absent for
	 * @throws EmptyResultSetException 
	 */
	public Set<Date> getAbsences() throws EmptyResultSetException
	{
		if (this.absences != null)
			return this.absences;
		else
		{
			return staffMemberDMO.getAbsences(this);
		}
	}
	
	/** getAllHolidays
	 * returns all of the Dates that this Staff Member has booked for a holiday
	 * @return a Set of Dates that the Staff Member has booked as Holiday
	 * @throws EmptyResultSetException 
	 */
	public Set<Date> getAllHolidays() throws EmptyResultSetException
	{
		if (this.holidays != null)
			return this.holidays;
		else
		{
			return staffMemberDMO.getAllHolidays(this);
		}
	}
	
	
	/** getCurrentYearHolidays
	 * returns all of the Dates that this Staff Member has booked for a holiday this year
	 * @return a Set of Dates that this Staff Member has booked for a holiday this year
	 */
	public Set<Date> getCurrentYearHolidays()
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		Date oneYearAgo = c.getTime();
		Set<Date> result = new HashSet<Date>();
		for (Date d : this.holidays)
		{
			if (d.getTime() > oneYearAgo.getTime()) // if the date is between NOW and 1 Year Ago
			{
				result.add(d);
			}
		}
		
		// only return a Set if there are results inside the Set
		if (!result.isEmpty())
			return result;
		else
			return null;
	}
	
	/** getEncryptedPassword
	 * returns the encrypted password as an array of bytes, to convert to String, use new String(sM.getEncryptedPassword());
	 * @return the encrypted password as an array of bytes
	 */
	public String getEncryptedPassword()
	{
		return this.encryptedPassword;
	}
	
	/** getEndDate
	 * 
	 */
	public Date getEndDate()
	{
		return this.endDate;
	}
	
	/** getFirstName
	 * returns the First Name of the Staff Member
	 * @return a string representation of the Staff Member's first name
	 */
	public String getFirstName()
	{
		return this.firstName;
	}
	
	/** getHolidayAllowance
	 * returns an integer representing the total amount of holiday the Staff Member is allocated
	 * @return the amount of holiday allocated yearly to the Staff Member
	 */
	public int getHolidayAllowance()
	{
		return this.holidayAllowance;
	}

	/** getHolidayLeft
	 * returns the amount of holiday the Staff Member has left by subtracting the holidays booked for this year
	 * @return the amount of holiday the Staff Member has left
	 */
	public int getHolidayLeft()
	{
		return this.holidayAllowance - holidays.size();
	}
	
	/** getLastName
	 * returns the Last Name of the Staff Member
	 * @return a string representation of the Staff Member's last name
	 */
	public String getLastName()
	{
		return this.lastName;
	}
	
	/** getRole
	 * returns the role of the Staff Member
	 * @return a string representation the Staff Member's role
	 */
	public String getRole()
	{
		return this.role;
	}
	
	public Date getStartDate()
	{
		return this.startDate;
	}
	
	/** getUsername
	 * returns the username for the Staff Member
	 * @return a string representation of the Staff Member's username
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	/** isAvailable
	 * returns whether or not a StaffMember is available on this day
	 * @param c
	 * @return
	 */
	public abstract boolean isAvailable(Date c);
	
	/** isDoctor
	 * returns whether or not this Staff Member is a Doctor or not
	 * @return true if this Staff Member is a Doctor, false otherwise
	 */
	public boolean isDoctor()
	{
		return this.role.equals("Doctor");
	}
	/** isFullTime()
	 * returns whether or not the Staff Member is working full time
	 * @return true if the Staff Member is working full time, false otherwise
	 */
	public boolean isFullTime()
	{
		return this.fullTime;
	}
	
	/** isNurse
	 * returns whether or not this Staff Member is a Nurse or not
	 * @return true if this Staff Member is a Nurse, false otherwise
	 */
	public boolean isNurse()
	{
		return this.role.equals("Nurse");
	}
	
	/** isOfficeManager
	 * returns whether or not this Staff Member is an Office Manager or not
	 * @return true if this Staff Member is an Office Manager, false otherwise
	 */
	public boolean isOfficeManager()
	{
		return this.officeManager;
	}
	
	/** isPartTime()
	 * returns whether or not the Staff Member is working part time
	 * @return true if the Staff Member is working part time, false otherwise
	 */
	public boolean isPartTime()
	{
		return !this.fullTime;
	}
	
	/** isReceptionist
	 * returns whether or not this Staff Member is a Receptionist or not
	 * @return true if this Staff Member is a Receptionist, false otherwise
	 */
	public boolean isReceptionist()
	{
		return this.role.equals("Receptionist");
	}
	
	/** isTemporary
	 * returns whether or not this Staff Member is a Temporary Staff Member
	 * @return true if this Staff Member is a Temporary Staff Member, false otherwise
	 */
	public boolean isTemporary()
	{
		return this.endDate != null;
	}
	
	/** register
	 * registers this Staff Member on the Database
	 */
	public void register()
	{
		staffMemberDMO.register(this);
	}
	
	
	/** setPassword
	 * set the password for the Staff Member given by a String
	 * @param p a String containing the new password (not hashed), this method will hash the password before saving it.
	 */
	public void setPassword(String p)
	{
		this.encryptedPassword = doHash(p);
	}
	
	/** setRole
	 * set the Role for the Staff Member given by a String. USE CONSTANT VALUES e.g StaffMember.RECEPTIONIST
	 * @param r
	 */
	public void setRole(String r)
	{
		this.role = r;
	}
	
	/** makeTemporary
	 * makes this Staff Member temporary by giving them an End Date
	 * @param eD the End Date of the Contract
	 */
	public void setTemporary(Date eD)
	{
		this.endDate = eD;
		staffMemberDMO.setTemporary(this);
	}
	
}
