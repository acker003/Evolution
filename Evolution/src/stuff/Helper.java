package stuff;

import java.awt.Color;
import java.awt.Point;

public class Helper {

	/**
	 * Generates random number between 1 and max (incl.).
	 * @param max 
	 * @return random number
	 */
	public static int randomInt(int max) {
		return (int)(Math.random() * max) + 1;
	}
	
	public static int randomInt0(int max) {
		return randomInt(max) - 1;
	}
	
	/**
	 * Generates random number between min and max (incl.).
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInt(int min, int max) {
		return randomInt(max - min + 1) + min - 1;
	}
	
	public static float randomFloat() {
		return (float)Math.random();
	}
	
	public static float sigmoid(float x) {
		float et = (float)Math.pow(Math.E, x);
		return (et / (1 + et)) * 2 - 1;
	}
	
	public static Position randomPosition() {
		// Random position in pixels
		return new Position(randomInt(Const.WORLD_WIDTH * Const.TILE_SIZE) - 1, 
				randomInt(Const.WORLD_HEIGHT * Const.TILE_SIZE) - 1);
	}
	
	public static Color randomColor() {
		return new Color(randomInt0(256), randomInt0(256), 
				randomInt0(256));
	}
	
	public static float toIntervall(float value) {
		if (value < 0) return 0;
		if (value > 1) return 1;
		return value;
	}
	
	public static float toPosNegIntervall(float value) {
		if (value < -1) return -1;
		if (value > 1) return 1;
		return value;
	}
	
	public static float toIntervall(float value, float min, float max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	
	public static float toIntervall(float value, double min, double max) {
		if (value < min) return (float)min;
		if (value > max) return (float)max;
		return value;
	}
	
	public static int toIntervall(int value, int min, int max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	
	public static float euclidianDistance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	
}
