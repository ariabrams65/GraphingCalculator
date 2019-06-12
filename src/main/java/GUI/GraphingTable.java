package GUI;

import Logic.Equation;
import Logic.Plot;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GraphingTable extends JPanel {

    private Plot plot;

    public GraphingTable() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphGrid(g);

        g.setColor(Color.BLUE);
        //I don't know why I need a try Catch
        try {
            for (Equation e : plot.getGraphs()) {
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

        int window = plot.getWindow();

        double increment = 1 / ((double) getWidth() / window);

        for (double i = window / 2.0 * -1; i < window / 2; i += increment) {

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

        g.fillRect(getWidth() / 2, 0, 2, getHeight());

        g.fillRect(0, getHeight() / 2, getWidth(), 2);
    }

    
    private int getXPixel(double i) {
       return (int)(getWidth() / 2 + (getWidth() / 2 * ((2 * i) / plot.getWindow())));
    }
    
    private int getYPixel(double i, Equation e) {
        return (int)(getHeight() / 2 - (getHeight() / 2 * ((2 * e.getYValue(i)) / plot.getWindow())));
    }
}
