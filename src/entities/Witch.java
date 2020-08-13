package entities;

import java.awt.Color;
import java.awt.Graphics;

import Elements.Creature;
import Elements.RangedCreature;
import graphics.Assets;
import tilegame.Handler;

public class Witch extends RangedCreature {
	
	public Witch(Handler handler, float x, float y, int team) {
		super(handler, x, y, 77, 169, team);
		
		damage = 5;
		attackSpeed = .7;
		speed = 2.5f;
		
		healingPower = 15;
		range = 600;
		seekerX = x + range;
		
		if(team == 1) 
		{
			speed = 3;
		}
		else {
			speed = -3;
		}
		
		bounds.x = 20;
		bounds.y = 25;
		bounds.width = 40;
		bounds.height = 120;
	}

	@Override
	public void tick() {
		moveY();
		if(state == 0) 
		{
			seek();
			moveX();
		}
		else if(state == 1) {
			healing();
		}	
		else if(state == 2) {
			seek();
			attack();
		}
	}

	@Override
	public void render(Graphics g)
	{
		if(team == 2) 
			g.drawImage(Assets.witch,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		else
			g.drawImage(Assets.witch_left,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

		// Draws BoundingBox red
		g.setColor(Color.red);
		g.drawRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		g.setColor(Color.BLUE);
		g.fillRect((int) (seekerX - handler.getGameCamera().getxOffset()),(int) (200 - handler.getGameCamera().getyOffset()), 10, 10);
		//	
	}
	

}
