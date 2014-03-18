/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Jeremy Brown
 *
 */

public class Ship extends MoveableEntity {
	
	/**
	 * The World's instance
	 */
	private World world;
	
	/**
	 * The ship's default velocity
	 * The ship will slowly return to the x velocity after it has hit another entity.
	 * The y velocity also limits the ship's vertical motion, which is controlled by the player.
	 * This is not automatically set by the constructor!
	 */
	public final Vector2 DEFAULT_VELOCITY = new Vector2(10, 10); 
	
	/**
	 * The ship's default acceleration
	 * The ship does not initially have a horizontal (x) acceleration, as it moves at a constant speed, which varies only from hitting obstacles and enemies.
	 * The y acceleration controls how fast the player is able to move the ship up and down.
	 * This is not automatically set by the constructor!
	 */
	public final Vector2 DEFAULT_ACCELERATION = new Vector2(0, 100);
	
	/**
	 * The type of bullets the ship will fire
	 */
	public final int bulletType = 0;
	
	/**
	 * The timestamp at which the last shot was fired from this ship, in milliseconds
	 */
	private long lastShotTime = 0;
	
	/**
	 * The time allowed between shots from this ship, in milliseconds
	 */
	private int shotCooldown = 300;
	
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
	 * Bullet leaves in the horizontal (right-side) direction
	 */
	public void fire() {
		if (TimeUtils.millis() - lastShotTime >= shotCooldown) {
			Bullet b = new BulletType0(new Vector2(position.x + width, position.y), 3, 2, 0, this);
			b.getPosition().y = position.y + height / 2 - b.height / 2;
			b.getVelocity().x = b.getDEFAULT_VELOCITY().x;
			b.getVelocity().y = b.getDEFAULT_VELOCITY().y;
			world.getBullets().add(b);
			
			lastShotTime = TimeUtils.millis();
			AudioPlayer.shoot();
			Gdx.app.log(SSJava.LOG, "Ship fired a bullet!");			
		}
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
		rotation = new Vector2(velocity.x * 2, velocity.y * 0.5f).angle();
		
		super.update();
	}

	/**
	 * @return the shot cooldown time for the ship, in milliseconds
	 */
	public int getShotCooldown() {
		return shotCooldown;
	}

	/**
	 * @param shotCooldown the shot cooldown time to set, in milliseconds
	 */
	public void setShotCooldown(int shotCooldown) {
		this.shotCooldown = shotCooldown;
	}
	
	
	
}
