/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;
import java.sql.Date;

import framework.GPSISObject;
/**
 *
 * @author skas
 */
public class Patient extends GPSISObject{
    
    private String firstName;
    private String lastName;
    private char sex;
    
    //Enumerable
    private String postCode;
    private String address;
    private String phone;
    private Date dob;
    
    private boolean isPermanent;
    private String nhsNumber;
    //Enumerable
    private String country;
    
    private StaffMember doctor;
    public Patient(int id, String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob)
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
        
        this.isPermanent = false;
        this.nhsNumber = null;
        this.doctor = null;
    }
    
    public Patient( String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob)
    {   
        this.firstName = firstName;
        this.lastName  = lastName;
        this.sex       = sex;
        this.dob       = dob;
        
        //address
        this.postCode  = postCode;
        this.address   = address;
        this.phone     = phone;
        
        this.isPermanent = false;
        this.nhsNumber = null;
        this.doctor = null;
    }
    
    public String getAddress()
    {
        return this.address;
    }
    public Date getDob()
    {
        return this.dob;
    }
    
    public String getFirstName()
    {
        return this.firstName;
    }
    
    public boolean getIsPermenant()
    {
        return isPermanent;
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
    
    public char getSex()
    {
        return this.sex;
    }
    
    public void setIsPermenant(boolean value)
    {
        isPermanent = value;
    }
    
    
    //Functionality
}

