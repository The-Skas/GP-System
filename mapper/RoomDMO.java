package mapper;

import framework.GPSISDataMapper;
import object.Room;
import object.StaffMember;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoomDMO extends GPSISDataMapper<Room> {
	
	public Set<Room> getAll() {
		try 
		{
			PreparedStatement getAll = dbConnection.prepareStatement("SELECT * FROM room");
			
			ResultSet r = getAll.executeQuery();
			
			Set<Room> returnSet = new HashSet<Room>();
			
			while (r.next())
			{
				returnSet.add(new Room(r.getInt(0), r.getString(1)));
			}
			return returnSet;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void put(Room o)	{
		if (o.getId() != 0) // if the object already exists on the database, use UPDATE
			try
			{
				PreparedStatement pS = dbConnection.prepareStatement("UPDATE room SET description = ? WHERE room.id = ?");
				pS.setString(1, o.getDescription());
				pS.setInt(2, o.getId());
				pS.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else // Use INSERT as the Object needs to be created on the database
		{
			try
			{
				PreparedStatement pS = dbConnection.prepareStatement("INSERT INTO room (description) VALUES (?)");
  				pS.setString(1, o.getDescription());
  				pS.executeUpdate();
  			}
  			catch (SQLException e)
  			{
  				e.printStackTrace();
  			}
		  
  		}
	}

	public Room getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeById(int id) {
		// TODO Auto-generated method stub
		
	}

	public void removeByProperty(String p, String v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Room> getAllByProperties(Map<String, String> a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room getByProperties(Map<String, String> p) {
		// TODO Auto-generated method stub
		return null;
	}

}

/**
 * End of File: RoomDMO.java
 * Location: gpsis/mapper
 */