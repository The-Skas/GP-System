/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Prescriptions;

import framework.GPSISDataMapper;
import framework.GPSISPopup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import mapper.MedicineDMO;
import mapper.PatientDMO;
import module.Broadcastable;
import module.Patient.AddPatient;
import module.Prescriptions.AddNewPrescription;
import module.Prescriptions.ViewDescription;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import object.Medicine;

/**
 *
 * @author Ozhan Azizi
 */
public class MedicineInformation extends GPSISPopup implements ActionListener{
    
    
    private static Medicine [] meds;
    
    private JList list;
    private DefaultListModel listModel;
    private JButton addButtonFld;
    private JButton delButtonFld;
    
    private JButton descriptionFld;
    private JButton relevantAmountFld;
    
    
    Medicine m;
    
    
    //Used a linkedHashSet to make sure no duplicate entries are added.
    private LinkedHashSet<Medicine> medicinesHashSet;
    private Broadcastable parent;
    private int selectedRow;
    private JComboBox comboBox;
    /*
     * This constructor takes in a Broadcastable object, this allows the class
     * to be able to communicate with the parent container that intialised it.
    */
    private MedicineInformation _this;
    public MedicineInformation(final Broadcastable parent,List<Medicine> medsList) 
    {
        //Should be an edit/ and for adding
        super("Medical Conditions");
        //assigning value to _this to be used in anonymous functions
        //Look up javascript 101 ;)
        _this = this;
       
        List <Medicine> tempMedicines;
        tempMedicines = MedicineDMO.getInstance().getAll();
        System.out.println(tempMedicines);
        meds = new Medicine[tempMedicines.size()];
        tempMedicines.toArray(meds);
        for(int i = 0; i < meds.length; i ++)
            System.out.println(meds[i]);
        //TODO: Why not add shortcuts for add/delete?
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new MigLayout());
        this.setBackground(new Color(240, 240, 240));
        this.setAlwaysOnTop(true);
        
        //Get Medicines and add them to listModel
        this.listModel = new DefaultListModel();
        this.medicinesHashSet = new LinkedHashSet<>();
        if(medsList != null)
        {
            this.medicinesHashSet.addAll(medsList);
            for(Medicine MC : this.medicinesHashSet)
            {
                listModel.addElement(MC);
            }
        }
        list = new JList(listModel);
		
        JPanel medView = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
        medView.add(new JScrollPane(list), new CC().growX().wrap());
        
        //Add Buttons
        this.descriptionFld = new JButton("Description");
        medView.add(descriptionFld, new CC().split(3));
        
        this.relevantAmountFld = new JButton("Relevant Amount");
        medView.add(this.relevantAmountFld);
        
        
        
        this.add(medView);
        this.pack();
        
        //Aligns text in center of list
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        Component parentComp = ((Component)parent);
        
        this.setLocation(parentComp.getX(), parentComp.getY());
        this.list = list;
        this.parent = parent;
       
        
        list.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    System.out.println("Clicking the list");
                    System.out.println(list.getSelectedIndex());
                }
         });
        
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closing window");
                //send a message to the PareitnContainer.
                //Skas says:
                //refer to variable _this, since 'this' in this scope, refers
                //to the anonymous function. Got a strange compiler error.
                //Apparently anything with 'className$1',where 1 can be any int,
                //refers to an anonymous class.
                parent.broadcast(_this);
                ((Component)parent).setEnabled(true);
                
            }

        });
        this.descriptionFld.addActionListener(this);
        this.descriptionFld.setActionCommand("Description");
        this.relevantAmountFld.addActionListener(this);
        this.relevantAmountFld.setActionCommand("Relevant Amount");
       
        this.setVisible(true);
    }
   
    public ArrayList<Medicine> getMedicines()
    {
        ArrayList<Medicine> listPatMedConds = new ArrayList<>();
        listPatMedConds.addAll(this.medicinesHashSet);
        return listPatMedConds;
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
//                if(list.getRowS)
        switch(e.getActionCommand())
        {
            case "Description":
            
                int selectedInd = list.getSelectedIndex();
                if(selectedInd != -1)
                {
                System.out.println("Getting medicine description");
                this.m = (Medicine) list.getSelectedValue();
                new ViewDescription(m);
                             
                
                }
            break;
            
            case "Relevant Amount":
                int selectedInd2 = list.getSelectedIndex();
                if(selectedInd2 != -1)
                {
                System.out.println("Getting medicine relevant amount");
                this.m = (Medicine) list.getSelectedValue();
                new ViewRelevantAmount(m);                                            
                }                    
            break;               
                
        }
    }
    public void keyPressed(KeyEvent event) {
     switch (event.getKeyCode()) {
         case KeyEvent.VK_UP:
             System.out.println("Up Selected");
             break;
         case KeyEvent.VK_DOWN:
             System.out.println("Down Selected");
             break;
         case KeyEvent.VK_RIGHT:
             // right arrow
             break;
         case KeyEvent.VK_LEFT:
             // left arrow
             break;
     }
}
    
}
