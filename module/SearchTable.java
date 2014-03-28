/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module;

import framework.GPSISDataMapper;
import framework.GPSISPopup;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import module.Patient.AddPatient;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author skas
 */
public class SearchTable<T extends AbstractTableModel> extends GPSISPopup implements ActionListener{
    public JTable tbl;
    private TableRowSorter<T> sorter;
    private JTextField textQuery;
    private JButton buttonFld;
    private Broadcastable parent;
    private int selectedRow;
    /*
     * This constructor takes in a Broadcastable object, this allows the class
     * to be able to communicate with the parent container that intialised it.
     * Additionally, it takes in a JTable to create the table, and a TableRowSorter
     * to create the search implementation.
    */
    
    public SearchTable(final Broadcastable parent,final JTable tbl, String title) 
    {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new MigLayout());
        this.setBackground(new Color(240, 240, 240));
        this.setAlwaysOnTop(true);
        JPanel searchView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
        searchView.add(new JScrollPane(tbl), new CC().wrap());
        
        this.buttonFld = new JButton("Select");
        //Splits the cell into 2
        searchView.add(buttonFld, "split 2");
        this.textQuery = new JTextField("");
        
        searchView.add(textQuery,  new CC().growX());
        this.add(searchView);
        this.pack();
        
        Component parentComp = ((Component)parent);
        
        this.setLocation(parentComp.getX(), parentComp.getY());
        this.tbl = tbl;
        this.sorter = new TableRowSorter<>((T)tbl.getModel());
        tbl.setRowSorter(this.sorter);
        this.parent = parent;
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
        
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    
                    
                    
                }
         });
        
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                ((Component)parent).setEnabled(true);
            }
           
            @Override
            public void windowClosed(WindowEvent e)
            {
                ((Component)parent).setEnabled(true);
            }
            
        });
        this.buttonFld.addActionListener(this);
        this.buttonFld.setActionCommand("Add Row");
       
        this.setVisible(true);
    }
   
     private void newFilter() 
    {
       RowFilter<T, Object> rf = null;
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
       this.sorter.setRowFilter(rf);
    }
    public int getSelectedRow()
    {
        return this.selectedRow;
        
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
//                if(tbl.getRowS)
        switch(e.getActionCommand())
        {
            case "Add Row":
                if(this.tbl.getSelectedRow() != -1)
                {
                    int row = tbl.getSelectedRow();
                    //Since filtering changes row numbering in views 
                    //we get the value of the index through the model
                    this.selectedRow = tbl.convertRowIndexToModel(row);
                    this.parent.broadcast(this);
                    ((Component)this.parent).setEnabled(true);
                    this.dispose();
                }
            break;
        }
    }
   
       public static void main(String [] args)
    {
        GPSISDataMapper.connectToDatabase();
        new SearchTable(new AddPatient(),PatientModule.buildPatientsTable(),
                        "Patient Search");
    }
}
