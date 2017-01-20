import javafx.scene.canvas.Canvas;

import java.awt.Point;

/**
 * A Player class which extends Unit with methods to
 * control the Player.
 * @author Kent Daleng
 * @version 0.2 (2017.01.20)
 */
class Player extends Unit {
    private final int[][] array;
    private final Canvas canvas;
    private Graphics graphics;
    private Point point;
    private String color;

    Player(Graphics graphics, int[][] array, String color) {
        super(graphics, array, color);
        this.graphics = graphics;
        this.point = this.getCoordinates();
        this.canvas = this.graphics.getFgCanvas();
        this.array = array;
        this.color = color;
    }
    /**
     * Move the player to another space relative from its current position
     * @param x     moves the player horizontally (negative is to the right)
     * @param y     moves the player vertically (negative is upwards)
     */
    void move(int x, int y) {
        Point oldPoint = this.getArrayCoordinates();
        Point newPoint = new Point(oldPoint.x + x, oldPoint.y + y);
        if (newPoint.x > Main.SIZE - 1 || newPoint.x < 0) {
            return;
        }
        else if (newPoint.y > Main.SIZE - 1 || newPoint.y < 0) {
            return;
        }
        if (this.array[newPoint.y][newPoint.x] == 1) {
            return;
        }
        this.graphics.clearCell(this.canvas, oldPoint);
        this.point.x = newPoint.x * Main.SCALE;
        this.point.y = newPoint.y * Main.SCALE;
        this.graphics.drawCell(this.canvas, newPoint, this.color);
    }

    /**
     * Rotate the player counter clockwise around the grid's center.
     */
    void moveRotate() {
        Point oldPoint = this.getArrayCoordinates();
        this.graphics.clearCell(this.canvas, oldPoint);
        double c = this.array.length/2;
        double new_y = (0 - (this.point.x/Main.SCALE - c) + c - 1)*Main.SCALE;
        this.point.x = this.point.y; // After rotating, the new x value will always be the old y value
        this.point.y = (int) new_y;
        Point newPoint = this.getArrayCoordinates();
        this.graphics.drawCell(this.canvas, newPoint, this.color);
    }
}
