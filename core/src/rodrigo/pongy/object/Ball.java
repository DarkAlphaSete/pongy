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
	}

	private void checkCollisions() {

		// Left collisions: goal
		if (ball.getX() <= 0 + leftRacket.getSprite().getWidth() / 2) {
			scoreManager.score(Racket.POSITIONS.LEFT);
		}
		// Right collisions: goal
		if (ball.getX() >= Gdx.graphics.getWidth() - rightRacket.getSprite().getWidth() / 2) {
			scoreManager.score(Racket.POSITIONS.RIGHT);
		}

		// Left collisions: racket
		if (ball.getX() <= leftRacket.getSprite().getX() + leftRacket.getSprite().getWidth()) {
			// Check if it's within the racket's coordinates
			if (ball.getY() >= leftRacket.getSprite().getY() && ball.getY() + ball.getHeight() <= leftRacket.getSprite().getY() + leftRacket.getSprite().getHeight()) {
				float alpha = leftRacket.getSprite().getY() + leftRacket.getSprite().getHeight() / 2;

				if (ball.getY() + ball.getHeight() > alpha) {
					alpha = alpha - ball.getY() / 2;
				} else {
					alpha = alpha + ball.getY() / 2;
				}

				velocity.x = velocity.x * -1 + MathUtils.clamp(alpha, -2, 2);
				velocity.y += MathUtils.clamp(alpha, -10, 10);
			}
		}
		// Right collisions: racket
		if (ball.getX() + ball.getWidth() >= Gdx.graphics.getWidth() - rightRacket.getSprite().getWidth()) {
			// Check if it's within the racket's coordinates
			if (ball.getY() >= rightRacket.getSprite().getY() && ball.getY() + ball.getHeight() <= rightRacket.getSprite().getY() + rightRacket.getSprite().getHeight()) {
				float alpha = rightRacket.getSprite().getY() + rightRacket.getSprite().getHeight() / 2;

				if (ball.getY() + ball.getHeight() > alpha) {
					alpha = alpha - ball.getY() / 2;
				} else {
					alpha = alpha + ball.getY() / 2;
				}

				velocity.x = velocity.x * -1 + MathUtils.clamp(alpha, -2, 2);
				velocity.y += MathUtils.clamp(alpha, -10, 10);
			}
		}

		// Bottom collisions
		if (ball.getY() <= 0) {
			velocity.y *= -1;
		}
		// Top collisions
		if (ball.getY() >= Gdx.graphics.getHeight() - ball.getHeight()) {
			velocity.y *= -1;
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
