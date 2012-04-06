/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package disegno.oggetti.punte;

import grafica.componenti.contenitori.PannelloBase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import disegno.oggetti.Segmento;

/**
 * This class defines arrow heads of various shapes.
 */
public class ArrowHead{

    /**
     * Draws the arrowhead.
     * 
     * @param g2 the graphics context
     * @param p a point on the axis of the arrow head
     * @param q the end point of the arrow head
     */
    public void draw(Graphics2D g2, Point2D p, Point2D q)
    {
        GeneralPath path = getPath(p, q);
        Color oldColor = g2.getColor();
        if (this != V && this != NONE)
        {
           g2.setColor(Color.WHITE);
           g2.fill(path);
        }        
        
        g2.setColor(oldColor);
        g2.draw(path);
    }

    /**
     * Gets the path of the arrowhead
     * 
     * @param p a point on the axis of the arrow head
     * @param q the end point of the arrow head
     * @return the path
     */
    public GeneralPath getPath(Point2D p, Point2D q)
    {
        GeneralPath path = new GeneralPath();
        if (this == NONE) return path;
        final double ARROW_ANGLE = Math.PI / 6;
        final double ARROW_LENGTH = 40;

        double dx = q.getX() - p.getX();
        double dy = q.getY() - p.getY();
        double angle = Math.atan2(dy, dx);
        double x1 = q.getX() - ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE);
        double y1 = q.getY() - ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE);
        double x2 = q.getX() - ARROW_LENGTH * Math.cos(angle - ARROW_ANGLE);
        double y2 = q.getY() - ARROW_LENGTH * Math.sin(angle - ARROW_ANGLE);

        path.moveTo((float) x1, (float) y1);
        path.lineTo((float) q.getX(), (float) q.getY());
        path.lineTo((float) x2, (float) y2);
        path.lineTo((float) q.getX(), (float) q.getY());
        path.lineTo((float) x1, (float) y1);
        path.closePath();
    
        return path;
    }

    /** Array head type : this head has no shape */
    public static final ArrowHead NONE = new ArrowHead();

    /** Array head type : this head is a V */
    public static final ArrowHead V = new ArrowHead();

    /** Internal Java UID */
    private static final long serialVersionUID = -3824887997763775890L;
    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {

    		@Override
    		public void run() {
    			JFrame f = new JFrame();
    			f.setVisible(true);
    			f.getContentPane().add(new PannelloBase(f){
    				@Override
    				protected void paintComponent(Graphics g) {
    					super.paintComponent(g);
    					Point uno = new Point(20, 20);
    					Point due = new Point(140, 50);
    					Segmento l = new Segmento(uno, due);
    					l.draw(g);
    					ArrowHead ah = new ArrowHead();
    					ah.draw((Graphics2D) g, uno, due);
    				}
    			});
    			f.setBounds(0, 0, 600, 500);
    			f.addWindowListener(new WindowAdapter() {
    				public void windowClosing(WindowEvent event) {
    					System.exit(0);
    				}
    			});
    		}
    	});
	}

}