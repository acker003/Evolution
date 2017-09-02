package gui;

import java.awt.image.BufferedImage;

import stuff.Const;

public class Image extends BufferedImage {
	
	private int width = Const.WORLD_WIDTH * Const.TILE_SIZE;
	private int height = Const.WORLD_HEIGHT * Const.TILE_SIZE;
	
	public Image() {
		super(Const.WORLD_WIDTH * Const.TILE_SIZE, 
			Const.WORLD_HEIGHT * Const.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
	}

}
