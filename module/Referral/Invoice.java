package module.Referral;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import object.InvoiceObject;
import object.ReferralObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import mapper.InvoiceDMO;
import mapper.ReferralDMO;


public class Invoice extends JFrame {
	private JLabel lab1,lab2,lab3,lab4,lab5, lab6, lab7;
	private JTextArea a1,a2,a3,a4,a5, a7;
	private JButton but1,but2, but7;
	private JComboBox combo;
	private int counter = 0;
	private String[] arr1;
	private BufferedWriter writer;
	private int id;
	
	public Invoice(String id, int refID, Double Amount, int ConId, int ispaid){
		//Set all this a1,a2,a3,etc.setText(from sql)
		setLayout(new FlowLayout());
		Event e = new Event();
		this.id = Integer.parseInt(id);
		InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		try {
			InvoiceObject inv = invoiceDMO.getById(this.id);
		} catch (EmptyResultSetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.print(refID);
		lab1 = new JLabel("          Invoice ID: ");
		add(lab1);
		a1 = new JTextArea(1,15);
		add(a1);
		a1.setText(id);
		a1.setEditable(false);
		
		lab7 = new JLabel("         Referral ID:");
		add(lab7);
		a7 = new JTextArea(1,15);
		add(a7);
		a7.setText(""+refID);
		a7.setEditable(false);
		
		lab2 = new JLabel("           Amount: £");
		add(lab2);
		a2 = new JTextArea(1,15);
		add(a2);
		a2.setText(""+Amount);
		a1.setEditable(false);
		
		lab3 = new JLabel("   Consultant ID: ");
		add(lab3);
		a3 = new JTextArea(1,15);
		add(a3);
		a3.setText(""+ConId);
		a1.setEditable(false);
		
		lab5 = new JLabel(" Recieved Date: ");
		add(lab5);
		a5 = new JTextArea(1,15);
		add(a5);
		Calendar cal = Calendar.getInstance();
		java.util.Date dt = cal.getTime();
		String s = new SimpleDateFormat("yyyy-MM-dd").format(dt);
		a5.setText(s);
		a5.setToolTipText("Change if not today");
		
		lab4 = new JLabel("Paid: ");
		add(lab4);
		//Response
		lab6 = new JLabel("");
		add(lab6);
		//represent the returned boolean value represented as an integer (0 or 1) in word form (true or false)
		lab6.setText(ispaid==1 ?"Paid":"Not Paid");
		but1 = new JButton("Pay");
		if(ispaid==0){
		add(but1);
		}
		else if(ispaid!=0){
		//Add close button
		}
		but1.addActionListener(e);
	}
	

	public Invoice(){
		setLayout(new FlowLayout());
		Event e = new Event();
		
		//I dont know if i need this here yet
		
		/*lab1 = new JLabel("Invoice ID: ");
		add(lab1);
		a1 = new JTextArea(1,15);
		add(a1);*/
		
		
		lab7 = new JLabel("       Referral ID: ");
		add(lab7);
		a7 = new JTextArea(1,15);
		add(a7);
		
		
		lab2 = new JLabel("         Amount: £");
		add(lab2);
		a2 = new JTextArea(1,15);
		add(a2);
		

		lab3 = new JLabel(" Consultant ID: ");
		add(lab3);
		a3 = new JTextArea(1,15);
		add(a3);
		
		lab5 = new JLabel("Recieved Date: ");
		add(lab5);
		a5 = new JTextArea(1,15);
		add(a5);
		a5.setToolTipText("yyyy-MM-dd");
		
		lab4 = new JLabel("Paid: ");
		add(lab4);
		//Just for break
		lab6 = new JLabel("                                                                         ");
		add(lab6);
		
		but1 = new JButton("Pay");
		add(but1);
		but1.addActionListener(e);
		
	}
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//So only one message pop-up is show
			
			counter = 0;
			int count = 0;
			if((e.getSource()==but1)){
				/*
				if((a1.getText().length()==0)&&(counter<1)){
					counter +=1;
				JOptionPane.showMessageDialog(null, "Please Enter an Invoice ID");
				}
				*/
				if((a7.getText().length()==0)&&(counter<1)){
					counter +=1;
				JOptionPane.showMessageDialog(null, "Please Enter a Referral ID");
				}
				else if((a2.getText().length()==0)&&(counter<1)){
					counter +=1;
				JOptionPane.showMessageDialog(null, "Please Enter an Amount");
				}
				else if((a3.getText().length()==0)&&(counter<1)){
					counter +=1;
				JOptionPane.showMessageDialog(null, "Please Enter a Consultant");
				}
				else if((a5.getText().length()==0)&&(counter<1)){
					counter +=1;
				JOptionPane.showMessageDialog(null, "Please Enter a Recieved Date");
				}
			}
			//Pass through current date or received (if it is previous)
			String receivedDate = "";
			Calendar cal = Calendar.getInstance();
			java.util.Date dt = cal.getTime();
			String s = new SimpleDateFormat("yyyy-MM-dd").format(dt);
			if(a5.getText().trim().equals(s)){
				receivedDate = s;
			}
			else if(!a5.getText().trim().equals(s)){
				receivedDate = a5.getText().trim();
			}
			
			if(/*(a1.getText().length()>=1)&&*/(a7.getText().length()>=1)&&(a2.getText().length()>=1)&&(a3.getText().length()>=1)&&(a5.getText().length()>=1)){
				try{
				lab6.setText("PAID");
				InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
				GPSISDataMapper.connectToDatabase();
				InvoiceObject inv = new InvoiceObject(Integer.parseInt(a7.getText()), Double.parseDouble(a2.getText()),Integer.parseInt(a3.getText()),receivedDate, 1);
				inv.setId(id);
				invoiceDMO.put(inv);
				setVisible(false);
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null, "Incorrect Date format");
				}
			}
		}
	}
}
