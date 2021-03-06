package com.asdf.ssjava.entities;

import java.math.BigInteger;
import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Planet type implementation of a game changer. 
 * @author Simon Thompson
 * @author Jeremy Brown
 */
public class Planet extends Obstacle {
	
	/**
	 * The planet's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 100000;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 100;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 1000; 
	
	/**
	 * The planet's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 24;
	
	/**
	 * The planet's default height, in game coordinates
	 */
	public static final float DEFAULT_HEIGHT = 24;
	
	/**
	 * The planet's default rotation, in degrees
	 */
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * The gravitational constant
	 */
	public static final double GRAVITATIONNAL_CONSTANT = 6.67;

	/**
	 * The planet's weight in kg
	 */
	public static final BigInteger PLANET_WEIGHT = new BigInteger("5972000000000000000000000"); 
	
	/**
	 * The planet's radius
	 */
	public static final long PLANET_RADIUS = 6378;
	
	/**
	 * Creates a planet with the specified parameters
	 * @param position the position of the planet
	 * @param width the width of the planet
	 * @param height the height of the planet
	 * @param rotation the rotation of the planet, in degrees
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
	 */
	public Planet(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		setHealth(DEFAULT_HEALTH);
		createDef();
	}
	
	/**
	 * Constructor for serialization. 
	 * Creates a planet with default parameters. 
	 */
	public Planet() {
		super(null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION, null, null);
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for level creator
	 * Creates a plane with default parameters. 
	 * @param position the position of the planet
	 * @param width the width of the planet
	 * @param height the height of the planet
	 * @param rotation the rotation of the planet, in degrees
	 */
	public Planet(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation, null, null);
		setHealth(DEFAULT_HEALTH);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Planet";
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#die()
	 */
	@Override
	public void die() {
		gameWorld.getScoreKeeper().add(KILL_SCORE);
		super.die();
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
		fixtureDef.density = 0.5f; 
		fixtureDef.density = 1000f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);
		
		// Sensor
		CircleShape circle2 = new CircleShape();
		circle2.setRadius(width / 2 + 8);
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
		body.setLinearVelocity(DEFAULT_VELOCITY);
		setInitialized(true);
	}
}
