package edu.warbot.gui.viewer.gdx.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import edu.warbot.gui.viewer.gdx.WarViewerGdx;

public class WarViewerAnimationExplosion extends WarViewerAnimation {
	
	private Array<TextureRegion> explosion;
	private TextureRegion currentFrame;
	private TextureRegion lastFrame;
	private Animation animation;
	
	private boolean finished = false;
	private Sprite mark;
	private boolean showMark;
	
	public WarViewerAnimationExplosion(int x, int y, int width, int height, boolean showMark) {
		super(x, y, width, height);
		this.showMark = showMark;
		timeAnimation = 0.5f;
		
		texture = WarViewerGdx.getTexture("explosion");
		
		explosion = new Array<TextureRegion>(25);
		TextureRegion[][] tmp = TextureRegion.split(texture,
				texture.getWidth() / 5, texture.getHeight() / 5);
		
		currentFrame = tmp[0][0];
		lastFrame = tmp[4][4];
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				explosion.add(tmp[i][j]);
			}
		}
		
		animation = new Animation(timeAnimation / 25, explosion);
		if (this.showMark) {
			mark = new Sprite(WarViewerGdx.getTexture("explosion_mark"));
		}
		
	}

	@Override
	public void render(float delta, OrthographicCamera camera, SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, false);
		batch.begin();
			if (!finished) {
				batch.draw(currentFrame, x, y, width, height);
			} else if (showMark){
				batch.draw(mark, x, y, width, height / 2);
			}
		batch.end();
		if (currentFrame.equals(lastFrame)) {
			finished = true;
		}
	}
	
	public boolean isFinished() {
		return finished;
	}
}
