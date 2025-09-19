package bricker.brick_strategies;

import static bricker.main.Constants.RAND_ALL_BOUND;
import static bricker.main.Constants.RAND_SPECIAL_BOUND;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;


/**
 * The StrategyFactory class is responsible for providing random collision strategies according to a
 * pre-defined probability.
 */
public class StrategyFactory {
    private final Random random;
    private final GameManager gameManager;
    private final GameObjectCollection gameObjects;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final Renderable tempPaddleImage;
    private final Vector2 extraPaddleSize;
    private final Counter extraPaddleLives;
    private final Renderable puckRenderable;
    private final Sound puckSound;
    private final Renderable heartRenderable;
    private final Vector2 heartSize;
    private final Counter livesCounter;
    private final Counter hitsBeforeCameraChange;
    private final Counter bricksLeft;

    /**
     * Constructor. All parameters given are passed to the strategies constructors.
     * @param gameManager            Manager of the game.
     * @param gameObjects            The collection of game objects.
     * @param windowController       Responsible for managing the game window.
     * @param windowDimensions       Window dimensions vector.
     * @param userInputListener      Object responsible for tracking player's keyboard presses.
     * @param tempPaddleRenderable   Image of the paddle.
     * @param tempPaddleSize         Size vector of the paddle.
     * @param tempPaddleLives        Counter that saves how many collisions with the paddle cause it to
     *                               disappear.
     * @param puckRenderable         Image of the puck ball.
     * @param puckSound              Sound when there's collision with a ball.
     * @param heartRenderable        Image of the heart.
     * @param heartSize              Size vector of the heart.
     * @param livesCounter           Counter that saves the player's remaining lives.
     * @param hitsBeforeCameraChange Counter that saves how many hits the ball has had before current
     *                               collision.
     * @param bricksLeft             Counter that saves how many bricks are left in the game
     *                               (yet to collide with a ball).
     */
    public StrategyFactory(GameManager gameManager, GameObjectCollection gameObjects,
                           WindowController windowController, Vector2 windowDimensions,
                           UserInputListener userInputListener, Renderable tempPaddleRenderable,
                           Vector2 tempPaddleSize, Counter tempPaddleLives, Renderable puckRenderable,
                           Sound puckSound, Renderable heartRenderable, Vector2 heartSize,
                           Counter livesCounter, Counter hitsBeforeCameraChange, Counter bricksLeft) {
        this.random = new Random();
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.inputListener = userInputListener;
        this.windowController = windowController;
        this.tempPaddleImage = tempPaddleRenderable;
        this.extraPaddleSize = tempPaddleSize;
        this.windowDimensions = windowDimensions;
        this.extraPaddleLives = tempPaddleLives;
        this.puckRenderable = puckRenderable;
        this.puckSound = puckSound;
        this.heartRenderable = heartRenderable;
        this.heartSize = heartSize;
        this.livesCounter = livesCounter;
        this.hitsBeforeCameraChange = hitsBeforeCameraChange;
        this.bricksLeft = bricksLeft;
    }

    /**
     * @return Random collision strategy object with a pre-defined probability:
     * 0.5 for BasicCollisionStrategy.
     * 0.1 for each of the other strategies.
     */
    public CollisionStrategy getRandomStrategy() {
        int rand = this.random.nextInt(RAND_ALL_BOUND);
        if (rand == 0) {
            return this.getRandomSpecialStrategy(RAND_SPECIAL_BOUND);
        }
        return new BasicCollisionStrategy(this.gameObjects, this.bricksLeft);
    }

    /**
     * @param bound Used to determine whether to include or exclude DoubleStrategy in randomization.
     * @return Random special collision strategy object with uniform probability.
     */
    public CollisionStrategy getRandomSpecialStrategy(int bound) {
        int rand = this.random.nextInt(bound);
        switch (rand) {
            case 0:
                return new PucksStrategy(this.gameObjects, this.puckRenderable, this.puckSound,
                        this.bricksLeft);
            case 1:
                return new CameraChangeStrategy(this.gameObjects, this.gameManager, this.windowController,
                        this.hitsBeforeCameraChange, this.bricksLeft);
            case 2:
                return new TempPaddleStrategy(this.gameObjects, this.extraPaddleSize, this.tempPaddleImage,
                        this.inputListener, this.windowDimensions, this.extraPaddleLives, this.bricksLeft);
            case 3:
                return new HeartFallStrategy(this.gameObjects, this.heartRenderable, this.heartSize,
                        this.livesCounter, this.bricksLeft);
            case 4:
                return new DoubleStrategy(this.gameObjects, this, this.bricksLeft);
        }
        return null;
    }
}
