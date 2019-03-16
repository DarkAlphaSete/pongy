package rodrigo.pongy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import rodrigo.pongy.input.MainInputProcessor;
import rodrigo.pongy.object.Racket;
import rodrigo.pongy.settings.PreferencesManager;

public class Pongy extends ApplicationAdapter {

	private SpriteBatch batch;
	private MainInputProcessor mainInputProcessor;
	private PreferencesManager prefManager;

	private Racket leftRacket;
	private Racket rightRacket;

	private float racketScale;


	// Current hardcoded keys (Player: Up / Down)
	// P1: W / S
	// P2: UpArrow / DownArrow

	// Initialisation
	@Override
	public void create() {
		batch = new SpriteBatch();
		mainInputProcessor = new MainInputProcessor();
		prefManager = new PreferencesManager("Pongy");

		Gdx.input.setInputProcessor(mainInputProcessor);

		racketScale = 1f;


		leftRacket = new Racket(
				new Texture("objects/racket.png"),
				racketScale,
				Racket.POSITIONS.LEFT,
				prefManager);
		rightRacket = new Racket(
				new Texture("objects/racket.png"),
				racketScale,
				Racket.POSITIONS.LEFT,
				prefManager);

	}

	@Override
	public void render() {
		clearScreen();

		mainInputProcessor.updateEvents();

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
	}

	// Clears the screen
	// Moved from the render() function to look a bit cleaner
	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
