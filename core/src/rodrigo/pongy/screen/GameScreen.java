package rodrigo.pongy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import rodrigo.pongy.input.RacketInputProcessor;
import rodrigo.pongy.listener.ResetListener;
import rodrigo.pongy.manager.ScoreManager;
import rodrigo.pongy.object.Ball;
import rodrigo.pongy.object.Racket;
import rodrigo.pongy.settings.PreferencesManager;


public class GameScreen implements Screen {

	private SpriteBatch batch;

	private RacketInputProcessor racketInputProcessor;

	private PreferencesManager preferencesManager;

	private ScoreManager scoreManager;

	private Racket leftRacket;
	private Racket rightRacket;

	private float racketsScale;
	private float racketsYMargin;

	private float ballScale;

	private Ball ball;

	private Array<ResetListener> resetListeners;

	private OrthographicCamera camera;


	public GameScreen() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		racketsScale = Gdx.graphics.getWidth() / 200f;
		racketsYMargin = Gdx.graphics.getHeight() / 30f;
		ballScale = Gdx.graphics.getHeight() / 30f;

		batch = new SpriteBatch();
		preferencesManager = new PreferencesManager("Pongy");

		preferencesManager.setUpDefaultControls(false);

		initialiseGameObjects();

		resetListeners = new Array<ResetListener>(3);

		resetListeners.add(leftRacket);
		resetListeners.add(rightRacket);
		resetListeners.add(ball);

		scoreManager = new ScoreManager(new BitmapFont(), ball, resetListeners);

		ball.scoreManager = scoreManager;

		racketInputProcessor = new RacketInputProcessor(leftRacket, rightRacket, preferencesManager);

		for(ResetListener listener: resetListeners) {
			listener.resetGame();
		}


	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		clearScreen();

		racketInputProcessor.checkKeyInput();
		racketInputProcessor.checkTouchInput();

		ball.update();

		batch.begin();

		leftRacket.getSprite().draw(batch);
		rightRacket.getSprite().draw(batch);

		ball.getSprite().draw(batch);

		scoreManager.drawText(batch);

		batch.end();

		// dirty thingy for debug purposes
		if (Gdx.input.isKeyPressed(Input.Keys.R)) {
			ball.reset();
		}

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

		ball.dispose();

		scoreManager.dispose();

	}


	// Set up the rackets
	private void initialiseGameObjects() {
		leftRacket = new Racket(
				new Texture("objects/racket.png"),
				Racket.POSITIONS.LEFT,
				racketsScale,
				racketsYMargin,
				camera);
		rightRacket = new Racket(
				new Texture("objects/racket.png"),
				Racket.POSITIONS.RIGHT,
				racketsScale,
				racketsYMargin,
				camera);

		ball = new Ball(
				new Texture("objects/ball.png"),
				(float) Gdx.graphics.getWidth() * Gdx.graphics.getHeight() / 2000f,
				ballScale,
				new Vector2(
						Gdx.graphics.getWidth() / 2f,
						Gdx.graphics.getHeight() / 2f),
				leftRacket, rightRacket);
	}


	// Clears the screen
	// Moved from the render() function to look a bit cleaner
	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
