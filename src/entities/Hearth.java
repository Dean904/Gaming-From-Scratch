package entities;

import java.awt.Graphics;

import Elements.Creature;
import graphics.Assets;
import states.GameOverState;
import states.State;
import tilegame.Handler;

public class Hearth extends Creature {

	public Hearth(Handler handler, float x, float y, int team) {
		super(handler, x, y, 32, 192, team);
		
		health = 1000;
		Max_Health = 1000;
		alive = true;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 32;
		bounds.height = 192;
	}
	
	@Override
	protected void die()
	{
		if(alive) 
		{
			alive = false;
			State.setState(new GameOverState(handler));
		}	
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.wall,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);	
	}

}
