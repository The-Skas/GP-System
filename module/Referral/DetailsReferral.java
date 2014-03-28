package module.Referral;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

import mapper.ConsultantDMO;
import mapper.InvoiceDMO;
import mapper.PaymentDMO;
import mapper.ReferralDMO;
import object.ConsultantObject;
import object.InvoiceObject;
import object.PaymentObject;
import object.ReferralObject;
import object.StaffMember;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;


public class DetailsReferral extends JFrame {
	private JLabel lab1,lab2,lab3,lab4,lab5,lab6,lab7, lab8;
	private JLabel space1, space2, space3, space4, space5,space6, space7;
	private JTextField a1,a2,a3,a4,a5,a6,a7,a8;
	private JButton but1,but2,but3,but4,pay;
	private JPanel pan1,pan2,pan3,pan4,pan5,pan6,pan7,pan8,main;
	private JMenu men;
	private JMenuItem itm;
	private JMenuBar mb;
	private int count = 0;
	
	public DetailsReferral(String searchValue, ReferralObject obj){
		Border border =BorderFactory.createLineBorder(Color.BLACK);
		main = new JPanel();
		add(main);
		main.setBorder(BorderFactory.createTitledBorder("Referral"));
		
		Event e = new Event();
		
		mb= new JMenuBar();
		setJMenuBar(mb);
		men = new JMenu("File");
		mb.add(men);
		
		pan1 = new JPanel();
		lab1 = new JLabel("Referral ID: ");
		pan1.add(lab1);
		space1 = new JLabel("                                                                                                   ");
		pan1.add(space1);
		a1 = new JTextField(15);
		pan1.add(a1);
		a1.setBorder(border);
		pan1.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan1);
		
		pan2 = new JPanel();
		lab2 = new JLabel("Date Made: ");
		pan2.add(lab2);
		space2 = new JLabel("                                                                                                   ");
		pan2.add(space2);
		a2 = new JTextField(15);
		pan2.add(a2);
		a2.setBorder(border);
		pan2.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan2);
		
		pan3 = new JPanel();
		lab8 = new JLabel("Doctors Id: ");
		a8 = new JTextField(15);
		a8.setBorder(border);
		pan3.add(lab8);
		space3 = new JLabel("                                                                                                   ");
		pan3.add(space3);
		pan3.add(a8);
		pan3.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan3);
		
		pan4 = new JPanel();
		lab3 = new JLabel("Consultant ID: ");
		pan4.add(lab3);
		space4 = new JLabel("                                                                                              ");
		pan4.add(space4);
		a3 = new JTextField(15);
		a3.setBorder(border);
		pan4.add(a3);
		pan4.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan4);
		
		pan5 = new JPanel();
		lab4 = new JLabel("Patient ID: ");
		pan5.add(lab4);
		space5 = new JLabel("                                                                                                     ");
		pan5.add(space5);
		a4 = new JTextField(15);
		a4.setBorder(border);
		pan5.add(a4);
		pan5.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan5);
		
		pan6 = new JPanel();
		lab5 = new JLabel("Payment ID: ");
		pan6.add(lab5);
		space6 = new JLabel("                                                                                                  ");
		pan6.add(space6);
		a5 = new JTextField(15);
		a5.setBorder(border);
		pan6.add(a5);
		pan6.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan6);
		
		
		lab7 = new JLabel("Invoice Paid: ");
		main.add(lab7);
		lab6 = new JLabel("NOT PAID");
		main.add(lab6);
		
		
		//Is paid or not
		InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		List<InvoiceObject> set1;
		try {
			set1 = invoiceDMO.getAll();
			for(InvoiceObject x: set1){
				if(Integer.parseInt(searchValue)==x.getRefID()){
					if(x.getIsPaid()==1){
						lab6.setText("PAID");
					}
				}
			}
		} catch (EmptyResultSetException e1) {
			e1.printStackTrace();
		}
		
		ReferralDMO referralDMO2 = ReferralDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		//Make a referralObject called r2 (using parseInt to turn text to an int)
		try {
			ReferralObject r2 = referralDMO2.getById(Integer.parseInt(searchValue.trim()));
			
			a1.setText(""+r2.getId());
			a2.setText(""+r2.getDate());
			a8.setText(""+r2.getDocId());
			a3.setText(""+r2.getConID());
			a4.setText(""+r2.getPatID());
			a5.setText(""+r2.getPayID());
			
		} catch (NumberFormatException | EmptyResultSetException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		but4 = new JButton("Make Payment");
		
		if((!a5.getText().trim().equals("0"))&&(count<1)){
			lab6.setText("Paid");
			count+=1;
		}
		
		but4.addActionListener(e);
	
		PaymentDMO paymentDMO = PaymentDMO.getInstance();
		//Connect to database
		GPSISDataMapper.connectToDatabase();
		//Make a set and store all the payment objects in it
		List<PaymentObject> payObj;
		try {
			payObj = paymentDMO.getAll();
			int num = Integer.parseInt(searchValue);
			System.out.println("FOUND SEARCHVALUE REFID");
			//Enhanced for loop iterating through payment objects stored in the set
			int counter = 0;
			String temp = "";
			for(PaymentObject x:payObj){
				if((num == x.getRefID())){
					//If payment has a reference number then set text-field to found payment id
					temp = "" + x.getId();
					a5.setText(temp);
					a5.setEditable(false);
				}
			}
		} catch (EmptyResultSetException e1) {
			e1.printStackTrace();
		}
		
		if(a5.getText().trim().equals("0")){
			main.add(but4);
		}
		
		a5.setEditable(false);
		setVisible(false);
	
	}
	
	/*
	public DetailsReferral(String docName, String PatientID){
		
		mb= new JMenuBar();
		setJMenuBar(mb);
		men = new JMenu("File");
		men.add(mb);
		
		main = new JPanel();
		main.add(main);
		
		Event e = new Event();
		
		lab1 = new JLabel("        Referral ID: ");
		main.add(lab1);
		a1 = new JTextArea(1,15);
		main.add(a1);
		
		lab2 = new JLabel("        Date Made: ");
		main.add(lab2);
		a2 = new JTextArea(1,15);
		main.add(a2);
		
		
		lab8 = new JLabel("  Doctors ID: ");
		a8 = new JTextArea(1,15);
		a8.setText(docName);
		main.add(lab8);
		main.add(a8);
		
		lab3 = new JLabel("   Consultant ID: ");
		main.add(lab3);
		a3 = new JTextArea(1,15);
		main.add(a3);
		
		lab4 = new JLabel("          Patient ID: ");
		main.add(lab4);
		a4 = new JTextArea(1,15);
		main.add(a4);
		a4.setText(PatientID);
		
		lab7 = new JLabel("Invoice Paid: ");
		main.add(lab7);
		lab6 = new JLabel("NOT PAID");
		main.add(lab6);
		
		but1 = new JButton("Make Payment");
		main.add(but1);
		but1.addActionListener(e);
		//In button action
		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		System.out.println(timeStamp);
		
		
	}
	*/
	
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==but1){
				//Save data
				
			}
			else if(e.getSource()==but4){
				Payment r2 = new Payment(Integer.parseInt(a1.getText()), Integer.parseInt(a3.getText()));
				r2.setVisible(true);
				r2.setTitle("Payment");
				r2.setSize(600, 360);
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		    	//Centre Window on screen
				int x = (int) ((dimension.getWidth() - r2.getWidth()) / 3);
				int y = (int) ((dimension.getHeight() - r2.getHeight()) / 4);
				r2.setLocation(x+200, y+200);
				setVisible(false);
			}
		}
		
	}
}
