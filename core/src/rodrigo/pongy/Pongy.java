package rodrigo.pongy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import rodrigo.pongy.input.RacketInputProcessor;
import rodrigo.pongy.object.Racket;
import rodrigo.pongy.settings.PreferencesManager;

public class Pongy extends ApplicationAdapter {

	private SpriteBatch batch;

	private RacketInputProcessor racketInputProcessor;

	private PreferencesManager prefManager;

	private Racket leftRacket;
	private Racket rightRacket;

	private float racketScale;
	private float racketYScreenMargin;


	// Initialisation
	@Override
	public void create() {
		prefManager = new PreferencesManager("Pongy");

		batch = new SpriteBatch();

		racketScale = Gdx.graphics.getWidth() / 200;
		racketYScreenMargin = Gdx.graphics.getHeight() / 30;

		leftRacket = new Racket(
				new Texture("objects/racket.png"),
				Racket.POSITIONS.LEFT,
				racketScale,
				racketYScreenMargin);
		rightRacket = new Racket(
				new Texture("objects/racket.png"),
				Racket.POSITIONS.RIGHT,
				racketScale,
				racketYScreenMargin);


		racketInputProcessor = new RacketInputProcessor(leftRacket, rightRacket, prefManager);

		// If the racket controls aren't set, set them to the defaults
		// Defaults are (MOVE_UP / MOVE_DOWN format):
		// W / S for the left racket
		// UpArrow / DownArrow for the right racket
		if (prefManager.getRacketControl(Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_UP) == -1) {

			prefManager.setRacketControl(
					Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_UP,
					Input.Keys.W);
		}
		if (prefManager.getRacketControl(Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_DOWN) == -1) {

			prefManager.setRacketControl(
					Racket.POSITIONS.LEFT, Racket.ACTIONS.MOVE_DOWN,
					Input.Keys.S);
		}
		if (prefManager.getRacketControl(Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_UP) == -1) {

			prefManager.setRacketControl(
					Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_UP,
					Input.Keys.UP);
		}
		if (prefManager.getRacketControl(Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_DOWN) == -1) {

			prefManager.setRacketControl(
					Racket.POSITIONS.RIGHT, Racket.ACTIONS.MOVE_DOWN,
					Input.Keys.DOWN);
		}

	}

	@Override
	public void render() {
		clearScreen();

		racketInputProcessor.checkInput();

		batch.begin();

		// Render the rackets
		leftRacket.getSprite().draw(batch);
		rightRacket.getSprite().draw(batch);

		batch.end();

		//Gdx.app.log("FPS", "" + Gdx.graphics.getFramesPerSecond());

	}

	@Override
	public void dispose() {
		batch.dispose();
		leftRacket.dispose();
		rightRacket.dispose();
	}

	// Clears the screen
	// Moved from the render() function to look a bit cleaner
	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
