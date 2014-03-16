package object;

import java.util.ArrayList;

import mapper.SpecialityTypeDMO;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import framework.GPSISObject;
//Extending GPSIS object so methods can be inherited (this is so we have to write less code, makes everything 
//-work neater and fit together properly)
public class SpecialityTypeObject extends GPSISObject {
	
	private String name;
	private int SpecTypeId, ConID;
	
	public SpecialityTypeObject(String name, int ConID){
		this.name = name;
		this.ConID = ConID;
	}
	public SpecialityTypeObject(){
		//Set Variables
	}
	public SpecialityTypeObject(int id, String name, int ConID){
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
	public int getSpecTypeID(){
		return SpecTypeId;
	}
	
	
	
	public static void main(String[] args) throws EmptyResultSetException{
		
		ArrayList<SpecialityTypeObject> arr1;
		ArrayList<SpecialityTypeObject> arr2;
		
		SpecialityTypeDMO s = SpecialityTypeDMO.getInstance();
		SpecialityTypeObject r = new SpecialityTypeObject("Allergy",1);
		GPSISDataMapper.connectToDatabase();
		s.put(r);
		//
		SpecialityTypeDMO ss = SpecialityTypeDMO.getInstance();
		System.out.println(r.getId());
		SpecialityTypeObject r2 = new SpecialityTypeObject("Heart",3);
		GPSISDataMapper.connectToDatabase();
		ss.put(r2);
		System.out.println(r2.getConID());
		try{
			
			arr1 = (ArrayList<SpecialityTypeObject>) s.getAll();
			//arr2 = (ArrayList<SpecialityTypeObject>) ss.getAll();
			
			/*
			for(SpecialityTypeObject x : arr2){
				System.out.print(x.getConID() + " ");
				System.out.println(x.getSpecTypeID());
			}
			*/
			
		}catch(Exception e){
			System.out.print("Empty");
		}
	}
	
}
