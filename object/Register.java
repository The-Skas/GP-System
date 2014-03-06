/**
 * 
 */
package object;

import java.util.Date;

import framework.GPSISObject;

/**
 * @author VJ
 *
 */
public class Register extends GPSISObject {
		
	private StaffMember staffMember;
	private Date date;
	private int availability;
	
	public static final int HOLIDAY = 0;
	public static final int MORNING = 1;
	public static final int AFTERNOON = 2;
	public static final int ALLDAY = 3;
	
	public Register(int i, StaffMember sM, Date d, int a)
	{
		this.id = i;
		this.staffMember = sM;
		this.date = d;
		this.availability = a;
	}

	public Register(StaffMember sM, Date d, int a) {
		this.staffMember = sM;
		this.date = d;
		this.availability = a;
		
		staffMemberDMO.putRegister(this);
	}

	/**
	 * @return the availability
	 */
	public int getAvailability() {
		return availability;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the staffMember
	 */
	public StaffMember getStaffMember() {
		return staffMember;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(int availability) {
		this.availability = availability;
	}
	
	public String getReadableAvailability()
	{
		switch (this.availability)
		{
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

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param staffMember the staffMember to set
	 */
	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}
}
