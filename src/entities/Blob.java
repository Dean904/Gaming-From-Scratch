package entities;

import java.awt.Graphics;

import Elements.Creature;
import Tiles.Tile;
import graphics.Assets;
import tilegame.Handler;

public class Blob extends Creature {

	public Blob(Handler handler, float x, float y, int team) {
		super(handler, x, y, Tile.Tile_Width, Tile.Tile_Height, team);
		
		health = 15;
		Max_Health = 15;
		alive = true;
		
		bounds.x = 4;
		bounds.y = 4;
		bounds.width = 24;
		bounds.height = 24;
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.blob, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	

}
