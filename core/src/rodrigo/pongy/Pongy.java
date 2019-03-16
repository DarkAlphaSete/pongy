package rodrigo.pongy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import rodrigo.pongy.input.MainInputProcessor;
import rodrigo.pongy.object.Racket;

public class Pongy extends ApplicationAdapter {

	private SpriteBatch batch;
	private MainInputProcessor mainInputProcessor;

	private Array<Racket> rackets;

	// Maybe someday I'll implement 4 players? Not sure... Future proofing!
	private int player_count;

	// Currently hardcoded keys (Player: Up / Down)
	// P1: W / S
	// P2: UpArrow / DownArrow

	// Initialisation
	@Override
	public void create() {
		batch = new SpriteBatch();
		mainInputProcessor = new MainInputProcessor();
		rackets = new Array<Racket>();
		player_count = 2;

		Gdx.input.setInputProcessor(mainInputProcessor);

		for (int i = 0; i < player_count; i++) {
			Racket racket;
			ObjectMap<Racket.ACTIONS, Integer> actions = new ObjectMap<Racket.ACTIONS, Integer>();

			//TODO: Load from a settings file
			if (i == 0) {
				racket = new Racket(new Texture("objects/racket.png"), 1f, Racket.POSITIONS.LEFT);
				actions.put(Racket.ACTIONS.MOVE_UP, Input.Keys.W);
				actions.put(Racket.ACTIONS.MOVE_DOWN, Input.Keys.S);
			} else if (i == 1) {
				racket = new Racket(new Texture("objects/racket.png"), 1f, Racket.POSITIONS.RIGHT);
				actions.put(Racket.ACTIONS.MOVE_UP, Input.Keys.UP);
				actions.put(Racket.ACTIONS.MOVE_DOWN, Input.Keys.DOWN);
			} else {
				Gdx.app.log("ERROR", "Remember? You haven't bothered to implement more than 2 players yet...");
				// Just to shut up IntelliJ with it's "might not have been initialized" warning...
				// You know, you thing? If it's not initialized the program will crash before even caring about it!
				racket = null;
				Gdx.app.exit();
			}

			rackets.add(racket);

			mainInputProcessor.subscribeToRacketMovement(racket, actions);
		}

	}

	@Override
	public void render() {
		clearScreen();

		mainInputProcessor.updateEvents();

		batch.begin();
		// Show the rackets
		for (Racket racket : rackets) {
			racket.getSprite().draw(batch);
		}

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
