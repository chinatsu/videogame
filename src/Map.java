import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is the Map class, and uses an OpenSimplex noise function to generate
 * 0s and 1s into a 2D array.
 * The class also handles drawing a representation of the array onto a GraphicsContext.
 * @author Kent Daleng
 * @version 0.2 (2017.01.13)
 */
class Map {
    private final int[][] array;
    private final OpenSimplexNoise noise;
    private GraphicsContext gc;

    Map(GraphicsContext gc) {
        this.gc = gc;
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
    void setValueAt(Point point, int value) {
        this.gc.clearRect(point.x*Main.SCALE, point.y*Main.SCALE, Main.SCALE, Main.SCALE);
        this.array[point.y][point.x] = value;
        drawCell(point.x, point.y);
    }

    /**
     * Draws the representation of our map onto a GraphicsContext,
     * a 1 in the array will be a wall on the layer.
     */
    void drawArray() {
        for (int y = 0; y < this.array.length; y++) {
            for (int x = 0; x < this.array.length; x++) {
                drawCell(x, y);
            }
        }
    }

    /**
     * Draws a single cell at a specific point
     * @param x     the x value of the point
     * @param y     the y value of the point
     */
    private void drawCell(int x, int y) {
        if (this.array[y][x] == 1) {
            this.gc.setFill(javafx.scene.paint.Paint.valueOf(Main.COLOR_WALL));
            this.gc.fillRect(x*Main.SCALE, y*Main.SCALE, Main.SCALE, Main.SCALE);
        }
        else if (this.array[y][x] == 2) {
            this.gc.setFill(Paint.valueOf(Main.COLOR_WALL_ALT));
            this.gc.fillRect(x*Main.SCALE, y*Main.SCALE, Main.SCALE, Main.SCALE);
        }
    }
    /**
     * Returns the value in the array at a specific point
     * @param point a Point at which the value should be returned
     * @return  an int representing the value in the specified cell
     */
    int getValueAt(Point point){
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
