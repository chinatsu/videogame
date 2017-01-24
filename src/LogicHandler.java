import java.awt.Point;

/**
 * A class for handling the game's logic.
 * @author Kent Daleng
 * @version 0.3 (2017.01.24)
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
        try {
            changeWall(playerPoint);
        } catch (OutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Kill the player if it has moved into a wall
     *  @param playerPoint      a Point representing the player's position in the 2D array
     */
    private void killPlayer(Point playerPoint) {
        try {
            if (this.map.getValueAt(playerPoint) == 1) {
                System.out.println("You got stuck in a wall and died");
                System.exit(0);
            }
        } catch (OutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * End the game if the player has reached the goal
     * @param playerPoint       a Point representing the player's position in the 2D array
     */
    private void winGame(Point playerPoint) {
        if (playerPoint.equals(goal.getArrayCoordinates())) {
            System.out.println("You win!");
            System.exit(0);
        }
    }

    /**
     * Only changes a "trap" wall if the player is on top of it
     * @param playerPoint       a Point representing the player's position in the 2D array
     * @throws OutOfBoundsException     if a point tries to access the map out of bounds, an error is thrown
     */
    private void changeWall(Point playerPoint) throws OutOfBoundsException {
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
