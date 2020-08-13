package tilegame;

import graphics.GameCamera;
import input.KeyManager;
import input.MouseManager;
import world.World;

public class Handler {

	private World world;
	private Game game;
	
	// Constructor
	public Handler(Game game) {
		this.game = game;
	}
	
	// GETTERS & SETTERS
	public int getWidth() {	// Returns game.width
		return game.width;
	}
	
	public int getHeight() {
		return game.height;
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}

	
}
