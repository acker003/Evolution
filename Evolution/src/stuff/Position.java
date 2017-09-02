package stuff;

public class Position {

	private float x;
	private float y;
	
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return Math.round(x);
	}
	
	public int getY() {
		return Math.round(y);
	}
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public Position add(Position pos) {
		return new Position(x + pos.getX(), y + pos.getY());
	}
	
	public float distance2(Position pos) {
		return Helper.euclidianDistance(x, y, pos.getX(), pos.getY());
	}
}
