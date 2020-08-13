package entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import Elements.ElementPlayer;
import tilegame.CollisionTree;
import tilegame.Handler;

public class EntityManager {

	private Handler handler;
	private ElementPlayer player;

	public ArrayList<Entity> entities;

	//Optimize comparator
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getWidth() * a.getHeight() < b.getWidth() * b.getHeight())
				return 1;
			return -1;
		}
		
	};
	
	public EntityManager(Handler handler, ElementPlayer player)
	{
		this.handler = handler;
		this.player = player;
		
		entities = new ArrayList<Entity>();
		
		Entity[] map = handler.getWorld().getMap();
		for(int i = 0; i < map.length; i++) {
			addEntity(map[i]);
		}
		
		addEntity(player);
	}
	
	public void tick() 
	{
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		
		entities.sort(renderSorter);
	}
	
	public void render(Graphics g) {
		for( Entity e : entities) {
			e.render(g);
		}

	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
		
	// GETTERS & SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ElementPlayer getPlayer() {
		return player;
	}

	public void setPlayer(ElementPlayer player) {
		this.player = player;
	}


}
