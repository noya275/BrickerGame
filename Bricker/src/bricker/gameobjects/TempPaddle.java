package bricker.gameobjects;

import static bricker.main.Constants.*;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The TempPaddle class represent a temporary paddle in the game. This temporary paddle serves as a
 * power-up that grants the player an additional limited-time paddle. It incorporates properties such
 * as responding to user input for movement and handling collisions with other game objects.
 */
public class TempPaddle extends Paddle {
    private final Counter paddleLivesCounter;

    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the temporary paddle.
     * @param dimensions Size vector of the paddle.
     * @param renderable Image of the paddle.
     * @param inputListener Object responsible for tracking player's keyboard presses.
     * @param relocationVec Vector that contains coordinates to locate paddle in, in case paddle is out of
     *                      window bounds.
     * @param paddleLivesCounter Counter that saves how many collisions till the paddle disappears.
     */
    public TempPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      UserInputListener inputListener, Vector2 relocationVec,
                      Counter paddleLivesCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener, relocationVec);
        this.paddleLivesCounter = paddleLivesCounter;
        // set tag for the temp paddle, for managing collisions
        this.setTag(TEMP_PADDLE);
    }

    /**
     * Ensures that temporary paddle object can only collide with balls.
     * @param other The other GameObject.
     * @return true upon allowed collision and false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        String otherTag = other.getTag();
        return super.shouldCollideWith(other) && (otherTag.equals(BALL)
                || (otherTag.equals(PUCK)));
    }

    /**
     * Decrements its lives counter. Calls father class to inherit movement and velocity properties.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.paddleLivesCounter.decrement();
    }
}
