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
import java.util.regex.Pattern;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import mapper.SQLBuilder;
import module.Patient.PatientATM;
import module.StaffMember.StaffMemberATM;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.Patient;
import object.PermanentPatient;
import module.Patient.EditPatient;

public class PatientModule extends GPSISModuleMain implements ActionListener, ListSelectionListener                                                  
{
    public static Pattern validator = Pattern.compile("[@$%^&*()<>|\\\\//+]"); // etc: write all characters you need.
    public static JTable patientTable;
    public static TableRowSorter<PatientATM> sorter;
    //Used for filterng.
    private List<Patient> patients;
    private JTextField textQuery;
    private JButton modifyPatientBtn;
    private JButton viewPatientBtn;
    
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getActionCommand() == "Add")
        {
                new AddPatient();
        }
        else if(ae.getActionCommand().equals("Edit"))
        {
            System.out.println("Clicking");
            int row = this.patientTable.getSelectedRow();
            if(row != -1)
            {
                 System.out.print("and inside!");
                //Since filtering changes row numbering in views 
                //we get the value of the index through the model
                row= this.patientTable.convertRowIndexToModel(row);
                new EditPatient(patients.get(row));
            }
        }
        else if(ae.getActionCommand().equals("View"))
        {
            int row = this.patientTable.getSelectedRow();
            if(row != -1)
            {
                 System.out.print("and inside!");
                //Since filtering changes row numbering in views 
                //we get the value of the index through the model
                row= this.patientTable.convertRowIndexToModel(row);
                new ViewPatient(patients.get(row));
            }
        }
    }
	@Override
	public JPanel getModuleView() {
		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
			
			
			// Table View
			JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
                        PatientModule.patientTable = PatientModule.buildPatientsTable();
                        this.patients = ((PatientATM)patientTable.getModel()).getData();
                        patientTable.getSelectionModel().addListSelectionListener(this);
			leftPanel.add(new JScrollPane(patientTable), new CC().span().grow());
                        patientView.add(leftPanel, new CC().span().grow());
                        
                        textQuery = new JTextField();
			patientView.add(textQuery, new CC().span().grow().dockSouth());

			// Controls (RIGHT PANE)
			JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
				JButton addPatient = new JButton("Add Patient");
					addPatient.addActionListener(this);
					addPatient.setActionCommand("Add");
				rightPanel.add(addPatient, new CC().wrap());
				
					modifyPatientBtn = new JButton("Edit Patient");
					modifyPatientBtn.addActionListener(this);
					modifyPatientBtn.setActionCommand("Edit");
					modifyPatientBtn.setVisible(false);
				rightPanel.add(modifyPatientBtn, new CC().wrap());
				
                                        viewPatientBtn = new JButton("View Patient");
                                        viewPatientBtn.addActionListener(this);
                                        viewPatientBtn.setActionCommand("View");
                                        viewPatientBtn.setVisible(true);
                                rightPanel.add(viewPatientBtn);
			patientView.add(rightPanel, new CC().dockEast());
                
                
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
                return patientView;
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
            sorter = new TableRowSorter<PatientATM>(pATM);
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
            this.patientTable.clearSelection();
            this.modifyPatientBtn.setVisible(false);
            RowFilter<PatientATM, Object> rf = null;
            List<RowFilter<Object,Object>> rfs = 
                new ArrayList<RowFilter<Object,Object>>();

            try {
                String text = textQuery.getText();
                String[] textArray = text.split(" ");

                for (int i = 0; i < textArray.length; i++) {
                    System.out.println(textArray[i]);
                rfs.add(RowFilter.regexFilter("(?i)^" + textArray[i]+".*"));
                }

                rf = RowFilter.andFilter(rfs);

            }catch (java.util.regex.PatternSyntaxException e) {
                return;
            }

            sorter.setRowFilter(rf);
        }

     @Override
    public void valueChanged(ListSelectionEvent e) {
        if(PatientModule.patientTable.getSelectedRow() != -1)
        {
            this.modifyPatientBtn.setVisible(true);
            this.viewPatientBtn.setVisible(true);
        }
        else
        {
            this.modifyPatientBtn.setVisible(false);
            this.viewPatientBtn.setVisible(false);
        }
    }
    
    
     public static void main(String [] args)
    {
        GPSISDataMapper.connectToDatabase();
        PatientModule patientM = new PatientModule();

        patientM.getModuleView();
    }


}
