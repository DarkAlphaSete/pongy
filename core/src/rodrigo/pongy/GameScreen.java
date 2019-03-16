package rodrigo.pongy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import rodrigo.pongy.input.RacketInputProcessor;
import rodrigo.pongy.object.Racket;
import rodrigo.pongy.settings.PreferencesManager;


public class GameScreen implements Screen {

	private SpriteBatch batch;

	private RacketInputProcessor racketInputProcessor;

	private PreferencesManager preferencesManager;

	private Racket leftRacket;
	private Racket rightRacket;

	private float racketsScale;
	private float racketsYMargin;


	public GameScreen() {
		racketsScale = Gdx.graphics.getWidth() / 200f;
		racketsYMargin = Gdx.graphics.getHeight() / 30f;

		batch = new SpriteBatch();
		preferencesManager = new PreferencesManager("Pongy");

		preferencesManager.setUpDefaultControls(false);
		initialiseRackets();

		racketInputProcessor = new RacketInputProcessor(leftRacket, rightRacket, preferencesManager);


	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		clearScreen();

		racketInputProcessor.checkKeyInput();
		racketInputProcessor.checkTouchInput();

		batch.begin();

		leftRacket.getSprite().draw(batch);
		rightRacket.getSprite().draw(batch);

		batch.end();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

		batch.dispose();
		leftRacket.dispose();
		rightRacket.dispose();

	}


	// Set up the rackets
	private void initialiseRackets() {
		leftRacket = new Racket(
				new Texture("objects/racket.png"),
				Racket.POSITIONS.LEFT,
				racketsScale,
				racketsYMargin);
		rightRacket = new Racket(
				new Texture("objects/racket.png"),
				Racket.POSITIONS.RIGHT,
				racketsScale,
				racketsYMargin);
	}


	// Clears the screen
	// Moved from the render() function to look a bit cleaner
	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
