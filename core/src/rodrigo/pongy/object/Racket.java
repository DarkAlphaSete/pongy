package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import rodrigo.pongy.listener.ResetListener;


public class Racket implements ResetListener {


	private Sprite racket;
	private float movementSpeed;
	private float yScreenMargin;
	private POSITIONS currentPosition;
	private OrthographicCamera camera;
	private boolean singleRacket;

	public Racket(Texture texture, POSITIONS position, float scaleFactor, float yScreenMargin, OrthographicCamera camera, boolean singleRacket) {
		this.camera = camera;

		racket = new Sprite(texture);
		racket.setSize(racket.getWidth() * scaleFactor, racket.getHeight() * scaleFactor);

		currentPosition = position;

		this.singleRacket = singleRacket;

		// If this is a survival racket, make it the size of the screen (like a wall)
		if (this.singleRacket) {
			if (position == POSITIONS.RIGHT) {
				racket.setSize(racket.getWidth(), Gdx.graphics.getHeight());
				racket.setPosition(Gdx.graphics.getWidth() - racket.getWidth() / 2, 0);
			} else {
				Gdx.app.error("Racket " + hashCode(), "Survival mode is only available for the right racket.");
				Gdx.app.exit();
			}

		}

		movementSpeed = Gdx.graphics.getWidth() * Gdx.graphics.getHeight() / 550f;
		this.yScreenMargin = yScreenMargin;


	}

	// Movement key event listeners, should execute movement code when fired
	public void moveUpPressed() {
		//Gdx.app.log(this.hashCode() + "", "UP key pressed.");
		// Check if the racket is within screen bounds
		if (!singleRacket && racket.getY() < Gdx.graphics.getHeight() - racket.getHeight() - yScreenMargin) {
			racket.translate(0, movementSpeed * Gdx.graphics.getDeltaTime());
		}
	}

	public void moveDownPressed() {
		//Gdx.app.log(this.hashCode() + "", "DOWN key pressed.");

		// Check if the racket is within screen bounds
		if (!singleRacket && racket.getY() - yScreenMargin > 0) {
			racket.translate(0, -movementSpeed * Gdx.graphics.getDeltaTime());
		}
	}


	// Note: the survival racket can't be controlled, since it will act as a wall

	public void draggedMove(float screenNewY) {
		// screenNewY is the new Y position for the racket, in screen coordinates

		// Extra checks are made to make sure the player drags the racket around instead of "teleporting" it
		// to wherever he wants / finds useful (like where the ball is)

		Vector3 newPosition = camera.unproject(new Vector3(
				0,
				screenNewY,
				0
		));

		if (!singleRacket) {
			racket.setPosition(racket.getX(), newPosition.y + racket.getHeight() * 1.25f);
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
				racket.setPosition(racket.getWidth() / 4, Gdx.graphics.getHeight() / 2f - racket.getHeight() / 2);
				break;

			case RIGHT:
				if (!singleRacket) {
					racket.setPosition(Gdx.graphics.getWidth() - racket.getWidth() - racket.getWidth() / 4, Gdx.graphics.getHeight() / 2f - racket.getHeight() / 2);
				}
				break;

			default:
				Gdx.app.error("Racket " + hashCode(), "Invalid racket position");
				Gdx.app.exit();
				break;
		}
	}

	// Returns this racket's sprite object
	public Sprite getSprite() {
		return racket;
	}

	// Shortcut to dispose
	// getSprite().getTexture().dispose() could always be used, but this way is cleaner
	public void dispose() {
		racket.getTexture().dispose();
	}


	public enum ACTIONS {
		MOVE_UP, MOVE_DOWN
	}

	public enum POSITIONS {
		LEFT, RIGHT,
	}
}
