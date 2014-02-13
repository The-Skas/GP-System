package object;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import static framework.GPSISDataMapper.putHelper;
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
/**
 *
 * @author oa305
 */
public class Prescription extends GPSISObject
{
    private int patientID;
    private int doctorID;
    private Date endDate; //Not sure to use Date or Calender//
    private List<Medicine> listofMedicine;
    private int frequency; //How many medicines///
    
    
    //// Constructer
   
    public Prescription(int id, int patientID, int doctorID, Date endDate, List<Medicine> listofMedicine, int frequency)
    {
        this.id=id;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.frequency = frequency;
        this.endDate = endDate;
        
        
        
        
        this.listofMedicine = prescriptionDMO.getMedicinesByPrescriptionById(this.id);
    }
    
    public Prescription(int patientID, int doctorID, Date endDate, List<Medicine> listofMedicine, int frequency)
    {
        
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.listofMedicine = listofMedicine;
        this.frequency = frequency;
        this.endDate = endDate;
        
        prescriptionDMO.put(this);
        prescriptionDMO.addMedicinestoPresc(this);
    }

    public Prescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
 
    public Date getendDate()
    {
        return endDate;
    }
    
    public void setendDate(Date input)
    {
        endDate = input;
    }   
    
    public List getlistofMedicine()
    {
        
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
    
    public void setfrequency(int input)
    {
        frequency = input;
    }
    /// end getter and setter methods
    //hello
 
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
