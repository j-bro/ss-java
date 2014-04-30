package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MagneticObject extends Obstacle{

	/**
	 * @param args
	 */
	/**
	 * The planet's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 5;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 150;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 2000; 
	
	/**
	 * The planet's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 6;
	
	/**
	 * The planet's default height, in game coordinates
	 */
	public static final float DEFAULT_HEIGHT = 6;
	
	/**
	 * The planet's default rotation, in degrees
	 */
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * Weight mod of a magnetic object, in kg
	 */
	public static final double MAG_OBJECT_WEIGHT_MOD = 0.0000000107185;
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 * @param gameWorld
	 * @param box2DWorld
	 */
	public MagneticObject(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		setHealth(DEFAULT_HEALTH);
		createDef();
	}
	
	/**
	 * Constructor for serialization
	 */
	public MagneticObject() {
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
	public MagneticObject(Vector2 position, float width, float height, float rotation) {
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
		fixtureDef.density = 0.5f; 
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
		return "Magnetic Object";
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
