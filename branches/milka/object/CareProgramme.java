package object;

import framework.GPSISObject;

public class CareProgramme extends GPSISObject {
	private String name;
	//private StaffMember doctor;
	private Room room;
	//private Set<CareProgrammeAppointment> schedule;
	//private Set<Patient> patients;
	
	public String getName()
	{
		return this.name;
	}
	
	public Room getRoom()
	{
		return this.room;
	}
	
}
