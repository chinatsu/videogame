import java.awt.Point;
import javafx.scene.Scene;

/**
 * An interface defining methods required for handling graphics.
 * @author Kent Daleng
 * @version 0.1 (2017.01.24)
 */
interface Graphics {
    Scene getScene();
    void swapBackground();
    void drawCell(String target, Point point, String color);
    void clearCell(String target, Point point);
}
