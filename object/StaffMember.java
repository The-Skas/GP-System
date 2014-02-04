package object;

/**
 * 
 * @author Vijendra Patel
 * NOTES:
 * Staff Properties
 * 	- role (Doctor, Nurse, Receptionist, Office Manager)
 * 	- Specialities (Set)
 * 	- Absences
 * 	- Tax Office, P60 form?
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
import java.util.Set;

public class StaffMember extends GPSISObject {
	protected String username;
	private byte[] encryptedPassword;
	
	protected String firstName;
	protected String lastName;
	protected boolean fullTime;
 
	protected Set<Role> roles;
	protected Set<Speciality> specialities;
	protected Set<Absence> absences;
	
	// already exists in Database
	public StaffMember(int id, String u, byte[] p, String fN, String lN, boolean fT)
	{
		this.id = id;
		this.username = u;
		this.encryptedPassword = p;
		this.firstName = fN;
		this.lastName = lN;
		this.fullTime = fT;
		
	}
	
	// insert into database
	public StaffMember(String u, String p, String fN, String lN, boolean fT)
	{
		this.username = u;
		this.encryptedPassword = doHash(p);
		this.firstName = fN;
		this.lastName = lN;
		this.fullTime = fT;
		staffMemberDMO.put(this);
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setPassword(String p)
	{
		this.encryptedPassword = doHash(p);
	}
	
	public byte[] getEncryptedPassword()
	{
		return this.encryptedPassword;
	}
	
	
	public boolean isFullTime()
	{
		return this.fullTime;
	}
	
	public boolean isPartTime()
	{
		return !this.fullTime;
	}
	
	public static byte[] encrypt(String p)
	{
		return doHash(p);
	}
	
	private static byte[] doHash(String p)
	{
		String passphrase = "qwerty";
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
