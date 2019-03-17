package rodrigo.pongy;

import com.badlogic.gdx.Game;
import rodrigo.pongy.screen.MainMenuScreen;


public class Pongy extends Game {

	@Override
	public void create() {
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {

		super.render();

	}

	@Override
	public void dispose() {

		super.dispose();

	}


}
