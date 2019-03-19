package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import rodrigo.pongy.listener.ResetListener;


public class Racket implements ResetListener {


	private Sprite sprite;
	private float movementSpeed;
	private float yScreenMargin;
	private POSITIONS currentPosition;
	private OrthographicCamera camera;
	private boolean singleRacket;

	public Racket(Texture texture, POSITIONS position, float scaleFactor, float yScreenMargin, OrthographicCamera camera, boolean singleRacket) {
		this.camera = camera;

		sprite = new Sprite(texture);
		sprite.setSize(sprite.getWidth() * scaleFactor, sprite.getHeight() * scaleFactor);

		currentPosition = position;

		this.singleRacket = singleRacket;

		// If this is a survival sprite, make it the size of the screen (like a wall)
		if (this.singleRacket) {
			if (position == POSITIONS.RIGHT) {
				sprite.setSize(sprite.getWidth(), Gdx.graphics.getHeight());
				sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth() / 2, 0);
			} else {
				Gdx.app.error("Racket " + hashCode(), "Survival mode is only available for the right sprite.");
				Gdx.app.exit();
			}

		}

		movementSpeed = Gdx.graphics.getWidth() * Gdx.graphics.getHeight() / 550f;
		this.yScreenMargin = yScreenMargin;


	}

	// Movement key event listeners, should execute movement code when fired
	public void moveUpPressed() {
		//Gdx.app.log(this.hashCode() + "", "UP key pressed.");
		// Check if the sprite is within screen bounds
		if (!singleRacket && sprite.getY() < Gdx.graphics.getHeight() - sprite.getHeight() - yScreenMargin) {
			sprite.translate(0, movementSpeed * Gdx.graphics.getDeltaTime());
		}
	}

	public void moveDownPressed() {
		//Gdx.app.log(this.hashCode() + "", "DOWN key pressed.");

		// Check if the sprite is within screen bounds
		if (!singleRacket && sprite.getY() - yScreenMargin > 0) {
			sprite.translate(0, -movementSpeed * Gdx.graphics.getDeltaTime());
		}
	}


	// Note: the survival sprite can't be controlled, since it will act as a wall

	public void draggedMove(float screenNewY) {
		// screenNewY is the new Y position for the sprite, in screen coordinates

		// Extra checks are made to make sure the player drags the sprite around instead of "teleporting" it
		// to wherever he wants / finds useful (like where the ball is)

		Vector3 newPosition = camera.unproject(new Vector3(
				0,
				screenNewY,
				0
		));

		if (!singleRacket) {
			sprite.setPosition(sprite.getX(), newPosition.y + sprite.getHeight() * 1.25f);
		}

	}

	// Reset the rackets
	@Override
	public void resetGame() {
		resetRackets();
	}

	private void resetRackets() {
		switch (currentPosition) {

			case LEFT:
				sprite.setPosition(sprite.getWidth() / 4, Gdx.graphics.getHeight() / 2f - sprite.getHeight() / 2);
				break;

			case RIGHT:
				if (!singleRacket) {
					sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth() - sprite.getWidth() / 4, Gdx.graphics.getHeight() / 2f - sprite.getHeight() / 2);
				}
				break;

			default:
				Gdx.app.error("Racket " + hashCode(), "Invalid sprite position");
				Gdx.app.exit();
				break;
		}
	}

	// Returns this sprite's sprite object
	public Sprite getSprite() {
		return sprite;
	}

	// Shortcut to dispose
	// getSprite().getTexture().dispose() could always be used, but this way is cleaner
	public void dispose() {
		sprite.getTexture().dispose();
	}


	public enum ACTIONS {
		MOVE_UP, MOVE_DOWN
	}

	public enum POSITIONS {
		LEFT, RIGHT,
	}
}
