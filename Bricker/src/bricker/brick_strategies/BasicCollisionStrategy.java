package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;


/**
 * The BasicCollisionStrategy class represents a basic collision event for a brick in the game.
 * This strategy does not introduce any additional behaviors upon collision.
 */
public class BasicCollisionStrategy implements CollisionStrategy{
    /**
     * Inheriting classes use this field in order to add objects to the game.
     */
    protected final GameObjectCollection gameObjects;
    private final Counter bricksLeft;

    /**
     * Constructor.
     * @param gameObjects gameObjects: The collection of game objects.
     * @param bricksLeft Counter that saves how many bricks are left in the game (yet to collide with a ball).
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter bricksLeft) {
        this.gameObjects = gameObjects;
        this.bricksLeft = bricksLeft;
    }

    /**
     * Removes brick (object1) from the game.
     * @param object1 Brick.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (this.gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS)){
            this.bricksLeft.decrement();
        }
    }
}
