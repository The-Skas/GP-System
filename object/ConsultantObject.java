package object;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import framework.GPSIS;
import framework.GPSISObject;
//Extending GPSIS object so methods can be inherited (this is so we have to write less code, makes everything 
//-work neater and fit together properly)
public class ConsultantObject extends GPSISObject{
	
		private String fname, lname, title, address, email, contactnum, speciality, accName;
		private double price;
		private int accNum, sortCode,isActive;
		
		public ConsultantObject(String title, String fname, String lname,String address, String email, String contactnum,
				double price, String AccName, int AccNum, int SortCode, int isActive){
			
			//Setting variableswhen constructed
			this.id = id;
			this.title = title;
			this.fname=fname;
			this.lname = lname;
			this.address = address;
			this.email = email;
			this.contactnum= contactnum;
			this.price = price;
			this.accName = AccName;
			this.accNum = AccNum;
			this.sortCode = SortCode;
			this.isActive = isActive;
		}
		
		public ConsultantObject(int id, String title, String fname, String lname,String address, String email, String contactnum,
				double price, String AccName, int AccNum, int SortCode,int isActive){
			//Setting variableswhen constructed
			
			this.id = id;
			this.title = title;
			this.fname=fname;
			this.lname = lname;
			this.address = address;
			this.email = email;
			this.contactnum= contactnum;
			this.price = price;
			this.accName = AccName;
			this.accNum = AccNum;
			this.sortCode = SortCode;
			this.isActive= isActive;
		}
		
		
		//Get methods
		public String getFName(){
			return fname;
		}
		public String getLName(){
			return lname;
		}
		public String getTitle(){
			return title;
		}
		public String getAddress(){
			return address;
		}
		public String getEmail(){
			return email;
		}
		public String getNum(){
			return contactnum;
		}
		public double getPrice(){
			return price;
		}
		public String getAccName(){
			return accName;
		}
		public int getAccNum(){
			return accNum;
		}
		public int getSortCode(){
			return sortCode;
		}
		public int isActive(){
			return isActive;
		}
		
		//Setters
		public void setFName(String fname){
			this.fname = fname;
		}
		public void setLName(String lname){
			this.lname = lname;
		}
		public void setTitle(String title){
			this.title = title;
		}
		public void setAddress(String add){
			this.address = add;
		}
		public void setEmail(String email){
			this.email = email;
		}
		//String as first number is 0 (it will be lost if data type is an int)
		public void setNum(String number){
			this.contactnum = number;
		}
		public void setPrice(double price){
			this.price=price;
		}
		public void setAccName(String accName){
			this.accName = accName;
		}
		public void setAccNum(int accNum){
			this.accNum = accNum;
		}
		public void setSortCode(int sortCode){
			this.sortCode = sortCode;
		}
		public void setisActive(int isActive){
			this.isActive = isActive;
		}
}
