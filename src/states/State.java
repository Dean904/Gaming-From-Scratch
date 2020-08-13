package states;

import java.awt.Graphics;

import tilegame.Game;
import tilegame.Handler;

public abstract class State {
	
	// ABSTRACT -------------------------------
	public abstract void tick();
	public abstract void render(Graphics g);
	// ----------------------------------------
	
	
	private static State currentState = null;
	
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	// CLASS
	
	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	
}
