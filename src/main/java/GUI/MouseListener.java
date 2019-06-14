
package GUI;

import Logic.Plot;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseListener extends MouseAdapter {
    
    private GraphingTable table;
    private Point mousePt;
    
    public MouseListener(GraphingTable table) {
        this.table = table;
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        table.setWindow(table.getWindow() + e.getWheelRotation());
        if (table.getWindow() < 1) {
            table.setWindow(1);
        }
        
        table.repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        double dx = ((double)(e.getX() - mousePt.x) / table.getWidth() * table.getWindow());
        double dy = - 1 * ((double)(e.getY() - mousePt.y) / table.getHeight() * table.getWindow());
  
        table.moveOrigin(dx, dy);
        mousePt = e.getPoint();

        table.repaint();
    }
    
    @Override 
    public void mousePressed(MouseEvent e) {
       this.mousePt = e.getPoint();
        table.repaint();
    }
}
