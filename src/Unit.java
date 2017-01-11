import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A general Unit class which can represent a player or a goal.
 * It contains some functions primarily to control an eventual player,
 * which also controls drawing onto a GraphicsContext.
 * @author Kent Daleng
 * @version 0.1 (2017.01.11)
 */
class Unit {
    private Point point;
    private final GraphicsContext gc;
    private final int[][] arr;

    /**
     * The constructor function for Unit, which spawns to a random position on a 2D array.
     * @param gc    a GraphicsContext layer to spawn the unit onto
     * @param arr   an int[][] from the Map, it is only used to check wall collision as of right now
     * @param color a String formatted as a CSS color code to color the unit, e.g. "#0000ff"
     */
    Unit(GraphicsContext gc, int[][] arr, String color) {
        this.arr = arr;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.point = new Point(Math.round(random.nextInt(0, this.arr.length-1) * Main.SIZE),
                               Math.round(random.nextInt(0, this.arr.length-1) * Main.SIZE));
        while (this.arr[this.point.y/Main.SIZE][this.point.x/Main.SIZE] == 1) {
            // If a wall exists where the unit has spawned, try again until we spawn on a floor
            this.point = new Point(Math.round(random.nextInt(0, this.arr.length-1) * Main.SIZE),
                                   Math.round(random.nextInt(0, this.arr.length-1) * Main.SIZE));
        }
        this.gc = gc;
        this.gc.setFill(Paint.valueOf(color));
        this.gc.fillRect(this.point.x, this.point.y, Main.SIZE, Main.SIZE);
    }

    /**
     * Move the player to another space relative from its current position
     * @param x     moves the player horizontally (negative is to the right)
     * @param y     moves the player vertically (negative is upwards)
     */
    void move(int x, int y) {
        if (this.point.x + x*Main.SIZE > this.gc.getCanvas().getWidth() - Main.SIZE || this.point.x + x*Main.SIZE < 0) {
            return;
        }
        else if (this.point.y + y*16 > this.gc.getCanvas().getHeight() - Main.SIZE || this.point.y + y*Main.SIZE < 0) {
            return;
        }
        if (this.arr[(this.point.y + y*Main.SIZE)/Main.SIZE][(this.point.x + x*Main.SIZE)/Main.SIZE] == 1) {
            return;
        }
        this.gc.clearRect(this.point.x, this.point.y, Main.SIZE, Main.SIZE);
        this.point.x += x*Main.SIZE;
        this.point.y += y*Main.SIZE;
        this.gc.fillRect(this.point.x, this.point.y, Main.SIZE, Main.SIZE);
    }

    /**
     * Rotate the player counter clockwise around the grid's center.
     */
    void moveRotate() {
        this.gc.clearRect(this.point.x, this.point.y, Main.SIZE, Main.SIZE);
        double c = this.arr.length/2;
        double new_y = (0 - (this.point.x/Main.SIZE - c) + c - 1)*Main.SIZE;
        this.point.x = this.point.y;
        this.point.y = (int) new_y;
        this.gc.fillRect(this.point.x, this.point.y, Main.SIZE, Main.SIZE);
    }

    /**
     * Returns the current coordinates of the unit
     * @return  a Point to represent an (x, y) position of the unit
     */
    Point getCoordinates() {
        return this.point;
    }

}
