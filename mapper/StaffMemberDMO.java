package mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import framework.GPSISDataMapper;
import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.StaffMember;

public class StaffMemberDMO extends GPSISDataMapper<StaffMember> 
{   
    
    private StaffMemberDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static StaffMemberDMO instance;
    
    public static StaffMemberDMO getInstance() 
    {
        if(instance == null)
        {
            instance = new StaffMemberDMO("StaffMember");
        }
        return instance;
    }
        
    @Override
    public Set<StaffMember> getAll() {
        // TODO Auto-generated method stub
        //Call super to get the RS.
        //
        return getAllByProperties( new SQLBuilder());


    }

    @Override
    public StaffMember getById(int id) {
        // TODO Auto-generated method stub
        return this.getByProperties(new SQLBuilder("id", "=", ""+id));
        
    }

    public StaffMember getByProperties(SQLBuilder query) {
        try {
            
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            
            if (res.next()) { // if found, create a the StaffMember object

                
                return new StaffMember( res.getInt("id"), 
                                        res.getString("username"), 
                                        res.getBytes("enc_password"),
                                        res.getString("first_name"), 
                                        res.getString("last_name"), 
                                        res.getBoolean("full_time"));
            } else {
                System.err.println("EMPTY SET");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Set<StaffMember> getAllByProperties(SQLBuilder query) {
          Set<StaffMember> staffMembers = new HashSet<>();
          try {
            
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
           
            
            
            while(res.next()) { // if found, create a the StaffMember object

                
                staffMembers.add(new StaffMember( res.getInt("id"), 
                                        res.getString("username"), 
                                        res.getBytes("enc_password"),
                                        res.getString("first_name"), 
                                        res.getString("last_name"), 
                                        res.getBoolean("full_time"))
                                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffMembers;//To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void removeById(int id) {
        try {
            // TODO Auto-generated method stub
            removeByProperty(new SQLBuilder("id","=",""+id));
        } catch (SQLException ex) {
            Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeByProperty(SQLBuilder query) throws SQLException 
    {
        GPSISDataMapper.removeByPropertyHelper(query, this.tableName);
        
    }
    
                                   
    public void updateByProperties(StaffMember Obj,SQLBuilder where) throws SQLException{
        //get A Patient, parse its properties. pass it in SQL Builder
        //Create a set statement here.
        SQLBuilder set;                         //Obj.firstname                  Obj.lastname
        set = new SQLBuilder("first_name","=",Obj.getLastName()).SET("last_name","=","Anderson");   
        
        //Just push in all the relevant values, dont worry about the rest.
        GPSISDataMapper.updateByPropertiesHelper(set,where, this.tableName);
        System.out.println("Check table");
//        GPSISDataMapper.update
    }
    @Override
    
    //Takes into account both inserts into a new object, and 
    //insert into an existing one.
    public void put(StaffMember o) 
    {
        SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("username","=",""+o.getUsername())
                .SET("first_name", "=", ""+o.getFirstName())
                .SET("last_name","=",""+o.getLastName())
                .SET("enc_password", "=",""+o.getEncryptedPassword())
                .SET("full_time", "=", "1");
        try {
            putHelper(sql, this.tableName);
        } catch (SQLException ex) {
            Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void removeByProperty(String p, String v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public static void main(String[] args) throws SQLException
    {
        StaffMemberDMO.connectToDatabase();
        StaffMemberDMO staffMembertbl;
        staffMembertbl = StaffMemberDMO.getInstance();
        
        Set<StaffMember> staffMembers = staffMembertbl.getAll();
        
        for(StaffMember staff : staffMembers)
        {
            System.out.println(staff.getLastName());
        }
     
        
        //Example usage updateByProperties.
        StaffMember staff = staffMembertbl.getById(1);
       
        staffMembertbl.updateByProperties(staff, new SQLBuilder("first_name", "=","Mr."));
       
        
        
        //Example usage removeByProperty, Should Remove skas
        staffMembertbl.removeByProperty(new SQLBuilder("username", "=","skas"));
        
        
        //Example usage, should add  Salman into database
   
        StaffMember aStaff = new StaffMember("sally", "password", "Salman", "K", true);
        staffMembertbl.put(aStaff);
     
    }



   

  

  
}
