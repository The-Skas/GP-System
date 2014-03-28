/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Prescriptions;

import framework.GPSISFramework;
import framework.GPSISPopup;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import object.Medicine;

/**
 *
 * @author Ozhan Azizi
 */
public class ViewDescription extends GPSISPopup implements ActionListener{
    
    
    private JTextArea displayDescriptionFld;   
    
    
    public ViewDescription(Medicine m)
    {
        super("Description");
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new MigLayout());
        this.setBackground(new Color(240, 240, 240));
        this.setSize(400, 600);
        
	JPanel h = new JPanel(new MigLayout());	
	JLabel hTitle = new JLabel("Description");
	GPSISFramework.getInstance();
//	hTitle.setFont(GPSISFramework.getFonts().get("Roboto").deriveFont(24f));
        h.add(hTitle, new CC().wrap());
			
	this.add(h, new CC().wrap());  
        
	JPanel descriptionForm = new JPanel(new MigLayout());         
        
        
                JPanel description = new JPanel();
                JLabel displayDescriptionLbl = new JLabel("Description: ");
                description.add(displayDescriptionLbl);
                this.displayDescriptionFld = new JTextArea(m.getDescription(),5,20);   
                this.displayDescriptionFld.setLineWrap(true);
                
                descriptionForm.add(displayDescriptionFld, new CC().wrap());
                
        this.add(descriptionForm, new CC().wrap());
        
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);                
                
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
