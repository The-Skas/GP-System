package module.Referral;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

import mapper.ConsultantDMO;
import mapper.SpecialityTypeDMO;
import module.Consultant.Consultant.Event;
import object.ConsultantObject;
import object.SpecialityTypeObject;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;

public class AddSpecialistType extends JFrame {
	private JLabel lab1,lab2,lab3,space1,space2,space3,space4,BigSpace1,BigSpace2;
	private JButton bu1,but2,but3,but4;
	private JTextArea tlb1,tlb2;
	private JScrollPane sp;
	private JComboBox jcb;
	private ArrayList<String> arr1 = new ArrayList<String>();
	private String[] array;
	private JCheckBox jc,jc2;
	private int check = 0;
	private List<SpecialityTypeObject> set1;
	private int ConID;
	private JPanel pan1,pan2,pan3,pan4,pan5,pan6,pan7, Main,SpacingPan,SpacingPan2;
	private JMenu men;
	private JMenuItem itm;
	private JMenuBar mb;
	
	public AddSpecialistType(int ConID){
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Event e = new Event();
		this.ConID = ConID;
		//Menu
		mb = new JMenuBar();
		setJMenuBar(mb);
		men = new JMenu("File");
		mb.add(men);
		
		//Main Panel
		Main = new JPanel();
		Main.setBorder(BorderFactory.createTitledBorder("Add Specialist Type"));
		Main.setLayout(new FlowLayout());
		add(Main);
		
		//Set ID for later use
		
		//Spacing Panel
		SpacingPan = new JPanel();
		BigSpace1 = new JLabel("                                                                                              ");
		SpacingPan.add(BigSpace1);
		Main.add(SpacingPan);
		
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
						arr1.add(x.getName());
					}
				}
			}
			
		} catch (EmptyResultSetException e1) {
			e1.printStackTrace();
		}
		
		//taking out duplicate entries
		HashSet hset = new HashSet();
		hset.addAll(arr1);
		arr1.clear();
		arr1.addAll(hset);
		
		//sort array-list here
		Collections.sort(arr1);
		
		//Add Drop-down choices
		array = new String[arr1.size()];
		for(int i=0;i<arr1.size();i++){
			array[i] = arr1.get(i);
		}
		
		//Drop-down box
		pan1 = new JPanel();
		space1 = new JLabel("                                                             ");
		pan1.add(space1);
		lab1 = new JLabel("Select Speciality: ");
		pan1.add(lab1);
		jcb = new JComboBox(array);
		pan1.add(jcb);
		pan1.setBorder(BorderFactory.createEtchedBorder());
		space1 = new JLabel("                                                             ");
		pan1.add(space1);
		Main.add(pan1);
		
		//Text box entry
		pan2 = new JPanel();
		space4 = new JLabel("                                  ");
		pan2.add(space4);
		lab2 = new JLabel("Add Speciality to list: ");
		pan2.add(lab2);
		tlb1 = new JTextArea(5,19);
		tlb1.setBorder(border);
		pan2.add(tlb1);
		pan2.setBorder(BorderFactory.createEtchedBorder());
		space2 = new JLabel("                                  ");
		pan2.add(space2);
		Main.add(pan2);
		tlb1.setSize( tlb1.getPreferredSize() );
		
		
		//Spacing Panel 2 
		SpacingPan2 = new JPanel();
		BigSpace2 = new JLabel("                                                                                                                                                      ");
		SpacingPan2.add(BigSpace2);
		Main.add(SpacingPan2);
				
		
		//Add from list
		but2 = new JButton("Add From List");
		Main.add(but2);
		but2.setToolTipText("Choose from dropdown");
		but2.addActionListener(e);
		
		//Add from Text box
		but4 = new JButton("Add From TextBox");
		Main.add(but4);
		but4.setToolTipText("Enter Type into textbox");
		but4.addActionListener(e);
		
	
		
	}
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if((e.getSource()==but4)&&(tlb1.getText().length() !=0)){
				//Add speciality from text-area to speciality sql table.
				//Speciality get by name and add by select* from Speciality where 
				SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
				//Put in get consultantID once get works(where 4 is)
				SpecialityTypeObject r = new SpecialityTypeObject(tlb1.getText().trim(), ConID);
				specialityTypeDMO.put(r);
				JOptionPane.showMessageDialog(null, "Added");
				setVisible(false);
			}
			else if((e.getSource()==but4)&&(tlb1.getText().length() ==0)){
				JOptionPane.showMessageDialog(null, "Enter into text box or use Drop Down!");
			}
			if(e.getSource()==but2){
				SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
				//Put in get consultantID once get works(where 4 is)
				//Turn choice into String
				String s = (String) jcb.getSelectedItem();
				//Insert String as type argument
				SpecialityTypeObject r = new SpecialityTypeObject(s, ConID);
				specialityTypeDMO.put(r);
				JOptionPane.showMessageDialog(null, "Added");
				setVisible(false);
				
				
			}
		}
		
	}
	public static void main(String[] args){
		
		SpecialityTypeDMO specialityTypeDMO = SpecialityTypeDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		SpecialityTypeObject r = new SpecialityTypeObject("Heart", 6);
		specialityTypeDMO.put(r);
		try {
			ArrayList<SpecialityTypeObject> ar = (ArrayList<SpecialityTypeObject>) specialityTypeDMO.getAll();
			for(SpecialityTypeObject x: ar){
				System.out.print(x.getName());
			}
		} catch (EmptyResultSetException e) {
			e.printStackTrace();
		}
	}
}
