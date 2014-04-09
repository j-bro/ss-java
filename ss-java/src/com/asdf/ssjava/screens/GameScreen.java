/**
 * The screen set to play the game.
 * Contains instances of the game, world and renderer.
 * Calls all render and update methods.
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.GameInputManager;
import com.asdf.ssjava.world.GameWorld;
import com.asdf.ssjava.world.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

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
	GameWorld world;
	
	/**
	 * The renderer instance
	 */
	WorldRenderer renderer;
		
	/**
	 * Constructor of the Game Screen which takes 
	 * @param game The game instance of type SSJava
	 */
	public GameScreen(SSJava game, String levelPath) {
		this.game = game;
		world = new GameWorld(game, 0, levelPath);
		renderer = new WorldRenderer(world);
		world.setRenderer(renderer);
		world.setManager(new GameInputManager(game, world));
		Gdx.input.setInputProcessor(world.getManager());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		renderer.render();
		world.box2DWorld.step(1/60f, 6, 2);
		world.update();
		
		Gdx.app.log(SSJava.LOG, "Camera: " + renderer.getCamera().position.x + ", " + renderer.getCamera().position.y);
		Gdx.app.log(SSJava.LOG, "Ship: " + world.getShip().getPosition().x + ", " + world.getShip().getPosition().y);
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
		Gdx.app.log(SSJava.LOG, "Show game");
		if (world != null) {
			Gdx.input.setInputProcessor(world.getManager());
		}
		AudioPlayer.playGameMusic(true);

	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		world.pauseGame();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		
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
