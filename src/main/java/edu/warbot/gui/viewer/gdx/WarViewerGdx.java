package edu.warbot.gui.viewer.gdx;


import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.WarGameListener;
import edu.warbot.gui.viewer.gdx.screens.WarViewerScreen;

public class WarViewerGdx extends Game implements WarGameListener
{
	public static int WIDTH;
	public static int HEIGHT;
	
	private WarViewerScreen gameScreen;
	
	public SpriteBatch batch;
    public BitmapFont font;
    
	private static HashMap<String, Texture> textures;
	private static TiledMap mapTexture;
	static IsometricTiledMapRenderer mapRenderer;

    public WarViewerGdx(int panelWidth, int panelHeight)
    {
    	WIDTH = panelWidth;
    	HEIGHT = panelWidth;
	}
    
	@Override
	public void create()
	{
		WarGame.addWarGameListener(this);

		batch = new SpriteBatch();
        font = new BitmapFont();
        loadTextures();
		gameScreen = new WarViewerScreen(this);
		setScreen(null);
	}
	
	public static void loadTextures() {
		textures = new HashMap<String, Texture>();
		textures.put(
				"base_blue",
				new Texture(Gdx.files
						.internal("assets/textures/agents/base_blue.png")));
		textures.put(
				"base_red",
				new Texture(Gdx.files
						.internal("assets/textures/agents/base_red.png")));
		textures.put(
				"turret_blue",
				new Texture(Gdx.files
						.internal("assets/textures/agents/turret_blue.png")));
		textures.put(
				"turret_red",
				new Texture(Gdx.files
						.internal("assets/textures/agents/turret_red.png")));
		textures.put(
				"sheet_blue",
				new Texture(Gdx.files
						.internal("assets/textures/agents/sheet_blue.png")));
		textures.put(
				"sheet_red",
				new Texture(Gdx.files.internal("assets/textures/agents/sheet_red.png")));
		textures.put("food",
				new Texture(Gdx.files.internal("assets/textures/resources/food.png")));
		textures.put(
				"rocket",
				new Texture(Gdx.files
						.internal("assets/textures/projectiles/rocket.png")));
		textures.put(
				"explosion",
				new Texture(Gdx.files
						.internal("assets/textures/animations/explosion.png")));
		textures.put(
				"explosion_mark",
				new Texture(Gdx.files
						.internal("assets/textures/animations/explosion_mark.png")));
		
		textures.put(
				"health_bar",
				new Texture(Gdx.files
						.internal("assets/textures/info/health_bar.png")));
		
		textures.put(
				"food_indicator",
				new Texture(Gdx.files
						.internal("assets/textures/info/food_indicator.png")));
		
		textures.put(
				"unities_info",
				new Texture(Gdx.files
						.internal("assets/textures/info/unities_info.png")));
		
		mapTexture = new TmxMapLoader().load("assets/maps/" + "map_8x8-patch_125_75_64_32_2" + ".tmx");
		mapRenderer = new IsometricTiledMapRenderer(mapTexture);
		
	}
	
	@Override
	public void dispose()
	{
        for(Texture t : textures.values())
			t.dispose();
		textures.clear();
		gameScreen.dispose();
		batch.dispose();
		font.dispose();
		super.dispose();
	}

	@Override
	public void render()
	{		
		super.render();
	}

	@Override
	public void resize(int width, int height)
	{
		WIDTH = width;
		HEIGHT = height;
		super.resize(width, height);
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}

	public Screen getScreen()
	{
		return screen;
	}
	
	public static Texture getTexture(String key) {
		return textures.get(key);
	}
	
	public static TiledMap getMapTexture() {
		return mapTexture;
	}
	
	public static IsometricTiledMapRenderer getMapRenderer() {
		return mapRenderer;
	}

	@Override
	public void onNewTeamAdded(InGameTeam newInGameTeam)
	{}

	@Override
	public void onTeamLost(InGameTeam removedInGameTeam)
	{}

	@Override
	public void onGameOver()
	{
		pause();
	}

	@Override
	public void onGameStopped()
	{
		System.out.println("stop");
		setScreen(null);
	}

	@Override
	public void onGameStarted()
	{
		gameScreen.loadNewWorld();
		setScreen(gameScreen);
	}

	@Override
	public void onGamePaused()
	{
		pause();
	}

	@Override
	public void onGameResumed()
	{
		resume();
	}
}
