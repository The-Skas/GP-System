/**
 * 
 */
package module;

import module.*;
import module.Patient.*;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModuleMain;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import mapper.SQLBuilder;
import module.Patient.PatientATM;
import module.StaffMember.StaffMemberATM;
import object.Patient;
import object.PermanentPatient;

public class PatientModule extends GPSISModuleMain implements ActionListener                                                  
{
    public static JTable patientTable;
    public static TableRowSorter<PatientATM> sorter;
    //Used for filterng.
    JTextField textQuery;
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getActionCommand() == "Add Patient")
        {
                new AddPatient();
        }
        else if(ae.getActionCommand() == "Edit Patient")
        {
            //I didnt think that far ahead and hence i need to
            //now figure out how to refrence the selected row
            //through the database. This means I need to have an id.
            int row = this.patientTable.getSelectedRow();
            if(row != -1)
            {
                System.out.println(this.patientTable.getModel().getValueAt(
                           row , 5
                ));
            }

        }
    }
	@Override
	public JPanel getModuleView() {
		JPanel patientModuleView = new JPanel(new GridBagLayout());
//                patientModuleView.setPreferredSize(new Dimension(800,600));
//                patientModuleView.setMinimumSize(new Dimension(800,600));
//		JLabel greeting = new JLabel("This is the Patient Module Main View in module/PatientModule.java!");
//			greeting.setFont(new Font("Serif", Font.BOLD, 24));
                GridBagConstraints gbC = new GridBagConstraints();
                patientModuleView.setBorder(BorderFactory.createLineBorder(Color.red));
                
                JPanel jp = new JPanel();
//                gbC.insets = new Insets(2,2,2,2);
		DefaultListModel listModel = new DefaultListModel();
                listModel.addElement("Cheese");
                
                JList list = new JList(listModel);
                    gbC.fill = GridBagConstraints.BOTH;
                    gbC.gridx = 0;
                    gbC.gridy = 0;
                    gbC.weightx = 0.3;
                    gbC.weighty = 1.0;
                JPanel jp1 = new JPanel();
                jp1.setBorder(BorderFactory.createLineBorder(Color.red));
                patientModuleView.add(jp1, gbC);
                textQuery = new JTextField();
                textQuery.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                        System.out.println("In insert.");
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();       
                        System.out.println("In remove.");

                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newFilter(); 
                        System.out.println("In update.");

                    }
                   
                });
                    gbC.fill = GridBagConstraints.HORIZONTAL;
                    gbC.gridx = 1;
                    gbC.gridy = 0;
                    gbC.weightx = 0.3;
                    gbC.weighty = 1.0;
                    gbC.insets = new Insets(0,65,0,65);
                patientModuleView.add(textQuery, gbC);
                    gbC.insets = new Insets(0,0,0,0);
                    gbC.gridx = 2;
                    gbC.gridy = 0;
                    gbC.weightx = 0.3;
                    gbC.weighty = 1.0;
                patientModuleView.add(new JPanel(), gbC);
                
                JScrollPane listScrollPane = new JScrollPane(list);
                    gbC.fill = GridBagConstraints.NONE;
                    gbC.gridx = 0;
                    gbC.gridy = 1;
                
                    gbC.weightx = 0.3;
                    gbC.weighty = 1.0;
                patientModuleView.add(listScrollPane, gbC);
//                gbC.anchor= GridBagConstraints.;
                    gbC.fill = GridBagConstraints.NONE;

                    gbC.gridx = 1;
                    gbC.gridy = 1;
                    gbC.weightx = 0.3;
                    gbC.weighty = 0;
                
                JScrollPane jscroll = new JScrollPane(this.buildPatientsTable());
                patientModuleView.add(jscroll, gbC);
               
                
                    
                //buttons
                JPanel buttonContainer = new JPanel();
                    JButton addButton = new JButton("Add Patient");
                    addButton.addActionListener(this);
                    buttonContainer.add(addButton);
                    JButton editButton = new JButton("Edit Patient");
                    editButton.addActionListener(this);
                    buttonContainer.add(editButton);
                    gbC.fill = GridBagConstraints.CENTER;
                    
                    gbC.gridx = 2;
                    gbC.gridy = 1;
                    gbC.weightx = 0.3;
                    gbC.weighty = 1.0;
                
                patientModuleView.add(buttonContainer,gbC);
                
		return patientModuleView;
	}
        
        
        //Skas:this would be more fitting in the DMO
        public static JTable buildPatientsTable() 
        {
            PatientATM pATM = null;
            try {            
                pATM = new PatientATM(patientDMO.getAll());
            } catch (EmptyResultSetException ex) {
                Logger.getLogger(PatientModule.class.getName()).log(Level.SEVERE, null, ex);
            }
            JTable table =  new JTable(pATM);
            sorter = new TableRowSorter<>(pATM);
            table.setRowSorter(sorter);
            table.getModel();
            
            PatientATM tempATM = (PatientATM)table.getModel();
            System.out.println(tempATM.columnNames[0]);
            //init patientTable
            PatientModule.patientTable = table;
            return table;
        }
        
         private void newFilter() 
         {
            RowFilter<PatientATM, Object> rf = null;
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
                return;
            }

            sorter.setRowFilter(rf);
        }

    
    
    
     public static void main(String [] args)
    {
        GPSISDataMapper.connectToDatabase();
        PatientModule patientM = new PatientModule();

        patientM.getModuleView();
    }


}
