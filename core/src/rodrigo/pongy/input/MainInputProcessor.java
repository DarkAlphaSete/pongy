package rodrigo.pongy.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import rodrigo.pongy.event.RacketMovementListener;


public class MainInputProcessor implements InputProcessor {


	// REFERENCE:
	private Array<RacketMovementListener> racketMovementListeners;


	// Stores the keys that are held down
	private IntArray heldKeys;


	// Initialisation
	public MainInputProcessor() {
		heldKeys = new IntArray();
		racketMovementListeners = new Array<RacketMovementListener>();

	}

	// Adds a RacketMovementListener to the respective subscriber list
	public void subscribeToRacketMovement(RacketMovementListener newSubscriber, int upKeycode, int downKeyCode) {

		racketMovementListeners.add(newSubscriber);
	}

	// Update and execute event action
	public void updateEvents() {

		// Fire events for racket movement subscribers if the keys match


	}

	@Override
	public boolean keyDown(int keycode) {

		// Add keycode to heldKeys if it's not already there
		if (!heldKeys.contains(keycode)) {
			heldKeys.add(keycode);
			return true;
		}

		return false;

	}

	@Override
	public boolean keyUp(int keycode) {
		// Remove keycode from heldKeys since the key is no longer held down
		for (Integer code : heldKeys.items) {
			if (code == keycode) {
				heldKeys.removeValue(code);
			}
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
