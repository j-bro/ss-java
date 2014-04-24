/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
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
	 * The ship's default starting health
	 */
	public static final int DEFAULT_HEALTH = 6;
	
	/**
	 * The ship's default velocity
	 * The ship will slowly return to the x velocity after it has hit another entity.
	 * The y velocity also limits the ship's vertical motion, which is controlled by the player.
	 * This is not automatically set by the constructor!
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(10, 10);  
	
	/**
	 * The velocity of the ship when the speed of light powerup is activated
	 */
	public static final Vector2 SPEED_OF_LIGHT_VELOCITY = new Vector2(35, 0);
	
	/**
	 * The ship's default acceleration
	 * The ship does not initially have a horizontal (x) acceleration, as it moves at a constant speed, which varies only from hitting obstacles and enemies.
	 * The y acceleration controls how fast the player is able to move the ship up and down.
	 */
	public static Vector2 DEFAULT_ACCELERATION = new Vector2(18, 300);
	
	/**
	 * 
	 */
	public static final int DEFAULT_SHOT_COOLDOWN_MS = 300;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 0;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 0; 
	
	/**
	 * The ship's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 6;
	
	/**
	 * The ship's default height, in game coordinates
	 */
	public static final float DEFAULT_HEIGHT = 3;
	
	/**
	 * The ship's default rotation, in game degrees
	 */
	public static final float DEFAULT_ROTATION = 0;
	
	
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
	private int shotCooldown;

	/**
	 * The ship cannot lose health from collisions as long as this is true
	 */
	private boolean lightSpeedEnabled = false;
	
	/**
	 * Indicates whether or not the ship is currently at its maximum speed 
	 */
	public boolean maxUpSpeedReached = false;
	public boolean maxDownSpeedReached = false;
	
	/**
	 * Creates a ship with a position, dimensions and rotation.
	 * Also creates body & fixture definitions, and sets the default velocity
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 * @param gameWorld
	 * @param box2DWorld
	 */
	public Ship(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		this.gameWorld = gameWorld;
		setHealth(DEFAULT_HEALTH);
		shotCooldown = DEFAULT_SHOT_COOLDOWN_MS;
		createDef();
		
	}
	
	/**
	 * Fire a bullet from the ship
	 * Bullet leaves in the horizontal (right-side) direction
	 */
	public void fire() {
		if (TimeUtils.millis() - lastShotTime >= shotCooldown) {
			Bullet b = new BulletType0(new Vector2(position.x + width, position.y), 3, 2, 0, gameWorld, box2DWorld, this);
			b.getPosition().y = position.y + height / 2 - b.height / 2;
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
			
			// Edge of screen collision	
			Camera cam = gameWorld.getRenderer().getCamera();
			float screenTop = cam.position.y + cam.viewportHeight / 2;
			float screenBottom = cam.position.y - cam.viewportHeight / 2;
			float screenLeft = cam.position.x - cam.viewportWidth / 2;
			float screenRight = cam.position.x + cam.viewportWidth / 2;
			
			if (getPosition().y + getHeight() / 2 >= (screenTop)) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Ship hit top or bottom of screen");
				getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
				getBody().getPosition().y = screenTop - getHeight() / 2;
			}
			else if (getPosition().y - getHeight() / 2 <= screenBottom) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Ship hit top or bottom of screen");
				getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
				getBody().getPosition().y = screenBottom + getHeight() / 2;
			}
			else if (getPosition().x + getWidth() / 2 >= screenRight) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Ship hit right or left of screen");
				getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
				getBody().getPosition().x = screenRight - getWidth() / 2;
			}
			
			else if (getPosition().x - getWidth() / 2 <= screenLeft) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Ship hit right or left of screen");
				getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
				getBody().getPosition().x = screenRight + getWidth() / 2;
			}	
			
			// Key input
			if (Gdx.input.isKeyPressed(Keys.W)) {
				if (!(getPosition().y + getHeight() / 2 >= (screenTop))) {
					getBody().applyForceToCenter(0, DEFAULT_ACCELERATION.y, true);
				}
				else {
					getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
				}
			}
			else if (Gdx.input.isKeyPressed(Keys.S)) {
				if (!(getPosition().y - getHeight() / 2 <= screenBottom)) {					
					getBody().applyForceToCenter(0, (-1) * DEFAULT_ACCELERATION.y, true);				
				}
				else {
					getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
				}
			}
			
			
			float shipAngle = getBody().getAngle();
			float mod = (float) (2 * Math.PI);
			float angleMod = (shipAngle < 0) ? (mod - (Math.abs(shipAngle) % mod) ) % mod : (shipAngle % mod);
			
			// TODO fix ship rotation
			if (angleMod < Math.PI) {
				float diff = angleMod;
//				Gdx.app.log(SSJava.LOG, "smaller");
//				getBody().applyAngularImpulse(-3f * diff, true);
				getBody().setAngularVelocity(-0.2f * diff);
			}
			else {
				float diff = mod - angleMod;
//				Gdx.app.log(SSJava.LOG, "greater");
//				getBody().applyAngularImpulse(3f * diff, true);
				getBody().setAngularVelocity(0.2f * diff);
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
	
	/**
	 * @return true if light speed mode is enabled
	 */
	public boolean isLightSpeedEnabled() {
		return lightSpeedEnabled;
	}

	/**
	 * @param lightSpeedMode the lightSpeedMode to set
	 */
	public void setLightSpeedEnabled(boolean lightSpeedMode) {
		this.lightSpeedEnabled = lightSpeedMode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createDef()
	 */
	@Override
	public void createDef() {
		super.createDef();
		// TODO Box2D stuff
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);
		body.setLinearVelocity(DEFAULT_VELOCITY.x, 0);
//		loader.attachFixture(body, "Ship", fixtureDef, DEFAULT_WIDTH);

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#setHealth(int)
	 */
	@Override
	public synchronized void setHealth(int health) {
		super.setHealth(health);
		if (this.health > DEFAULT_HEALTH) {
			this.health = DEFAULT_HEALTH;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#healthChange(int)
	 */
	public synchronized void healthChange(int increment) {
		super.healthChange(increment);
		if (health > DEFAULT_HEALTH) {
			health = DEFAULT_HEALTH;
		}
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
