package rodrigo.pongy.input;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import rodrigo.pongy.screen.MainMenuScreen;

public class GameScreenInputProcessor extends InputAdapter {

	private Game game;

	public GameScreenInputProcessor(Game game) {
		// Catch the back key
		Gdx.input.setCatchBackKey(true);

		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {

		// If the user presses the back key, go back to the main menu
		// The, stop catching the key, so pressing it back again quits the game
		if(keycode == Input.Keys.BACK) {
			Gdx.input.setCatchBackKey(false);
			game.setScreen(new MainMenuScreen(game));
		}


		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return super.keyUp(keycode);
	}

}
