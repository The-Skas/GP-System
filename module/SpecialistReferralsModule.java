/**
 * 
 */
package module;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import object.ConsultantObject;
import object.StaffMember;
import mapper.ConsultantDMO;
import module.Consultant.AddChangeConsultant;
import module.Referral.Referral;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import framework.GPSISModuleMain;
import object.Patient;

public class SpecialistReferralsModule extends GPSISModuleMain {
	
	private JButton addChanCon, MakeRef;
	private JLabel space;
	private Patient p = null;
	private JButton search;
	
	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		Event e = new Event();
		JPanel specialistReferralsModuleView = new JPanel(new GridBagLayout());
		
		JLabel greeting = new JLabel("This is module/SpecialistReferralsModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			specialistReferralsModuleView.setBorder(BorderFactory.createTitledBorder("Referrals"));
			
			addChanCon = new JButton("Add/Change Consultant");
			addChanCon.addActionListener(e);
			space = new JLabel("            ");
			MakeRef = new JButton("Make/View Referrals");
			MakeRef.addActionListener(e);
			specialistReferralsModuleView.add(addChanCon,gbC);
			specialistReferralsModuleView.add(space,gbC);
			specialistReferralsModuleView.add(MakeRef,gbC);
			
			
			
			
		return specialistReferralsModuleView;
	}
	public class Event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==addChanCon){
				
				AddChangeConsultant a = new AddChangeConsultant();
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (int) ((dimension.getWidth() - a.getWidth()) / 3);
				int y = (int) ((dimension.getHeight() - a.getHeight()) / 4);
				a.setLocation(x, y);
				a.setVisible(true);
				a.setSize(600,350);
				a.setTitle("Menu");
				
			}
                        
			else if(e.getSource()==MakeRef){
				
				Referral refer;
				try {
					refer = new Referral();
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
				} catch (EmptyResultSetException e1) {
					e1.printStackTrace();
				}
				
			}
			
		}
		
		
	}

}
