package states;

import java.awt.Graphics;

import graphics.Assets;
import tilegame.Handler;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;
import world.World;

public class GameOverState extends State {
	
	private UIManager uiManager;
	private World world;

	public GameOverState(Handler handler) {
		super(handler);
		world = handler.getWorld();
				
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.addObject(new ImageButton(200, 200, 32, 32, Assets.start, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(new MenuState(handler));
			}}));
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		uiManager.render(g);
	}

	
	
}
