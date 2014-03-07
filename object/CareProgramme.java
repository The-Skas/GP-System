package object;

import java.util.HashSet;
import java.util.Set;

import exception.EmptyResultSetException;
import framework.GPSISObject;

public class CareProgramme extends GPSISObject {
	private String name;
	private StaffMember doctor;
	//private Room room;
	private Set<Patient> patients;
	
	
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDoctor()
	{
		return this.doctor.getUsername();
	}

	
	public CareProgramme getTempCP()
	{
		this.name = "Temporary Care Programme";
		try {
			this.doctor = staffMemberDMO.getById(1);
			this.patients = new HashSet<Patient>();
			this.patients.add(patientDMO.getById(1));
			this.patients.add(patientDMO.getById(2));
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
}
