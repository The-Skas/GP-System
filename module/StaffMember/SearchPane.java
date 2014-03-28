/** Search Staff Members Pane
 * 
 * Used to Pick a Staff Member :D
 * 
 * If Copying and Pasting I highly recommend that you just a Find and Replace to replace the following:
 * - StaffMemberDMO with YOURDMO
 * - StaffMemberATM with YOURATM
 * - StaffMember with YOUROBJECT
 * 
 * @author Vijendra Patel (vp302)
 *
 */
package module.StaffMember;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import mapper.StaffMemberDMO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import exception.EmptyResultSetException;
import exception.UserDidntSelectException;
import framework.GPSISFramework;
import object.StaffMember;

public class SearchPane implements ListSelectionListener, DocumentListener, ActionListener {

    	private static String dialogTitle = "Select a Staff Member";
    	private static StaffMemberDMO dmo = StaffMemberDMO.getInstance(); // change to your own DMO!
    	private static StaffMemberATM aTM; // change to your own Abstract Table Model Type
    	private static TableRowSorter<StaffMemberATM> tableSorter;  // change to your own Abstract Table Model Type    	
    	private static List<StaffMember> itemList; // change to a list of your own Objects
    	private static StaffMember selectedItem; // change to your Object
    	    	
    	// the below doesn't need changing xD
    	private static JTextField searchFld = new JTextField(30);
    	private static int selectedRowInModel;
    	private static JButton selectBtn;
    	private static JTable table; 
    	
    	
    	/** doSearch
    	 * builds and displays a JDialog with the Table and Search in.
    	 * @return the selected StaffMember
    	 * @throws UserDidntSelectException 
    	 */
        public static StaffMember doSearch() throws UserDidntSelectException, EmptyResultSetException
        {
        	JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setTitle(dialogTitle);
            
           
            JPanel content = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));	  
            
            // build Header
	    		JPanel header = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().shrink()));
	    			JLabel hTitle = new JLabel(dialogTitle);
	    			hTitle.setFont(GPSISFramework.getFonts().get("OpenSans").deriveFont(24f));
	    		header.add(hTitle);	    		
    		content.add(header, new CC().wrap());
	    		
    		// build Content Pane
	    		JPanel searchPanel = new JPanel(new MigLayout(new LC().fill(), new AC().grow(), new AC().grow()));
	    		searchFld.getDocument().addDocumentListener(new module.StaffMember.SearchPane());
	    		searchPanel.add(searchFld, new CC().span().wrap());
				itemList = dmo.getAll();						
				aTM = new StaffMemberATM(itemList);
				table = new JTable (aTM);
				tableSorter = new TableRowSorter<StaffMemberATM>(aTM);
				
				table.setRowSorter(tableSorter);
				table.getSelectionModel().addListSelectionListener(new module.StaffMember.SearchPane());
				searchPanel.add(new JScrollPane(table), new CC().span().grow().wrap());
				
				selectBtn = new JButton("Select");
	            selectBtn.addActionListener(new module.StaffMember.SearchPane());
	            selectBtn.setVisible(false);
	            searchPanel.add(selectBtn);
	    			
    		content.add(searchPanel, new CC().span().grow());   		
            
            dialog.getContentPane().add(content);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            
            if (selectedItem != null)
            	return selectedItem;
            else
            	throw new UserDidntSelectException();
        }
        
        /* (non-Javadoc)
         * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
         */
        @Override
    	public void valueChanged(ListSelectionEvent lse) {
    		selectBtn.setVisible(true);
    		int viewRow = table.getSelectedRow();
            if (viewRow < 0) {
            } else {
                selectedRowInModel = table.convertRowIndexToModel(viewRow);
            }		
    	}
        

        /** filter
         * runs the filter
         */
        private void filter() 
        {
    		RowFilter<StaffMemberATM, Object> rf = null;
            List<RowFilter<Object,Object>> rfs = new ArrayList<RowFilter<Object,Object>>();

            try {
                String text = searchFld.getText();
                String[] textArray = text.split(" ");

                for (int i = 0; i < textArray.length; i++) {
                rfs.add(RowFilter.regexFilter("(?i)" + textArray[i]));
                }

                rf = RowFilter.andFilter(rfs);

            }catch (java.util.regex.PatternSyntaxException e) {
                return;
            }

            tableSorter.setRowFilter(rf);
       }
        
        /* (non-Javadoc)
         * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            filter();
        }

        /* (non-Javadoc)
         * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            filter();       
        }

        /* (non-Javadoc)
         * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            filter(); 
        }

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedItem = itemList.get(selectedRowInModel);
            JButton button = (JButton)e.getSource();
            SwingUtilities.getWindowAncestor(button).dispose();			
		}
}

/**
 * End of File: SearchPane.java 
 * Location: module/StaffMember
 */