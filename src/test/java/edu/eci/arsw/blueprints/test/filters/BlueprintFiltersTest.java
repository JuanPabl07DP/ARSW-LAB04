package edu.eci.arsw.blueprints.test.filters;

import edu.eci.arsw.blueprints.filters.RedundancyFilter;
import edu.eci.arsw.blueprints.filters.SubsamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlueprintFiltersTest {

    @Test
    public void shouldRemoveRedundantPoints() {
        Point[] points = new Point[]{
                new Point(0,0),
                new Point(0,0),  // redundant
                new Point(1,1),
                new Point(2,2),
                new Point(2,2),  // redundant
                new Point(3,3)
        };

        Blueprint bp = new Blueprint("test", "redundancy", points);
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);

        assertEquals("Should have 4 points after filtering",
                4, filtered.getPoints().size());
    }

    @Test
    public void shouldSubsamplePoints() {
        Point[] points = new Point[]{
                new Point(0,0),
                new Point(1,1),
                new Point(2,2),
                new Point(3,3),
                new Point(4,4)
        };

        Blueprint bp = new Blueprint("test", "subsample", points);
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);

        assertEquals("Should have 3 points after filtering",
                3, filtered.getPoints().size());
        assertEquals("First point should be (0,0)",
                0, filtered.getPoints().get(0).getX());
        assertEquals("Second point should be (2,2)",
                2, filtered.getPoints().get(1).getX());
    }
}