package module.CalendarAppointments;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class AnalogClock extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	final static double TWOPI = Math.PI * 2.0;
    
    public AnalogClock() {
//    	javax.swing.Timer timer = new javax.swing.Timer(1000,this);
//        timer.start();
    }
    
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
         RenderingHints.VALUE_ANTIALIAS_ON);

        // draw the background
        g.setColor(new Color(51, 51, 51));
        g.fillRect(0,0,getWidth(),getHeight());
        
        Calendar calendar = Calendar.getInstance();
        int secs = calendar.get(Calendar.SECOND);
        int mins = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR);
        
        int size = Math.min(getWidth(),getHeight());
        int center = size/2;
        
        // save the original transform
        AffineTransform at = g.getTransform();
        
        // draw minute ticks
        g.setColor(Color.GRAY);
        for (int i=0; i<60; i++) {
            g.drawLine(center,center/25,center,center/50);
            g.rotate(TWOPI * (6.0 / 360.0),center,center);
        }
        g.setTransform(at);
        
        // draw hour dots
        g.setColor(Color.RED);
        for (int i=0; i<12; i++) {
            g.fillOval(center-center/40,center/100,center/20,center/20);
            g.rotate(TWOPI * (30.0 / 360.0),center,center);
        }
        g.setTransform(at);

        int[] x,y;

        // draw hour hand
        g.setColor(Color.WHITE);
        g.rotate(TWOPI * (((hours * 3600.0) + (mins * 60.0) + secs) / 43200.0),
         center,center);
        x = new int[] { center,center+center/16,center,center-center/16 };
        y = new int[] { center+center/10,center,center/3,center };
        g.fill(new Polygon(x,y,x.length));
        g.setTransform(at);
        
        // draw minute hand
        g.setColor(Color.WHITE);
        g.rotate(TWOPI * ((mins * 60.0 + secs) / 3600.0),center,center);
        x = new int[] { center,center+center/20,center,center-center/20 };
        y = new int[] { center+center/5,center,center/20,center };
        g.fill(new Polygon(x,y,x.length));
        g.setTransform(at);
        
        // draw second hand
        g.setColor(Color.WHITE);
        g.fillOval(center-center/50,center-center/50,center/25,center/25);
        g.rotate(TWOPI * (secs/ 60.0),center,center);
        x = new int[] { center,center-center/100,center,center+center/100 };
        y = new int[] { center+center/4,center,center/30,center };
        g.fill(new Polygon(x,y,x.length));
        g.drawLine(center,center+center/4,center,center/15);
        g.setTransform(at);
    }
    

	public void run() {
		javax.swing.Timer timer = new javax.swing.Timer(500,this);
        timer.start();
	}
}