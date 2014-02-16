package framework;
/** GPSISFramework
 * Superclass for every Data Mapper in GPSIS. Data Mappers will be children of this node.
 * NOTE: Every class that extends this model SHOULD be appended with DMO. e.g PatientDMO, CareProgrammeDMO etc.
 * 
 * @author Vijendra Patel
 */
import static mapper.SQLBuilder.VALUE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import mapper.SQLBuilder;
import exception.EmptyResultSetException;


public abstract class GPSISDataMapper<T> extends GPSISFramework {
	protected String tableName;
        
	protected static Connection dbConnection; // store database connection. Only child elements can use this.
	
        
    protected static String SQLSelect = "SELECT * FROM ";
    protected static String SQLDelete = "DELETE FROM ";
    protected static String SQLUpdate = "";
    	
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
		String url = "jdbc:mysql://alineofcode.co.uk:3306/" + dbName + "?useUnicode=true&characterEncoding=UTF-8";
		
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
    
    public static Connection getDbConnection()
    {
        return dbConnection;
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
    
    /*
     * The PutHelper takes a SQLBuilder Object, that should be in SET form
     * and executes the query within the specified tableName, and sets the
     * passed object id with the created id.
     */
    public static void putHelper(SQLBuilder setQ,String tableName, GPSISObject object) throws SQLException
    {
        String query = "INSERT INTO "+tableName+" SET ";
        //set query
        query = setQ.toPreparedStatement(query)+" ON DUPLICATE KEY UPDATE "+setQ.toPreparedStatement("");
         
        PreparedStatement pS =dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
        
        ResultSet keyRs= pS.getGeneratedKeys();
        if(keyRs.next())
        {
            object.setId(keyRs.getInt(1));
        }
    }
    
    public static void removeByPropertyHelper(SQLBuilder sqlQ, String tableName) throws SQLException
    {
        String sql = "DELETE FROM "+tableName + " WHERE ";
        
        sql=sqlQ.toPreparedStatement(sql);
        
        PreparedStatement pS =dbConnection.prepareStatement(sql);
        sqlQ.prepare(pS);
        pS.executeUpdate();
        
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

    /** getAll
	 * return a set containing all of the elements in the table for the child element
	 * @return a Set of elements from the table in their Object form.
	 * @throws EmptyResultSetException 
	 */
	public Set<T> getAll() throws EmptyResultSetException
	{
		return this.getAllByProperties(new SQLBuilder());
	}
	
    /** getAllByProperty
	 * return a Set containing all of the Rows that match the given criteria
	 * @param p the Property to check
	 * @param v the Value of the Property to match
	 * @return a Set containing all of the Objects that match the Property and Value
	 */
	public abstract Set<T> getAllByProperties(SQLBuilder query) throws EmptyResultSetException;
	
	/** getById
	 * return a single Object of this table type
	 * @param id the numerical identifier for the row in the table
	 * @return a single Object which has the given id.
	 * @throws EmptyResultSetException
	 */
	public T getById(int id) throws EmptyResultSetException
	{
		return this.getByProperties(new SQLBuilder("id", "=", ""+id));
	}
	
	
	/** getByProperties
	 * return the first Object in the table that matches the given criteria
	 * See StaffMemberDMO for demonstration of implementation
	 * 
	 * @param p  the Map with the filter parameters in
	 * @return a single Object which matches the Property and Value
	 */
	public abstract T getByProperties(SQLBuilder query) throws EmptyResultSetException;
	
	/** put
	 * Saves a given object to the database. automatically chooses whether to UPDATE or INSERT into the table
	 * @param o the object of given type to insert or update on the database
	 */
	public abstract void put(T o);
	
	/** removeByProperty
	 * WARNING: multiple rows from a table may be removed
	 * remove row(s) from the table that match a given criteria
	 * @param p the Property to check
	 * @param v the Value of the property to match
	 */
	
	/** removeById
	 * remove a Row from the table matched by a given id
	 * @param id the id of the row to remove
	 */
	public abstract void removeById(int id);

}

/**
 * End of File: GPSISDataMapper.java
 * Location: gpsis/framework
 */
