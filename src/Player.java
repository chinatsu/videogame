import javafx.scene.canvas.GraphicsContext;

import java.awt.Point;

/**
 * A Player class which extends Unit with methods to
 * control the Player.
 * @author Kent Daleng
 * @version 0.1 (2017.01.13)
 */
class Player extends Unit {
    private final int[][] array;
    private final GraphicsContext gc;
    private Point point;

    Player(GraphicsContext gc, int[][] array, String color) {
        super(gc, array, color);
        this.point = this.getCoordinates();
        this.gc = gc;
        this.array = array;
    }
    /**
     * Move the player to another space relative from its current position
     * @param x     moves the player horizontally (negative is to the right)
     * @param y     moves the player vertically (negative is upwards)
     */
    void move(int x, int y) {
        int arrayX = this.getArrayCoordinates().x; // Use array coordinates for clearer
        int arrayY = this.getArrayCoordinates().y; // position checks
        if (arrayX + x > Main.SIZE - 1 || arrayX + x < 0) {
            return;
        }
        else if (arrayY + y > Main.SIZE - 1 || arrayY + y < 0) {
            return;
        }
        if (this.array[arrayY + y][arrayX + x] == 1) {
            return;
        }
        this.gc.clearRect(this.point.x, this.point.y, Main.SCALE, Main.SCALE);
        this.point.x += x*Main.SCALE;
        this.point.y += y*Main.SCALE;
        this.gc.fillRect(this.point.x, this.point.y, Main.SCALE, Main.SCALE);
    }

    /**
     * Rotate the player counter clockwise around the grid's center.
     */
    void moveRotate() {
        this.gc.clearRect(this.point.x, this.point.y, Main.SCALE, Main.SCALE);
        double c = this.array.length/2;
        double new_y = (0 - (this.point.x/Main.SCALE - c) + c - 1)*Main.SCALE;
        this.point.x = this.point.y; // After rotating, the new x value will always be the old y value
        this.point.y = (int) new_y;
        this.gc.fillRect(this.point.x, this.point.y, Main.SCALE, Main.SCALE);
    }
}
