import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for testing Player methods
 * @author Kent Daleng
 * @version 0.1 (2017.01.24)
 */

public class PlayerTests {
    private Graphics graphics;
    private Map map;
    private Player player;

    PlayerTests() {
        this.graphics = new MockGraphicsHandler();
        this.map = new Map(this.graphics); // Let's make an empty map without any walls
        this.player = new Player(this.graphics, this.map.getArr(), Main.COLOR_PLAYER);
    }

    @Test
    public void testMoveOnce() {
        Point oldPoint = this.player.getArrayCoordinates();
        if (oldPoint.getX() < this.map.getArr().length - 2) {
            this.player.move(1, 0);
            assertEquals(oldPoint.getX() + 1, this.player.getArrayCoordinates().getX());
        }
        else {
            this.player.move(-1, 0);
            assertEquals(oldPoint.getX() - 1, this.player.getArrayCoordinates().getX());
        }
    }
    @Test void testMoveOutOfBounds() {
        Point oldPoint = this.player.getArrayCoordinates();
        this.player.move(99*99, 0);
        assertEquals(oldPoint, this.player.getArrayCoordinates());
    }
}
