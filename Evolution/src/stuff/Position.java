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
	
}
