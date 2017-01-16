import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A general Unit class which can represent a player or a goal.
 * It contains some functions primarily to control an eventual player,
 * which also controls drawing onto a GraphicsContext.
 * @author Kent Daleng
 * @version 0.2 (2017.01.13)
 */
class Unit {
    private Point point;
    private final GraphicsContext gc;
    private final int[][] array;

    /**
     * The constructor function for Unit, which spawns to a random position on a 2D array.
     * @param gc    a GraphicsContext layer to spawn the graphical unit representation onto
     * @param array   an int[][] from the Map, it is only used to check wall collision as of right now
     * @param color a String formatted as a CSS color code to color the unit, e.g. "#0000ff"
     */
    Unit(GraphicsContext gc, int[][] array, String color) {
        this.array = array;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.point = new Point(Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE),
                               Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE));
        while (this.array[this.point.y/Main.SCALE][this.point.x/Main.SCALE] != 0) {
            // If a wall exists where the unit has spawned, try again until we spawn on a floor
            this.point = new Point(Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE),
                                   Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE));
        }
        this.gc = gc;
        this.gc.setFill(Paint.valueOf(color));
        this.gc.fillRect(this.point.x, this.point.y, Main.SCALE, Main.SCALE);
    }

    /**
     * Returns the current coordinates of the unit on the Canvas
     * @return  a Point to represent an (x, y) position of the unit
     */
    Point getCoordinates() {
        return this.point;
    }

    /**
     * Returns the current coordinates of the unit in the map array
     * @return  a Point to represent an (x, y) position of the unit
     */
    Point getArrayCoordinates() {
        int x = this.point.x / Main.SCALE;
        int y = this.point.y / Main.SCALE;
        return new Point(x, y);
    }
}
