package entities;

import java.awt.Graphics;

import graphics.Assets;
import tilegame.Handler;

public class Wall extends Entity {

	public Wall(Handler handler, float x, float y) {
		super(handler, x, y, 32, 192, 0);
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 32;
		bounds.height = 192;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.wall,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	
	
}
