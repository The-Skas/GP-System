/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapper;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import static framework.GPSISDataMapper.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import object.Patient;
import object.PermanentPatient;
import object.MedicalCondition;
/**
 *
 * @author skas
 */
public class PatientDMO extends GPSISDataMapper<Patient> 
{
    public final String[]columnNames = {"First Name", "Last Name","Sex", "Date of Birth", "NHS", "id"};
    private final String tblPermenant = "PermanentPatient";
    private final String tblMC        = "MedicalCondition";
    private final String tblPatientMC = "PatientMedCond";
    private final String tblFR = "FamilyRelation";
    private final String tblPatientFR = "PatientFamilyRelation";
    
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
    public PermanentPatient buildPermanentPatient(ResultSet res) throws SQLException, EmptyResultSetException
    {
        PermanentPatient permPatient;
        permPatient = new PermanentPatient( res.getInt("Patient.id"), 
                                    res.getString("Patient.first_name"), 
                                    res.getString("Patient.last_name"),
                                    res.getString("sex").charAt(0),
                                    res.getString("postcode"), 
                                    res.getString("address"),
                                    res.getString("phone"),
                                    res.getDate("dob"),
                                    res.getInt("father_id"),
                                    res.getInt("mother_id"),
                                    res.getInt("doctor_id"),
                                    res.getString("NHS_number"));
        return permPatient;
        
    }
    public Patient buildPatient(ResultSet res) throws SQLException
    {
        Patient patient;
        patient = new Patient(  res.getInt("Patient.id"), 
                                res.getString("Patient.first_name"), 
                                res.getString("Patient.last_name"),
                                res.getString("sex").charAt(0),
                                res.getString("postcode"), 
                                res.getString("address"),
                                res.getString("phone"),
                                res.getDate("dob"),
                                res.getInt("father_id"),
                                res.getInt("mother_id"));
     
        return patient;
    }
    
   
    //**FAMILY **//
    
