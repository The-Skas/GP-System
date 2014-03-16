/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;
import exception.EmptyResultSetException;
import framework.GPSISFramework;
import framework.GPSISObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapper.PatientDMO;
import org.joda.time.DateTime;

import framework.GPSISObject;
import java.util.List;
import object.MedicalCondition;
/**
 *
 * @author skas
 */

public class Patient extends GPSISObject{
    public enum AgeGroup
    {
        UnderFive,
        Youth,
        Adult,
        Elderly
    }
    private static int YOUTH_YEARS = 5;
    private static int ADULT_YEARS = 18;
    private static int ELDER_YEARS = 65;
    private String firstName;
    private String lastName;
    private String postCode;
    private String address;
    private String phone;
    private String nhsNumber;
    private String country;
    
    private char sex;
    
    private Date dob;
    
    private int fatherId;
    
    private int motherId;
    
    
    //Although it is a lengthy constructor, builder method isnt as useful as ALL
    //the attributes are needed
    public Patient(int id, String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob
                    ,int fatherId, int motherId)
    {
        this.id = id;
        
        this.firstName = firstName;
        this.lastName  = lastName;
        this.sex       = sex;
        this.dob       = dob;
        
        //address
        this.postCode  = postCode;
        this.address   = address;
        this.phone     = phone;
        
        this.fatherId = fatherId;
        this.motherId = motherId;
    }
    
    public Patient( String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob
                    ,int fatherId,int motherId)
    {   
        this.firstName = firstName;
        this.lastName  = lastName;
        this.sex       = sex;
        this.dob       = dob;
        
        //address
        this.postCode  = postCode;
        this.address   = address;
        this.phone     = phone;
        
        this.fatherId = fatherId;
        this.motherId = motherId;
        
        PatientDMO.getInstance().put(this);
    }
    public Patient setFatherId(int id)
    {
        System.out.println("In patient object id is: "+ id);
        this.fatherId = id;
        return this;
    }
    
    public Patient setMotherId(int id)
    {
        this.motherId = id;
        return this;
    }
    
    public int getMotherId()
    {
        return this.motherId;
    }
    
    public int getFatherId()
    {
        return this.fatherId;
    }
    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }
    

    public String getPhone()
    {
        return this.phone;
    }
    
    public String getPostCode()
    {
        return this.postCode;
    }
    
    public String getAddress()
    {
        return this.address;
    }
    public char getSex()
    {
        return this.sex;
    }
    
    public Date getDob()
    {
        return this.dob;
    }
    public AgeGroup getAgeGroup()
    {
        DateTime dobDT = new DateTime(this.dob);
        DateTime today = new DateTime();
        
        if(dobDT.isAfter(today.minusYears(YOUTH_YEARS)))
        {
            return AgeGroup.UnderFive;
        }
        else if(dobDT.isAfter(today.minusYears(ADULT_YEARS)))
        {
            return AgeGroup.Youth;
        }
        else if(dobDT.isAfter(today.minusYears(ELDER_YEARS)))
        {
            return AgeGroup.Adult;
        }
        else
        {
            return AgeGroup.Elderly;
        }
    }
    
    public ArrayList<MedicalCondition> getMedicalConditions()
    {
        PatientDMO tblPatient = PatientDMO.getInstance();
        try{
            return tblPatient.getPatientMedicalConditions(this);
        }catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    
    public void addMedicalConditions(ArrayList<MedicalCondition> mc)
    {
        PatientDMO tblPatient = PatientDMO.getInstance();
        
            tblPatient.addPatientMedicalConditions(mc, this);
        
    }
    
    public Patient getFather() {
        PatientDMO tbl = PatientDMO.getInstance();
        try {
            return tbl.getById(this.fatherId);
        } catch (EmptyResultSetException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
     public Patient getMother() {
        PatientDMO tbl = PatientDMO.getInstance();
        try {
            return tbl.getById(this.motherId);
        } catch (EmptyResultSetException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public List<Patient> getChildren()
    {
        return PatientDMO.getInstance().getPatientChildren(this);
    }
     
    public List<Patient> getSiblings()
    {
        return PatientDMO.getInstance().getPatientSiblings(this);
    }
    
    public String toString()
    {
        String s = "";
        s+="------------ \n";
        s+="id: "+ this.getId()+"\n";
        s+="name: "+ this.getFirstName()+"\n";
        return s;
    }
    public static void main(String [] args)
    {
        
        if(AgeGroup.Adult instanceof AgeGroup)
        {
            System.out.println("Adult is an instance");
        }
        DateTime tomorrow = new DateTime();
        System.out.println("Should be current date: "+ tomorrow);
        
        DateTime dob = tomorrow.minusYears(64);
        DateTime youthYears=tomorrow.minusYears(5);
        System.out.println("Should be younger then " +youthYears+" and dob is: "+ dob);
        DateTime adultYears = tomorrow.minusYears(18);
        
        DateTime elderYears = tomorrow.minusYears(65);
        
        if(dob.isAfter(youthYears))
        {
            System.out.println(youthYears);
            System.out.println("is Younger then 5.");
        }
        else if(dob.isAfter(adultYears))
        {
            System.out.println(adultYears);
            System.out.println("Is Youth");
        }
        else if(dob.isAfter(elderYears))
        {
            System.out.println(elderYears);
            System.out.println("Is Adult");
        }
        else
        {
            System.out.println("Is Elder ");
        }
        System.out.println(dob);
    }
    //Functionality

   
}

