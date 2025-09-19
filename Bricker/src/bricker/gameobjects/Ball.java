package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * The Ball class represents a ball in the game. It incorporates properties such as handling collisions,
 * triggering direction changes and making a sound, and also saves a counter for collisions.
 */
public class Ball extends GameObject {
    private int collisionCounter = 0;
    private final Sound collisionSound;

    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the ball.
     * @param dimensions Size vector of the ball.
     * @param renderable Image of the ball.
     * @param collisionSound Sound when there's a collision with a ball.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Flips ball velocity, updates collisionCounter and plays collision sound.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        this.collisionCounter++;
        this.collisionSound.play();
    }

    /**
     * @return Number of collisions the ball has had so far.
     */
    public int getCollisionCounter(){
        return this.collisionCounter;
    }
}
