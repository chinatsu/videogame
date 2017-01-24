import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A general Unit class which can represent a player or a goal.
 * It contains some functions primarily to control an eventual player,
 * which also controls drawing onto a GraphicsContext.
 * @author Kent Daleng
 * @version 0.4 (2017.01.24)
 */
class Unit {
    private Point point;
    private final Graphics graphics;
    private final int[][] array;
    private final String color;
    private final static String CANVAS = "fg";

    /**
     * The constructor function for Unit, which spawns to a random position on a 2D array.
     * @param graphics           a Graphics object to handle drawing of the Unit
     * @param array              an int[][] from the Map, it is only used to check wall collision as of right now
     * @param color              a String formatted as a CSS color code to color the unit, e.g. "#0000ff"
     */
    Unit(Graphics graphics, int[][] array, String color) {
        this.array = array;
        this.color = color;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.point = new Point(Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE),
                               Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE));
        while (this.array[this.point.y/Main.SCALE][this.point.x/Main.SCALE] != 0) {
            // If a wall exists where the unit has spawned, try again until we spawn on a floor
            this.point = new Point(Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE),
                                   Math.round(random.nextInt(0, this.array.length-1) * Main.SCALE));
        }
        this.graphics = graphics;
        this.graphics.drawCell(CANVAS, this.getArrayCoordinates(), this.color);
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
