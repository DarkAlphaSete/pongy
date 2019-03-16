package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import rodrigo.pongy.event.RacketMovementListener;

public class Racket implements RacketMovementListener {

	public enum ACTIONS {
		MOVE_UP, MOVE_DOWN
	}

	public enum POSITIONS {
		LEFT, RIGHT,
		TOP, DOWN
	}

	private Sprite racket;

	// Initialisation
	public Racket(Texture texture, float scaleMag, POSITIONS position) {
		racket = new Sprite(texture);
		racket.setScale(scaleMag);

		switch (position) {

			case LEFT:
				racket.setPosition(racket.getWidth(), Gdx.graphics.getHeight() / 2 - racket.getHeight() / 2);

				break;
			case RIGHT:
				racket.setPosition(Gdx.graphics.getWidth() - racket.getWidth() * 2, Gdx.graphics.getHeight() / 2 - racket.getHeight() / 2);
				break;

			// Ignore the TOP and DOWN positions for now...
			case TOP:
			case DOWN:
				break;

			default:
				Gdx.app.log("ERROR", "Unavailable position for the racket.");
				Gdx.app.exit();
				break;
		}

	}

	// Implementations

	// When these events are fired code to move should execute
	@Override
	public void moveUpPressed(RacketMovementListener racket) {
		if (racket.equals(this)) {
			//Gdx.app.log("INFO", "Up key pressed");
		}
		this.racket.translate(0, 100f * Gdx.graphics.getDeltaTime());
	}

	@Override
	public void moveDownPressed(RacketMovementListener racket) {
		if (racket.equals(this)) {
			//Gdx.app.log("INFO", "Down key pressed");
		}
		this.racket.translate(0, -100f * Gdx.graphics.getDeltaTime());
	}

	// Returns thi racket's sprite object
	public Sprite getSprite() {
		return racket;
	}

	// Quick way to dispose the texture
	public void dispose() {
		racket.getTexture().dispose();
	}
}
