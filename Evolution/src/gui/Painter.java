package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import creature.Creature;
import creature.Feeler;
import stuff.Const;
import stuff.Helper;
import stuff.Position;

public class Painter implements KeyListener {
	
	private Image img = new Image();
	private Graphics g = img.getGraphics();
	private int tileSize = Const.TILE_SIZE;
	private int creatureRad = Const.CREATURE_RAD;
	private int feelerRad = Const.FEELER_RAD;
	private float zoomFactor = 1;
	private Point topLeftCorner = new Point(0,0);
	private Map<Integer, Boolean> isPressed = new HashMap<Integer, Boolean>();

	public void paintTile(int x, int y, Color col) {
		g.setColor(col);
		int xx = Math.round(x * tileSize * zoomFactor) + topLeftCorner.x;
		int yy = Math.round(y * tileSize * zoomFactor) + topLeftCorner.y;
		g.fillRect(xx, yy, Math.round(tileSize * zoomFactor), Math.round(tileSize * zoomFactor));
	}
	
	public void paintCreature(Creature c) {
		// correct position because java paint left top corner and not the center 
		int xx = Math.round(c.getPos().getX() * zoomFactor) + topLeftCorner.x - Const.CREATURE_RAD;
		int yy = Math.round(c.getPos().getY() * zoomFactor) + topLeftCorner.y - Const.CREATURE_RAD;
		
		if (zoomFactor > 0.8) {
			// paint feelers
			g.setColor(Color.black);
			for (Feeler f : c.getFeelers()) {
				int xF = Math.round(f.getFeelerPosition().getX() * zoomFactor) + topLeftCorner.x - Const.FEELER_RAD;
				int yF = Math.round(f.getFeelerPosition().getY() * zoomFactor) + topLeftCorner.y - Const.FEELER_RAD;
				g.drawLine(xx + Const.CREATURE_RAD, yy + Const.CREATURE_RAD, xF + Const.FEELER_RAD, yF + Const.FEELER_RAD);
				g.setColor(c.getColor());
				g.fillOval(xF, yF, 2 * Math.round(feelerRad * zoomFactor), 2 * Math.round(feelerRad * zoomFactor));
			}
		}
		g.setColor(c.getColor());
		g.fillOval(xx, yy, 2 * Math.round(creatureRad * zoomFactor), 2 * Math.round(creatureRad * zoomFactor));
	}
	
	public void clear() {
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 2000, 2000);
	}
	
	public Image getImage() {
		return img;
	}
	
	public void zoomeToCenter(float factor) {
		// TODO Fix this mess
		float oldWidth = Const.WORLD_WIDTH * Const.TILE_SIZE * 1.0f / zoomFactor;
		float oldHeight = Const.WORLD_HEIGHT * Const.TILE_SIZE * 1.0f / zoomFactor;
		zoomFactor *= factor;
		int x = Math.round(oldWidth / 2 * (1 - 1 / factor));
		int y = Math.round(oldHeight / 2 * (1 - 1 / factor));
		scroll(-x, -y);
	}
	
	public void zoomeToMouse(float factor, Position mouse) {
		zoomFactor *= factor;
	}
	
	public void resetZoom() {
		zoomFactor = 1;
	}
	
	public void scroll(int xDiff, int yDiff) {
		topLeftCorner.setLocation(topLeftCorner.x + xDiff, topLeftCorner.y + yDiff);
	}

	public float getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(float zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public Point getTopLeftCorner() {
		return topLeftCorner;
	}

	public void setTopLeftCorner(Point topLeftCorner) {
		this.topLeftCorner = topLeftCorner;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		isPressed.put(e.getKeyCode(), true);
		//while (isPressed.get(e.getKeyCode())) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				scroll(-10, 0);
				break;
			case KeyEvent.VK_LEFT:
				scroll(10, 0);
				break;
			case KeyEvent.VK_UP:
				scroll(0, 10);
				break;
			case KeyEvent.VK_DOWN:
				scroll(0, -10);
				break;
			case KeyEvent.VK_1:
				zoomeToCenter(1.05f);
				break;
			case KeyEvent.VK_2:
				zoomeToCenter(0.95f);
				break;
			default:
				break;
			}
			Helper.sleep(10);
		//}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isPressed.put(e.getKeyCode(), false);
	}
	
	
	
	
	
}
