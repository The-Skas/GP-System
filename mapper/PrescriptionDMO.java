/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapper;
import java.util.Date;
import framework.GPSISDataMapper;
import java.sql.ResultSet;
import java.util.Set;
import object.Prescription;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.Medicine;
import object.Patient;
import object.StaffMember;
/**
 *
 * @author oa305
 */
public class PrescriptionDMO extends GPSISDataMapper<Prescription> {
    
    private final String tblMedPresc = "PrescriptionMedicine";
    
    
    private PrescriptionDMO(String tableName)
    {
        this.tableName = tableName;
    }
    
    private static PrescriptionDMO instance;
    
    public static PrescriptionDMO getInstance() 
    {
        if(instance == null)
        {
            instance = new PrescriptionDMO("Prescription");
        }
        return instance;
    }
    
    
    
    @Override
    public List<Prescription> getAll() {
        
        return getAllByProperties(new SQLBuilder());
    }

    @Override
    public Prescription getById(int id) {
          try {
            SQLBuilder query = new SQLBuilder("id","=",""+id);
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            
            if (res.next()) { // if found, create 
               return new Prescription(res.getInt("id"),
                                 res.getInt("p_id"),
                                 res.getInt("d_id"), 
                                 res.getDate("startDate"),
                                 res.getDate("end_date"),
                                new ArrayList<Medicine>(),
                                res.getInt("frequency"),
                                res.getString("pay_or_free"),
                                res.getString("medical_condition"));
                
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    
        
    }
        
    public void removeByProperty(SQLBuilder query) throws SQLException 
    {
        GPSISDataMapper.removeByPropertyHelper(query, this.tableName);        
    }
    
    @Override
    public void removeById(int id) 
    {
       try 
        {
            removeByProperty(new SQLBuilder("id","=",""+id));
        } 
        catch (SQLException e) 
        {
        	System.err.println(e.getMessage());
        }
   }

   public List<Prescription> getPrescriptionsByPatient(Patient p)
   {
       SQLBuilder sql = new SQLBuilder("p_id", "=",""+p.getId());
       return getAllByProperties(sql);
   }
    public List<Prescription> getPrescriptionsByDoctor(StaffMember sM)
    {
        SQLBuilder sql = new SQLBuilder("d_id", "=",""+sM.getId());
        return getAllByProperties(sql);
    }
    @Override
    public void put(Prescription o) {
        SQLBuilder sql;
        String S = new SimpleDateFormat("yyyy-MM-dd").format(o.getendDate());
        String A = new SimpleDateFormat("yyyy-MM-dd").format(o.getStartDate());

        
        if(o.getId()!=0)
        {
            sql = new SQLBuilder("id","=",""+o.getId())
              .SET("p_id","=",""+ o.getpatientID())
              .SET("d_id","=",""+ o.getdoctorID())
              .SET("startDate", "=",""+A)
              .SET("end_date","=","" + S)
              .SET("frequency","=",""+ o.getfrequency())
              .SET("pay_or_free", "=", ""+o.getPayOrFree())
              .SET("medical_condition", "=", ""+o.getMedicalCondition());
        }
        else
        {
            sql = new SQLBuilder("p_id","=",""+ o.getpatientID())
              .SET("d_id","=",""+ o.getdoctorID())
              .SET("startDate", "=",""+A)
              .SET("end_date","=","" + S)
              .SET("frequency","=",""+ o.getfrequency())
              .SET("pay_or_free", "=", ""+o.getPayOrFree())
              .SET("medical_condition", "=", ""+o.getMedicalCondition());
        }
        
  //                                     .SET("list_of_medicine","=","" + new ArrayList())
        
        try{
                putHelper(sql, this.tableName, o);
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
                
    }
    
        public void addMedicinestoPresc(Prescription o)
    {
        List<Medicine> meds = o.getlistofMedicine();
        //Delete all medicines associated with prescription
        SQLBuilder sqlDelPrescMeds = new SQLBuilder("Presc_id", "=", ""+o.getId());
        try {
            GPSISDataMapper.removeByPropertyHelper(sqlDelPrescMeds, this.tblMedPresc);
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //re-add all medicines associated with prescription.
        for(Medicine med : meds)
        {
            SQLBuilder sqlJoin = new SQLBuilder("Presc_id","=",""+o.getId())
                                           .SET("Med_id", "=", ""+med.getID());
            try {
                putHelper(sqlJoin, this.tblMedPresc, o);
            } catch (SQLException ex) {
                Logger.getLogger(PrescriptionDMO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    @Override
   public Prescription getByProperties(SQLBuilder query) {
       try {
            //SELECT * FROM Prescription + QUERY;
                                            //where id = 1 or blah
           
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            
            if (res.next()) { // if found, create a the StaffMember object
               return new Prescription(res.getInt("id"),
                                 res.getInt("p_id"),
                                 res.getInt("d_id"), 
                                 res.getDate("startDate"),
                                 res.getDate("end_date"),
                                new ArrayList<Medicine>(),
                                res.getInt("frequency"),
                                res.getString("pay_or_free"),
                                res.getString("medical_condition"));
                
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Prescription> getAllByProperties(SQLBuilder query) {
        List<Prescription> prescriptions;
        prescriptions = new ArrayList<>();
        try {
            //SELECT * FROM Prescription + QUERY;
                                            //where id = 1 or blah
           
            ResultSet res = GPSISDataMapper.getResultSet(query, this.tableName);
            
            
            while(res.next()) { // if found, create a the StaffMember object
               prescriptions.add(new Prescription(res.getInt("id"),
                                 res.getInt("p_id"),
                                 res.getInt("d_id"), 
                                 res.getDate("startDate"),
                                 res.getDate("end_date"),
                                new ArrayList<Medicine>(),
                                res.getInt("frequency"),
                                res.getString("pay_or_free"),
                                res.getString("medical_condition")));
                
                
            } 

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }
    
    public List<Medicine> getMedicinesByPrescriptionById(int id)
    {
        ArrayList<Medicine> listOfMedicine = new ArrayList<>();
        try {
            ResultSet rs = GPSISDataMapper.getResultSet
                    (
                        new SQLBuilder("Presc_id","=",""+id),this.tblMedPresc
                    );
            MedicineDMO medTbl = MedicineDMO.getInstance();
            while(rs.next())
            {
                listOfMedicine.add(medTbl.getById(rs.getInt("Med_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return listOfMedicine;
    }
    
      
    
    public static void main(String [] args)
    {
        GPSISDataMapper.connectToDatabase();
        PrescriptionDMO presTbl = PrescriptionDMO.getInstance();
        Prescription presc = presTbl.getByProperties(new SQLBuilder("id","=","1")
                                .OR("p_id","=","1"));
      
      
    }

}
