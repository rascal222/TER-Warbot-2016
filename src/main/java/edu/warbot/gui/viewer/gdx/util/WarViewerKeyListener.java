package edu.warbot.gui.viewer.gdx.util;



import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import edu.warbot.gui.viewer.gdx.screens.WarViewerScreen;

public class WarViewerKeyListener implements InputProcessor {
	
	private OrthographicCamera camera;
	
	private float mouveCameraSpeed = 0.1f;
	private final float zoomSpeed = 0.1f;
	
	private int xMouseLeftDown;
	private int yMouseLeftDown;
	
	public WarViewerKeyListener(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		if (arg0 == Keys.F12) {
			if (WarViewerScreen.GAME_SCREEN.isShowInfo()) {
				WarViewerScreen.GAME_SCREEN.setShowInfo(false);
			} else {
				WarViewerScreen.GAME_SCREEN.setShowInfo(true);
			}
		}
		
		if (arg0 == Keys.F11) {
			if (WarViewerScreen.GAME_SCREEN.isShowInitialsUnities()) {
				WarViewerScreen.GAME_SCREEN.setShowInitialsUnities(false);
			} else {
				WarViewerScreen.GAME_SCREEN.setShowInitialsUnities(true);
			}
		}
		/*
		if (arg0 == Keys.P) {
			WarViewerScreen.GAME_SCREEN.getGameGdx().setScreen(
					WarViewerScreen.GAME_SCREEN.getGameGdx().getPauseScreen());
		}*/
		
		if(arg0 == Keys.ESCAPE)
			Gdx.app.exit();
		return true;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		switch (arg0) {
		case -1: // molette haut
			if (camera.zoom == 1.0) {
				return false;
			}
			camera.zoom -= zoomSpeed;
			break;
		case 1: // molette bas
			camera.zoom += zoomSpeed;
			break;
		}
		return true;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		xMouseLeftDown = arg0;
		yMouseLeftDown = arg1;
		return true;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		Vector2 vect = new Vector2(xMouseLeftDown - arg0, yMouseLeftDown - arg1);
		camera.translate(vect.x * mouveCameraSpeed, vect.y * (- mouveCameraSpeed));
		return true;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		if (arg3 == 1) { //right click
			camera.zoom = 1.0f;
		}
		return true;
	}
}
