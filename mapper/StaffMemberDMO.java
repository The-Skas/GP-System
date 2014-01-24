package mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import framework.GPSISDataMapper;
import object.StaffMember;

public class StaffMemberDMO extends GPSISDataMapper<StaffMember> {

	@Override
	public Set<StaffMember> getAll() {
		// TODO Auto-generated method stub
		return null;
		
		
	}

	@Override
	public StaffMember getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public StaffMember getByProperties(Map<String, String> params) {
		try {
			// build SQL
			String sql = "SELECT id";
			for (String column : params.keySet())
			{
				sql += ", " + column; // add the column to the statement
			}
			sql += " FROM StaffMember";
			
			PreparedStatement pS = dbConnection.prepareStatement(sql);
			ResultSet res = pS.executeQuery();
			ResultSetMetaData rsmd = res.getMetaData(); // retrieve column types
			
			sql = "SELECT * FROM StaffMember WHERE "; // rewrite the SELECT to include all fields
			
			Set<String> keys = params.keySet();
			int i = 0;
			for (String column : params.keySet())
			{
				sql += column + " = ? "; // define the parameters
				if (i < params.keySet().size() - 1)
					sql += " AND ";
				i++;
			}
			
			pS = dbConnection.prepareStatement(sql);
			i = 1;
			for (String column : params.keySet())
			{
				pS.setObject(i, params.get(column), rsmd.getColumnType(i + 1)); // add the parameters to the Statement
				i++;
			}
			
			System.out.println(pS);
			res = pS.executeQuery(); // execute the final query
			
			if (res.next()) { // if found, create a the StaffMember object
				
				return new StaffMember(res.getInt("id"), res.getString("username"), res.getBytes("enc_password"), res.getString("first_name"), res.getString("last_name"), res.getBoolean("full_time"));
			} else {
				System.err.println(pS);
				System.err.println("EMPTY SET");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void removeById(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeByProperty(String p, String v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void put(StaffMember o) {
		
		if (o.getId() != 0) // if the object already exists on the database, use UPDATE
			try
			{
				PreparedStatement pS = dbConnection.prepareStatement("UPDATE StaffMember SET first_name = ?, last_name = ?, enc_password = ?, full_time = ? WHERE staff_member.id = ?");
				pS.setString(1, o.getFirstName());
				pS.setString(2, o.getLastName());
				pS.setBytes(3, o.getEncryptedPassword());
				pS.setBoolean(4, o.isFullTime());
				pS.setInt(5, o.getId());
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
				PreparedStatement pS = dbConnection.prepareStatement("INSERT INTO StaffMember (username, enc_password, first_name, last_name,  full_time) VALUES (?, ?, ?, ?, ?)");
				pS.setString(1, o.getUsername());
				pS.setBytes(2, o.getEncryptedPassword());
				pS.setString(3, o.getFirstName());
				pS.setString(4, o.getLastName());
				pS.setBoolean(5, o.isFullTime());
				pS.executeUpdate();
 
  			}
  			catch (SQLException e)
  			{
  				e.printStackTrace();
  			}
		  
  		}		
	}

	@Override
	public Set<StaffMember> getAllByProperties(Map<String, String> p) {
		// TODO Auto-generated method stub
		return null;
	}

}
