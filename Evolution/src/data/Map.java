package data;

import java.awt.Color;

import gui.Painter;
import stuff.Const;
import stuff.Helper;
import stuff.Position;

public class Map {

	private int width = Const.WORLD_WIDTH;
	private int height = Const.WORLD_HEIGHT;
	
//	private int outerCenterX = (int)(0.5 * width);
//	private int outerCenterY = (int)(0.5 * height);
//	private int outerRad = (int)(width * 0.4);
//	
//	private int innerCenterX = (int)(0.5 * width);
//	private int innerCenterY = (int)(0.5 * height);
//	private int innerRad = (int)(width * 0.1);
	
	private Tile[][] tiles =  new Tile[width][height];
	private Tile nothing = new Tile(Terrain.NOTHING);
	
	private Painter painter;
	
	public Map(Painter painter) {
		this.painter = painter;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Terrain terrain = Terrain.WATER;
//				float dist2InnerCenter = Helper.euclidianDistance(
//						x, y, innerCenterX, innerCenterY);
//				float dist2OuterCenter = Helper.euclidianDistance(
//						x, y, outerCenterX, outerCenterY);
//				if (dist2InnerCenter > innerRad && dist2OuterCenter < outerRad) {
//					terrain = Terrain.LAND;
//				}
				float noiseValue = Helper.noise(x * 1.0 / 30, y * 1.0 / 30) + 0.5f * Helper.noise(x * 1.0 / 60, y * 1.0 / 60);
				if (noiseValue >= 0) terrain = Terrain.LAND;
				tiles[x][y] = new Tile(terrain);
				if (noiseValue < 0) {
					tiles[x][y].setColor(new Color(0, (int)(200 - 200 * Helper.toPosNegIntervall((float)Math.sqrt(-noiseValue))), 255));
				}
			}
		}
	}
	
	public void update() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (isFertile(x, y)) tiles[x][y].increaseFood();
			}
		}
	}
	
	private boolean isFertileToNeighbors(int x, int y)
    {
      if (x < 0 || y < 0 || x >= Const.WORLD_WIDTH || y >= Const.WORLD_HEIGHT)
      {
        return false;
      }
      if (tiles[x][y].isWater())
      {
        return true;
      }
      if (tiles[x][y].isLand() && tiles[x][y].getFood() > 0.5 * Const.MAXIMUM_FOOD_PER_TILE)
      {
        return true;
      }
      return false;
    }

    private boolean isFertile(int x, int y)
    {
      if (tiles[x][y].isLand())
      {
        if (tiles[x][y].getFood() > 0.5 * Const.MAXIMUM_FOOD_PER_TILE)
        {
          return true;
        }
        if (isFertileToNeighbors(x - 1, y))
        {
          return true;
        }
        if (isFertileToNeighbors(x + 1, y))
        {
          return true;
        }
        if (isFertileToNeighbors(x, y - 1))
        {
          return true;
        }
        if (isFertileToNeighbors(x, y + 1))
        {
          return true;
        }
      }

      return false;
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
