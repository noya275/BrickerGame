package bricker.gameobjects;

import static bricker.main.Constants.PADDLE_SPEED;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;


/**
 * The Paddle class represents the player-controlled paddle within the game. It incorporates properties such
 * as responding to user input, initial velocity.
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final Vector2 relocationVec;

    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the paddle.
     * @param dimensions Size vector of the paddle.
     * @param renderable Image of the paddle.
     * @param inputListener Object responsible for tracking player's keyboard presses.
     * @param relocationVec Vector that contains coordinates to locate paddle in, in case paddle is out of
     *                      window bounds.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 relocationVec) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.relocationVec = relocationVec;
    }

    /**
     * Updates paddle location and velocity in each frame.
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
        relocatePaddleOutOfWindow();
        setPaddleVelocity();
    }

    private void relocatePaddleOutOfWindow() {
        // in case of reaching window bounds
        if (this.getTopLeftCorner().x() < 0)
        {
            this.setTopLeftCorner(new Vector2(0, this.relocationVec.y()));
        }
        else if (this.getTopLeftCorner().x() > this.relocationVec.x())
        {
            this.setTopLeftCorner(new Vector2(this.relocationVec.x(), this.relocationVec.y()));
        }
    }

    private void setPaddleVelocity() {
        // changes paddle velocity according to player's keyboard presses
        Vector2 movDirection = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movDirection = movDirection.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movDirection = movDirection.add(Vector2.RIGHT);
        }
        setVelocity(movDirection.mult(PADDLE_SPEED));
    }
}
