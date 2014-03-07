/**
 * Base world class that contains all entities present in the world at the current time.
 * Loads the selected level when game screen is set.
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.InputManager;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.entities.SpaceRock;
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
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				enemies.add(new EnemyType1(new Vector2(50 * (i + 1), 5 * (j + 1)), 1, 1, 0, this));
			}
		}
		
		// Set game input processor
		Gdx.input.setInputProcessor(new InputManager(game, this));
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
		
		// Collision detection
		for (Enemy e: enemies) { 
			if (e.getHitbox().overlaps(ship.getHitbox())) {
				Gdx.app.log(SSJava.LOG, "Ship collided with enemy " + Integer.toHexString(e.hashCode()));
			}
			
			for (Bullet b: bullets) {
				if (e.getHitbox().overlaps(b.getHitbox())) {
					Gdx.app.log(SSJava.LOG, "Ship's bullet hit enemy " + Integer.toHexString(e.hashCode()));
				}
			}
		}
		
		// TODO bullets cleanup if they go offscreen
		
		for (Bullet b: bullets) {
			if (b.getPosition().x > ship.getPosition().x + 50 || b.getPosition().x < ship.getPosition().x - 30) {
				bullets.removeValue(b, true);
			}
		}
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
	 *
	 */
	public void dispose() {
		
	}
}
