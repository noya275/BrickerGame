package bricker.brick_strategies;

import static bricker.main.Constants.EXCLUDE_DOUBLE_BOUND;
import static bricker.main.Constants.INCLUDE_DOUBLE_BOUND;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.ArrayList;


/**
 * The DoubleStrategy class represents the collision strategy for a brick with the ability to exhibit two
 * additional behaviors simultaneously upon collision. The class contains an array of strategies as a
 * field, and it utilizes the StrategyFactory class to random its strategies and fill this array.
 */
public class DoubleStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private final ArrayList<CollisionStrategy> collisionStrategies;
    private final StrategyFactory strategyFactory;

    /**
     * Constructor.
     * @param gameObjects        The collection of game objects.
     * @param strategyFactory Class responsible for randomizing collision strategies.
     * @param bricksLeft Counter that saves how many bricks are left in the game (yet to collide with a ball).
     */
    public DoubleStrategy(GameObjectCollection gameObjects, StrategyFactory strategyFactory,
                          Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.collisionStrategies = new ArrayList<>();
        this.strategyFactory = strategyFactory;

        // if first strategy randomized is DoubleStrategy, exclude it in the second, otherwise, add first
        CollisionStrategy firstStrategy = strategyFactory.getRandomSpecialStrategy(INCLUDE_DOUBLE_BOUND);
        int bound = INCLUDE_DOUBLE_BOUND;
        if (firstStrategy instanceof DoubleStrategy) {
            bound = EXCLUDE_DOUBLE_BOUND;
            randAgainIfStrategyIsDouble();
        } else {
            this.collisionStrategies.add(firstStrategy);
        }
        // if second strategy randomized is not DoubleStrategy, add second
        CollisionStrategy secondStrategy = strategyFactory.getRandomSpecialStrategy(bound);
        if (secondStrategy instanceof DoubleStrategy) {
            randAgainIfStrategyIsDouble();
        } else {
            this.collisionStrategies.add(secondStrategy);
        }
    }

    /**
     * Calls super for brick removal. Activates all randomized collision strategies.
     * @param object1 Brick.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        for (CollisionStrategy strategy : this.collisionStrategies) {
            strategy.onCollision(object1, object2);
        }
        super.onCollision(object1, object2);
    }

    private void randAgainIfStrategyIsDouble() {
        // if a strategy is DoubleStrategy, randomize 2 strategies excluding DoubleStrategy
        CollisionStrategy firstStrategy =
                this.strategyFactory.getRandomSpecialStrategy(EXCLUDE_DOUBLE_BOUND);
        CollisionStrategy secondStrategy =
                this.strategyFactory.getRandomSpecialStrategy(EXCLUDE_DOUBLE_BOUND);
        // add strategies to array
        this.collisionStrategies.add(firstStrategy);
        this.collisionStrategies.add(secondStrategy);
    }
}
