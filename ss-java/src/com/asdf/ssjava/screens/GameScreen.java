/**
 * The screen set to play the game.
 * Contains instances of the game, world and renderer.
 * Calls all render and update methods.
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.World;
import com.asdf.ssjava.world.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * @author Jeremy Brown
 *
 */
public class GameScreen implements Screen {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The world instance
	 */
	World world;
	
	/**
	 * The renderer instance
	 */
	WorldRenderer renderer;
	
	/**
	 * Constructor of the Game Screen which takes 
	 * @param game The game instance of type SSJava
	 */
	public GameScreen(SSJava game) {
		this.game = game;
		world = new World(game);
		renderer = new WorldRenderer(world);
		world.setRenderer(renderer);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		world.update();
		renderer.render();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (world != null) {
			Gdx.input.setInputProcessor(world.getManager());
		}
		Gdx.app.log(SSJava.LOG, "Show game");
		AudioPlayer.playGameMusic(true);

	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		AudioPlayer.pauseGameMusic();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {

	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		game.setScreen(new PauseMenu(game, this));
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		world.dispose();
		renderer.dispose();
	}
}
