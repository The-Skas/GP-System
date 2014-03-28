package module.Referral;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

import mapper.ConsultantDMO;
import mapper.InvoiceDMO;
import mapper.ReferralDMO;
import mapper.SpecialityTypeDMO;
import module.Consultant.Consultant.Event;
import object.ConsultantObject;
import object.InvoiceObject;
import object.ReferralObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import exception.UserDidntSelectException;
import framework.GPSISDataMapper;
import java.awt.HeadlessException;

//Main window for referrals option
public class Referral extends JFrame {
	
	//Creating variables
	private JComboBox combo1;
	//Choices for dropdown box
	private String[] choices = {"Referral ID", "Invoice ID"};
	private JLabel lab1,tab1,tab2,tab3,find1,space1,space2,space3,space4,space5,space6,space7,space8,space9,space10;
	private JButton but1, but2, but3, but4, but5, find2;
	private JTextField text1,find3;
	private JMenuBar mb;
	private JMenuItem itm;
	private JMenu men;
	private JPanel pan1,pan2,pan3,pan4,pan5,pan6,pan7,Main;
	private JList list;
	
		public Referral() throws EmptyResultSetException{
			//Border line colour
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			//ActionListener class
			Event e = new Event();
			
			//Menu
			mb = new JMenuBar();
			setJMenuBar(mb);
			men = new JMenu("File");
			mb.add(men);
			
			//Main Panel
			Main = new JPanel();
			add(Main);
			Main.setBorder(BorderFactory.createTitledBorder("Referral"));
			Main.setLayout(new FlowLayout());
			
			//Spacing
			pan1 = new JPanel();
			space2 = new JLabel("                                                                                       ");
			pan1.add(space2);
			Main.add(pan1);

			space1 = new JLabel("                                                                                                                                                             ");
			pan2 = new JPanel();
			pan2.add(space1);
			Main.add(pan2);
			
			//Label, Text-box and Button to find referral
			pan4 = new JPanel();
			space3 = new JLabel("                                               ");
			pan4.add(space3);
			lab1 = new JLabel("Find Referral:");
			pan4.add(lab1);
			text1 = new JTextField(10);
			pan4.add(text1);
                        text1.setEnabled(false);
			text1.setBorder(border);
			but1 = new JButton("Search");
			pan4.add(but1);
			space4 = new JLabel("                                                ");
			pan4.add(space4); 
			but1.addActionListener(e);
			//but1.setToolTipText("Change Search Type with Dropdown");
			pan4.setBorder(BorderFactory.createEtchedBorder());
			Main.add(pan4);
			
			//Label, Text-box and Button to find invoice
			pan5 = new JPanel();
			space4 = new JLabel("                                               ");
			pan5.add(space4);
			find1 = new JLabel("  Find Invoice:");
			pan5.add(find1);
			find3 = new JTextField(10);
			pan5.add(find3);
			find3.setBorder(border);
			find2 = new JButton("Search");
			pan5.add(find2);
			space5 = new JLabel("                                                ");
			pan5.add(space5);
			find2.addActionListener(e);
			find2.setToolTipText("Enter ID selected from dropdown");
			pan5.setBorder(BorderFactory.createEtchedBorder());
			Main.add(pan5);
			
			//Spacing
			pan6 = new JPanel();
			space6 = new JLabel("                                                                                             ");
			pan6.add(space6);
			space7 = new JLabel("                                                                                             ");
			pan6.add(space7);
			space8 = new JLabel("                                                                                             ");
			pan6.add(space8);
			space9 = new JLabel("                                                                                             ");
			pan6.add(space9);
			space10 = new JLabel("                                                                                       ");
			pan6.add(space10);
			Main.add(pan6);
			
			//Outstanding invoice button
			pan7 = new JPanel();
			but2 = new JButton("Outstanding Invoice");
			pan7.add(but2);
			but2.addActionListener(e);
			//Make referral Button
			but3 = new JButton("     Make Referral     ");
			pan7.add(but3);
			but3.addActionListener(e);
			Main.add(pan7);
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
					r.setLocation(x-50, y-10);
					r.setVisible(true);
					r.setTitle("Make Referral");
					r.setSize(600, 350);
				}
				
				//Button to find referral, the if statement also determines if the corresponding-
				//-text box is empty or not
				else if(e.getSource()== but1){
					
					//String selection = (String)combo1.getSelectedItem();
					//Search when the drop-down selection = Referral ID.
					
						String searchValue = text1.getText();
                                                
						//Insert number at the end as the other method has 2 string arguments
						try{
//							//Connecting to database
//							ReferralDMO referralDMO = ReferralDMO.getInstance();
//							// 
//							//Make a referralObject called r2 (using parseInt to turn text to an int)
							ReferralObject r2 = module.Referral.SearchPane.doSearch();
//							//The GUI DetailReferral is constructed
							DetailsReferral r = new DetailsReferral(r2.getId()+"",r2);
							Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					    	//Centre Window on screen
						
							int x = (int) ((dimension.getWidth() - r.getWidth()) / 3);
							int y = (int) ((dimension.getHeight() - r.getHeight()) / 4);
							r.setLocation(x, y);
							r.setVisible(true);
							r.setTitle("Found Referral");
							r.setSize(600, 350);
							//Catch errors
							}catch(UserDidntSelectException | EmptyResultSetException | HeadlessException ex){
								//Pop-up message
                                                                System.out.println("Error is"+ex.getMessage());
								JOptionPane.showMessageDialog(null, "ERROR");
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
			
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			    	//Centre Window on screen
					int x = (int) ((dimension.getWidth() - r.getWidth()) / 3);
					int y = (int) ((dimension.getHeight() - r.getHeight()) / 4);
					r.setLocation(x+100, y+100);
					r.setVisible(true);
					r.setTitle("Outstanding Invoice");
					r.setSize(300, 155);
				}
				//Finding an invoice (to pay or view)
				else if(e.getSource()==find2){
					
					int iden = 0;
					try{
						iden = Integer.parseInt(find3.getText());
						InvoiceDMO invoiceDMO = InvoiceDMO.getInstance();
						// 
						InvoiceObject obj = invoiceDMO.getById(iden);
						Invoice i = new Invoice(find3.getText(),obj.getRefID(),obj.getAmount(),obj.getConID(),obj.getIsPaid());
						i.setSize(600, 350);
						i.setVisible(true);
						i.setTitle("Invoice");
						String tester = ""+obj.getRefID()+" "+obj.getAmount()+" "+obj.getConID()+" "+obj.getIsPaid();
						Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				    	//Centre Window on screen
						int x = (int) ((dimension.getWidth() - i.getWidth()) / 3);
						int y = (int) ((dimension.getHeight() - i.getHeight()) / 4);
						i.setLocation(x+100, y+100);
					}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Not Correct Data");
					}
					//find3 is turned to in in invoice (parseInt(String)
				}
			}
		}
		public static void main(String[] args) throws EmptyResultSetException{
			//Start	
	    	Referral refer = new Referral();
	    	//Centring the window on screen 
	    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - refer.getWidth()) / 3);
			int y = (int) ((dimension.getHeight() - refer.getHeight()) / 4);
			refer.setLocation(x-50, y-10);
			refer.setVisible(true);
			refer.setTitle("Referral");
			refer.setSize(600,350);
			//Closes all windows after referral main window is closed
			refer.setResizable(false);
		
		}
}

