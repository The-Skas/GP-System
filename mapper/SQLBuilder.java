package mapper;

/**
 * The SQLBuilder class serves to abstract the unnecessary work in creating a 
 * PREPARED SQL Statement using the JDBC library.
 * 
 * At a glance, the class may seem as an overhead for simple querying.
 * Though this is specifically made to abstract out PreparedStatement
 * 
 * To use the class, look into an example used in the LoginModule.
 * Where the StaffMemberDMO getByAttributes takes an SQLBuilder object as a 
 * parameter.
 * This class uses an approach similar to the Builder Pattern. 
 * 
 * 
 * This class only creates the logical block.
 * (Everything after the 'WHERE' SQL Statement) 
 *                     
 *                      //Column  //Comparison //Value
 *       new SQLBuilder("username","=","vj")
 *              //Logic //Column  //Comparison //Value
 *                   .OR("username", "=","salman")
 *                   .AND("AND", "age",">=","18");
 * 
 * The equivilant query would be:
 *                  "Select * From table WHERE username = vj OR 
 *                              username = salman AND age >= 18;
 * 
 * The table is irrelvant for this class. This classes job is to merely 
 * replace every occurance of a value (salman, vj, 18) to question marks.
 * 
 * The equivilant PreparedStatement Query would be:
 *                  "Select * From table WHERE username = ? OR 
 *                              username = ? AND age >= ?;
 * 
 * (This PreparedStatement prevents SQLInjections!)
 *                  
 * 
 * @author Salman Khalifa
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class SQLBuilder {
    public static final byte LOGIC  = 0;
    public static final byte COLUMN = 1;
    public static final byte COMPR  = 2;
    public static final byte VALUE  = 3;
    
    //Array size(Logic,Column,Compr,Value);
    public static final byte SIZE =   4;
    
    //This stores the queries in an ArrayList.
    //The String array size is 4;
    public ArrayList<String []> qBlocks;
    
    /**
     * Constructor:
     * With three parameters, logic is ignored as the first statement
     * in a SQL Query has no Logic
     * 
     *  ie    "username = vj ..."  
     *         
     * 
     */
    public SQLBuilder()
    {
        //Hacky but works for abstraction. This sets a default query. 
      this("1","=","1");
    }
    public SQLBuilder(String column, String compr, String value)
    {
       qBlocks = new ArrayList<String[]>();
       String [] block = new String[SIZE];
       
       block[LOGIC]  = "";
       block[COLUMN] = column;
       block[COMPR]  = compr;
       block[VALUE]  = value;
       
       qBlocks.add(block);
    }
     /**
     * The 'AND' function is used to construct AND queries, taking into account
     * logical operators.
     * 
     *      ie: new SQLBuilder("username","=","vj")
     *                      .AND("age",">=", "18")
     *                      .AND("sex", "=", "m");
     * 
     * @param logic
     * @param column
     * @param compr
     * @param value
     * @return SQLBuilder
     */
    
    public SQLBuilder AND(String column, String compr, String value)
    {
        String [] block = new String[SIZE];
        
        block[LOGIC]  = "AND";
        block[COLUMN] = column;
        block[COMPR]  = compr;
        block[VALUE]  = value;
        
        qBlocks.add(block);
        return this;
    }
    
    /**
     * The 'OR' function is used to construct OR queries, taking into account
     * logical operators.
     * 
     *      ie: new SQLBuilder("username","=","vj")
     *                      .OR("age",">=", "18")
     *                      .OR("sex", "=", "m");
     * 
     * @param logic
     * @param column
     * @param compr
     * @param value
     * @return SQLBuilder
     */
    public SQLBuilder OR(String column, String compr, String value)
    {
        String [] block = new String[SIZE];
        
        block[LOGIC]  = "OR";
        block[COLUMN] = column;
        block[COMPR]  = compr;
        block[VALUE]  = value;
        
        qBlocks.add(block);
        return this;
    } 
    
    /**
     * The 'SET' function is used to construct Update/Insert queries. It can
     * only be assigned values, making the comparison operator redundant.
     * This is kept to make it easier on the user to remember the syntax for
     * using all three functions (OR,AND,SET)
     * 
     * 
     * 
     *      ie: new SQLBuilder("username","=","skas")
     *                      .SET("age","=", "24")
     *                      .SET("sex", "=", "m");
     * 
     * @param logic
     * @param column
     * @param compr
     * @param value
     * @return SQLBuilder
     */
    
    public SQLBuilder SET(String column, String compr, String value)
    {
        String [] block = new String[SIZE];
        
        block[LOGIC]  = ",";
        block[COLUMN] = column;
        block[COMPR]  = compr;
        block[VALUE]  = value;
        
        qBlocks.add(block);
        return this;
    }
    
    
    //Executes A statement, returning a result set. Check JDBC Documentation for
    //what a result Set is :)
    public ResultSet executeStatement(Connection dbConn, String preparedQuery) throws SQLException
    {
       PreparedStatement pS = dbConn.prepareStatement(preparedQuery);
       int i = 1;
        for(String[] qBlock : this.qBlocks)
        {
            pS.setString(i, qBlock[VALUE]);
            i++;
        }
        return pS.executeQuery();
        
    } 
    
    
    
    public void prepare(PreparedStatement pS) throws SQLException
    {
        int i = 1;
        for(String[] qBlock : this.qBlocks)
        {
            pS.setString(i, qBlock[VALUE]);
            i++;
        }
    }
    //Combines both functions into one. ONLY WORKS ON SELECT
    public ResultSet prepareAndExecute(Connection dbConn, String query) throws SQLException
    {
        query = PreparedStatementWhere(query);
        return executeStatement(dbConn, query);
    }
    
    //Formats Query, Taking into account the passed, SELECT * from table
    public String PreparedStatementWhere(String query)
    {
        for(String[] qBlock : this.qBlocks)
        {
            query +=" "+qBlock[LOGIC]+" "+qBlock[COLUMN]+" "+qBlock[COMPR]+" "+"?";
        }
        return query;
    }
    
    public String toPreparedStatement(String query)
    {
      for(String[] qBlock : this.qBlocks)
        {
            query +=" "+qBlock[LOGIC]+" "+qBlock[COLUMN]+" "+qBlock[COMPR]+" "+"?";
        }  
      return query;
    }
}
