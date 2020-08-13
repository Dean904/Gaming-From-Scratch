package graphics;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;

	public static BufferedImage player, dirt, grass, stone, tree, water, stoneWall, blob, ElementPlayer, Necromancer, blackMagicBall
								,Necromancer_Left, ground, wall, witch, witch_left;
	
	public static BufferedImage[] start;
	
	public static void init() {
		
		SpriteSheet sheet0 = new SpriteSheet(ImageLoader.loadImage("/AngbandTk/dg_features32.gif"));
		SpriteSheet sheet1 = new SpriteSheet(ImageLoader.loadImage("/AngbandTk/dg_humans32.gif")); 
		SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("/AngbandTk/dg_effects32.gif")); 
		SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("/AngbandTk/dg_armor32.gif"));
		
		start = new BufferedImage[2];
		start[0] = sheet3.crop(0, 9);
		start[1] = sheet3.crop(3, 9);
		
		player = sheet1.crop(0, 0);
		grass = sheet0.crop(0, 5);
		tree = sheet0.crop(3, 4);
		water = sheet0.crop(3, 5);
		stoneWall = sheet0.crop(6, 6);
		blob = sheet2.crop(8, 3);
		blackMagicBall = sheet2.crop(1, 7);
		
		ElementPlayer = ImageLoader.loadImage("/Plasma_knight.png");
		Necromancer = ImageLoader.loadImage("/necromancer.gif");
		Necromancer_Left = ImageLoader.loadImage("/necromancer_left.gif");
		ground = ImageLoader.loadImage("/stonebrick ground.gif");
		wall = ImageLoader.loadImage("/wall.gif");
		witch = ImageLoader.loadImage("/Witch.png");
		witch_left = ImageLoader.loadImage("/Witch_Left.png");
		
	}
	
}
