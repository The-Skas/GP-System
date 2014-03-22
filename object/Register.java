/** Register
 * An Object representing a Register
 * 
 * @author Vijendra Patel (vp302)
 */
package object;

import java.util.Date;

import framework.GPSISObject;

public class Register extends GPSISObject {

	private StaffMember		staffMember;
	private Date			date;
	private int				availability;

	public static final int	HOLIDAY		= 0;
	public static final int	MORNING		= 1;
	public static final int	AFTERNOON	= 2;
	public static final int	ALLDAY		= 3;

	/** Register Constructor
	 * Used when retrieving a Register object from the Database
	 * @param i
	 * @param sM
	 * @param d
	 * @param a
	 */
	public Register(int i, StaffMember sM, Date d, int a) {
		this.id = i;
		this.staffMember = sM;
		this.date = d;
		this.availability = a;
	}

	/** Register Constructor
	 * Used when creating a new Register object
	 * @param sM
	 * @param d
	 * @param a
	 */
	public Register(StaffMember sM, Date d, int a) {
		this.staffMember = sM;
		this.date = d;
		this.availability = a;

		registerDMO.put(this);
	}

	/** getAvailability
	 * @return the availability
	 */
	public int getAvailability() {
		return availability;
	}

	/** getDate
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/** getStaffMember
	 * @return the staffMember
	 */
	public StaffMember getStaffMember() {
		return staffMember;
	}

	/** getAvailability
	 * @param availability
	 *            the availability to set
	 */
	public void setAvailability(int availability) {
		this.availability = availability;
	}

	/** getReadableAvailability
	 * @return
	 */
	public String getReadableAvailability() {
		switch (this.availability) {
			case 0:
				return "Holiday";
			case 1:
				return "Morning";
			case 2:
				return "Afternoon";
			case 3:
				return "All Day";
		}
		return "";
	}

	/** setDate
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/** setStaffMember
	 * @param staffMember
	 *            the staffMember to set
	 */
	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}
}

/**
 * End of File: Register.java 
 * Location: object
 */