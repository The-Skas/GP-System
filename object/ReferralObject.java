package object;
import exception.EmptyResultSetException;
import java.util.Date;

import framework.GPSIS;
import framework.GPSISObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapper.PatientDMO;
//Extending GPSIS object so methods can be inherited (this is so we have to write less code, makes everything 
//-work neater and fit together properly)
public class ReferralObject extends GPSISObject{
	//The fields in Referrals Table
	private Date dateMade;
	private int conID,patID,payID,invID,docid;
	private int invPaid;
        private Patient patient;
	
	public ReferralObject(){
		
	}
	public ReferralObject(int id, Date dateMade, int docid, int conID, int patID, int invPaid){
		this.id = id;
		//Setting the objects variables
		this.dateMade = dateMade;
		this.docid = docid;
		this.conID=conID;
		this.patID=patID;
		this.payID=payID;
		this.invID=invID;
		this.invPaid=invPaid;
	}
	
	public ReferralObject(Date dateMade, int docid, int conID, int patID, int invPaid){
		//Setting the objects variables
		this.dateMade = dateMade;
		this.docid = docid;
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
	public int getDocId(){
		return docid;
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
        
        public Patient getPatient()
        {
            if(patient == null)
            {
                try {
                    this.patient = PatientDMO.getInstance().getById(this.getPatID());
                } catch (EmptyResultSetException ex) {
                    Logger.getLogger(ReferralObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return this.patient;
        }
}
