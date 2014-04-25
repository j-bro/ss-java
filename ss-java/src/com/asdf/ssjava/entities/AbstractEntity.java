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

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
   
public abstract class AbstractEntity {
	
	/**
	 * The entity's position (now the center of the entity)
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
	 * Whether or not the entity is currently visible
	 */
	protected transient boolean visible = true;

	/**
	 * The game world instance
	 */
	protected transient GameWorld gameWorld;
	
	/**
	 * The Box2D world instance
	 */
	protected transient World box2DWorld;
	
	/**
	 * The Box2D body of this entity
	 */
	protected transient Body body;
		
	/**
	 * The loader instance for the body's fixtures
	 */
//	private transient BodyEditorLoader loader;
	
	/**
	 * Creates an entity
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 * @param gameWorld
	 * @param box2DWorld
	 */
	protected AbstractEntity(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		this.gameWorld = gameWorld;
		this.box2DWorld = box2DWorld;
		
	}
	
	/**
	 * Creates the body definition for this entity
	 */
	public void createDef() {
		// TODO Box2D stuff
//		loader = new BodyEditorLoader(Gdx.files.internal("data/bodies.json"));
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position.x, position.y);
		bodyDef.angle = MathUtils.degreesToRadians * rotation;

		body = box2DWorld.createBody(bodyDef);
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
		if (health < 0) {
			health = 0;
		}
	}
	
	/**
	 * 
	 * @return if the entity is dead
	 */
	public boolean isDead() {
		if (health <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @return whether or not the entity is currently visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible sets whether or not the entity is visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Returns the Box2D body for this entity
	 */
	public Body getBody() {
		return body;
	}
	
	/**
	 * 
	 * @return gameWorld the gameWorld instance
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
	}
	
	/**
	 * Initialize the non-serialized worlds
	 */
	public void initWorlds(GameWorld gameWorld, World box2DWorld) {
		this.gameWorld = gameWorld;
		this.box2DWorld = box2DWorld;
	}
	
	/**
	 * Runs every time the game renders a frame.
	 */
	public void update() {
		
	}
	
	public abstract int getHitScore();
	public abstract int getKillScore();
	
	/**
	 * Called when the entity's health is 0
	 * Usually removes the entity and it's display instance from the world
	 */
	public abstract void die();
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Abstract Entity";
	}
}
    