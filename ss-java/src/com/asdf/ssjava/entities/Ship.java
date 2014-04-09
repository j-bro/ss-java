/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Jeremy Brown
 *
 */

public class Ship extends MoveableEntity {
	
	/**
	 * The World's instance
	 */
	private GameWorld gameWorld;
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 6;
	
	/**
	 * The ship's default velocity
	 * The ship will slowly return to the x velocity after it has hit another entity.
	 * The y velocity also limits the ship's vertical motion, which is controlled by the player.
	 * This is not automatically set by the constructor!
	 */
	public final Vector2 DEFAULT_VELOCITY = new Vector2(10, 10); 
	
	/**
	 * The ship's slow velocity
	 * Slows down to this speed when it collides with another object
	 */
	public final Vector2 SLOW_VELOCITY = new Vector2(3, 0); 
	
	/**
	 * The ship's default acceleration
	 * The ship does not initially have a horizontal (x) acceleration, as it moves at a constant speed, which varies only from hitting obstacles and enemies.
	 * The y acceleration controls how fast the player is able to move the ship up and down.
	 * This is not automatically set by the constructor!
	 */
	public final static Vector2 DEFAULT_ACCELERATION = new Vector2(10, 1000);
	
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
	 * The ship cannot lose health from collisions as long as this is true
	 */
	public boolean lightSpeedMode = false;
	
	/**
	 * Indicates whether or not the ship is currently at its maximum speed 
	 */
	public boolean maxUpSpeedReached = false;
	public boolean maxDownSpeedReached = false;
	
	/**
	 * Creates a ship with a position, dimensions and rotation.
	 * Does not give the ship an initial speed.
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Ship(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world) {
		super(position, width, height, rotation, world);
		this.gameWorld = gameWorld;
		setHealth(6);
	}
	
	/**
	 * Fire a bullet from the ship
	 * Bullet leaves in the horizontal (right-side) direction
	 */
	public void fire() {
		if (TimeUtils.millis() - lastShotTime >= shotCooldown) {
			Bullet b = new BulletType0(new Vector2(position.x + width, position.y), 3, 2, 0, world, this);
			b.getPosition().y = position.y + height / 2 - b.height / 2;
			b.getBody().setLinearVelocity(BulletType0.DEFAULT_VELOCITY);
			gameWorld.getBullets().add(b);
			
			lastShotTime = TimeUtils.millis();
			AudioPlayer.shoot();
			Gdx.app.log(SSJava.LOG, "Ship fired bullet " + Integer.toHexString(b.hashCode()));			
		}
	}
	
	/**
	 * Is called when the ship's health reaches zero (or lower)
	 */
	public void die() {
		Gdx.app.log(SSJava.LOG, "SHIP DIES!!!");
		dead = true;
	}

	@Override
	public void update() { 
		if (!isDead()) {
			if (Math.abs(getBody().getLinearVelocity().y) >= DEFAULT_VELOCITY.y) {
				if (getBody().getLinearVelocity().y > 0) {
					maxUpSpeedReached = true;
				}
				else {
					maxDownSpeedReached = true;
				}
			}
			else {
				maxUpSpeedReached = false;
				maxDownSpeedReached = false;
			}
			
			// TODO fix too high velocity
			if (getBody().getLinearVelocity().x < DEFAULT_VELOCITY.x); {
				Gdx.app.log(SSJava.LOG, "vel: " + getBody().getLinearVelocity().x + " def: " + DEFAULT_VELOCITY.x);
				getBody().applyForceToCenter(DEFAULT_ACCELERATION.x, 0, true);
			}
		}
		
		super.update();
	}

	public String toString() {
		return "Player's ship";
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
