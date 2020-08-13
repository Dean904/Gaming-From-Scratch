package Elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import Tiles.Tile;
import entities.Entity;
import entities.Temp;
import graphics.Assets;
import tilegame.Handler;

public class ElementPlayer extends RangedCreature
{

	protected int health = 100;
	
	protected float speed = 0;
	protected float verticalSpeed = 0;
	protected float maxSpeed = 4;
	
	protected float gravity = .27f;
	protected float friction = .2f;
	
	protected float acceleration = .15f;
	protected float accelerationLeft = 0;
	protected float accelerationRight = 0;
	
	protected boolean crouching;
	private boolean facingFront;
	
	public int seekDist;
	private int seekIte;

	public ElementPlayer(Handler handler, float x, float y) {
		super(handler, x, y, 115, 133, 1);
		
		team = 1;
		bounds.x = 58;
		bounds.y = 2;
		bounds.width = 50;
		bounds.height = 128;
		
		damage = 55;
		range = 30;		
		
		facingFront = true;
		seekDist = (handler.getWorld().getWidth() * 32 / 81);
		seekIte = 500 / seekDist;		
		
		System.out.println("D: " + seekDist + " I: " + seekIte);
	}
	
	public Creature seekTar() 
	{
		if(facingFront) 
		{
						
			// Checks immediate area
			Rectangle attackArea = new Rectangle((int) (x + (width * 3/4)), (int) (y - 20), (width / 2) + 30, 120);
			ArrayList<Entity> entities = handler.getWorld().getCollisionTree().retrieveEnemies(this);
		//	System.out.print(entities.size());
			for(int i = 0; i < entities.size(); i++) 
			{
				Creature e = (Creature) entities.get(i);
				Rectangle eBounds = new Rectangle(e.getBounds());
				eBounds.x += e.getX();
				eBounds.y += e.getY();
				
				if(eBounds.intersects(attackArea)) {
					return e;
				}
			}
			
			// Checks area in front (possible tree boundaries)
		//	System.out.print(" ~ Seeking More ");
			Entity seeker = new Temp(handler, (float) x + width + range, 200, team);
			entities = handler.getWorld().getCollisionTree().retrieveEnemies(seeker);	
		//	System.out.print(entities.size() + "\n");
			for(int i = 0; i < entities.size(); i++) 
			{
				Creature e = (Creature) entities.get(i);
				Rectangle eBounds = new Rectangle(e.getBounds());
				eBounds.x += e.getX();
				eBounds.y += e.getY();
				
				if(eBounds.intersects(attackArea)) {
					return e;
				}
			}	
		}
		else
		{
			// Checks immediate area
			// Backwards needs refining once character flip flops depending on movement
			Rectangle attackArea = new Rectangle((int) x, (int) (y - 20), width / 2 + 30, 120);
			ArrayList<Entity> entities = handler.getWorld().getCollisionTree().retrieveEnemies(this);
	//		System.out.print(entities.size());
			for(int i = 0; i < entities.size(); i++) 
			{
				Creature e = (Creature) entities.get(i);
				Rectangle eBounds = new Rectangle(e.getBounds());
				eBounds.x += e.getX();
				eBounds.y += e.getY();
				
				if(eBounds.intersects(attackArea)) {
					target = e;
					return e;
				}
			}
			
			// Checks area in front (possible tree boundaries)
		//	System.out.print(" ~ Seeking More ");
			for(int z = seekIte; z >= 0; z--) 
			{
				Entity seeker = new Temp(handler, (float) x - seekDist * z, 200, team);
			//	System.out.print("X: " + x + "SeekerX: " + (x - seekDist * z) + "Z: " + z + "Size: ");
				entities = handler.getWorld().getCollisionTree().retrieveEnemies(seeker);	
			//	System.out.print(entities.size() + "\n");
				for(int i = 0; i < entities.size(); i++) 
				{
					Creature e = (Creature) entities.get(i);
					Rectangle eBounds = new Rectangle(e.getBounds());
					eBounds.x += e.getX();
					eBounds.y += e.getY();
					
					if(eBounds.intersects(attackArea)) {
						return e;
					}
				}	
			}
		}
		
		return null;
	}
	
	
	@Override
	protected void attack() {
		
		if(attackTick > 90 ) {
			attackTick = 0;

			target = seekTar();
			if(target != null)
			{
				System.out.println(" PLAYER ATTACK @ " + target);
				boolean killed = target.hurt(damage);
				if(killed || !target.alive)
					target = null;
			}				
			
		}	
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
			respawn();
		}
	}
	
	private void respawn() {
		handler.getWorld().getEntityManager().addEntity(new ElementPlayer(handler, 100, 100));
	}
	@Override
	public void tick() {
		attackTick++;
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
	}
	
	public void move() {
	//	System.out.println("Speed: " + speed);
	//	System.out.println("Vert Speed: " + verticalSpeed);
		friction();
		moveX();
		moveY();
	}
	
	private void getInput() {
		accelerationLeft = 0;
		accelerationRight = 0;
		
		if(handler.getKeyManager().space)
			attack();
		if(handler.getKeyManager().up)
			jump();
		if(handler.getKeyManager().down)
			crouching = true;
		if(handler.getKeyManager().left)
		{
			accelerationLeft = -acceleration;
			facingFront = false;
		}
		if(handler.getKeyManager().right) 
		{
			accelerationRight = acceleration;				
			facingFront = true;
		}
				
	}
	
	public void moveX() {
		
		// Acceleration
		if(accelerationLeft < 0) 
		{
			if(speed + accelerationLeft > -maxSpeed)
				speed += accelerationLeft;
			else
				speed = -maxSpeed;

		}
		else if(accelerationRight > 0) 
		{	
			if(speed + accelerationRight < maxSpeed)
				speed += accelerationRight;
			else
				speed = maxSpeed;
		}
		
		// Move & Collision
		int tx = (int) (x + speed);
		if(speed < 0) {
			// Left Collision Check
			if(collision(tx, y) == null){
				x += speed;
			}
			else {
				speed = 0;
			}
		}
			
		else if(speed > 0) {
			// Right Collision Check
			if(collision(tx, y) == null){
			x += speed;
			}
			else {
				speed = 0;
			}
		}
		
	}
	
	public void moveY() {
			
			if(verticalSpeed + gravity < maxSpeed) 
				verticalSpeed += gravity;
			else
				verticalSpeed = maxSpeed;
		
			int ty = (int) (y + verticalSpeed);
			
			if(collision(x, ty) == null) {
					y += verticalSpeed;
			}
			else {
				grounded = true;
			}			
	}
	
	public void friction() {
		
		if(accelerationLeft == 0 && accelerationRight == 0) {
			
			if(speed > 0) {
				if(speed - friction > 0)
					speed -= friction;
				else
					speed = 0;
			}
			else if(speed < 0) 
				if(speed + friction < 0)
					speed += friction;
				else
					speed = 0;
		}
	}
	
	public void jump() {
		
		if(grounded) {
			grounded = false;
			verticalSpeed = -7.7f;
				
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.ElementPlayer,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		// Draws BoundingBox red
			g.setColor(Color.red);
			g.drawRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
			g.setColor(Color.BLUE);
	
		//
			if(facingFront)
				g.drawRect( (int) (x + (width * 3/4) - handler.getGameCamera().getxOffset()), (int) (y - 20 - handler.getGameCamera().getyOffset()), (width / 2) + 30, 120);
			else
				g.drawRect( (int) (x - handler.getGameCamera().getxOffset()), (int) (y - 20 - handler.getGameCamera().getyOffset()), 30 + width / 2, 120);
			
	

	}

	
	
	public boolean isCrouching() {
		return crouching;
	}

	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
	}

	
	
}
