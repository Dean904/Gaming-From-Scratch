package entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import tilegame.Game;
import tilegame.Handler;

public abstract class Entity {
	
	protected Handler handler;
	protected int team;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	
	protected float speed;
	protected float gravity;
	protected float acceleration;
	protected float friction;
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public Entity(Handler handler, float x, float y, int width, int height, int team) {
		
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.team = team;

		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	
	// GET & SET	
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getGravity() {
		return gravity;
	}
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	public float getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	public float getFriction() {
		return friction;
	}
	public void setFriction(float friction) {
		this.friction = friction;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public int getTeam() {
		return team;
	}
	
	
	
	
}
