/**
 * Implementation of an enemy type.
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Jeremy Brown
 * 
 */
public class EnemyType1 extends Enemy {
	
	/**
	 * Default velocity for the Type 1 Enemy
	 */
	public final static Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 3;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 50;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 500; 
	
	/**
	 * The enemy's type
	 */
	public static final int type = 1;
	
	/**
	 * The type of bullets the ship will fire
	 */
	public static final int bulletType = 1;
	
	/**
	 * The cooldown, in milliseconds, of the enemy's fire
	 */
	private transient int shotCooldown = 300;
	
	/**
	 * The time since the last shot was taken
	 */
	private transient long lastShotTime = 0;
	
	/**
	 * @param position the position of the enemy
	 * @param width the width of the enemy
	 * @param height the height of the enemy
	 * @param rotation the rotation of the enemy in degrees
	 * @param world the world instance
	 */
	public EnemyType1(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world) {
		super(position, width, height, rotation, gameWorld, world);
		this.gameWorld = gameWorld;
		setHealth(DEFAULT_HEALTH);
		
		createFixtureDef();
	}
	
	// TODO constructor for serialization
	/*
	public EnemyType1() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		// TODO world something init for bullets...
		setHealth(DEFAULT_HEALTH);
	}
	*/
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#fire()
	 */
	@Override
	public void fire() {
		if (TimeUtils.millis() - lastShotTime >= shotCooldown) {
			Bullet b = new BulletType1(new Vector2(position.x, position.y), 3, 2, 0, gameWorld, world, this);
			b.getPosition().x = position.x - b.width;
			b.getPosition().y = position.y + height / 2 - b.height / 2;
			b.getBody().setLinearVelocity(BulletType1.DEFAULT_VELOCITY);
			gameWorld.getBullets().add(b);
			
			lastShotTime = TimeUtils.millis();
			Gdx.app.log(SSJava.LOG, "Enemy fired a bullet!");	
		}
	}

	public void advance(Ship ship) {
		super.update();
//		position.lerp(ship.getPosition(), Gdx.graphics.getDeltaTime());
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#getType()
	 */
	@Override
	public int getType() {
		return type;
	}
	/**
	 * @return the shot cooldown time for the enemy, in milliseconds
	 */
	public int getShotCooldown() {
		return shotCooldown;
	}

	/**
	 * @param shotCooldown the shot cooldown time to set, in milliseconds
	 */
	public void setShotCooldown(int shotCooldown) {
		this.shotCooldown = shotCooldown;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Enemy Type 1";
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#die()
	 */
	@Override
	public void die() {
		gameWorld.getScoreKeeper().add(KILL_SCORE);
		super.die();
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
}
