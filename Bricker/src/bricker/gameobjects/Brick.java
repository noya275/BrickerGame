package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * The Brick class represents a brick in the game. Handles collisions, triggering one/more of the strategies
 * defined in the strategies classes, allowing diverse interaction with collisions.
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;

    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the brick.
     * @param dimensions Size vector of the brick.
     * @param renderable Image of the brick.
     * @param collisionStrategy Random strategy out of the strategies classes defined.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Triggers one/more of the strategies defined in the strategies classes. decrements bricksLeft.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this, other);
    }
}
