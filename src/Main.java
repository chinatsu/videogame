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
 * @version 0.1 (2017.01.11)
 */
public class Main extends Application {
    static final int SIZE = 16;

    /**
     * @param args  does nothing as of now
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A videogame");
        Map map = new Map(gridSize);
        map.populate(); // Generate walls
        Canvas bgCanvas = initBackground(map);
        Canvas fgCanvas = initForeground(map);
        Scene scene = initScene([bgCanvas, fgCanvas])
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Canvas initBackground(Map map) {
        Canvas bgCanvas = new Canvas(gridSize*SIZE, gridSize*SIZE);
        GraphicsContext bgGc = bgCanvas.getGraphicsContext2D();
        drawLines(bgGc); // Draw gridlines,
        map.draw(bgGc); // then draw the map walls onto the background layer
        return bgCanvas;
    }

    private Canvas initForeground(Map map) {
        Canvas fgCanvas = new Canvas(gridSize*SIZE, gridSize*SIZE);
        GraphicsContext fgGc = fgCanvas.getGraphicsContext2D();
        Unit goal = new Unit(fgGc, map.getArr(), "#cccc00"); // Place a goal,
        Unit player = new Unit(fgGc, map.getArr(), "#0000ff"); // and a player onto the foreground layer
        return fgCanvas;
    }

    private Scene initScene(Canvas[] canvases) {
      StackPane root = new StackPane();
      for (canvas : canvases) {
          stackpane.getChildren().add(canvas);
      }
      root.setStyle("-fx-background-color: #faf8ce");
      Scene scene = new Scene(root);
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
                  swapBackground(root);
                  break;
              case Q:
                  player.moveRotate();
                  Point point = player.getCoordinates();
                  if (arr[point.y/16][point.x/16] == 1) {
                      // After rotating, there is a chance that the player ends up
                      // inside a wall. In which case, we'll say the player has died.
                      System.out.println("You got stuck in a wall and died");
                      System.exit(0);
                  }
                  break;
          }
          if (player.getCoordinates().equals(goal.getCoordinates())) {
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
        gc.setStroke(Paint.valueOf("#cbbbc0"));
        for (int i = 0; i < gc.getCanvas().getWidth(); i+=SIZE) {
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
        if (root.getStyle().equals("-fx-background-color: #faf8ce")) {
            root.setStyle("-fx-background-color: #faf8ff");
        }
        else if (root.getStyle().equals("-fx-background-color: #faf8ff")) {
            root.setStyle("-fx-background-color: #faf8ce");
        }
    }
}
