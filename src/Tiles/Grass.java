package Tiles;

import java.awt.image.BufferedImage;

import graphics.Assets;

public class Grass extends Tile {

	public Grass(int id) {
		super(Assets.grass, id);
	}

	@Override
	public boolean isSolid() {
		return false;
	}
	
	
}
