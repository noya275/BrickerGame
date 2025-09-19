package bricker.brick_strategies;

import static bricker.main.Constants.HEART_VELOCITY;

import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The HeartFallStrategy class represents the collision strategy for a brick that triggers the falling of a
 * heart upon collision.
 */
public class HeartFallStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final Renderable renderable;
    private final Vector2 heartSize;
    private final Counter livesCounter;

    /**
     * Constructor.
     * @param gameObjects The collection of game objects.
     * @param renderable Image of the Heart
     * @param heartSize Size vector of the heart.
     * @param livesCounter Counter that saves the player's remaining lives.
     * @param bricksLeft Counter that saves how many bricks are left in the game (yet to collide with a ball).
     */
    public HeartFallStrategy(GameObjectCollection gameObjects, Renderable renderable,
                             Vector2 heartSize, Counter livesCounter, Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.renderable = renderable;
        this.heartSize = heartSize;
        this.livesCounter = livesCounter;
    }

    /**
     * Calls super for brick removal. Creates a new Heart object  and adds it to gameObjects.
     * @param object1 Brick.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        Vector2 brickCenter = object1.getCenter();
        super.onCollision(object1, object2);
        GameObject heart = new Heart(brickCenter, this.heartSize, this.renderable, HEART_VELOCITY,
                this.livesCounter);
        heart.setCenter(brickCenter);
        this.gameObjects.addGameObject(heart);
    }
}
