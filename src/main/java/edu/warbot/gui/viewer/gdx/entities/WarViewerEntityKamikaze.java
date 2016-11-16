package edu.warbot.gui.viewer.gdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.gui.viewer.gdx.WarViewerGdx;

public class WarViewerEntityKamikaze extends WarViewerEntityControllable
		implements WarViewerEntityMovable {
	
	protected int nbFrames;
	protected float timeAnimation = 0.05f;
	protected float stateTime;
	
	protected Texture texture;
	protected TextureRegion[][] textureRegion;
	protected TextureRegion currentFrame;
	protected Animation[] animation;

	public WarViewerEntityKamikaze(int id, int patchX, int patchY,
			int teamColor, double heading, int health, int maxHealth) {
		super(id, patchX, patchY, teamColor, heading, health, maxHealth);
		
		this.width = 20;
		this.height = 20;
		
		texture = WarViewerGdx.getTexture("sheet_"
				+ teamsColors[teamColor]);
		this.heading = heading;
		
		nbFrames = 8;
		
		textureRegion = new TextureRegion[nbFrames][nbPositions];
		TextureRegion[][] tmp = TextureRegion.split(texture,
				texture.getWidth() / nbPositions, texture.getHeight() / nbFrames);
		
		currentFrame = tmp[0][0];
		
		for (int i = 0; i < nbFrames; i++) {
			for (int j = 0; j < nbPositions; j++) {
				textureRegion[i][j] = tmp[i][j];
			}
		}
		animation = new Animation[nbPositions];
		for (int i = 0; i < nbPositions; i++) {
			Array<TextureRegion> anim = new Array<TextureRegion>();
			anim.addAll(textureRegion[i]);
			animation[i] = new Animation(timeAnimation / nbFrames, anim);
			anim.clear();
		}
		
		stateTime = 0f;
		
		initInfos();
	}

	@Override
	public void render(float delta, OrthographicCamera camera, SpriteBatch batch) {
		ControllableWarAgent ag = getCurrentWarAgent();
		if (ag != null) {
			update(ag.getX(), ag.getY(), ag.getHeading(), ag.getHealth(), ag.getNbElementsInBag());
			if (isMoving(ag)) {
				animateMoving();
			}	
		}
		
		batch.begin();
			batch.draw(currentFrame, getXByPatches(), getYByPatches(), width, height);
		batch.end();
	}

	@Override
	public void animateMoving() {
		int idAnimation = getIsometricOrientationByHeading();
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation[idAnimation].getKeyFrame(stateTime, true);
		
	}

}
