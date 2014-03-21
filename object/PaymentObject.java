package object;
import java.util.Date;
import framework.GPSIS;
import framework.GPSISObject;
//Extending GPSIS object so methods can be inherited (this is so we have to write less code, makes everything 
//-work neater and fit together properly)
public class PaymentObject  extends GPSISObject{
	
		private int refid;
		private String total, name, accnum, expdate, csc;
		
		public PaymentObject(){
			
		}
		public PaymentObject(int id, int refid, String total, String name, String accnum, String expdate, String csc){
			//Set Variables
			this.id=id;
			this.refid = refid;
			this.total=calTax(total);
			this.name=name;
			this.accnum=accnum;
			this.expdate=expdate;
			this.csc=csc;
		}
	
		public PaymentObject(int refid, String total, String name, String accnum, String expdate, String csc){
			//Set Variables
			this.id=getId();
			this.refid = refid;
			this.total=calTax(total);
			this.name=name;
			this.accnum=accnum;
			this.expdate=expdate;
			this.csc=csc;
		}
		
		//Tax
		public String calTax(String total){
			Double taxedTotal = Double.parseDouble(total);
			taxedTotal = ((taxedTotal/100)*20) + taxedTotal ;
			String taxedTotalS = ""+ taxedTotal;
			return taxedTotalS;
		}
	
		//Get methods
		public int getRefID(){
			return refid;
		}
		public String getTotal(){
			return total;
		}
		public String getName(){
			return name;
		}
		public String getAccNum(){
			return accnum;
		}
		public String getEX(){
			return expdate;
		}
		public String getCSC(){
			return csc;
		}
	}
