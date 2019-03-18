package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FloorDecor {

	private Sprite line;


	// If it's not a floor, then it becomes a ceiling by the way!
	// This just renders a line on the top and bottom to give the impression that there's a limit
	// to the play area.
	// Yes, 1 pixel is enough when it's white against black!
	public FloorDecor(Texture texture, boolean isFloor) {
		line = new Sprite(texture);

		line.setPosition(0, 0);

		line.setScale(Gdx.graphics.getWidth() * 2, 1);

		if (!isFloor) {
			// If it's not a floor, throw it to the ceiling!
			line.setPosition(0, Gdx.graphics.getHeight() - line.getHeight());
		}

	}


	public Sprite getSprite() {
		return line;
	}


	// Blah blah blah .getSprite blah blah other scripts blah blah
	public void dispose() {
		line.getTexture().dispose();
	}
}
