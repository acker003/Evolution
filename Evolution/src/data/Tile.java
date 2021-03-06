package data;

import java.awt.Color;

import stuff.Const;

public class Tile {

	private float food = 0;
	private Terrain terrain = Terrain.NOTHING;
	private Color col = Color.gray;
	
	public Tile(Terrain terrain) {
		this.terrain = terrain;
		switch (terrain) {
		case WATER:
			col = Color.blue;
			break;
		case LAND:
			food = (int)(0.4 * Const.MAXIMUM_FOOD_PER_TILE);
			col = new Color((float)((255 - 2 * 100 / Const.MAXIMUM_FOOD_PER_TILE * food) / 255), 1, 0);
			break;
		default:
			break;
		}
	}
	
	public boolean isLand() {
		return terrain == Terrain.LAND;
	}
	
	public boolean isWater() {
		return terrain == Terrain.WATER;
	}
	
	public boolean reduceFood() {
		if (food >= 1) {
			food--;
			col = new Color((float)((255 - 2 * 100 / Const.MAXIMUM_FOOD_PER_TILE * food) / 255), 1, 0);
			return true;
		} return false;
	}
	
	public void increaseFood() {
		if (isLand()) {
			food += Const.NEW_FOOD_PER_FRAME;
			food = food > Const.MAXIMUM_FOOD_PER_TILE ? Const.MAXIMUM_FOOD_PER_TILE : food;
			col = new Color((float)((255 - 2 * 100 / Const.MAXIMUM_FOOD_PER_TILE * food) / 255), 1, 0);
		}
	}
	
	public float getFood() {
		return food;
	}
	
	public void setFood(float food) {
		this.food = food <= Const.MAXIMUM_FOOD_PER_TILE ? 
				food : Const.MAXIMUM_FOOD_PER_TILE;
		col = new Color(255 - 2 * food, 255, 0);
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public Color getColor() {
		return col;
	}
	
	public void setColor(Color col) {
		this.col = col;
	}
	
	
}
