package edu.warbot.gui.viewer.gdx.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.gui.viewer.gdx.WarViewerGdx;
import edu.warbot.gui.viewer.gdx.WarViewerObject;

public class WarViewerEntityTurret extends WarViewerEntityControllable
		implements WarViewerObject {
	
	protected Texture texture;
	protected TextureRegion[][] textureRegion;
	protected TextureRegion currentOrientation;
	protected Animation[] animation;
	
	public WarViewerEntityTurret(int id, int patchX, int patchY, int teamColor,
			double heading, int health, int maxHealth) {
		super(id, patchX, patchY, teamColor, heading, health, maxHealth);
		
		this.width = 64;
		this.height = 64;
		
		texture = WarViewerGdx.getTexture("turret_"
				+ teamsColors[teamColor]);
		
		textureRegion = new TextureRegion[nbPositions][1];
		TextureRegion[][] tmp = TextureRegion.split(texture,
				texture.getWidth(), texture.getHeight() / nbPositions);
				
		currentOrientation = tmp[0][0];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 1; j++) {
				textureRegion[i][j] = tmp[i][j];
			}
		}
		
		initInfos();
	}
	
	@Override
	public void render(float delta, OrthographicCamera camera, SpriteBatch batch) {
		ControllableWarAgent ag = getCurrentWarAgent();
		if (ag != null) {
			update(ag.getX(), ag.getY(), ag.getHeading(), ag.getHealth(), ag.getNbElementsInBag());
		}
		
		currentOrientation = textureRegion[getIsometricOrientationByHeading()][0];
		
		batch.begin();
			batch.draw(currentOrientation, getXByPatches(), getYByPatches(), width, height);
		batch.end();
	}
	
	

}
