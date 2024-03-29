package GUI;

import Logic.Equation;
import Logic.Plot;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

public class GraphingTable extends JPanel {

    private Plot plot;
    private double window;
    private Point origin;

    public GraphingTable() {
        setBackground(Color.WHITE);
        this.window = 20;
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        //this.origin = new Point(getWidth() / 2, getHeight() / 2);
        
        List<Color> colors = new ArrayList<Color>();
        colors.addAll(Arrays.asList(Color.BLUE, Color.RED, Color.GREEN, Color.orange, Color.yellow));

        super.paintComponent(g);

        graphGrid(g);
        //I don't know why I need a try Catch
        try {
            for (Equation e : plot.getGraphs()) {
                //changes color for each equation
                g.setColor(colors.get(e.getNumber()));

                graph(e, g);
            }
        } catch (NullPointerException e) {
            return;
        }
    }

    public void setGraphs(Plot plot) {
        this.plot = plot;
    }

    private void graph(Equation equation, Graphics g) {

        double increment = 1 / ((double) getWidth() / window);

        for (double x = window / -2.0 - getOriginXCoord(); x < window / 2.0 - getOriginXCoord(); x += increment) {
            int xPixel = getXPixel(x);
            int yPixel = getYPixel(x, equation);

            //checks to see if the value is undefined and skips it
            if (Double.toString(equation.getYValue(x)).equals("NaN")) {
                continue;
            }
            //calculating the next y value to remove gaps in the graph
            int yNext = getYPixel(x + increment, equation);
            int yHeight = yNext - yPixel;

            if (yHeight == 0) {
                yHeight = 2;
            }

            g.fillRect(xPixel, yPixel, 2, yHeight);
        }
    }

    private void graphGrid(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, origin.y, getWidth(), 2);
        g.fillRect(origin.x, 0, 2, getHeight());

        graphGridMarks(g);
    }

    private int getXPixel(double x) {
        return (int)(origin.x + (getWidth() * ((x) / window)));
    }

    private int getYPixel(double x, Equation equation) {
        return (int)(origin.y - (getHeight() * ((equation.getYValue(x)) / window)));
    }

    public void graphGridMarks(Graphics g) {
        g.setColor(Color.GRAY);

        double increment = (double)getHeight() / window;

        for (double y = origin.y; y <= getHeight(); y += increment) {
            g.fillRect(0, (int) y, getWidth(), 1);
        }
        for (double y = origin.y; y >= 0; y -= increment) {
            g.fillRect(0, (int) y, getWidth(), 1);
        }

        increment = (double)getWidth() / window;

        for (double x = origin.x; x <= getWidth(); x += increment) {
            g.fillRect((int) x, 0, 1, getHeight());
        }
        for (double x = origin.x; x >= 0; x -= increment) {
            g.fillRect((int) x, 0, 1, getHeight());
        }
    }

    public void setWindow(double size) {
        this.window = size;
    }

    public double getWindow() {
        return this.window;
    }

    public void moveOrigin(int dx, int dy) {
        this.origin.translate(dx, dy);
    }
    
    public void resetOrigin() {
        this.origin = new Point(getWidth() / 2, getHeight() / 2);
    }
    
    public double getOriginXCoord() {
        //returns grid/non-pixel version of the x coord 
        return (double)window * (origin.x - (getWidth() / 2)) / getWidth();
    }
    
    public Point getOrigin() {
        return this.origin;
    }
}
