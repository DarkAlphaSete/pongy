package rodrigo.pongy.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import rodrigo.pongy.listener.ResetListener;
import rodrigo.pongy.object.Ball;
import rodrigo.pongy.object.Racket;

public class ScoreManager {

	private BitmapFont font;
	private Ball ball;

	private int leftScore;
	private int rightScore;

	private Array<ResetListener> resetListeners;

	public ScoreManager(BitmapFont font, Ball ball, Array<ResetListener> resetListeners) {
		this.font = font;
		this.ball = ball;

		font.getData().setScale((float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight());

		leftScore = 0;
		rightScore = 0;

		this.resetListeners = resetListeners;
	}

	public int score(Racket.POSITIONS position) {

		for (ResetListener listener : resetListeners) {
			listener.resetGame();
		}

		switch (position) {
			case LEFT:
				return rightScore++;
			case RIGHT:
				return leftScore++;
			default:
				Gdx.app.error("ScoreManager", "Invalid racket position");
				Gdx.app.exit();
				return 0;
		}

	}

	public void drawText(SpriteBatch batch) {
		font.draw(batch, "" + leftScore, ball.getSprite().getWidth() * 3, Gdx.graphics.getHeight() - ball.getSprite().getHeight());
		font.draw(batch, "" + rightScore, Gdx.graphics.getWidth() - ball.getSprite().getWidth() * 4, Gdx.graphics.getHeight() - ball.getSprite().getHeight());
	}

	public void dispose() {
		font.dispose();
	}
}
