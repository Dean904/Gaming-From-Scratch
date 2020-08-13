package Tiles;

import java.awt.image.BufferedImage;

import graphics.Assets;

public class StoneWall extends Tile {

	public StoneWall(int id) {
		super(Assets.stoneWall, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	
	
}
