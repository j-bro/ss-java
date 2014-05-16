package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
   
/**
 * Base class for an entity. Includes fields for position, dimensions, and hitbox.
 * @author Jeremy Brown
 * @author Simon Thompson
 */
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
	 * The entity's health level
	 * One point (integer) is equivalent to half a heart in gameplay
	 */
	protected transient int health;
	
	/**
	 * Whether or not the entity is currently visible
	 */
	protected transient boolean visible = true;

	/**
	 * Whether or not the entity was initialized (given its intial velocity)
	 */
	protected transient boolean initialized = false;
	
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
	 * The timestamp at which the last contact w/ the ship was made
	 */
	public long lastContactTime = 0;
	
	/**
	 * Creates an entity with specified parameters
	 * @param position the entity's position
	 * @param width the entity's width
	 * @param height the entity's height
	 * @param rotation the entity's rotation in degrees
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance (Box2D for collisions)
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
	 * Gets the entity's rotation in degrees
	 * @return the rotation of the entity in degrees
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Sets the entity's rotation in degrees
	 * @param rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Gets the entity's health
	 * @return the entity's health points
	 */
	public synchronized int getHealth() {
		return health;
	}
	
	/**
	 * Sets the entity's health
	 * @param health the entity's health to be set
	 */
	public synchronized void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * Modifies the health by the specified increment
	 * Increment can be positive or negative
	 * Does not allow health to go below zero
	 * @param increment the value to add/subtract from the entity's health
	 */
	public synchronized void healthChange(int increment) {
		health += increment;
		if (health < 0) {
			health = 0;
		}
	}
	
	/**
	 * Checks if the entity is dead
	 * @return true if the entity's health is zero; false otherwise
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
	 * Checks if the entity is currently visible
	 * @return true is the entity is visible; false otherwise
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the visibility of the entity
	 * @param visible sets whether or not the entity is visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Checks if the entity has been initialized
	 * @return true if the initialized flag is set
	 */
	public boolean isInitialized() {
		return initialized;
	}
	/**
	 * Sets the entity's initialized flag
	 * @param initialized the initialized flag to set
	 */
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	/**
	 * Gets the Box2D body for this entity
	 * @return the body instance
	 */
	public Body getBody() {
		return body;
	}
	
	/**
	 * Gets the GameWorld instance associated to this entity
	 * @return gameWorld the associated gameWorld instance
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
	}
	
	/**
	 * Initialize the worlds
	 * Adds references to the two game worlds to this object
	 * Is called when loading a level to pass references of the non-serialized world objects to the entity
	 */
	public void initWorlds(GameWorld gameWorld, World box2DWorld) {
		this.gameWorld = gameWorld;
		this.box2DWorld = box2DWorld;
	}
	
	/**
	 * Updates the entity
	 * Does nothing unless overridden
	 * Runs every time the game renders a frame
	 */
	public void update() {
		
	}
	
	public abstract int getHitScore();
	public abstract int getKillScore();
	
	/**
	 * Initializes the entity to make it active in the game
	 * Should give the entity a default velocity and allow it to shoot
	 */
	public abstract void initialize();
	
	/**
	 * Called when the entity's health is 0
	 * In most implementations, removes the entity and it's display instance from the world
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
    