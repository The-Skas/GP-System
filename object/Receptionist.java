/**
 * 
 */
package object;

import java.util.Date;

/**
 * @author VJ
 *
 */
public class Receptionist extends StaffMember {

	public Receptionist(int id, String u, byte[] p, String fN, String lN, boolean fT, Date sD, boolean oM, int hA) {
		super(id, u, p, fN, lN, fT, sD, oM, "Receptionist", hA);
	}
	
	public Receptionist(String u, String p, String fN, String lN, boolean fT, Date sD, boolean oM, int hA) {
		super(u, p, fN, lN, fT, sD, oM, "Receptionist", hA);
	}
	
	public boolean isAvailable(Date c)
	{
		return !this.unavailables.contains(c) && !this.holidays.contains(c);
	}

}
