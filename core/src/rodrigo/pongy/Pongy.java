package rodrigo.pongy;

import com.badlogic.gdx.Game;
import rodrigo.pongy.screen.GameScreen;
import rodrigo.pongy.screen.MainMenu;


public class Pongy extends Game {


	@Override
	public void create() {
		this.setScreen(new MainMenu(this));
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
