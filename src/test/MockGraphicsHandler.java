import javafx.scene.Scene;
import java.awt.Point;

/**
 * A mock class to act as a graphics handler during tests
 * @author Kent Daleng
 * @version 0.1 (2017.01.24)
 */
public class MockGraphicsHandler implements Graphics {

    @Override
    public Scene getScene() {
        return null;
    }

    @Override
    public void swapBackground() {
        System.out.println("Background swapped!");
    }

    @Override
    public void drawCell(String target, Point point, String color) {
        System.out.println("Drew a cell on canvas: " + target + "\n"
                         + "at point (" + point.x + ", " + point.y + ")\n"
                         + "with the color " + color);
    }

    @Override
    public void clearCell(String target, Point point) {
        System.out.println("Cleared a cell on canvas: " + target + "\n"
                         + "at point (" + point.x + ", " + point.y + ")");
    }
}
