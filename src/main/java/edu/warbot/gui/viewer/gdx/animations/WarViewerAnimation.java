package edu.warbot.gui.viewer.gdx.animations;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.warbot.gui.viewer.gdx.WarViewerObject;

public abstract class WarViewerAnimation implements WarViewerObject {
	protected int x, y;
	protected int width, height;
	protected float timeAnimation = 0.1f;
	protected float stateTime = 0.0f;
	protected Texture texture;
	protected boolean loop;
	
	public WarViewerAnimation(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void render(float delta, OrthographicCamera camera, SpriteBatch batch);
}
