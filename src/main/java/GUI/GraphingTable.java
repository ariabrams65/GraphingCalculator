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
    private int window;
    private double x;
    private double y;

    public GraphingTable() {
        setBackground(Color.WHITE);
        this.window = 20;
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
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

    private void graph(Equation e, Graphics g) {

        double increment = 1 / ((double) getWidth() / window);

        for (double i = window / -2.0; i < window / 2.0; i += increment) {

            int xPixel = getXPixel(i);
            int yPixel = getYPixel(i, e);

            //checks to see if the value is undefined and skips it
            if (Double.toString(e.getYValue(i)).equals("NaN")) {
                continue;
            }
            //calculating the next y value to remove gaps in the graph
            int yNext = getYPixel(i + increment, e);
            int yHeight = yNext - yPixel;

            if (yHeight == 0) {
                yHeight = 2;
            }

            g.fillRect(xPixel, yPixel - 1, 2, yHeight);
        }
    }

    private void graphGrid(Graphics g) {
        g.setColor(Color.BLACK);

        g.fillRect(0, getYPixel(y), getWidth(), 2);
        g.fillRect(getXPixel(x), 0, 2, getHeight());

        graphGridMarks(g);
    }

    private int getXPixel(double i) {
        return (int) (getWidth() / 2 + (getWidth() / 2 * ((2 * i) / window)));
    }

    private int getYPixel(double i, Equation e) {
        return (int) (getHeight() / 2 - (getHeight() / 2 * ((2 * e.getYValue(i)) / window)));
    }

    private int getYPixel(double y) {
        return (int) (getHeight() / 2 - (getHeight() / 2 * ((2 * y) / window)));
    }

    public void graphGridMarks(Graphics g) {
        g.setColor(Color.GRAY);

        int xPixel = getXPixel(x);
        int yPixel = getYPixel(y);
        double increment = getHeight() / (double) window;

        for (double y = yPixel; y <= getHeight(); y += increment) {
            g.fillRect(0, (int) y, getWidth(), 1);
        }
        for (double y = yPixel; y >= 0; y -= increment) {
            g.fillRect(0, (int) y, getWidth(), 1);
        }

        increment = (double) getWidth() / window;

        for (double x = xPixel; x <= getWidth(); x += increment) {
            g.fillRect((int) x, 0, 1, getHeight());
        }
        for (double x = xPixel; x >= 0; x -= increment) {
            g.fillRect((int) x, 0, 1, getHeight());
        }
    }

    public void setWindow(int size) {
        this.window = size;
    }

    public int getWindow() {
        return this.window;
    }

    public void moveOrigin(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
}
