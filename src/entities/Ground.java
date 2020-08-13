package entities;

import java.awt.Graphics;

import graphics.Assets;
import tilegame.Handler;

public class Ground extends Entity {

	public Ground(Handler handler, float x, float y) {
		super(handler, x, y, handler.getWidth(), 64, 0);
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = handler.getWidth();
		bounds.height = 64;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.ground,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	
	
}
