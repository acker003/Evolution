package stuff;

public class Const {
	
	// Window Variables
	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 700;

	// Game World Variables
	public static final int WORLD_WIDTH = 100;	// World width in tiles
	public static final int WORLD_HEIGHT = 100;	// World height in tiles
	public static final int TILE_SIZE = 20;	
	public static final float MAXIMUM_FOOD_PER_TILE = 10;
	public static final float NEW_FOOD_PER_FRAME = 0.1f;
	public static final int CREATURE_RAD = 10;
	public static final int FEELER_RAD = 4;
	
	// Energy Costs
	public static final float MINIMUM_SURVIVAL_ENERGY = 100;
	public static final float START_ENERGY = 150;
	public static final float COST_PER_FRAME = 0.1f;
	public static final float COST_WALK = 0.5f;	// per pixel
	public static final float GAIN_EAT = 15f;
	public static final float COST_EAT = 2f;
	public static final float COST_ROTATE = 0.5f;
	
	// Age
	public static final float AGE_PER_FRAME = 0.01f;
	
	// Neuron variables
	public static final int NUMBER_OF_START_HIDDEN_NEURONS = 5;
	public static final int MUTATED_CONNECTIONS_AMOUNT = 10;
	public static final float MUTATION_RATE = 0.1f;
	
	// Stuff
	public static final float MOVESPEED = 4;
	public static final float ROTATE_FACTOR = 0.5f;
	public static final int MIN_AGE_TO_GIVE_BIRTH = 0;
	
	// Thread variables
	public static final int MAX_CREATURE_PER_THREAD = 20;
	public static final int NEW_THREAD_AMOUNT = 10;
	
	// Update variables
	public static final int MS_PER_DELTA_TIME = 50;
	public static final int SLEEP_TIME_PER_ACT = 50;
	public static final int SLEEP_TIME_PER_GUI_UPDATE = 50;	// 20 frames per sec
	public static final int SLEEP_TIME_PER_MAP_UPDATE = 50;	// increase food
	
	// Feeler Variables
	public static final float STANDARD_FEELER_LENGTH = 3 * CREATURE_RAD;
	public static final float MAX_FEELER_DISTANCE = 3 * STANDARD_FEELER_LENGTH;
	public static final float CHANCE_FOR_NEW_FEELER = 1f;
	public static final float CHANCE_FOR_FEELER_DISTANCE_MUTATION = 0.05f;
	public static final float MAX_FEELER_AMOUNT = 1;
}
