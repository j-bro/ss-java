/**
 * Manages the rendering and drawing dependencies of all entities present in the World.
 * Draws the HUD comprising of the score, the player's life points and the level progress.
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.Ship;
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
	Texture enemyType1Texture, enemyType2Texture, enemyType3Texture;
	Texture bulletType1Texture, bulletType2Texture, bulletType3Texture;
	Texture speedOfLightTexture, healthUpTexture;
	
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
		
		// TODO potentially fix this square/rectangle
		width = Gdx.graphics.getWidth() / 20;
		height = Gdx.graphics.getHeight() / 20;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		shipTexture = new Texture("data/textures/player_ship.png");
		shipTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
//		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		enemyType1Texture = new Texture("data/textures/enemy1.png");
		enemyType1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletType1Texture = new Texture("data/textures/bullet_strip.png");
		bulletType1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		ship = world.getShip();
		
		sr = new ShapeRenderer();
	}
	
	/**
	 * 
	 */
	public void render() {  
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);  
		
		cam.position.set(ship.getPosition().x + 20, cam.position.y, 0);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
			for (Obstacle o: world.obstacles) {
				
			}
			for (Enemy e: world.enemies) {
				Texture enemyTexture = enemyType1Texture;
				/* TODO fix
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
						
//						enemyTexture = enemyType1Texture;
						break;
				}
				*/
				batch.draw(enemyTexture, e.getPosition().x, e.getPosition().y, e.getWidth() / 2, e.getHeight() / 2 , e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, enemyTexture.getWidth(), enemyTexture.getHeight(), false, false);
			}
			for (Bullet b: world.bullets) {
				Texture bulletTexture = null;
				float srcX;
				float srcY;
				float srcWidth;
				float srcHeight;
				switch(b.getType()) {
				case 1:
					bulletTexture = bulletType1Texture;
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
//				batch.draw(bulletTexture, b.getPosition().x, b.getPosition().y, b.getWidth() / 2, b.getHeight() / 2 , b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 7, 15, 50, 33, false, false);
				batch.draw(bulletTexture, b.getPosition().x, b.getPosition().y, b.getWidth() / 2, b.getHeight() / 2 , b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 7, 15, 50, 33, false, false);
			}
			for (Powerup p: world.powerups) {
				
			}
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() / 2, ship.getHeight() / 2, ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 8, 4, 48, 24, false, false);
		batch.end();  
		
		// Shape renderer for hitboxes
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Line);
			sr.setColor(Color.RED);
			sr.rect(ship.getHitbox().x, ship.getHitbox().y, ship.getHitbox().width, ship.getHitbox().height);
			
			sr.setColor(Color.ORANGE);
			for (Enemy e: world.enemies) {
				sr.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
			}
			
			sr.setColor(Color.PINK);
			for (Bullet b: world.bullets) {
				sr.rect(b.getHitbox().x, b.getHitbox().y, b.getHitbox().width, b.getHitbox().height);
			}
		sr.end();
		
//		Gdx.app.log("Ship", "x: " + ship.getVelocity().x + " y: " + ship.getVelocity().y);
	}

	/**
	 * 
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
