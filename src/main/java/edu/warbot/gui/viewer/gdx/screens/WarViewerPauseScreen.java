package edu.warbot.gui.viewer.gdx.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.warbot.gui.viewer.gdx.WarViewerGdx;

public class WarViewerPauseScreen implements Screen {
	
	private WarViewerGdx game;
	
	private SpriteBatch batch;
	private BitmapFont font;
	private float x, y;
	
	public WarViewerPauseScreen(WarViewerGdx game) {
		this.game = game;
		batch = game.batch;
		font = game.font;
	}

	@Override
	public void show() {
//		batch = new SpriteBatch();
//		font = new BitmapFont();
//		BitmapFont.TextBounds tb = font.getBounds("Affichage en pause");
//		
//		x = WarViewerGdx.WIDTH / 2 - tb.width / 2;
//		y = WarViewerGdx.HEIGHT / 2 - tb.height / 2;

		x = WarViewerGdx.WIDTH / 2;
		y = WarViewerGdx.HEIGHT / 2;
	}

	@Override
	public void dispose() {}

	@Override
	public void hide() {System.out.println("WarViewerPauseScreen begin/end hide");}

	@Override
	public void pause() {System.out.println("WarViewerPauseScreen begin/end pause");}

	@Override
	public void resume() {System.out.println("WarViewerPauseScreen begin/end resume");}

	@Override
	public void resize(int arg0, int arg1) {}

	@Override
	public void render(float arg0)
	{
		System.out.println("WarViewerPauseScreen begin/end render");
		if (Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.justTouched()) {
			game.setScreen(game.getScreen());
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1); // fond en noir
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
			font.setColor(Color.WHITE);
			font.draw(batch, "Affichage en pause", x, y);
		batch.end();
	}

}
