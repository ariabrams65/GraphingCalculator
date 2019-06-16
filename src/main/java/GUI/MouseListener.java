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
        table.setWindow(table.getWindow() + e.getWheelRotation() * (table.getWindow() / 20));
        if (table.getWindow() < 1) {
            table.setWindow(1);
            return;
        }
        int x = e.getX() - table.getOrigin().x;
        int y = e.getY() - table.getOrigin().y;
        
        if (e.getWheelRotation() < 0) {
            table.moveOrigin((int)(-1 * x / 20), (int)(-1 * y / 20));

        } else {
            table.moveOrigin((int)(x / 20), (int)(y / 20));
        }
        table.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - mousePt.x;
        int dy = e.getY() - mousePt.y;

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
