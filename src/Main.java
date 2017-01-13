import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.awt.Point;

/**
 * The main class for a simple 2D videogame. This is the
 * top-level class in the project, and utilizes JavaFX
 * canvases to draw a map, as well as player and goal objects.
 * @author Kent Daleng
 * @version 0.2 (2017.01.13)
 */
public class Main extends Application {
    /**
     * an int that declares the size of each cell
     * in the graphical view
     */
    static final int SCALE = 16;
    /**
     * an int that declares the size of the array
     * `SIZE * SCALE` is then the window size
     */
    static final int SIZE = 30;

    /**
     * Color of walls
     */
    static final String COLOR_WALL = "#000000";

    /**
     * Color of "traversable" walls
     */
    static final String COLOR_WALL_ALT = "#333333";

    /**
     * Color of the player
     */
    static final String COLOR_PLAYER = "#0000ff";

    /**
     * Color of the goal
     */
    static final String COLOR_GOAL = "#cccc00";

    /**
     * Color of the grid lines
     */
    static final String COLOR_GRID = "#cbbbc0";

    /**
     * Color of the background (floor)
     */
    static final String COLOR_FLOOR = "#faf8ce";

    /**
     * Alternate color of the background
     */
    static final String COLOR_FLOOR_ALT = "#faf8ff";

    private Player player;
    private Unit goal;
    private Map map;

    /**
     * Launches JavaFX with arguments
     * @param args  does nothing as of now
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A videogame");
        Canvas bgCanvas = initBackground();
        Canvas fgCanvas = initForeground();
        Canvas[] canvases = new Canvas[] {bgCanvas, fgCanvas};
        Scene scene = initScene(canvases);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes the background canvas
     * @return  a Canvas object representing the background layer
     */
    private Canvas initBackground() {
        Canvas bgCanvas = new Canvas(SIZE*SCALE, SIZE*SCALE);
        GraphicsContext bgGc = bgCanvas.getGraphicsContext2D();
        drawLines(bgGc); // Draw grid lines,
        this.map = new Map(bgGc);
        this.map.populate(); // generate a map,
        this.map.drawArray(); // then draw the map walls onto the background layer
        return bgCanvas;
    }

    /**
     * Initializes the foreground canvas
     * @return  a Canvas object representing the foreground layer
     */
    private Canvas initForeground() {
        Canvas fgCanvas = new Canvas(SIZE*SCALE, SIZE*SCALE);
        GraphicsContext fgGc = fgCanvas.getGraphicsContext2D();
        this.goal = new Unit(fgGc, this.map.getArr(), COLOR_GOAL); // Place a goal,
        this.player = new Player(fgGc, this.map.getArr(), COLOR_PLAYER); // and a player onto the foreground layer
        return fgCanvas;
    }

    /**
     * Gathers the canvases and adds them to a StackPane object,
     * the StackPane is then used to initiate a Scene which is returned
     * @param canvases a list of Canvas objects to add to the StackPane
     * @return  a resulting Scene with the added Canvas layers
     */
    private Scene initScene(Canvas[] canvases) {
        StackPane root = new StackPane();
        for (Canvas canvas : canvases) {
            root.getChildren().add(canvas);
        }
        root.setStyle("-fx-background-color: " + COLOR_FLOOR);
        Scene scene = new Scene(root);
        setInput(scene, root);
        return scene;
    }

    /**
     * A function which sets the game's controls
     * and checks if the player has won or has died.
     * TODO: Use a config file to map inputs and actions instead of hardcoding
     * @param scene a Scene object to handle inputs of.
     */
    private void setInput(Scene scene, StackPane root) {
        scene.setOnKeyPressed(e -> {
            Point point = this.player.getArrayCoordinates();
            if (this.map.getValueAt(point) == 2) {
                // When the player has moved on top of a traversable wall,
                // turn it into a real wall which cannot be moved into
                // afterwards.
                this.map.setValueAt(point, 1);
            }
            switch (e.getCode()) {
                case D:
                case RIGHT:
                    this.player.move(1, 0);
                    break;
                case A:
                case LEFT:
                    this.player.move(-1, 0);
                    break;
                case W:
                case UP:
                    this.player.move(0, -1);
                    break;
                case S:
                case DOWN:
                    this.player.move(0, 1);
                    break;
                case R:
                    swapBackground(root);
                    break;
                case Q:
                    this.player.moveRotate();
                    point = this.player.getArrayCoordinates();
                    if (this.map.getValueAt(point) == 1) {
                        // After rotating, there is a chance that the player ends up
                        // inside a wall. In which case, we'll say the player has died.
                        System.out.println("You got stuck in a wall and died");
                        System.exit(0);
                    }
                    break;
                case E:
                    this.player.moveRotate();
                    this.player.moveRotate();
                    this.player.moveRotate();
                    point = this.player.getArrayCoordinates();
                    if (this.map.getValueAt(point) == 1) {
                        System.out.println("You got stuck in a wall and died");
                        System.exit(0);
                    }
                    break;
                case ESCAPE:
                    System.out.println("Exiting game.");
                    System.exit(0);
            }
            if (this.player.getArrayCoordinates().equals(this.goal.getArrayCoordinates())) {
                // Always check after an input, if the player has reached the goal
                System.out.println("You win!");
                System.exit(0);
            }
        });
    }
    /**
     * Draw grid lines onto a layer
     * @param gc    a GraphicsContext instance to draw onto
     */
    private void drawLines(GraphicsContext gc) {
        gc.setStroke(Paint.valueOf(COLOR_GRID));
        for (int i = 0; i < gc.getCanvas().getWidth(); i+=SCALE) {
            // Since the canvas is always equilateral, we can draw vertical
            // and horizontal lines in every iteration
            gc.strokeLine(i, 0, i, gc.getCanvas().getHeight());
            gc.strokeLine(0, i, gc.getCanvas().getWidth(), i);
        }
    }

    /**
     * Change the background of the floor tiles.
     * This may at some point become more than a useless feature, and
     * alter gameplay in some way or another
     * @param root  the root StackPane object containing all the GraphicsContext layers
     */
    private void swapBackground(StackPane root) {
        if (root.getStyle().equals("-fx-background-color: " + COLOR_FLOOR)) {
            root.setStyle("-fx-background-color: " + COLOR_FLOOR_ALT);
        }
        else if (root.getStyle().equals("-fx-background-color: " + COLOR_FLOOR_ALT)) {
            root.setStyle("-fx-background-color: " + COLOR_FLOOR);
        }
    }
}
