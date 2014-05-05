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
import com.asdf.ssjava.entities.MagneticObject;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Planet;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.entities.Sun;
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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

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
	LevelCreatorScreen creator;
	
	/**
	 * World type constant definition
	 */
	public static final int GAME_TYPE = 0;
	
	/**
	 * World type constant definition
	 */
	public static final int CREATOR_TYPE = 1;
	
	/**
	 * Reference to this screen
	 */
//	Screen thisScreen = this;
	
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
	
	/**
	 * The file containing the current level
	 */
	FileHandle levelFile;
	
	/**
	 * The ship's heat indicator
	 */
	private double shipHeatIndicator;
	
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
	 * Constructor for testing a level from the level creator
	 * @param game
	 * @param worldType
	 * @param levelPath
	 * @param creator
	 */
	public GameWorld(SSJava game, int worldType, FileHandle levelFile, LevelCreatorScreen creator) {
		this(game, worldType, levelFile);
		this.creator = creator;
	}
	/**
	 * Creates a world for an instance of SSJava
	 * @param game the instance of the game
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
			
			//  Check toasts
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
	 * Sets the level background
	 * Currently not fully implemented
	 */
	public void setBackground(String path) {
		getLevel().setBackgroundPath(Gdx.files.absolute(path).path());
		if (path != null) {
			renderer.bgTexture = new Texture(Gdx.files.absolute(path));
			renderer.bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
			renderer.bgSprite = new Sprite(renderer.bgTexture);
		}
	}
	
	/**
	 * Checks whether or not the ship has died
	 * @return true if the ship has completed the level
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
	 * Ship behaviour when level completed
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
					// TODO try a Thread
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
	 * Causes the ship to be attracted/repelled from the magnetic object
	 */
	public void magnetActivate() {
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
	
	public void setMagneticObject(MagneticObject m) {
		magnet = m;
	}
	
	public MagneticObject getMagneticObject() {
		return magnet;
	}
	
	/**
	 * Causes the ship to be attracted to the planet
	 */
	public void gravityActivate() {
		double xLine = ship.getPosition().x - planet.getPosition().x;
		double yLine = ship.getPosition().y - planet.getPosition().y;
		double distance = Math.sqrt(Math.pow(xLine, 2) 
				+ Math.pow(yLine, 2));
		double force = Planet.GRAVITATIONNAL_CONSTANT * 5000 * 1 / Math.pow(distance, 2);
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Force: " + force);
		float xForce = (float) (force / distance * xLine);
		float yForce = (float) (force / distance * yLine);
		ship.getBody().applyForceToCenter(-xForce, -yForce, true);
	}
	
	/**
	 * 
	 * @param p
	 */
	public void setPlanet(Planet p) {
		planet = p;
	}
	
	/**
	 * 
	 * @return
	 */
	public Planet getPlanet() {
		return planet;
	}
	
	/**
	 * Causes the ship to be burned by the sun
	 */
	public void sunActivate() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Heat: " + shipHeatIndicator);
		shipHeatIndicator += (double)(Gdx.graphics.getDeltaTime());
		if (shipHeatIndicator >= 3.5) {
			ship.setHealth(0);
		}
		else if (shipHeatIndicator >= 2) {
			renderer.shipHeatIndicatorLabel.setText("Heat: DANGER!");
		}
	}
	
	public void setSun(Sun s) {
		sun = s;
	}
	
	public Sun getSun() {
		return sun;
	}
	
	/**
	 * Calls the pause screen and stops rendering the game
	 */
	public void pauseGame() {
		game.screenshot = ScreenUtils.getFrameBufferTexture();
		game.setScreen(new PauseMenu(game, game.gameScreen));
		AudioPlayer.pauseGameMusic();
		AudioPlayer.pauseGameSounds();
	}
	
	/**
	 * Exports the current level to a file in JSON format
	 * @param path the path at which to save the exported level
	 */
	public void exportLevel(FileHandle levelFile) {
		levelFile.writeString(new Json().prettyPrint(level), false);
	}
	
	/**
	 * Loads a level from a JSON file into the level instance
	 * @param path the path of the JSON level file to be loaded
	 */
	private void loadLevel(FileHandle levelFile) {		
		level = new Json().fromJson(Level.class, levelFile);
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
	public WorldRenderer getRenderer(){
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
	public LevelCreatorScreen getCreator() {
		return creator;
	}
	
	/**
	 * @return the levelFile
	 */
	public FileHandle getLevelFile() {
		return levelFile;
	}
	/**
	 * @param levelFile the levelFile to set
	 */
	public void setLevelFile(FileHandle levelFile) {
		this.levelFile = levelFile;
	}
	/**
	 * @return the progress
	 */
	public int getProgress() {
		return progress;
	}
	/**
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
	 * @return true if the play is ended
	 */
	public boolean isPlayEnded() {
		return playEnded;
	}
	/**
	 * @param playEnded the playEnded to set
	 */
	private void setPlayEnded(boolean playEnded) {
		this.playEnded = playEnded;
	}
	
	/**
	 * Dispose method
	 */
	public void dispose() {
		box2DWorld.dispose();
	}
}
