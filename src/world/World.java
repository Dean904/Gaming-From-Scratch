package world;

import java.awt.Graphics;
import java.util.ArrayList;

import Elements.ElementPlayer;
import Elements.Necromancer;
import Tiles.Tile;
import entities.Blob;
import entities.Entity;
import entities.EntityManager;
import entities.Ground;
import tilegame.CollisionTree;
import tilegame.Game;
import tilegame.Handler;
import tilegame.SpawnController;
import tilegame.Utils;

public class World {

	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private Entity[] map;
	
	//Entities
	private EntityManager entityManager;
	private CollisionTree collisionTree;
	private SpawnController spawnController;

	public World(Handler handler, int width, int height)
	{
		this.handler = handler;
		handler.setWorld(this);
		
		loadWorld(width, height);
		
		//Entities
		entityManager = new EntityManager(handler, new ElementPlayer(handler, 100, 100));
		collisionTree= new CollisionTree(handler);
		spawnController = new SpawnController(handler);
			
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}

	public void tick() {
		
		entityManager.tick();
		spawnController.tick();
		
		collisionTree.tick();
	
	}
	
	public void render(Graphics g) {
		
		int pos = (int) entityManager.getPlayer().getX() / handler.getWidth();
		int length = width * 32 / handler.getWidth();

	//	System.out.println("pos: " + pos);
		
		map[pos].render(g);
		if(pos < length - 1)
		map[pos + 1].render(g);
		if(pos < 0)
		map[pos - 1].render(g);
		
		entityManager.render(g);
		// Testing Render'ers
		collisionTree.render(g);
	}
	
	private void loadWorld(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.spawnX = 150;
		this.spawnY = 200;
			
		int length = width * 32 / handler.getWidth() + 1;
		map = new Entity[length];
		
		for(int i = 0; i < length; i++) {
			map[i] = new Ground(handler, i * handler.getWidth(), height * 32 - 64);
		}
	}

	
	// GETTERS & SETTERS

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	
	public CollisionTree getCollisionTree() {
		return collisionTree;
	}

	public SpawnController getSpawnController() {
		return spawnController;
	}

	public Entity[] getMap() {
		return map;
	}
	
}

