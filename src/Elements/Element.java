package Elements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Element {

	//
	
	public static Element player;
	
	//
	
	public int Element_Width, Element_Height;
	protected BufferedImage texture;
	protected final int id;
	
	public abstract boolean isSolid();
	
	public Element(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, Element_Width, Element_Height, null);
		
		
	}
	
	public int getId() {
		return id;
	}
}
