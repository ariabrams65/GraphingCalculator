package Logic;

import java.util.ArrayList;
import java.util.List;

public class Plot {

    private List<Equation> graphs;

    public Plot(int window) {
        this.graphs = new ArrayList<Equation>();
    }

    public void addEquations(String... equation) {
        graphs.clear();
        int i = 0;
        
        for (String e : equation) {
            
            if (e.isEmpty()) {
                i++;
                continue;
            }
            graphs.add(new Equation(e, i));
            i++;
        }
    }

    public List<Equation> getGraphs() {
        return this.graphs;
    }
}
