package object;
import java.util.Date;

import framework.GPSIS;
import framework.GPSISObject;
//Extending GPSIS object so methods can be inherited (this is so we have to write less code, makes everything 
//-work neater and fit together properly)
public class ReferralObject extends GPSISObject{
	//The fields in Referrals Table
	private Date dateMade;
	private String docName;
	private int conID,patID,payID,invID;
	private int invPaid;
	
	public ReferralObject(){
		
	}
	public ReferralObject(int id, Date dateMade, String docName, int conID, int patID, int invPaid){
		this.id = id;
		//Setting the objects variables
		this.dateMade = dateMade;
		this.docName = docName;
		this.conID=conID;
		this.patID=patID;
		this.payID=payID;
		this.invID=invID;
		this.invPaid=invPaid;
	}
	
	public ReferralObject(Date dateMade, String docName, int conID, int patID, int invPaid){
		//Setting the objects variables
		this.dateMade = dateMade;
		this.docName = docName;
		this.conID=conID;
		this.patID=patID;
		this.payID=payID;
		this.invID=invID;
		this.invPaid=invPaid;
	}
	//Get methods for retrieval of wanted information
	public Date getDate(){
		return dateMade;
	}
	public String getDocName(){
		return docName;
	}
	public int getConID(){
		return conID;
	}
	public int getPatID(){
		return patID;
	}
	public int getPayID(){
		return payID;
	}
	public int getInvID(){
		return invID;
	}
	public int isInvPaid(){
		return invPaid;
	}
}
