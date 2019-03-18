package rodrigo.pongy.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {

	private Game game;

	private GameScreen gameScreen;

	private BitmapFont font;

	private OrthographicCamera camera;

	private SpriteBatch batch;

	private GlyphLayout gameTitle;
	private GlyphLayout singlePlayerModeText;
	private GlyphLayout twoPlayerModeText;


	public MainMenuScreen(Game game) {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		font = new BitmapFont();

		gameTitle = new GlyphLayout(font, "Pongy");
		singlePlayerModeText = new GlyphLayout(font, "1 x 0");
		twoPlayerModeText = new GlyphLayout(font, "1 x 1");

		font.getData().setScale(1.1f);

		this.game = game;

	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		clearScreen();

		batch.begin();
		font.draw(batch, gameTitle, camera.viewportWidth / 2 - gameTitle.width / 2, camera.viewportHeight * 0.8f);
		//Left: survival mode
		font.draw(batch, twoPlayerModeText, camera.viewportWidth * 0.75f, camera.viewportHeight / 2);
		// Right: player vs player
		font.draw(batch, singlePlayerModeText, camera.viewportWidth / 4, camera.viewportHeight / 2);
		batch.end();

		// Check for touches to select the mode
		if (Gdx.input.isTouched(0)) {
			// Check which side the user pressed (each side = a mode, as written before)
			game.setScreen(new GameScreen(Gdx.input.getX() < camera.viewportWidth / 2, game));

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
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	// Clears the screen
	// Moved from the default render() function to look a bit cleaner
	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
