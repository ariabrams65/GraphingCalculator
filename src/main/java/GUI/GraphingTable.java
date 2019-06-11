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

            int xPixel = (int) (getWidth() / 2 + (getWidth() / 2 * ((2 * i) / window)));
            int yPixel = (int) (getHeight() / 2 - (getHeight() / 2 * ((2 * e.getYValue(i)) / window)));

            if (Double.toString(e.getYValue(i)).equals("NaN")) {
                continue;
            }

            g.fillRect(xPixel, yPixel - 1, 2, 3);
        }
    }

    private void graphGrid(Graphics g) {
        g.setColor(Color.BLACK);

        g.fillRect(getWidth() / 2, 0, 2, getHeight());

        g.fillRect(0, getHeight() / 2, getWidth(), 2);
    }
}
