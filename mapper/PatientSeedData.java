/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapper;

import framework.GPSISDataMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapper.PatientDMO;
import object.Patient;

/**
 *
 * @author skas
 */
public class PatientSeedData {
    public static void main(String [] args)
    {
        GPSISDataMapper.connectToDatabase();
        PatientDMO pDMO = PatientDMO.getInstance();
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        Patient p; 
        try {
            p =new Patient( 
                    "Salman",
                    "Khalifa",
                    'm',
                    "N1X 3FT",
                    "45th Moore St, Manchester", 
                    "07384929123",
                    fm.parse("1989-12-01"),
                    0, //father
                    0); //mother
            
            pDMO.put(p);
            
            p = new Patient( 
                    "Abdulla",
                    "Khalifa",
                    'm',
                    "N1X 3FT",
                    "45th Moore St, Manchester", 
                    "07384929123",
                    fm.parse("1988-10-23"),
                    0, //father
                    0); 
            
            pDMO.put(p);
            
            p =new Patient( 
                    "John",
                    "Belushi",
                    'm',
                    "W2F IJT",
                    "Mourn 58, Sourn St", 
                    "07557989794",
                    fm.parse("1975-04-11"),
                    0, //father
                    0); 
            
        } catch (ParseException ex) {
            Logger.getLogger(PatientSeedData.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                
    }


}
