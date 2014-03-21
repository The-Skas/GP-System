package object;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import framework.GPSIS;
import framework.GPSISObject;
//Extending GPSIS object so methods can be inherited (this is so we have to write less code, makes everything 
//-work neater and fit together properly)
public class InvoiceObject  extends GPSISObject{
		//Create Variables
		private int refid;
		private double amount;
		private int conid, ispaid;
		private Date date2;
		
		public InvoiceObject(){
			
		}
		public InvoiceObject(int id, int refid, double amount, int conid,String date, int ispaid){
			//Sets Variables upon construction
			this.id = id;
			this.refid = refid;
			this.amount = calTax(amount);
			this.conid = conid;
			this.ispaid = ispaid;
			//Turn string to date
			date2 = java.sql.Date.valueOf(date);
		}
		public InvoiceObject(int refid, double amount, int conid,String date, int ispaid){
			//Sets Variables upon construction
			this.id = getId();
			this.refid = refid;
			this.amount = calTax(amount);
			this.conid = conid;
			this.ispaid = ispaid;
			//Turn string to date
			date2 = java.sql.Date.valueOf(date);
		}
		public Double calTax(Double total){
			Double taxedTotal = ((total/100)*20) + total ;
			return taxedTotal;
		}
		//Get methods for variables
		public int getRefID(){
			return refid;
		}
		public double getAmount(){
			return amount;
		}
		public int getConID(){
			return conid;
		}
		public int getIsPaid(){
			return ispaid;
		}
		public Date getDate(){
			return date2;
		}
		
}
