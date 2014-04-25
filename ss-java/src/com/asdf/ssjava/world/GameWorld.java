/**
 * Base world class that contains all entities present in the world at the current time.
 * Loads the selected level when game screen is set.
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.screens.LevelCreator;
import com.asdf.ssjava.screens.PauseMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer.Task;

/* Commented imports
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.SpaceRock;
import com.badlogic.gdx.files.FileHandle;
 */

/**
 * @author Jeremy Brown
 *
 */

public class GameWorld {

	/**
	 * The game's instance
	 */
	SSJava game;
	
	/**
	 * The ship's instance
	 */
	Ship ship;
	
	/**
	 * The WorldRenderer instance
	 */
	WorldRenderer renderer;
	
	/**
	 * The InputManager instance
	 */
	InputProcessor manager;
	
	/**
	 * The world type
	 */
	int worldType;
	
	/**
	 * The level creator instance
	 */
	LevelCreator creator;
	
	/**
	 * World type constant definition
	 */
	public static final int GAME_TYPE = 0;
	
	/**
	 * World type constant definition
	 */
	public static final int CREATOR_TYPE = 1;
	
	
	/**
	 * The ScoreKeeper instance
	 */
	ScoreKeeper scoreKeeper;
	
	/**
	 * ArrayList containing all the obstacles in the current level
	 */
	private Level level;
	
	/**
	 * Array containing all the bullets present in the level
	 */
	public Array<Bullet> bullets;
	
	/**
	 * Task to restore default speed (after Speed Of Light powerup)
	 */
	Task resetShipXVelocity;
	
	/**
	 * The Box2D World
	 */
	public World box2DWorld;
	
	public boolean levelCompleted = false;
	/**
	 * Constructor for testing a level from the level creator
	 * @param game
	 * @param worldType
	 * @param levelPath
	 * @param creator
	 */
	public GameWorld(SSJava game, int worldType, String levelPath, LevelCreator creator) {
		this(game, worldType, levelPath);
		this.creator = creator;
	}
	/**
	 * Creates a world for an instance of SSJava
	 * @param game the instance of the game
	 */
	public GameWorld(SSJava game, int worldType, String levelPath) {
		this.game = game;
		this.worldType = worldType;
		
		// Box2D stuff
		box2DWorld = new World(new Vector2(0, 0), true);
		
		ship = new Ship(new Vector2(5, Gdx.graphics.getHeight() / 40), 6, 3, 0, this, box2DWorld);
		// TODO position
		
		bullets = new Array<Bullet>();
		
		level = new Level();
		
		// Level Loading
		if (levelPath != null) {
			loadLevel(levelPath);
			
			// Initialize loaded level elements 
			for (Obstacle o: level.obstacles) {
				o.initWorlds(this, box2DWorld);
				o.createDef();
			}
			for (Enemy e: level.enemies) {
				e.initWorlds(this, box2DWorld);
				e.createDef();
			}
			for (Powerup p: level.powerups) {
				p.initWorlds(this, box2DWorld);
				p.createDef();
			}
			for (Obstacle g: level.gameChangers) {
				g.initWorlds(this, box2DWorld);
				g.createDef();
			}
		}
		
		// Score keeper
		scoreKeeper = new ScoreKeeper();
		
	}
	
	/**
	 * Update method run in every iteration of the main loop to update entity position, rotation and behaviour
	 * Also verifies which entities/bodies are dead and removes them
	 */
	public void update() {
		if (getWorldType() == GAME_TYPE) {
			// Map Box2D physics to game entities
			Array<Body> bodiesArray = new Array<Body>();
			box2DWorld.getBodies(bodiesArray);
			
			Array<Body> deadBodies = new Array<Body>();
			
			// Iterate through all bodies
			for (Body b: bodiesArray) {
				// Get the entity corresponding to the body
			    AbstractEntity e = (AbstractEntity) b.getUserData();
			    
			    if (e != null) {
			    	// Check if the entity is dead and act accordingly
			    	if (e.isDead()) {				
						e.die();
						Gdx.app.log(SSJava.LOG, e.toString() + " " + Integer.toHexString(e.hashCode()) + " died.");
						deadBodies.add(b);
			    	}
			    	else {
			    		// Update the entity's position 
			    		e.update();
			    		e.setPosition(new Vector2(b.getPosition().x, b.getPosition().y));
			    		e.setRotation(MathUtils.radiansToDegrees * b.getAngle());		
			    	}
			    }
			}
			// Remove all dead bodies from Box2D world
			for (Body b: deadBodies) {
				box2DWorld.destroyBody(b);
			}
			
			// Check if the level is completed
			// TODO stop accelerating ship
			if (isLevelComplete() || levelCompleted) {
				levelCompleted();
			}
		}
		
		else if (getWorldType() == CREATOR_TYPE) {
			// Camera controls
			// Key input
			if (Gdx.input.isKeyPressed(Keys.A)) {
				renderer.getCamera().translate(-1, 0, 0);
			}
			else if (Gdx.input.isKeyPressed(Keys.D)) {
				renderer.getCamera().translate(1, 0, 0);
			}
		}
	}
	
