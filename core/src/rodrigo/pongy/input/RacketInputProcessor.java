package rodrigo.pongy.input;

import com.badlogic.gdx.Gdx;
import rodrigo.pongy.object.Racket;
import rodrigo.pongy.settings.PreferencesManager;


public class RacketInputProcessor {

	private PreferencesManager preferencesManager;

	private Racket leftRacket;
	private Racket rightRacket;


	public RacketInputProcessor(Racket leftRacket, Racket rightRacket, PreferencesManager preferencesManager) {
		this.preferencesManager = preferencesManager;

		this.leftRacket = leftRacket;
		this.rightRacket = rightRacket;

	}

	public void checkKeyInput() {

		// Left racket
		if (Gdx.input.isKeyPressed(
				preferencesManager.getRacketControl(
						Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_DOWN))) {

			leftRacket.moveDownPressed();
		}
		if (Gdx.input.isKeyPressed(
				preferencesManager.getRacketControl(
						Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_UP))) {

			leftRacket.moveUpPressed();
		}

		// Right racket
		if (Gdx.input.isKeyPressed(
				preferencesManager.getRacketControl(
						Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_DOWN))) {

			rightRacket.moveDownPressed();
		}
		if (Gdx.input.isKeyPressed(
				preferencesManager.getRacketControl(
						Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_UP))) {

			rightRacket.moveUpPressed();
		}

	}



}
