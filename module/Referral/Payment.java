package module.Referral;
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

import mapper.ConsultantDMO;
import mapper.InvoiceDMO;
import mapper.PaymentDMO;
import mapper.SpecialityTypeDMO;
import object.ConsultantObject;
import object.InvoiceObject;
import object.PaymentObject;
import object.SpecialityTypeObject;
import framework.GPSISDataMapper;


public class Payment extends JFrame {
	 
	private JLabel lab1,lab2,lab3,lab4,lab5,lab6, sp,xtra;
	private JTextArea a1,a2,a3,a4,a5,a6,ax;
	private JButton but1;
	private BufferedWriter writer;
	private int id, refid,conid;
	private double price;
	
	public Payment(int refid, int conid){
		ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		List<ConsultantObject> s = consultantDMO.getAll();
		for(ConsultantObject x: s){
			if(x.getId()==conid){
				price = x.getPrice();
			}
		}
		
		setLayout(new FlowLayout());
		Event e = new Event();
		this.refid = refid;
		this.conid = conid;
		lab1 = new JLabel("       Payment ID: ");
		add(lab1); 
		a1 = new JTextArea(1,15);
		add(a1);
		a1.setText("Not Paid");
		a1.setEditable(false);
		
		xtra = new JLabel("        Referral ID: ");
		add(xtra); 
		ax = new JTextArea(1,15);
		add(ax);
		ax.setText(""+ refid);
		ax.setEditable(false);
		
		lab6 = new JLabel("  Name on Card: ");
		add(lab6); 
		a6 = new JTextArea(1,15);
		add(a6);
		
		lab2 = new JLabel("                   Total:  ");
		add(lab2); 
		a2 = new JTextArea(1,15);
		add(a2);
		a2.setText(" "+ price);
		
		lab3 = new JLabel("   Account Num: ");
		add(lab3); 
		a3 = new JTextArea(1,15);
		add(a3);
		
		lab4 = new JLabel("          Exp. Date: ");
		add(lab4); 
		a4 = new JTextArea(1,15);
		add(a4);
		
		lab5 = new JLabel("           CSC Num:");
		add(lab5); 
		a5 = new JTextArea(1,15);
		add(a5);
		
		but1 = new JButton("Pay");
		add(but1);
		but1.addActionListener(e);
		
		sp = new JLabel("");
		add(sp);
		
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
					setVisible(false);
					
			}else{
				JOptionPane.showMessageDialog(null, "Missing data");
			}
		}
	}
}
//60% Screen size

