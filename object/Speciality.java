/** Speciality
 * An Object representing a Speciality
 * 
 * @author Vijendra Patel (vp302)
 */
package object;

import mapper.SQLBuilder;
import exception.DuplicateEntryException;
import exception.EmptyResultSetException;
import framework.GPSISObject;

public class Speciality extends GPSISObject {
	private String	name;

	/** Speciality Constructor
	 * Used when retrieving Speciality objects from the Database
	 * @param id
	 * @param n
	 */
	public Speciality(int id, String n) {
		this.id = id;
		this.name = n;
	}

	/** Speciality Constructor
	 * Used when creating a new Speciality
	 * @param n
	 * @throws DuplicateEntryException
	 */
	public Speciality(String n) throws DuplicateEntryException {
		this.name = n;
		// check for duplicates in StaffMember table
		SQLBuilder sql = new SQLBuilder("name", "=", n);
		try {
			specialityDMO.getByProperties(sql);
			throw new DuplicateEntryException();
		} catch (EmptyResultSetException e) // if there's an Empty Result Set,
											// this Speciality is not taken :D
		{
			this.name = n;
			specialityDMO.put(this);
		}
	}

	/** getName
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/** setName
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}

/**
 * End of File: Speciality.java 
 * Location: object
 */