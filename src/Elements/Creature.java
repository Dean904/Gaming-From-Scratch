package Elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import Tiles.Tile;
import entities.Entity;
import entities.Temp;
import tilegame.Handler;

public abstract class Creature extends Entity {
	
	public static final int Default_Health = 100;
	public static final float Default_Speed = 3;
	public int Max_Health;
	
	protected int state = 0;
	protected int health;
	protected boolean alive;
	
	protected int attackTick = 0;
	protected int damage;
	protected double attackSpeed;
	
	protected float speed;
	protected float maxSpeed = 4;
	protected float verticalSpeed = 0;
	protected float gravity = .27f;

	protected boolean grounded = false;
	
	protected Creature lastCollision = null;

	// Team 0 = Neutral
	// Team 1 = Player
	// Team 2 = Enemy
	public Creature(Handler handler, float x, float y, int width, int height, int team)
	{
		super(handler, x, y, width, height, team);
		health = Default_Health;
		Max_Health = Default_Health;
		speed = Default_Speed;
		alive = true;
	}
	
	// Attacking
	public void melee() 
	{
		attackTick++;
		Entity e = collision((int) (x + speed),(int) (y + verticalSpeed));
		if(e == null || e.getTeam() == 0) 
		{
			state = 0;
			return;
		}
		
		if(attackTick >= 60 * attackSpeed) 
		{
			attackTick = 0;

		//	System.out.println("ATTACKING @ " + e);
			boolean killed = ((Creature) e).hurt(damage);
			if(killed)
			{
				state = 0;
				lastCollision = null;
			}				
		}
	}
	
	public boolean heal(int hp)
	{
		health += hp;
		if(health >= Max_Health) {
			health = Max_Health;
			return true;
		}
		return false;
	}
	
	public boolean hurt(int damage) 
	{
		health -= damage;
		if(health <= 0)
		{
			die();
			return true;
		}
		return false;
	}
	
	protected void die()
	{
		if(alive) 
		{
			alive = false;
			handler.getWorld().getEntityManager().removeEntity(this);
		}	
	}
	
	// Movement
	public void move()
	{
		attackTick = 0;

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
	//Collisions
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
				state = 1;
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
				//	System.out.println("Collision: " + this + " AND " + e);
					lastCollision = (Creature) e;
					state = 1;
				}
				return e;
			}
		}	
		return null;	
	}
	

	// Get & Set
	public void setHealth(int hp) {
		health = hp;
		if(hp > Max_Health) {
			Max_Health = hp;
		}
	}

	public int getHealth() {
		return health;
	}

}
