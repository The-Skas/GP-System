package module.Referral;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

import mapper.ConsultantDMO;
import mapper.InvoiceDMO;
import mapper.PatientDMO;
import mapper.PaymentDMO;
import mapper.ReferralDMO;
import mapper.SpecialityTypeDMO;
import object.ConsultantObject;
import object.InvoiceObject;
import object.Patient;
import object.PaymentObject;
import object.ReferralObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;


public class Payment extends JFrame {
	 
	private JLabel lab1,lab2,lab3,lab4,lab5,lab6, sp,xtra;
	private JTextField a1,a2,a3,a4,a5,a6,ax;
	private JButton but1;
	private BufferedWriter writer;
	private int id, refid,conid,counter = 0;
	private double price;
	private JPanel pan1,pan2,pan3,pan4,pan5,pan6,pan7,pan8,pan9,pan10,main;
	private JMenuItem itm;
	private JMenuBar mb;
	private JMenu men;
	private JLabel space1,space2,space3,space4,space5,space6,space7,space8;
	private ConsultantObject consult;
	private ReferralObject refobj;
	private Patient patobj;
	
	public Payment(int refid, int conid){
		
		ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		List<ConsultantObject> s = consultantDMO.getAll();
		for(ConsultantObject x: s){
			if(x.getId()==conid){
				price = x.getPrice();
				consult = x;
			}
		}
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		main = new JPanel();
		add(main);
		main.setBorder(BorderFactory.createTitledBorder("Enter Card Details"));
		
		Event e = new Event();
		this.refid = refid;
		this.conid = conid;
		
		
		pan1 = new JPanel();
		lab1 = new JLabel("Payment ID: ");
		pan1.add(lab1); 
		space1 = new JLabel("                                                                                                    ");
		pan1.add(space1);
		a1 = new JTextField(15);
		a1.setBorder(border);
		pan1.add(a1);
		a1.setText("Not Paid");
		a1.setEditable(false);
		pan1.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan1);
		
