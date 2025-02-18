package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Juan Pablo Daza Pereira
 */
@Component("subsamplingFilter")
public class SubsamplingFilter implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        List<Point> filteredPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i += 2) {
            filteredPoints.add(points.get(i));
        }

        return new Blueprint(blueprint.getAuthor(),
                blueprint.getName(),
                filteredPoints.toArray(new Point[0]));
    }
}
