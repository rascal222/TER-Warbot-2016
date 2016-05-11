package edu.warbot.gui.viewer.gdx.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.warbot.agents.WarProjectile;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.gui.viewer.gdx.WarViewerGdx;
import edu.warbot.gui.viewer.gdx.WarViewerObject;
import edu.warbot.gui.viewer.gdx.animations.WarViewerAnimationExplosion;

public class WarViewerEntityProjectile extends WarViewerEntity implements WarViewerObject {
	
	private InGameTeam team;
	private Sprite sprite;
	
	public WarViewerEntityProjectile(int id, int patchX, int patchY, int width,
			int height, InGameTeam team) {
		this.id = id;
		this.patchX = patchX;
		this.patchY = patchY;
		this.width = width;
		this.height = height;
		this.team = team;
		this.sprite = new Sprite(
				WarViewerGdx.getTexture("rocket"));
		
		x = getXByPatches();
		y = getYByPatches();
	}

	@Override
	public void render(float delta, OrthographicCamera camera, SpriteBatch batch) {
		updatePosition();
		batch.begin();
			batch.draw(sprite.getTexture(), getXByPatches(), getYByPatches(), width, height);
		batch.end();
	}
	
	private void updatePosition() {
		WarGame g = WarGame.getInstance();
		if(g.getPlayerTeam(team.getName()) != null)
		{
			List<WarProjectile> projectiles = new ArrayList<WarProjectile>(g.getPlayerTeam(team.getName()).getProjectiles());
			for (WarProjectile projectile: projectiles) {
				if (projectile.getID() == id) {
					patchX = (int) projectile.getX();
					patchY = (int) projectile.getY();
				}
			}
		}
	}
	
	public WarViewerAnimationExplosion explode() {
		return new WarViewerAnimationExplosion(getXByPatches(), getYByPatches(), 32, 32, false);
	}

	public InGameTeam getTeam() {
		return team;
	}
}
