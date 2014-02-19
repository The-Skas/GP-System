package module.Consultant;
import java.awt.FlowLayout;

import object.SpecialityTypeObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import mapper.ConsultantDMO;
import mapper.SQLBuilder;
import mapper.SpecialityTypeDMO;
import module.Referral.AddSpecialistType;
import object.ConsultantObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class Consultant extends JFrame{
	private JLabel lab1,lab2,lab3,lab4,lab5,lab6,lab7,lab8,lab9;
	private JButton but1,but2,but3;
	private JButton butt1,butt2,butt3,butt4;
	private String[] speciality;
	private JTextArea jb1,jb2,jb3,jb4,jb5,jb6,jb7,jb8,jb9;
	private int counter = 0;
	private int ID;
	
	public Consultant(int s, String title, String fn, String ln, String add, String email, String conn,String price){
		setLayout(new FlowLayout());
		Event e = new Event();
		
		this.ID = s;
		lab1 = new JLabel("Title: ");
		add(lab1);
		jb1 = new JTextArea(1,15);
		add(jb1);
		jb1.setEditable(false);
		jb1.setText(title);
		lab2 = new JLabel("First Name: ");
		add(lab2);
		jb2 = new JTextArea(1,15);
		add(jb2);
		jb2.setEditable(false);
		jb2.setText(fn);
		lab3 = new JLabel("Last Name: ");
		add(lab3);
		jb3 = new JTextArea(1,15);
		add(jb3);
		jb3.setEditable(false);
		jb3.setText(ln);
		lab4 = new JLabel("Address: ");
		add(lab4);
		jb4 = new JTextArea(1,15);
		add(jb4);
		jb4.setEditable(false);
		jb4.setText(add);
		lab5 = new JLabel("Email: ");
		add(lab5);
		jb5 = new JTextArea(1,15);
		add(jb5);
		jb5.setEditable(false);
		jb5.setText(email);
		lab6 = new JLabel("Contact num: ");
		add(lab6);
		jb6 = new JTextArea(1,13);
		add(jb6);
		jb6.setEditable(false);
		jb6.setText(conn);
		
		lab8 = new JLabel("Price: ");
		add(lab8);
		jb8 = new JTextArea(1,13);
		add(jb8);
		jb8.setEditable(false);
		jb8.setText(price);
		
		lab9 = new JLabel("Specialitys: ");
		add(lab9);
		jb9 = new JTextArea(2,13);
		add(jb9);
		//Search speciality by id and make a string a combination of the specialitys and add them to the jb9
		String spec = "";
		SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		List<SpecialityTypeObject> tempset;
		try {
			tempset = specialityTypeDMO.getAll();
			for(SpecialityTypeObject x:tempset){
				if(ID == x.getConID()){
					spec += x.getName() + ", ";
				}
			}
		} catch (EmptyResultSetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		jb9.setText(spec);
		jb9.setEditable(false);
		
		
		butt1 = new JButton("Edit");
		add(butt1);
		butt1.addActionListener(e);
		
		butt3 = new JButton("Save");
		add(butt3);
		butt3.addActionListener(e);
		
		butt2 = new JButton("Delete");
		add(butt2);
		butt2.addActionListener(e);
		
		butt4 = new JButton("Add Speciality: ");
		add(butt4);
		butt4.addActionListener(e);
		
	}
	
	public Consultant(){
		setLayout(new FlowLayout());
		Event e = new Event();
		
		lab1 = new JLabel("Title: ");
		add(lab1);
		jb1 = new JTextArea(1,15);
		add(jb1);
		
		lab2 = new JLabel("First Name: ");
		add(lab2);
		jb2 = new JTextArea(1,15);
		add(jb2);
		
		lab3 = new JLabel("Last Name: ");
		add(lab3);
		jb3 = new JTextArea(1,15);
		add(jb3);
		
		lab4 = new JLabel("Address: ");
		add(lab4);
		jb4 = new JTextArea(1,15);
		add(jb4);
		
		lab5 = new JLabel("Email: ");
		add(lab5);
		jb5 = new JTextArea(1,15);
		add(jb5);
		
		lab6 = new JLabel("Contact num: ");
		add(lab6);
		jb6 = new JTextArea(1,13);
		add(jb6);
		
		lab8 = new JLabel("Price: ");
		add(lab8);
		jb8 = new JTextArea(1,13);
		add(jb8);
		
		but1 = new JButton("Add");
		add(but1);
		but1.addActionListener(e);
		
		
		lab7 = new JLabel();
		add(lab7);
	}
	public class Event implements ActionListener{

		@Override
		
		public void actionPerformed(ActionEvent e) {
			counter = 0;
			//Editing
		if((e.getSource()==butt1)){
		
				jb1.setEditable(true);
				jb2.setEditable(true);
				jb3.setEditable(true);
				jb4.setEditable(true);
				jb5.setEditable(true);
				jb6.setEditable(true);
				jb8.setEditable(true);
				//As i add speciality through a droplist
				jb9.setEditable(false);
			}
		if((e.getSource()==but1)&&(counter<1)){
				counter+=1;
				double amo =0;
			
				try{
				amo= Double.parseDouble(jb8.getText().trim());
			
				ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
				GPSISDataMapper.connectToDatabase();
				//have to convert boolean to tiny int
				ConsultantObject r = new ConsultantObject(jb1.getText(), jb2.getText(), jb3.getText(), jb4.getText(),
														  jb5.getText(), jb6.getText(), amo);
				consultantDMO.put(r);
				//Return messege with added consultants ID
				JOptionPane.showMessageDialog(null, "Consultant ("+ r.getFName().toUpperCase() + " "+ r.getLName().toUpperCase() + "'s) ID: "+ r.getId());
				setVisible(false);
				
				//Close current window here
				//JOptionPane.showMessageDialog(null,""+consultantDMO.getById(4).getEmail());
				
				}catch(NumberFormatException eee){
						JOptionPane.showMessageDialog(null, "Please Enter valid data" );
						setVisible(false);
				}
		}
		//Saving
		if((e.getSource()==butt3)&&(counter<1)){
			//need to update as not making new entry every time just changing info
			ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
			GPSISDataMapper.connectToDatabase();
			double tNum = Double.parseDouble(jb8.getText());
			ConsultantObject r = new ConsultantObject(jb1.getText(),jb2.getText(),jb3.getText(),jb4.getText(),jb5.getText(),jb6.getText(),tNum);
			
			consultantDMO.put(r);
			
		}
		
        //Removed by ID
		
		if(e.getSource()==butt2){
			JOptionPane.showMessageDialog(null, "Deleted" );
			ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
			GPSISDataMapper.connectToDatabase();
			consultantDMO.removeById(ID);
			butt2.setText("Removed");
			setVisible(false);
			//close current window here 

		}
		if(e.getSource()==butt4){
			//SpecialityDMO specialityDMO = SpecialityDMO.getInstance();
			//GPSISDataMapper.connectToDatabase();
			//SpecialityObject r = new SpecialityObject();
			AddSpecialistType as = new AddSpecialistType(ID);
			as.setVisible(true);
			as.setSize(300, 170);
			as.setTitle("Add Specialist type");
			//Hide window
			setVisible(false);
		}
	}
}
}
