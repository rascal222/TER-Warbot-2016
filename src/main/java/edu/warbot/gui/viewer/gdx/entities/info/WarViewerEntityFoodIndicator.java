package edu.warbot.gui.viewer.gdx.entities.info;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.warbot.gui.viewer.gdx.WarViewerGdx;
import edu.warbot.gui.viewer.gdx.WarViewerObject;
import edu.warbot.gui.viewer.gdx.entities.WarViewerEntityControllable;

public class WarViewerEntityFoodIndicator implements WarViewerObject {
	private int width;
	private int height;
	private int x, y;
	private Sprite sprite;
	private boolean hasFood;
	private WarViewerEntityControllable entity;
	
	public WarViewerEntityFoodIndicator(WarViewerEntityControllable entity) {
		this.entity = entity;
		
		hasFood = false;
		
		Texture texture = WarViewerGdx.getTexture("food_indicator");
		width = texture.getWidth();
		height = texture.getHeight();
		sprite = new Sprite(texture);
		
		x = entity.getX() + entity.getWidth() + 2;
		y = entity.getY() + entity.getHeight();
	}

	public void render(float delta, OrthographicCamera camera, SpriteBatch batch) {
		if (hasFood) {
			batch.begin();
				batch.draw(sprite, x, y, width, height);
			batch.end();
		}
	}
	
	public void update(boolean hasFood) {
		x = entity.getX() + entity.getWidth() + 2;
		y = entity.getY() + entity.getHeight();
		this.hasFood = hasFood;
	}
}
