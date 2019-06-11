package Logic;

import java.util.ArrayList;
import java.util.List;

public class Plot {

    private List<Equation> graphs;
    private int window;

    public Plot(int window) {
        this.graphs = new ArrayList<Equation>();
        this.window = window;
    }

    public void addEquations(String... equation) {
        graphs.clear();
        
        for (String e : equation) {
            
            if (e.isEmpty()) {
                continue;
            }
            graphs.add(new Equation(e));
        }
    }

    public int getWindow() {
        return this.window;
    }

    public List<Equation> getGraphs() {
        return this.graphs;
    }
}
