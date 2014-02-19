package module.Referral;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import mapper.ConsultantDMO;
import mapper.SpecialityTypeDMO;
import object.ConsultantObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class AddSpecialistType extends JFrame {
	private JLabel lab1,lab2,lab3;
	private JButton bu1,but2,but3,but4;
	private JTextArea tlb1,tlb2;
	private JScrollPane sp;
	private JComboBox jcb;
	private ArrayList<String> arr1 = new ArrayList<String>();
	private String[] array;
	private JCheckBox jc,jc2;
	private int check = 0;
	private List<SpecialityTypeObject> set1;
	private int ID;
	
	public AddSpecialistType(int ID){
		setLayout(new FlowLayout());
		Event e = new Event();
		
		this.ID = ID;
		//Populate dropdown
		SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		SpecialityTypeObject r = new SpecialityTypeObject();
		try {
			set1 = specialityTypeDMO.getAll();
		} catch (EmptyResultSetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(SpecialityTypeObject x:set1){
			arr1.add(x.getName());
		}
		//taking out duplicate entrys
		HashSet hset = new HashSet();
		hset.addAll(arr1);
		arr1.clear();
		arr1.addAll(hset);
		//sort arraylist here
		Collections.sort(arr1);
		//Add Dropdown choices
		array = new String[arr1.size()];
		for(int i=0;i<arr1.size();i++){
			array[i] = arr1.get(i);
		}
		
		lab1 = new JLabel("Select Speciality: ");
		add(lab1);
		jcb = new JComboBox(array);
		add(jcb);
		
		lab2 = new JLabel("Add Speciality to list: ");
		add(lab2);
		tlb1 = new JTextArea(3,13);
		add(tlb1);
		
		but2 = new JButton("Add From List");
		add(but2);
		but2.setToolTipText("Choose from dropdown");
		but2.addActionListener(e);
		
		but4 = new JButton("Add From TextBox");
		add(but4);
		but4.setToolTipText("Enter Type into textbox");
		but4.addActionListener(e);
		
	
		
	}
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if((e.getSource()==but4)&&(tlb1.getText().length() !=0)){
				//Add speciality from textarea to speciality sql table.
				//Speciality get by name and add by select* from Speciality where 
				SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
				GPSISDataMapper.connectToDatabase();
				//Put in get consultantID once get works(where 4 is)
				SpecialityTypeObject r = new SpecialityTypeObject(tlb1.getText(), ID);
				specialityTypeDMO.put(r);
				JOptionPane.showMessageDialog(null, "Added");
				setVisible(false);
			}
			else if((e.getSource()==but4)&&(tlb1.getText().length() ==0)){
				JOptionPane.showMessageDialog(null, "Enter into text box or use Drop Down!");
			}
			if(e.getSource()==but2){
				SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
				GPSISDataMapper.connectToDatabase();
				//Put in get consultantID once get works(where 4 is)
				//Turn choice into String
				String s = (String) jcb.getSelectedItem();
				//Insert String as type argument
				SpecialityTypeObject r = new SpecialityTypeObject(s, ID);
				specialityTypeDMO.put(r);
				JOptionPane.showMessageDialog(null, "Added");
				setVisible(false);
				
			}
			//No longer needed
			/*if(e.getSource()==but3){
				
				AddSpecialistType as = new AddSpecialistType(ID);
				as.setVisible(true);
				as.setSize(300, 170);
				as.setTitle("Add Specialist type");
				setVisible(false);
				//close previous window upon opening this one
			}
			*/
		}
		
	}
}
