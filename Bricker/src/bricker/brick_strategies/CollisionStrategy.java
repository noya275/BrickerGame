package bricker.brick_strategies;

import danogl.GameObject;


/**
 * The CollisionStrategy interface represents a strategy for handling collisions between game objects.
 * It defines a method, onCollision, for managing collision behavior between game objects. Implementing
 * classes define specific collision-handling behaviors.
 */
public interface CollisionStrategy {
    /**
     * The classes that implement this interface define specific collision-handling behaviors in this method,
     * including removing the collided object from the game and activating different collision strategies.
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    void onCollision(GameObject object1, GameObject object2);
}
