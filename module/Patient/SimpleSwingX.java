package module.Patient;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * Sample auto-completion for a simple model with SwingX.
 * 
 * @author Kirill Grouchnikov
 */
public class SimpleSwingX extends JPanel {
	private JComboBox comboBox;

	public SimpleSwingX() {
		this.comboBox = new JComboBox(new Object[]{"Ester", "Jordi", "Jordina",
				"Jorge", "Sergi","COOL","a","b","c","d","e","z","X","y","Z"});
		this.add(this.comboBox);

		// Install auto-completion support. 
		AutoCompleteDecorator.decorate(this.comboBox);
		System.out.println("Is editable - " + this.comboBox.isEditable()
				+ ". Surprise!");

		this.comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						System.out.println("Selected '"
								+ SimpleSwingX.this.comboBox.getSelectedItem()
								+ "'");
					}
				});
			}
		});
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SimpleSwingX sample = new SimpleSwingX();
				JFrame frame = new JFrame("Simple SwingX");
				frame.add(sample, BorderLayout.CENTER);
				frame.setSize(300, 200);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}