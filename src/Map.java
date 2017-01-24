import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is the Map class, and uses an OpenSimplex noise function to generate
 * 0s and 1s into a 2D array.
 * The class also handles drawing a representation of the array onto a GraphicsContext.
 * @author Kent Daleng
 * @version 0.3 (2017.01.20)
 */
class Map {
    private final int[][] array;
    private final OpenSimplexNoise noise;
    private Graphics graphics;
    private final static String CANVAS = "bg";

    /**
     * Constructor for Map objects
     * @param graphics  a Graphics object to handle our graphics calls
     */
    Map(Graphics graphics) {
        this.graphics = graphics;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.array = new int[Main.SIZE][Main.SIZE];
        this.noise = new OpenSimplexNoise(random.nextLong(100));
    }

    /**
     * Generates a map based on the OpenSimplex noise function,
     * where 1 represents a wall and 0 represents floor.
     */
    void populate() {
        for (int y = 0; y < this.array.length; y++) {
            for (int x = 0; x < this.array[y].length; x++) {
                double point = this.noise.eval(x, y);
                int p = 1;
                if (point > 0.7 || point < 0) {
                    p = 0;
                }
                else if (point < 0.1 && point > 0) {
                    p = 2;
                }
                this.array[y][x] = p;
            }
        }
    }

    /**
     * Sets the value at a certain point in the array,
     * and draws the resulting graphical representation
     * @param point     a Point with array coordinates
     * @param value     a value to set at the array position
     */
    void setValueAt(Point point, int value) throws OutOfBoundsException {
        if (point.x > this.array.length || point.y > this.array.length) {
            throw new OutOfBoundsException();
        }
        this.graphics.clearCell(CANVAS, point);
        this.array[point.y][point.x] = value;
        drawMapCell(point);
    }

    /**
     * Draws the representation of our map onto a GraphicsContext,
     * a 1 in the array will be a wall on the layer.
     */
    void drawMap() {
        for (int y = 0; y < this.array.length; y++) {
            for (int x = 0; x < this.array.length; x++) {
                Point point = new Point(x, y);
                drawMapCell(point);
            }
        }
    }

    /**
     * Draws a single cell at a specific point
     * @param point     a point in the array coordinate system to draw at
     */
    private void drawMapCell(Point point) {
        if (this.array[point.y][point.x] == 1) {
            this.graphics.drawCell(CANVAS, point, Main.COLOR_WALL);
        }
        else if (this.array[point.y][point.x] == 2) {
            this.graphics.drawCell(CANVAS, point, Main.COLOR_WALL_ALT);
        }
    }
    /**
     * Returns the value in the array at a specific point
     * @param point     a Point at which the value should be returned
     * @return          an int representing the value in the specified cell
     */
    int getValueAt(Point point) throws OutOfBoundsException {
        if (point.x > this.array.length || point.y > this.array.length) {
            throw new OutOfBoundsException();
        }
        return this.array[point.y][point.x];
    }

    /**
     * Returns the map array
     * @return  an int[][] representing the map
     */
    int[][] getArr() {
        return this.array;
    }
}
