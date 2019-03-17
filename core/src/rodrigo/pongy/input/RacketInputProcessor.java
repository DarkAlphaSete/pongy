package rodrigo.pongy.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
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


	public void checkTouchInput() {

		for(int i = 0; i < 20; i++) {
			if(Gdx.input.isTouched(i)) {
				touchMove(Gdx.input.getY(i),Gdx.input.getX(i));
			}
		}
	}

	private void touchMove(float screenYCoord, float screenXCoord) {
		// = Left side of the screen
		if (screenXCoord < Gdx.graphics.getWidth() / 2) {
			leftRacket.draggedMove(screenYCoord);
		}
		// = Right side of the screen
		else {
			rightRacket.draggedMove(screenYCoord);
		}
	}

}
