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
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(2, 0);
	
	/**
	 * The enemy's default width, in game coordinates
	 */
	public static final float DEFAULT_WIDTH = 6;
	
	/**
	 * The enemy's default height, in game coordinates
	 */
	public static final float DEFAULT_HEIGHT = 3;
	
	/**
	 * The enemy's default rotation, in degrees
	 */
	public static final float DEFAULT_ROTATION = 180;
	
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
	 * The default cooldown, in milliseconds, of the enemy's fire
	 */
	public static final int DEFAULT_SHOT_COOLDOWN_MS = 1000;
	
	/**
	 * The enemy's type
	 */
	public static final int type = 1;
	
	/**
	 * The type of bullets the ship will fire
	 */
	public static final int bulletType = 1;
	
	/**
	 * The shot cool down time, in milliseconds, of the enemy's fire
	 */
	private int shotCooldown;
	
	/**
	 * The time since the last shot was taken
	 */
	private transient long lastShotTime = 0;
	
	/**
	 * The enemy's weight modifier
	 */
	public static final long ENEMY_TYPE_1_WEIGHT_MOD = 22800;
	
	/**
	 * @param position the position of the enemy
	 * @param width the width of the enemy
	 * @param height the height of the enemy
	 * @param rotation the rotation of the enemy in degrees
	 * @param gameWorld the world instance
	 * @param box2DWorld 
	 */
	public EnemyType1(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world) {
		super(position, width, height, rotation, gameWorld, world);
		setHealth(DEFAULT_HEALTH);
		shotCooldown = DEFAULT_SHOT_COOLDOWN_MS;
		createDef();
	}
	
	/**
	 * Constructor for serialization
	 */
	public EnemyType1() {
		super(null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION, null, null);
		setHealth(DEFAULT_HEALTH);
		shotCooldown = DEFAULT_SHOT_COOLDOWN_MS;
	}
	
	/**
	 * Constructor for level creator
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public EnemyType1(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation, null, null);
		setHealth(DEFAULT_HEALTH);
		shotCooldown = DEFAULT_SHOT_COOLDOWN_MS;
	}
	
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#fire()
	 */
	@Override
	public void fire() {
		if (TimeUtils.millis() - lastShotTime >= shotCooldown) {
			Bullet b = new BulletType1(new Vector2(position.x - width, position.y), 3, 2, 0, gameWorld, box2DWorld, this);
			b.getPosition().x = position.x - width / 2 - b.width;
			b.getPosition().y = position.y - b.height / 2;
			gameWorld.getBullets().add(b);
			
			lastShotTime = TimeUtils.millis();
			Gdx.app.log(SSJava.LOG, "Enemy fired a bullet!");	
		}
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {		
		
		float shipAngle = (float) (getBody().getAngle() - Math.PI);
		float mod = (float) (2 * Math.PI);
		float angleMod = (shipAngle < 0) ? (mod - (Math.abs(shipAngle) % mod) ) % mod : (shipAngle % mod);
		
		// TODO fix ship rotation
		if (angleMod < Math.PI) {
			float diff = angleMod;
			getBody().setAngularVelocity(-0.2f * diff);
		}
		else {
			float diff = mod - angleMod;
			getBody().setAngularVelocity(0.2f * diff);
		}
		
		
		fire();
		
		super.update();
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
	 * @see com.asdf.ssjava.entities.AbstractEntity#createDef()
	 */
	@Override
	public void createDef() {
		super.createDef();
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);
		body.setLinearVelocity(DEFAULT_VELOCITY);
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
