package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 * @author Juan Pablo Daza Pereira
 */
public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){

        }
    }

    @Test
    public void getBlueprintTest() throws BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        // Try to get non-existent blueprint
        try {
            ibpp.getBlueprint("non-existent", "bp");
            fail("Should throw BlueprintNotFoundException for non-existent blueprint");
        } catch (BlueprintNotFoundException ex) {
            // Expected
        }

        // Add and then get blueprint
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("test-author", "test-bp", pts);

        try {
            ibpp.saveBlueprint(bp);

            // Should find the blueprint now
            Blueprint retrieved = ibpp.getBlueprint("test-author", "test-bp");
            assertEquals("Retrieved blueprint should match stored one", bp, retrieved);
        } catch (Exception ex) {
            fail("Unexpected exception: " + ex.getMessage());
        }
    }

    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        // Try to get blueprints for non-existent author
        try {
            ibpp.getBlueprintsByAuthor("non-existent");
            fail("Should throw BlueprintNotFoundException for non-existent author");
        } catch (BlueprintNotFoundException ex) {
            // Expected
        }

        // Add blueprints for one author
        String author = "multi-bp-author";

        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint(author, "bp1", pts1);

        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint(author, "bp2", pts2);

        // Add blueprint for different author
        Point[] pts3 = new Point[]{new Point(40, 40), new Point(50, 50)};
        Blueprint bp3 = new Blueprint("different-author", "bp3", pts3);

        try {
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);

            // Get blueprints by author
            Set<Blueprint> authorBlueprints = ibpp.getBlueprintsByAuthor(author);

            assertEquals("Should have returned 2 blueprints", 2, authorBlueprints.size());
            assertTrue("Should contain bp1", authorBlueprints.contains(bp1));
            assertTrue("Should contain bp2", authorBlueprints.contains(bp2));
            assertFalse("Should not contain bp3", authorBlueprints.contains(bp3));
        } catch (Exception ex) {
            fail("Unexpected exception: " + ex.getMessage());
        }
    }

    @Test
    public void getAllBlueprintsTest() throws BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        // Get all blueprints with only the initial blueprint
        Set<Blueprint> initialBlueprints = ibpp.getAllBlueprints();
        assertEquals("Should have 1 initial blueprint", 1, initialBlueprints.size());

        // Add more blueprints
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("author1", "bp1", pts1);

        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("author2", "bp2", pts2);

        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);

        // Get all blueprints again
        Set<Blueprint> allBlueprints = ibpp.getAllBlueprints();
        assertEquals("Should have 3 blueprints total", 3, allBlueprints.size());
        assertTrue("Should contain bp1", allBlueprints.contains(bp1));
        assertTrue("Should contain bp2", allBlueprints.contains(bp2));
    }
}
