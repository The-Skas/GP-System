package object;

import framework.GPSISObject;
import mapper.RoomDMO;

public class Room extends GPSISObject {
	private String description;
	protected static RoomDMO tbl = new RoomDMO();
	
	// get DMO
	// put using DMO
	public Room(String desc)
	{
		this.description = desc;
		
		tbl.put(this);
	}
	
	// used when creating an instance from database by DMO
	public Room(int id, String desc)
	{
		this.id = id;
		this.description = desc;
	}

	public String getDescription()
	{
		return this.description;
	}
}
