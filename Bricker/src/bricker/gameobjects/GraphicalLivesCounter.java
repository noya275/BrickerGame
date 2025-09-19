package bricker.gameobjects;

import static bricker.main.Constants.BORDER_THICKNESS;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Stack;


/**
 * The GraphicalLivesCounter class is responsible for a visual representation of the player's remaining
 * lives in the game, using Heart objects. This graphical counter is displayed on the game screen to
 * provide a visual indication of the player's status.
 */
public class GraphicalLivesCounter extends GameObject {
    private final Vector2 topLeftCorner;
    private final Vector2 diameterVec;
    private final Renderable renderable;
    private final GameObjectCollection gameObjects;
    private final Counter livesLeft;
    private int currentHeartsNumber;
    private final Stack<GameObject> heartsStack;

    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the graphical lives counter.
     * @param diameterVec Size vector of the graphical lives counter.
     * @param livesLeft Counter that saves the player's remaining lives.
     * @param renderable image of a heart.
     * @param gameObjects The collection of game objects.
     * @param currentHeartsNumber How many Lives left at the moment.
     */
    public GraphicalLivesCounter(Vector2 topLeftCorner, Vector2 diameterVec, Counter livesLeft,
                                 Renderable renderable, GameObjectCollection gameObjects,
                                 int currentHeartsNumber) {
        super(topLeftCorner, Vector2.ZERO, null);
        this.topLeftCorner = topLeftCorner;
        this.diameterVec = diameterVec;
        this.renderable = renderable;
        this.gameObjects = gameObjects;
        this.livesLeft = livesLeft;
        this.currentHeartsNumber = currentHeartsNumber;
        this.heartsStack = new Stack<>();
        this.createHearts(0);
    }

    /**
     * Updates graphical lives counter in players screen in each frame.
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
        // create initial amount of hearts
        createHearts(this.currentHeartsNumber);
        // remove used hearts
        while (currentHeartsNumber > this.livesLeft.value()) {
            GameObject heartToRemove = this.heartsStack.pop();
            this.gameObjects.removeGameObject(heartToRemove, Layer.UI);
            currentHeartsNumber--;
        }
    }

    private void createHearts(int heartIdx) {
        for (int i = heartIdx; i < this.livesLeft.value(); i++) {
            GameObject heart = new Heart(new Vector2(i * topLeftCorner.x() + 4 * BORDER_THICKNESS,
                    topLeftCorner.y()), this.diameterVec, this.renderable);
            // push heart to stack
            this.heartsStack.push(heart);
            // add heart to game objects
            this.gameObjects.addGameObject(heart, Layer.UI);
            // if it's not the first creation of hearts, increment currentHeartsNumber
            if (heartIdx != 0) {
                this.currentHeartsNumber++;
            }
        }
    }
}
