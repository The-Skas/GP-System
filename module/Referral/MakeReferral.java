package module.Referral;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

import mapper.ConsultantDMO;
import mapper.PaymentDMO;
import mapper.ReferralDMO;
import mapper.SpecialityTypeDMO;
import module.Consultant.Consultant.Event;
import object.*;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class MakeReferral extends JFrame {
	private JComboBox combo1;
	//Temp holding for drop-down list
	private ArrayList<String> choicesA = new ArrayList<String>();
	//Choices for drop-down list
	private String[] choicesB;
	private JLabel lab1, lab2, lab3,sp,sp2,findinv,findinv2,space1,space2,space3,space4,space5,space6;
	private JButton but1, but2, but3;
	private JTextField text1, text2;
	private int con = 0,pat = 0,pay = 0,conId = 0; 
	private Double price = 0.0;
	private JPanel Main, pan1,pan2,pan3,pan4,pan5,pan6;
	private JMenu men;
	private JMenuItem itm;
	private JMenuBar mb;
	
	//GUI for Make Referral form
	public MakeReferral(){
		Border border = BorderFactory.createLineBorder(Color.BLACK);
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
		pan4 = new JPanel();
		space4 = new JLabel("                                                                                                                                           ");
		pan4.add(space4);
		Main.add(pan4);
				
		//Connecting to database in GPSISMapper class
		
		SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		try {
	
			List<SpecialityTypeObject> set1 = specialityTypeDMO.getAll();
			ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
		    
			List<ConsultantObject> set2 = consultantDMO.getAll();
			
			for(SpecialityTypeObject x:set1){
				int temp = x.getConID();
				
				for(ConsultantObject y:set2){
					
					if(y.isActive()==0){
						choicesA.add(x.getName());
					}
				}
			}
			
		} catch (EmptyResultSetException e1) {
			e1.printStackTrace();
		}
		

		//taking out duplicate entry's by putting them into a hash-set 
		HashSet hset = new HashSet();
		//Add all of array-list
		hset.addAll(choicesA);
		choicesA.clear();
		choicesA.addAll(hset);
		
		//sort array-list here
		Collections.sort(choicesA);
		
		//Add Drop-down choices
		choicesB = new String[choicesA.size()];
		for(int i=0;i<choicesA.size();i++){
			choicesB[i] = choicesA.get(i);
		}
		//Combo Box
		pan1 = new JPanel();
		lab1 = new JLabel("             Choose Type: ");
		pan1.add(lab1);
		combo1 = new JComboBox(choicesB);
		pan1.add(combo1);
		combo1.addActionListener(e);
		combo1.setToolTipText("Consultant type");
		space1 = new JLabel("            ");
		pan1.add(space1);
		pan1.setBorder(border);
		Main.add(pan1);
		pan1.setBorder(BorderFactory.createEtchedBorder());
		
		//Name
		pan2 = new JPanel();
		lab2 = new JLabel("                                                           Enter Staff ID: ");
		pan2.add(lab2);
		text1 = new JTextField(10);
		pan2.add(text1);
		space2 = new JLabel("                                                            ");
		pan2.add(space2);
		text1.setBorder(border);
		pan2.setBorder(BorderFactory.createEtchedBorder());
		Main.add(pan2);
		
		//Add Patient ID
		pan3 = new JPanel();
		lab3 = new JLabel("                                                       Enter Patient ID: ");
		pan3.add(lab3);
		text2 = new JTextField(10);
		text2.setBorder(border);
		pan3.add(text2);
		space3 = new JLabel("                                                            ");
		pan3.add(space3);
		pan3.setBorder(BorderFactory.createEtchedBorder());
		Main.add(pan3);
		
		//Spacing
		pan5 = new JPanel();
		space5= new JLabel("                                                                                                                                                                                       ");
		pan5.add(space5);
		Main.add(pan5);
		
		//Add Create Button
		but1 = new JButton("CREATE");
		Main.add(but1);
		but1.addActionListener(e);
		
	}
	public class Event implements ActionListener{
		//Events handler
		@Override
		public void actionPerformed(ActionEvent e) {
			//Create a referral
			if(e.getSource()== but1){
				//Making sure the text fields contain data
				if((text1.getText().length() >=1)&&(text2.getText().length() >=1)){
				//Connects to database
				ReferralDMO referralDMO = ReferralDMO.getInstance();
				GPSISDataMapper.connectToDatabase();
				//Creates Date
				Calendar cal = Calendar.getInstance();
				java.util.Date dt = cal.getTime();
				//Turns strings to integers
					try
					{
						//Catch exception (if text-field doesn't equal a real number (no characters))
						try{
						//ConID Search finding a consultant suiting the selected drop-down item
						String select = (String) combo1.getSelectedItem();
						//Connect to database
						SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
						GPSISDataMapper.connectToDatabase();
						SpecialityTypeObject r2 = new SpecialityTypeObject();
						List<SpecialityTypeObject> set1  = specialityTypeDMO.getAll();
						//Enhanced for loop iterating through the set returned from the database
						for(SpecialityTypeObject x: set1){
							if(x.getName().equals(select)){
								//Selecting an id from the suited consultant
								con = x.getConID();
							}
						}
						}catch(Exception eee){
							//Pop-up message telling user there is no consultant available for their problems
							JOptionPane.showMessageDialog(null,  "No Consultant matching Speciaity");
						}
						//make invoice so can get invoice id = overwrite it in invoice passing through inv id
						//and payment
						//Have to convert boolean to tiny int (1 and zero)
				    	ReferralObject r = new ReferralObject(dt, Integer.parseInt(text1.getText()), con, Integer.parseInt(text2.getText()),1);//last 2 have to be different
				    	referralDMO.put(r);
				    	//Prints out id number to use for adding Speciality's
				    	JOptionPane.showMessageDialog(null, "Your Referral ID is: "+ r.getId());
						//Create payment GUI Form
				    	
						
						Payment r2 = new Payment(r.getId(),con);
				    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
						int x = (int) ((dimension.getWidth() - r2.getWidth()) / 3);
						int y = (int) ((dimension.getHeight() - r2.getHeight()) / 4);
						r2.setLocation(x+60, y+50);
						r2.setVisible(true);
						r2.setTitle("Payment");
						r2.setSize(600, 360);
						
						//Turn string to an integer willing that it is correct data (otherwise caught in catch)
						pat = Integer.parseInt(text2.getText());
					}
					catch (NumberFormatException nfe)
					{
						JOptionPane.showMessageDialog(null,  "BAD DATA");
					}		
			}
			
				//Catches if data entered into text fields is null if so the corresponding pop-up is shown
				else if(text1.getText().length() <1){
						JOptionPane.showMessageDialog(null, "Enter Your ID!");
				}
				else if(text2.getText().length() <1){
						JOptionPane.showMessageDialog(null, "Enter Patients ID!");
				}
		}
	}
}
	//Testing
	public static void main(String[] args){
		
		//Creates Date
		Calendar cal = Calendar.getInstance();
		java.util.Date dt = cal.getTime();
		ReferralDMO referralDMO = ReferralDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		ReferralObject r = new ReferralObject(dt, 6, 8, Integer.parseInt("566"),1);
    	referralDMO.put(r);
    	System.out.print(r.getId());
    	
		
	}
	
}
