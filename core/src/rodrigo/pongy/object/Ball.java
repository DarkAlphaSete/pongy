package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import rodrigo.pongy.listener.ResetListener;
import rodrigo.pongy.manager.ScoreManager;

public class Ball implements ResetListener {

	public ScoreManager scoreManager;
	private Sprite sprite;
	private Vector2 velocity;
	private float initialSpeed;
	private Vector2 playAreaCenter;
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

	private int collisionChecksPerFrame;


	// Note: the direction the sprite goes to after spawning will be random
	// leftRacketRightEdge: left racket's right edge, used to calculate the collisions.
	// Also used on the right racket's collision calculations, which means if they aren't symmetrical, everything screws
	// up.
	public Ball(Texture texture, float initialSpeed, float scaleFactor, Vector2 playAreaCenter, Racket leftRacket, Racket rightRacket, boolean singlePlayerMode) {
		this.playAreaCenter = playAreaCenter;
		this.initialSpeed = initialSpeed;

		this.singlePlayerMode = singlePlayerMode;

		sprite = new Sprite(texture);
		sprite.setSize(sprite.getWidth() * scaleFactor, sprite.getHeight() * scaleFactor);


		bounceSFX = Gdx.audio.newSound(Gdx.files.internal("sound/bounce.wav"));

		velocity = new Vector2(1, 1).scl(initialSpeed);


		reset();

		this.leftRacket = leftRacket;
		this.rightRacket = rightRacket;

		bounceCount = 0;
		hasBouncedOnSide = false;

		collisionChecksPerFrame = 500;

	}

	@Override
	public void resetGame() {
		reset();
	}

	public void reset() {
		// Only -1 and 1 are needed, so a random boolean should do the trick
		int xVel = MathUtils.randomSign() * MathUtils.random(1, 2);
		int yVel = MathUtils.randomSign();

		// If it's a single player game, make the sprite always start going left.
		// This way the best time won't be affected by RNG.
		if (singlePlayerMode) {
			xVel = -Math.abs(xVel);
		}

		sprite.setPosition(playAreaCenter.x - sprite.getWidth() / 2, playAreaCenter.y + sprite.getHeight() / 2);

		velocity = new Vector2(xVel, yVel).scl(initialSpeed);

		//bounceCount /= 2;
	}

	private void checkCollisions() {


		// Bottom collisions
		if (sprite.getY() <= 0 && !hasBouncedOnTopBottom) {
			velocity.y *= -1;
			hasBouncedOnTopBottom = true;

			bounceSFX.play();
		}
		// Top collisions
		else if (sprite.getY() + sprite.getHeight() >= Gdx.graphics.getHeight() && !hasBouncedOnTopBottom) {
			velocity.y *= -1;
			hasBouncedOnTopBottom = true;

			bounceSFX.play();
		}

		// Check if sprite the as passed the middle of the screen, X
		if (sprite.getX() > Gdx.graphics.getWidth() / 2 - sprite.getWidth() && sprite.getX() < Gdx.graphics.getWidth() / 2 + sprite.getWidth()) {
			hasBouncedOnSide = false;
		}
		// Check if the sprite has passed the middle of the screen, Y
		if (sprite.getY() > Gdx.graphics.getHeight() / 2 - sprite.getHeight() && sprite.getY() < Gdx.graphics.getHeight() / 2 + sprite.getHeight()) {
			hasBouncedOnTopBottom = false;
		}

		// Left collisions: goal
		if (sprite.getX() <= 0 + leftRacket.getSprite().getX() && !hasBouncedOnSide) {
			scoreManager.score(Racket.POSITIONS.LEFT);
		}
		// Right collisions: goal
		else if (sprite.getX() >= rightRacket.getSprite().getX() + rightRacket.getSprite().getWidth() && !hasBouncedOnSide) {
			scoreManager.score(Racket.POSITIONS.RIGHT);
		}

		// Left collisions: racket
		if (sprite.getX() < leftRacket.getSprite().getX() + leftRacket.getSprite().getWidth() && !hasBouncedOnSide) {
			// Check if it's within the racket's coordinates
			if (sprite.getY() > leftRacket.getSprite().getY() && sprite.getY() + sprite.getHeight() < leftRacket.getSprite().getY() + leftRacket.getSprite().getHeight()) {
				bounceCount++;

				velocity.x = (velocity.x + bounceCount / 2) * -1.1f;

				hasBouncedOnSide = true;

				bounceSFX.play();
			}
		}
		// Right collisions: racket
		else if (sprite.getX() + sprite.getWidth() > rightRacket.getSprite().getX() && !hasBouncedOnSide) {
			// Check if it's within the racket's coordinates
			if (sprite.getY() > rightRacket.getSprite().getY() && sprite.getY() + sprite.getHeight() < rightRacket.getSprite().getY() + rightRacket.getSprite().getHeight()) {
				bounceCount++;

				velocity.x = (velocity.x + bounceCount / 2) * -1.1f;

				hasBouncedOnSide = true;

				bounceSFX.play();
			}
		}


	}

	public void update() {

		checkCollisions();

		// Workaround to too-fast balls: translate n% n times and check between each translation
		// No human player is going to last until this system breaks... hopefully...
		float factor = 1f / collisionChecksPerFrame;

		Vector2 newVel = new Vector2(
				velocity.x * Gdx.graphics.getDeltaTime(),
				velocity.y * Gdx.graphics.getDeltaTime());

		for (int i = 0; i < collisionChecksPerFrame; i++) {

			sprite.translate(
					newVel.x * factor,
					newVel.y * factor);

			checkCollisions();

		}

		// Now, add the delta time
/*
		sprite.translate(
				Gdx.graphics.getDeltaTime() * factor,
				Gdx.graphics.getDeltaTime() * factor);
		checkCollisions();*/

	}


	// Useful functions

	public Sprite getSprite() {
		return sprite;
	}


	// Cleaner than getSprite().getTexture().dispose()
	public void dispose() {
		sprite.getTexture().dispose();
	}
}
