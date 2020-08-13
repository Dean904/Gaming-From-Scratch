package Tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Tile {
	
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new Grass(0);
//	public static Tile dirtTile = new Dirt(1);
    public static Tile treeTile = new Tree(1);	
	public static Tile waterTile = new Water(2);
	public static Tile stoneWallTile = new StoneWall(3);
    
    
	public static final int Tile_Width = 32, Tile_Height = 32;
	
	protected BufferedImage texture;
	protected final int id;
	
	public abstract boolean isSolid();

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, Tile_Width, Tile_Height, null);
	}
	
	
	public int getId() {
		return id;
	}
	
	
	
}
