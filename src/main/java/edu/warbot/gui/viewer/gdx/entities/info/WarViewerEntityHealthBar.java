package edu.warbot.gui.viewer.gdx.entities.info;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.warbot.gui.viewer.gdx.WarViewerGdx;
import edu.warbot.gui.viewer.gdx.WarViewerObject;
import edu.warbot.gui.viewer.gdx.entities.WarViewerEntityControllable;

public class WarViewerEntityHealthBar implements WarViewerObject {
	
	private int width;
	private int height;
	private int healingPoints;
	private int maxHealingPoints;
	private WarViewerEntityControllable entity;
	private int x, y;
	private TextureRegion[][] bars;
	private TextureRegion current; 
	private Texture texture;
	
	public WarViewerEntityHealthBar(int healingPoints, int maxHealingPoints,
			WarViewerEntityControllable entity) {
		this.healingPoints = healingPoints;
		this.maxHealingPoints = maxHealingPoints;
		this.entity = entity;
		width = entity.getWidth();
		height = 8;

		texture = WarViewerGdx.getTexture("health_bar");
		bars = new TextureRegion[10][1];
		TextureRegion[][] tmp = TextureRegion.split(texture,
				texture.getWidth(), texture.getHeight() / 10);
		
		current = tmp[0][0];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 1; j++) {
				bars[i][j] = tmp[i][j];
			}
		}
		initCoordinates();
	}

	private void initCoordinates() {
		x = entity.getX();
		y = entity.getY() + entity.getHeight();
	}

	public void render(float delta, OrthographicCamera camera, SpriteBatch batch) {
		checkHealingPoints();
		batch.begin();
			batch.draw(current, x, y, width, height);
		batch.end();
	}

	private void checkHealingPoints() {
		float healthPercentage = 100 * ((float) healingPoints / (float) maxHealingPoints);
		if (healthPercentage >= 0 && healthPercentage <= 10){
			current = bars[9][0];
		} else if (healthPercentage > 10 && healthPercentage <= 20) {
			current = bars[8][0];
		} else if (healthPercentage > 20 && healthPercentage <= 30) {
			current = bars[7][0];
		} else if (healthPercentage > 30 && healthPercentage <= 40) {
			current = bars[6][0];
		} else if (healthPercentage > 40 && healthPercentage <= 50) {
			current = bars[5][0];
		} else if (healthPercentage > 50 && healthPercentage <= 60) {
			current = bars[4][0];
		} else if (healthPercentage > 60 && healthPercentage <= 70) {
			current = bars[3][0];
		} else if (healthPercentage > 70 && healthPercentage <= 80) {
			current = bars[2][0];
		} else if (healthPercentage > 80 && healthPercentage <= 90) {
			current = bars[1][0];
		} else if (healthPercentage > 90 && healingPoints <= 100) {
			current = bars[0][0];
		}
	}

	public void update() {
		x = entity.getX();
		y = entity.getY() + entity.getHeight();
		healingPoints = entity.getHealth();	
	}
}
