/**
 * Entity model for a bullet.
 * Adds starting velocity which can be in any direction, but will most likely be in that of the ship's velocity OR only horizontal.
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Jeremy Brown
 * 
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
	 * The health of a bullet (1, as it disappears as soon as it collides)
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * @param position the position of the bullet
	 * @param width the width of the bullet
	 * @param height the height of the bullet
	 * @param rotation the rotation of the bullet in degrees
	 */
	public Bullet(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world, AbstractEntity shooter) {
		super(position, width, height, rotation, gameWorld, world);
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

	public abstract int getType();	
	public abstract int getDamage();
	public abstract AbstractEntity getShooter();
	public abstract Vector2 getDEFAULT_VELOCITY();
	
}
