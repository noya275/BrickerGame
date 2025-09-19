package bricker.gameobjects;

import static bricker.main.Constants.GREEN_NUMERICAL_LOWER_BOUND;
import static bricker.main.Constants.YELLOW_NUMERICAL_VALUE;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;


/**
 * The NumericalLivesCounter class is responsible for tracking and displaying the numerical count of the
 * player's remaining lives. This numerical lives counter provides a quantitative representation of the
 * player's status.
 */
public class NumericalLivesCounter extends GameObject {
    private final TextRenderable textObject;
    private final Counter livesLeft;

    /**
     * Constructor.
     * @param livesLeft Counter that saves the player's remaining lives.
     * @param topLeftCorner The top-left corner position of the numerical lives counter.
     * @param dimensions Size vector of the numerical lives counter.
     * @param gameObjects The collection of game objects.
     */
    public NumericalLivesCounter(Counter livesLeft, Vector2 topLeftCorner, Vector2 dimensions,
                                 GameObjectCollection gameObjects) {
        super(topLeftCorner, Vector2.ZERO, null);
        this.textObject = new TextRenderable(String.format("%d", livesLeft.value()));
        this.textObject.setColor(Color.GREEN);
        this.livesLeft = livesLeft;
        gameObjects.addGameObject(new GameObject(topLeftCorner, dimensions, this.textObject), Layer.UI);
    }

    /**
     * Updates numerical lives counter in players screen in each frame.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.textObject.setString(String.format("%d", this.livesLeft.value()));
        if (this.livesLeft.value() >= GREEN_NUMERICAL_LOWER_BOUND) {
            this.textObject.setColor(Color.GREEN);
        } else if (this.livesLeft.value() == YELLOW_NUMERICAL_VALUE) {
            this.textObject.setColor(Color.YELLOW);
        } else {
            this.textObject.setColor(Color.RED);
        }
    }
}