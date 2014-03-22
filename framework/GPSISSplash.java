/**
 * 
 */
package framework;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JWindow;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

public class GPSISSplash extends JWindow {

	private static final long serialVersionUID = 1L;
	public JTextArea debug;

	/** GPSISSplash Constructor
	 * 
	 */
	public GPSISSplash() {
		this.setLayout(new MigLayout(new LC(), new AC().grow(), new AC().grow()));
		this.setSize(600, 350);
		this.setLocationRelativeTo(null);

		ImageIcon header = new ImageIcon(this.getClass().getResource(
				"/image/splash_header.jpg"));
		this.add(new JLabel(header), new CC().span().grow().wrap().dockNorth());

		debug = new JTextArea();
		debug.setBackground(new Color(51, 51, 51));
		debug.setForeground(new Color(240, 240, 240));
		debug.setEditable(false);

		this.getContentPane().setBackground(new Color(51, 51, 51));
		this.add(debug, new CC().pad("5px").grow().span());
		this.setVisible(true);
		this.toFront();
	}

	/** addText
	 * add Text to the Debug
	 * @param s
	 */
	public void addText(String s) {
		debug.setText(debug.getText() + s);
		this.revalidate();
		this.repaint();
		this.toFront();

	}

	/** clearText
	 * empties the Debug of all Text
	 */
	public void clearText() {
		debug.setText("");
		this.revalidate();
		this.repaint();
		this.toFront();
	}
}
