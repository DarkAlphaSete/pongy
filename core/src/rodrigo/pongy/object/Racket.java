package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Racket {


	public enum ACTIONS {
		MOVE_UP, MOVE_DOWN
	}

	public enum POSITIONS {
		LEFT, RIGHT,
	}


	private Sprite racket;
	private float movementSpeed;
	private float yScreenMargin;

	private OrthographicCamera camera;


	public Racket(Texture texture, POSITIONS position, OrthographicCamera orthographicCamera, float scaleFactor, float yScreenMargin) {
		racket = new Sprite(texture);
		racket.setSize(racket.getWidth() * scaleFactor, racket.getHeight() * scaleFactor);

		camera = orthographicCamera;

		//movementSpeed = 150f;
		movementSpeed = camera.viewportWidth * camera.viewportHeight / 550f;
		this.yScreenMargin = yScreenMargin;

		switch (position) {

			case LEFT:
				racket.setPosition(racket.getWidth() / 4, camera.viewportHeight / 2f - racket.getHeight() / 2);
				break;

			case RIGHT:
				racket.setPosition(camera.viewportWidth - racket.getWidth() - racket.getWidth() / 4, camera.viewportHeight / 2f - racket.getHeight() / 2);
				break;

			default:
				Gdx.app.error(this.hashCode() + "", "Invalid racket position.");
				Gdx.app.exit();
				break;
		}

	}


	// Movement key event listeners, should execute movement code when fired
	public void moveUpPressed() {
		//Gdx.app.log(this.hashCode() + "", "UP key pressed.");
		// Check if the racket is within screen bounds
		if (racket.getY() < camera.viewportHeight - racket.getHeight() - yScreenMargin) {
			racket.translate(0, movementSpeed * Gdx.graphics.getDeltaTime());
		}
	}

	public void moveDownPressed() {
		//Gdx.app.log(this.hashCode() + "", "DOWN key pressed.");

		// Check if the racket is within screen bounds
		if (racket.getY() - yScreenMargin > 0) {
			racket.translate(0, -movementSpeed * Gdx.graphics.getDeltaTime());
		}
	}

	public void draggedMove(int screenNewY) {
		// screenNewY is the new Y position for the racket, in screen coordinates

		// Extra checks are made to make sure the player drags the racket around instead of "teleporting" it
		// to wherever he wants / finds useful (like where the ball is)

		racket.setPosition(racket.getX(), screenNewY);

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
}
