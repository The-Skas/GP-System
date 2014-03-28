package module.Consultant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import object.ConsultantObject;
import framework.GPSISDataMapper;
import mapper.ConsultantDMO;

//Only for consultants
public class AddChangeConsultant extends JFrame {
	private JLabel lab1,lab2,lab3,lab4;
	private JButton but1,but2,but3;
	private JTextField jb;
	private JMenu men1;
	private JMenuItem menitm;
	private JMenuBar bar1;
	private JPanel pan1, pan2,pan3;
	
	public AddChangeConsultant(){
		
	Event e = new Event();
	//Below change
	pan2 = new JPanel();
	add(pan2);
	pan1 = new JPanel();
	pan2.add(pan1,BorderLayout.CENTER);
	
	pan2.setLayout(new GridBagLayout());
	
		bar1 = new JMenuBar();
		setJMenuBar(bar1);
		men1 = new JMenu("Add/Remove Consultant");
		bar1.add(men1);
		menitm = new JMenuItem("Add/Change Consultant");
		men1.add(menitm);
		menitm.addActionListener(e);
		
		
		lab1 = new JLabel("Search by ID: ");
		pan1.add(lab1);
		jb = new JTextField(15);
		
		pan1.add(jb);
		//For break
		lab3 = new JLabel("                                                                         ");
		but1 = new JButton("Search");
		pan1.add(but1);
		but1.setToolTipText("Search to 'delete', 'Edit' or 'Add Speciality' to a specialist");
		but1.addActionListener(e);
		
		but2 = new JButton("Add Consultant");
		//pan3.add(but2);
		but2.addActionListener(e);
		
		pan2.add(pan1, new GridBagConstraints());
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		jb.setBorder(border);
		pan2.setBorder(BorderFactory.createTitledBorder("ADD/Remove/Search Consultant"));
		pan1.setBorder(BorderFactory.createEtchedBorder());
	
		
	}
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==menitm){
				Consultant consul = new Consultant();
				consul.setVisible(true);
				consul.setTitle("Add Consultant");
				consul.setSize(600,490);	
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (int) ((dimension.getWidth() - consul.getWidth()) / 3);
				int y = (int) ((dimension.getHeight() - consul.getHeight()) / 4);
				consul.setLocation(x+200, y+80);
			}
			else if((e.getSource()==but1)&&(jb.getText().trim().length()!=0)){
				//passing in the id so if remove is selected, i can 'removeByID()'
				int id = 0;
				
				try{
					//Turns String into an int
					id = Integer.parseInt(jb.getText());
					ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
					
					String s = consultantDMO.getById(id).getTitle();
					Consultant consul = new Consultant(id,consultantDMO.getById(id).getTitle(),consultantDMO.getById(id).getFName(),
							consultantDMO.getById(id).getLName(),consultantDMO.getById(id).getAddress(),consultantDMO.getById(id).getEmail(),
							consultantDMO.getById(id).getNum(),""+ consultantDMO.getById(id).getPrice());
					consul.setVisible(true);
					consul.setTitle("View Consultant");
					consul.setSize(600,559);	
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					int x = (int) ((dimension.getWidth() - consul.getWidth()) / 3);
					int y = (int) ((dimension.getHeight() - consul.getHeight()) / 4);
					consul.setLocation(x+200, y+90);
			
				}catch(Exception exep){
					JOptionPane.showMessageDialog(null, "Incorrect data/Doesnt exist");
				}
				
			}
			
		}
		
	}
	public static void main(String[] args){
		
		ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
		GPSISDataMapper.connectToDatabase();
		List<ConsultantObject> se = consultantDMO.getAll();
		
		for(ConsultantObject x:se){
		System.out.print(x.getId());
		}
		
		AddChangeConsultant a = new AddChangeConsultant();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - a.getWidth()) / 3);
		int y = (int) ((dimension.getHeight() - a.getHeight()) / 4);
		a.setLocation(x, y);
		a.setVisible(true);
		a.setSize(600,350);
		a.setTitle("Menu");
	}
	
}
