/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import framework.GPSISObject;
import java.util.ArrayList;
import mapper.PatientDMO;

/**
 *
 * @author skas
 */
public class MedicalCondition extends GPSISObject{
    //Assuming we cant add medicalConditions except by the DB!
    private final String name;
    private static ArrayList<MedicalCondition> allMedicalConditions;
    public MedicalCondition(int id, String medicalCondition)
    {
       this.id = id;
       this.name = medicalCondition;
    }
    public static MedicalCondition[] getAllArray()
    {
        if(allMedicalConditions == null)
        {
            allMedicalConditions=PatientDMO.getInstance().getAllMedicalConditions();
        }
        
        MedicalCondition[] medCondArr = new MedicalCondition[allMedicalConditions.size()];
        for(int i = 0; i < allMedicalConditions.size();i++)
        {
            medCondArr[i] = allMedicalConditions.get(i);
        }
        
        return medCondArr;
    }
    public static String[] getAllString()
    {
        if(allMedicalConditions == null)
        {
            allMedicalConditions=PatientDMO.getInstance().getAllMedicalConditions();
        }
        
        String [] medCondString = new String[allMedicalConditions.size()];
        for(int i = 0; i < allMedicalConditions.size(); i++)
        {
            medCondString[i] = allMedicalConditions.get(i).getName();
        }
        
        return medCondString;
    }
    public String getName()
    {
        return this.name;
    }
    
    public String toString()
    {
        return this.name;
    }
}
