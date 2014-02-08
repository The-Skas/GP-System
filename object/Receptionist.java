/**
 * 
 */
package object;

import java.util.Calendar;
import java.util.Date;

/**
 * @author VJ
 *
 */
public class Receptionist extends StaffMember {

	public Receptionist(int id, String u, byte[] p, String fN, String lN, boolean fT, Calendar sD, boolean oM, int hA) {
		super(id, u, p, fN, lN, fT, sD, oM, "Receptionist", hA);
	}
	
	public boolean isAvailable(Date c)
	{
		return !this.unavailables.contains(c) && !this.holidays.contains(c);
	}

}
