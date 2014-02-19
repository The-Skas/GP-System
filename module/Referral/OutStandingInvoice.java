package module.Referral;

	import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import mapper.InvoiceDMO;
import mapper.SpecialityTypeDMO;
import object.InvoiceObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

	public class OutStandingInvoice extends JFrame {
		private JLabel lab1,lab2,lab3,lab4,lab5, lab6, lab7, lab8;
		private JTextArea a1,a2,a3,a4,a5, a7;
		private JButton but1,but2, but7;
		private JComboBox combo;
		private int counter = 0;
		private String[] arr1;
		private List<InvoiceObject> set1;
		
		public OutStandingInvoice(){
			setLayout(new FlowLayout());
			Event e = new Event();
			 	InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
				GPSISDataMapper.connectToDatabase();
				List<InvoiceObject> set1;
				try {
					set1 = invoiceDMO.getAll();
					for(InvoiceObject x:set1){
						System.out.print(x.getIsPaid());
					}
					this.set1 = set1;
				} catch (EmptyResultSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
			lab8 = new JLabel("Choose ID: ");
			add(lab8);
			arr1 = fillArray();
			combo = new JComboBox(arr1);
			add(combo);
			combo.addActionListener(e);
			
			
			but7 = new JButton("Get");
			add(but7);
			but7.addActionListener(e);
			
		}
		
		public Date addDays(Date d, int days)
		    {
		        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
		        return d;
		    }
		public String[] fillArray(){
			ArrayList<String> arr2 = new ArrayList<String>();
			//getOutsIv inherited from dmo
			for(InvoiceObject x: set1){
				if(x.getIsPaid()==0){//0 = false
					String temp = ""+x.getId();
					arr2.add(temp);
				}
			}
			arr1 = new String[arr2.size()];
			for(int i=0; i<arr2.size();i++){
				arr1[i] = "" + arr2.get(i);
			}
			return arr1;
		}
	
		public class Event implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if((e.getSource()== but7)&&(arr1.length>=1)){
				String com = (String) combo.getSelectedItem();
				//com is ID
				int iden = 0;
					try{
						iden = Integer.parseInt(com);
						InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
						GPSISDataMapper.connectToDatabase();
						InvoiceObject obj = invoiceDMO.getById(iden);
						Invoice i = new Invoice(com,obj.getRefID(),obj.getAmount(),obj.getConID(),obj.getIsPaid());
						i.setSize(300, 200);
						i.setVisible(true);
						i.setTitle("OutStanding");
						setVisible(false);
					}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Not Correct Data");
					}
				}
				//So if there is no outstanding invoices no exceptions will be thrown
				else if((e.getSource()== but7)&&(arr1.length<1)){
					JOptionPane.showMessageDialog(null,  "No outstanding invoices!");
				}
			}
			
		}
		
}
