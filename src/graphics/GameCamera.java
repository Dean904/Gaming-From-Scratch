package graphics;

import Tiles.Tile;
import entities.Entity;
import tilegame.Game;
import tilegame.Handler;

public class GameCamera {
	
	private float xOffset, yOffset;
	private Handler handler;
	
	
	public GameCamera(Handler handler) {
		this.handler = handler;
		xOffset = 1;
		yOffset = 1;
	}
	
	public void checkBlankSpace() {
		if(xOffset < 0) 
		{
			xOffset = 0;
		}
		else if(xOffset > handler.getWorld().getWidth() * Tile.Tile_Width - handler.getWidth()) 
		{
			xOffset = handler.getWorld().getWidth() * Tile.Tile_Width - handler.getWidth();
		}
		if(yOffset < 0) {
			yOffset = 0;
		}
		else if(yOffset > handler.getWorld().getHeight() * Tile.Tile_Height - handler.getHeight())
		{
			yOffset = handler.getWorld().getHeight() * Tile.Tile_Height - handler.getHeight();
		}
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}
	
	public void move(float x, float y) {
		yOffset += y;
		xOffset += x;
		checkBlankSpace();
	}
	
	// GET & SET
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
	
	
}
