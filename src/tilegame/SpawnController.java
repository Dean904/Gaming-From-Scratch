package tilegame;

import java.awt.Point;

import Elements.Necromancer;
import entities.Blob;
import entities.Entity;
import entities.EntityManager;
import entities.Hearth;
import entities.Wall;
import entities.Witch;

public class SpawnController {

	Handler handler;
	EntityManager entityManager;
	private int tickCount = 599;
	
	public int leftSpawnX;
	public int leftSpawnY;
	public int rightSpawnX;
	public int rightSpawnY; 
	
	public SpawnController(Handler handler) 
	{
		this.handler = handler;
		entityManager = handler.getWorld().getEntityManager();
		
		leftSpawnX = 300;
		leftSpawnY = handler.getWorld().getHeight() * 32 - 500;
		
		rightSpawnX = handler.getWorld().getWidth() * 32 - 500;
		rightSpawnY = handler.getWorld().getHeight() * 32 - 500;	
		
		entityManager.addEntity(new Blob(handler, 1000, 250, 2));
		entityManager.addEntity(new Blob(handler, 3200, 250, 2));
		entityManager.addEntity(new Blob(handler, 6400, 250, 2));
		
		Spawn(new Wall(handler, 0, handler.getWorld().getHeight() * 32 - 64 - 192));
		Spawn(new Wall(handler, handler.getWorld().getWidth() * 32 - 32, handler.getWorld().getHeight() * 32 - 64 - 192));
		
		Spawn(new Hearth(handler, 40, handler.getWorld().getHeight() * 32 - 64 - 192, 1));
		Spawn(new Hearth(handler, handler.getWorld().getWidth() * 32 - 72, handler.getWorld().getHeight() * 32 - 64 - 192, 2));

		
	//	Spawn(new Necromancer(handler, leftSpawnX + 400, leftSpawnY, 1));
		Spawn(new Witch(handler, leftSpawnX, leftSpawnY, 1));
		
	//	Spawn(new Necromancer(handler, rightSpawnX + 400, rightSpawnY, 2));
	//	Spawn(new Necromancer(handler, rightSpawnX, rightSpawnY, 2));

	}
	
	public void Spawn(Entity e) 
	{
		entityManager.addEntity(e);
	}
	

	public void tick() 
	{		
		tickCount++;
		if(tickCount > 600) 
		{			
			System.out.println("SPAWNING ");
			Spawn(new Necromancer(handler, leftSpawnX, leftSpawnY, 1));
			Spawn(new Necromancer(handler, rightSpawnX, rightSpawnY, 2));
			Spawn(new Blob(handler, (float) (leftSpawnX + Math.random() * 50), (int) (leftSpawnY + 300 + Math.random() * 20), 2));

			tickCount = 0;		
		}	
	}
	
	
	
}
