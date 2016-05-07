package edu.warbot.gui.viewer.gdx.entities;


import edu.warbot.gui.viewer.gdx.WarViewerMap;

public abstract class WarViewerEntity {
	
	protected int id;
	protected int x, y;
	protected int width, height;
	protected int tileX, tileY;
	protected int patchX, patchY;
	
	protected int getXByPatches() {
		return (WarViewerMap.ORIGINX + (patchX - patchY) * WarViewerMap.PATCH_WIDTH / 2) - width / 2;
	}
	
	protected int getYByPatches() {
		return (WarViewerMap.ORIGINY - (patchX + patchY) * WarViewerMap.PATCH_HEIGHT / 2) - height / 2;
	}
	
	public void setPatchX(int patchX) {
		this.patchX = patchX;
	}

	public void setPatchY(int patchY) {
		this.patchY = patchY;
	}
	
	public int getId() {
		return id;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}