/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * The SQLBuilder class serves to abstract the unnecessary work in creating a 
 * SQL Statement. 
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
 *                   .Q("OR", "username", "=","salman")
 *                   .Q("AND", "age",">=","18");
 * 
 * Look at the main function for examples of uses.
 * 
 * @author skas
 * @version 1.0
 */
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
     * Constructuter:
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
     * The 'Q' function is used to construct logical queries, taking into account
     * logical operators.
     * 
     *      ie: new SQLBuilder("username","=","vj")
     *                      .Q("AND","age",">=", "18");
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
        System.out.println(pS);
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
}
