/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.Patient;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A JTextField that accepts only integers.
 *
 */
public class IntegerField extends JTextField {
    private int limit = 99999;
    public IntegerField() {
        super();
    }

    public IntegerField( int cols, int limit ) {
        super( cols );
        ((UpperCaseDocument)getDocument()).limit = limit;
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument(this.limit);
    }

    static class UpperCaseDocument extends PlainDocument {
        private int limit;
        public UpperCaseDocument(int limit)
        {
           super();
        }
        @Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }
            
            char[] chars = str.toCharArray();
            
            if(getLength() + str.length() > this.limit)
                return;
            
            boolean ok = true;

            for ( int i = 0; i < chars.length; i++ ) {

                try {
                    Integer.parseInt( String.valueOf( chars[i] ) );
                } catch ( NumberFormatException exc ) {
                    ok = false;
                    break;
                }


            }

            if ( ok )
                super.insertString( offs, new String( chars ), a );

        }
    }

}
