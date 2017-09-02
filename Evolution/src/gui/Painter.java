package gui;

import java.awt.Color;
import java.awt.Graphics;

import stuff.Const;
import stuff.Position;

public class Painter {
	
	private Image img = new Image();
	private Graphics g = img.getGraphics();
	private int tileSize = Const.TILE_SIZE;
	private int creatureRad = Const.CREATURE_RAD;

	public void paintTile(int x, int y, Color col) {
		g.setColor(col);
		g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
	}
	
	public void paintCreature(Position pos, Color col) {
		g.setColor(col);
		g.fillOval(pos.getX(), pos.getY(), creatureRad, creatureRad);
	}
	
	public Image getImage() {
		return img;
	}
}
