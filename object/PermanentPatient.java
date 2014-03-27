/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import exception.EmptyResultSetException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapper.PatientDMO;
import mapper.StaffMemberDMO;
import object.Patient;
import object.StaffMember;

/**
 *
 * @author skas
 */
public class PermanentPatient extends Patient {
    private String nhsNumber;
    private StaffMember doctor;
    private int doctorId;
    public PermanentPatient(int id, String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob, 
                    int fatherId, int motherId)
    {
        super(id,firstName,lastName,sex,postCode,address,phone,dob, fatherId, motherId);
    };
    public PermanentPatient(int id, String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob, 
                    int fatherId, int motherId, int docId, String nhs)
    {
        super(id,firstName,lastName,sex,postCode,address,phone,dob, fatherId, motherId);
        this.nhsNumber = nhs;
        this.doctorId = docId;
    };
    public PermanentPatient( String firstName, String lastName, char sex,
                    String postCode, String address, String phone,Date dob
                    , int fatherId, int motherId)
    {
        super(firstName,lastName,sex,postCode,address,phone,dob, fatherId, motherId);
    };
    
    public static PermanentPatient constructByPatient(Patient o, StaffMember doctor, String nhsNumber)
    {
        PermanentPatient pat = PermanentPatient.getByPatient(o, doctor, nhsNumber);
        PatientDMO.getInstance().putPermanentPatient(pat);
        return pat;
    }
    
    public static PermanentPatient getByPatient(Patient o, StaffMember doctor, String nhsNumber)
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
        if(this.doctor == null)
        {
            try {
                this.doctor = StaffMemberDMO.getInstance().getById(this.doctorId);
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(PermanentPatient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
