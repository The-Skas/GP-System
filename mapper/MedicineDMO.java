/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapper;

import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import static framework.GPSISDataMapper.putHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import object.Medicine;



/**
 *
 * @author oa305
 */
public class MedicineDMO extends GPSISDataMapper<Medicine>{

    private MedicineDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static MedicineDMO instance;
    
    public static MedicineDMO getInstance() 
    {
        if(instance == null)
        {
            instance = new MedicineDMO("medicine");
        }
        return instance;  
    }
    
    
    @Override
    public Set<Medicine> getAll() {
        return getAllByProperties(new SQLBuilder());
    }
  
    @Override
    public Medicine getById(int id) {
          try {
            SQLBuilder query = new SQLBuilder("id","=",""+id);
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            
            if (res.next()) { // if found, create the object
               return new Medicine(res.getInt("id"),
                                 res.getString("name"),
                                 res.getString("description"),                                  
                                res.getInt("relevant_amount"));
                
                
            } else {
                System.err.println("EMPTY SET");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;          
    }

    @Override
    public Medicine getByProperties(SQLBuilder query) {
       try {
            //SELECT * FROM Medicine + QUERY;
                                            //where id = 1 or blah
           
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            
            if (res.next()) { // if found, create a the StaffMember object
               return new Medicine(res.getInt("id"),
                                 res.getString("name"),
                                 res.getString("description"), 
                                res.getInt("relevant_amount"));
                
                
            } else {
                System.err.println("EMPTY SET");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;        
    }

    @Override
    public Set<Medicine> getAllByProperties(SQLBuilder query) {
        Set<Medicine> medicines;
        medicines = new HashSet<>();
        try {
            //SELECT * FROM medicines + QUERY;
                                            //where id = 1 or blah
           
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            
            while(res.next()) { // if found, create a the StaffMember object
               medicines.add(new Medicine(res.getInt("id"),
                                 res.getString("name"),
                                 res.getString("description"), 
                                res.getInt("relevant_amount")));
                
                
            } 

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    @Override
    public void removeById(int id) {
        try 
        {
            removeByProperty(new SQLBuilder("id","=",""+id));
        } 
        catch (SQLException e) 
        {
        	System.err.println(e.getMessage());
        }
    }

    @Override
    public void put(Medicine o) {
        SQLBuilder sql;
        

        
        if(o.getID()!=0)
        {
            sql = new SQLBuilder("id","=",""+o.getID())
              .SET("name","=",""+ o.getName())
              .SET("description","=",""+ o.getDescription())
              .SET("relevant_amount","=","" + o.getRelevant_amount());
        }
        else
        {
            sql = new SQLBuilder("id","=",""+ o.getID())
              .SET("name","=",""+ o.getName())
              .SET("description","=",""+ o.getDescription())
              .SET("relevant_amount","=","" + o.getRelevant_amount());
        }
        
        try{
                putHelper(sql, this.tableName, o);
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
      
     private void removeByProperty(SQLBuilder query) throws SQLException {
                GPSISDataMapper.removeByPropertyHelper(query, this.tableName);  
    }   

  
    
}
