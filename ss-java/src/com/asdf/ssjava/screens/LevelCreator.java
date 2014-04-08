/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Planet;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.SpaceRock;
import com.asdf.ssjava.world.GameWorld;
import com.asdf.ssjava.world.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * @author Jeremy Brown
 *
 */
public class LevelCreator implements Screen {

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
	 * A list of entities to cycle through
	 */
	Array<AbstractEntity> entityTypes;
	
	/**
	 * The currently selected entity
	 */
	public static AbstractEntity selectedEntity;
	
	
	SpriteBatch batch;
	
	/**
	 * Default constructor
	 * @param game the game instance
	 */
	public LevelCreator(SSJava game) {
		this.game = game;
		world = new GameWorld(game, 1, null);
		renderer = new WorldRenderer(world);
		world.setRenderer(renderer);
		world.setManager(new LevelCreatorInput());
		Gdx.input.setInputProcessor(world.getManager());
		
		/*
		entityTypes = new Array<AbstractEntity>() {{
			add(new Asteroid());
			add(new SpaceRock());
			add(new EnemyType1());
			add(new PowerupHealthUp());
			add(new PowerupSpeedOfLight());
			add(new Planet());
		}};
		
		
		for (AbstractEntity e: entityTypes) {
			e.addListener(new DragListener());
		}
		selectedEntity = entityTypes.get(1);
		*/
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		renderer.render();
		
		batch.begin();
		// TODO 
//			batch.draw(selectedEntity.getTexture(), renderer.getCamera().position.x + renderer.getCamera().viewportWidth / 2 - 2, renderer.getCamera().position.y - renderer.getCamera().viewportHeight / 2 + 2);
		
		batch.end();
		
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
		Gdx.app.log(SSJava.LOG, "Show level creator");
		if (world != null) {
			Gdx.input.setInputProcessor(world.getManager());
		}
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
		showCreatorOptions();
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
		renderer.dispose();
		world.dispose();
	}

	/**
	 * Show the options screen
	 * Allows to save/load a level
	 */
	public void showCreatorOptions() {
		game.setScreen(new LevelCreatorOptionsMenu(game, this));
	}
	
	/**
	 * Input manager for level creator
	 * @author Jeremy Brown
	 *
	 */
	class LevelCreatorInput implements InputProcessor {

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
		 */
		@Override
		public boolean keyDown(int keycode) {
			switch (keycode) {
			case Keys.RIGHT: 
				renderer.getCamera().translate(1, 0, 0);
				break;
			case Keys.LEFT: 
				renderer.getCamera().translate(-1, 0, 0);
				break;
			case Keys.A:
				renderer.getCamera().translate(1, 0, 0);
				break;
			case Keys.D:
				renderer.getCamera().translate(-1, 0, 0);
				break;
			case Keys.SPACE: 
				
				break;
			case Keys.ESCAPE:
				 showCreatorOptions();
				break;
			case Keys.ENTER: // for testing enemy firing
				
				break;
			default: break;
		}
		return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}

