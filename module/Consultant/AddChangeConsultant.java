package module.Consultant;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import object.ConsultantObject;
import framework.GPSISDataMapper;
import mapper.ConsultantDMO;

//Only for consultants
public class AddChangeConsultant extends JFrame {
	private JLabel lab1,lab2,lab3;
	private JButton but1,but2,but3;
	private JTextArea jb;
	
	
	public AddChangeConsultant(){
		setLayout(new FlowLayout());
		Event e = new Event();
		
		lab1 = new JLabel("Search by ID: ");
		add(lab1);
		jb = new JTextArea(1,15);
		add(jb);
		//For break
		lab3 = new JLabel("                             ");
		but1 = new JButton("Search");
		add(but1);
		but1.setToolTipText("Search to 'delete', 'Edit' or 'Add Speciality' to a specialist");
		but1.addActionListener(e);
		
		but2 = new JButton("Add Consultant");
		add(but2);
		but2.addActionListener(e);
		
		
	}
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==but2){
				Consultant consul = new Consultant();
				consul.setVisible(true);
				consul.setTitle("Add Consultant");
				consul.setSize(260,230);	
			}
			else if(e.getSource()==but1){
				//passing in the id so if remove is selected, i can 'removeByID()'
				int id = 0;
				
				try{
					//Turns String into an int
					id = Integer.parseInt(jb.getText());
					ConsultantDMO consultantDMO = ConsultantDMO.getInstance();
					GPSISDataMapper.connectToDatabase();
					
					String s = consultantDMO.getById(id).getTitle();
					Consultant consul = new Consultant(id,consultantDMO.getById(id).getTitle(),consultantDMO.getById(id).getFName(),
							consultantDMO.getById(id).getLName(),consultantDMO.getById(id).getAddress(),consultantDMO.getById(id).getEmail(),
							consultantDMO.getById(id).getNum(),""+ consultantDMO.getById(id).getPrice());
					consul.setVisible(true);
					consul.setTitle("View Consultant");
					consul.setSize(260,300);	
			
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
		a.setVisible(true);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setSize(300,100);
		a.setTitle("Menu");
	}
	
	
}
