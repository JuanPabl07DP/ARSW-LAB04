package edu.eci.arsw.blueprints.test.filters;

import edu.eci.arsw.blueprints.filters.RedundancyFilter;
import edu.eci.arsw.blueprints.filters.SubsamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * @author Juan Pablo Daza Pereira
 */
public class BlueprintFiltersTest {

    @Test
    public void shouldKeepNonRedundantPoints() {
        Point[] points = new Point[]{
                new Point(0,0),
                new Point(1,1),
                new Point(2,2),
                new Point(3,3)
        };

        Blueprint bp = new Blueprint("test", "no-redundancy", points);
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);

        assertEquals("Should keep all non-redundant points",
                points.length, filtered.getPoints().size());
    }

    @Test
    public void shouldRemoveAllRedundantPoints() {
        Point[] points = new Point[]{
                new Point(0,0),
                new Point(0,0),  // redundant
                new Point(0,0),  // redundant
                new Point(1,1),
                new Point(1,1),  // redundant
                new Point(2,2)
        };

        Blueprint bp = new Blueprint("test", "with-redundancy", points);
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);

        assertEquals("Should have 3 points after filtering redundancy",
                3, filtered.getPoints().size());

        // Verify points are correct
        List<Point> filteredPoints = filtered.getPoints();
        assertEquals("First point should be (0,0)", 0, filteredPoints.get(0).getX());
        assertEquals("Second point should be (1,1)", 1, filteredPoints.get(1).getX());
        assertEquals("Third point should be (2,2)", 2, filteredPoints.get(2).getX());
    }

    @Test
    public void shouldHandleEmptyBlueprint() {
        Point[] points = new Point[]{};
        Blueprint bp = new Blueprint("test", "empty", points);

        RedundancyFilter redundancyFilter = new RedundancyFilter();
        SubsamplingFilter subsamplingFilter = new SubsamplingFilter();

        Blueprint filteredRedundancy = redundancyFilter.filter(bp);
        Blueprint filteredSubsampling = subsamplingFilter.filter(bp);

        assertEquals("Redundancy filter should handle empty blueprint",
                0, filteredRedundancy.getPoints().size());
        assertEquals("Subsampling filter should handle empty blueprint",
                0, filteredSubsampling.getPoints().size());
    }

    @Test
    public void shouldSubsampleEvenNumberOfPoints() {
        Point[] points = new Point[]{
                new Point(0,0),
                new Point(1,1),
                new Point(2,2),
                new Point(3,3)
        };

        Blueprint bp = new Blueprint("test", "even-points", points);
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);

        assertEquals("Should have half the points",
                points.length/2, filtered.getPoints().size());

        List<Point> filteredPoints = filtered.getPoints();
        assertEquals("Should keep first point (0,0)", 0, filteredPoints.get(0).getX());
        assertEquals("Should keep third point (2,2)", 2, filteredPoints.get(1).getX());
    }

    @Test
    public void shouldSubsampleOddNumberOfPoints() {
        Point[] points = new Point[]{
                new Point(0,0),
                new Point(1,1),
                new Point(2,2),
                new Point(3,3),
                new Point(4,4)
        };

        Blueprint bp = new Blueprint("test", "odd-points", points);
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);

        assertEquals("Should have ceiling of half the points",
                3, filtered.getPoints().size());

        List<Point> filteredPoints = filtered.getPoints();
        assertEquals("Should keep first point (0,0)", 0, filteredPoints.get(0).getX());
        assertEquals("Should keep third point (2,2)", 2, filteredPoints.get(1).getX());
        assertEquals("Should keep fifth point (4,4)", 4, filteredPoints.get(2).getX());
    }
}