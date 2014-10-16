package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Speed of Light implementation of a powerup. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class PowerupSpeedOfLight extends Powerup {

	/**
	 * The time the powerup's effect will last, in seconds
	 */
	public static final float COOLDOWN_SECONDS = 5;
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * Default velocity for the Speed of Light powerup
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The powerup's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 6;
	/**
	 * The powerup's default height, in game coordinates
	 */
	public static final float DEFAULT_HEIGHT = 3;
	/**
	 * The powerup's default rotation, in degrees
	 */
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 0;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 100;
	
	/**
	 * Creates a speed of light powerup with the specified parameters. 
	 * @param position the position of the speed of light powerup 
	 * @param width the width of the speed of light powerup
	 * @param height the height of the speed of light powerup
	 * @param rotation the rotation of the speed of light powerup
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
	 */
	public PowerupSpeedOfLight(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		setHealth(DEFAULT_HEALTH);
		createDef();
	}

	/**
	 * Constructor for serialization
	 */
	public PowerupSpeedOfLight() {
		super(null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION, null, null);
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for level creator. 
	 * Creates a speed of light powerup with the specified parameters. 
	 * Does not initialize the world pointers.
	 * @param position the position of the speed of light powerup
	 * @param width the width of the speed of light powerup
	 * @param height the height of the speed of light powerup
	 * @param rotation the rotation of the speed of light powerup, in degrees
	 */
	public PowerupSpeedOfLight(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation, null, null);
		setHealth(DEFAULT_HEALTH);
	}
		
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Speed of Light Powerup";
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
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.1f;
		fixtureDef.isSensor = true;
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

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		gameWorld.getScoreKeeper().add(KILL_SCORE);
		super.die();
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
		body.setLinearVelocity(DEFAULT_VELOCITY);
		setInitialized(true);
	}
}
