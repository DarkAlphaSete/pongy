package rodrigo.pongy.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import rodrigo.pongy.listener.ResetListener;
import rodrigo.pongy.object.Ball;
import rodrigo.pongy.object.Racket;
import rodrigo.pongy.settings.PreferencesManager;

import java.text.DecimalFormat;

public class ScoreManager {

	private BitmapFont font;
	private Ball ball;

	private int leftScore;
	private int rightScore;

	private float singlePlayerTimer;
	private float singlePlayerBestTime;

	private PreferencesManager preferencesManager;

	private DecimalFormat decimalFormat;

	private Array<ResetListener> resetListeners;

	private boolean singlePlayerMode;


	public ScoreManager(BitmapFont font, Ball ball, Array<ResetListener> resetListeners, boolean singlePlayerMode, PreferencesManager preferencesManager) {
		this.font = font;
		this.ball = ball;

		this.preferencesManager = preferencesManager;

		this.singlePlayerMode = singlePlayerMode;

		singlePlayerTimer = 0f;
		singlePlayerBestTime = preferencesManager.getSinglePlayerBestTime();

		decimalFormat = new DecimalFormat("#0.00");


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
				if (singlePlayerMode) {

					if (singlePlayerTimer > singlePlayerBestTime) {
						singlePlayerBestTime = singlePlayerTimer;
						preferencesManager.saveSinglePlayerBestTime(singlePlayerBestTime);
					}

					singlePlayerTimer = 0f;

				}

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
		// If the survival mode is enabled, don't show the left player it's score, because there won't be any.
		if (!singlePlayerMode) {
			font.draw(batch, "" + leftScore, ball.getSprite().getWidth() * 3, Gdx.graphics.getHeight() - ball.getSprite().getHeight());
			font.draw(batch, "" + rightScore, Gdx.graphics.getWidth() - ball.getSprite().getWidth() * 4, Gdx.graphics.getHeight() - ball.getSprite().getHeight());

		} else {
			font.draw(batch, "" + decimalFormat.format(singlePlayerTimer), ball.getSprite().getWidth() * 3, Gdx.graphics.getHeight() - ball.getSprite().getHeight());
			font.draw(batch, "" + decimalFormat.format(singlePlayerBestTime), Gdx.graphics.getWidth() - ball.getSprite().getWidth() * 4, Gdx.graphics.getHeight() - ball.getSprite().getHeight());

		}

	}

	public void update() {
		singlePlayerTimer += Gdx.graphics.getDeltaTime();

	}

	public void dispose() {
		font.dispose();
	}
}
