package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Tiles.Tile;
import graphics.Assets;
import tilegame.Game;
import tilegame.Handler;
import world.World;

public class GameState extends State {

	private World world;
	
	public boolean paused;
	
	public GameState(Handler handler) {
		super(handler);
		world = new World(handler, 240, 20);
		handler.setWorld(world);
		paused = false;
	}
	
	public void controller() {
		
		paused = handler.getKeyManager().esc;
		
	}
	
	
	@Override
	public void tick() {
		
		controller();
		
		if(!paused) {
			world.tick();
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		world.render(g);
//		Tile.tiles[0].render(g, 0,0);
	}
}
