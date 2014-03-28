package object;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import exception.EmptyResultSetException;
import framework.GPSISObject;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapper.PrescriptionDMO;
import mapper.SQLBuilder;
import mapper.StaffMemberDMO;
/**
 *
 * @author oa305
 */
public class Prescription extends GPSISObject
{
    private int patientID;
    private int doctorID;
    private Date startDate;
    private Date endDate; //Not sure to use Date or Calender//
    private List<Medicine> listofMedicine;
    private int frequency; //How many medicines to take a day///
    private Patient patient;    
    private StaffMember doctor;
    private String medicalCondition;
    private String payOrFree;
    //// Constructer
   
    public Prescription(int id, int patientID, int doctorID, Date startDate, Date endDate, List<Medicine> listofMedicine, int frequency, String payOrFree, String medicalCondition)
    {
        this.id = id;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.frequency = frequency;
        
        this.startDate = startDate;
        this.endDate = endDate;
        this.payOrFree = payOrFree;
        this.medicalCondition = medicalCondition; 
        this.listofMedicine = null;
    }
    
    public Prescription(int patientID, int doctorID,Date startDate, Date endDate, List<Medicine> listofMedicine, int frequency, String payOrFree,String medicalCondition)
    {
        
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.listofMedicine = listofMedicine;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payOrFree = payOrFree;        
        this.medicalCondition = medicalCondition;
        
        prescriptionDMO.put(this);
        prescriptionDMO.addMedicinestoPresc(this);
    }
    
//    public Prescription(Patient p, int frequency, Date startDate, Date endDate, String payOrFree, 
//            String medicalCondition)
//    {
//        this.patient = p;
//        this.patientID = p.getId();
//        this.frequency = frequency;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.payOrFree = payOrFree;
//        this.medicalCondition = medicalCondition;
//       // this.listofMedicine = listOfMedicine;
//    }

    public Prescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getMedicalCondition()
    {
        return this.medicalCondition;
    }
    /// getter and setter methods
    public int getPrescriptionID()
    {
        return id;
    }
    
    public void setPrescriptionID(int inputId)
    {
        id = inputId;
    }
    
    public int getpatientID()
    {
        return patientID;
    }
    
    public void setpatientID(int input)
    {
        patientID = input;
    }
    
    public int getdoctorID()
    {
        return doctorID;
    }
    
    public void setdoctorID(int input)
    {
        doctorID = input;
    }
 
   
    public StaffMember getDoctor()
    {
        if(doctor == null)
        {
            try {
               this.doctor = StaffMemberDMO.getInstance().getById(this.doctorID);
            }catch (EmptyResultSetException ex) {
               Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        return doctor;
    }
    
    public Patient getPatient()
    {
        if(this.patient == null)
        {
            try {
                this.patient = patientDMO.getById(patientID);
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return patient;
    }
    
 
    
    public void setStartDate(Date input)
    {
        this.startDate = input;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }
    
    public Date getendDate()
    {
        return endDate;
    }
    
    public void setendDate(Date input)
    {
        endDate = input;
    }   
    
    public List<Medicine> getlistofMedicine()
    {
        if(this.listofMedicine == null)
            this.listofMedicine = PrescriptionDMO.getInstance().getMedicinesByPrescriptionById(this.id);

        return listofMedicine;
    }
    
    public void setlistofMedicine(List<Medicine> input)
    {
        listofMedicine = input;
    }
    
    public int getfrequency()
    {
        return frequency;
    }
    
    public String getfrequencyToString()
    {
        return ""+frequency;
    }
    
    public void setfrequency(int input)
    {
        frequency = input;
    }
    /// end getter and setter methods
    //hello
    
    public void setPayOrFree(String input)
    {
        payOrFree = input;
    }
    
    public String getPayOrFree()
    {
        return payOrFree;
    }
 
    public boolean isItExpired()
    {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
  
        
        if(today.before(this.endDate))
        {
            System.out.println("Its expired.");
            System.out.println("Todays Date: "+today);
            System.out.println("Expiry Date: "+ this.endDate);
            return true;
        }
        else
        {
            System.out.println("Its NOT expired.");
            System.out.println("Todays Date: "+today);
            System.out.println("Expiry Date: "+ this.endDate);
            return false;
        }
       
    }
    
 
    
    public void renewPrescriptions()
    {
        System.out.println("Date before " + this.endDate);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        this.endDate = cal.getTime();
        System.out.println("Date after renewal " + this.endDate);
    }
    
    public void setCondition(String inputCondition)
    {
        this.medicalCondition = inputCondition;
    }
    
    public String getCondition()
    {
        return medicalCondition;
    }
    
    public void printPrescriptions()
    {
        
    }
    
    public String toString(){
        return " ID: "+ this.id
             + "\n Patient ID: "+ this.patientID
             + "\n Doctor  ID: "+ this.doctorID;
    }
    public static void main(String [] args)
    {
        Prescription presc = new Prescription();
            
        presc.renewPrescriptions();
        
        
      
        
    }
    
}
