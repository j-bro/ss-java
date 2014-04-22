/**
 * The screen set to create a level
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Planet;
import com.asdf.ssjava.entities.Powerup;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * @author Jeremy Brown
 */
public class LevelCreator implements Screen {

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
	 * A list of entities to cycle through
	 */
	Array<AbstractEntity> entityTypes;
	
	/**
	 * The currently selected entity
	 */
	public AbstractEntity selectedEntity;
	
	/**
	 * Sprite batch for creator-specific screen elements
	 */
	SpriteBatch batch;
	
	/**
	 * Default constructor
	 * @param game the game instance
	 */
	public LevelCreator(SSJava game) {
		this.game = game;
		gameWorld = new GameWorld(game, GameWorld.CREATOR_TYPE, null);
		renderer = new WorldRenderer(gameWorld);
		gameWorld.setRenderer(renderer);
		gameWorld.setManager(new LevelCreatorInput());
		Gdx.input.setInputProcessor(gameWorld.getManager());
		
		entityTypes = new Array<AbstractEntity>() {{
			add(new Asteroid());
			add(new SpaceRock());
			add(new EnemyType1());
			add(new PowerupHealthUp());
			add(new PowerupSpeedOfLight());
			add(new Planet());
		}};
		
		
		renderer.setEntityToAdd(entityTypes.get(0)); 
		
		batch = new SpriteBatch();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		renderer.render();
		gameWorld.update();
		
		// TODO outline selectedEntity	
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
		if (gameWorld != null) {
			Gdx.input.setInputProcessor(gameWorld.getManager());
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
		gameWorld.dispose();
	}

	/**
	 * Show the options screen
	 * Allows saving/loading of a level
	 */
	public void showCreatorOptions() {
		game.setScreen(new LevelCreatorOptionsMenu(game, this));
	}
	
	/** 
	 * @return the entity following entityToAdd in the entityTypes array
	 */
	public AbstractEntity getNextEntityType() {
		if (entityTypes.indexOf(renderer.getEntityToAdd(), false) == entityTypes.size - 1) {
			return entityTypes.get(0);
		}
		else {
			return entityTypes.get(entityTypes.indexOf(renderer.getEntityToAdd(), false) + 1);
		}
	}
	/** 
	 * @return the entity previous to entityToAdd in the entityTypes array
	 */
	public AbstractEntity getPrevEntityType() {
		if (entityTypes.indexOf(renderer.getEntityToAdd(), false) == 0) {
			return entityTypes.get(entityTypes.size - 1);
		}
		else {
			return entityTypes.get(entityTypes.indexOf(renderer.getEntityToAdd(), false) - 1);
		}
	}
	
	/**
	 * Adds a new instance of the passed entity type to the world
	 * @param e the entity type to be added
	 */
	protected void addEntity(AbstractEntity e) {
		if (e instanceof Asteroid) {
			Asteroid a = new Asteroid(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), Asteroid.DEFAULT_WIDTH, Asteroid.DEFAULT_HEIGHT, Asteroid.DEFAULT_ROTATION);
			selectedEntity = a;
			gameWorld.getObstacles().add(a);
		}
		else if (e instanceof SpaceRock) {
			SpaceRock s = new SpaceRock(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), SpaceRock.DEFAULT_WIDTH, SpaceRock.DEFAULT_HEIGHT, SpaceRock.DEFAULT_ROTATION);
			selectedEntity = s;
			gameWorld.getObstacles().add(s);
		}
		else if (e instanceof Planet) {
			Planet p = new Planet(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), Planet.DEFAULT_WIDTH, Planet.DEFAULT_HEIGHT, Planet.DEFAULT_ROTATION);
			selectedEntity = p;
			gameWorld.getObstacles().add(p);
		}
		else if (e instanceof EnemyType1) {
			EnemyType1 e1 = new EnemyType1(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), EnemyType1.DEFAULT_WIDTH, EnemyType1.DEFAULT_HEIGHT, EnemyType1.DEFAULT_ROTATION);
			selectedEntity = e1;
			gameWorld.getEnemies().add(e1);
		}
		else if (e instanceof PowerupHealthUp) {
			PowerupHealthUp p = new PowerupHealthUp(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), PowerupHealthUp.DEFAULT_WIDTH, PowerupHealthUp.DEFAULT_HEIGHT, PowerupHealthUp.DEFAULT_ROTATION);
			selectedEntity = p;
			gameWorld.getPowerups().add(p);
		}
		else if (e instanceof PowerupSpeedOfLight) {
			PowerupSpeedOfLight p = new PowerupSpeedOfLight(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), PowerupSpeedOfLight.DEFAULT_WIDTH, PowerupSpeedOfLight.DEFAULT_HEIGHT, PowerupSpeedOfLight.DEFAULT_ROTATION);
			selectedEntity = p;
			gameWorld.getPowerups().add(p);
		}
		Gdx.app.log(SSJava.LOG, "Added new " + e.toString());
	}
	
	/**
	 * Sets the currently selected entity
	 */
	public void setSelectedEntity(AbstractEntity e) {
		selectedEntity = e;
		renderer.setSelectedEntity(e);
	}
	
	/**
	 * Remove the selected entity from the level
	 * @param selectedEntity
	 */
	public void removeEntity(AbstractEntity selectedEntity) {
		if (selectedEntity instanceof Obstacle) {
			gameWorld.getObstacles().removeValue((Obstacle) selectedEntity, true);
		}
		else if (selectedEntity instanceof Enemy) {
			gameWorld.getEnemies().removeValue((Enemy) selectedEntity, true);
		}
		else if (selectedEntity instanceof Powerup) {
			gameWorld.getPowerups().removeValue((Powerup) selectedEntity, true);
		}
	}
	
	
	/**
	 * @return the gameWorld
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
	}

	/**
	 * Input manager for level creator
	 * @author Jeremy Brown
	 */
	class LevelCreatorInput implements InputProcessor {

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
		 */
		@Override
		public boolean keyDown(int keycode) {
			switch (keycode) {
			// Selected entity movement
			case Keys.RIGHT: 
				selectedEntity.getPosition().x += 1;
				break;
			case Keys.LEFT: 
				selectedEntity.getPosition().x -= 1;
				break;
			case Keys.UP:
				selectedEntity.getPosition().y += 1;
				break;
			case Keys.DOWN:
				selectedEntity.getPosition().y -= 1;
				break;
			
			// Pause / options menu
			case Keys.ESCAPE:
				 showCreatorOptions();
				break;
				
			// Adding new entities
			case Keys.V:
				addEntity(renderer.getEntityToAdd());
				break;
			case Keys.C:
				renderer.setEntityToAdd(getNextEntityType());
				break;
			case Keys.X:
				renderer.setEntityToAdd(getPrevEntityType());
				break;
			case Keys.Z:
				removeEntity(selectedEntity);
				break;
			default: break;
		}
		return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// Check if click is on an entity
			// Select the entity
			for (Obstacle o: gameWorld.getObstacles()) {
				if (isClickOnEntity(o, screenX, screenY)) {
					selectedEntity = o;
					Gdx.app.log(SSJava.LOG, "Selected entity: " + o.toString() + " " + Integer.toHexString(o.hashCode()));
					return true;
				}
			}
			for (Enemy e: gameWorld.getEnemies()) {
				if (isClickOnEntity(e, screenX, screenY)) {
					selectedEntity = e;
					Gdx.app.log(SSJava.LOG, "Selected entity: " + e.toString() + " " + Integer.toHexString(e.hashCode()));
					return true;
				}
			}
			for (Powerup p: gameWorld.getPowerups()) {
				if (isClickOnEntity(p, screenX, screenY)) {
					selectedEntity = p;
					Gdx.app.log(SSJava.LOG, "Selected entity: " + p.toString() + " " + Integer.toHexString(p.hashCode()));
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO relative movement
			if (isClickOnEntity(selectedEntity, screenX, screenY)) {
				Vector3 v = new Vector3(screenX, screenY, 0);
				renderer.getCamera().unproject(v);
				selectedEntity.setPosition(new Vector2(v.x, v.y));
				return true;			
			}
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}
		
		/**
		 * Checks whether or not the click is on the selected entity
		 * @param e the entity to be checked
		 * @param screenX the x coordinate of the pointer 
		 * @param screenY the y coordinate of the pointer
		 * @return
		 */
		public boolean isClickOnEntity(AbstractEntity e, float screenX, float screenY) {
			Vector3 v = new Vector3(screenX, screenY, 0);
			renderer.getCamera().unproject(v);
			Vector2 op = e.getPosition();
			if (v.x >= op.x - e.getWidth() / 2 && v.x <= op.x + e.getWidth() / 2 && v.y >= op.y - e.getHeight() / 2 && v.y <= op.y + e.getHeight() / 2) {
				return true;
			}
			return false;
		}
	}
}

