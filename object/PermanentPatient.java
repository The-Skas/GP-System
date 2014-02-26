/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import java.util.Date;
import object.Patient;
import object.StaffMember;

/**
 *
 * @author skas
 */
public class PermanentPatient extends Patient {
    private String nhsNumber;
    private StaffMember doctor;
    public PermanentPatient(int id, String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob, 
                    int fatherId, int motherId)
    {
        super(id,firstName,lastName,sex,postCode,address,phone,dob, fatherId, motherId);
    };
    
    public PermanentPatient( String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob
                    , int fatherId, int motherId)
    {
        super(firstName,lastName,sex,postCode,address,phone,dob, fatherId, motherId);
    };
    
    public static PermanentPatient constructByPatient(Patient o, StaffMember doctor, String nhsNumber)
    {
        PermanentPatient pat = new PermanentPatient(o.getId(), o.getFirstName(), o.getLastName(), o.getSex(), o.getPostCode(),
            o.getAddress(), o.getPhone(),(Date)o.getDob(), o.getFatherId(), o.getMotherId());
        
        pat.nhsNumber = nhsNumber;
        pat.doctor = doctor;
        
        return pat;
    }
     public PermanentPatient setNHSAndDoctor(String NHS, StaffMember doctor)
    {
        this.nhsNumber = NHS;
        this.doctor = doctor;
        return this;
    }
     
    public String getNHSNumber()
    {
        return this.nhsNumber;
    }
    
    public StaffMember getDoctor()
    {
        return this.doctor;
    }
    
    public String toString()
    {
        String s = "";
        s+="------------ \n";
        s+="id: "+ this.getId()+"\n";
        s+="name: "+ this.getFirstName()+"\n";
        s+="doc: " + this.getDoctor().getFirstName()+"\n";
        return s;
    }
}
