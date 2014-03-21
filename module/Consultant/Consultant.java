package module.Consultant;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import object.SpecialityTypeObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

import mapper.ConsultantDMO;
import mapper.SQLBuilder;
import mapper.SpecialityTypeDMO;
import module.Referral.AddSpecialistType;
import object.ConsultantObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class Consultant extends JFrame{
	//Class variables
	private JLabel lab1,lab2,lab3,lab4,lab5,lab6,lab7,lab8,lab9,lab10,lab11,lab12,lab13,AccountName,AccountNum,SortNum;
	private JLabel space1, space2, space3, space4, space5, space6, space7,space8,space9,space10,space11;
	private JButton but1,but2,but3;
	private JButton butt1,butt2,butt3,butt4;
	private String[] speciality;
	private JTextField jb1,jb2,jb3,jb4,jb5,jb6,jb7,jb8,jb9,AccountNa,AccountNu,SortNu;
	//private JTextArea jb9;
	private int counter = 0;
	private int ID;
	private JPanel pan1,pan2,pan3, pan4, pan5, pan6, pan7, pan8, pan9,pan10,pan11,pan12;
	private JMenuBar mb;
	private JMenu men;
	private JMenuItem itm;
	private ConsultantObject object;
	
	//Consultant constructor
	public Consultant(int s, ConsultantObject obj){
		//Setting border line colour
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Event e = new Event();
		object = obj;
		
		//Menu
		mb = new JMenuBar();
		setJMenuBar(mb);
		men = new JMenu("File");
		mb.add(men);
		
		//ID
		this.ID = s;
		
		//Main Panel
		pan1 = new JPanel();
		add(pan1);
		pan1.setBorder(BorderFactory.createTitledBorder("ADD Speciality/Change Deatils of Consultant"));
		pan1.setLayout(new FlowLayout());
		
		//Title
		pan2 = new JPanel();
		pan1.add(pan2);
		lab1 = new JLabel("Title: ");
		pan2.add(lab1);
		space1 = new JLabel("                                                                                                                ");
		pan2.add(space1);
		jb1 = new JTextField(15);
		pan2.add(jb1);
		jb1.setEditable(false);
		//Text box set to string corresponding to object passed in
		jb1.setText(obj.getTitle());
		jb1.setBorder(border);
		pan2.setBorder(BorderFactory.createEtchedBorder());
		
		//FirstName
		pan3 = new JPanel();
		pan1.add(pan3);
		lab2 = new JLabel("First Name: ");
		pan3.add(lab2);
		space2 = new JLabel("                                                                                                    ");
		pan3.add(space2);
		jb2 = new JTextField(15);
		pan3.add(jb2);
		jb2.setEditable(false);
		jb2.setText(obj.getFName());
		jb2.setBorder(border);
		pan3.setBorder(BorderFactory.createEtchedBorder());
		
		//LastName
		pan4 = new JPanel();
		pan1.add(pan4);
		lab3 = new JLabel("Last Name: ");
		pan4.add(lab3);
		space3 = new JLabel("                                                                                                    ");
		pan4.add(space3);
		jb3 = new JTextField(15);
		pan4.add(jb3);
		jb3.setEditable(false);
		jb3.setText(obj.getLName());
		jb3.setBorder(border);
		pan4.setBorder(BorderFactory.createEtchedBorder());
		
		//Address
		pan5 = new JPanel();
		pan1.add(pan5);
		lab4 = new JLabel("Address: ");
		pan5.add(lab4);
		space4 = new JLabel("                                                                                                        ");
		pan5.add(space4);
		jb4 = new JTextField(15);
		pan5.add(jb4);
		jb4.setEditable(false);
		jb4.setText(obj.getAddress());
		jb4.setBorder(border);
		pan5.setBorder(BorderFactory.createEtchedBorder());
	
		//Email
		pan6 = new JPanel();
		pan1.add(pan6);
		lab5 = new JLabel("Email: ");
		pan6.add(lab5);
		space5 = new JLabel("                                                                                                              ");
		pan6.add(space5);
		jb5 = new JTextField(15);
		pan6.add(jb5);
		jb5.setEditable(false);
		jb5.setText(obj.getEmail());
		jb5.setBorder(border);
		pan6.setBorder(BorderFactory.createEtchedBorder());
	
	
		//Contact Number
		pan7 = new JPanel();
		pan1.add(pan7);
		lab6 = new JLabel("Contact Number: ");
		pan7.add(lab6);
		space6 = new JLabel("                                                                                          ");
		pan7.add(space6);
		jb6 = new JTextField(15);
		pan7.add(jb6);
		jb6.setEditable(false);
		jb6.setText(obj.getNum());
		jb6.setBorder(border);
		pan7.setBorder(BorderFactory.createEtchedBorder());
		
		//Price
		pan8 = new JPanel();
		pan1.add(pan8);
		lab8 = new JLabel("Price: ");
		pan8.add(lab8);
		space7 = new JLabel("                                                                                                             ");
		pan8.add(space7);
		jb8 = new JTextField(15);
		pan8.add(jb8);
		jb8.setEditable(false);
		jb8.setText(""+obj.getPrice());
		jb8.setBorder(border);
		pan8.setBorder(BorderFactory.createEtchedBorder());
		
		//Account Name
		pan10 = new JPanel();
		pan1.add(pan10);
		AccountName = new JLabel("Account Name");
		pan10.add(AccountName);
		space9 = new JLabel("                                                                                                ");
		pan10.add(space9);
		AccountNa = new JTextField(15);
		AccountNa.setText(obj.getAccName());
		pan10.add(AccountNa);
		AccountNa.setEditable(false);
		AccountNa.setBorder(border);
		pan10.setBorder(BorderFactory.createEtchedBorder());
		
		//Account Number
		pan11 = new JPanel();
		pan1.add(pan11);
		AccountNum = new JLabel("Account Number: ");
		pan11.add(AccountNum);
		space10 = new JLabel("                                                                                          ");
		pan11.add(space10);
		AccountNu = new JTextField(15);
		AccountNu.setText(""+obj.getAccNum());
		pan11.add(AccountNu);
		AccountNu.setEditable(false);
		AccountNu.setBorder(border);
		pan11.setBorder(BorderFactory.createEtchedBorder());
		
		//SortCode
		pan12 = new JPanel();
		pan1.add(pan12);
		SortNum = new JLabel("Sort Code: ");
		pan12.add(SortNum);
		space11 = new JLabel("                                                                                                       ");
		pan12.add(space11);
		SortNu = new JTextField(15);
		SortNu.setText(""+obj.getSortCode());
		pan12.add(SortNu);
		SortNu.setEditable(false);
		SortNu.setBorder(border);
		pan12.setBorder(BorderFactory.createEtchedBorder());
		
		//Specialities
		pan9 = new JPanel();
		pan1.add(pan9);
		lab9 = new JLabel("Speciality Type(s): ");
		pan9.add(lab9);
		space8 = new JLabel("                                                                                        ");
		pan9.add(space8);
		jb9 = new JTextField(15);
		pan9.add(jb9);
		jb9.setEditable(false);
		jb9.setBorder(border);
		pan9.setBorder(BorderFactory.createEtchedBorder());
	
		//Search speciality by id and make a string a combination of the speciality's and add them to the jb9 text box
		String spec = "";
		SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
		//Adding speciality to text box (so is displayed with consultant details )
		List<SpecialityTypeObject> tempset;
		
		try {
			tempset = specialityTypeDMO.getAll();
			for(SpecialityTypeObject x:tempset){
				if(ID == x.getConID()){
					spec += x.getName() + ", ";
				}
			}
		} catch (EmptyResultSetException e1) {
			e1.printStackTrace();
		}
		
		jb9.setText(spec);
		jb9.setEditable(false);
		
		//Telling user if consultant is active or not 
		JLabel Active = new JLabel("");
		pan1.add(Active);
		if(obj.isActive()!=0){
			String act = "NOT ACTIVE";
			Active.setText(act);
		}
		else if(obj.isActive()==0){
			
			//Edit
			butt1 = new JButton("Edit");
			pan1.add(butt1);
			butt1.addActionListener(e);
			
			//Save
			butt3 = new JButton("Save");
			pan1.add(butt3);
			butt3.addActionListener(e);
			
			//Delete
			butt2 = new JButton("Delete");
			pan1.add(butt2);
			butt2.addActionListener(e);
			
			//Add
			butt4 = new JButton("Add Speciality: ");
			pan1.add(butt4);
			butt4.addActionListener(e);
			
		}
		
	}
	
	//Constructor 2
	public Consultant(){
		
		//Creating line border
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Event e = new Event();
		
		//Menu
		mb = new JMenuBar();
		setJMenuBar(mb);
		men = new JMenu("File");
		mb.add(men);
		
		//Main panel
		pan1 = new JPanel();
		add(pan1);
		//Creating titled border for main panel
		pan1.setBorder(BorderFactory.createTitledBorder("ADD Consultant"));
		pan1.setLayout(new FlowLayout());
		
		//Title
		pan2 = new JPanel();
		pan1.add(pan2);
		pan2.setBorder(BorderFactory.createEtchedBorder());
		lab1 = new JLabel("Title:");
		pan2.add(lab1);
		lab2 = new JLabel("                                                                                                                ");
		pan2.add(lab2);
		jb1 = new JTextField(15);
		pan2.add(jb1);
		jb1.setBorder(border);
		
		//FirstName
		pan3 = new JPanel();
		pan1.add(pan3);
		pan3.setBorder(BorderFactory.createEtchedBorder());
		lab4 = new JLabel("FName:");
		pan3.add(lab4);
		lab4 = new JLabel("                                                                                                           ");
		pan3.add(lab4);
		jb2 = new JTextField(15);
		pan3.add(jb2);
		jb2.setBorder(border);
		
		//SecondName
		pan4 = new JPanel();
		pan1.add(pan4);
		pan4.setBorder(BorderFactory.createEtchedBorder());
		lab5 = new JLabel("LName:");
		pan4.add(lab5);
		lab6 = new JLabel("                                                                                                           ");
		pan4.add(lab6);
		jb3 = new JTextField(15);
		pan4.add(jb3);
		jb3.setBorder(border);
		
		//Address
		pan5 = new JPanel();
		pan1.add(pan5);
		pan5.setBorder(BorderFactory.createEtchedBorder());
		lab6 = new JLabel("Address:");
		pan5.add(lab6);
		lab7 = new JLabel("                                                                                                         ");
		pan5.add(lab7);
		jb4 = new JTextField(15);
		pan5.add(jb4);
		jb4.setBorder(border);
		
		//Email
		pan6 = new JPanel();
		pan1.add(pan6);
		pan6.setBorder(BorderFactory.createEtchedBorder());
		lab7 = new JLabel("Email:");
		pan6.add(lab7);
		lab8 = new JLabel("                                                                                                               ");
		pan6.add(lab8);
		jb5 = new JTextField(15);
		pan6.add(jb5);
		jb5.setBorder(border);
		
		//ContactNumber
		pan7 = new JPanel();
		pan1.add(pan7);
		pan7.setBorder(BorderFactory.createEtchedBorder());
		lab8 = new JLabel("Contact Number:");
		pan7.add(lab8);
		lab9 = new JLabel("                                                                                           ");
		pan7.add(lab9);
		jb6 = new JTextField(15);
		pan7.add(jb6);
		jb6.setBorder(border);
		
		//Price
		pan8 = new JPanel();
		pan1.add(pan8);
		pan8.setBorder(BorderFactory.createEtchedBorder());
		lab9 = new JLabel("Price: ");
		pan8.add(lab9);
		lab10 = new JLabel("                                                                                                               ");
		pan8.add(lab10);
		jb7 = new JTextField(15);
		pan8.add(jb7);
		jb7.setBorder(border);
		
		//Account Name
		pan10 = new JPanel();
		pan1.add(pan10);
		AccountName = new JLabel("Account Name");
		pan10.add(AccountName);
		space9 = new JLabel("                                                                                                ");
		pan10.add(space9);
		AccountNa = new JTextField(15);
		pan10.add(AccountNa);
		//AccountNa.setEditable(false);
		AccountNa.setBorder(border);
		pan10.setBorder(BorderFactory.createEtchedBorder());
				
		//Account Number
		pan11 = new JPanel();
		pan1.add(pan11);
		AccountNum = new JLabel("Account Number: ");
		pan11.add(AccountNum);
		space10 = new JLabel("                                                                                          ");
		pan11.add(space10);
		AccountNu = new JTextField(15);
		pan11.add(AccountNu);
		//AccountNu.setEditable(false);
		AccountNu.setBorder(border);
		pan11.setBorder(BorderFactory.createEtchedBorder());
				
		//SortCode
		pan12 = new JPanel();
		pan1.add(pan12);
		SortNum = new JLabel("Sort Code: ");
		pan12.add(SortNum);
		space11 = new JLabel("                                                                                                       ");
		pan12.add(space11);
		SortNu = new JTextField(15);
		pan12.add(SortNu);
		//SortNu.setEditable(false);
		SortNu.setBorder(border);
		pan12.setBorder(BorderFactory.createEtchedBorder());
		
		//Action Listener
		but1 = new JButton("Add");
		pan1.add(but1);
		but1.addActionListener(e);
	
	}
	public class Event implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			counter = 0;
			
			//Editing
		if((e.getSource()==butt1)){
			
			//Set all the text field editable (apart from speciality)
				jb1.setEditable(true);
				jb2.setEditable(true);
				jb3.setEditable(true);
				jb4.setEditable(true);
				jb5.setEditable(true);
				jb6.setEditable(true);
				jb8.setEditable(true);
				AccountNa.setEditable(true);
				AccountNu.setEditable(true);
				SortNu.setEditable(true);
				//As i add speciality through a drop-list
				jb9.setEditable(false);
				
			}
		
		//Add consultant
		else if((e.getSource()==but1)&&(counter<1)){
			
				counter+=1;
				double amo =0;
				
				try{	
					if((AccountNa.getText().trim().length()>2)&&(AccountNu.getText().trim().length()==8)&&(SortNu.getText().trim().length()==6)&&(jb1.getText().trim().length()!=0)&&
							( jb2.getText().trim().length()!=0)&&( jb5.getText().trim().length()!=0)&&( jb3.getText().trim().length()!=0)&&( jb4.getText().trim().length()!=0)
							&&( jb6.getText().trim().length()!=0)){
						//Checking data can be converted if not will result in an exception thrown (bad data)
						amo= Double.parseDouble(jb7.getText().trim());
						ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
						
						String acc = (AccountNa.getText());
						int acc2 = Integer.parseInt(AccountNu.getText());
						int acc3 =Integer.parseInt(SortNu.getText());
						//have to convert boolean to tiny int
						
						//Constructing consultant
						ConsultantObject r = new ConsultantObject(jb1.getText(), jb2.getText(), jb3.getText(), jb4.getText(),
																  jb5.getText(), jb6.getText(), amo, acc, acc2,acc3,0);
						//Added consultant
						consultantDMO.put(r);
						
						//Return message with added consultants ID
						JOptionPane.showMessageDialog(null, "Consultant ("+ r.getFName().toUpperCase() + " "+ r.getLName().toUpperCase() + "'s) ID: "+ r.getId());
						//Set window not visible
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null, "Please enter correct data into fields");
					}
				
				}catch(NumberFormatException eee){
						JOptionPane.showMessageDialog(null, "Please Enter valid data" );
				}
		}
		//Saving
		else if((e.getSource()==butt3)&&(counter<1)){
			//need to update as not making new entry every time just changing info
			ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
			//Doing the same as above, making sue data is valid before updating consultant details
			try{
				double tNum = Double.parseDouble(jb8.getText());
				String acc = (AccountNa.getText());
				int acc2 = Integer.parseInt(AccountNu.getText());
				int acc3 =Integer.parseInt(SortNu.getText());
				
				//Set details to be over written
				ConsultantObject r = object;
				r.setTitle(jb1.getText());
				r.setFName(jb2.getText());
				r.setLName(jb3.getText());
				r.setAddress(jb4.getText());
				r.setEmail(jb5.getText());
				r.setNum(jb6.getText());
				r.setPrice(tNum);
				r.setAccName(acc);
				r.setAccNum(acc2);
				r.setSortCode(acc3);
				//set is still active 
				r.setisActive(0);
				//Update/overwrite entry
				consultantDMO.put(r);
				//Saved confirmation message
				JOptionPane.showMessageDialog(null, "Saved!");
				
			}catch(NumberFormatException eee){
				JOptionPane.showMessageDialog(null, "Please Enter valid data" );
			}
		}
		
        //making inactive
		if(e.getSource()==butt2){
			//Doesn't delete consultant details from database as referrals made contain details related. To combat this i have just made the
			//-consultant inactive once deleted.
			//Making sue the user wants to delete the consultant
			String string = JOptionPane.showInputDialog("Are you sure?(Y or y for yes)");
			if((string.equals("y"))||(string.equals("Y"))){
				//Deleted (inactive)
				JOptionPane.showMessageDialog(null, "Deleted" );
				ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
				ConsultantObject r = object;
				
				/*
				r.setAccName("INACTIVE");
				r.setAccNum(0);
				r.setPrice(0.0);
				r.setSortCode(0);
				*/
				
				r.setisActive(1);
				consultantDMO.put(r);
				setVisible(false);
			}
			
		}
		
		//Adding a speciality to the consultant
		else if(e.getSource()==butt4){
			
			//Construct window
			AddSpecialistType as = new AddSpecialistType(ID);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			//Centre the window
			int x = (int) ((dimension.getWidth() - as.getWidth()) / 3);
			int y = (int) ((dimension.getHeight() - as.getHeight()) / 4);
			as.setLocation(x, y);
			as.setVisible(true);
			as.setSize(600,350);
			as.setTitle("Add Specialist type");
			//Hide window
			setVisible(false);
		}
	}
}
}
