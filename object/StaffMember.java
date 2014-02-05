package object;

/**
 * 
 * @author Vijendra Patel
 */


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
	
	// already exists in Database
	public StaffMember(int id, String u, byte[] p, String fN, String lN, boolean fT, Calendar sD, boolean oM, String r, int hA)
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
	public StaffMember(String u, String p, String fN, String lN, boolean fT, Calendar sD, boolean oM, String r, int hA)
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
	
	public Set<Date> getAllHolidays()
	{
		return this.holidays;
	}

	public Set<Date> getCurrentYearHolidays()
	{
		// TODO
		return null;
	}
	
	public Set<Date> getAbsences()
	{
		return this.absences;
	}
	
	public Set<Date> getUnavailables()
	{
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOfficeManager()
	{
		return this.officeManager;
	}
	
	public boolean isDoctor()
	{
		return this.role.equals("Doctor");
	}
	
	public boolean isNurse()
	{
		return this.role.equals("Nurse");
	}
	
	public boolean isReceptionist()
	{
		return this.role.equals("Receptionist");
	}
	
	public boolean isTemp()
	{
		return this.endDate != null;
	}
	
	public void setContract(Calendar eD)
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
	 * this where all of the magic Hashing works. The String is hashed using a secret Passphrase with MD5 and DES
	 * @param p the String to hash
	 * @return a byte array of the hashed string
	 */
	private static byte[] doHash(String p)
	{
		String passphrase = "qwerty"; // will change before final implementation
		byte[] salt = { (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99 };
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
		
		// generate key with passphrase
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passphrase.toCharArray()); 
		
		try {

			SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
			
			// Create the Cipher
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
			
			// Initialise with key and params
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
