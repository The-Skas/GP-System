 /**
 * 
 */
package module;

import javax.swing.JPanel;

import framework.GPSISModuleMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import module.Prescriptions.PrescriptionsATM;
import module.Prescriptions.RenewPrescriptions;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.Prescription;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import module.Prescriptions.AddNewPrescription;
import module.Prescriptions.ViewAndPrintPrescription;



public class PrescriptionsModule extends GPSISModuleMain implements ActionListener, ListSelectionListener {
    
// private static final long serialVersionUID = -8748112836660009010L; 
        
        JTextField spaceOut;
 	private static JTable prescriptionTable;       
	private static JButton modifyPrescriptionBtn; 
        private static JButton viewPrintButton;
        private static List<Prescription> prescriptions;
        private static PrescriptionsATM pM;
        public static TableRowSorter<PrescriptionsATM> sorter;
        JTextField textQuery; 
        
    @Override
	public void actionPerformed(ActionEvent ae) {

       if(ae.getActionCommand() == "Add")
        {
                new AddNewPrescription();
        }
        else if(ae.getActionCommand().equals("Renew"))
        {
           
            int row = this.prescriptionTable.getSelectedRow();
            if(row != -1)
            {
                row= this.prescriptionTable.convertRowIndexToModel(row);
                 Prescription p = prescriptions.get(row);
                 new RenewPrescriptions(p);
     //           Prescription p = prescriptions.get(prescriptionTable.getSelectedRow());
            }

        }
        else if(ae.getActionCommand().equals("View"))
       {
           int row = this.prescriptionTable.getSelectedRow();
           if(row != -1)
           {
               row= this.prescriptionTable.convertRowIndexToModel(row);

               Prescription p = prescriptions.get(row);
               new ViewAndPrintPrescription(p);
           }
       }
                      
	}    
        
    
	/* (non-Javadoc)
	 * @see framework.GPSISModuleMain#getModuleView()
	 */
	@Override
	public JPanel getModuleView() {
                                
        JPanel prescriptionModuleView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));                     

                spaceOut = new JTextField();
                spaceOut.setVisible(false);
                spaceOut.setEnabled(false);
                prescriptionModuleView.add(spaceOut, new CC().span().grow().dockNorth());
        
                // using the text query to input words for searching prescriptions.
                textQuery = new JTextField(20);
                textQuery.getDocument().addDocumentListener(
                new DocumentListener() {                                    
                   
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();

                    }

                    
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newFilter(); 

                    }       
                });
                                        
			prescriptionModuleView.add(textQuery, new CC().dockNorth());
                        
                        //Table View
                        JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
                        PrescriptionsModule.prescriptionTable = PrescriptionsModule.buildPrescriptionTable();
                        this.prescriptions = ((PrescriptionsATM)prescriptionTable.getModel()).getData();
                        prescriptionTable.getSelectionModel().addListSelectionListener(this);
                        leftPanel.add(new JScrollPane(prescriptionTable), new CC().span().grow());
                        prescriptionModuleView.add(leftPanel, new CC().span().grow());
                
              
                
                //buttons
                JPanel buttonContainer = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
                    JButton addButton = new JButton("Add Prescription");
                    addButton.addActionListener(this);
                    addButton.setActionCommand("Add");
                    buttonContainer.add(addButton, new CC().wrap());
                    
                    // Setting these two buttons not visible until a row
                    // has been selected. 
                    modifyPrescriptionBtn = new JButton("Renew Prescription");
                    modifyPrescriptionBtn.addActionListener(this);
                    modifyPrescriptionBtn.setActionCommand("Renew");
                    modifyPrescriptionBtn.setVisible(false);
                    buttonContainer.add(modifyPrescriptionBtn);
                    
                    viewPrintButton = new JButton("View Prescription");
                    viewPrintButton.addActionListener(this);
                    viewPrintButton.setActionCommand("View");
                    viewPrintButton.setVisible(false);
                    buttonContainer.add(viewPrintButton);
                    
                                    

              prescriptionModuleView.add(buttonContainer, new CC().dockEast());

                    return prescriptionModuleView; 
	}
        
        	public static List<Prescription> getPrescriptions()
	{
		return prescriptions;
	}
        
        	public static JTable getPrescriptionTable()
	{
            if(PrescriptionsModule.prescriptionTable == null)
            {
                PrescriptionsModule.prescriptionTable = buildPrescriptionTable();
            }
		return PrescriptionsModule.prescriptionTable;
	}        
        
                

                // building prescription table
	public static JTable buildPrescriptionTable()
	{
		
			prescriptions = prescriptionDMO.getAll();
						
			pM = new PrescriptionsATM(prescriptions);
			JTable sMT = new JTable (pM);
                        sorter = new TableRowSorter<PrescriptionsATM>(pM);
                        sMT.setRowSorter(sorter);
			return sMT;
               
        }
        
        // able to search for a prescription
         private void newFilter() 
         {
            RowFilter<PrescriptionsATM, Object> rf = null;
            List<RowFilter<Object,Object>> rfs = 
                new ArrayList<RowFilter<Object,Object>>();

            try {
                String text = textQuery.getText();
                String[] textArray = text.split(" ");

                for (int i = 0; i < textArray.length; i++) {
                rfs.add(RowFilter.regexFilter("(?i)" + textArray[i]));
                }

                rf = RowFilter.andFilter(rfs);
            }catch (java.util.regex.PatternSyntaxException e) {
                System.out.print("PatternError: "+e);
                return;
            }
            catch (Exception e) {
                System.out.print("Any Exc: "+e);
                return;
            }
            sorter.setRowFilter(rf);              
        }
         
         
         /// Once select a row from the table then able to
         // view prescription and renew prescription
         // by turning them visible.
         @Override
 	public void valueChanged(ListSelectionEvent e) {
        if(PrescriptionsModule.prescriptionTable.getSelectedRow() != -1)
        {
            this.modifyPrescriptionBtn.setVisible(true);
            this.viewPrintButton.setVisible(true);
        }
        else
        {
            this.modifyPrescriptionBtn.setVisible(false);
            this.viewPrintButton.setVisible(false);
        }
	}       
        
        
}
    



