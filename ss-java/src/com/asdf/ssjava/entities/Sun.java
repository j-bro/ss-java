package com.asdf.ssjava.entities;

import java.math.BigInteger;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The sun implementation of a game changer. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class Sun extends Obstacle {

	/**
	 * The sun's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The sun's default health
	 */
	public static final int DEFAULT_HEALTH = 100000;
	
	/**
	 * Kill score for killing the sun
	 */
	public static final int KILL_SCORE = 1000000;
	
	/**
	 * The sun's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 48;
	
	/**
	 * The sun's default height, in game coordinates
	 */
	public static final float DEFAULT_HEIGHT = 48;
	
	/**
	 * The sun's default rotation, in degrees
	 */
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * The sun's weight, in kg
	 */
	public static final BigInteger SUN_WEIGHT = new BigInteger("1989000000000000000000000000000"); 
	
	
	/**
	 * Creates a sun with the specified parameters. 
	 * @param position the position of the sun
	 * @param width the width of the sun
	 * @param height the height of the sun
	 * @param rotation the rotation of the sun
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
	 */
	public Sun(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		createDef();
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for serialization. 
	 * Creates a sun with default parameters. 
	 */
	public Sun() {
		super(null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION, null, null);
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for level creator. 
	 * Creates a sun with the specified parameters. 
	 * Does not initialize the world pointers.
	 * @param position the position of the sun
	 * @param width the width of the sun
	 * @param height the height of the sun
	 * @param rotation the rotation of the sun, in degrees
	 */
	public Sun(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation, null, null);
		setHealth(DEFAULT_HEALTH);
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createFixtureDef()
	 */
	@Override
	public void createDef() {
		super.createDef();
		
		// Main fixture
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1000000f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);
		
		// Sensor
		CircleShape circle2 = new CircleShape();
		circle2.setRadius(width / 2 + 13);
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.shape = circle2;
		fixtureDef2.density = 0.5f; 
		fixtureDef2.friction = 1.0f;
		fixtureDef2.restitution = 0.1f;
		fixtureDef2.isSensor = true;
		body.createFixture(fixtureDef2);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Sun";
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#getHitScore()
	 */
	@Override
	public int getHitScore() {
		return 0;
	}

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
