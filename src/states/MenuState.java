package states;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Assets;
import tilegame.Game;
import tilegame.Handler;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;

public class MenuState extends State{
	
	private UIManager uiManager;
	
	public MenuState(Handler handler)
	{
		super(handler);
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.addObject(new ImageButton(200, 200, 64, 64, Assets.start, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}}));
		
	}

	@Override
	public void tick() {
		// System.out.println(handler.getMouseManager().getMouseX() + " " + handler.getMouseManager().getMouseY());
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		
		uiManager.render(g);
		
	//	g.setColor(Color.RED);
	//	g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY() ,8, 8);
		
	}

}
