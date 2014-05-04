/**
 * The screen set to play the game.
 * Contains instances of the game, world and renderer.
 * Calls all render and update methods.
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.GameCollisionListener;
import com.asdf.ssjava.world.GameInputManager;
import com.asdf.ssjava.world.GameWorld;
import com.asdf.ssjava.world.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;

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
	GameWorld gameWorld;
	
	/**
	 * The renderer instance
	 */
	WorldRenderer renderer;
	
	/**
	 * Constructor for testing levels
	 */
	public GameScreen(SSJava game, FileHandle levelFile, LevelCreatorScreen creator) {
		this.game = game;
		gameWorld = new GameWorld(game, GameWorld.GAME_TYPE, levelFile, creator);
		renderer = new WorldRenderer(gameWorld);
		gameWorld.setRenderer(renderer);
		gameWorld.setManager(new GameInputManager(game, gameWorld));
		Gdx.input.setInputProcessor(gameWorld.getManager());
	}
	
	/**
	 * Constructor of the Game Screen which takes 
	 * @param game The game instance of type SSJava
	 */
	public GameScreen(SSJava game, FileHandle levelFile) {
		this.game = game;
		gameWorld = new GameWorld(game, GameWorld.GAME_TYPE, levelFile);
		renderer = new WorldRenderer(gameWorld);
		gameWorld.setRenderer(renderer);
		gameWorld.setManager(new GameInputManager(game, gameWorld));
		Gdx.input.setInputProcessor(gameWorld.getManager());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		renderer.render();
		gameWorld.box2DWorld.step(1/60f, 6, 2);
		gameWorld.update();
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
		if (gameWorld != null) {
			Gdx.input.setInputProcessor(gameWorld.getManager());
			gameWorld.box2DWorld.setContactListener(new GameCollisionListener(gameWorld));
		}
		
		// Music
		if (AudioPlayer.menuMusic.isPlaying()) {			
			AudioPlayer.stopMenuMusic();
		}
		
		if (!gameWorld.isLevelComplete()) {			
			AudioPlayer.playGameMusic(true);
			AudioPlayer.resumeGameSounds();
		}
	}
	
	/**
	 * @return the gameWorld
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
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
		gameWorld.pauseGame();
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
		gameWorld.dispose();
		renderer.dispose();
	}
}
