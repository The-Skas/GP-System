/**
 * 
 */
package module;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLayeredPane;
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
	private JLabel space,space2,space3,space4,space5;
	private Patient p = null;
	private JButton search;
	
	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		Event e = new Event();
		JPanel specialistReferralsModuleView = new JPanel(new FlowLayout());
		
		JLabel greeting = new JLabel("This is module/SpecialistReferralsModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			GridBagConstraints gbC2 = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			gbC2.anchor = GridBagConstraints.SOUTHWEST;
			specialistReferralsModuleView.setBorder(BorderFactory.createTitledBorder("Referrals"));
			
			addChanCon = new JButton("Add/Change Consultant");
			addChanCon.addActionListener(e);
			space = new JLabel("                                                                                                                                                                                      ");
			MakeRef = new JButton("Make/View Referrals");
			MakeRef.addActionListener(e);
			specialistReferralsModuleView.add(addChanCon,gbC);
			specialistReferralsModuleView.add(MakeRef,gbC2);
			specialistReferralsModuleView.setBorder(BorderFactory.createBevelBorder(0));
			
			search = new JButton("Search For patient ID");
			specialistReferralsModuleView.add(space,gbC);
			specialistReferralsModuleView.add(search);
			search.addActionListener(e);
			search.setToolTipText("returns ID");

			
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
					refer.setResizable(false);
				} catch (EmptyResultSetException e1) {
					e1.printStackTrace();
				}
				
			}
			else if(e.getSource()== search){
				
				try {
					 p = module.Patient.SearchPane.doSearch();
					 JOptionPane.showMessageDialog(null, p.getFirstName() + "'s ID: " + p.getId());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
			
		}
		
		
	}

}
