package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import rodrigo.pongy.listener.ResetListener;
import rodrigo.pongy.manager.ScoreManager;

public class Ball implements ResetListener {

	private Sprite ball;

	private Vector2 velocity;
	private float initialSpeed;
	private Vector2 playAreaCenter;

	public ScoreManager scoreManager;

	private Racket leftRacket;
	private Racket rightRacket;

	private float bounceCount;
	private boolean hasBouncedOnSide;
	private boolean hasBouncedOnTopBottom;


	// Note: the direction the ball goes to after spawning will be random
	// leftRacketRightEdge: left racket's right edge, used to calculate the collisions.
	// Also used on the right racket's collision calculations, which means if they aren't symmetrical, everything screws
	// up.
	public Ball(Texture texture, float initialSpeed, float scaleFactor, Vector2 playAreaCenter, Racket leftRacket, Racket rightRacket) {
		this.playAreaCenter = playAreaCenter;
		this.initialSpeed = initialSpeed;

		ball = new Sprite(texture);
		ball.setSize(ball.getWidth() * scaleFactor, ball.getHeight() * scaleFactor);

		reset();

		this.leftRacket = leftRacket;
		this.rightRacket = rightRacket;

		bounceCount = 0;
		hasBouncedOnSide = false;

	}

	@Override
	public void resetGame() {
		reset();
	}

	public void reset() {
		// Only -1 and 1 are needed, so a random boolean should do the trick
		int xVel = MathUtils.randomSign();
		int yVel = MathUtils.randomSign();

		ball.setPosition(playAreaCenter.x - ball.getWidth() / 2, playAreaCenter.y + ball.getHeight() / 2);
		velocity = new Vector2(xVel, yVel).scl(initialSpeed);

		//bounceCount /= 2;
	}

	private void checkCollisions() {

		float scaleLimit = 500f * bounceCount;

		// Left collisions: goal
		if (ball.getX() <= 0 + leftRacket.getSprite().getWidth() / 2 && !hasBouncedOnSide) {
			scoreManager.score(Racket.POSITIONS.LEFT);
		}
		// Right collisions: goal
		else if (ball.getX() >= rightRacket.getSprite().getX() + rightRacket.getSprite().getWidth() / 2 && !hasBouncedOnSide) {
			scoreManager.score(Racket.POSITIONS.RIGHT);
		}

		// Left collisions: racket
		else if (ball.getX() < leftRacket.getSprite().getX() + leftRacket.getSprite().getWidth() && !hasBouncedOnSide) {
			// Check if it's within the racket's coordinates
			if (ball.getY() > leftRacket.getSprite().getY() && ball.getY() + ball.getHeight() < leftRacket.getSprite().getY() + leftRacket.getSprite().getHeight()) {
				float alpha = leftRacket.getSprite().getY() + leftRacket.getSprite().getHeight() / 2;

				if (ball.getY() + ball.getHeight() > alpha) {
					alpha = alpha - ball.getY() / 2;
				} else {
					alpha = alpha + ball.getY() / 2;
				}

				velocity.x = velocity.x + -alpha;
				velocity.y += alpha;

				hasBouncedOnSide = true;
				bounceCount++;
			}
		}
		// Right collisions: racket
		else if (ball.getX() + ball.getWidth() > rightRacket.getSprite().getX() && !hasBouncedOnSide) {
			// Check if it's within the racket's coordinates
			if (ball.getY() > rightRacket.getSprite().getY() && ball.getY() + ball.getHeight() < rightRacket.getSprite().getY() + rightRacket.getSprite().getHeight()) {
				float alpha = rightRacket.getSprite().getY() + rightRacket.getSprite().getHeight() / 2;

				if (ball.getY() + ball.getHeight() > alpha) {
					alpha = alpha - ball.getY() / 2;
				} else {
					alpha = alpha + ball.getY() / 2;
				}

				velocity.x = velocity.x + -alpha;
				velocity.y += alpha;

				hasBouncedOnSide = true;
				bounceCount++;
			}
		}

		// Bottom collisions
		else if (ball.getY() <= 0 && !hasBouncedOnTopBottom) {
			velocity.y *= -1;
			hasBouncedOnTopBottom = true;
		}
		// Top collisions
		else if (ball.getY() >= Gdx.graphics.getHeight() - ball.getHeight() && !hasBouncedOnTopBottom) {
			velocity.y *= -1;
			hasBouncedOnTopBottom = true;
		}

		// Check if ball the as passed the middle of the screen, X
		else if (ball.getX() > Gdx.graphics.getWidth() / 2 - ball.getWidth() && ball.getX() < Gdx.graphics.getWidth() / 2 + ball.getWidth()) {
			hasBouncedOnSide = false;
		}
		// Check if the ball has passed the middle of the screen, Y
		else if (ball.getY() > Gdx.graphics.getHeight() / 2 - ball.getHeight() && ball.getY() < Gdx.graphics.getHeight() / 2 + ball.getHeight()) {
			hasBouncedOnTopBottom = false;
		}

	}

	public void update() {
		checkCollisions();
		ball.translate(
				velocity.x * Gdx.graphics.getDeltaTime(),
				velocity.y * Gdx.graphics.getDeltaTime());
	}


	// Useful functions

	public Sprite getSprite() {
		return ball;
	}


	// Cleaner than getSprite().getTexture().dispose()
	public void dispose() {
		ball.getTexture().dispose();
	}
}
