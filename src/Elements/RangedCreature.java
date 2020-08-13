package Elements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import entities.Entity;
import entities.Temp;
import tilegame.Handler;

public abstract class RangedCreature extends Creature {

	protected int range;
	protected int healingPower;
	
	protected double dx;
	protected double seekerX;
	
	protected Creature target = null;
	
	public RangedCreature(Handler handler, float x, float y, int width, int height, int team) {
		super(handler, x, y, width, height, team);
	}
	
	protected void healing() 
	{
		attackTick++;
		if(rangeCheck() && target.alive)
		{
			if(attackTick >= 60 * attackSpeed) 
			{
				attackTick = 0;
			//	System.out.println("HEALING @ " + target);
				boolean full = target.heal(healingPower);
				if(full) {
			//		System.out.println("HEALING COMPLETE ");
					target = null;
					state = 0;
				}
			}
		}
		else {
			target = null;
			state = 0;
		}
	}

	protected void attack()
	{
		attackTick++;
		if(rangeCheck())
		{
			if(attackTick >= 60 * attackSpeed) 
			{
				attackTick = 0;
		
			//	System.out.println("RANGED ATTACKING @ " + target);
				boolean killed = target.hurt(damage);
				if(killed || !target.alive)
				{
					state = 0;
					target = null;
				}				
			}	
		}
		else {
			target = null;
			state = 0;
		}
	}
	
	public void move()
	{
		moveX();
		moveY();
	}
	
	public void moveX() 
	{	
		int tx = (int) (x + speed);

		if((collision(tx, y) == null))
		{
			this.x += speed;
		}
	}
	
	public void moveY()
	{
		if(verticalSpeed + gravity < maxSpeed) 
			verticalSpeed += gravity;
		else
			verticalSpeed = maxSpeed;
	
		int ty = (int) (y + verticalSpeed);
		
		if((collision(x, ty) == null))
		{
			y += verticalSpeed;
		}
		else {
			grounded = true;
		}		
	}
	
	private boolean rangeCheck() 
	{
		if(Math.abs(target.getX() - x) < range) {
			return true;
		}	
		
		return false;	
	}
	
	public void seek() 
	{
		dx = range / 30;
		Entity seeker = new Temp(handler, (float) seekerX, 200, team);
		ArrayList<Entity> entities = handler.getWorld().getCollisionTree().retrieveFriends(seeker);		
	//	System.out.println("P: " + x + " S: " + seekerX + " Friendly Size: " + entities.size() + " " + dir + " R: " + range + " " + dx);
		if(entities.size() != 0) {
			for(int i = 0; i < entities.size(); i++) 
			{
				Creature e = (Creature) entities.get(i);
				float delta = Math.abs(e.getX() - x);
					if(delta < range) {
						if(e.getHealth() < e.Max_Health)
						{
							target = (Creature) entities.get(i);
							state = 1;
							return;
						}				
					}
			}
		}
		
		entities = handler.getWorld().getCollisionTree().retrieveEnemies(seeker);		
		for(int i = 0; i < entities.size(); i++) 
		{
			Creature e = (Creature) entities.get(i);
			float delta = Math.abs(e.getX() - x);
			if(delta < range) {
				target = (Creature) entities.get(i);
				state = 2;
				return;					
			}
		}
		
		double var = seekerX - x;		
		if(Math.abs(var) < dx)
			seekerX = x + range;
		else {
			if(var > 0) {
				seekerX -= 2 * var;
				seekerX += dx;
			}
			else {
				seekerX -= 2 * var;
				seekerX -= dx;
			}
		}
		
		
	}
	
	@Override
	protected Entity collision(float tx, float ty)
	{
		Rectangle eBounds;
		Rectangle tBounds = new Rectangle(bounds);
		tBounds.x += tx;
		tBounds.y += ty;
		
		if(lastCollision != null && lastCollision.alive)
		{
			eBounds = new Rectangle(lastCollision.getBounds());
			eBounds.x += lastCollision.getX();
			eBounds.y += lastCollision.getY();
			if(eBounds.intersects(tBounds)) 
			{
				return lastCollision;
			}
		}
		else {
			lastCollision = null;
		}
		
		ArrayList<Entity> entities = handler.getWorld().getCollisionTree().retrieve(this);		
		for(int i = 0; i < entities.size(); i++)
		{						
			Entity e = entities.get(i);
			eBounds = new Rectangle(e.getBounds());
			eBounds.x += e.getX();
			eBounds.y += e.getY();
							
			if(eBounds.intersects(tBounds))
			{
				if(e.getTeam() != 0)
				{
	//				System.out.println("Collision: " + this + " AND " + e);
					lastCollision = (Creature) e;
				}
				return e;
			}
		}	
		return null;	
	}
	
	
}
