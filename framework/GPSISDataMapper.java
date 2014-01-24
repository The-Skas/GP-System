package framework;
/** GPSISFramework
 * Superclass for every Data Mapper in GPSIS. Data Mappers will be children of this node.
 * NOTE: Every class that extends this model SHOULD be appended with DMO. e.g PatientDMO, CareProgrammeDMO etc.
 * 
 * @author Vijendra Patel
 * @version 2
 */
import java.util.Map;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import object.StaffMember;


public abstract class GPSISDataMapper<T> {
	
	protected static Connection dbConnection; // store database connection. Only child elements can use this.
	
	/** connectToDatabase
	 * Initiate a connection to the database and store that connection so all subclasses can use it.
	 */
	public static boolean connectToDatabase()
	{
		//String username = "SEGJ";
		//String password = "sv1e022g";
		//String dbName = "SEGJ";
		//String url = "jdbc:mysql://localhost:3307/" + dbName;
		
		String username = "grpj";
		String password = "wv7PBUZBMXH88NP6";
		String dbName = "GPSIS_GROUPJ";
		String url = "jdbc:mysql://alineofcode.co.uk:3306/" + dbName;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection(url, username, password);
			return true;
		}
		catch (ClassNotFoundException e)
		{
			System.err.println("ClassNotFoundException: " + e.getMessage());
			//throw new ServletException("Class not found Error");
		}
		catch
		(SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
		return false;		
	}
	
	/** getAll
	 * return a set containing all of the elements in the table for the child element
	 * @return a Set of elements from the table in their Object form.
	 * @throws SQLException 
	 */
	public abstract Set<T> getAll();
	
	/** getById
	 * return a single Object of this table type
	 * @param id the numerical identifier for the row in the table
	 * @return a single Object which has the given id.
	 */
	public abstract T getById(int id);
	
	/** getByProperties
	 * return the first Object in the table that matches the given criteria
	 * A Map where its Keys represent columns of the table (properties, fields whatever you want to call them) and the Value of that Key is the filter.
	 * E.G:
	 * A Map that looks like this:
	 * 	{	
	 * 		"first_name" => "VJ",
	 * 		"last_name" => "Patel"
	 * 	}
	 * 
	 * will generate an SQL WHERE clause like this: WHERE first_name = 'VJ' AND last_name = 'Patel'
	 * 
	 * For ease, copy the code that i've written in mapper/StaffMemberDMO, Salman's going to look a bit more at Abstraction this weekend in the hope of 
	 * actually abstracting these nicely so code doesn't get repeated.
	 * 
	 * @param p  the Map with the filter parameters in
	 * @return a single Object which matches the Property and Value
	 */
	public abstract T getByProperties(Map<String, String> p);
	
	
	/** getAllByProperty
	 * return a Set containing all of the Rows that match the given criteria
	 * @param p the Property to check
	 * @param v the Value of the Property to match
	 * @return a Set containing all of the Objects that match the Property and Value
	 */
	public abstract Set<T> getAllByProperties(Map<String, String> p);
	
	/** removeById
	 * remove a Row from the table matched by a given id
	 * @param id the id of the row to remove
	 */
	public abstract void removeById(int id);
	
	/** removeByProperty
	 * WARNING: multiple rows from a table may be removed
	 * remove row(s) from the table that match a given criteria
	 * @param p the Property to check
	 * @param v the Value of the property to match
	 */
	public abstract void removeByProperty(String p, String v);
	
	/** put
	 * Saves a given object to the database. automatically chooses whether to UPDATE or INSERT into the table
	 * @param o the object of given type to insert or update on the database
	 * 
	 * EXAMPLE in RoomDMO Class
	 * public void put(Room o)
	 * {
	 * 		if (o.getId() != 0) // if the object already exists on the database, use UPDATE
	 * 			try
	 * 			{
	 * 				PreparedStatement pS = dbConnection.prepareStatement("UPDATE room SET description = ? WHERE room.id = ?");
	 * 				pS.setString(1, o.getDescription());
	 * 				pS.setInt(2, o.getId());
	 * 				pS.executeUpdate();
	 * 			}
	 * 			catch (SQLException e)
	 * 			{
	 * 				e.printStackTrace();
	 * 			}
	 * 		else // Use INSERT as the Object needs to be created on the database
	 * 		{
	 * 			try
	 * 			{
	 * 				PreparedStatement pS = dbConnection.prepareStatement("INSERT INTO room (description) VALUES (?)");
	 * 				pS.setString(1, o.getDescription());
	 * 				pS.executeUpdate();
	 * 			}
	 * 			catch (SQLException e)
	 * 			{
	 * 				e.printStackTrace();
	 * 			}
	 * 
	 * 		}
	 * 
	 */
	public abstract void put(T o);

}

/**
 * End of File: GPSISDataMapper.java
 * Location: gpsis/framework
 */
