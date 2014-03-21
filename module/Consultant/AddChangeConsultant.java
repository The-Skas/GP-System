package module.Consultant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
	
	//Add/Change consultants
	public AddChangeConsultant(){
		
	Event e = new Event();
	//Add panel
	pan2 = new JPanel();
	add(pan2);
	pan1 = new JPanel();
	pan2.add(pan1,BorderLayout.CENTER);
	//Set panels layout
	pan2.setLayout(new GridBagLayout());
	
	//Menu bar with 
		bar1 = new JMenuBar();
		setJMenuBar(bar1);
		//Menu name
		men1 = new JMenu("Add/Remove/Edit Consultant");
		bar1.add(men1);
		//Menu item
		menitm = new JMenuItem("Add Consultant");
		men1.add(menitm);
		//Adding action listener for event
		menitm.addActionListener(e);
		
		//Text box to search by id
		lab1 = new JLabel("Search by ID: ");
		pan1.add(lab1);
		jb = new JTextField(15);
		
		pan1.add(jb);
		//For break
		lab3 = new JLabel("                                                                         ");
		//Search button
		but1 = new JButton("Search");
		pan1.add(but1);
		but1.setToolTipText("Search to 'delete', 'Edit' or 'Add Speciality' to a specialist");
		but1.addActionListener(e);
		
		pan2.add(pan1, new GridBagConstraints());
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		jb.setBorder(border);
		pan2.setBorder(BorderFactory.createTitledBorder("ADD/Remove/Search Consultant"));
		pan1.setBorder(BorderFactory.createEtchedBorder());
		
	}
	//Action listener class
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Adding consultant
			if(e.getSource()==menitm){
				//Creating consultant add window
				Consultant consul = new Consultant();
				consul.setVisible(true);
				consul.setTitle("Add Consultant");
				consul.setSize(600,490);	
				//Setting the window to the middle of screen
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (int) ((dimension.getWidth() - consul.getWidth()) / 3);
				int y = (int) ((dimension.getHeight() - consul.getHeight()) / 4);
				consul.setLocation(x+200, y+80);
			}
			//If search is pressed and the text area doesn't equal 0
			else if((e.getSource()==but1)&&(jb.getText().trim().length()!=0)){
				int id = 0;
				try{
					//Turns String into an int and if it fails an exception will be thrown/task will not be carried out
					id = Integer.parseInt(jb.getText());
					ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
					
					ConsultantObject obj = consultantDMO.getById(id);
					Consultant consul = new Consultant(id,obj);
					consul.setVisible(true);
					consul.setTitle("View Consultant");
					consul.setSize(600,559);	
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					int x = (int) ((dimension.getWidth() - consul.getWidth()) / 3);
					int y = (int) ((dimension.getHeight() - consul.getHeight()) / 4);
					consul.setLocation(x+200, y+90);
					
				//Caught exception
				}catch(Exception exep){
					JOptionPane.showMessageDialog(null, "Incorrect data/Doesnt exist");
				}
				
			}
			
		}
		
	}
}
