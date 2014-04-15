/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Jeremy Brown
 *
 */

public class Ship extends MoveableEntity {
		
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
	 */
	public final static Vector2 DEFAULT_ACCELERATION = new Vector2(8, 1000);
	
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
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 0;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 0; 
	
	/**
	 * Creates a ship with a position, dimensions and rotation.
	 * Does not give the ship an initial speed.
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Ship(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world) {
		super(position, width, height, rotation, gameWorld, world);
		this.gameWorld = gameWorld;
		setHealth(DEFAULT_HEALTH);
		
		createFixtureDef();
		
	}
	
	/**
	 * Fire a bullet from the ship
	 * Bullet leaves in the horizontal (right-side) direction
	 */
	public void fire() {
		if (TimeUtils.millis() - lastShotTime >= shotCooldown) {
			Bullet b = new BulletType0(new Vector2(position.x + width, position.y), 3, 2, 0, gameWorld, world, this);
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
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() { 
		if (!isDead()) {
			// Accelerate ship if it is going slower than default velocity & limit speed at maximum velocity
			if (getBody().getLinearVelocity().x < DEFAULT_VELOCITY.x) {
				getBody().applyForceToCenter(DEFAULT_ACCELERATION.x, 0, true);
			}
			
			// TODO Correct ship rotation course
			
			// check if angle multiple of 2*pi
			if (!(getBody().getAngle() % (2 * Math.PI) == 0)) {
				
			}
			
		}
		
		super.update();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Ship";
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

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createFixtureDef()
	 */
	@Override
	public void createFixtureDef() {
		// TODO Box2D stuff
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#getHitScore()
	 */
	@Override
	public int getHitScore() {
		return HIT_SCORE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#getKillScore()
	 */
	@Override
	public int getKillScore() {
		return KILL_SCORE;
	}
}
