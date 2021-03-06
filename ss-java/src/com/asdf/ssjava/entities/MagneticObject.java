package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * 
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */
public class MagneticObject extends Obstacle {

	/**
	 * The object's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The object's starting health
	 */
	public static final int DEFAULT_HEALTH = 5;
	
	/**
	 * The score given for hitting this object
	 */
	public static final int HIT_SCORE = 150;
	
	/**
	 * The score given for killing this object
	 */
	public static final int KILL_SCORE = 2000; 
	
	/**
	 * The object's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 6;
	
	/**
	 * The object's default height, in game coordinates
	 */
	public static final float DEFAULT_HEIGHT = 6;
	
	/**
	 * The object's default rotation, in degrees
	 */
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * Weight mod of a magnetic object, in kg
	 */
	public static final double MAG_OBJECT_WEIGHT_MOD = 0.0000000107185;
	
	/**
	 * Creates a magnetic object with the specified parameters
	 * @param position the objects's position
	 * @param width the objects's width
	 * @param height the objects's height
	 * @param rotation the objects's rotation in degrees
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
	 */
	public MagneticObject(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		setHealth(DEFAULT_HEALTH);
		createDef();
	}
	
	/**
	 * Constructor for serialization. 
	 * Creates a magnetic object with default parameters. 
	 */
	public MagneticObject() {
		super(null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION, null, null);
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * Constructor for level creator. 
	 * Creates a magnetic object with the specified parameters. 
	 * Does not initialize the world pointers.
	 * @param position the object's position
	 * @param width the object's width
	 * @param height the object's height
	 * @param rotation the object's rotation in degrees
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
		
		// Main fixture
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.7f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);
		
		// Sensor
		CircleShape circle2 = new CircleShape();
		circle2.setRadius(width / 2 + 10);
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
