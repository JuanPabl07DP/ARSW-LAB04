package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Pablo Daza Pereira
 */
@Component("redundancyFilter")
public class RedundancyFilter implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        List<Point> filteredPoints = new ArrayList<>();

        if (points.isEmpty()) {
            return blueprint;
        }

        filteredPoints.add(points.get(0));

        for (int i = 1; i < points.size(); i++) {
            Point current = points.get(i);
            Point previous = points.get(i-1);

            if (current.getX() != previous.getX() ||
                    current.getY() != previous.getY()) {
                filteredPoints.add(current);
            }
        }

        return new Blueprint(blueprint.getAuthor(),
                blueprint.getName(),
                filteredPoints.toArray(new Point[0]));
    }
}
