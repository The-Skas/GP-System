/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skas
 */
package module;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImagePanel extends JPanel 
{

  Image image;

  public ImagePanel() 
  {
    image = Toolkit.getDefaultToolkit().createImage("font/0119.gif");
  }

  @Override
  public void paintComponent(Graphics g) 
  {
    super.paintComponent(g);
    if (image != null) 
    {
      g.drawImage(image, 0, 0, this);
    }
  }

  public static void main(String[] args) 
  {
  
        JFrame frame = new JFrame();
        frame.add(new ImagePanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
  
  }
}