package object;

import framework.GPSISObject;
//Extending GPSIS object so methods can be inherited (this is so we have to write less code, makes everything 
//-work neater and fit together properly)
public class SpecialityTypeObject extends GPSISObject {
	
	private String name;
	private int id, ConID;
	
	public SpecialityTypeObject(){
		
	}
	public SpecialityTypeObject(int id, String name, int ConID){
		this.id = id;
		//Set Variables
		this.name = name;
		this.ConID = ConID;
	}
	
	public SpecialityTypeObject(String name, int ConID){
		//Set Variables
		this.name = name;
		this.ConID = ConID;
	}
	
	public String getName(){
		return name;
	}
	public int getConID(){
		return ConID;
	}
	
}
