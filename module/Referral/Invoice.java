package module.Referral;
import java.awt.Color;
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
import javax.swing.border.Border;

import object.InvoiceObject;
import object.ReferralObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import mapper.InvoiceDMO;
import mapper.ReferralDMO;


public class Invoice extends JFrame {
	private JLabel lab1,lab2,lab3,lab4,lab5, lab6, lab7;
	private JTextField a1,a2,a3,a4,a5, a7;
	private JButton but1,but2, but7;
	private JComboBox combo;
	private int counter = 0;
	private String[] arr1;
	private BufferedWriter writer;
	private int id;
	private JMenu men;
	private JMenuItem itm;
	private JMenuBar mb;
	private JLabel space1, space2,space3,space4,space5,space6,space7;
	private JPanel pan1,pan2,pan3,pan4,pan5,pan6,pan7,pan8,main;
			
	public Invoice(String id, int refID, Double Amount, int ConId, int ispaid){
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		//Set all this a1,a2,a3,etc.setText(from sql)
		
		mb = new JMenuBar();
		setJMenuBar(mb);
		men = new JMenu("File");
		mb.add(men);
		
		main = new JPanel();
		add(main);
		main.setBorder(BorderFactory.createTitledBorder("Invoice Details"));
		
		Event e = new Event();
		this.id = Integer.parseInt(id);
		InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
		
		try {
			InvoiceObject inv = invoiceDMO.getById(this.id);
		} catch (EmptyResultSetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		pan1 = new JPanel();
		lab1 = new JLabel("Invoice ID: ");
		pan1.add(lab1);
		space1 = new JLabel("                                                                                                      ");
		pan1.add(space1);
		a1 = new JTextField(15);
		a1.setBorder(border);
		pan1.add(a1);
		a1.setText(id);
		a1.setEditable(false);
		pan1.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan1);
		
		pan2 = new JPanel();
		lab7 = new JLabel("Referral ID:");
		pan2.add(lab7);
		space2 = new JLabel("                                                                                                     ");
		pan2.add(space2);
		a7 = new JTextField(15);
		pan2.add(a7);
		a7.setBorder(border);
		a7.setText(""+refID);
		a7.setEditable(false);
		pan2.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan2);
		
		pan3 = new JPanel();
		lab2 = new JLabel("Amount: ");
		pan3.add(lab2);
		space3 = new JLabel("                                                                                                       ");
		pan3.add(space3);
		a2 = new JTextField(15);
		a2.setBorder(border);
		pan3.add(a2);
		a2.setText(""+Amount);
		a2.setEditable(false);
		pan3.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan3);
		
		pan4 = new JPanel();
		lab3 = new JLabel("Consultant ID: ");
		pan4.add(lab3);
		space4 = new JLabel("                                                                                                ");
		pan4.add(space4);
		a3 = new JTextField(15);
		a3.setBorder(border);
		a3.setBorder(border);
		pan4.add(a3);
		a3.setText(""+ConId);
		pan4.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan4);
		a3.setEditable(false);
		
		pan5 = new JPanel();
		lab5 = new JLabel(" Recieved Date: ");
		pan5.add(lab5);
		space5 = new JLabel("                                                                                             ");
		pan5.add(space5);
		a5 = new JTextField(15);
		a5.setBorder(border);
		pan5.add(a5);
		pan5.setBorder(BorderFactory.createEtchedBorder());
		main.add(pan5);
		a5.setEditable(false);
		
		Calendar cal = Calendar.getInstance();
		java.util.Date dt = cal.getTime();
		String s = new SimpleDateFormat("yyyy-MM-dd").format(dt);
		a5.setText(s);
		a5.setToolTipText("Change if not today");
		
		pan6 = new JPanel();
		space6 = new JLabel("                                                                                                                                                                       ");
		pan6.add(space6);
		main.add(pan6);
		
		pan7 = new JPanel();
		lab4 = new JLabel("Paid: ");
		pan7.add(lab4);
		//Response
		lab6 = new JLabel("");
		pan7.add(lab6);
		//represent the returned boolean value represented as an integer (0 or 1) in word form (true or false)
		lab6.setText(ispaid==1 ?"Paid":"Not Paid");
		but1 = new JButton("Pay");
		if(ispaid==0){
		pan7.add(but1);
		}
		else if(ispaid!=0){
		//Add close button
		}
		main.add(pan7);
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
				InvoiceObject inv = new InvoiceObject(Integer.parseInt(a7.getText()), Double.parseDouble(a2.getText()),Integer.parseInt(a3.getText()),receivedDate, 1);
				inv.setId(id);
				invoiceDMO.put(inv);
				//text file buildlinvoicw file
				setVisible(false);
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null, "Incorrect Date format");
				}
			}
		}
	}
}
