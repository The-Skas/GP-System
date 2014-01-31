package framework;
/** GPSISFramework
 * Superclass for every Data Mapper in GPSIS. Data Mappers will be children of this node.
 * NOTE: Every class that extends this model SHOULD be appended with DMO. e.g PatientDMO, CareProgrammeDMO etc.
 * 
 * @author Vijendra Patel
 * @version 2
 */
import mapper.SQLBuilder;
import static mapper.SQLBuilder.*;
import java.util.Map;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import object.StaffMember;


public abstract class GPSISDataMapper<T> {
	protected String tableName;
        
	protected static Connection dbConnection; // store database connection. Only child elements can use this.
	
        
        protected static String SQLSelect = "SELECT * FROM ";
        protected static String SQLDelete = "DELETE FROM ";
        protected static String SQLUpdate = "";
	/** connectToDatabase
	 * Initiate a connection to the database and store that connection so all subclasses can use it.
	 */
        
        public static Connection getDbConnection()
        {
            return dbConnection;
        }
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
        
        /** getResultSet
         * returns a ResultSet Object by parsing the SQLBuilder, and 
         * executing it by using prepared statements. This is to avoid SQL Injections.
         * @param sqlQ
         * @param tableName
         * @return
         * @throws SQLException 
         */
        public static ResultSet getResultSet(SQLBuilder sqlQ, String tableName ) throws SQLException
        {
            String sql = "SELECT * FROM "+tableName + " WHERE ";

            
            return sqlQ.prepareAndExecute(dbConnection, sql);

            
        }
        public static void updateByPropertiesHelper(SQLBuilder set, SQLBuilder where, String tableName) throws SQLException
        {
            String query = "UPDATE "+tableName+" SET ";
            query=set.toPreparedStatement(query);
            query += " WHERE ";
            query=where.toPreparedStatement(query);

            PreparedStatement pS = GPSISDataMapper.dbConnection.prepareStatement(query);

            int i = 1;
            for(String [] block: set.qBlocks)
            {
                pS.setString(i, block[3]);
                i++;
            }

            for(String [] block: where.qBlocks)
            {
                pS.setString(i, block[3]);
                i++;
            }
            pS.executeUpdate();
        }
        public static void putHelper(SQLBuilder setQ,String tableName) throws SQLException
        {
            String query = "INSERT INTO "+tableName+" SET ";
            //set query
            query = setQ.toPreparedStatement(query)+" ON DUPLICATE KEY UPDATE "+setQ.toPreparedStatement("");
             
            PreparedStatement pS =dbConnection.prepareStatement(query);
            int i = 1;
            for(String[] qBlock : setQ.qBlocks)
            {
                pS.setString(i, qBlock[VALUE]);
                i++;
            }
            for(String[] qBlock : setQ.qBlocks)
            {
                pS.setString(i, qBlock[VALUE]);
                i++;
            }
            System.out.println(pS);

            pS.executeUpdate();
        }
        public static void removeByPropertyHelper(SQLBuilder sqlQ, String tableName) throws SQLException
        {
            String sql = "DELETE FROM "+tableName + " WHERE ";
            
            sql=sqlQ.toPreparedStatement(sql);
            
            PreparedStatement pS =dbConnection.prepareStatement(sql);
            sqlQ.prepare(pS);
            pS.executeUpdate();
            
        }
	/** getAll
	 * return a set containing all of the elements in the table for the child element
	 * @return a Set of elements from the table in their Object form.
	 * @throws SQLException 
	 */
	public abstract Set<T> getAll();
        {
            
        }
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
	public abstract T getByProperties(SQLBuilder query);
	
	
	/** getAllByProperty
	 * return a Set containing all of the Rows that match the given criteria
	 * @param p the Property to check
	 * @param v the Value of the Property to match
	 * @return a Set containing all of the Objects that match the Property and Value
	 */
	public abstract Set<T> getAllByProperties(SQLBuilder query);
	
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
