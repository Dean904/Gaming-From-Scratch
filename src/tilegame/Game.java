package tilegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import display.Display;
import graphics.Assets;
import graphics.GameCamera;
import graphics.ImageLoader;
import graphics.SpriteSheet;
import input.KeyManager;
import input.MouseManager;
import states.GameState;
import states.MenuState;
import states.State;
import world.World;

public class Game implements Runnable {

	private Display display;
	public int width, height;	// Width and Height of Camera
	public String title;
	
	private Thread thread;
	private Boolean running = false;
	
	private BufferStrategy bs;	// controls buffer mode
	private Graphics g;
	
	// Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	// States
	public State gameState;	
	
	// Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;
	
	// Constructor
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
	}
		
	// ~~~~~~~~~ Thread functions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private void tick() {
		
		keyManager.tick();
		
		if(State.getState() != null) {
			State.getState().tick();
		}
	}
	
	private void render() {
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		/*
		g.setColor(Color.red);		// sets all future objects to red
		g.fillRect(30, 60, 30, 60);
		g.drawImage(Assets.player, 0, 0, null);
		*/
		
		if(State.getState() != null) {
			State.getState().render(g);
		}
		
		bs.show();
		g.dispose();
	}
	
	// INIT / RUN
	private void init() {	// run once at run() initializes graphics etc
		
		display = new Display(title, width, height);
		display.getCanvas().createBufferStrategy(3);
		display.getFrame().addKeyListener(keyManager);
		
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);

		bs = display.getCanvas().getBufferStrategy();
		
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler);
		
		gameState = new GameState(handler);		
		
		State.setState(new MenuState(handler));
	}
	
	public void run() {
		
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
//		long timer = 0;
//		int ticks = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
//			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
		//		ticks++;
				delta--;
			}
			
		//	if(timer >= 1000000000){
		//		System.out.println("Ticks and Frames: " + ticks);
		//		ticks = 0;
		//		timer = 0;
		//	}
		}
		
		stop();
	}
	

	
	// START / STOP
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();	// automatically calls run();
	}
	
	public synchronized void pause() {
		running = false;
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	


	// get & set 

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}

}
