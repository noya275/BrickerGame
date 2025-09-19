package bricker.brick_strategies;

import static bricker.main.Constants.BALL;
import static bricker.main.Constants.PUCK;

import bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The CameraChangeStrategy class represents the collision strategy for a brick that triggers the camera
 * to follow the ball. The class updates the number of collisions the main ball has had before the current
 * camera change brick collision.
 */
public class CameraChangeStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final GameManager gameManager;
    private final WindowController windowController;
    private final Counter hitsBeforeCameraChange;

    /**
     * Constructor. Calls super for
     * @param gameObjects The collection of game objects.
     * @param gameManager Manager of the game. Used to check if camera is null.
     * @param windowController Responsible for managing the game window. Used for getting measurements.
     * @param hitsBeforeCameraChange Counter that saves how many hits the ball has had before current
     *                               collision.
     * @param bricksLeft Counter that saves how many bricks are left in the game (yet to collide with a ball).
     */
    public CameraChangeStrategy(GameObjectCollection gameObjects, GameManager gameManager,
                                WindowController windowController, Counter hitsBeforeCameraChange,
                                Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.gameManager = gameManager;
        this.windowController = windowController;
        this.hitsBeforeCameraChange = hitsBeforeCameraChange;
    }

    /**
     * Calls super for brick removal. If camera already active, returns. Otherwise, creates a new
     * camera that follows the Main ball.
     * @param object1 Brick.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        // if object collided with brick isn't main Ball / there's already an active camera, do noting
        if(object2.getTag().equals(PUCK) || !(object2.getTag().equals(BALL))
                || this.gameManager.camera() != null) {
            return;
        }
        // update counter of ball hits before collision with camera strategy; +1 for hitting object1
        this.hitsBeforeCameraChange.reset();
        this.hitsBeforeCameraChange.increaseBy(((Ball)object2).getCollisionCounter() + 1);
        // set a new camera to follow the ball, reset the cameraHitsCounter
        this.gameManager.setCamera(new Camera(object2, Vector2.ZERO,
                this.windowController.getWindowDimensions().mult(1.f),
                this.windowController.getWindowDimensions()));

    }
}
