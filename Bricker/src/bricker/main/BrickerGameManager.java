package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import static bricker.main.Constants.*;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * The BrickerGameManager class is responsible for the management of the Bricker game. The class
 * Manages the initialization of the game, including setting up game objects, background, borders, and
 * other necessary components.
 * It Controls the game's main execution loop, handles removing objects in case needed, and updates the game
 * state in each frame; manages game state transitions.
 */
public class BrickerGameManager extends GameManager {
    private final int brickRows;
    private final int brickCols;
    private Counter bricksCounter;
    private Counter livesLeft;
    private Ball ball;
    private Counter paddleLivesCounter;
    private Counter hitsBeforeCameraChange;
    private StrategyFactory strategyFactory;
    private WindowController windowController;
    private final Vector2 windowDimensions;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * Constructor.
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param brickRows        The number of rows for the bricks layout.
     * @param brickCols        The number of columns for the bricks layout.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickRows, int brickCols) {
        super(windowTitle, windowDimensions);
        this.brickRows = brickRows;
        this.brickCols = brickCols;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Initializes the game, sets up necessary fields and creates game objects.
     * @param imageReader      Contains a single method: readImage, which reads an image from disk. See its
     *                         documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from disk. See its
     *                         documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether a given key is
     *                         currently pressed by the user or not. See its documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.paddleLivesCounter = new Counter();
        this.livesLeft = new Counter(Constants.INITIAL_HEARTS);
        this.hitsBeforeCameraChange = new Counter();
        this.bricksCounter = new Counter(this.brickRows * this.brickCols);
        // set background and create game objects
        setBackground();
        createBorders();
        createLivesCounters();
        createMainBall();
        createMainPaddle();
        createStrategyFactory();
        createBricks();
    }

    /**
     * Updates the game state based on the elapsed time since the last frame.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // check if objects are no longer in use in the game and remove them
        checkIfRemovingObjectNeeded();
        // check camera status
        setCameraNull();
        // if livesLeft has passed max, set it to max
        boundLivesLeft();
        // check if win or lose
        String prompt = manageWinOrLose();
        // if win / lose ask if user wants to play again
        resetOrClose(prompt);
    }

    /**
     * The entry point for starting the Bricker game.
     * @param args Command-line arguments for customizing brick layout; rows and cols (optional).
     */
    public static void main(String[] args) {
        // default settings
        int bricks_cols = DEFAULT_BRICKS_COLS;
        int bricks_rows = DEFAULT_BRICKS_ROWS;
        // change defaults in case 2 parameters were given
        if (args.length == 2) {
            bricks_cols = Integer.parseInt(args[0]);
            bricks_rows = Integer.parseInt(args[1]);
        }
        // run game
        BrickerGameManager brickGameManager = new BrickerGameManager(WINDOW_TITLE,
                new Vector2(WINDOW_X, WINDOW_Y), bricks_rows, bricks_cols);
        brickGameManager.run();
    }

    private void setBackground() {
        Renderable backgroundImage = this.imageReader.readImage(BACKGROUND_IMG_PATH,true);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(),
                this.windowDimensions.y()), backgroundImage);
        // add background to game objects
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    private void createBorders() {
        // create borders
        GameObject leftBorder = new GameObject(Vector2.ZERO,
                new Vector2(BORDER_THICKNESS, this.windowDimensions.y()), null);
        GameObject rightBorder = new GameObject(new Vector2(this.windowDimensions.x() - BORDER_THICKNESS,
                0), new Vector2(BORDER_THICKNESS, this.windowDimensions.y()), null);
        GameObject topBorder = new GameObject(Vector2.ZERO,
                new Vector2(this.windowDimensions.x(), BORDER_THICKNESS), null);
        // add borders to game objects
        gameObjects().addGameObject(leftBorder, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightBorder, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(topBorder, Layer.STATIC_OBJECTS);
    }

    private void createMainBall() {
        Sound collisionSound = this.soundReader.readSound(SOUND_PATH);
        Renderable mainBallImage = this.imageReader.readImage(BALL_IMG_PATH, true);
        // create ball and set ball to start at required location with an init velocity
        this.ball = new Ball(this.windowDimensions.mult(0.5f),
                BALL_DIAMETER_VEC, mainBallImage, collisionSound);
        setBallRandomVelocity();
        // add ball to game objects
        gameObjects().addGameObject(this.ball);
        // set tag for the ball, for managing collisions
        this.ball.setTag(BALL);
    }

    private void setBallRandomVelocity() {
        // sets velocity to one of the diagonals / anti-diagonals
        float ballVelocityX = BALL_SPEED;
        float ballVelocityY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelocityX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelocityY *= -1;
        }
        this.ball.setVelocity(new Vector2(ballVelocityX, ballVelocityY));
    }

    private void createMainPaddle() {
        // create relocation vec in case user trys to move paddle beyond frame bounds
        Vector2 relocationVec = new Vector2(this.windowDimensions.x() - PADDLE_WIDTH,
                this.windowDimensions.y() - PADDLE_DIST_FROM_FRAME);
        Renderable paddleImage = this.imageReader.readImage(PADDLE_IMG_PATH, true);
        // create paddle and set it to start at required location in screen
        GameObject paddle = new Paddle(new Vector2(this.windowDimensions.x() * 0.5f,
                this.windowDimensions.y() - PADDLE_DIST_FROM_FRAME),
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage, this.inputListener, relocationVec);
        // add paddle to game objects
        gameObjects().addGameObject(paddle);
        // set tag for the paddle, for managing collisions
        paddle.setTag(PADDLE);
    }

    private void createBricks() {
        Renderable brickImage = this.imageReader.readImage(BRICK_IMG_PATH, false);
        // create bricks
        float totalAddedSpace = DISTANCE_BETWEEN_BRICKS * (this.brickCols - 1);
        float brickWidth = (this.windowDimensions.x() - 2 * BORDER_THICKNESS - totalAddedSpace) /
                this.brickCols;
        for (int j = 0; j < this.brickCols; j++) {
            for (int i = 0; i < this.brickRows; i++) {
                GameObject brick = new Brick(
                        new Vector2(j * (brickWidth + DISTANCE_BETWEEN_BRICKS) + BORDER_THICKNESS,
                                i * (BRICK_HEIGHT + DISTANCE_BETWEEN_BRICKS) + BORDER_THICKNESS),
                        new Vector2(brickWidth, BRICK_HEIGHT), brickImage,
                        this.strategyFactory.getRandomStrategy());
                // add brick to game objects
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                // set tag for the brick, for managing collisions
                brick.setTag(BRICK);
            }
        }
    }

    private void createLivesCounters() {
        // graphical
        Renderable image = imageReader.readImage(HEART_IMG_PATH, true);
        GameObject graphicalLifeCounter = new GraphicalLivesCounter(new Vector2(
                HEART_DIAMETER + DISTANCE_BETWEEN_HEARTS,
                this.windowDimensions.y() - HEARTS_DIST_FROM_FRAME),
                new Vector2(HEART_DIAMETER, HEART_DIAMETER),
                this.livesLeft,
                image,
                this.gameObjects(),
                this.livesLeft.value());
        this.gameObjects().addGameObject(graphicalLifeCounter, Layer.UI);
        // numerical
        GameObject numericLifeCounter = new NumericalLivesCounter(this.livesLeft,
                new Vector2(BORDER_THICKNESS, windowDimensions.y() - HEARTS_DIST_FROM_FRAME),
                new Vector2(HEARTS_NUMERICAL_DIAMETER, HEARTS_NUMERICAL_DIAMETER),
                this.gameObjects());
        this.gameObjects().addGameObject(numericLifeCounter, Layer.UI);
    }

    private void checkIfRemovingObjectNeeded() {
        for (GameObject obj : gameObjects()) {
            if (((obj.getTag().equals(PUCK) || obj.getTag().equals(HEART)) &&
                    obj.getCenter().y() > this.windowDimensions.y()) ||
                    (obj.getTag().equals(TEMP_PADDLE) && this.paddleLivesCounter.value() <= 0)) {
                gameObjects().removeGameObject(obj);
            }
        }
    }

    private void boundLivesLeft() {
        // if livesLeft has passed max, set it to max
        if (this.livesLeft.value() >= MAX_HEARTS) {
            this.livesLeft.reset();
            this.livesLeft.increaseBy(MAX_HEARTS);
        }
    }

    private void setCameraNull() {
        // if there have been COLLISIONS_PER_CAMERA_CHANGE hits after camera change
        int hitsSinceCameraChange = this.ball.getCollisionCounter() - this.hitsBeforeCameraChange.value();
        if (super.camera() != null) {
            if (hitsSinceCameraChange >= COLLISIONS_PER_CAMERA_CHANGE) {
                super.setCamera(null);
            }
        }
    }

    private String manageWinOrLose() {
        // get ball center location to check if it went out of bound
        float ballHeight = this.ball.getCenter().y();
        String prompt = "";
        // if player wins update prompt
        if ((this.bricksCounter.value() == 0) || (this.inputListener.isKeyPressed(KeyEvent.VK_W))) {
            prompt = YOU_WIN;
        }
        // if player loses update prompt, otherwise there are lives left so reset ball and decrement lives
        else if (ballHeight > windowDimensions.y()) {
            this.livesLeft.decrement();
            if (this.livesLeft.value() > 0) {
                this.ball.setCenter(windowDimensions.mult(0.5f));
                setBallRandomVelocity();
            } else {
                prompt = YOU_LOSE;
            }
        }
        return prompt;
    }

    private void resetOrClose(String prompt) {
        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN;
            // if player has won or lost, raise a pop-up window asking if player wants to play again.
            if (windowController.openYesNoDialog(prompt)) {
                // if player answers yes, reset game
                windowController.resetGame();
            } else {
                // otherwise close the game
                windowController.closeWindow();
            }
        }
    }

    private void createStrategyFactory() {
        // get all information needed for the strategies that exist
        Renderable puckImage = imageReader.readImage(PUCK_IMG_PATH, true);
        Sound puckSound = soundReader.readSound(SOUND_PATH);
        Renderable tempPaddleImage = imageReader.readImage(PADDLE_IMG_PATH, false);
        Renderable heartImage = imageReader.readImage(HEART_IMG_PATH, true);
        Vector2 heartSize = new Vector2(HEART_DIAMETER, HEART_DIAMETER);
        // create StrategyFactory object
        this.strategyFactory = new StrategyFactory(this,
                this.gameObjects(), this.windowController, this.windowDimensions, this.inputListener,
                tempPaddleImage, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), this.paddleLivesCounter,
                puckImage, puckSound, heartImage, heartSize, this.livesLeft, this.hitsBeforeCameraChange,
                this.bricksCounter);
    }
}
