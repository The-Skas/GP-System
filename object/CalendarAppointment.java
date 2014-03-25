package object;
 
import java.util.Date;

import framework.GPSISObject;
 
 
public class CalendarAppointment extends GPSISObject 
{        
        protected Date startTime; 
        protected Date endTime; // always startTime + 15 for RoutineAppointments
 
        public CalendarAppointment(int id, java.util.Date sT, java.util.Date eT) // constructor 1 - already exists in database
        { 
            this.id = id;    
            this.startTime = sT;
            this.endTime = eT;
        }
 
        public CalendarAppointment(java.util.Date sT, java.util.Date eT) // constructor 2 - insert into database
        { 
            this.startTime = sT;
            this.endTime = eT;
 
            // Need to retrieve ID from Query
        }
         
         
        /*


        public CalendarAppointment makeNewAppointment(){
            // TODO - check if it is in the opening hours of the surgery Mon-Fri 9am to 5.30pm and Sat 9am-12 noon
            // check if the day is not a public or bank holiday or a training day (determined by office manager)
            // respect AVAILABILITY of nurses and doctors 
            // can only be made in the future - check if the dateTime? is not in the past
            CalendarAppointment newAppointment;
            return newAppointment;
        }
*/
         
         
        public Date getEndTime()
        {
            return this.endTime;
        }

         
        public Date getStartTime()
        {
            return this.startTime;
        }
         
 
        public boolean isRoutine()
        {
            if(this instanceof RoutineAppointment)
                return true;
            else
                return false;
        }
        
        public String getType()
        {
        	if(this.isRoutine())
        		return "Routine";
        	else
        		return "Care Management";
        }
 
    } 