import javafx.scene.canvas.GraphicsContext;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is the Map class, and uses an OpenSimplex noise function to generate
 * 0s and 1s into a 2D array.
 * The class also handles drawing a representation of the array onto a GraphicsContext.
 * @author Kent Daleng
 * @version 0.1 (2017.01.11)
 */
class Map {
    private int[][] arr;
    private OpenSimplexNoise noise;

    Map(int size) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.arr = new int[size][size];
        this.noise = new OpenSimplexNoise(random.nextLong(100));
    }

    /**
     * Generates a map based on the OpenSimplex noise function,
     * where 1 represents a wall and 0 represents floor.
     */
    void populate() {
        for (int y = 0; y < this.arr.length; y++) {
            for (int x = 0; x < this.arr[y].length; x++) {
                double point = this.noise.eval(x, y);
                int p = 1;
                if (point > 0.2 || point < -0.2) {
                    p = 0;
                }
                this.arr[y][x] = p;
            }
        }
    }
    /**
     * Draws the representation of our map onto a GraphicsContext,
     * a 1 in the array will be a wall on the layer.
     * @param gc    a GraphicsContext instance to draw onto
     */

    void draw(GraphicsContext gc) {
        for (int y = 0; y < this.arr.length; y++) {
            for (int x = 0; x < this.arr.length; x++) {
                if (this.arr[y][x] == 1) {
                    gc.fillRect(x*Main.SIZE, y*Main.SIZE, Main.SIZE, Main.SIZE);
                }
            }
        }
    }

    int[][] getArr() {
        return this.arr;
    }
}
