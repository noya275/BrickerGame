package bricker.gameobjects;

import static bricker.main.Constants.PADDLE;
import static bricker.main.Constants.TEMP_PADDLE;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The Heart class represents a heart-shaped game objects that symbolize the player's lives within the game.
 */
public class Heart extends GameObject {
    private final Counter livesCounter;

    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the heart.
     * @param dimensions Size vector of the heart.
     * @param renderable Image of the heart.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        this(topLeftCorner, dimensions, renderable, Vector2.ZERO, null);
    }

    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the heart.
     * @param dimensions Size vector of the heart.
     * @param renderable Image of the heart.
     * @param velocity Velocity vector of the heart when it falls from brick as power-up.
     * @param livesCounter Counter that saves the player's remaining lives.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 Vector2 velocity, Counter livesCounter) {
        super(topLeftCorner, dimensions, renderable);
        super.setVelocity(velocity);
        this.livesCounter = livesCounter;
    }

    /**
     * Ensures that Heart objects can only collide with the Main paddle and not the temporary power-up paddle.
     * @param other The other GameObject.
     * @return true upon allowed collision and false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) && other.getTag().equals(PADDLE)
                && !(other.getTag().equals(TEMP_PADDLE));
    }

    /**
     * If livesCounter is null then the heart object is a static object, it doesn't collide, so it returns
     * without doing anything, Otherwise add another life to player.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // if livesCounter is null then the heart object is a static object, it doesn't collide, return
        if (this.livesCounter == null) {
            return;
        }
        // if paddle collides with heart, increment livesCounter
        this.livesCounter.increment();
        this.setDimensions(Vector2.ZERO);
    }
}
