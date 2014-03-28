package mapper;
import module.Referral.*;
import framework.GPSISDataMapper;
import object.ReferralObject;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Inherits GPSISDataMapper<ReferralObject>'s methods
public class ReferralDMO extends GPSISDataMapper<ReferralObject>{   
	// stores the only instance of this DataMapper
	private static ReferralDMO instance;
	
	//ReferralDMO Constructor
    private ReferralDMO(String tableName){
        this.tableName = tableName;
    }    
    
   //getInstance
    public static ReferralDMO getInstance(){
        if(instance == null)
            instance = new ReferralDMO("Referral");
        return instance;
    }  
   
   //getByProperties
    //Returns the first Referral object matching the criteria
    public ReferralObject getByProperties(SQLBuilder query){
        try 
        {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
            if (res.next()) // if found, create a the Referral object 
            {
            	return this.buildReferral(res);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
        
    //getAllByProperties
    //Returns a Set of Referrals that match the given criteria
    public List<ReferralObject> getAllByProperties(SQLBuilder query){
          List<ReferralObject> Referral = new ArrayList<>();
          
          try 
          {            
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);            
            while(res.next()) // While there's a Referral, create a the Referral object and add it to a Set
            {
                Referral.add(this.buildReferral(res));
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return Referral;
    }
    
    //builds a Staff Member object from the given Result Set. Used as a helper method in retrieving Referrals from the Database
    //buildReferral
    private ReferralObject buildReferral(ResultSet res) throws SQLException
    {
    	if (res != null) // if found, create a the Referral object 
        {
  
    				return	new ReferralObject(
    								res.getInt("id"),
    								res.getDate("Referral.date_made"),
    								res.getInt("doctors_id"),
    								res.getInt("consultant_id"),
    								res.getInt("patient_id"),
    								res.getInt("invoice_paid"));
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "EMPTY SET - No Referral Found matching the criteria");
        }
		return null;
    }
    
    //put
    //Put a given Referral object onto the Database. Used for INSERT and UPDATE
    public void put(ReferralObject o){
    //To add current date as string 
     String s = new SimpleDateFormat("yyyy-MM-dd").format(o.getDate());
  
       SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("date_made","=",""+s)
                .SET("doctors_id", "=", ""+o.getDocId())
                //So they have the same id as the referral itself
       			.SET("invoice_paid", "=", ""+o.isInvPaid())
       			.SET("consultant_id","=",""+o.getConID())
                .SET("patient_id", "=",""+o.getPatID());
        try 
        {
            putHelper(sql, this.tableName, o);
        } 
        catch (SQLException e) 
        {
        	JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
