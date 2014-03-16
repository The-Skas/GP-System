/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Patient;

import module.Patient.*;
import object.Patient;

/**
 *
 * @author skas
 */

public class PatientFamily {
    public enum Relationship{
        Parent, Sibling, Child
    }
    private Relationship relationship;
    private Patient patient;
    
    public PatientFamily(Patient p, Relationship rel)
    {
        this.patient = p;
        this.relationship = rel;
    }
    
    public Patient getPatient()
    {
        return this.patient;
    }
    
    public Relationship getRelation()
    {
        return this.relationship;
    }
    
}
