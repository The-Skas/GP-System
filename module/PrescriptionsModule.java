/**
 * 
 */
package module;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModuleMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import module.Prescriptions.RenewPrescriptions;
import module.Prescriptions.createPrescriptions;

public class PrescriptionsModule extends GPSISModuleMain implements ActionListener {
    
         JPanel pane = new JPanel();
        JPanel pane2 = new JPanel();
        JButton press1 = new JButton("Renew prescription");
        JButton press2 = new JButton("Create prescription");
        JLabel answer = new JLabel("");
	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
		JPanel prescriptionsModuleView = new JPanel(new GridBagLayout());
		
		/*JLabel greeting = new JLabel("This is the Prescriptions Module Main View in module/PrescriptionsModule.java!");
			greeting.setFont(new Font("Serif", Font.BOLD, 24));
			GridBagConstraints gbC = new GridBagConstraints();
			gbC.anchor = GridBagConstraints.CENTER;
			prescriptionsModuleView.add(greeting, gbC);*/
  
                
                prescriptionsModuleView.add(pane);
            
                press1.setMnemonic('P');
                pane.add(press1);
                pane.requestFocus();

                press2.setMnemonic('P');
                pane.add(press2);
                pane.requestFocus();       

                press1.addActionListener(this);
                pane.add(answer);
                pane.add(press1);
                press1.requestFocus();


                press2.addActionListener(this);
                pane.add(answer);
                pane.add(press2);
                press2.requestFocus();
		
                
                System.out.println("YAYAY");
		return prescriptionsModuleView;
	}

    @Override
    public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();
            System.out.println("Action Performed:");
            if (source == press1)   
            {
                new RenewPrescriptions();
            }
            else if(source == press2)
            {
                System.out.println("Create Presc");
                new createPrescriptions();
       // JOptionPane.showMessageDialog(null,"I hear you 2!","Creating new prescritpion",
      //  JOptionPane.PLAIN_MESSAGE); 
       // setVisible(true);  // show something                
            }
            
        }


}
