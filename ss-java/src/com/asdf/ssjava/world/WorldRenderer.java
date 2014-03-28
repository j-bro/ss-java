/**
 * Manages the rendering and drawing dependencies of all entities present in the World.
 * Draws the HUD comprising of the score, the player's life points and the level progress.
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Planet;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.entities.SpaceRock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Jeremy Brown
 *
 */

public class WorldRenderer {

	/**
	 * The game's instance
	 */
	SSJava game;
	/**
	 * The World's instance
	 */
	World world;
	
	/**
	 * The sprite batch responsible for drawing all elements in the world
	 */
	SpriteBatch batch;
	
	/**
	 * The camera 
	 */
	OrthographicCamera cam;
	
	/**
	 * The ship's instance
	 */
	Ship ship;
	
	/**
	 * Textures for the various elements 
	 */
	Texture shipTexture;
	Texture spaceRockTexture, asteroidTexture;
	Texture enemyType1Texture, enemyType2Texture, enemyType3Texture;
	Texture bulletType0Texture, bulletType1Texture, bulletType2Texture, bulletType3Texture;
	Texture speedOfLightTexture, healthUpTexture;
	Texture powerupHealthUpTexture, powerupSpeedOfLightTexture;
	Texture planetTexture;
	
	// TODO ...
	float width, height;
	
	/**
	 * Shape renderer for debugging ONLY!
	 */
	ShapeRenderer sr;
	
	/**
	 * Creates the world instance
	 * @param world
	 */
	public WorldRenderer(World world) {
		this.world = world;
		game = world.game;
		
		// TODO potentially fix this square/rectangle
		width = Gdx.graphics.getWidth() / 20;
		height = Gdx.graphics.getHeight() / 20;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		shipTexture = game.assetManager.get("data/textures/player_ship.png", Texture.class);
		shipTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
//		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		spaceRockTexture = game.assetManager.get("data/textures/space_rock.png", Texture.class);
		spaceRockTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		asteroidTexture = game.assetManager.get("data/textures/brick.png", Texture.class);
		asteroidTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		enemyType1Texture = game.assetManager.get("data/textures/enemy1.png", Texture.class);
		enemyType1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletType0Texture = game.assetManager.get("data/textures/bullet_strip.png", Texture.class);
		bulletType0Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupHealthUpTexture = game.assetManager.get("data/textures/healthUp.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupSpeedOfLightTexture = game.assetManager.get("data/textures/waterball.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		planetTexture = game.assetManager.get("data/textures/planet.png", Texture.class);
		planetTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// TODO extra textures
		bulletType1Texture = bulletType0Texture;
		bulletType2Texture = bulletType0Texture;
		
		ship = world.getShip();
		
		sr = new ShapeRenderer();
	}
	
	/**
	 * Render loop
	 */
	public void render() {  
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);  
		
		cam.position.set(ship.getPosition().x + 20, cam.position.y, 0);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
			for (Obstacle o: world.obstacles) {
				Texture obstacleTexture = null;
				if (o instanceof SpaceRock) {
					obstacleTexture = spaceRockTexture;
				}
				else if (o instanceof Asteroid) {
					obstacleTexture = asteroidTexture;
				}
				batch.draw(obstacleTexture, o.getPosition().x, o.getPosition().y, o.getWidth(), o.getHeight());
			}
			
			for (Enemy e: world.enemies) {
				Texture enemyTexture = null;
				switch(e.getType()) {
					case 1:
						enemyTexture = enemyType1Texture;
						break;
					case 2: 
						enemyTexture = enemyType2Texture;
						break;
					case 3:
						enemyTexture = enemyType3Texture;
						break;
					default:
						enemyTexture = enemyType1Texture;
						break;
				}
				batch.draw(enemyTexture, e.getPosition().x, e.getPosition().y, e.getWidth() / 2, e.getHeight() / 2 , e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, enemyTexture.getWidth(), enemyTexture.getHeight(), false, false);
			}
			
			for (Bullet b: world.bullets) {
				Texture bulletTexture = null;
				int srcX = 0, srcY = 0, srcWidth = 0, srcHeight = 0;
				switch(b.getType()) {
					case 0:
						bulletTexture = bulletType0Texture;
						srcX = 7;
						srcY = 15;
						srcWidth = 50;
						srcHeight = 33;
						break;
					case 1: 
						bulletTexture = bulletType1Texture;
						// TODO also temp
						srcX = 7;
						srcY = 15;
						srcWidth = 50;
						srcHeight = 33;
						break;
					case 2: 
						bulletTexture = bulletType2Texture;
						break;
					case 3:
						bulletTexture = bulletType3Texture;
						break;
					default:
						break;
				}
				batch.draw(bulletTexture, b.getPosition().x, b.getPosition().y, b.getWidth() / 2, b.getHeight() / 2 , b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), srcX, srcY, srcWidth, srcHeight, false, false);
			}
			
			for (Powerup p: world.powerups) {
				Texture powerupTexture = null;
				if (p instanceof PowerupHealthUp) {
					powerupTexture = powerupHealthUpTexture;
				}
				else if (p instanceof PowerupSpeedOfLight) {
					powerupTexture = powerupSpeedOfLightTexture;
				}
				batch.draw(powerupTexture, p.getPosition().x, p.getPosition().y, p.getWidth(), p.getHeight());
			}
			
			for (Obstacle g: world.gameChangers) {
				Texture planetTexture = null;
				if (g instanceof Planet) {
					planetTexture = this.planetTexture;
				}
				batch.draw(planetTexture, g.getPosition().x, g.getPosition().y, g.getWidth(), g.getHeight());
			}
			
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() / 2, ship.getHeight() / 2, ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 8, 4, 48, 24, false, false);
		batch.end();  
		
		if (SSJava.DEBUG) { 			
			// Shape renderer (hitboxes) for DEBUG only
			sr.setProjectionMatrix(cam.combined);
			sr.begin(ShapeType.Line);
			sr.setColor(Color.RED);
			sr.rect(ship.getHitbox().x, ship.getHitbox().y, ship.getHitbox().width, ship.getHitbox().height);
			
			sr.setColor(Color.LIGHT_GRAY);
			for (Obstacle o: world.obstacles) {
				sr.rect(o.getHitbox().x, o.getHitbox().y, o.getHitbox().width, o.getHitbox().height);
			}
			
			sr.setColor(Color.ORANGE);
			for (Enemy e: world.enemies) {
				sr.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
			}
			
			sr.setColor(Color.PINK);
			for (Bullet b: world.bullets) {
				sr.rect(b.getHitbox().x, b.getHitbox().y, b.getHitbox().width, b.getHitbox().height);
			}
			sr.end();
		}
	}

	/**
	 * Dispose method
	 */
	public void dispose() {
		batch.dispose();
		
		shipTexture.dispose();
		
		enemyType1Texture.dispose();
		enemyType2Texture.dispose();
		enemyType3Texture.dispose();
		
		bulletType1Texture.dispose();
		bulletType2Texture.dispose();
		bulletType3Texture.dispose();
		
		sr.dispose();
	}
}
