/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Jeremy Brown
 * 
 */
public class Asteroid extends Obstacle {

	/**
	 * The asteroid's default velocity
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(2, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 2;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 5;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 20; 
	
	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Asteroid(Vector2 position, float width, float height, float rotation, World world) {
		super(position, width, height, rotation, world);
		setHealth(DEFAULT_HEALTH);
		
		createFixtureDef();
	}
	
	// TODO constructor for serialization
	/*
	public Asteroid() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		setHealth(DEFAULT_HEALTH);
	}
	*/

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
	
	/**
	 * @return the object's description string
	 */
	public String toString() {
		return "Asteroid";
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
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);

	}
	
}
