package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Health up implementation of a powerup. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class PowerupHealthUp extends Powerup {
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * Default velocity for the Health up powerup
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The health points given to the ship when it picks up this power up
	 */
	public static final int HEALTH_GIVEN = 4;
	
	/**
	 * The powerup's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 3;
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
	 * Creates a health up powerup with the specified parameters. 
	 * @param position the position of the health up powerup
	 * @param width the width of the health up powerup
	 * @param height the height of the health up powerup
	 * @param rotation the rotation of the health up powerup
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
	 */
	public PowerupHealthUp(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		setHealth(DEFAULT_HEALTH);
		createDef();
	}
	
	/**
	 * Constructor for serialization. 
	 * Creates a health up powerup with default parameters. 
	 **/
	public PowerupHealthUp() {
		super(null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION, null, null);
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for level creator. 
	 * Creates a health up powerup with the specified parameters. 
	 * Does not initialize the world pointers.
	 * @param position the position of the health up powerup
	 * @param width the width of the health up powerup
	 * @param height the height of the health up powerup
	 * @param rotation the rotation of the health up powerup, in degrees
	 */
	public PowerupHealthUp(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation, null, null);
		setHealth(DEFAULT_HEALTH);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Health Up Powerup";
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createDef()
	 */
	@Override
	public void createDef() {
		super.createDef();
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
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
