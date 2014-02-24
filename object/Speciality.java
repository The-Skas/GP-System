package object;

import mapper.SQLBuilder;
import exception.DuplicateEntryException;
import exception.EmptyResultSetException;
import framework.GPSISObject;

public class Speciality extends GPSISObject {
	private String name;
	
	public Speciality(int id, String n)
	{
		this.id = id;
		this.name = n;
	}
	
	public Speciality(String n) throws DuplicateEntryException
	{
		this.name = n;
		// check for duplicates in StaffMember table
		SQLBuilder sql = new SQLBuilder("name", "=", n);
		try 
		{
			specialityDMO.getByProperties(sql);
			throw new DuplicateEntryException();
		} 
		catch (EmptyResultSetException e) // if there's an Empty Result Set, this Speciality is not taken :D
		{ 
			this.name = n;			
			specialityDMO.put(this);
		}
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
