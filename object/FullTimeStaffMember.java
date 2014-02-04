package object;

public class FullTimeStaffMember extends StaffMember {

	public FullTimeStaffMember(int id, String u, byte[] p, String fN, String lN)
	{
		super(id, u, p, fN, lN, true);
	}
	
	public FullTimeStaffMember(String u, String p, String fN, String lN )
	{
		super(u, p, fN, lN, false);
	}
}
