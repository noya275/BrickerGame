package bricker.brick_strategies;

import static bricker.main.Constants.COLLISIONS_TO_DISAPPEAR;

import bricker.gameobjects.TempPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The TempPaddleStrategy class represents the collision strategy for a brick that triggers the temporary
 * appearance of an additional paddle in the middle of the screen upon collision. Updates paddleLivesCounter
 * when creating a new TempPaddle.
 */
public class TempPaddleStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private final Vector2 paddleSize;
    private final Renderable renderable;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final Counter paddleLivesCounter;

    /**
     * Constructor.
     * @param gameObjects The collection of game objects.
     * @param paddleSize Size vector of the paddle.
     * @param renderable Image of the paddle.
     * @param inputListener Object responsible for tracking player's keyboard presses.
     * @param windowDimensions Window dimensions vector.
     * @param paddleLivesCounter Counter that saves how many collisions till the paddle disappears.
     * @param bricksLeft Counter that saves how many bricks are left in the game (yet to collide with a ball).
     */
    public TempPaddleStrategy(GameObjectCollection gameObjects, Vector2 paddleSize, Renderable renderable,
                              UserInputListener inputListener, Vector2 windowDimensions,
                              Counter paddleLivesCounter, Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.paddleSize = paddleSize;
        this.renderable = renderable;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddleLivesCounter = paddleLivesCounter;
    }

    /**
     * Calls super for brick removal. If there already exists a TempPaddle in the game, returns.
     * Otherwise, creates a new TempPaddle object, adds it to gameObjects and updates paddleLivesCounter to
     * COLLISIONS_TO_DISAPPEAR.
     * @param object1 Brick.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        // create an extra paddle only if one doesn't already exist
        if (this.paddleLivesCounter.value() != 0) {
            return;
        }
        Vector2 relocationVec = new Vector2(this.windowDimensions.x() - paddleSize.x(),
                this.windowDimensions.y() * 0.5f);
        TempPaddle tempPaddle = new TempPaddle(windowDimensions.mult(0.5f), this.paddleSize,
                this.renderable, this.inputListener, relocationVec, this.paddleLivesCounter);
        // add paddle to game objects
        this.gameObjects.addGameObject(tempPaddle);
        // reset paddleLivesCounter
        this.paddleLivesCounter.increaseBy(COLLISIONS_TO_DISAPPEAR);
    }
}

