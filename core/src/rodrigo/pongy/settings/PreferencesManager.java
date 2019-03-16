package rodrigo.pongy.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import rodrigo.pongy.object.Racket;

// Settings naming pattern
// setting-action

// I think hardcoding stuff would be inevitable for this subject

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
}
