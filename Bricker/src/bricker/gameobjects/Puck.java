package bricker.gameobjects;

import static bricker.main.Constants.*;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;


/**
 * The Puck class represents a puck ball object in the game. This puck serves as a power-up that grants the
 * player an additional few balls to hit. The class responds to collisions by changing direction and
 * playing collision sounds. Inherits from Ball.
 */
public class Puck extends Ball{
    /**
     * Constructor.
     * @param topLeftCorner The top-left corner position of the puck.
     * @param renderable Image of the puck ball.
     * @param collisionSound Sound when there's a collision with a ball.
     */
    public Puck(Vector2 topLeftCorner, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, BALL_DIAMETER_VEC.mult(0.75f), renderable, collisionSound);
        // set tag, for managing collisions
        this.setTag(PUCK);
        // set random velocity to one of the vectors in upper half of unit circle
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float puckVelocityX = (float)Math.cos(angle) * BALL_SPEED;
        float puckVelocityY = (float)Math.sin(angle) * BALL_SPEED;
        setVelocity(new Vector2(puckVelocityX, puckVelocityY));
    }
}
