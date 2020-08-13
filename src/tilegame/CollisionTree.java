package tilegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import entities.Entity;
import world.World;

public class CollisionTree {

	private static Handler handler;
	
	private int Max_Objects = 5;	// Max objects a node can hold ( in ArrayList)
	private int Max_Levels = 3;
	
	private static CollisionTree root;
	private int level;
	private ArrayList<Entity> objects;
	private Rectangle bounds;
	
	public CollisionTree[] nodes;
	
	public CollisionTree(Handler handler) {
		this.handler = handler;
		root = this;
		level = 0;
		bounds = new Rectangle(0, 0, handler.getWorld().getWidth() * 32, handler.getWorld().getHeight() * 32);
		objects = new ArrayList<Entity>();
		nodes = new CollisionTree[3];
	}
	
	private CollisionTree(int level, Rectangle bounds) {
		this.level = level;
		this.bounds = bounds;
		objects = new ArrayList<Entity>();
		nodes = new CollisionTree[3];
	}
	
	public void tick()
	{
		root.clear();
		ArrayList<Entity> temp = handler.getWorld().getEntityManager().entities;
		for(int i = 0; i < temp.size(); i++) {	//Adds neutral entities
			root.insert(temp.get(i));
		}
	}
	
	public void clear() {	// Clears the tree
		objects.clear();
		for(int i = 0; i < nodes.length; i++) {
			if(nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	private void split() {
		
	//	System.out.print("SPLITTING \n");
		
		int subWidth = (int) bounds.width / 3;
		int height = bounds.height;
		int x = (int) bounds.x;
		int y = (int) bounds.y;
		
		nodes[0] = new CollisionTree(level + 1, new Rectangle(x, y, subWidth, height));
		nodes[1] = new CollisionTree(level + 1, new Rectangle(x + subWidth, y, subWidth, height));
		nodes[2] = new CollisionTree(level + 1, new Rectangle(x + subWidth * 2, y, subWidth, height));
		
	//	System.out.println("Level: " + level + " 0: " +  x + " 1: " + Math.addExact(x, subWidth) + " 2: " + Math.addExact(x, subWidth * 2));
		
	}
	
	private int getIndex(Entity e) {
		
		Rectangle rect = e.getBounds();
		float x = e.getX();
		
		int index = -1;
		int subWidth = (int) bounds.width / 3;
		
		double boundary1 = bounds.x + subWidth;
		double boundary2 = bounds.x + subWidth * 2;
		double boundary3 = bounds.x + subWidth * 3;
		
		
		if(e.getTeam() == 0) 
		{
			if(x+rect.x < boundary1 && x+rect.x + rect.width < boundary1) {
				index = 0;
			}
			else if(x+rect.x < boundary2 && x+rect.x + rect.width < boundary2 && x+rect.x > boundary1) {
				index = 1;
			}
			else if(x+rect.x < boundary3 && x+rect.x + rect.width < boundary3 && x+rect.x > boundary2) {
				index = 2;
			}	
		}
		else if(e.getTeam() == 1)
		{
			if(x+rect.x + rect.width < boundary1) {
				index = 0;
			}
			else if(x+rect.x + rect.width < boundary2) {
				index = 1;
			}
			else if(x+rect.x + rect.width < boundary3) {
				index = 2;
			}	
		}
		else if(e.getTeam() == 2)
		{
			if(x+rect.x < boundary1) {
				index = 0;
			}
			else if(x+rect.x < boundary2) {
				index = 1;
			}
			else if(x+rect.x < boundary3) {
				index = 2;
			}
		}
		
		/*
		if(x+rect.x < boundary1 && x+rect.x + rect.width < boundary1) {
			index = 0;
		}
		else if(x+rect.x < boundary2 && x+rect.x + rect.width < boundary2 && x+rect.x > boundary1) {
			index = 1;
		}
		else if(x+rect.x < boundary3 && x+rect.x + rect.width < boundary3 && x+rect.x > boundary2) {
			index = 2;
		}
		*/
		return index;
	}
	
	public void insert(Entity e) {
				
		if(nodes[0] != null) 
		{	// Checks for child nodes
			int index = getIndex(e);
			if(index != -1) 
			{	// If rect fits in child node, insert
				nodes[index].insert(e);
				return;
			}
		}
		
		// No child nodes, so at leaf, add rect to objects
		objects.add(e);
		
		if(objects.size() >= Max_Objects && level <= Max_Levels) {	// Check if ArrayList exceeds size standards
			if(nodes[0] == null) {	// Create new child nodes if necessary
				split();
			}
			
			int i = 0;
			while(i < objects.size()) {	// Moves pertinent nodes to child nodes
				int index = getIndex(objects.get(i));
				if(index != -1) {
					nodes[index].insert(objects.remove(i));
				}
				else {
					i++;
				}
			}
		}
	}
	
	public ArrayList<Entity> retrieve(Entity e) {
		ArrayList<Entity> collisions = new ArrayList<Entity>();
		return retrieve(collisions, e);
	}
	
	public ArrayList<Entity> retrieve(ArrayList<Entity> returnObjects, Entity e) {
		int index = getIndex(e);
		if(index != -1 && nodes[0] != null) {	// If e belongs in child node, retrieve child node
			nodes[index].retrieve(returnObjects, e);
		}
		
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).getTeam() != e.getTeam()) {
				returnObjects.add(objects.get(i));
			}
		}
		
	//	returnObjects.remove(e);
		
		return returnObjects;
	}
	
	public ArrayList<Entity> retrieveFriends(Entity e) {
		ArrayList<Entity> collisions = new ArrayList<Entity>();
		return retrieveFriends(collisions, e);
	}

	public ArrayList<Entity> retrieveFriends(ArrayList<Entity> returnObjects, Entity e) {
		int index = getIndex(e);
		if(index != -1 && nodes[0] != null) {	// If e belongs in child node, retrieve child node
			nodes[index].retrieveFriends(returnObjects, e);
		}
		
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).getTeam() == e.getTeam()) {
				returnObjects.add(objects.get(i));
			}
		}
		
	//	returnObjects.remove(e);
		
		return returnObjects;
	}
	
	public ArrayList<Entity> retrieveEnemies(Entity e) {
		ArrayList<Entity> collisions = new ArrayList<Entity>();
		return retrieveEnemies(collisions, e);
	}
	public ArrayList<Entity> retrieveEnemies(ArrayList<Entity> returnObjects, Entity e) {
		int index = getIndex(e);
		if(index != -1 && nodes[0] != null) {	// If e belongs in child node, retrieve child node
			nodes[index].retrieveEnemies(returnObjects, e);
		}
		
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).getTeam() != e.getTeam() && objects.get(i).getTeam() != 0) {
				returnObjects.add(objects.get(i));
			}
		}
		
	//	returnObjects.remove(e);
		
		return returnObjects;
	}
	
	public void render(Graphics g) {
		
		if(nodes[0] != null) {
			for(int i = 0; i < 3; i++) {
				nodes[i].render(g);
			}
		}
		
		g.setColor(Color.red);
		g.fillRect((int)(bounds.x - handler.getGameCamera().getxOffset()), (int) (bounds.y + bounds.height - 50 - handler.getGameCamera().getyOffset()), 15, 15);
		
	}
	
}
