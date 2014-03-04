/**
 * 
 */
package com.asdf.ssjava.screens;

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
	 * 
	 */
	SSJava game;
	
	/**
	 * 
	 */
	World world;
	
	/**
	 * 
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
	}

	@Override
	public void render(float delta) {
		world.update();
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show game");

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		world.dispose();
	}

}