    public List<Patient> getPatientSiblings(Patient p)
    {
        
        SQLBuilder sql = new SQLBuilder("father_id","=",""+p.getFatherId())
                                    .AND("father_id","!=", "NULL")
                                    .AND("father_id","!=", "0")
                                    .AND("father_id", "!=", ""+p.getId())
                                    .AND("id", "!=", ""+p.getId())
                                    .OR("mother_id","=",""+p.getMotherId())
                                    .AND("mother_id","!=", "NULL")
                                    .AND("mother_id","!=", "0")
                                    .AND("mother_id", "!=", ""+p.getId())
                                    .AND("id", "!=", ""+p.getId());
        try {
            return this.getAllByProperties(sql);
        } catch (EmptyResultSetException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<Patient>();
        
    }
    
    public List<Patient> getPatientChildren(Patient p)
    {
        SQLBuilder sql = new SQLBuilder("father_id","=",""+p.getId())
                                    .OR("mother_id","=",""+p.getId());
        try {
            return this.getAllByProperties(sql);
        } catch (EmptyResultSetException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<Patient>();

    }
    
    /*** MEDICAL CONDITIONS ***/
    
    //Add A list of Medical Conditions if Found to the specified patient
    public ArrayList<MedicalCondition> getAllMedicalConditions()
    {
        ArrayList<MedicalCondition> medicalConditions = new ArrayList<>();
        
        ResultSet res = null;
        try {
            res = GPSISDataMapper.getResultSet
                    (
                            new SQLBuilder(), this.tblMC
                    );
        } catch (SQLException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while(res.next())
            {
                medicalConditions.add(
                        new MedicalCondition(
                                res.getInt("id"),
                                res.getString("name")
                        ));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return medicalConditions;
    }
    public void addPatientMedicalConditions(ArrayList<MedicalCondition>medicalConditions, Patient p) 
    {
        //delete all before adding new, that way we can update/add/remove
        deletePatientMedicalConditions(p);
        int id;
        for(MedicalCondition mC : medicalConditions)
        {
            try {
                ResultSet res =GPSISDataMapper.getResultSet
                        (
                                new SQLBuilder("name","=",""+mC.getName()), this.tblMC
                        );
                if(res.next())
                {
                    id = res.getInt("id");
                    
                    SQLBuilder insertSql = new SQLBuilder("patient_id","=",""+p.getId())
                            .SET("mc_id","=",""+id);
                    
                    GPSISDataMapper.putHelper(insertSql, this.tblPatientMC, null);
                }
                else
                {
                    System.out.println("ERROR: Medical Condition of name "+ mC.getName() +" Not found");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deletePatientMedicalConditions(Patient p)
    {   
        
        //remove all PatientMedicalConditions
        try {
            GPSISDataMapper.removeByPropertyHelper
                (
                        new SQLBuilder("patient_id","=",""+p.getId()), this.tblPatientMC   
                );
        } catch (SQLException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<MedicalCondition> getPatientMedicalConditions(Patient p) throws SQLException
    {
        ArrayList<MedicalCondition> medicalConditions = new ArrayList<>();
        
        ResultSet res = GPSISDataMapper.getResultSet
            ( 
                    new SQLBuilder("patient_id","=",""+p.getId()), this.tblPatientMC
            );
        
        while(res.next())
        {
            ResultSet resMC = GPSISDataMapper.getResultSet
                (
                    new SQLBuilder("id","=",""+res.getInt("mc_id")),
                    this.tblMC
                );
            if(resMC.next())
            {
                medicalConditions.add(
                        new MedicalCondition(
                                resMC.getInt("id"),
                                resMC.getString("name")
                        ));
            }
        }
        
        
        return medicalConditions;
        
    }
    
    //** END MEDICAL CONDITIONS **//
    
    public List<Patient> getAllPermanentPatientsByDoctorId(int id) throws EmptyResultSetException
    {
        
        return this.getAllByProperties(new SQLBuilder("doctor_id","=",""+id));

	        
    }
    
    public PermanentPatient getPermanentPatientById(int id)
    {
        //this should return the permanent Patient.
        //though a permanent patient is constructed by a normal Patient.
        try 
        {
            Patient patient = this.getById(id);


            if(patient instanceof PermanentPatient)
            {
                return (PermanentPatient)patient;
            }
        }catch(Exception e)
        {
           e.printStackTrace();
        }
        return null;
    }
    public Patient getPermanentPatientOrPatient(Patient patient) 
    {   
        try {
    
        ResultSet rs = 
                GPSISDataMapper.getResultSet
                (
                        new SQLBuilder("patient_id","=",""+patient.getId()),this.tblPermenant
                );
        //Returns a PermanentPatient if found
        if(rs.next())
        {
            return  PermanentPatient.
                    getByPatient
                    (                   
                        patient, 
                        StaffMemberDMO.getInstance().getById(rs.getInt("doctor_id")),
                        rs.getString("NHS_number")
                    );
        }
        }catch(Exception e) {
            e.printStackTrace();
        }
        //Otherwise returns the Patient passed in;
        return patient;
        
    }
    
    public void deletePermanentPatientById(int id)
    {
        SQLBuilder sql = new SQLBuilder("patient_id","=",""+id);
        try {
            GPSISDataMapper.removeByPropertyHelper(sql, this.tblPermenant);
        } catch (SQLException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Patient getPatientByName(String name)
    {
        return this.getByProperties(new SQLBuilder("first_name","=",name)
                                               .OR("last_name", "=",name));
                                                    
    }
  
    @Override
    public Patient getByProperties(SQLBuilder query) {
    try {
             String joinTbl = this.tableName 
                    + " Left Outer Join PermanentPatient on Patient.id = PermanentPatient.patient_id";
            ResultSet res = GPSISDataMapper.getResultSet(query, joinTbl);
            
            if (res.next()) { // if found, create a the StaffMember object
                res.getInt("NHS_number");
            
                return (res.wasNull() ? this.buildPatient(res) : this.buildPermanentPatient(res));
                
                
                ///patient = PatientDMO.getPermenantPatientById(int id);
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmptyResultSetException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    

   
    public List<Patient> getAllByProperties(SQLBuilder query) throws EmptyResultSetException {
        List<Patient> patients = new ArrayList<>();
        
        try {
            String joinTbl = this.tableName 
                    + " Left Outer Join PermanentPatient on Patient.id = PermanentPatient.patient_id";
            ResultSet res = GPSISDataMapper.getResultSet(query, joinTbl);
            while(res.next()) 
            { // if found, create a the patient object
                //Before I add it, surely I would want a refrence to set
                //its NHS Number for the permenant Patients.
                res.getInt("NHS_number");
               
                patients.add( (res.wasNull() ? this.buildPatient(res) : this.buildPermanentPatient(res)));
                
                        
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
        
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
    public void putPermanentPatient(PermanentPatient pP)
    {
        this.put(pP);
        SQLBuilder sql = new SQLBuilder("patient_id","=",""+pP.getId())
                        .SET("doctor_id", "=", ""+pP.getDoctor().getId())
                        .SET("NHS_number", "=", ""+pP.getNHSNumber());
        try {
            putHelper(sql, this.tblPermenant, pP);
        } catch (SQLException ex) {
            Logger.getLogger(PatientDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Override
    public void put(Patient o) {
        
       String dob = new SimpleDateFormat("yyyy-MM-dd").format(o.getDob());

       SQLBuilder sql = new SQLBuilder("id","=",""+o.getId())
                .SET("first_name", "=", ""+o.getFirstName())
                .SET("last_name","=",""+o.getLastName())
                .SET("sex", "=", ""+o.getSex())
                .SET("dob", "=",""+dob)
                .SET("address", "=", ""+o.getAddress())
                .SET("postcode", "=", ""+o.getPostCode())
                .SET("phone", "=", ""+ o.getPhone())
                .SET("father_id", "=",""+o.getFatherId())
                .SET("mother_id", "=", ""+o.getMotherId());
        try {
            putHelper(sql, this.tableName, o);
        } catch (SQLException ex) {
            Logger.getLogger(StaffMemberDMO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
          
}
