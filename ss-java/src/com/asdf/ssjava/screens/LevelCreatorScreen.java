package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.MagneticObject;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Planet;
import com.asdf.ssjava.entities.Points;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.SpaceRock;
import com.asdf.ssjava.entities.Sun;
import com.asdf.ssjava.world.GameWorld;
import com.asdf.ssjava.world.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The level creation screen. 
 * Allows the placement of all game entities in the world.
 * Camera moves to place entities throughout the world. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class LevelCreatorScreen implements Screen {

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
	 * True if the level has been modified since it was last saved
	 */
	private boolean levelModified;
	
	/**
	 * Creates a level creator screen with the specified parameters. 
	 * @param game the SSJava instance
	 * @param levelFile the FileHandle for the level
	 */
	public LevelCreatorScreen(SSJava game, FileHandle levelFile) {
		this.game = game;
		gameWorld = new GameWorld(game, GameWorld.CREATOR_TYPE, levelFile, this);
		renderer = new WorldRenderer(gameWorld);
		gameWorld.setRenderer(renderer);
		gameWorld.setManager(new LevelCreatorInput());
		Gdx.input.setInputProcessor(gameWorld.getManager());
		
		entityTypes = new Array<AbstractEntity>() {{
			add(new Asteroid());
			add(new SpaceRock());
			add(new Planet());
			add(new Sun());
			add(new MagneticObject());
			add(new EnemyType1());
			add(new PowerupHealthUp());
			add(new PowerupSpeedOfLight());
			add(new Points());
		}};
		
		
		renderer.setEntityToAdd(entityTypes.get(0)); 
		
		batch = new SpriteBatch();
		
		levelModified = false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		renderer.render();
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
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show level creator");
		if (gameWorld != null) {
			Gdx.input.setInputProcessor(gameWorld.getManager());
		}
		
		// Menu Music
		if (AudioPlayer.menuMusic.isPlaying()) {			
			AudioPlayer.stopMenuMusic();
		}
		
		if (!AudioPlayer.creatorMusic.isPlaying()) {			
			AudioPlayer.playCreatorMusic(true);
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
	 * Shows the options screen which allows testing, saving, and loading of a level, and exiting the creator mode.   
	 */
	public void showCreatorOptions() {
		game.screenshot = ScreenUtils.getFrameBufferTexture();
		game.setScreen(new LevelCreatorOptionsMenu(game, this));
	}
	
	/** 
	 * Gets the following entity in the array of entity types.
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
	 * Gets the previous entity in the array of entity types. 
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
	 * Adds a new instance of the passed entity type to the level. 
	 * @param e the entity type to be added to the level
	 */
	protected void addEntity(AbstractEntity e) {
		if (e instanceof Asteroid) {
			Asteroid a = new Asteroid(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), Asteroid.DEFAULT_WIDTH, Asteroid.DEFAULT_HEIGHT, Asteroid.DEFAULT_ROTATION);
			setSelectedEntity(a);
			gameWorld.getObstacles().add(a);
			updateLevelEnd(a);
		}
		else if (e instanceof SpaceRock) {
			SpaceRock s = new SpaceRock(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), SpaceRock.DEFAULT_WIDTH, SpaceRock.DEFAULT_HEIGHT, SpaceRock.DEFAULT_ROTATION);
			setSelectedEntity(s);
			gameWorld.getObstacles().add(s);
			updateLevelEnd(s);
		}
		else if (e instanceof Planet) {
			Planet p = new Planet(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), Planet.DEFAULT_WIDTH, Planet.DEFAULT_HEIGHT, Planet.DEFAULT_ROTATION);
			setSelectedEntity(p);
			gameWorld.getGameChangers().add(p);
			updateLevelEnd(p);
		}
		else if (e instanceof MagneticObject) {
			MagneticObject m = new MagneticObject(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), MagneticObject.DEFAULT_WIDTH, MagneticObject.DEFAULT_HEIGHT, MagneticObject.DEFAULT_ROTATION);
			setSelectedEntity(m);
			gameWorld.getGameChangers().add(m);
			updateLevelEnd(m);
		}
		else if (e instanceof Sun) {
			Sun s = new Sun(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), Sun.DEFAULT_WIDTH, Sun.DEFAULT_HEIGHT, Sun.DEFAULT_ROTATION);
			setSelectedEntity(s);
			gameWorld.getGameChangers().add(s);
			updateLevelEnd(s);
		}
		else if (e instanceof EnemyType1) {
			EnemyType1 e1 = new EnemyType1(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), EnemyType1.DEFAULT_WIDTH, EnemyType1.DEFAULT_HEIGHT, EnemyType1.DEFAULT_ROTATION);
			setSelectedEntity(e1);
			gameWorld.getEnemies().add(e1);
			updateLevelEnd(e1);
		}
		else if (e instanceof PowerupHealthUp) {
			PowerupHealthUp h = new PowerupHealthUp(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), PowerupHealthUp.DEFAULT_WIDTH, PowerupHealthUp.DEFAULT_HEIGHT, PowerupHealthUp.DEFAULT_ROTATION);
			setSelectedEntity(h);
			gameWorld.getPowerups().add(h);
			updateLevelEnd(h);
		}
		else if (e instanceof PowerupSpeedOfLight) {
			PowerupSpeedOfLight s = new PowerupSpeedOfLight(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), PowerupSpeedOfLight.DEFAULT_WIDTH, PowerupSpeedOfLight.DEFAULT_HEIGHT, PowerupSpeedOfLight.DEFAULT_ROTATION);
			setSelectedEntity(s);
			gameWorld.getPowerups().add(s);
			updateLevelEnd(s);
		}
		else if (e instanceof Points) {
			Points p = new Points(new Vector2(renderer.getCamera().position.x, renderer.getCamera().position.y), Points.DEFAULT_WIDTH, Points.DEFAULT_HEIGHT, PowerupSpeedOfLight.DEFAULT_ROTATION);
			setSelectedEntity(p);
			gameWorld.getPowerups().add(p);
			updateLevelEnd(p);
		}
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Added new " + e.toString() + Integer.toHexString(e.hashCode()));
		levelModified = true;
		
	}
	
	/**
	 * Sets the level end point.
	 * Called adds a new entity past the previous level end point.
	 * @param a the entity added, pushing the end point past it
	 */
	public void updateLevelEnd(AbstractEntity a) {
		if (a.getPosition().x > gameWorld.getLevel().getLevelEnd()) {
			gameWorld.getLevel().setLevelEnd(a.getPosition().x + 30);
		}
	}
	
	/**
	 * Sets the currently selected entity. 
	 */
	public void setSelectedEntity(AbstractEntity e) {
		selectedEntity = e;
		renderer.setSelectedEntity(e);
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Selected entity: " + e.toString() + " " + Integer.toHexString(e.hashCode()));
	}
	
	/**
	 * Remove the selected entity from the level. 
	 * @param selectedEntity the entity to be removed
	 */
	public void removeEntity(AbstractEntity selectedEntity) {
		if (selectedEntity instanceof SpaceRock || selectedEntity instanceof Asteroid) {
			gameWorld.getObstacles().removeValue((Obstacle) selectedEntity, true);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Removed obstacle " + Integer.toHexString(selectedEntity.hashCode()));
		}
		if (selectedEntity instanceof Planet || selectedEntity instanceof Sun || selectedEntity instanceof MagneticObject) {
			gameWorld.getGameChangers().removeValue((Obstacle) selectedEntity, true);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Removed game changer " + Integer.toHexString(selectedEntity.hashCode()));
		}
		else if (selectedEntity instanceof Enemy) {
			gameWorld.getEnemies().removeValue((Enemy) selectedEntity, true);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Removed Enemy " + Integer.toHexString(selectedEntity.hashCode()));
		}
		else if (selectedEntity instanceof Powerup) {
			gameWorld.getPowerups().removeValue((Powerup) selectedEntity, true);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Removed powerup " + Integer.toHexString(selectedEntity.hashCode()));
		}
		
		selectedEntity = null;
		levelModified = true;
	}

	/**
	 * Gets the GameWorld instance. 
	 * @return the gameWorld
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
	}
	
	/**
	 * Checks if the level modified flag is set. 
	 * @return true if the level modified flag is set; false otherwise
	 */
	public boolean isLevelModified() {
		return levelModified;
	}
	/**
	 * Sets the level modified flag. 
	 * @param levelModified the level modified flag to set
	 */
	public void setLevelModified(boolean levelModified) {
		this.levelModified = levelModified;
	}

	/**
	 * True the cursor was clicked on an entity and is still down
	 */
	boolean clickDown = false;
	
	/**
	 * Input manager for level creator. 
	 * Defines behaviour for the different keys in the level creator. 
	 * Camera movement requires continuous polling and is implemented in the GameWorld class. 
	 * @see com.asdf.ssjava.world.GameWorld#update()
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
				if (selectedEntity != null)
					selectedEntity.getPosition().x += 1;
				break;
			case Keys.LEFT: 
				if (selectedEntity != null)
					selectedEntity.getPosition().x -= 1;
				break;
			case Keys.UP:
				if (selectedEntity != null)
					selectedEntity.getPosition().y += 1;
				break;
			case Keys.DOWN:
				if (selectedEntity != null)
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
			
			// Set level end point
			case Keys.E:
				gameWorld.getLevel().setLevelEnd(renderer.getCamera().position.x);
				setLevelModified(true);
				break;
			default: break;
		}
		return true;
		}

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
		 */
		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
		 */
		@Override
		public boolean keyTyped(char character) {
			return false;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
		 */
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// Check if click is on an entity
			// Select the entity
			for (Obstacle o: gameWorld.getObstacles()) {
				if (isClickOnEntity(o, screenX, screenY)) {
					setSelectedEntity(o);
					clickDown = true;
					return true;
				}
			}
			for (Enemy e: gameWorld.getEnemies()) {
				if (isClickOnEntity(e, screenX, screenY)) {
					setSelectedEntity(e);
					clickDown = true;
					return true;
				}
			}
			for (Powerup p: gameWorld.getPowerups()) {
				if (isClickOnEntity(p, screenX, screenY)) {
					setSelectedEntity(p);
					clickDown = true;
					return true;
				}
			}
			for (Obstacle g: gameWorld.getGameChangers()) {
				if (isClickOnEntity(g, screenX, screenY)) {
					setSelectedEntity(g);
					clickDown = true;
					return true;
				}
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
		 */
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			clickDown = false;
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
		 */
		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			if (clickDown) {
				Vector3 v = new Vector3(screenX, screenY, 0);
				renderer.getCamera().unproject(v);
				selectedEntity.setPosition(new Vector2(v.x, v.y));
				levelModified = true;
				return true;			
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
		 */
		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
		 */
		@Override
		public boolean scrolled(int amount) {
			return false;
		}
		
		/**
		 * Checks if the mouse click is on a given entity
		 * @param e the entity to be checked
		 * @param screenX the x coordinate of the mouse pointer 
		 * @param screenY the y coordinate of the mouse pointer
		 * @return true if the mouse click is on the specified entity; false otherwise
		 */
		public boolean isClickOnEntity(AbstractEntity e, float screenX, float screenY) {
			if (e != null) {				
				Vector3 v = new Vector3(screenX, screenY, 0);
				renderer.getCamera().unproject(v);
				Vector2 op = e.getPosition();
				if (v.x >= op.x - e.getWidth() / 2 && v.x <= op.x + e.getWidth() / 2 && v.y >= op.y - e.getHeight() / 2 && v.y <= op.y + e.getHeight() / 2) {
					return true;
				}
			}
			return false;
		}
	}
}

