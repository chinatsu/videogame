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

    private LogicHandler logic;
    private Scene scene;

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
        Canvas bgCanvas = new Canvas(SIZE*SCALE, SIZE*SCALE);
        Canvas fgCanvas = new Canvas(SIZE*SCALE, SIZE*SCALE);
        this.logic = new LogicHandler(bgCanvas, fgCanvas);
        drawLines(bgCanvas);
        this.logic.getMap().drawArray();
        Canvas[] canvases = new Canvas[] {bgCanvas, fgCanvas};
        this.scene = initScene(canvases);
        primaryStage.setScene(this.scene);
        primaryStage.show();
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
     * @param root  a StackPane to handle certain things
     */
    private void setInput(Scene scene, StackPane root) {
        Player player = this.logic.getPlayer();
        scene.setOnKeyPressed(e -> {
            this.logic.tick();
            switch (e.getCode()) {
                case D:
                case RIGHT:
                    player.move(1, 0);
                    break;
                case A:
                case LEFT:
                    player.move(-1, 0);
                    break;
                case W:
                case UP:
                    player.move(0, -1);
                    break;
                case S:
                case DOWN:
                    player.move(0, 1);
                    break;
                case R:
                    swapBackground(root);
                    break;
                case Q:
                    player.moveRotate();
                    break;
                case E:
                    player.moveRotate();
                    player.moveRotate();
                    player.moveRotate();
                    break;
                case ESCAPE:
                    System.out.println("Exiting game.");
                    System.exit(0);
            }
            this.logic.tock();

        });
    }
    /**
     * Draw grid lines onto a layer
     * @param canvas    a Canvas instance to draw onto
     */
    private void drawLines(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
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
