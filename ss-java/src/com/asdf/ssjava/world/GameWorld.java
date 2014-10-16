package com.asdf.ssjava.world;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.*;
import com.asdf.ssjava.screens.GameScreen;
import com.asdf.ssjava.screens.LevelCompletedMenu;
import com.asdf.ssjava.screens.LevelCreatorScreen;
import com.asdf.ssjava.screens.LevelRetryMenu;
import com.asdf.ssjava.screens.PauseMenu;
import com.asdf.ssjava.screens.screenelements.Toast;
import com.asdf.ssjava.screens.screenelements.ToastMessage;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Base world class containing all entities present in the world at the current time.
 * Manages updating the entities, the score, loading, beginning and ending the level.
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class GameWorld {

	/**
	 * The SSJava instance
	 */
	SSJava game;
	
	/**
	 * The ship instance
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
	LevelCreatorScreen creator;
	
	/**
	 * World game type constant definition
	 */
	public static final int GAME_TYPE = 0;
	
	/**
	 * World creator type constant definition
	 */
	public static final int CREATOR_TYPE = 1;
	
	/**
	 * The ScoreKeeper instance
	 */
	ScoreKeeper scoreKeeper;
	
	/**
	 * Array containing all the obstacles in the current level
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
	
	/**
	 * The file containing the current level
	 */
	FileHandle levelFile;
	
	/**
	 * The ship's heat indicator
	 */
	public double shipHeatIndicator;
	
	/**
	 * The time in seconds
	 */
	private double time = 1;
	
	/**
	 * The ship's current progress in the level
	 */
	private int progress;
	
	/**
	 * If the current level has stopped being played (either ship died or end of level reached)
	 */
	private boolean playEnded = false;
	
	/**
	 * If the ship is within range of the sun
	 */
	public boolean sunActivated = false;
	/**
	 * The Sun instance
	 */
	public Sun sun;
	/**
	 * If the ship is within range of a planet
	 */
	public boolean gravityActivated = false;
	/**
	 * The Planet instance
	 */
	public Planet planet;
	/**
	 * If the ship is within range of a magnetic object
	 */
	public boolean magnetActivated = false;
	/**
	 * The MagneticObject instance
	 */
	public MagneticObject magnet;
	
	/**
	 * Creates a world instance from the specified parameters. 
	 * Constructor for testing a level from the level creator. 
	 * Helper constructor that sets the creator and uses the main constructor. 
	 * Allows for returning to the creator when the play is ended. 
	 * @param game the SSJava instance
	 * @param worldType the type of this world instance (game or creator)
	 * @param levelFile the FileHandle for the file of this level
	 * @param creator the level creator screen instance (if worldType is CREATOR_TYPE)
	 */
	public GameWorld(SSJava game, int worldType, FileHandle levelFile, LevelCreatorScreen creator) {
		this(game, worldType, levelFile);
		this.creator = creator;
	}
	/**
	 * Creates a world instance from the specified parameters. 
	 * @param game the SSJava instance
	 * @param worldType the type of this world instance (game or creator)
	 * @param levelFile the FileHandle for the file of this level
	 */
	public GameWorld(SSJava game, int worldType, FileHandle levelFile) {
		this.game = game;
		this.worldType = worldType;
		this.levelFile = levelFile;
		
		// Box2D stuff
		box2DWorld = new World(new Vector2(0, 0), true);
		
		ship = new Ship(new Vector2(5, 18), 6, 3, 0, this, box2DWorld);
		
		bullets = new Array<Bullet>();
		
		level = new Level();
		
		// Level Loading
		if (levelFile != null) {
			loadLevel(levelFile);
			
			// Initialize level if invalid file
			if (level == null) {
				level = new Level();
			}
			
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
		
		setProgress(0);
		
		if (worldType == GameWorld.GAME_TYPE) {	
			AudioPlayer.levelStart();
		}
	}
	
	/**
	 * Update method run in every iteration of the main loop to update entity position, rotation and behaviour. 
	 * Also verifies which entities/bodies are dead and removes them. 
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
			    
			    // Initiate entities when they get within 30m of the ship
			    if (b.getPosition().x - ship.getPosition().x < 60 && !e.isInitialized()) {
			    	e.initialize();
			    }
			    
			    if (e != null) {
			    	// Check if the entity is dead and act accordingly
			    	if (e.isDead()) {				
						e.die();
						if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, e.toString() + " " + Integer.toHexString(e.hashCode()) + " died.");
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
			
			// Check if the ship is dead
			if (ship.isDead()) {
				shipDied();
			}
			
			// Check if the level is completed
			if (isLevelComplete()) {
				levelCompleted();
			}
			
			// Check if ship is within range of sun
			if (sunActivated) {
				sunActivate();
			}
			
			// Check if ship is within range of planet
			if (gravityActivated) {
				gravityActivate();
			}
			
			// Check if ship is within range of magnetic object 
			if (magnetActivated) {
				magnetActivate();
			}
			
			//  Display toast messages
			for (ToastMessage m: getLevel().messages) {
				// Not the best way to check but will do for this purpose
				if (m.progress <= ship.getBody().getPosition().x && m.progress + 0.3 >= ship.getBody().getPosition().x) {
					if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Toast: " + m.message + " at " + m.progress + " for " + m.duration + "s");
					renderer.getStage().addActor(Toast.create(m));
				}
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
		
		// Update level progress
		int newProgress = (int) (Math.round(ship.getPosition().x / level.getLevelEnd() * 100) / 1);		
		setProgress(newProgress);
	}
	
	/**
	 * Checks whether or not the level is complete. 
	 * @return true if the ship has completed the level; false otherwise
	 */
	public boolean isLevelComplete() {
		if (ship.getBody().getPosition().x >= level.getLevelEnd()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Called when the ship has died
	 */
	public void shipDied() {
		if (!isPlayEnded()) {
			new Timer().scheduleTask(new Task() {
				/*
				 * (non-Javadoc)
				 * @see com.badlogic.gdx.utils.Timer.Task#run()
				 */
				@Override
				public void run() {
					AudioPlayer.stopGameMusic();
					AudioPlayer.pauseGameSounds();
					if (getCreator() == null) {						
						game.screenshot = ScreenUtils.getFrameBufferTexture();
						game.setScreen(new LevelRetryMenu(game, game.gameScreen));
					}
					else {
						game.setScreen(creator);
					}
				}
			}, 4);
			setPlayEnded(true);
		}
	}
	
	/**
	 * Changes ship behaviour when the level is completed. 
	 * Ship slows down until its speed reaches zero and starts spinning. 
	 * After 2 seconds the next screen is shown. 
	 */
	public void levelCompleted() {	
		if (getLevel().nextLevelPath == null) {
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Level completed");
			
			if (ship.isSpeedOfLightEnabled()) {
				ship.disableSpeedOfLight();
			}
			
			// Decelerate ship
			if (ship.getBody().getLinearVelocity().x > 0) {
				ship.getBody().applyForceToCenter(-30, 0, false);
			}
			else if (ship.getBody().getLinearVelocity().x < 0) {
				ship.getBody().applyForceToCenter(30, 0, false);
			}
			if (ship.getBody().getLinearVelocity().y > 0) {
				ship.getBody().applyForceToCenter(0, -30, false);
			}
			else if (ship.getBody().getLinearVelocity().y < 0) {
				ship.getBody().applyForceToCenter(0, 30, false);
			}
			
			// Start rotating ship
			if (ship.getBody().getLinearVelocity().x < 1 && ship.getBody().getLinearVelocity().x > -1 && ship.getBody().getLinearVelocity().y < 1 && ship.getBody().getLinearVelocity().y > -1) {
				ship.getBody().applyAngularImpulse(1000, true);
				
				if (!isPlayEnded()) {
					new Timer().scheduleTask(new Task() {
						/*
						 * (non-Javadoc)
						 * @see com.badlogic.gdx.utils.Timer.Task#run()
						 */
						@Override
						public void run() {
							AudioPlayer.stopGameMusic();
							AudioPlayer.pauseGameSounds();
							
							if (getCreator() == null) {	
								// Update player progress if level is game-integrated level
								if (levelFile.type() == FileType.Internal) {
									SSJava.writeCompletedLevel(getLevel().getLevelCode());
								}
								
								// Auto-continue after tutorial level (0)
								if (getLevel().getLevelCode() == 0) {
									new Timer().scheduleTask(new Task() {
										/*
										 * (non-Javadoc)
										 * @see com.badlogic.gdx.utils.Timer.Task#run()
										 */
										@Override
										public void run() {
											game.gameScreen = new GameScreen(game, Gdx.files.internal("data/levels/level1-intro.json"));
											game.setScreen(game.gameScreen);
										}
									}, 3);
									
								}
								else {								
									game.screenshot = ScreenUtils.getFrameBufferTexture();
									game.setScreen(new LevelCompletedMenu(game, game.gameScreen));
								}
								
							}
							else {
								game.setScreen(creator);
							}
						}
					}, 2);
					AudioPlayer.levelComplete();
					setPlayEnded(true);
				}
			}
		}
		else {
			game.gameScreen = new GameScreen(game, Gdx.files.internal(getLevel().nextLevelPath));
			game.setScreen(game.gameScreen);
		}
	}
	
	/**
	 * Causes the ship to be attracted/repelled from the magnetic object. 
	 */
	public void magnetActivate() {
		if (!ship.isSpeedOfLightEnabled()) {
			double xLine = ship.getPosition().x - magnet.getPosition().x;
			double yLine = ship.getPosition().y - magnet.getPosition().y;
			double distance = Math.sqrt(Math.pow(xLine, 2) 
					+ Math.pow(yLine, 2));
			double force = Planet.GRAVITATIONNAL_CONSTANT * 1500 * 1 / Math.pow(distance, 2);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Force: " + force);
			float xForce = (float) (force / distance * xLine);
			float yForce = (float) (force / distance * yLine);
			ship.getBody().applyForceToCenter(xForce, yForce, true);
		}
	}
	
	/**
	 * Sets the magnetic object currently affecting the ship. 
	 * @param m the magnetic object to affect the ship
	 */
	public void setMagneticObject(MagneticObject m) {
		magnet = m;
	}
	
	/**
	 * Gets the magnetic object currently affecting the ship.
	 * @return the magnetic object affecting the ship
	 */
	public MagneticObject getMagneticObject() {
		return magnet;
	}
	
	/**
	 * Causes the ship to be attracted towards the planet. 
	 */
	public void gravityActivate() {
		if (!ship.isSpeedOfLightEnabled()) {
			double xLine = ship.getPosition().x - planet.getPosition().x;
			double yLine = ship.getPosition().y - planet.getPosition().y;
			double distance = Math.sqrt(Math.pow(xLine, 2) 
					+ Math.pow(yLine, 2));
			double force = Planet.GRAVITATIONNAL_CONSTANT * 3000 * 1 / Math.pow(distance, 2);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Force: " + force);
			float xForce = (float) (force / distance * xLine);
			float yForce = (float) (force / distance * yLine);
			ship.getBody().applyForceToCenter(-xForce, -yForce, true);
		}
	}
	
	/**
	 * Sets the planet currently affecting the ship.
	 * @param p the planet to affect the ship
	 */
	public void setPlanet(Planet p) {
		planet = p;
	}
	
	/**
	 * Gets the planet currently affecting the ship.
	 * @return the currently activated planet
	 */
	public Planet getPlanet() {
		return planet;
	}
	
	/**
	 * Causes the ship to be burned by the sun. 
	 */
	public void sunActivate() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Speed of Light: " + ship.isSpeedOfLightEnabled());
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Heat: " + shipHeatIndicator);
		if (!ship.isSpeedOfLightEnabled()) {
			shipHeatIndicator += (double)(Gdx.graphics.getDeltaTime());
			if (shipHeatIndicator >= 3) {
				time += (double)(Gdx.graphics.getDeltaTime());
				if (time >= 1) {
					ship.healthChange(-2);
					time--;
				}
			}
			else if (shipHeatIndicator >= 2) {
				renderer.shipHeatIndicatorLabel.setText("Heat: DANGER!");
			}
		}
	}
	
	/**
	 * Sets the Sun currently affecting the ship.
	 * @param s the sun to affect the ship
	 */
	public void setSun(Sun s) {
		sun = s;
	}
	
	/**
	 * Gets the Sun currently affecting the ship.
	 * @return the currently activated Sun
	 */
	public Sun getSun() {
		return sun;
	}
	
	/**
	 * Calls the pause screen and stops rendering the game. 
	 */
	public void pauseGame() {
		game.screenshot = ScreenUtils.getFrameBufferTexture();
		game.setScreen(new PauseMenu(game, game.gameScreen));
		AudioPlayer.pauseGameMusic();
		AudioPlayer.pauseGameSounds();
	}
	
	/**
	 * Exports the current level to a file in JSON format. 
	 * @param levelFile the FileHandle path at which to save the exported level
	 */
	public void exportLevel(FileHandle levelFile) {
		levelFile.writeString(new Json().prettyPrint(level), false);
	}
	
	/**
	 * Loads a level from a JSON file into the level instance. 
	 * @param levelFile the FileHandle of the JSON level file to be loaded
	 */
	private void loadLevel(FileHandle levelFile) {		
		level = new Json().fromJson(Level.class, levelFile);
	}
	
	/**
	 * Gets the ship instance
	 * @return the ship instance
	 */
	public Ship getShip() {
		return ship;
	}
	/**
	 * Gets the obstacles array
	 * @return the obstacles array
	 */
	public Array<Obstacle> getObstacles() {
		return level.obstacles;
	}
	/**
	 * Gets the enemies array
	 * @return the enemies array
	 */
	public Array<Enemy> getEnemies() {
		return level.enemies;
	}
	/**
	 * Gets the bullets array
	 * @return the bullets array
	 */
	public Array<Bullet> getBullets() {
		return bullets;
	}
	/**
	 * Gets the powerups array
	 * @return the powerups array
	 */
	public Array<Powerup> getPowerups() {
		return level.powerups;
	}
	/**
	 * Gets the game changers array
	 * @return the gameChangers array
	 */
	public Array<Obstacle> getGameChangers() {
		return level.gameChangers;
	}
	
	/**
	 * Sets the associated renderer.  
	 * @param renderer the world renderer to be set
	 */
	public void setRenderer(WorldRenderer renderer) {
		this.renderer = renderer;
	}
	
	/**
	 * Gets the associated renderer. 
	 * @return renderer the associated WorldRenderer instance
	 */
	public WorldRenderer getRenderer(){
		return renderer;
	}
	
	/**
	 * Gets the associated input manager.
	 * @return the associated GameInputManager instance
	 */
	public InputProcessor getManager() {
		return manager;
	}
	
	/**
	 * Sets the InputManager instance
	 * @param manager the manager to set
	 */
	public void setManager(InputProcessor manager) {
		this.manager = manager;		
	}
	
	/**
	 * Gets the world type. 
	 * @return the world's type (game or creator)
	 */
	public int getWorldType() {
		return worldType;
	}
	
	/**
	 * Gets the level object instance. 
	 * @return level the game world's level instance
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Gets the score keeper instance. 
	 * @return the ScoreKeeper instance
	 */
	public ScoreKeeper getScoreKeeper() {
		return scoreKeeper;
	}
	
	/**
	 * Gets the associated creator instance. 
	 * @return the LevelCreator instance
	 */
	public LevelCreatorScreen getCreator() {
		return creator;
	}
	
	/**
	 * Gets the level file FileHandle. 
	 * @return the level file FileHandle
	 */
	public FileHandle getLevelFile() {
		return levelFile;
	}
	/**
	 * Sets the level file. 
	 * @param levelFile the levelFile to set
	 */
	public void setLevelFile(FileHandle levelFile) {
		this.levelFile = levelFile;
	}
	/**
	 * Gets the ship's progress in the level. 
	 * @return the ship's progress
	 */
	public int getProgress() {
		return progress;
	}
	/**
	 * Sets the ship's progress in the level. 
	 * @param progress the progress to set
	 */
	private void setProgress(int progress) {
		if (progress <= 100) {			
			this.progress = progress;
		}
		else {
			this.progress = 100;
		}
	}
	
	/**
	 * Gets the play ended flag. 
	 * @return true if the play is ended; false otherwise
	 */
	public boolean isPlayEnded() {
		return playEnded;
	}
	/**
	 * Sets the play ended flag. 
	 * @param playEnded true if the play has ended
	 */
	private void setPlayEnded(boolean playEnded) {
		this.playEnded = playEnded;
	}
	
	/**
	 * Dispose method for the World. 
	 */
	public void dispose() {
		box2DWorld.dispose();
	}
}
