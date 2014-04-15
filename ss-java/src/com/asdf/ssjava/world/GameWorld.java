/**
 * Base world class that contains all entities present in the world at the current time.
 * Loads the selected level when game screen is set.
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Planet;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.entities.SpaceRock;
import com.asdf.ssjava.screens.PauseMenu;
import com.badlogic.gdx.Gdx;
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
	WorldRenderer render;
	
	/**
	 * The InputManager instance
	 */
	InputProcessor manager;
	
	/**
	 * The world view type
	 * 0 = game
	 * 1 = creator 
	 */
	int worldType;
	
	
	/**
	 * The ScoreKeeper instance
	 */
	ScoreKeeper scoreKeeper;
	
	/**
	 * ArrayList containing all the obstacles in the current level
	 */
	Level level;
	
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
	 * Creates a world for an instance of SSJava
	 * @param game the instance of the game
	 */
	public GameWorld(SSJava game, int worldType, String levelPath) {
		this.game = game;
		this.worldType = worldType;
		
		// TODO Box2D stuff
		box2DWorld = new World(new Vector2(0, 0), true);
		
		ship = new Ship(new Vector2(5, Gdx.graphics.getHeight() / 40), 6, 3, 0, this, box2DWorld);
		ship.getBody().setLinearVelocity(ship.DEFAULT_VELOCITY.x, 0);
		
		bullets = new Array<Bullet>();
		
		// Level Loading
		/*if (levelPath != null) {
			loadLevel(levelPath);
		}*/
		
		
		level = new Level();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				if (j % 2 == 0) {					
					Obstacle o = new SpaceRock(new Vector2(50 * i + 25, 2.5f + j * 5), SpaceRock.DEFAULT_WIDTH, SpaceRock.DEFAULT_HEIGHT, SpaceRock.DEFAULT_ROTATION, this, box2DWorld);
					o.getBody().setLinearVelocity(SpaceRock.DEFAULT_VELOCITY);
					level.obstacles.add(o);
				}
				else {
					Obstacle o = new Asteroid(new Vector2(50 * i + 25, 2.5f + j * 5), Asteroid.DEFAULT_WIDTH, Asteroid.DEFAULT_HEIGHT, Asteroid.DEFAULT_ROTATION, this, box2DWorld);
					o.getBody().setLinearVelocity(Asteroid.DEFAULT_VELOCITY);
					level.obstacles.add(o);
				}
			}
		}
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 5; j++){
				Powerup p = new PowerupSpeedOfLight(new Vector2(200 * i, 4 * j), PowerupSpeedOfLight.DEFAULT_WIDTH, PowerupSpeedOfLight.DEFAULT_HEIGHT, PowerupSpeedOfLight.DEFAULT_ROTATION, this, box2DWorld);
				p.getBody().setLinearVelocity(PowerupSpeedOfLight.DEFAULT_VELOCITY);
				level.powerups.add(p);
				p = new PowerupHealthUp(new Vector2(50 * i - 10, 4 * j + 30), PowerupHealthUp.DEFAULT_WIDTH, PowerupHealthUp.DEFAULT_HEIGHT, PowerupHealthUp.DEFAULT_ROTATION, this, box2DWorld);
				p.getBody().setLinearVelocity(PowerupHealthUp.DEFAULT_VELOCITY);
				level.powerups.add(p);
			}
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				Enemy e = new EnemyType1(new Vector2(50 * (i + 1), 5 * (j + 1)), EnemyType1.DEFAULT_WIDTH, EnemyType1.DEFAULT_HEIGHT, EnemyType1.DEFAULT_ROTATION, this, box2DWorld);
				e.getBody().setLinearVelocity(EnemyType1.DEFAULT_VELOCITY);
				level.enemies.add(e);
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 1; j++) {
				Planet p = new Planet(new Vector2(250 * i + 100, 40 + j * 50), Planet.DEFAULT_WIDTH, Planet.DEFAULT_HEIGHT, Planet.DEFAULT_ROTATION, this, box2DWorld);
				p.getBody().setLinearVelocity(Planet.DEFAULT_VELOCITY);
				level.gameChangers.add(p);
			}
		}
		
