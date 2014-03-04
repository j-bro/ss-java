/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.World;
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
	
	/**
	 * The ship's default acceleration
	 * The ship does not initially have a horizontal (x) acceleration, as it moves at a constant speed, which varies only from hitting obstacles and enemies.
	 * The y acceleration controls how fast the player is able to move the ship up and down.
	 */
	public final Vector2 DEFAULT_ACCELERATION = new Vector2(0, 15);
	
	/**
	 * The World's instance
	 */
	World world;
	
	/**
	 * Creates a ship with a position, dimensions and rotation.
	 * Does not give the ship an initial speed.
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Ship(Vector2 position, float width, float height, float rotation, World world) {
		super(position, width, height, rotation);
		this.world = world;
	}
	
	/**
	 * Fire a bullet from the ship
	 * Bullet leaves in the ... direction
	 */
	public void fire() {
		Bullet b = new Bullet(new Vector2(position.x + width / 2, position.y + height / 2), 3, 2, 0);
		b.getVelocity().x = (b.DEFAULT_VELOCITY.x);
		b.getVelocity().y = (b.DEFAULT_VELOCITY.y);
		world.getBullets().add(b);
		
		Gdx.app.log("Ship", "Ship fired a bullet!");
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
		rotation = velocity.angle();
		
		super.update();
	}
	
}
