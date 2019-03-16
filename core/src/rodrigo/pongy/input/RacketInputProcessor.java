package rodrigo.pongy.input;

import com.badlogic.gdx.Gdx;
import rodrigo.pongy.object.Racket;
import rodrigo.pongy.settings.PreferencesManager;

public class RacketInputProcessor {

	private PreferencesManager prefManager;

	private Racket leftRacket;
	private Racket rightRacket;


	public RacketInputProcessor(Racket leftRacket, Racket rightRacket, PreferencesManager prefManager) {
		this.prefManager = prefManager;

		this.leftRacket = leftRacket;
		this.rightRacket = rightRacket;

	}

	public void checkInput() {

		// Left racket
		if (Gdx.input.isKeyPressed(
				prefManager.getRacketControl(
						Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_DOWN))) {

			leftRacket.moveDownPressed();
		}
		if (Gdx.input.isKeyPressed(
				prefManager.getRacketControl(
						Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_UP))) {

			leftRacket.moveUpPressed();
		}

		// Right racket
		if (Gdx.input.isKeyPressed(
				prefManager.getRacketControl(
				Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_DOWN))) {

			rightRacket.moveDownPressed();
		}
		if (Gdx.input.isKeyPressed(
				prefManager.getRacketControl(
				Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_UP))) {

			rightRacket.moveUpPressed();
		}

	}

}