//		exportLevel(levelPath);		
		
		// Score keeper
		scoreKeeper = new ScoreKeeper();
		
	}
	
	/**
	 * Update method run in every iteration of the main loop to update entity position and rotation
	 * Also verifies which entities/bodies are dead and removes them
	 */
	public void update() {
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
		    		e.setPosition(new Vector2(b.getPosition().x, b.getPosition().y));
		    		e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
		    		
		    		e.update();
		    	}
		    }
		}
		
		// Remove all dead bodies from Box2D and game
		for (Body b: deadBodies) {
			box2DWorld.destroyBody(b);
		}
		
		// TODO Collision detection		
		/*
		for (Bullet b: bullets) {

			// Bullet collision with enemies
			for (Enemy e: level.enemies) { 
				if (e.getHitbox().overlaps(b.getHitbox())) {
					AudioPlayer.bulletImpact();
					bullets.removeValue(b, true);
					
					// enemy damage
					e.healthChange((-1) * b.getDamage());
					// life check
					int killScore = 0;
					int hitScore = 0;
					if (e instanceof EnemyType1) {
						killScore = EnemyType1.KILL_SCORE;
						hitScore = EnemyType1.HIT_SCORE;
					}
					
					if (checkIfDead(e)) {
						level.enemies.removeValue(e, true);
						scoreKeeper.add(killScore);
					}
					else {
						scoreKeeper.add(hitScore);
					}
					
					Gdx.app.log(SSJava.LOG, "Ship's bullet " + Integer.toHexString(b.hashCode()) + " hit enemy " + Integer.toHexString(e.hashCode()));
				}
			}
			
			// Bullet collision with obstacles
			for (Obstacle o: level.obstacles) { 
				if (o.getHitbox().overlaps(b.getHitbox())) {
					AudioPlayer.bulletImpact();
					bullets.removeValue(b, true);
					
					// obstacle damage
					o.healthChange((-1) * b.getDamage());
					// life check
					int killScore = 0;
					int hitScore = 0;
					if (o instanceof Asteroid) {
						killScore = Asteroid.KILL_SCORE;
						hitScore = Asteroid.HIT_SCORE;
					}
					else if (o instanceof SpaceRock) {
						killScore = SpaceRock.KILL_SCORE;
						hitScore = SpaceRock.HIT_SCORE;
					}
					
					if (checkIfDead(o)) {
						level.obstacles.removeValue(o, true);
						scoreKeeper.add(killScore);
					}
					else {
						scoreKeeper.add(hitScore);
					}

					Gdx.app.log(SSJava.LOG, "Ship's bullet " + Integer.toHexString(b.hashCode()) + " hit obstacle " + Integer.toHexString(o.hashCode()));
				}
			}
			
			//Bullet collision with gameChangers
			for (Obstacle g: level.gameChangers) { 
				if (g.getHitbox().overlaps(b.getHitbox())) {
					AudioPlayer.bulletImpact();
					bullets.removeValue(b, true);
					
					// game changer damage
					g.healthChange((-1) * b.getDamage());
					// life check
					int killScore = 0;
					int hitScore = 0;
					if (g instanceof Planet) {
						killScore = Planet.KILL_SCORE;
						hitScore = Planet.HIT_SCORE;
					}
					
					if (checkIfDead(g)) {
						level.gameChangers.removeValue(g, true);
						scoreKeeper.add(killScore);
					}
					else {
						scoreKeeper.add(hitScore);
					}
					
					Gdx.app.log(SSJava.LOG, "Planet's health: " + g.getHealth());
				}
			}
			
			// Bullet collision with ship
			if (ship.getHitbox().overlaps(b.getHitbox())) {
				bullets.removeValue(b, true);
				if(!ship.lightSpeedMode){
					AudioPlayer.bulletImpact();
					
					// ship damage
					ship.healthChange((-1) * b.getDamage());
					// life check
					checkIfDead(ship);
				}
				Gdx.app.log(SSJava.LOG, "Enemy " + Integer.toHexString(b.getShooter().hashCode()) + "'s bullet " + Integer.toHexString(b.hashCode()) + " hit ship");
			}
			
		}
		// Enemy collision with ship
		for (Enemy e: level.enemies) {
			if (e.getHitbox().overlaps(ship.getHitbox()) && !e.alreadyCollided){
				if (ship.lightSpeedMode){
					//enemy damage
					e.healthChange(-999);
					// life check
					if (checkIfDead(e)) {
						level.enemies.removeValue(e, true);
					}
				}
				else {
					AudioPlayer.shipImpact();
					Gdx.app.log(SSJava.LOG, "Remaining health: " + ship.getHealth());
					e.alreadyCollided = true;
					// ship and enemy damage
					e.healthChange(-1);
					ship.healthChange(-1);
					// life check
					if (checkIfDead(e)) {
						level.enemies.removeValue(e, true);
					}
					
					checkIfDead(ship); // TODO
					
					//Change ship speed
					ship.getVelocity().x = ship.SLOW_VELOCITY.x;
				}
			}	
		}
		
		// Obstacle collision with ship		
		for (Obstacle o: level.obstacles) {
			if (o.getHitbox().overlaps(ship.getHitbox()) && !o.alreadyCollided) {
				if (ship.lightSpeedMode){
					//obstacle damage
					o.healthChange(-999);
					// life check
					if (checkIfDead(o)) {
						level.obstacles.removeValue(o, true);
					}
				}
				else {
					AudioPlayer.shipImpact();
					Gdx.app.log(SSJava.LOG, "Remaining health: " + ship.getHealth());
					o.alreadyCollided = true;
					// ship and enemy damage
					o.healthChange(-1);
					ship.healthChange(-1);
					// life check
					if (checkIfDead(o)) {
						level.obstacles.removeValue(o, true);
					}
					
					checkIfDead(ship); // TODO
					
					//Change ship speed
					ship.getVelocity().x = ship.SLOW_VELOCITY.x;
				}
			}
		}
		
		//Power-up collision with ship
		for (Powerup p: level.powerups){
			if (p.getHitbox().overlaps(ship.getHitbox())) {
				//Collision with the Speed of Light power-up
				if (p.toString().equals("Speed of Light Powerup") && !p.alreadyCollided) {
					p.alreadyCollided = true;
					ship.getVelocity().x = ship.DEFAULT_VELOCITY.x * 4;
					ship.lightSpeedMode = true;
					//Reset the ship to default after 5 seconds
					new Timer().scheduleTask(new Task() {
						@Override
						public void run() {
							ship.getVelocity().x = ship.DEFAULT_VELOCITY.x;
							ship.lightSpeedMode = false;
						}
					}, PowerupSpeedOfLight.COOLDOWN_SECONDS);
					Gdx.app.log(SSJava.LOG, "Ship sped up!" + Integer.toHexString(p.hashCode()));
				}
				//Collision with the Health Up power-up
				else if (p.toString().equals("Health Up Powerup") && !p.alreadyCollided) {
					p.alreadyCollided = true;
					ship.healthChange(PowerupHealthUp.HEALTH_GIVEN);
					if (ship.getHealth() > Ship.DEFAULT_HEALTH) {
						ship.setHealth(Ship.DEFAULT_HEALTH);
					}
					Gdx.app.log(SSJava.LOG, "Ship healed, heath: " + ship.getHealth());	
				}
				level.powerups.removeValue(p, true);
			}
		}
		
		//Game-changer collision with ship
		for (Obstacle g: level.gameChangers) {
			if (g.getHitbox().overlaps(ship.getHitbox()) && !g.alreadyCollided) {
				if (ship.lightSpeedMode){

				}
				else {
					AudioPlayer.shipImpact();
					Gdx.app.log(SSJava.LOG, "Remaining health: " + ship.getHealth());
					g.alreadyCollided = true;
					// ship and enemy damage
					g.healthChange(-1);
					ship.healthChange(-1);
					// life check
					if (checkIfDead(g)) {
						level.obstacles.removeValue(g, true);
					}
					
					checkIfDead(ship); // TODO
					
				}
				//Change ship speed
				ship.getVelocity().x = ship.SLOW_VELOCITY.x;
			}
		}
		*/
		
		// Edge of screen collision	
		float screenTop = render.cam.position.y + render.cam.viewportHeight / 2;
		float screenBottom = render.cam.position.y - render.cam.viewportHeight / 2;
		float screenLeft = render.cam.position.x - render.cam.viewportWidth / 2;
		float screenRight = render.cam.position.x + render.cam.viewportWidth / 2;
				
		if (ship.getPosition().y + ship.getHeight() / 2 >= (screenTop) || ship.getPosition().y - ship.getHeight() / 2 <= screenBottom) {
			Gdx.app.log(SSJava.LOG, "Ship hit top or bottom of screen");
			// TODO FIX moveable up increments
			/*if (ship.getAcceleration().y < 0 && ship.getPosition().y <= screenBottom) {
				ship.getPosition().y = screenBottom;
			}
			else if (ship.getAcceleration().y > 0 && ship.getPosition().y + ship.getHeight() >= screenTop) {
				ship.getPosition().y = screenTop - ship.getHeight();
			}*/
			ship.getBody().setLinearVelocity(ship.getBody().getLinearVelocity().x, 0);
		}
		
		if (ship.getPosition().x + ship.getWidth() / 2 >= screenRight || ship.getPosition().x - ship.getWidth() / 2 <= screenLeft) {
			Gdx.app.log(SSJava.LOG, "Ship hit right or left of screen");
			ship.getBody().setLinearVelocity(0, ship.getBody().getLinearVelocity().y);
		}
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
	 */
	public void exportLevel(String path) {
		Gdx.files.local(path).writeString(new Json().prettyPrint(level), false);
	}
	
	/**
	 * Loads a level from a JSON file into the level
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
	 * 
	 * @return the obstacles array
	 */
	public Array<Obstacle> getObstacles() {
		return level.obstacles;
	}
	
	/**
	 * 
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
	 * 
	 * @return the powerups array
	 */
	public Array<Powerup> getPowerups() {
		return level.powerups;
	}
	
	/**
	 * Passes the WorldRenderer into this class
	 */
	public void setRenderer(WorldRenderer render){
		this.render = render;
	}
	
	/**
	 * 
	 * @return the input processor
	 */
	public InputProcessor getManager() {
		return manager;
	}
	
	/**
	 * 
	 * @return the world's type (game or creator)
	 */
	public int getWorldType() {
		return worldType;
	}
	
	/**
	 * 
	 * @return level the game world's level instance
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * 
	 * @return
	 */
	public ScoreKeeper getScoreKeeper() {
		return scoreKeeper;
	}
	
	/**
	 * Dispose method
	 */
	public void dispose() {
		
	}

	public void setManager(InputProcessor manager) {
		this.manager = manager;		
	}
}
