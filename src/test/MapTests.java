import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;


/**
 * A test class for testing Map methods
 * @author Kent Daleng
 * @version 0.1 (2017.01.24)
 */
public class MapTests {

    private Graphics graphics;
    private Map map;


    MapTests() {
        this.graphics = new MockGraphicsHandler();
        this.map = new Map(graphics);
    }

    /**
     * Tests if a set value is found in the map array afterwards
     * @throws OutOfBoundsException throws an exception if the point accessed is out of bounds
     */
    @Test
    public void testSetValue() throws OutOfBoundsException {
        Point point = new Point(0, 0);
        this.map.setValueAt(point, 2);
        assertEquals(2, this.map.getValueAt(point));
    }

    @Test
    public void testSetValueOutOfXBounds() throws OutOfBoundsException {
        Point point = new Point(99*99, 1);
        assertThrows(OutOfBoundsException.class, () -> this.map.setValueAt(point, 1));
    }

    @Test
    public void testGetValueOutOfXBounds() throws OutOfBoundsException {
        Point point = new Point(99*99, 1);
        assertThrows(OutOfBoundsException.class, () -> this.map.getValueAt(point));
    }
    @Test
    public void testSetValueOutOfYBounds() throws OutOfBoundsException {
        Point point = new Point(1, 99*99);
        assertThrows(OutOfBoundsException.class, () -> this.map.setValueAt(point, 1));
    }

    @Test
    public void testGetValueOutOfYBounds() throws OutOfBoundsException {
        Point point = new Point(1, 99*99);
        assertThrows(OutOfBoundsException.class, () -> this.map.getValueAt(point));
    }
}
