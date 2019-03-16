package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
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


	public Racket(Texture texture, POSITIONS position, float scaleFactor, float yScreenMargin) {
		racket = new Sprite(texture);
		racket.setSize(racket.getWidth() * scaleFactor, racket.getHeight() * scaleFactor);

		//movementSpeed = 150f;
		movementSpeed = Gdx.graphics.getWidth() * Gdx.graphics.getHeight() / 550f;
		this.yScreenMargin = yScreenMargin;

		switch (position) {

			case LEFT:
				racket.setPosition(racket.getWidth() / 4, Gdx.graphics.getHeight() / 2f - racket.getHeight() / 2);
				break;

			case RIGHT:
				racket.setPosition(Gdx.graphics.getWidth() - racket.getWidth() - racket.getWidth() / 4, Gdx.graphics.getHeight() / 2f - racket.getHeight() / 2);
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
		if (racket.getY() < Gdx.graphics.getHeight() - racket.getHeight() - yScreenMargin) {
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
