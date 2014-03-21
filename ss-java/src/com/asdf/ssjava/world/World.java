/**
 * Base world class that contains all entities present in the world at the current time.
 * Loads the selected level when game screen is set.
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.InputManager;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.entities.SpaceRock;
import com.asdf.ssjava.screens.PauseMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author Jeremy Brown
 *
 */

public class World {

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
	InputManager manager;
	
	/**
	 * ArrayList containing all the obstacles in the current level
	 */
	Array<Obstacle> obstacles;
	
	/**
	 * ArrayList containing all the enemies in the current level
	 */
	Array<Enemy> enemies;
	
	/**
	 * Array containing all the bullets present in the level
	 */
	Array<Bullet> bullets;
	
	/**
	 * ArrayList containing all the powerups in the current level
	 */
	Array<Powerup> powerups;
	
	/**
	 * Creates a world for an instance of SSJava
	 * @param game the instance of the game
	 */
	public World(SSJava game) {
		this.game = game;
		
		obstacles = new Array<Obstacle>();
		enemies = new Array<Enemy>();
		bullets = new Array<Bullet>();
		powerups = new Array<Powerup>();
		
		ship = new Ship(new Vector2(5, Gdx.graphics.getHeight() / 40), 6, 3, 0, this);
		ship.getVelocity().x = ship.DEFAULT_VELOCITY.x; // default horizontal ship speed
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				if (j % 2 == 0) {					
					obstacles.add(new SpaceRock(new Vector2(50 * i + 25, 2.5f + j * 5), 2, 2, 0));
				}
				else {
					Obstacle o = new Asteroid(new Vector2(50 * i + 25, 2.5f + j * 5), 2, 2, 0);
					o.getVelocity().x = o.getDEFAULT_VELOCITY().x;
					obstacles.add(o);
				}
			}
		}
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 5; j++){
				powerups.add(new PowerupSpeedOfLight(new Vector2(50 * i, 4 * j), 1, 1, 0));
				powerups.add(new PowerupHealthUp(new Vector2(50 * i - 10, 4 * j + 30), 1, 1, 0));
			}
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				enemies.add(new EnemyType1(new Vector2(50 * (i + 1), 5 * (j + 1)), 1, 1, 0, this));
			}
		}
		
		// Set game input processor
		manager = new InputManager(game, this);
		Gdx.input.setInputProcessor(manager);
	}
	
	/**
	 * Update method run in every iteration of the main loop to update entity position, behaviour and collision
	 */
	public void update() {
		// Entity updates
		ship.update();
		
		for (Obstacle o: obstacles) {
			o.update();
		}
		for (Enemy e: enemies) {
			e.update();
		}
		for (Bullet b: bullets) {
			b.update();
		}
		for (Powerup p: powerups) {
			p.update();
		}
		
		// TODO Collision detection		
		
		for (Bullet b: bullets) {
			// TODO Bullets cleanup if they go offscreen
			if (b.getPosition().x > ship.getPosition().x + 50 || b.getPosition().x < ship.getPosition().x - 30) {
				bullets.removeValue(b, true);
			}
			// Bullet collision with enemies
			for (Enemy e: enemies) { 
				if (e.getHitbox().overlaps(b.getHitbox())) {
					AudioPlayer.bulletImpact();
					bullets.removeValue(b, true);
					
					// enemy damage
					e.healthChange((-1) * b.getDamage());
					// life check
					if (checkIfDead(e)) {
						enemies.removeValue(e, true);
					}
					
					Gdx.app.log(SSJava.LOG, "Ship's bullet " + Integer.toHexString(b.hashCode()) + " hit enemy " + Integer.toHexString(e.hashCode()));
				}
			}
			
			// Bullet collision with obstacles
			for (Obstacle o: obstacles) { 
				if (o.getHitbox().overlaps(b.getHitbox())) {
					AudioPlayer.bulletImpact();
					bullets.removeValue(b, true);
					
					// obstacle damage
					o.healthChange((-1) * b.getDamage());
					// life check
					if (checkIfDead(o)) {
						obstacles.removeValue(o, true);
					}

					Gdx.app.log(SSJava.LOG, "Ship's bullet " + Integer.toHexString(b.hashCode()) + " hit obstacle " + Integer.toHexString(o.hashCode()));
				}
			}
			
			// Bullet collision with ship
			if (ship.getHitbox().overlaps(b.getHitbox())) {
				AudioPlayer.bulletImpact();
				bullets.removeValue(b, true);
				
				// ship damage
				ship.healthChange((-1) * b.getDamage());
				// life check
				if (checkIfDead(ship)) {
					
				}

				Gdx.app.log(SSJava.LOG, "Enemy " + Integer.toHexString(b.getShooter().hashCode()) + "'s bullet " + Integer.toHexString(b.hashCode()) + " hit ship");
			}
			
		}
		// Enemy collision with ship
		for (Enemy e: enemies) {
			if (e.getHitbox().overlaps(ship.getHitbox())) {
				AudioPlayer.shipImpact();
				Gdx.app.log(SSJava.LOG, "Ship collided with enemy " + Integer.toHexString(e.hashCode()));
				
				// ship and enemy damage
				e.healthChange(-1);
				ship.healthChange(-1);
				// life check
				if (checkIfDead(e)) {
					enemies.removeValue(e, true);
				}
				checkIfDead(ship);

			}
		}
		
		// Obstacle collision with ship		
		for (Obstacle o: obstacles) {
			if (o.getHitbox().overlaps(ship.getHitbox())) {
				AudioPlayer.shipImpact();
				Gdx.app.log(SSJava.LOG, "Ship collided with obstacle " + Integer.toHexString(o.hashCode()));
				
				// obstacle damage?
			}
		}
		//Power-up collision with ship
		for (Powerup p: powerups){
			if (p.getHitbox().overlaps(ship.getHitbox())) {
				if (p.toString().equals("Speed of Light Powerup")) {
					ship.getVelocity().x = ship.DEFAULT_VELOCITY.x * 2;
					Gdx.app.log(SSJava.LOG, "Ship sped up!" + Integer.toHexString(p.hashCode()));
				}
				else if (p.toString().equals("Health Up Powerup")) {
					ship.healthChange(2);
					Gdx.app.log(SSJava.LOG, "Ship healed up!" + Integer.toHexString(p.hashCode()));	
				}
			}
		}
		
		//Edge of screen collision	
		float screenTop = render.cam.position.y + render.cam.viewportHeight / 2;
		float screenBottom = render.cam.position.y - render.cam.viewportHeight / 2;
		float screenLeft = render.cam.position.x - render.cam.viewportWidth / 2;
		float screenRight = render.cam.position.x + render.cam.viewportWidth / 2;
		
		if (ship.getPosition().y + ship.getHeight() >= (screenTop) || ship.getPosition().y <= screenBottom) {
			ship.getVelocity().y = 0;
			if (ship.getAcceleration().y < 0 && ship.getPosition().y <= screenBottom) {
				ship.getPosition().y = screenBottom;
			}
			else if (ship.getAcceleration().y > 0 && ship.getPosition().y + ship.getHeight() >= screenTop) {
				ship.getPosition().y = screenTop - ship.getHeight();
			}
		}
		
		if (ship.getPosition().x + ship.getWidth() >= screenRight || ship.getPosition().x <= screenLeft) {
			ship.getVelocity().x = 0;
			if (ship.getAcceleration().x < 0 && ship.getPosition().x <= screenLeft) {
				ship.getPosition().x = screenLeft;
			}
			else if (ship.getAcceleration().x > 0 && ship.getPosition().x + ship.getWidth() >= screenRight) {
				ship.getPosition().x = screenRight - ship.getWidth();
			}
		}
	}
	
	/**
	 * Verifies if the passed entity is dead (life <= 0) and should be removed from the screen
	 * @param e the entity to verify
	 * @return true if the verified entity is dead
	 */
	public boolean checkIfDead(AbstractEntity e) {
		if (e.getHealth() <=0) {
			e.die();
			return true;
		}
		return false;
		
	}
	
	/**
	 * Calls the pause screen and stops rendering the game
	 */
	public void pauseGame() {
		game.setScreen(new PauseMenu(game));
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
		return obstacles;
	}
	
	/**
	 * 
	 * @return the enemies array
	 */
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	/**
	 * Returns the bullets array
	 */
	public Array<Bullet> getBullets() {
		return bullets;
	}
	/**
	 * Passes the WorldRenderer into this class
	 */
	public void setRenderer(WorldRenderer render){
		this.render = render;
	}
	
	public InputManager getManager() {
		return manager;
	}
	
	/**
	 *
	 */
	public void dispose() {
		
	}
}
