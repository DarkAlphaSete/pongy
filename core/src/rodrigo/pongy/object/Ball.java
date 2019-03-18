package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

	private boolean singlePlayerMode;

	// No credit is needed since the sound is on the public domain (CC 0 license)
	// but I'll throw the link to the sound here: https://freesound.org/people/michorvath/sounds/269718/
	// I have no idea how to credit the sound
	private Sound bounceSFX;

	// Workaround to the glitch where the ball goes through the hitboxes before it being checked
	private OrthographicCamera camera;


	// Note: the direction the ball goes to after spawning will be random
	// leftRacketRightEdge: left racket's right edge, used to calculate the collisions.
	// Also used on the right racket's collision calculations, which means if they aren't symmetrical, everything screws
	// up.
	public Ball(Texture texture, float initialSpeed, float scaleFactor, Vector2 playAreaCenter, Racket leftRacket, Racket rightRacket, boolean singlePlayerMode, OrthographicCamera orthographicCamera) {
		this.playAreaCenter = playAreaCenter;
		this.initialSpeed = initialSpeed;

		this.singlePlayerMode = singlePlayerMode;

		camera = orthographicCamera;

		ball = new Sprite(texture);
		ball.setSize(ball.getWidth() * scaleFactor, ball.getHeight() * scaleFactor);

		bounceSFX = Gdx.audio.newSound(Gdx.files.internal("sound/bounce.wav"));

		reset(true);

		this.leftRacket = leftRacket;
		this.rightRacket = rightRacket;

		bounceCount = 0;
		hasBouncedOnSide = false;

	}

	@Override
	public void resetGame() {
		reset(true);
	}

	public void reset(boolean resetVelocity) {
		// Only -1 and 1 are needed, so a random boolean should do the trick
		int xVel = MathUtils.randomSign();
		int yVel = MathUtils.randomSign();

		// If it's a single player game, make the ball always start going left.
		// This way the best time won't be affected by RNG.
		if(singlePlayerMode) {
			xVel = -1;
		}

		ball.setPosition(playAreaCenter.x - ball.getWidth() / 2, playAreaCenter.y + ball.getHeight() / 2);

		if(resetVelocity) {
			velocity = new Vector2(xVel, yVel).scl(initialSpeed);
		} else {
			// If the velocity isn't going to get reset, it means it was called by the camera workaround, so make it
			// go right to make sure the player has time to react...
			velocity = new Vector2(Math.abs(velocity.x), velocity.y);
		}

		//bounceCount /= 2;
	}

	private void checkCollisions() {

		if(camera.frustum.pointInFrustum(ball.getX(), ball.getY(), 0)) {
			reset(false);
		}


		// Bottom collisions
		if (ball.getY() <= 0 && !hasBouncedOnTopBottom) {
			velocity.y *= -1;
			hasBouncedOnTopBottom = true;

			bounceSFX.play(0.25f);
		}
		// Top collisions
		else if (ball.getY() + ball.getHeight() >= Gdx.graphics.getHeight() && !hasBouncedOnTopBottom) {
			velocity.y *= -1;
			hasBouncedOnTopBottom = true;

			bounceSFX.play(0.25f);
		}

		// Check if ball the as passed the middle of the screen, X
		if (ball.getX() > Gdx.graphics.getWidth() / 2 - ball.getWidth() && ball.getX() < Gdx.graphics.getWidth() / 2 + ball.getWidth()) {
			hasBouncedOnSide = false;
		}
		// Check if the ball has passed the middle of the screen, Y
		if (ball.getY() > Gdx.graphics.getHeight() / 2 - ball.getHeight() && ball.getY() < Gdx.graphics.getHeight() / 2 + ball.getHeight()) {
			hasBouncedOnTopBottom = false;
		}

		// Left collisions: goal
		if (ball.getX() <= 0 + leftRacket.getSprite().getX() && !hasBouncedOnSide) {
			scoreManager.score(Racket.POSITIONS.LEFT);
		}
		// Right collisions: goal
		else if (ball.getX() >= rightRacket.getSprite().getX() + rightRacket.getSprite().getWidth() && !hasBouncedOnSide) {
			scoreManager.score(Racket.POSITIONS.RIGHT);
		}

		// Left collisions: racket
		if (ball.getX() < leftRacket.getSprite().getX() + leftRacket.getSprite().getWidth() && !hasBouncedOnSide) {
			// Check if it's within the racket's coordinates
			if (ball.getY() > leftRacket.getSprite().getY() && ball.getY() + ball.getHeight() < leftRacket.getSprite().getY() + leftRacket.getSprite().getHeight()) {
				bounceCount++;

				velocity.x = (velocity.x + bounceCount / 2) * -1.1f;

				hasBouncedOnSide = true;

				bounceSFX.play();
			}
		}
		// Right collisions: racket
		else if (ball.getX() + ball.getWidth() > rightRacket.getSprite().getX() && !hasBouncedOnSide) {
			// Check if it's within the racket's coordinates
			if (ball.getY() > rightRacket.getSprite().getY() && ball.getY() + ball.getHeight() < rightRacket.getSprite().getY() + rightRacket.getSprite().getHeight()) {
				bounceCount++;

				velocity.x = (velocity.x + bounceCount / 2) * -1.1f;

				hasBouncedOnSide = true;

				bounceSFX.play();
			}
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
