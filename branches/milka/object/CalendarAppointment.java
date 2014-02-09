package object;

import framework.GPSISObject;
import mapper.CalendarAppointmentDMO;

import java.util.Date;
import java.util.Calendar;


public class CalendarAppointment extends GPSISObject 
{		//protected - only the classes which extend one can see it
		protected Date startTime; 
		protected Date endTime; // always startTime + 15 for RoutineAppointments

		public CalendarAppointment(int id, java.util.Date sT, java.util.Date eT) // constructor 1 - already exists in database
		{ 
			this.id = id;		
		}

		public CalendarAppointment(java.util.Date sT, java.util.Date eT) // constructor 2 - insert into database
		{ 
			this.startTime = sT;
			this.endTime = eT;

			calendarAppointmentDMO.put(this);
			// Need to retrieve ID from Query
		}

/*
 * 
 * Actually, it makes more sense to create new RoutineAppointments and new CareManagementAppointments
 * than creating a CalendarAppointment...
 * 
 * 
		public CalendarAppointment makeNewAppointment(){
			// TODO - check if it is in the opening hours of the surgery Mon-Fri 9am to 5.30pm and Sat 9am-12 noon
			// check if the day is not a public or bank holiday or a training day (determined by office manager)
			// respect AVAILABILITY of nurses and doctors 
			// can only be made in the future - check if the dateTime? is not in the past
			CalendarAppointment newAppointment;
			return newAppointment;
		}
*/
		public void determineTrainingDays(){ //this method should only be available for Office Managers
			// check if the logged in user is an Office Manager

		}

		public void WriteAppointmentSummary(){ // doctors are supposed to write a summary of an appointment so that appointment history is available for each patient
			// check if the logged in user is an Doctor

		}

	}
