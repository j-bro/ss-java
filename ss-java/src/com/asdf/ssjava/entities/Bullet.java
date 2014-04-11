/**
 * Entity model for a bullet.
 * Adds starting velocity which can be in any direction, but will most likely be in that of the ship's velocity OR only horizontal.
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

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
	 * @param position the position of the bullet
	 * @param width the width of the bullet
	 * @param height the height of the bullet
	 * @param rotation the rotation of the bullet in degrees
	 */
	public Bullet(Vector2 position, float width, float height, float rotation, AbstractEntity shooter) {
		super(position, width, height, rotation);
		this.shooter = shooter;
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime())); 
		super.update();
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		dead = true;
	}

	public abstract int getType();	
	public abstract int getDamage();
	public abstract AbstractEntity getShooter();
	public abstract Vector2 getDEFAULT_VELOCITY();
	
}
