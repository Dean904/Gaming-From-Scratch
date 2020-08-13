package Tiles;

import java.awt.image.BufferedImage;
import graphics.Assets;

public class Water extends Tile {

	public Water(int id) {
		super(Assets.water, id);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	
	
}
