/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
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
	 * @param game
	 */
	public World(SSJava game) {
		this.game = game;
		
		obstacles = new Array<Obstacle>();
		enemies = new Array<Enemy>();
		bullets = new Array<Bullet>();
		powerups = new Array<Powerup>();
		
		ship = new Ship(new Vector2(5, Gdx.graphics.getHeight() / 40), 6, 3, 0, this);
		ship.getVelocity().x = 5; // default horizontal ship speed
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				enemies.add(new EnemyType1(new Vector2(50 * (i + 1), 5 * (j + 1)), 1, 1, 0));
//				enemies.get(i).setVelocity(new Vector2(1, 0));
			}
		}
		
		// Set game input processor
		Gdx.input.setInputProcessor(new InputManager(game, this));
	}

	/**
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}
	
	/**
	 * Update method run in every iteration of the main loop to update entity position and behaviour
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
			if (ship.getHitbox().overlaps(e.getHitbox())) {
				Gdx.app.log("Collision", "Ship hit");
			}
		}		
	}
	
	
	// TODO bullets cleanup
	
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
