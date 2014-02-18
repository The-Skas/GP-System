package mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import object.Room;
import framework.GPSISDataMapper;

public class RoomDMO extends GPSISDataMapper<Room> {
    
        
        private static RoomDMO instance;;
    
        public static RoomDMO getInstance() 
        {
            if(instance == null)
            {
                instance = new RoomDMO();
            }
            return instance;
        }

        private RoomDMO(){}

	
	@Override
	public List<Room> getAllByProperties(SQLBuilder query) {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Room getByProperties(SQLBuilder query) {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
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
	
}

/**
 * End of File: RoomDMO.java
 * Location: gpsis/mapper
 */