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
 * The screen shown during gameplay.
 * Contains instances of the game, world and renderer.
 * Calls all render and update methods.
 * @author Jeremy Brown
 * @author Simon Thompson
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
	 * Constructor for testing levels.
	 * @param game the SSJava instance
	 * @param levelFile the FileHandle for the level
	 * @param creator the level creator screen instance for reference to return to
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
	 * @param levelFile the FileHandle for the level
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
		renderer.createHUD(width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show game");
		if (gameWorld != null) {
			Gdx.input.setInputProcessor(gameWorld.getManager());
			gameWorld.box2DWorld.setContactListener(new GameCollisionListener(gameWorld));
		}
		
		// Game Music
		if (AudioPlayer.menuMusic.isPlaying()) {			
			AudioPlayer.stopMenuMusic();
		}
		
		if (!gameWorld.isLevelComplete()) {			
			AudioPlayer.playGameMusic(true);
			AudioPlayer.resumeGameSounds();
		}
	}
	
	/**
	 * Gets the GameWorld instance. 
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
