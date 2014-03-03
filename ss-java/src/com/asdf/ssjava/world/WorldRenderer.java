/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
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
 * @author Simon Thompson
 *
 */

public class WorldRenderer {

	/**
	 * The World's instance
	 */
	World world;
	
	SpriteBatch batch;
	
	OrthographicCamera cam;
	
	Ship ship;
	EnemyType1 enemy1;
	
	Texture shipTexture;
	Texture enemy1Texture;
	
	float width, height;
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
		
		shipTexture = new Texture("data/textures/ship.png");
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		
		enemy1Texture = new Texture("data/textures/enemy1.png");
		enemy1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		ship = world.getShip();
		enemy1 = (EnemyType1) world.enemies.get(0);
		
		sr = new ShapeRenderer();
	}
	
	/**
	 * 
	 */
	public void render() {  
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);  
		
		cam.position.set(ship.getPosition().x, cam.position.y, 0);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
			// TODO fix rotate ship around origin
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() / 2, ship.getHeight() / 2 , ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(), shipTexture.getHeight(), false, false);
			for (Enemy e: world.enemies) {
				batch.draw(enemy1Texture, e.getPosition().x, e.getPosition().y, e.getWidth() / 2, e.getHeight() / 2 , e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, enemy1Texture.getWidth(), enemy1Texture.getHeight(), false, false);
			}
		batch.end();  
		
		// Shape renderer for hitboxes
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Line);
			sr.setColor(Color.CYAN);
			sr.rect(ship.getHitbox().x, ship.getHitbox().y, ship.getHitbox().width, ship.getHitbox().height);
			
			sr.setColor(Color.ORANGE);
			for (Enemy e: world.enemies) {
				sr.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
			}
		sr.end();
		
		
		Gdx.app.log("Ship", "x: " + ship.getPosition().x + " y: " + ship.getPosition().y);
	}

	/**
	 * 
	 */
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
		sr.dispose();
	}
}
