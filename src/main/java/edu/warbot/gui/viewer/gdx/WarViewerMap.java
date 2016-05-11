package edu.warbot.gui.viewer.gdx;

import java.awt.Point;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class WarViewerMap implements WarViewerObject
{
	public static final int PATCHES_PER_TILES = 8;
	
	public static int MAP_WIDHT;
	public static int MAP_HEIGHT;
	public static int TILE_WIDTH;
	public static int TILE_HEIGHT;
	public static int PATCH_WIDTH, PATCH_HEIGHT;
	
	public static int ORIGINX;
	public static int ORIGINY;
	
	private int id;
	private String name;
	private TiledMap map;
	private IsometricTiledMapRenderer renderer;
	
	public WarViewerMap(int id, String name, int mapWidth, int mapHeight, int tileWidth, int tileHeight) {
		this.id = id;
		this.name = name;
		map = WarViewerGdx.getMapTexture();
		renderer = WarViewerGdx.getMapRenderer();
		MAP_WIDHT = mapWidth;
		MAP_HEIGHT = mapHeight;
		TILE_WIDTH = tileWidth;
		TILE_HEIGHT = tileHeight;
		ORIGINX = MAP_HEIGHT * TILE_WIDTH / 2;
		ORIGINY = MAP_HEIGHT * TILE_HEIGHT / 2;
		
		PATCH_WIDTH = TILE_WIDTH / PATCHES_PER_TILES;
		PATCH_HEIGHT = TILE_HEIGHT / PATCHES_PER_TILES;
		
	}

	public void render(float delta, OrthographicCamera camera, SpriteBatch batch) {
		batch.begin();
			renderer.setView(camera);
			renderer.render();
		batch.end();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public TiledMap getMap() {
		return map;
	}
	
	public Point getPixelCoordinatesOfTile(int tileX, int tileY) {
		return new Point(ORIGINX + (tileX - tileY) * WarViewerMap.TILE_WIDTH
				/ 2, ORIGINY - (tileX + tileY) * WarViewerMap.TILE_HEIGHT / 2);
	}

	public IsometricTiledMapRenderer getRenderer() {
		return renderer;
	}
	
	public void dispose()
	{
		renderer.dispose();
		map.dispose();
	}
	
}
