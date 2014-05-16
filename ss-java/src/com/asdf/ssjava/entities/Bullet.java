package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Entity model for a bullet. 
 * Adds starting velocity which can be in any direction, but in most cases will only horizontal.
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public abstract class Bullet extends MoveableEntity {
	
	/**
	 * The type of bullet
	 */
	int type;
	
	/**
	 * The bullet's default velocity
	 */
	Vector2 DEFAULT_VELOCITY;
	
	/**
	 * The entity that shot this bullet
	 */
	AbstractEntity shooter;
	
	/**
	 * The damage incurred when the bullet hits the ship or an enemy
	 */
	int damage;
	
	/**
	 * The health of a bullet (1, as it dies as soon as it collides)
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * Creates a bullet with the specified parameters
	 * @param position the position of the bullet
	 * @param width the width of the bullet
	 * @param height the height of the bullet
	 * @param rotation the rotation of the bullet in degrees
	 */
	public Bullet(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld, AbstractEntity shooter) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
		this.shooter = shooter;
		this.health = DEFAULT_HEALTH;
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		gameWorld.bullets.removeValue(this, true);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {
		// Remove bullets if they go offscreen
		if (getPosition().x > gameWorld.getShip().getPosition().x + 50 || getPosition().x < gameWorld.getShip().getPosition().x - 30) {
			setHealth(0);
		}
		super.update();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Abstract Bullet";
	}

	/**
	 * Gets the type of the bullet
	 * @return the type of the bullet
	 */
	public abstract int getType();
	
	/**
	 * Gets the damage value of the bullet. 
	 * @return the damage value the bullet deals out to other entities
	 */
	public abstract int getDamage();
	
	/**
	 * Gets the shooter of the bullet. 
	 * @return the entity that fired the bullet
	 */
	public abstract AbstractEntity getShooter();	
}
