/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * 
 * @author Simon Thompson
 *
 */
public class Planet extends Obstacle {
	
	/**
	 * The planet's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 4;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 100;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 1000; 
	
	public static final float DEFAULT_WIDTH = 4;
	public static final float DEFAULT_HEIGHT = 4;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Planet(Vector2 position, float width, float height, float rotation, World world) {
		super(position, width, height, rotation, world);
		setHealth(DEFAULT_HEALTH);
		
		createFixtureDef();
	}
	
	// TODO constructor for serialization
	/*
	public Planet() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		setHealth(DEFAULT_HEALTH);
	}
	*/

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Planet";
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createFixtureDef()
	 */
	@Override
	public void createFixtureDef() {
		// TODO Box2D stuff
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);
		
	}

}
