package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class PowerupHealthUp extends Powerup {
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * The health points given to the ship when it picks up this power up
	 */
	public static final int HEALTH_GIVEN = 2;
	
	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public PowerupHealthUp(Vector2 position, float width, float height, float rotation, World world) {
		super(position, width, height, rotation, world);
		setHealth(DEFAULT_HEALTH);
		createFixtureDef();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Powerup#update()
	 */
	public void update() {
		super.update();
	}
	
	/**
	 * TODO Constructor for serialization
	 */
	/*
	public PowerupHealthUp() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		setHealth(DEFAULT_HEALTH);
	}
	*/
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Health Up Powerup";
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
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);

	}
}
