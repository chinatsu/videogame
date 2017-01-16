import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * A class for handling the game's logic.
 * @author Kent Daleng
 * @version 0.1 (2017.01.16)
 */
public class LogicHandler {
    private Player player;
    private Unit goal;
    private Map map;

    LogicHandler(Canvas bgCanvas, Canvas fgCanvas) {
        GraphicsContext fgGc = fgCanvas.getGraphicsContext2D();
        GraphicsContext bgGc = bgCanvas.getGraphicsContext2D();
        this.map = new Map(bgGc);
        this.map.populate();
        this.player = new Player(fgGc, this.map.getArr(), Main.COLOR_PLAYER);
        this.goal = new Unit(fgGc, this.map.getArr(), Main.COLOR_GOAL);
    }

    /**
     * Game logic that runs on every game tick
     */
    void tick() {
        tryChangeWall();
    }

    /**
     * Game logic that runs right after every game tick
     */
    void tock() {
        if (isPlayerDead()) {
            System.out.println("You got stuck in a wall and died");
            System.exit(0);
        }
        else if (player.getArrayCoordinates().equals(goal.getArrayCoordinates())) {
            System.out.println("You win!");
            System.exit(0);
        }
    }

    /**
     *
     */
    private boolean isPlayerDead() {
        Point point = this.player.getArrayCoordinates();
        return this.map.getValueAt(point) == 1;
    }

    /**
     * If the player exists on a trap wall, change the the wall to a real wall
     */
    private void tryChangeWall() {
        Point point = this.player.getArrayCoordinates();
        if (this.map.getValueAt(point) == 2) {
            this.map.setValueAt(point, 1);
        }
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public Unit getGoal() {
        return goal;
    }
}
