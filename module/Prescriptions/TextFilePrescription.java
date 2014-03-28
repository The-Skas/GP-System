/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Prescriptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import object.Medicine;
import object.Prescription;

/**
 *
 * @author razizi
 */
public class TextFilePrescription {
    
    Prescription chosenPrescription;
    String fileName;
    public TextFilePrescription(Prescription p) throws IOException
    {
        chosenPrescription = p;
        buildPrescriptionsFile();
    }
    // creating a file so able to print.
    public void buildPrescriptionsFile() throws IOException
    {
        String body = "Prescription ID: "+ chosenPrescription.getId() + " \n";
               body += "Patient ID: "+ chosenPrescription.getPatient().getId() + " \n";
               body += "Patient First Name: " + chosenPrescription.getPatient().getFirstName() + " \n";
               body += "Patient Last Name: " + chosenPrescription.getPatient().getLastName() + " \n";
               body += "Patient Address: " + chosenPrescription.getPatient().getAddress() + " \n";
               body += "Patient Post Code: " + chosenPrescription.getPatient().getPostCode() + " \n";
               body += "Medical Condition: " + chosenPrescription.getMedicalCondition() + " \n";
               body += "Medicines prescribed: " + chosenPrescription.getlistofMedicine() + " \n";
               body += "How many times per day to use the medicine: " + chosenPrescription.getfrequency() + " \n";
               body += "Doctor Name Approval: " + chosenPrescription.getDoctor().getName() + " \n";
               body += "Paying or free (Patient choice): " + chosenPrescription.getPayOrFree() + " \n";
               body += "If paying, £  \n";
               body += "Prescription given: " + chosenPrescription.getStartDate() + " \n";
               body += "Prescription expires: " + chosenPrescription.getendDate() + " \n";
               
               // id is in the name of the file since every prescription is unique
               // Reason I also put the start date in the file name is because if a 
               // prescription has been renewed they will still have the same id,
               // but they will have a different start date. Therefore putting the 
               // id and the start date makes the file name unique. 
        this.fileName = chosenPrescription.getId()+"_"+chosenPrescription.getStartDate()+".txt";
        File file = new File(this.fileName); 
        
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(body);
        bufferedWriter.close();
        System.out.print(body);
               
    }
    
    public String getFileName()
    {
        return this.fileName;
    }
    
}
