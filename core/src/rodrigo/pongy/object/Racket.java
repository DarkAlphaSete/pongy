package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import rodrigo.pongy.event.RacketMovementListener;
import rodrigo.pongy.settings.PreferencesManager;

public class Racket implements RacketMovementListener {

	public enum ACTIONS {
		MOVE_UP, MOVE_DOWN
	}

	public enum POSITIONS {
		LEFT, RIGHT,
	}

	private Sprite racket;
	private float movementSpeed;
	private PreferencesManager prefManager;


	// Initialisation
	public Racket(Texture texture, float scaleMag, POSITIONS position, PreferencesManager prefManager) {
		racket = new Sprite(texture);
		racket.setScale(scaleMag);

		this.prefManager = prefManager;

		movementSpeed = 150f;

		switch (position) {

			case LEFT:
				racket.setPosition(racket.getWidth(), Gdx.graphics.getHeight() / 2 - racket.getHeight() / 2);
				break;

			case RIGHT:
				racket.setPosition(Gdx.graphics.getWidth() - racket.getWidth() * 2, Gdx.graphics.getHeight() / 2 - racket.getHeight() / 2);
				break;

			default:
				Gdx.app.error(this.hashCode() + "", "Invalid racket position.");
				Gdx.app.exit();
				break;
		}

	}

	// Implementations

	// Movement key event listeners, should execute movement code when fired
	@Override
	public void moveUpPressed(RacketMovementListener racket) {
		if (racket.equals(this)) {
			Gdx.app.log(this.hashCode() + "", "UP key pressed.");
		}
		this.racket.translate(0, movementSpeed * Gdx.graphics.getDeltaTime());
	}

	@Override
	public void moveDownPressed(RacketMovementListener racket) {
		if (racket.equals(this)) {
			Gdx.app.log(this.hashCode() + "", "DOWN key pressed.");
		}
		this.racket.translate(0, -movementSpeed * Gdx.graphics.getDeltaTime());
	}


	// Useful functions

	// Returns this racket's sprite object
	public Sprite getSprite() {
		return racket;
	}

	// Quick way to dispose the texture
	public void dispose() {
		racket.getTexture().dispose();
	}
}
