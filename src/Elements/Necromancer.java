package Elements;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Assets;
import tilegame.Handler;

public class Necromancer extends Creature {
		
	public Necromancer(Handler handler, int x, int y, int team) {
		super(handler, x, y, 375, 250, team);
		
		damage = 20;
		attackSpeed = 2;
		
		if(team == 1) 
		{
			speed = 3;
			bounds.x = 30;
		}
		else {
			speed = -3;
			bounds.x = 180;
		}
		
		bounds.y = 50;
		bounds.width = 175;
		bounds.height = 200;
	}
	
	@Override
	public void tick() {
		if(state == 0)
			move();
		else if(state == 1)
			melee();
	}

	@Override
	public void render(Graphics g)
	{
		if(team == 2) 
			g.drawImage(Assets.Necromancer,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		else
			g.drawImage(Assets.Necromancer_Left,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

		// Draws BoundingBox red
			g.setColor(Color.red);
			g.drawRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		//
	}


}
