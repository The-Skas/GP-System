package module.Referral;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import exception.EmptyResultSetException;
import mapper.StaffMemberDMO;
import object.ConsultantObject;
import object.StaffMember;

public class TextFiles {
	
	public static void buildPatientsLetter(String refID, String Fname, String Lname, String address, String docName, 
			String conID, String conName, String conAddress, String payID, String amount,String conNum) throws IOException{
		
		StaffMemberDMO staff = StaffMemberDMO.getInstance();
		String name = "";
		try {
			
		StaffMember doc = staff.getById(Integer.parseInt(docName));
		name = doc.getFirstName()+ " " + doc.getLastName()+ " ";
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			e.printStackTrace();
		}
		
		String body = address + " \n";
		body += "Dear " + Fname + " " + Lname + ", \n";
		body += "\n";
		body += "You Have been booked into a Specialist meeting with ";
		body += conName + ". ";
		body += "\nYour Referral ID is " + refID + " (made by " + name +").\n";
		body += "\nYour Payment ID is " + payID + " and the amount paid was �" + amount + "p.";
		body += "\nThe address to attend is " + conAddress + ". \nPlease Ring " + conNum+ " ";
		body += "to arrange a time.";
		body += "\nRegards, " + name;
		
		//Writes file using argument as filename.txt
        File file = new File("Patient Letter ID"+ refID + ".txt"); 
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(body);
        bufferedWriter.close();
        System.out.print(body);
        
	}

	public void buildConsultantsLetter(String refID, String Fname, String Lname, String docName, 
			String conID, String conAddress,String patName, String amount) throws IOException{
		
		StaffMemberDMO staff = StaffMemberDMO.getInstance();
		String name = "";
		try {
			
		StaffMember doc = staff.getById(Integer.parseInt(docName));
		name = doc.getFirstName()+ " " + doc.getLastName()+ " ";
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			e.printStackTrace();
		}
		
		
		String body = conAddress + " \n";
		body += "Dear " + Fname + " " + Lname + ", \n";
		body += "\n";
		body += "A referral for " + patName + "has been made to you.";
		body += "\nThe Referral ID is " + refID + " (made by " + name +").\n";
		body += "\nPlease send the invoice as soon as possible, " + "the amount paid was " + amount + "p.";
		body += "The Patient has been informed to contact you as soon as possible to arrange a time for this meet.";
		body += "\nRegards, " + name;
		System.out.print(body);
		
		//Writes file using argument as filename.txt
        File file = new File("Referrance ID"+  refID + ".txt"); 
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(body);
        bufferedWriter.close();
		
	}

	public static void buildInvoicePayment(String invId, String total, ConsultantObject consult){
		
		String body = "" + consult.getAccName() + " " + consult.getAccNum()+ " "+ consult.getSortCode() ;
		
		
		  File file = new File("C:/Users/Matthew/Desktop/" + "Invoice_Payment ID"+ invId + ".txt"); 
	        FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        try {
				bufferedWriter.write(body);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        try {
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public static void main(String[] args){
	
		try {
			
			buildPatientsLetter("1","Matt","Sharp","23 ButterCup Close, Paddock Wood, Kent TN12 6BG", "Dr. Jack", "7", "Dr. Jill", "Woodlands e6 5np", "8", "23.89", "07810731102" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
}
