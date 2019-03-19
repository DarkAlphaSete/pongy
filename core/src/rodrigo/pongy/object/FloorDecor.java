package rodrigo.pongy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FloorDecor {

	private Sprite sprite;


	// If it's not a floor, then it becomes a ceiling by the way!
	// This just renders a sprite on the top and bottom to give the impression that there's a limit
	// to the play area.
	// Yes, 1 pixel is enough when it's white against black!
	public FloorDecor(Texture texture, boolean isFloor) {
		sprite = new Sprite(texture);

		sprite.setPosition(0, 0);

		sprite.setScale(Gdx.graphics.getWidth() * 2, 1);

		if (!isFloor) {
			// If it's not a floor, throw it to the ceiling!
			sprite.setPosition(0, Gdx.graphics.getHeight() - sprite.getHeight());
		}

	}


	public Sprite getSprite() {
		return sprite;
	}


	// Blah blah blah .getSprite blah blah other scripts blah blah
	public void dispose() {
		sprite.getTexture().dispose();
	}
}
