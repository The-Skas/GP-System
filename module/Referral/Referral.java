package module.Referral;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import javax.swing.*;

import mapper.ConsultantDMO;
import mapper.InvoiceDMO;
import mapper.ReferralDMO;
import mapper.SpecialityTypeDMO;
import object.ConsultantObject;
import object.InvoiceObject;
import object.ReferralObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class Referral extends JFrame {
	
	private JComboBox combo1;
	//Choices for dropdown box
	private String[] choices = {"Referral ID", "Invoice ID"};
	private JLabel lab1,tab1,tab2,tab3,find1,l;
	private JButton but1, but2, but3, but4, but5, find2;
	private JTextArea text1,find3;
	private JMenuBar jb;
	
		public Referral(){
			setLayout(new FlowLayout());
			//For the actionListener
			Event e = new Event();
			//Spacing label
			l = new JLabel("                                ");   
			add(l);
			
			//Combo box (and populating it)
			combo1 = new JComboBox(choices);
			//Hovering text
			combo1.setToolTipText("Select Type");
			add(combo1);
			combo1.addActionListener(e);
			
			//Spacing
			tab1 = new JLabel("                              ");
			add(tab1);
			
			//Label, Text-box and Button to find referral
			lab1 = new JLabel("Find Referral:");
			add(lab1);
			text1 = new JTextArea(1,10);
			add(text1);
			but1 = new JButton("Search");
			add(but1);
			but1.addActionListener(e);
			but1.setToolTipText("Change Search Type with Dropdown");
			
			//Label, Text-box and Button to find invoice
			find1 = new JLabel("  Find Invoice:");
			add(find1);
			find3 = new JTextArea(1,10);
			add(find3);
			find2 = new JButton("Search");
			add(find2);
			find2.addActionListener(e);
			find2.setToolTipText("Enter ID selected from dropdown");
			//but4 = new JButton("Add Invoice");
			//add(but4);
			//but4.addActionListener(e);
			
			//Outstanding invoice button
			but2 = new JButton("Outstanding Invoice");
			add(but2);
			but2.addActionListener(e);
			//Make referral Button
			but3 = new JButton("Make Referral");
			add(but3);
			but3.addActionListener(e);
			//Spacing label
			tab2 = new JLabel("                                            ");
			add(tab2);
			text1.setEditable(true);

		}
		public class Event implements ActionListener{
			//Action Listener for actions performed
			@Override
			public void actionPerformed(ActionEvent e) {
				//Button to make referral
				if(e.getSource()==but3){
					MakeReferral r = new MakeReferral();
			    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			    	//Centre Window on screen
					int x = (int) ((dimension.getWidth() - r.getWidth()) / 3);
					int y = (int) ((dimension.getHeight() - r.getHeight()) / 4);
					r.setLocation(x+110, y+260);
					r.setVisible(true);
					r.setTitle("Make Referral");
					r.setSize(240, 190);
				}
				//Button to find referral, the if statement also determines if the corresponding-
				//-text box is empty or not
				else if((e.getSource()== but1)&&(text1.getText().length()>=1)){
					
					String selection = (String)combo1.getSelectedItem();
					//Search when the drop-down selection = Referral ID.
					if(selection.equals("Referral ID")){
						String searchValue = text1.getText();
						//Insert number at the end as the other method has 2 string arguments
						try{
							//Connecting to database
							ReferralDMO referralDMO = ReferralDMO.getInstance();
							GPSISDataMapper.connectToDatabase();
							//Make a referralObject called r2 (using parseInt to turn text to an int)
							ReferralObject r2 = referralDMO.getById(Integer.parseInt(searchValue));
							//The GUI DetailReferral is constructed
							DetailsReferral r = new DetailsReferral(selection, searchValue,r2);
							r.setVisible(true);
							r.setTitle("Found Referral");
							r.setSize(320, 200);
							//Catch errors
							}catch(NumberFormatException eee){
								//Pop-up message
								JOptionPane.showMessageDialog(null, "ERROR");
								eee.printStackTrace();
							} catch (EmptyResultSetException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				}
				//For empty textbox
				else if((e.getSource()== but1)&&(text1.getText().length()<1)){
					//Pop-up message telling user to enter value if text-box is empty and but1 is pressed
					JOptionPane.showMessageDialog(null, "Please enter value");
				}
				//Outstanding invoice button
				else if(e.getSource()== but2){
					//Create a date object
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date(0);
				    Calendar cal = Calendar.getInstance();
				    String temp =dateFormat.format(cal.getTime());
				    //removes time so returns just date
				    temp = temp.substring(0,10);
				    //passing in the argument temp.With the date i can return all the outstanding payments in invoice
					OutStandingInvoice r = new OutStandingInvoice();
					r.setVisible(true);
					r.setTitle("Outstanding Invoice");
					r.setSize(300, 80);
				}
				//Finding an invoice (to pay or view)
				else if(e.getSource()==find2){
					/*find3.getText();
					InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
					GPSISDataMapper.connectToDatabase();*/
					//Construct invoice GUI by id set in text-box (find3.getText())
					int iden = 0;
					try{
						iden = Integer.parseInt(find3.getText());
						InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
						GPSISDataMapper.connectToDatabase();
						InvoiceObject obj = invoiceDMO.getById(iden);
						Invoice i = new Invoice(find3.getText(),obj.getRefID(),obj.getAmount(),obj.getConID(),obj.getIsPaid());
						i.setSize(300, 200);
						i.setVisible(true);
						i.setTitle("OutStanding");
						String tester = ""+obj.getRefID()+" "+obj.getAmount()+" "+obj.getConID()+" "+obj.getIsPaid();
						System.out.print(tester);
					}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Not Correct Data");
					}
					//find3 is turned to in in invoice (parseInt(String)
				}
			}
		}
		public static void main(String[] args){
			//Start	
		
	    	Referral refer = new Referral();
	    	//Centring the window on screen 
	    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - refer.getWidth()) / 3);
			int y = (int) ((dimension.getHeight() - refer.getHeight()) / 4);
			refer.setLocation(x+70, y+85);
			refer.setVisible(true);
			refer.setTitle("Referral");
			refer.setSize(320, 160);
			//Closes all windows after referral main window is closed
			refer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			refer.setResizable(false);
		
		}
}

