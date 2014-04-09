/**
 * Base class for an entity.
 * Includes fields for position, dimensions, and hitbox.
 * Includes abstract update method.
 */
package com.asdf.ssjava.entities;

/**
 * @author Jeremy Brown
 *
 */

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
   
public abstract class AbstractEntity {
	
	/**
	 * The entity's position
	 */
	protected Vector2 position;
	
	/**
	 * The entity's width in pixels
	 */
	protected float width;
	
	/**
	 * The entity's height in pixels
	 */
	protected float height;
	
	/**
	 * The entity's rotation, in degrees
	 */
	protected float rotation;
	
	/**
	 * The entity's health levels. One point (integer) is equivalent to half a heart in gameplay.
	 */
	protected transient int health;
	
	/**
	 * Whether the entity is dead (or not)
	 */
	boolean dead;
	
	/**
	 * The Box2D world
	 */
	protected World world;
	
	/**
	 * The Box2D body of this entity
	 */
	protected Body body;
	
	/**
	 * Creates an entity
	 */
	protected AbstractEntity(Vector2 position, float width, float height, float rotation, World world) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		this.world = world;
		
		// TODO Box2D stuff
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(position.x, position.y);

		// Create our body in the world using our body definition
		body = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit;
		
		body.createFixture(fixtureDef);
		
		body.setUserData(this);
		
	}
	
	/**
	 * Returns the entity's position
	 * @return the position of the entity
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Sets the entity's position
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * Returns the entity's width
	 * @return the width of the entity
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Sets the entity's width
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	
	/**
	 * Returns the entity's height
	 * @return the height of the entity
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Sets the entity's height
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * Returns the entity's rotation
	 * @return the rotation of the entity in degrees
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * 
	 * @param rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * 
	 * @return the entity's health points
	 */
	public synchronized int getHealth() {
		return health;
	}
	
	/**
	 * 
	 * @param health the entity's health to be set
	 */
	public synchronized void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * 
	 * @param increment the value to add/subtract from the entity's health
	 */
	public synchronized void healthChange(int increment) {
		health += increment;
		if (getHealth() < 0) {
			setHealth(0);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Returns the Box2D body for this entity
	 */
	public Body getBody() {
		return body;
	}
	
	/**
	 * 
	 */
	public abstract void die();

}
    