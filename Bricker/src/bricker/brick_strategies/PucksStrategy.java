package bricker.brick_strategies;

import static bricker.main.Constants.PUCKS_BEHIND_BRICK;

import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * The PucksStrategy class represents the collision strategy for a brick that triggers the appearance of
 * additional balls (pucks) upon collision.
 */
public class PucksStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final Renderable renderable;
    private final Sound sound;

    /**
     * Constructor.
     * @param gameObjects The collection of game objects.
     * @param renderable Image of the puck ball.
     * @param sound Sound when there's collision with a ball.
     * @param bricksLeft Counter that saves how many bricks are left in the game (yet to collide with a ball).
     */
    public PucksStrategy(GameObjectCollection gameObjects, Renderable renderable, Sound sound,
                         Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.renderable = renderable;
        this.sound = sound;
    }

    /**
     * Calls super for brick removal. Creates new pucks objects and adds them to gameObjects.
     * @param object1 Brick.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        // Calculating the size of a puck depending on the size of the
        Vector2 brickCenter = object1.getCenter();
        super.onCollision(object1, object2);
        for (int i = 0; i < PUCKS_BEHIND_BRICK; i++){
            GameObject puck = new Puck(brickCenter, renderable, sound);
            this.gameObjects.addGameObject(puck);
            puck.setCenter(brickCenter);
        }
    }
}
