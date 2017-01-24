import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.HashMap;

/**
 * A class for handling graphical changes
 * @author Kent Daleng
 * @version 0.2 (2017.01.24)
 */
public class GraphicsHandler implements Graphics {
    private HashMap<String, Canvas> canvases;
    private Scene scene;
    private StackPane root;

    /**
     * Constructor for a Graphics handler
     * @param primaryStage      the base Stage object
     */
    GraphicsHandler(Stage primaryStage) {
        this.canvases = initCanvases();
        this.root = initStackPane();
        setBackground(Main.COLOR_FLOOR);
        drawLines();
        this.scene = new Scene(this.root);
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    /**
     * Get the scene
     * @return  the Scene object
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Change the background of the floor tiles.
     * This may at some point become more than a useless feature, and
     * alter gameplay in some way or another
     */
    public void swapBackground() {
        if (this.root.getStyle().equals("-fx-background-color: " + Main.COLOR_FLOOR)) {
            setBackground(Main.COLOR_FLOOR_ALT);
        }
        else if (this.root.getStyle().equals("-fx-background-color: " + Main.COLOR_FLOOR_ALT)) {
            setBackground(Main.COLOR_FLOOR);
        }
    }

    /**
     * Draw a cell at a specific point, onto a canvas
     * @param target    the String representing the target canvas in a private HashMap. Can either be "bg" or "fg"
     * @param point     a Point representing a position in a 2D array
     * @param color     a String representing a hex value, e.g. "#FF000"
     */
    public void drawCell(String target, Point point, String color) {
        GraphicsContext graphicsContext = getCanvas(target).getGraphicsContext2D();
        graphicsContext.setFill(Paint.valueOf(color));
        graphicsContext.fillRect(point.x*Main.SCALE, point.y*Main.SCALE, Main.SCALE, Main.SCALE);
    }

    /**
     * Clear a cell at a specific point on a canvas
     * @param target    the String representing the target canvas in a private HashMap. Can either be "bg" or "fg"
     * @param point     a Point representing a position in a 2D array
     */
    public void clearCell(String target, Point point) {
        GraphicsContext graphicsContext = getCanvas(target).getGraphicsContext2D();
        graphicsContext.clearRect(point.x*Main.SCALE, point.y*Main.SCALE, Main.SCALE, Main.SCALE);
    }

    /**
     * Get the background canvas
     * @return  the background Canvas object
     */
    private Canvas getCanvas(String target) {
        return this.canvases.get(target);
    }
    /**
     * Adds the background and foreground canvases to a StackPane object,
     * which is returned
     * @return  the new StackPane object
     */
    private StackPane initStackPane() {
        StackPane root = new StackPane();
        root.getChildren().add(this.canvases.get("bg"));
        root.getChildren().add(this.canvases.get("fg"));
        return root;
    }

    /**
     * Creates a HashMap of `<String, Canvas>` pairs, to map "bg" and "fg"
     * to the background and foreground canvas, respectively.
     * @return  a HashMap containing bg and fg Canvases.
     */
    private HashMap<String, Canvas> initCanvases() {
        HashMap<String, Canvas> canvases = new HashMap<>();
        Canvas bgCanvas = new Canvas(Main.SIZE*Main.SCALE, Main.SIZE*Main.SCALE);
        Canvas fgCanvas= new Canvas(Main.SIZE*Main.SCALE, Main.SIZE*Main.SCALE);
        canvases.put("bg", bgCanvas);
        canvases.put("fg", fgCanvas);
        return canvases;
    }

    /**
     * Sets the background color to a specified color
     * @param color     a String representing a hex value, e.g. "#FF0000"
     */
    private void setBackground(String color) {
        this.root.setStyle("-fx-background-color: " + color);
    }

    /**
     * Draw grid lines onto the background
     */
    private void drawLines() {
        Canvas canvas = getCanvas("bg");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Paint.valueOf(Main.COLOR_GRID));
        for (int i = 0; i < canvas.getWidth(); i+=Main.SCALE) {
            // Since the canvas is always equilateral, we can draw vertical
            // and horizontal lines in every iteration
            graphicsContext.strokeLine(i, 0, i, canvas.getHeight());
            graphicsContext.strokeLine(0, i, canvas.getWidth(), i);
        }
    }
}
