package entities;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;

public class Temp extends Entity{
	
	public Temp(Handler handler, float x, float y, int team) {
		super(handler, x, y, 0, 0, 0);
		this.team = team;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		// Draws BoundingBox red
		g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),(int) (y + bounds.y + 100 - handler.getGameCamera().getyOffset()), 3, 3);
		//
	}

	
	
}
