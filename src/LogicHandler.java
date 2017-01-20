import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Point;

/**
 * A class for handling the game's logic.
 * @author Kent Daleng
 * @version 0.2 (2017.01.20)
 */
public class LogicHandler {
    private Player player;
    private Unit goal;
    private Map map;

    LogicHandler(Graphics graphics) {
        this.map = new Map(graphics);
        this.map.populate();
        this.map.drawMap();
        this.player = new Player(graphics, this.map.getArr(), Main.COLOR_PLAYER);
        this.goal = new Unit(graphics, this.map.getArr(), Main.COLOR_GOAL);
    }

    /**
     * Game logic that runs on every key press
     */
    void tick() {
        Point playerPoint = this.player.getArrayCoordinates();
        killPlayer(playerPoint);
        winGame(playerPoint);
        changeWall(playerPoint);
    }

    /**
     *  Kill the player if it has moved into a wall
     */
    private void killPlayer(Point playerPoint) {
        if (this.map.getValueAt(playerPoint) == 1) {
            System.out.println("You got stuck in a wall and died");
            System.exit(0);
        }
    }

    /**
     * End the game if the player has reached the goal
     */
    private void winGame(Point playerPoint) {
        if (playerPoint.equals(goal.getArrayCoordinates())) {
            System.out.println("You win!");
            System.exit(0);
        }
    }

    private void changeWall(Point playerPoint) {
        if (this.map.getValueAt(playerPoint) == 2) {
            this.map.setValueAt(playerPoint, 1);
        }
    }

    /**
     * Returns the map
     * @return  the Map object
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the player
     * @return  the Player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the goal
     * @return  the Unit object representing the goal
     */
    public Unit getGoal() {
        return goal;
    }
}
