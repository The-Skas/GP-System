/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapper;
import framework.GPSISDataMapper;
import static framework.GPSISDataMapper.putHelper;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.Patient;
import object.StaffMember;
/**
 *
 * @author skas
 */
public class PatientDMO extends GPSISDataMapper<Patient> 
{
    private PatientDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static PatientDMO instance;
    
    public static PatientDMO getInstance() 
    {
        if(instance == null)
        {
            instance = new PatientDMO("Patient");
        }
        return instance;
    }
    
    public Patient getPermenantPatientById(int id) throws SQLException
    {   
        Patient patient = null;
        
        ResultSet rs = 
                GPSISDataMapper.getResultSet(
                        new SQLBuilder("id","=",""+id), this.tableName
                );
        
        return patient;
    }
    
    @Override
    public Set<Patient> getAll() {
        return getAllByProperties( new SQLBuilder());
    }

    @Override
    public Patient getById(int id) {
        return this.getByProperties(new SQLBuilder("id", "=", ""+id));
    }

    @Override
    public Patient getByProperties(SQLBuilder query) {
    try {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            Patient patient;
            if (res.next()) { // if found, create a the StaffMember object

                //I can make one query, and have a boolean that checks.
                //if is regestered or not. If he is. Then push to temp patient
                //table. If he isnt still push to patient table. Only diff is
                //that if he is, we need an additional query of pushing in the 
                //doctor as well as the patient into a different table. It would
                //be a simple if. Hacky. Hm. From a design point. I still, need
                //patient to be a super class. As the way the database has
                //created it is to set that.
                patient = new Patient( res.getInt("id"), 
                                    res.getString("first_name"), 
                                    res.getString("last_name"),
                                    res.getString("sex").charAt(0),
                                    res.getString("postcode"), 
                                    res.getString("address"),
                                    res.getString("phone"),
                                    res.getDate("dob"));
                
                
//                patient = PatientDMO.getPermenantPatientById(int id);
                
            } else {
                System.err.println("EMPTY SET");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }    

    @Override
    public Set<Patient> getAllByProperties(SQLBuilder query) {
        Set<Patient> patients = new HashSet<>();
        
        try {
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            while(res.next()) 
            { // if found, create a the patient object

                //Before I add it, surely I would want a refrence to set
                //its NHS Number for the permenant Patients.
                patients.add(new Patient( res.getInt("id"), 
                                    res.getString("first_name"), 
                                    res.getString("last_name"),
                                    res.getString("sex").charAt(0),
                                    res.getString("postcode"), 
                                    res.getString("address"),
                                    res.getString("phone"),
                                    res.getDate("dob")));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
            
        return patients;
        
    }

    @Override
    public void removeById(int id) {
        try {
            removeByProperty(new SQLBuilder("id","=",""+id));
        } catch (SQLException ex) {
            Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeByProperty(SQLBuilder query) throws SQLException 
    {
        GPSISDataMapper.removeByPropertyHelper(query, this.tableName);
        
    }
    
    @Override
    public void put(Patient o) {
       SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("first_name", "=", ""+o.getFirstName())
                .SET("last_name","=",""+o.getLastName())
                .SET("sex", "=", ""+o.getSex())
                .SET("dob", "=",""+o.getDob())
                .SET("address", "=", ""+o.getAddress())
                .SET("postcode", "=", ""+o.getPostCode())
                .SET("phone", "=", ""+ o.getPhone());
        try {
            putHelper(sql, this.tableName);
        } catch (SQLException ex) {
            Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String [] args)
    {
        PatientDMO tbl = PatientDMO.getInstance();
        Patient patient;
        //Should return Patient whos ID is one.
//        Patient patient = tbl.getById(1);

        patient = new Patient(1, "cool", "beans", 'm', "n123fx", "London", "0737231313", new Date(1,3,4));
    }
    

    
}
