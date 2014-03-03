/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */

public class Ship extends MoveableEntity {
	
	/**
	 * The ship's default velocity
	 * The ship will slowly return to the x velocity after it has hit another entity
	 * The y velocity also limits the ship's vertical motion, which is controlled by the player
	 */
	public final Vector2 DEFAULT_VELOCITY = new Vector2(5, 5); 
	
	public final Vector2 DEFAULT_ACCELERATION = new Vector2(7, 10);
	
	/**
	 * Creates a ship with a position, dimensions and rotation.
	 * Does not give the ship an initial speed.
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Ship(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
	}
	
	/**
	 * Fire a bullet from the ship
	 * Bullet leaves in the ... direction
	 */
	public void fire() {
		// TODO add bullet
//		world.getBullets().add(new Bullet());
		Gdx.app.log("Ship", "Ship fired!");
	}

	@Override
	public void update() { 
		if (Math.abs(velocity.y) >= DEFAULT_VELOCITY.y) {
			if (velocity.y > 0) {
				if (acceleration.y > 0) {
					acceleration.y = 0;
				}
			}
			else {
				if (acceleration.y < 0) {
					acceleration.y = 0;
				}
			}
		}
		
		velocity.x += acceleration.x * Gdx.graphics.getDeltaTime();
		velocity.y += acceleration.y * Gdx.graphics.getDeltaTime();
		
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime())); 
		rotation = velocity.angle() - 90;
		
		super.update();
	}
	
}
