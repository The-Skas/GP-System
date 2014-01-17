package gpsis.framework;
/** GPSISFramework
 * Superclass for the GPSIS Framework. The GPSIS System resides under this Root Node.
 * 
 * @author Vijendra Patel
 * @date 16/01/2014
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GPSISFramework {

	private static final GPSIS instance = new GPSIS(); // Singleton implementation
	protected Connection dbConnection; // store database connection. Only child elements can use this.
	
	/** getInstance() 
	 * returns the ONLY instance of GPSIS
	 * @return the instance of GPSIS
	 */
	public static GPSIS getInstance()
	{
		return instance;
	}
	
	/** initialise
	 * Initialises the Framework
	 * Initialising List: 
	 *  - Database Connection
	 *  - Core Framework Test
	 */
	public void initialise()
	{
		System.out.println("GPSIS Main Framework");
		System.out.print("\t- Connecting to Database");
		if (this.connectToDatabase())
			System.out.println("\t\tSuccess.");
		else
			System.out.println("\t\tFailed.");
		
	}
	
	/** connectToDatabase
	 * Initiate a connection to the database and store that connection so all subclasses can use it.
	 */
	private boolean connectToDatabase()
	{
		String username = "SEGJ";
		String password = "sv1e022g";
		String dbName = "SEGJ";
		String url = "jdbc:mysql://localhost:3307/" + dbName;
		
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
}

/**
 * End of File: GPSISFramework.java
 * Location: gpsis/framework
 */
