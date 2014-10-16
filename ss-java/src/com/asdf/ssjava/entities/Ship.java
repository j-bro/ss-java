package com.asdf.ssjava.entities;

import java.math.BigInteger;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.GameWorld;
import com.asdf.ssjava.world.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Entity model of the ship. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class Ship extends MoveableEntity {
		
	/**
	 * The ship's default starting health
	 */
	public static final int DEFAULT_HEALTH = 8;
	
	/**
	 * The WorldRenderer instance
	 */
	WorldRenderer renderer;
	
	/**
	 * The ship's default velocity
	 * The ship will slowly return to the x velocity after it has hit another entity.
	 * The y velocity also limits the ship's vertical motion, which is controlled by the player.
	 * This is not automatically set by the constructor!
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(10, 30);  
	
	/**
	 * The velocity of the ship when the speed of light powerup is activated
	 */
	public static final Vector2 SPEED_OF_LIGHT_VELOCITY = new Vector2(40, 0);
	
	/**
	 * The ship's default acceleration
	 * The ship does not initially have a horizontal (x) acceleration, as it moves at a constant speed, which varies only from hitting obstacles and enemies.
	 * The y acceleration controls how fast the player is able to move the ship up and down.
	 */
	public static Vector2 DEFAULT_ACCELERATION = new Vector2(35, 500);
	
	/**
	 * The ship's default shot cooldown, in milliseconds
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
	private boolean speedOfLightEnabled = false;
	
	/**
	 * Indicates whether or not the ship is currently at its maximum upwards speed 
	 */
	public boolean maxUpSpeedReached = false;
	
	/**
	 * Indicates whether or not the ship is currently at its maximum downwards speed 
	 */
	public boolean maxDownSpeedReached = false;
	
	/**
	 * Weight of the ship, in kg 
	 */
	public static final BigInteger SHIP_WEIGHT = new BigInteger("907185"); 
	
	/**
	 * Creates a ship with a position, dimensions and rotation. 
	 * Also creates body & fixture definitions. 
	 * @param position the position of the ship
	 * @param width the width of the ship
	 * @param height the height of the ship
	 * @param rotation the rotation of the ship
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
	 */
	public Ship(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		this.gameWorld = gameWorld;
		setHealth(DEFAULT_HEALTH);
		shotCooldown = DEFAULT_SHOT_COOLDOWN_MS;
		createDef();
	}
	
	/**
	 * Fires a bullet from the ship. 
	 * Bullet leaves in the horizontal (right-side) direction. 
	 */
	public void fire() {
		if (TimeUtils.millis() - lastShotTime >= shotCooldown) {
			Bullet b = new BulletType0(new Vector2(position.x + width, position.y), 1.5f, 1, 0, gameWorld, box2DWorld, this);
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
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "SHIP DIES!!!");
		AudioPlayer.shipDeath();
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {
		if (!isDead()) {
			// Edge of screen collision	
			renderer = gameWorld.getRenderer();
			float screenTop = renderer.getCamera().position.y + renderer.getCamera().viewportHeight / 2;
			float screenBottom = renderer.getCamera().position.y - renderer.getCamera().viewportHeight / 2;
			float screenLeft = renderer.getCamera().position.x - renderer.getCamera().viewportWidth / 2;
			float screenRight = renderer.getCamera().position.x + renderer.getCamera().viewportWidth / 2;
				
			//Stop the ship from going off the top of the screen
			if (getPosition().y + getHeight() / 2 >= (screenTop)) {				
				getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);	
				getBody().getPosition().y = screenTop - getHeight()/2;
				getBody().applyForceToCenter(0, (-1) * DEFAULT_ACCELERATION.y, true);
				Gdx.app.log(SSJava.LOG, "TOP: " + getBody().getPosition().y);
			}
			//Stop the ship from going off the bottom of the screen
			else if (getPosition().y - getHeight() / 2 <= screenBottom) {
				getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
				getBody().getPosition().y = screenBottom + getHeight()/2;
				getBody().applyForceToCenter(0, DEFAULT_ACCELERATION.y, true);
				Gdx.app.log(SSJava.LOG, "BOT: " + getBody().getPosition().y);
			}
			
			//Stop the ship from going off the right or left sides of the screen
			if (getPosition().x + getWidth() / 2 >= screenRight || getPosition().x - getWidth() / 2 <= screenLeft) {
				Gdx.app.log(SSJava.LOG, "Ship hit right or left of screen");
				getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
			}
			
			// Key input && if level is not complete
			if (!(gameWorld.isLevelComplete())) {
				// Accelerate ship if it is going slower than default velocity & limit speed at maximum velocity	
				if (getBody().getLinearVelocity().x < DEFAULT_VELOCITY.x) {
					getBody().applyForceToCenter(DEFAULT_ACCELERATION.x, 0, true);
				}
				
				if (Gdx.input.isKeyPressed(Keys.W)) {
					// Limit y velocity
					if (getBody().getLinearVelocity().y > DEFAULT_VELOCITY.y) {
						getBody().setLinearVelocity(getBody().getLinearVelocity().x, DEFAULT_VELOCITY.y);
					}
					//Stop the ship from going off the top of the screen when W is continuously pressed 
					if(getPosition().y + getHeight()/2 >= screenTop) {
						getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);	
						getBody().getPosition().y = screenTop - getHeight()/2;	
					}
					else {
						getBody().applyForceToCenter(0, DEFAULT_ACCELERATION.y, true);
					}
				}
				
				else if (Gdx.input.isKeyPressed(Keys.S)) {
					// Limit y velocity
					if (getBody().getLinearVelocity().y < -DEFAULT_VELOCITY.y) {
						getBody().setLinearVelocity(getBody().getLinearVelocity().x, -DEFAULT_VELOCITY.y);
					}
					//Stop the ship from going off the bottom of the screen when S is continuously pressed 
					if(getPosition().y - getHeight()/2 <= screenBottom) {
						getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);	
						getBody().getPosition().y = screenBottom + getHeight()/2;
					}
					else {
						getBody().applyForceToCenter(0, (-1) * DEFAULT_ACCELERATION.y, true);
					}
				}
				
				else if (getPosition().x - getWidth() / 2 <= screenLeft) {
					if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Ship hit right or left of screen");
					getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
					getBody().getPosition().x = screenRight + getWidth() / 2;
				}	
			}
				
			float shipAngle = getBody().getAngle();
			float mod = (float) (2 * Math.PI);
			float angleMod = (shipAngle < 0) ? (mod - (Math.abs(shipAngle) % mod) ) % mod : (shipAngle % mod);
			
			// Realign ship
			if (angleMod < Math.PI) {
				float diff = angleMod;
				getBody().setAngularVelocity(-0.2f * diff);
			}
			else {
				float diff = mod - angleMod;
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
	 * Gets the shot cooldown time
	 * @return the shot cooldown time for the ship, in milliseconds
	 */
	public int getShotCooldown() {
		return shotCooldown;
	}

	/**
	 * Sets the shot cooldown time
	 * @param shotCooldown the shot cooldown time to set, in milliseconds
	 */
	public void setShotCooldown(int shotCooldown) {
		this.shotCooldown = shotCooldown;
	}
	
	/**
	 * Gets the speed of light flag
	 * @return true if light speed mode flag is set
	 */
	public boolean isSpeedOfLightEnabled() {
		return speedOfLightEnabled;
	}

	/**
	 * Sets the speed of light flag
	 * @param enabled true if the speed of light flag is to be enabled
	 */
	public void setSpeedOfLightEnabled(boolean enabled) {
		this.speedOfLightEnabled = enabled;
	}
	
	/**
	 * Enables the speed of light mode of the ship.
	 */
	public void enableSpeedOfLight() {
		if (!isSpeedOfLightEnabled() && !isDead()) {			
			getBody().setLinearVelocity(Ship.SPEED_OF_LIGHT_VELOCITY);
			Fixture f = getBody().getFixtureList().get(0);
			if (f != null) { 					
				f.setSensor(true);
			}
			setSpeedOfLightEnabled(true);
			AudioPlayer.speedOfLightOn();
		}
	}
	
	/**
	 * Disables the speed of light mode of the ship. 
	 */
	public void disableSpeedOfLight() {
		if (!isDead()) {
			getBody().setLinearVelocity(Ship.DEFAULT_VELOCITY.x, 0);
			Fixture f = getBody().getFixtureList().get(0);
			if (f != null) { 					
				f.setSensor(false);
			}
			setSpeedOfLightEnabled(false);
			AudioPlayer.speedOfLightOff();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createDef()
	 */
	@Override
	public void createDef() {
		super.createDef();
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);
		initialize();
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
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#initialize()
	 */
	@Override
	public void initialize() {
		body.setLinearVelocity(DEFAULT_VELOCITY.x, 0);
		setInitialized(true);
	}
}
