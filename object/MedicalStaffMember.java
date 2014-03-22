/** MedicalStaffMember
 * An Object representing a Medical Staff Member
 * 
 * @author Vijendra Patel (vp302)
 */
package object;

import java.util.Date;
import exception.DuplicateEntryException;

public class MedicalStaffMember extends StaffMember {

	/** MedicalStaffMember Constructor
	 * Used when retrieving a Medical Staff Member from the Database
	 * @param id
	 * @param u
	 * @param p
	 * @param fN
	 * @param lN
	 * @param fT
	 * @param sD
	 * @param oM
	 * @param r
	 * @param hA
	 */
	public MedicalStaffMember(int id, String u, String p, String fN, String lN, boolean fT, Date sD, boolean oM, String r, int hA) {
		super(id, u, p, fN, lN, fT, sD, oM, r, hA);
	}

	/** MedicalStaffMember Constructor
	 * Used when Creating a new Medical Staff Member
	 * @param u
	 * @param p
	 * @param fN
	 * @param lN
	 * @param fT
	 * @param sD
	 * @param oM
	 * @param r
	 * @param hA
	 * @throws DuplicateEntryException
	 */
	public MedicalStaffMember(String u, String p, String fN, String lN, boolean fT, Date sD, boolean oM, String r, int hA) throws DuplicateEntryException {
		super(u, p, fN, lN, fT, sD, oM, r, hA);
	}

}

/**
 * End of File: Register.java 
 * Location: object
 */