
package GUI;

import Logic.Plot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class MouseListener extends MouseAdapter {
    
    private GraphingTable table;
    private Plot plot;
    
    public MouseListener(GraphingTable table, Plot plot) {
        this.table = table;
        this.plot = plot;
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        plot.setWindow(plot.getWindow() + e.getWheelRotation());
        if (plot.getWindow() < 1) {
            plot.setWindow(1);
        }
        System.out.println(plot.getWindow());
        
        table.repaint();
    }
}
