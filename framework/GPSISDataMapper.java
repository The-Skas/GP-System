/** GPSISFramework
 * Superclass for every Data Mapper in GPSIS. Data Mappers will be children of this node.
 * NOTE: Every class that extends this model SHOULD be appended with DMO. e.g PatientDMO, CareProgrammeDMO etc.
 * 
 * @author Vijendra Patel (vp302)
 * @author Salman Khalifa
 */
package framework;

import static mapper.SQLBuilder.VALUE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mapper.SQLBuilder;
import exception.EmptyResultSetException;

public abstract class GPSISDataMapper<T> extends GPSISFramework {
	protected String tableName;
	
	// store database connection. Only child elements can use this.
	protected static Connection dbConnection; 

	/** connectToDatabase 
	 * Initiate a connection to the database and store that
	 * connection so all subclasses can use it. 
	 * QMUL DBProjects Server uses : 5.0.95 
	 * A Line of Code Server uses : 5.6.16
	 */
	public static boolean connectToDatabase() {
		// String username = "SEGJ";
		// String password = "sv1e022g";
		// String dbName = "SEGJ";
		// String url = "jdbc:mysql://localhost:3307/" + dbName + "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

		String username = "grpj";
		String password = "wv7PBUZBMXH88NP6";
		String dbName = "GPSIS_GROUPJ";
		String url = "jdbc:mysql://alineofcode.co.uk:3306/"
				+ dbName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection(url, username, password);
			return true;
		} catch (ClassNotFoundException e) {
			System.err.println("MYSQL's JDBC Not in Class Path");
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	/** getAll 
	 * return a set containing all of the elements in the table for the
	 * child element
	 * 
	 * @return a Set of elements from the table in their Object form.
	 * @throws EmptyResultSetException
	 */
	public List<T> getAll() throws EmptyResultSetException {
		return this.getAllByProperties(new SQLBuilder());
	}

	/** getAllByProperty 
	 * return a Set containing all of the Rows that match the
	 * given criteria
	 * 
	 * @param p
	 *            the Property to check
	 * @param v
	 *            the Value of the Property to match
	 * @return a Set containing all of the Objects that match the Property and
	 *         Value
	 */
	public abstract List<T> getAllByProperties(SQLBuilder query)
			throws EmptyResultSetException;

	/** getById 
	 * return a single Object of this table type
	 * 
	 * @param id
	 *            the numerical identifier for the row in the table
	 * @return a single Object which has the given id.
	 * @throws EmptyResultSetException
	 */
	public T getById(int id) throws EmptyResultSetException {
		return this.getByProperties(new SQLBuilder(this.tableName + ".id", "=", "" + id));
	}

	/** getByProperties 
	 * return the first Object in the table that matches the
	 * given criteria See StaffMemberDMO for demonstration of implementation
	 * 
	 * @param p
	 *            the Map with the filter parameters in
	 * @return a single Object which matches the Property and Value
	 */
	public abstract T getByProperties(SQLBuilder query)
			throws EmptyResultSetException;

	/** put 
	 * Saves a given object to the database. automatically chooses whether
	 * to UPDATE or INSERT into the table
	 * 
	 * @param o
	 *            the object of given type to insert or update on the database
	 */
	public abstract void put(T o);

	/** removeByProperty 
	 * WARNING: Removes all StaffMembers from the database that
	 * match the given criteria
	 * 
	 * @param query
	 *            the criteria to match
	 * @throws SQLException
	 */
	public void removeByProperty(SQLBuilder query) throws SQLException {
		removeByPropertyHelper(query, this.tableName);
	}

	/** removeById 
	 * remove a Row from the table matched by a given id
	 * 
	 * @param id
	 *            the id of the row to remove
	 */
	public void removeById(int id) {
		try {
			removeByProperty(new SQLBuilder("id", "=", "" + id));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/** putHelper
	 * 
	 * @param setQ
	 * @param tableName
	 * @param object
	 * @throws SQLException
	 */
	protected static void putHelper(SQLBuilder setQ, String tableName,
			GPSISObject object) throws SQLException {
		String query = "INSERT INTO " + tableName + " SET ";
		// set query
		query = setQ.toPreparedStatement(query) + " ON DUPLICATE KEY UPDATE "
				+ setQ.toPreparedStatement("");

		PreparedStatement pS = dbConnection.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		for (String[] qBlock : setQ.qBlocks) {
			pS.setString(i, qBlock[VALUE]);
			i++;
		}
		for (String[] qBlock : setQ.qBlocks) {
			pS.setString(i, qBlock[VALUE]);
			i++;
		}
		pS.executeUpdate();

		ResultSet keyRs = pS.getGeneratedKeys();
		if (keyRs.next()) {
			object.setId(keyRs.getInt(1));
		}
	}

	/** removeByPropertyHelper
	 * 
	 * @param sqlQ
	 * @param tableName
	 * @throws SQLException
	 */
	protected static void removeByPropertyHelper(SQLBuilder sqlQ,
			String tableName) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE ";

		sql = sqlQ.toPreparedStatement(sql);

		PreparedStatement pS = dbConnection.prepareStatement(sql);
		sqlQ.prepare(pS);
		pS.executeUpdate();

	}

	/** updateByPropertiesHelper
	 * 
	 * @param set
	 * @param where
	 * @param tableName
	 * @throws SQLException
	 */
	protected static void updateByPropertiesHelper(SQLBuilder set,
			SQLBuilder where, String tableName) throws SQLException {
		String query = "UPDATE " + tableName + " SET ";
		query = set.toPreparedStatement(query);
		query += " WHERE ";
		query = where.toPreparedStatement(query);

		PreparedStatement pS = dbConnection.prepareStatement(query);

		int i = 1;
		for (String[] block : set.qBlocks) {
			pS.setString(i, block[3]);
			i++;
		}

		for (String[] block : where.qBlocks) {
			pS.setString(i, block[3]);
			i++;
		}
		pS.executeUpdate();
	}

	/** getResultSet 
	 * returns a ResultSet Object by parsing the SQLBuilder, and
	 * executing it by using prepared statements. This is to avoid SQL
	 * Injections.
	 * 
	 * @param sqlQ
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	protected static ResultSet getResultSet(SQLBuilder sqlQ, String tableName)
			throws SQLException {
		String sql = "SELECT * FROM " + tableName + " WHERE ";
		return sqlQ.prepareAndExecute(dbConnection, sql);
	}
}

/**
 * End of File: GPSISDataMapper.java 
 * Location: framework
 */