	/**
	 * 
	 * @return true if the ship has completed the level
	 */
	public boolean isLevelComplete() {
		if (ship.getBody().getPosition().x >= level.getLevelEnd()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Ship behaviour when level completed
	 */
	public void levelCompleted() {
		Gdx.app.log(SSJava.LOG, "Level completed");
		// Decelerate ship
		if (ship.getBody().getLinearVelocity().x > 0) {
			ship.getBody().applyForceToCenter(-30, 0, false);
		}
		if (ship.getBody().getLinearVelocity().x < 0) {
			ship.getBody().applyForceToCenter(30, 0, false);
		}
		if (ship.getBody().getLinearVelocity().y > 0) {
			ship.getBody().applyForceToCenter(0, -30, false);
		}
		else if (ship.getBody().getLinearVelocity().y < 0) {
			ship.getBody().applyForceToCenter(0, 30, false);
		}
		
		// Start rotating ship
		if (ship.getBody().getLinearVelocity().x < 1 && ship.getBody().getLinearVelocity().x > -1 && ship.getBody().getLinearVelocity().y < 1 && ship.getBody().getLinearVelocity().x > -1) {
			ship.getBody().applyAngularImpulse(30, true);
		}
		
		// TODO ship zoom off into distance
		
	}
	
	/**
	 * Calls the pause screen and stops rendering the game
	 */
	public void pauseGame() {
		game.setScreen(new PauseMenu(game, game.gameScreen));
		AudioPlayer.pauseGameMusic();
	}
	
	/**
	 * Exports the current level to a file in JSON format
	 * @param path the path at which to save the exported level
	 */
	public void exportLevel(String path) {
		Gdx.files.local(path).writeString(new Json().prettyPrint(level), false);
	}
	
	/**
	 * Loads a level from a JSON file into the level
	 * @param path the path of the JSON level file to be loaded
	 */
	private void loadLevel(String path) {		
		level = new Json().fromJson(Level.class, Gdx.files.local(path));
	}
	
	/**
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}
	/**
	 * @return the obstacles array
	 */
	public Array<Obstacle> getObstacles() {
		return level.obstacles;
	}
	/**
	 * @return the enemies array
	 */
	public Array<Enemy> getEnemies() {
		return level.enemies;
	}
	/**
	 * @return the bullets array
	 */
	public Array<Bullet> getBullets() {
		return bullets;
	}
	/**
	 * @return the powerups array
	 */
	public Array<Powerup> getPowerups() {
		return level.powerups;
	}
	/**
	 * @return the gameChangers array
	 */
	public Array<Obstacle> getGameChangers() {
		return level.gameChangers;
	}
	
	/**
	 * Passes the WorldRenderer into this class
	 * @param renderer the renderer to be set
	 */
	public void setRenderer(WorldRenderer renderer) {
		this.renderer = renderer;
	}
	
	/**
	 * @return renderer the associated WorldRenderer instance
	 */
	public WorldRenderer getRenderer() {
		return renderer;
	}
	
	/**
	 * @return the input processor
	 */
	public InputProcessor getManager() {
		return manager;
	}
	
	/**
	 * @return the world's type (game or creator)
	 */
	public int getWorldType() {
		return worldType;
	}
	
	/**
	 * @return level the game world's level instance
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * @return the ScoreKeeper instance
	 */
	public ScoreKeeper getScoreKeeper() {
		return scoreKeeper;
	}

	/**
	 * Set the Input manager instance
	 * @param manager the manager to set
	 */
	public void setManager(InputProcessor manager) {
		this.manager = manager;		
	}
	
	/**
	 * @return the LevelCreator instance
	 */
	public LevelCreator getCreator() {
		return creator;
	}
	
	/**
	 * Dispose method
	 */
	public void dispose() {
		
	}
}
