package data;

import gui.Painter;
import stuff.Const;
import stuff.Helper;
import stuff.Position;

public class Map {

	private int width = Const.WORLD_WIDTH;
	private int height = Const.WORLD_HEIGHT;
	
	private int outerCenterX = (int)(0.5 * width);
	private int outerCenterY = (int)(0.5 * height);
	private int outerRad = (int)(width * 0.4);
	
	private int innerCenterX = (int)(0.5 * width);
	private int innerCenterY = (int)(0.5 * height);
	private int innerRad = (int)(width * 0.1);
	
	private Tile[][] tiles =  new Tile[width][height];
	private Tile nothing = new Tile(Terrain.NOTHING);
	
	private Painter painter;
	
	public Map(Painter painter) {
		this.painter = painter;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Terrain terrain = Terrain.WATER;
				float dist2InnerCenter = Helper.euclidianDistance(
						x, y, innerCenterX, innerCenterY);
				float dist2OuterCenter = Helper.euclidianDistance(
						x, y, outerCenterX, outerCenterY);
				if (dist2InnerCenter > innerRad && dist2OuterCenter < outerRad) {
					terrain = Terrain.LAND;
				}
				tiles[x][y] = new Tile(terrain);
			}
		}
	}
	
	public void update() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (Math.random() > 0.3) tiles[x][y].increaseFood();
			}
		}
	}
	
	public void paint() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				painter.paintTile(x, y, tiles[x][y].getColor());
			}
		}
	}
	
	public Tile getTileAtWorldPosition(Position pos) {
		int x = pos.getX() / Const.TILE_SIZE;
		int y = pos.getY() / Const.TILE_SIZE;
		if (x >= width || y >= height || x < 0 || y < 0) {
			return nothing;
		}
		return tiles[x][y];
	}
}
