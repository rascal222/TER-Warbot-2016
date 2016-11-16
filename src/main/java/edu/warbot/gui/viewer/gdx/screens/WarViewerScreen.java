package edu.warbot.gui.viewer.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import edu.warbot.gui.viewer.gdx.WarViewerGdx;
import edu.warbot.gui.viewer.gdx.WarViewerMap;
import edu.warbot.gui.viewer.gdx.WarViewerWorld;
import edu.warbot.gui.viewer.gdx.animations.WarViewerAnimationExplosion;
import edu.warbot.gui.viewer.gdx.entities.WarViewerEntityControllable;
import edu.warbot.gui.viewer.gdx.entities.WarViewerEntityFood;
import edu.warbot.gui.viewer.gdx.entities.WarViewerEntityProjectile;
import edu.warbot.gui.viewer.gdx.util.WarViewerKeyListener;

public class WarViewerScreen implements Screen
{
	public static WarViewerScreen GAME_SCREEN;
	
	private OrthographicCamera camera;
	private WarViewerKeyListener keyListener;
	private WarViewerWorld world;
	
	private final WarViewerGdx gameGdx;
	
	private boolean showInfo;
	private boolean showInitialsUnities;
	
	boolean gamePaused;
    public BitmapFont font;

	public WarViewerScreen(final WarViewerGdx gameGdx)
	{
		camera = new OrthographicCamera(WarViewerGdx.WIDTH, WarViewerGdx.HEIGHT);
		camera.setToOrtho(false, WarViewerGdx.WIDTH, WarViewerGdx.HEIGHT);

		keyListener = new WarViewerKeyListener(camera);
		
		this.gameGdx = gameGdx;

		GAME_SCREEN = this;
		
		Gdx.input.setInputProcessor(keyListener);
		
		showInfo = true;
		showInitialsUnities = true;
		
		gamePaused = true;
		font = gameGdx.font;
	}
	
	@Override
	public void show() {}

	@Override
	public void dispose() {}

	@Override
	public void hide() {}

	@Override
	public void pause()
	{
		gamePaused = true;
	}

	@Override
	public void resume()
	{
		gamePaused = false;
	}

	@Override
	public void resize(int arg0, int arg1) {}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(1, 1, 1, 1); // fond en blanc
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		gameGdx.batch.setProjectionMatrix(camera.combined);
		
		SpriteBatch batch = gameGdx.batch;
		
		world.updateWorld();
		world.getMap().render(delta, camera, batch);
		
		for (WarViewerAnimationExplosion entity: world.getEntitiesControllableExplosions()) {
			if (entity.isFinished()) { //affichage des traces d explosion en premier
				entity.render(delta, camera, batch);
			}
		}
		
		for (WarViewerEntityFood entity : world.getEntitiesResource()) {
			entity.render(delta, camera, batch);
		}
		
		for (WarViewerEntityControllable entity: world.getEntitiesControllable()) {
			entity.render(delta, camera, batch);
		}
		for (WarViewerEntityProjectile entity: world.getEntitiesProjectile()) {
			entity.render(delta, camera, batch);
		}
		
		if (showInfo) {
			for (WarViewerEntityControllable entity: world.getEntitiesControllable()) {
				entity.getHealthBar().render(delta, camera, batch);
				entity.getFoodIndicator().render(delta, camera, batch);	
			}
		}
		
		if (showInitialsUnities) {
			for (WarViewerEntityControllable entity: world.getEntitiesControllable()) {
				entity.getUnityInfo().render(delta, camera, batch);
			}
		}
		
		for (WarViewerAnimationExplosion entity: world.getEntitiesControllableExplosions()) {
			if (!entity.isFinished()) { // affichage des explosions au dessus du reste
				entity.render(delta, camera, batch);
			}
		}
		
		if(gamePaused)
		{
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		    
		    float saveZoom = camera.zoom;
			camera.zoom = 0.5f;
			camera.update();
			gameGdx.batch.setProjectionMatrix(camera.combined);

			ShapeRenderer shapeRenderer = new ShapeRenderer();
		    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		    shapeRenderer.setProjectionMatrix(camera.combined);
		    shapeRenderer.setColor(0,0,0,0.3f);
			shapeRenderer.rect((camera.position.x - WarViewerGdx.WIDTH/2), (camera.position.y - WarViewerGdx.HEIGHT/2), WarViewerGdx.WIDTH, WarViewerGdx.HEIGHT);
		    shapeRenderer.end();
		    
			batch.begin();
				font.setColor(Color.WHITE);
				font.draw(batch, "Simulation en pause", camera.position.x-font.getSpaceWidth()*"Simulation en pause     ".length(), camera.position.y);
			batch.end();
			
			camera.zoom = saveZoom;
		}
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public WarViewerGdx getGameGdx() {
		return gameGdx;
	}
	
	public WarViewerMap getMap() {
		return world.getMap();
	}
	
	public void setShowInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}

	public boolean isShowInfo() {
		return showInfo;
	}

	public boolean isShowInitialsUnities() {
		return showInitialsUnities;
	}

	public void setShowInitialsUnities(boolean showInitialsUnities) {
		this.showInitialsUnities = showInitialsUnities;
	}
	
	public void loadNewWorld()
	{
		world = new WarViewerWorld();
		world.initEntities();
	}
}
