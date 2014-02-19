package object;

import framework.GPSISObject;

public class Speciality extends GPSISObject {
	private String name;
	
	public Speciality (String n, String d)
	{
		this.name = n;
		//specialityDMO.put(this);
	}
	
	public Speciality (int id, String n, String d)
	{
		this.id = id;
		this.name = n;
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
