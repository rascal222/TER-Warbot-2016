package edu.warbot.gui.viewer.gdx.entities;

import java.awt.Point;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.gui.viewer.gdx.WarViewerWorld;
import edu.warbot.gui.viewer.gdx.animations.WarViewerAnimationExplosion;
import edu.warbot.gui.viewer.gdx.entities.info.WarViewerEntityFoodIndicator;
import edu.warbot.gui.viewer.gdx.entities.info.WarViewerEntityHealthBar;
import edu.warbot.gui.viewer.gdx.entities.info.WarViewerEntityUnityInfo;

public abstract class WarViewerEntityControllable extends WarViewerEntity {
	
	protected static int EAST = 0;
	protected static int NORTH = 1;
	protected static int NORTH_EAST = 2;
	protected static int NORTH_WEST = 3;
	protected static int SOUTH = 4;
	protected static int SOUTH_EAST = 5;
	protected static int SOUTH_WEST = 6;
	protected static int WEST = 7;
	
	protected static final String[] teamsColors = {"blue", "red"};
	protected static int nbPositions = 8;
	
	protected double heading;
	protected int color;
	protected Point lastPatch;
	protected int health;
	protected int maxHealth;
	
	protected WarViewerEntityHealthBar healthBar;
	protected WarViewerEntityFoodIndicator foodIndicator;
	protected WarViewerEntityUnityInfo unityInfo;
	
	public WarViewerEntityControllable(int id, int patchX, int patchY,
			int teamColor, double heading, int health, int maxHealth) {
		this.id = id;
		this.patchX = patchX;
		this.patchY = patchY;
		this.health = health;
		this.maxHealth = maxHealth;
		this.color = teamColor;

		this.heading = heading;
			
		x = getXByPatches();
		y = getYByPatches();
		
		lastPatch = new Point(patchX, patchY);
	}
	
	public abstract void render(float delta, OrthographicCamera camera, SpriteBatch batch);
	
	protected void initInfos() {
		healthBar = new WarViewerEntityHealthBar(this.health, this.maxHealth, this);
		foodIndicator = new WarViewerEntityFoodIndicator(this);
		unityInfo = new WarViewerEntityUnityInfo(this);
	}
	
	protected int getIsometricOrientationByHeading() {
		if (heading < 23 || heading >= 338 && heading <= 360) {
			return SOUTH_EAST;
		} else if (heading >= 23 && heading < 68) {
			return SOUTH;
		} else if (heading >= 68 && heading < 113) {
			return SOUTH_WEST;
		} else if (heading >= 113 && heading < 158) {
			return WEST;
		} else if (heading >= 158 && heading < 203) {
			return NORTH_WEST;
		} else if (heading >= 203 && heading < 248) {
			return NORTH;
		} else if (heading >= 248 && heading < 293) {
			return NORTH_EAST;
		} else /*if (heading >= 293 && heading < 338)*/ {
			return EAST;
		}
	}
	
	public void update(double x, double y, double heading, int health, int bagSize) {
		
		lastPatch.x = patchX;
		lastPatch.y = patchY;
		
		this.heading = heading;
		this.patchX = (int) x;
		this.patchY = (int) y;
		this.health = health;
		
		this.x = getXByPatches();
		this.y = getYByPatches();
		
		healthBar.update();
		foodIndicator.update(bagSize != 0);
	}
	
	protected ControllableWarAgent getCurrentWarAgent() {
//		WarGame g = WarGame.getInstance();
//		for (ControllableWarAgent ag : g.getPlayerTeams().get(color)
//				.getControllableAgents()) {
//			if (ag.getID() == id) {
//				return ag;
//			}
//		}
//		return null;
		return (ControllableWarAgent) WarViewerWorld.getInGameTeams().get(color).getAgentWithID(id);
	}
	
	protected boolean isMoving(WarAgent agent) {
		return patchX != lastPatch.x || patchY != lastPatch.y;
	}
	
	public WarViewerAnimationExplosion explode() {
		return new WarViewerAnimationExplosion(getXByPatches(), getYByPatches(), height, height, true);
	}

	public WarViewerEntityHealthBar getHealthBar() {
		return healthBar;
	}

	public int getHealth() {
		return health;
	}

	public WarViewerEntityFoodIndicator getFoodIndicator() {
		return foodIndicator;
	}

	public WarViewerEntityUnityInfo getUnityInfo() {
		return unityInfo;
	}
}
