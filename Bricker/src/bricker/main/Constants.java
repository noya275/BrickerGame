package bricker.main;

import danogl.util.Vector2;


/**
 * The Constants class contains various constant values used throughout the game.
 * These constants include file paths, object names, window dimensions, speeds, distances, and other
 * parameters that define the behavior and appearance of game elements.
 */
public class Constants {
    /**
     * Constructor.
     */
    public Constants() {}

    /**
     * Window title of the game.
     */
    public static final String WINDOW_TITLE = "Bricker";

    /**
     * Path for the Background image used in the game.
     */
    public static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * Path for the heart image used in the game.
     */
    public static final String HEART_IMG_PATH = "assets/heart.png";

    /**
     * Path for the sound file used in the game.
     */
    public static final String SOUND_PATH = "assets/blop_cut_silenced.wav";

    /**
     * Path for the ball image used in the game.
     */
    public static final String BALL_IMG_PATH = "assets/ball.png";

    /**
     * Path for the paddle image used in the game.
     */
    public static final String PADDLE_IMG_PATH = "assets/paddle.png";

    /**
     * Path for the brick image used in the game.
     */
    public static final String BRICK_IMG_PATH = "assets/brick.png";

    /**
     * Path for the puck image used in the game.
     */
    public static final String PUCK_IMG_PATH = "assets/mockBall.png";

    /**
     * Constant representing the ball object in the game.
     */
    public static final String BALL = "Ball";

    /**
     * Constant representing the paddle object in the game.
     */
    public static final String PADDLE = "Paddle";

    /**
     * Constant representing the brick object in the game.
     */
    public static final String BRICK = "Brick";

    /**
     * Constant representing the puck object in the game.
     */
    public static final String PUCK = "Puck";

    /**
     * Constant representing the temporary paddle object in the game.
     */
    public static final String TEMP_PADDLE = "TempPaddle";

    /**
     * Constant representing the heart object in the game.
     */
    public static final String HEART = "Heart";

    /**
     * String representing the win message displayed in the game.
     */
    public static final String YOU_WIN = "You win!";

    /**
     * String representing the loss message displayed in the game.
     */
    public static final String YOU_LOSE = "You lose!";

    /**
     * String representing the play again prompt displayed in the game.
     */
    public static final String PLAY_AGAIN = " Play again?";

    /**
     * Default X coordinate of the game window.
     */
    public static final int WINDOW_X = 700;

    /**
     * Default Y coordinate of the game window.
     */
    public static final int WINDOW_Y = 500;

    /**
     * Default number of brick columns in the game.
     */
    public static final int DEFAULT_BRICKS_COLS = 8;

    /**
     * Default number of brick rows in the game.
     */
    public static final int DEFAULT_BRICKS_ROWS = 7;

    /**
     * Thickness of the window borders.
     */
    public static final float BORDER_THICKNESS = 10f;

    /**
     * Speed of the ball object.
     */
    public static final float BALL_SPEED = 250;

    /**
     * Diameter of the ball object.
     */
    public static final Vector2 BALL_DIAMETER_VEC = new Vector2(20f, 20f);

    /**
     * Number of pucks to initialize behind a brick with PucksStrategy.
     */
    public static final int PUCKS_BEHIND_BRICK = 2;

    /**
     * Width of the paddle object.
     */
    public static final float PADDLE_WIDTH = 100f;

    /**
     * Height of the paddle object.
     */
    public static final float PADDLE_HEIGHT = 15f;

    /**
     * Movement speed of the paddle object.
     */
    public static final float PADDLE_SPEED = 350f;

    /**
     * Number of collisions of the ball before the temporary paddle disappears.
     */
    public static final int COLLISIONS_TO_DISAPPEAR = 4;

    /**
     * Height of the brick object.
     */
    public static final float BRICK_HEIGHT = 15f;

    /**
     * Distance of the paddle from the frame Y-coordinate-wise.
     */
    public static final float PADDLE_DIST_FROM_FRAME = 60f;

    /**
     * Distance of the hearts from the frame Y-coordinate-wise.
     */
    public static final float HEARTS_DIST_FROM_FRAME = 36f;

    /**
     * Distance between bricks.
     */
    public static final float DISTANCE_BETWEEN_BRICKS = 2f;

    /**
     * Distance between hearts X-coordinate-wise.
     */
    public static final float DISTANCE_BETWEEN_HEARTS = 6f;

    /**
     * Diameter of the hearts displayed numerically.
     */
    public static final float HEARTS_NUMERICAL_DIAMETER = 28;

    /**
     * Number of collisions of the ball allowed per camera change.
     */
    public static final int COLLISIONS_PER_CAMERA_CHANGE = 4;

    /**
     * Initial number of lives in the game.
     */
    public static final int INITIAL_HEARTS = 3;

    /**
     * Maximum number of lives in the game.
     */
    public static final int MAX_HEARTS = 4;

    /**
     * Diameter of the heart object.
     */
    public static final float HEART_DIAMETER = 30;

    /**
     * Velocity vector of a falling heart.
     */
    public static final Vector2 HEART_VELOCITY = new Vector2(0, 100);

    /**
     * Bound of integers randomization used to choose brick strategies including DoubleStrategy.
     */
    public static final int INCLUDE_DOUBLE_BOUND = 5;

    /**
     * Bound of integers randomization used to choose brick strategies excluding DoubleStrategy.
     */
    public static final int EXCLUDE_DOUBLE_BOUND = 4;

    /**
     * Bound of integers randomization used to choose between special brick strategies only.
     */
    public static final int RAND_SPECIAL_BOUND = 5;

    /**
     * Bound of integers randomization used to choose between basic brick strategy or special ones.
     */
    public static final int RAND_ALL_BOUND = 2;

    /**
     * If livesCounter is more than this value, the color of the numerical lives text should change to green.
     */
    public static final int GREEN_NUMERICAL_LOWER_BOUND = 3;

    /**
     * If livesCounter equals this value, the color of the numerical lives text should change to yellow.
     */
    public static final int YELLOW_NUMERICAL_VALUE = 2;
}
