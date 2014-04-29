package com.asdf.ssjava.entities;

import java.math.BigInteger;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Sun extends Obstacle{

	/**
	 * @param args
	 */
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
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 * @param gameWorld
	 * @param box2DWorld
	 */
	public Sun(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		createDef();
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for serialization
	 */
	public Sun() {
		super(null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION, null, null);
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for level creator
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
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
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1000000f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);
		body.setLinearVelocity(DEFAULT_VELOCITY);
		
		CircleShape circle2 = new CircleShape();
		circle2.setRadius(width / 2 + 10);
		
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.shape = circle2;
		fixtureDef2.density = 0.5f; 
		fixtureDef2.friction = 1.0f;
		fixtureDef2.restitution = 0.1f;
		fixtureDef2.isSensor = true;
		
		body.createFixture(fixtureDef2);
		body.setLinearVelocity(DEFAULT_VELOCITY);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Sun";
	}

	@Override
	public int getHitScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getKillScore() {
		// TODO Auto-generated method stub
		return KILL_SCORE;
	}

}
