/** Receptionist
 * An Object representing a Receptionist
 * 
 * @author Vijendra Patel (vp302)
 */
package object;

import java.util.Date;

import exception.DuplicateEntryException;

public class Receptionist extends StaffMember {

	/** Receptionist Constructor
	 * Used when retrieving a Receptionist from the Database
	 * @param id
	 * @param u
	 * @param p
	 * @param fN
	 * @param lN
	 * @param fT
	 * @param sD
	 * @param oM
	 * @param hA
	 */
	public Receptionist(int id, String u, String p, String fN, String lN, boolean fT, Date sD, boolean oM, int hA) {
		super(id, u, p, fN, lN, fT, sD, oM, "Receptionist", hA);
	}

	/** Receptionist Constructor
	 * Used when creating a new Receptionist
	 * @param u
	 * @param p
	 * @param fN
	 * @param lN
	 * @param fT
	 * @param sD
	 * @param oM
	 * @param hA
	 * @throws DuplicateEntryException
	 */
	public Receptionist(String u, String p, String fN, String lN, boolean fT, Date sD, boolean oM, int hA) throws DuplicateEntryException {
		super(u, p, fN, lN, fT, sD, oM, "Receptionist", hA);
	}

}

/**
 * End of File: Receptionist.java 
 * Location: object
 */