import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for a simple 2D video game. This is the
 * top-level class in the project, and utilizes JavaFX
 * canvases to draw a map, as well as player and goal objects.
 * @author Kent Daleng
 * @version 0.4 (2017.01.24)
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
     * Color of "trap" walls
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
    private Graphics graphics;

    /**
     * Launches JavaFX with arguments
     * @param args  does nothing as of now
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A video game");
        this.graphics = new GraphicsHandler(primaryStage);
        this.logic = new LogicHandler(graphics);
        setInput(graphics.getScene());
    }


    /**
     * A function which sets the game's controls
     * and checks if the player has won or has died.
     * TODO: Use a config file to map inputs and actions instead of hardcoding
     * @param scene a Scene object to handle inputs of.
     */
    private void setInput(Scene scene) {
        Player player = this.logic.getPlayer();
        scene.setOnKeyPressed(e -> {
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
                    this.graphics.swapBackground();
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
            this.logic.tick();
        });
    }

}
