import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.Point;

/**
 * A class for handling graphical changes
 */
public class Graphics {
    private Canvas bgCanvas;
    private Canvas fgCanvas;
    private Scene scene;
    private StackPane root;

    Graphics(Stage primaryStage) {
        this.bgCanvas = new Canvas(Main.SIZE*Main.SCALE, Main.SIZE*Main.SCALE);
        this.fgCanvas = new Canvas(Main.SIZE*Main.SCALE, Main.SIZE*Main.SCALE);
        this.root = new StackPane();
        initStackPane();
        setBackground(Main.COLOR_FLOOR);
        drawLines();
        this.scene = new Scene(this.root);
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    public Canvas getBgCanvas() {
        return this.bgCanvas;
    }

    public Canvas getFgCanvas() {
        return this.fgCanvas;
    }

    public Scene getScene() {
        return this.scene;
    }

    public StackPane getRoot() {
        return this.root;
    }

    /**
     * Adds the canvases to a StackPane object, and
     * sets the background color
     */
    private void initStackPane() {
        this.root.getChildren().add(this.bgCanvas);
        this.root.getChildren().add(this.fgCanvas);
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
        GraphicsContext graphicsContext = this.bgCanvas.getGraphicsContext2D();
        graphicsContext.setStroke(Paint.valueOf(Main.COLOR_GRID));
        for (int i = 0; i < this.bgCanvas.getWidth(); i+=Main.SCALE) {
            // Since the canvas is always equilateral, we can draw vertical
            // and horizontal lines in every iteration
            graphicsContext.strokeLine(i, 0, i, this.bgCanvas.getHeight());
            graphicsContext.strokeLine(0, i, this.bgCanvas.getWidth(), i);
        }
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

    public void drawCell(Canvas canvas, Point point, String color) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Paint.valueOf(color));
        graphicsContext.fillRect(point.x*Main.SCALE, point.y*Main.SCALE, Main.SCALE, Main.SCALE);
    }

    public void clearCell(Canvas canvas, Point point) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(point.x*Main.SCALE, point.y*Main.SCALE, Main.SCALE, Main.SCALE);
    }
}
