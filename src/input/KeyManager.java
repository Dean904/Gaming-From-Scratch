package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys;
	public boolean up, down, left, right, esc, space;
	
	public KeyManager(){
		keys = new boolean[256];
	}
	
	public void tick(){
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
		
		space = keys[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	//	System.out.println(e.getKeyChar());	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			if(esc == true) 
				esc = false;
			else
				esc = true;
		}
		else 
		{
			keys[e.getKeyCode()] = false;
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