		pan2 = new JPanel();
		xtra = new JLabel("Referral ID: ");
		pan2.add(xtra); 
		space2 = new JLabel("                                                                                                      ");
		pan2.add(space2);
		ax = new JTextField(15);
		ax.setBorder(border);
		pan2.add(ax);
		ax.setText(""+ refid);
		ax.setEditable(false);
		pan2.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan2);
		
		pan3 = new JPanel();
		lab6 = new JLabel("Name on Card: ");
		pan3.add(lab6); 
		space3 = new JLabel("                                                                                                ");
		pan3.add(space3);
		a6 = new JTextField(15);
		a6.setBorder(border);
		pan3.add(a6);
		pan3.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan3);
		
		pan4 = new JPanel();
		lab2 = new JLabel("Total: ");
		pan4.add(lab2); 
		space4 = new JLabel("                                                                                                              ");
		pan4.add(space4);
		a2 = new JTextField(15);
		a2.setBorder(border);
		pan4.add(a2);
		pan4.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan4);
		a2.setText(" "+ price);
		
		pan5 = new JPanel();
		lab3 = new JLabel("Account Num: ");
		pan5.add(lab3); 
		space5 = new JLabel("                                                                                                 ");
		pan5.add(space5);
		a3 = new JTextField(15);
		a3.setBorder(border);
		pan5.add(a3);
		pan5.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan5);
		
		pan6 = new JPanel();
		lab4 = new JLabel("Exp. Date: ");
		pan6.add(lab4); 
		space6 = new JLabel("                                                                                                         ");
		pan6.add(space6);
		a4 = new JTextField(15);
		a4.setBorder(border);
		pan6.add(a4);
		pan6.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan6);
		
		pan7 = new JPanel();
		lab5 = new JLabel("CSC Num:");
		pan7.add(lab5); 
		space7 = new JLabel("                                                                                                          ");
		pan7.add(space7);
		a5 = new JTextField(15);
		a5.setBorder(border);
		pan7.add(a5);
		pan7.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan7);
		
		if(counter<1){
		but1 = new JButton("Pay");
		main.add(but1);
		but1.addActionListener(e);
		counter +=1;
		}
		
		sp = new JLabel("");
		main.add(sp);
		
	}
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if((e.getSource()== but1)&&(a6.getText().length()!=0)&&(a4.getText().length()!=0)&&(a2.getText().length()!=0)&&(a3.getText().length()!=0)&&(a5.getText().length()!=0)){
				
					PaymentDMO paymentDMO = PaymentDMO.getInstance();
					GPSISDataMapper.connectToDatabase();
			
					//have to convert boolean to tiny int
		    		PaymentObject r = new PaymentObject(refid,a2.getText(),a6.getText(),a3.getText(),a4.getText(), a5.getText());
		    		paymentDMO.put(r);
		    		
		    		
		    		//Getting todays date and storing it in dt
		    		Calendar cal = Calendar.getInstance();
					java.util.Date dt = cal.getTime();
					String s = new SimpleDateFormat("yyyy-MM-dd").format(dt);
					//send payment to text file for Accountants
					sp.setText("Paid!");
					//Send letters(to specialist and Patient
					InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
					GPSISDataMapper.connectToDatabase();
					//Once payment is made the invoice is set up ready for paying upon being received.
					
					InvoiceObject inv = new InvoiceObject(refid, price,conid,s, 0);
					invoiceDMO.put(inv);
					JOptionPane.showMessageDialog(null, "Payment ID: " + r.getId() + "Invoice ID: "+ inv.getId());
					
					//Send Letters to both patient and Consultant
					TextFiles SendLetters = new TextFiles();
					
					/*try {
						SendLetters.buildPaymentFile(""+r.getId(), ""+r.getName(), ""+r.getAccNum(), ""+r.getEX(), ""+r.getCSC(), ""+r.getTotal());
					} catch (IOException e5) {
						e5.printStackTrace();
					}
					*//*
					
					ReferralDMO ref = ReferralDMO.getInstance();
					GPSISDataMapper.connectToDatabase();
					ArrayList<ReferralObject> all = new ArrayList<ReferralObject>();
					try {
						all = (ArrayList<ReferralObject>) ref.getAll();
					} catch (EmptyResultSetException e4) {
						e4.printStackTrace();
					}
					for(ReferralObject x: all){
						if(x.getId()==refid ){
							refobj = x;
						}
					}
					String patName = "";
					PatientDMO pat = PatientDMO.getInstance();
					GPSISDataMapper.connectToDatabase();
					ArrayList<Patient> all2 = new ArrayList<Patient>();
					
					try {
						all2 = (ArrayList<Patient>) pat.getAll();
					} catch (EmptyResultSetException e3) {
						
						e3.printStackTrace();
					}
					
					for(Patient x: all2){
						if(x.getId()==refobj.getPatID()){
							patobj = x;
							patName = x.getFirstName() + " " + x.getLastName();
						}
					}
					/*
					try {
						SendLetters.buildConsultantsLetter(""+ refid, consult.getFName(), consult.getLName(), ""+refobj.getDocId(), ""+consult.getId(), consult.getAddress(), patName, r.getTotal());
					} catch (IOException e2) {
						JOptionPane.showMessageDialog(null,  "No Such Doctor");
					}
					try {
						TextFiles.buildPatientsLetter(""+ refid, patobj.getFirstName(), patobj.getLastName(), patobj.getAddress(), ""+refobj.getDocId(), ""+consult.getId(), consult.getFName() + 
								consult.getLName(), consult.getAddress(), ""+r.getId(), r.getTotal(), ""+consult.getNum());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					*/
					setVisible(false);
					
			}else{
				JOptionPane.showMessageDialog(null, "Missing data");
			}
		}
	}
}
//60% Screen size

