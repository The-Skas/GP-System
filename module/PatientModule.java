/**
 * 
 */
package module;

import module.*;
import module.Patient.*;
import exception.EmptyResultSetException;
import framework.GPSISDataMapper;
import framework.GPSISFramework;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import framework.GPSISModuleMain;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import mapper.PatientDMO;
import module.Patient.PatientATM;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import object.Patient;

public class PatientModule extends GPSISModuleMain implements ActionListener, ListSelectionListener                                                  
{
    public static Pattern validator = Pattern.compile("[@$%^&*()<>|\\\\//+]"); // etc: write all characters you need.
    public static JTable patientTable;
    public static TableRowSorter<PatientATM> sorter;
    //Used for filterng.
    private List<Patient> patients;
    private JTextField textQuery;
    private JButton viewPatientBtn;
    private JButton delPatientBtn;
    
    public static boolean iscurrentUserNotAllowed()
    {
       return (!"Receptionist".equals(currentUser.getRole())
               &&!currentUser.isOfficeManager());
    }
    public void actionPerformed(ActionEvent ae) 
    {
        
        if(ae.getActionCommand() == "Add")
        {
                new AddPatient();
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
        else if(ae.getActionCommand().equals("Delete"))
        {
            int row = this.patientTable.getSelectedRow();
            if(row != -1)
            {
                 System.out.print("and inside!");
                //Since filtering changes row numbering in views 
                //we get the value of the index through the model
                row= this.patientTable.convertRowIndexToModel(row);
                
                PatientATM pATM =(PatientATM)this.patientTable.getModel();
                Patient p = pATM.getData().get(row);
                PatientDMO.getInstance().removeById(p.getId());
                pATM.removeRow(p);
                                pATM.getData().remove(p);

            }
        }
    }
	@Override
	public JPanel getModuleView() {
		JPanel patientView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
                    textQuery = new HintTextField("Search", 30);
			// Table View
                    JPanel leftPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
                        leftPanel.add(textQuery, new CC().span().wrap());

                        PatientModule.patientTable = PatientModule.buildPatientsTable();
                        this.patients = ((PatientATM)patientTable.getModel()).getData();
                        patientTable.getSelectionModel().addListSelectionListener(this);
			leftPanel.add(new JScrollPane(patientTable), new CC().span().grow());
                        patientView.add(leftPanel, new CC().span().grow());
                        

			// Controls (RIGHT PANE)
			JPanel rightPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
				
                                JButton addPatient = new JButton("Add Patient");
					addPatient.addActionListener(this);
					addPatient.setActionCommand("Add");
				rightPanel.add(addPatient, new CC().wrap());
				
                                        viewPatientBtn = new JButton("View/Edit Patient");
                                        viewPatientBtn.addActionListener(this);
                                        viewPatientBtn.setActionCommand("View");
                                        viewPatientBtn.setVisible(false);
                                rightPanel.add(viewPatientBtn, new CC().wrap());
                                
                                        delPatientBtn = new JButton("X");
                                        delPatientBtn.setForeground(Color.red);
                                        delPatientBtn.addActionListener(this);
                                        delPatientBtn.setActionCommand("Delete");
                                        delPatientBtn.setVisible(false);
                                rightPanel.add(delPatientBtn);
			patientView.add(rightPanel, new CC().dockEast());
                
                        
                        //Check if can edit
                        if(GPSISFramework.getCurrentUser().getRole() !="Receptionist"
                            &&!GPSISFramework.getCurrentUser().isOfficeManager() ) {
                                delPatientBtn.setEnabled(false);
                                addPatient.setEnabled(false);
                            }
                
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
            //init patientTable
            PatientModule.patientTable = table;
            return table;
        }
        
         private void newFilter() 
         {
            this.patientTable.clearSelection();
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
            this.viewPatientBtn.setVisible(true);
            this.delPatientBtn.setVisible(true);
        }
        else
        {
            this.viewPatientBtn.setVisible(false);
            this.delPatientBtn.setVisible(false);
        }

    }
    
    
     public static void main(String [] args)
    {
        GPSISDataMapper.connectToDatabase();
        PatientModule patientM = new PatientModule();

        patientM.getModuleView();
    }


}
