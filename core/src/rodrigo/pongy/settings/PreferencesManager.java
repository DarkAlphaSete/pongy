package rodrigo.pongy.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import rodrigo.pongy.object.Racket;


// Settings naming pattern
// "setting"-"action"

// Maybe hardcoding a bit is inevitable sometimes?

public class PreferencesManager {

	private Preferences preferences;

	public PreferencesManager(String preferencesName) {
		preferences = Gdx.app.getPreferences(preferencesName);
	}

	public void setRacketControl(Racket.POSITIONS racketPosition, Racket.ACTIONS action, int keycode) {
		switch (racketPosition) {

			case LEFT:
				switch (action) {
					case MOVE_UP:
						preferences.remove("LEFT-MOVE_UP");
						preferences.putInteger("LEFT-MOVE_UP", keycode);
						break;
					case MOVE_DOWN:
						preferences.remove("LEFT-MOVE_DOWN");
						preferences.putInteger("LEFT-MOVE_DOWN", keycode);
						break;
					default:
						Gdx.app.error(this.hashCode() + "", "Invalid racket action.");
						Gdx.app.exit();
						break;
				}
				break;

			case RIGHT:
				switch (action) {
					case MOVE_UP:
						preferences.remove("RIGHT-MOVE_UP");
						preferences.putInteger("RIGHT-MOVE_UP", keycode);
						break;
					case MOVE_DOWN:
						preferences.remove("RIGHT-MOVE_DOWN");
						preferences.putInteger("RIGHT-MOVE_DOWN", keycode);
						break;
					default:
						Gdx.app.error(this.hashCode() + "", "Invalid racket action.");
						Gdx.app.exit();
						break;
				}
				break;
			default:
				Gdx.app.error(this.hashCode() + "", "Invalid racket position.");
				Gdx.app.exit();
				break;
		}

		preferences.flush();
	}

	public int getRacketControl(Racket.POSITIONS racketPosition, Racket.ACTIONS action) {
		switch (racketPosition) {

			case LEFT:
				switch (action) {
					case MOVE_UP:
						return preferences.getInteger("LEFT-MOVE_UP", -1);
					case MOVE_DOWN:
						return preferences.getInteger("LEFT-MOVE_DOWN", -1);
					default:
						Gdx.app.error(this.hashCode() + "", "Invalid racket action.");
						Gdx.app.exit();
						break;
				}
				break;

			case RIGHT:
				switch (action) {
					case MOVE_UP:
						return preferences.getInteger("RIGHT-MOVE_UP", -1);
					case MOVE_DOWN:
						return preferences.getInteger("RIGHT-MOVE_DOWN", -1);
					default:
						Gdx.app.error(this.hashCode() + "", "Invalid racket action.");
						Gdx.app.exit();
						break;
				}
				break;
			default:
				Gdx.app.error(this.hashCode() + "", "Invalid racket position.");
				Gdx.app.exit();
				break;
		}

		// I don't think this statement is even reachable...
		return -1;
	}


	// The the controls to the defaults below, if either resetToDefaults is true or they weren't set yet
	// (format: MOVE_UP / MOVE_DOWN):
	// W / S for the left racket
	// UpArrow / DownArrow for the right racket
	public void setUpDefaultControls(boolean resetToDefaults) {

		// Left racket
		if (getRacketControl(Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_UP) == -1 || resetToDefaults) {

			setRacketControl(
					Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_UP,
					Input.Keys.W);
		}
		if (getRacketControl(Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_DOWN) == -1 || resetToDefaults) {

			setRacketControl(
					Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_DOWN,
					Input.Keys.S);
		}

		// Right racket
		if (getRacketControl(Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_UP) == -1 || resetToDefaults) {

			setRacketControl(
					Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_UP,
					Input.Keys.UP);
		}
		if (getRacketControl(Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_DOWN) == -1 || resetToDefaults) {

			setRacketControl(
					Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_DOWN,
					Input.Keys.DOWN);
		}

		preferences.flush();
	}

	public void saveSinglePlayerBestTime(float newTime) {
		preferences.remove("SINGLE_PLAYER_BEST_TIME");
		preferences.putFloat("SINGLE_PLAYER_BEST_TIME", newTime);
		preferences.flush();
	}

	public float getSinglePlayerBestTime() {
		return preferences.getFloat("SINGLE_PLAYER_BEST_TIME", 0);
	}
}
