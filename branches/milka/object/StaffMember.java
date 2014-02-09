package object;

/** StaffMember Object
 * An Abstract class defining and implementing the methods that are inherited by MedicalStaffMember and Receptionist
 * @author Vijendra Patel
 */


import exception.EmptyResultSetException;
import framework.GPSISObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Calendar;

public abstract class StaffMember extends GPSISObject {
	protected String username;
	private byte[] encryptedPassword;
	
	protected String firstName;
	protected String lastName;
	protected boolean fullTime;
	protected boolean officeManager;
	protected String role;
	protected int holidayAllowance;
	
	protected Calendar startDate;
	protected Calendar endDate = null; // null if not temporary

	protected Set<Date> holidays;
	protected Set<Date> absences;
	protected Set<Date> unavailables;
	protected Set<TaxForm> taxForms;
	
	public static final String RECEPTIONIST = "Receptionist";
	public static final String DOCTOR = "Doctor";
	public static final String NURSE = "Nurse";
	
	// already exists in Database
	protected StaffMember(int id, String u, byte[] p, String fN, String lN, boolean fT, Calendar sD, boolean oM, String r, int hA)
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
	
	// insert into database
	protected StaffMember(String u, String p, String fN, String lN, boolean fT, Calendar sD, boolean oM, String r, int hA)
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
	
	/** getFirstName
	 * returns the First Name of the Staff Member
	 * @return a string representation of the Staff Member's first name
	 */
	public String getFirstName()
	{
		return this.firstName;
	}
	
	/** getLastName
	 * returns the Last Name of the Staff Member
	 * @return a string representation of the Staff Member's last name
	 */
	public String getLastName()
	{
		return this.lastName;
	}
	
	/** getUsername
	 * returns the username for the Staff Member
	 * @return a string representation of the Staff Member's username
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	/** getRole
	 * returns the role of the Staff Member
	 * @return a string representation the Staff Member's role
	 */
	public String getRole()
	{
		return this.role;
	}
	
	public Calendar getStartDate()
	{
		return this.startDate;
	}
	
	/** isAvailable
	 * returns whether or not a StaffMember is available on this day
	 * @param c
	 * @return
	 */
	public abstract boolean isAvailable(Date c);
	
	
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
		
		// only return a Set if there are results inside
		if (!result.isEmpty())
			return result;
		else
			return null;
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

	/** isOfficeManager
	 * returns whether or not this Staff Member is an Office Manager or not
	 * @return true if this Staff Member is an Office Manager, false otherwise
	 */
	public boolean isOfficeManager()
	{
		return this.officeManager;
	}
	
	/** isDoctor
	 * returns whether or not this Staff Member is a Doctor or not
	 * @return true if this Staff Member is a Doctor, false otherwise
	 */
	public boolean isDoctor()
	{
		return this.role.equals("Doctor");
	}
	
	/** isNurse
	 * returns whether or not this Staff Member is a Nurse or not
	 * @return true if this Staff Member is a Nurse, false otherwise
	 */
	public boolean isNurse()
	{
		return this.role.equals("Nurse");
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
	public boolean isTemp()
	{
		return this.endDate != null;
	}
	
	/** makeTemporary
	 * makes this Staff Member temporary by giving them an End Date
	 * @param eD the End Date of the Contract
	 */
	public void makeTemporary(Calendar eD)
	{
		this.endDate = eD;
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
	
	/** getEncryptedPassword
	 * returns the encrypted password as an array of bytes, to convert to String, use new String(sM.getEncryptedPassword());
	 * @return the encrypted password as an array of bytes
	 */
	public byte[] getEncryptedPassword()
	{
		return this.encryptedPassword;
	}
	
	/** isFullTime()
	 * returns whether or not the Staff Member is working full time
	 * @return true if the Staff Member is working full time, false otherwise
	 */
	public boolean isFullTime()
	{
		return this.fullTime;
	}
	
	/** isPartTime()
	 * returns whether or not the Staff Member is working part time
	 * @return true if the Staff Member is working part time, false otherwise
	 */
	public boolean isPartTime()
	{
		return !this.fullTime;
	}
	
	/**
	 * 
	 */
	public void register()
	{
		
	}
	
	/**
	 * 
	 */
	public void addHoliday()
	{
		
	}
	
	
	/** public accessor to do the private method in which we hash a String
	 * 
	 * @param p the String to hash
	 * @return a byte array of the hashed String
	 */
	public static byte[] encrypt(String p)
	{
		return doHash(p);
	}
	
	/** doHash
	 * this where all of the magic Hashing works. The String is hashed using a secret Pass Phrase with MD5 and DES
	 * @param p the String to hash
	 * @return a byte array of the hashed string
	 */
	private static byte[] doHash(String p)
	{
		String passphrase = "qwerty"; // will change before final implementation
		byte[] salt = { (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99 };
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
		
		// generate key with pass phrase
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passphrase.toCharArray()); 
		
		try {

			SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
			
			// Create the Cipher
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
			
			// Initialise with key and parameters
			pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
			

			byte[] eP = pbeCipher.doFinal(p.getBytes());
			

			System.out.println("Encrypted String: " + eP);
			
			return eP;
			
			
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
