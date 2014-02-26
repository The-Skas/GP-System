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

    public IntegerField() {
        super();
    }

    public IntegerField( int cols ) {
        super( cols );
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        @Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }

            char[] chars = str.toCharArray();
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
